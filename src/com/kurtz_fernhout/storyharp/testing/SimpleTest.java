package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.kurtz_fernhout.storyharp.DomainOptionsStructure;

@RunWith(Suite.class)
@Suite.SuiteClasses({Test_KfCommand.class, Test_KfCommandList.class, Test_TSDraggableObject.class})

public class SimpleTest extends Test_KfCommand {
	
	@Test public void simpleTest() {
		DomainOptionsStructure options = new DomainOptionsStructure();
		String testString = "test";
		options.extraMediaDirectory = testString;
		assertTrue(options.extraMediaDirectory.equals(testString));
	}
	
	
}
