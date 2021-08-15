package com.dat3m.dartagnan.program.utils;

import org.sosy_lab.java_smt.api.BitvectorFormula;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.Formula;
import org.sosy_lab.java_smt.api.FormulaManager;
import org.sosy_lab.java_smt.api.SolverContext;
import org.sosy_lab.java_smt.api.NumeralFormula.IntegerFormula;

public class Utils {

	public static BooleanFormula generalEqual(Formula f1, Formula f2, SolverContext ctx) {
		FormulaManager fmgr = ctx.getFormulaManager();
		if(f1 instanceof IntegerFormula && f2 instanceof IntegerFormula) {
			return fmgr.getIntegerFormulaManager().equal((IntegerFormula)f1, (IntegerFormula)f2);
		}
		if(f1 instanceof BitvectorFormula && f2 instanceof BitvectorFormula) {
			return fmgr.getBitvectorFormulaManager().equal((BitvectorFormula)f1, (BitvectorFormula)f2);
		}
		throw new RuntimeException(String.format("Formulas %s and %s have different types or are of unsopported type for generalEqual", f1, f2));
	}
	
	public static BooleanFormula generalGreaterThan(Formula f1, Formula f2, SolverContext ctx) {
		FormulaManager fmgr = ctx.getFormulaManager();
		if(f1 instanceof IntegerFormula && f2 instanceof IntegerFormula) {
			return fmgr.getIntegerFormulaManager().greaterThan((IntegerFormula)f1, (IntegerFormula)f2);
		}
		if(f1 instanceof BitvectorFormula && f2 instanceof BitvectorFormula) {
			return fmgr.getBitvectorFormulaManager().greaterThan((BitvectorFormula)f1, (BitvectorFormula)f2, false);
		}
		throw new RuntimeException(String.format("Formulas %s and %s have different types or are of unsopported type for generalEqual", f1, f2));
	}
	
}
