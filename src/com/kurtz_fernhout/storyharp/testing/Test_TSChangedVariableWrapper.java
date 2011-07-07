package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.kurtz_fernhout.storyharp.TSChangedVariableWrapper;
import com.kurtz_fernhout.storyharp.TSVariable;


public class Test_TSChangedVariableWrapper {
	@Test public void testTSChangedVariableWrapper() {
		TSVariable variable = new TSVariable();
		TSChangedVariableWrapper rule = new TSChangedVariableWrapper(variable, TSVariable.TSVariableState.kPresent);
		assertTrue(rule != null);
		assertEquals(variable.getState(), TSVariable.TSVariableState.kAbsent);
		rule.doChange();
		assertEquals(variable.getState(), TSVariable.TSVariableState.kPresent);
		rule.undoChange();
		assertEquals(variable.getState(), TSVariable.TSVariableState.kAbsent);
		}
}
