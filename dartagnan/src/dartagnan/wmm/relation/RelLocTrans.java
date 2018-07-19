package dartagnan.wmm.relation;

import dartagnan.program.Program;
import dartagnan.program.event.Event;
import dartagnan.program.event.Fence;
import dartagnan.program.event.Local;
import dartagnan.program.event.MemEvent;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 * @author Florian Furbach
 */
public class RelLocTrans extends RelTrans {

    public RelLocTrans(Relation r1) {
        super(r1);
    }

    @Override
    protected Collection<Event> getProgramEvents(Program program){
        return program.getEvents().stream().filter(e -> e instanceof MemEvent || e instanceof Fence || e instanceof Local).collect(Collectors.toSet());
    }
}
