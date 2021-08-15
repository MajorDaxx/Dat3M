package com.dat3m.dartagnan.expression.op;

import java.math.BigInteger;
import org.sosy_lab.java_smt.api.BitvectorFormula;
import org.sosy_lab.java_smt.api.BitvectorFormulaManager;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.BooleanFormulaManager;
import org.sosy_lab.java_smt.api.Formula;
import org.sosy_lab.java_smt.api.IntegerFormulaManager;
import org.sosy_lab.java_smt.api.NumeralFormula.IntegerFormula;
import org.sosy_lab.java_smt.api.SolverContext;

public enum COpBin {
    EQ, NEQ, GTE, LTE, GT, LT, UGTE, ULTE, UGT, ULT;

    @Override
    public String toString() {
        switch(this){
            case EQ:
                return "==";
            case NEQ:
                return "!=";
            case GTE:
            case UGTE:
                return ">=";
            case LTE:
            case ULTE:
                return "<=";
            case GT:
            case UGT:
                return ">";
            case LT:
            case ULT:
                return "<";
        }
        return super.toString();
    }

    public BooleanFormula encode(Formula e1, Formula e2, SolverContext ctx) {
        BooleanFormulaManager bmgr = ctx.getFormulaManager().getBooleanFormulaManager();
        if(e1 instanceof BooleanFormula) {
    		switch(this) {
            	case EQ:
            		return bmgr.equivalence((BooleanFormula)e1, (BooleanFormula)e2);
            	case NEQ:
            		return bmgr.not(bmgr.equivalence((BooleanFormula)e1, (BooleanFormula)e2));
            	default:
            		throw new UnsupportedOperationException("Encoding of not supported for COpBin " + this);
    		}
        }
        if(e1 instanceof IntegerFormula) {
			IntegerFormulaManager imgr = ctx.getFormulaManager().getIntegerFormulaManager();
    		switch(this) {
    			case EQ:            		
            		return imgr.equal((IntegerFormula)e1, (IntegerFormula)e2);
    			case NEQ:
            		return bmgr.not(imgr.equal((IntegerFormula)e1, (IntegerFormula)e2));
    			case LT:
    			case ULT:
            		return imgr.lessThan((IntegerFormula)e1, (IntegerFormula)e2);
    			case LTE:
    			case ULTE:
            		return imgr.lessOrEquals((IntegerFormula)e1, (IntegerFormula)e2);
    			case GT:
    			case UGT:
            		return imgr.greaterThan((IntegerFormula)e1, (IntegerFormula)e2);
    			case GTE:
    			case UGTE:
            		return imgr.greaterOrEquals((IntegerFormula)e1, (IntegerFormula)e2);
    		}  	
        }
        if(e1 instanceof BitvectorFormula) {
        	BitvectorFormulaManager bvmgr = ctx.getFormulaManager().getBitvectorFormulaManager();
    		switch(this) {
            	case EQ:
            		return bvmgr.equal((BitvectorFormula)e1, (BitvectorFormula)e2);
            	case NEQ:
            		return bmgr.not(bvmgr.equal((BitvectorFormula)e1, (BitvectorFormula)e2));
            	case LT:
            	case ULT:
            		return bvmgr.lessThan((BitvectorFormula)e1, (BitvectorFormula)e2, this.equals(LT));
            	case LTE:
            	case ULTE:
            		return bvmgr.lessOrEquals((BitvectorFormula)e1, (BitvectorFormula)e2, this.equals(LTE));
            	case GT:
            	case UGT:
            		return bvmgr.greaterThan((BitvectorFormula)e1, (BitvectorFormula)e2, this.equals(GT));
            	case GTE:
            	case UGTE:
            		return bvmgr.greaterOrEquals((BitvectorFormula)e1, (BitvectorFormula)e2, this.equals(GTE));
    		}        	
        }
        throw new UnsupportedOperationException("Encoding of not supported for COpBin " + this);
    }

    public boolean combine(BigInteger a, BigInteger b){
        switch(this){
            case EQ:
                return a.compareTo(b) == 0;
            case NEQ:
                return a.compareTo(b) != 0;
            case LT:
            case ULT:
                return a.compareTo(b) < 0;
            case LTE:
            case ULTE:
                return a.compareTo(b) <= 0;
            case GT:
            case UGT:
                return a.compareTo(b) > 0;
            case GTE:
            case UGTE:
                return a.compareTo(b) >= 0;
        }
        throw new UnsupportedOperationException("Illegal operator " + this + " in COpBin");
    }
}
