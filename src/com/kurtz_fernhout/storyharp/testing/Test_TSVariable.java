package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.kurtz_fernhout.storyharp.TSVariable;
import com.kurtz_fernhout.storyharp.TSVariableDisplayOptions;
import com.kurtz_fernhout.storyharp.TSVariable.TSVariableState;


public class Test_TSVariable {
	@Test public void testTSVariable() {
		TSVariable variable = new TSVariable();
		assertTrue(variable != null);
		assertEquals(variable.getState(), TSVariable.TSVariableState.kAbsent);
		TSVariableState newState = TSVariable.TSVariableState.kPresent;
		variable.setState(newState);
		assertEquals(variable.getState(), TSVariable.TSVariableState.kPresent);
		variable.commandUseages = 1;
		assertTrue(variable.hasUseagesForField(TSVariableDisplayOptions.kRuleCommand));
		variable.moveUseages = 1;
		TSVariableDisplayOptions displayOptions = new TSVariableDisplayOptions();
		assertFalse(variable.meetsDisplayOptions(displayOptions));
		displayOptions.displayOptions[TSVariableDisplayOptions.kRuleMove] = true;
		assertTrue(variable.meetsDisplayOptions(displayOptions));
	}
}
