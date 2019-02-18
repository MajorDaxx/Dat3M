package com.dat3m.dartagnan.program.arch.tso.event;

import com.dat3m.dartagnan.program.Thread;
import com.dat3m.dartagnan.wmm.utils.Arch;
import com.google.common.collect.ImmutableSet;
import com.dat3m.dartagnan.program.Register;
import com.dat3m.dartagnan.program.event.Local;
import com.dat3m.dartagnan.program.event.MemEvent;
import com.dat3m.dartagnan.program.event.rmw.RMWLoad;
import com.dat3m.dartagnan.program.event.rmw.RMWStore;
import com.dat3m.dartagnan.program.event.utils.RegReaderData;
import com.dat3m.dartagnan.program.event.utils.RegWriter;
import com.dat3m.dartagnan.program.memory.Address;
import com.dat3m.dartagnan.program.arch.tso.utils.EType;

public class Xchg extends MemEvent implements RegWriter, RegReaderData {

    private Register resultRegister;
    private ImmutableSet<Register> dataRegs;

    public Xchg(Address address, Register register, String atomic) {
        this.address = address;
        this.resultRegister = register;
        this.atomic = atomic;
        this.condLevel = 0;
        this.dataRegs = ImmutableSet.of(resultRegister);
        addFilters(EType.ANY, EType.VISIBLE, EType.MEMORY, EType.READ, EType.WRITE, EType.ATOM, EType.REG_WRITER, EType.REG_READER);
    }

    @Override
    public Register getResultRegister(){
        return resultRegister;
    }

    @Override
    public ImmutableSet<Register> getDataRegs(){
        return dataRegs;
    }

    @Override
    public Thread compile(Arch target) {
        if(target == Arch.TSO) {
            Register dummyReg = new Register(null, resultRegister.getThreadId());
            RMWLoad load = new RMWLoad(dummyReg, address, atomic);
            load.setHLId(hlId);
            load.setCondLevel(condLevel);
            load.addFilters(EType.ATOM);

            RMWStore store = new RMWStore(load, address, resultRegister, atomic);
            store.setHLId(hlId);
            store.setCondLevel(condLevel);
            store.addFilters(EType.ATOM);

            return Thread.fromArray(false, load, store, new Local(resultRegister, dummyReg));
        }
        throw new RuntimeException("xchg " + atomic + " is not implemented for " + target);
    }

    @Override
    public String toString() {
        return nTimesCondLevel() + "xchg(*" + address + ", " + resultRegister + ", " + "atomic)";
    }

    @Override
    public Xchg clone() {
        if(clone == null){
            clone= new Xchg((Address) address, resultRegister, atomic);
            afterClone();
        }
        return (Xchg)clone;
    }
}