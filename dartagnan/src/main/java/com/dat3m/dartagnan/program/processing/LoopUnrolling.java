package com.dat3m.dartagnan.program.processing;

import com.dat3m.dartagnan.program.Program;
import com.dat3m.dartagnan.program.Thread;
import com.dat3m.dartagnan.program.event.EventFactory;
import com.dat3m.dartagnan.program.event.Tag;
import com.dat3m.dartagnan.program.event.core.CondJump;
import com.dat3m.dartagnan.program.event.core.Event;
import com.dat3m.dartagnan.program.event.core.Label;
import com.dat3m.dartagnan.utils.printer.Printer;
import com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sosy_lab.common.configuration.*;

import static com.dat3m.dartagnan.configuration.OptionNames.*;

@Options
public class LoopUnrolling implements ProgramProcessor {

    private static final Logger logger = LogManager.getLogger(LoopUnrolling.class);

    // =========================== Configurables ===========================

    @Option(name = BOUND,
            description = "Unrolls loops up to loopBound many times.",
            secure = true)
    @IntegerOption(min = 1)
    private int bound = 1;

    public int getUnrollingBound() { return bound; }
    public void setUnrollingBound(int bound) {
        Preconditions.checkArgument(bound >= 1, "The unrolling bound must be positive.");
        this.bound = bound;
    }

    @Option(name = PRINT_PROGRAM_AFTER_UNROLLING,
            description = "Prints the program after unrolling.",
            secure = true)
    private boolean print = false;

    // =====================================================================

    private LoopUnrolling() { }

    private LoopUnrolling(Configuration config) throws InvalidConfigurationException {
        this();
        config.inject(this);
    }

    public static LoopUnrolling fromConfig(Configuration config) throws InvalidConfigurationException {
        return new LoopUnrolling(config);
    }

    public static LoopUnrolling newInstance() {
        return new LoopUnrolling();
    }


    @Override
    public void run(Program program) {
        if (program.isUnrolled()) {
            logger.warn("Skipped unrolling: Program is already unrolled.");
            return;
        }

        int nextId = 0;
        for(Thread thread : program.getThreads()){
            nextId = unrollThread(thread, bound, nextId);
        }
        program.clearCache(false);
        program.markAsUnrolled(bound);

        logger.info("Program unrolled {} times", bound);
        if(print) {
        	System.out.println("===== Program after unrolling =====");
        	System.out.println(new Printer().print(program));
        	System.out.println("===================================");
        }
}

    private int unrollThread(Thread t, int bound, int nextId){
        while(bound > 0) {
            unrollThreadOnce(t, bound--);
        }
        t.clearCache();
        t.updateExit(t.getEntry());
        for (Event e : t.getEvents()) {
            e.setUId(nextId++);
        }
        return nextId;
    }

    private void unrollThreadOnce(Thread t, int bound) {
        // NOTE: The implemented unroll semantics are identical to the previous one we had.
        //TODO: We might want to allow usage of different bounds per loop by e.g.
        // annotating the looping jump with a custom bound counter
        //TODO (2): The code can surely be cleaned up somehow
        Event cur = t.getEntry();
        Event successor;
        Event predecessor = null;
        Event newPred;
        do {
            successor = cur.getSuccessor();

            if (cur instanceof CondJump && ((CondJump) cur).getLabel().getOId() < cur.getOId()) {
                CondJump jump = (CondJump) cur;
                Label label = jump.getLabel();
                if (bound > 1) {
                    predecessor = copyPath(label, successor, predecessor);
                }

                if (bound == 1 || jump.hasFilter(Tag.SPINLOOP)) {
                    Label target = (Label) jump.getThread().getExit();
                    newPred = EventFactory.newGoto(target);
                    newPred.addFilters(Tag.BOUND);
                    if(jump.hasFilter(Tag.SPINLOOP)) {
                    	newPred.addFilters(Tag.SPINLOOP);
                    }
                    predecessor.setSuccessor(newPred);
                } else {
                    newPred = predecessor;
                }
            } else {
                newPred = cur;
                if (predecessor != null) {
                    // This check must be done inside this if
                    // Needed for the current implementation of copy in If events
                    //TODO: Is this needed anymore since we got rid of If events?
                    if (bound != 1) {
                        newPred = cur.getCopy();
                    }
                    predecessor.setSuccessor(newPred);
                }
            }

            cur = successor;
            predecessor = newPred;
        } while(successor != null);

    }

    private Event copyPath(Event from, Event until, Event appendTo){
        while(from != null && !from.equals(until)){
            Event copy = from.getCopy();
            appendTo.setSuccessor(copy);
            appendTo = copy;
            from = from.getSuccessor();
        }
        return appendTo;
    }
}