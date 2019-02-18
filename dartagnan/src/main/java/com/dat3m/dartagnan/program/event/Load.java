package com.dat3m.dartagnan.program.event;

import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.dat3m.dartagnan.expression.ExprInterface;
import com.dat3m.dartagnan.expression.IExpr;
import com.dat3m.dartagnan.program.Register;
import com.dat3m.dartagnan.program.event.utils.RegWriter;
import com.dat3m.dartagnan.program.utils.EType;

public class Load extends MemEvent implements RegWriter {

    protected Register resultRegister;

    public Load(Register register, IExpr address, String atomic) {
        this.address = address;
        this.atomic = atomic;
        this.condLevel = 0;
        this.resultRegister = register;
        addFilters(EType.ANY, EType.VISIBLE, EType.MEMORY, EType.READ, EType.REG_WRITER);
    }

    @Override
    public void initialise(Context ctx) {
        memValueExpr = resultRegister.toZ3IntResult(this, ctx);
        memAddressExpr = address.toZ3Int(this, ctx);
    }

    @Override
    public Register getResultRegister(){
        return resultRegister;
    }

    @Override
    public IntExpr getResultRegisterExpr(){
        return memValueExpr;
    }

    @Override
    public String toString() {
        return nTimesCondLevel() + resultRegister + " = load(*" + address + (atomic != null ? ", " + atomic : "") + ")";
    }

    @Override
    public String label(){
        return "R_" + atomic;
    }

    @Override
    public Load clone() {
        if(clone == null){
            clone = new Load(resultRegister, address, atomic);
            afterClone();
        }
        return (Load)clone;
    }

    @Override
    public ExprInterface getMemValue(){
        return resultRegister;
    }
}