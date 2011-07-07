package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.kurtz_fernhout.storyharp.TSDomain;
import com.kurtz_fernhout.storyharp.TSRule;
import com.kurtz_fernhout.storyharp.TSVariable;
import com.kurtz_fernhout.storyharp.TWorld;
import junitx.framework.FileAssert;

public class Test_TWorld {
	@Test public void testWorldLoadingAndSaving() {
		TWorld world = new TWorld();
		assertTrue(world != null);
		File worldFile = new File("/Users/pdf/Documents/workspace/StoryHarpInJava/data/testing.wld");
		File tempFile = new File(worldFile.getAbsoluteFile() + ".output");
		boolean loadWorldResult = world.loadWorldFromFile(worldFile.getAbsolutePath());
		assertTrue(loadWorldResult);
		assertTrue(world.rules.size() > 0);
		//TSVariable variable = world.findVariable("one");
		//assertEquals(variable.state, TSVariable.TSVariableState.kAbsent);
		try {
			boolean saveResult = world.saveWorldToFile(tempFile.getAbsolutePath(), false);
			assertTrue(saveResult);
			FileAssert.assertEquals(worldFile, tempFile);
		} finally {
			if (tempFile.exists()) {
				tempFile.delete();
			}
		}
	}
	
	@Test public void testSessionLoadingAndSaving() {
		TWorld world = new TWorld();
		assertTrue(world != null);
		// load a world
		File worldFile = new File("/Users/pdf/Documents/workspace/StoryHarpInJava/data/testing.wld");
		boolean loadWorldResult = world.loadWorldFromFile(worldFile.getAbsolutePath());
		assertTrue(loadWorldResult);
		assertTrue(world.rules.size() > 0);
		TSVariable variable = world.findVariable("one");
		assertEquals(variable.state, TSVariable.TSVariableState.kAbsent);
		// load the session
		String worldFileName = "testing.wld";
		File sessionFile = new File("/Users/pdf/Documents/workspace/StoryHarpInJava/data/test.ses");
		File tempFile = new File(sessionFile.getAbsoluteFile() + ".output");
		boolean loadSessionResult = world.loadSessionFromFile(sessionFile.getAbsolutePath(), worldFileName);
		assertTrue(loadSessionResult);
		assertEquals(variable.state, TSVariable.TSVariableState.kPresent);
		try {
			boolean saveResult = world.saveSessionToFile(tempFile.getAbsolutePath(), worldFileName);
			assertTrue(saveResult);
			FileAssert.assertBinaryEquals(sessionFile, tempFile);
		} finally {
			if (tempFile.exists()) {
				tempFile.delete();
			}
		}
	}
	
	@Test public void testRuleFiring() {
		TWorld world = new TWorld();
		TSRule rule = world.newRule();
        rule.setContext("context_test");
        rule.setCommand("command_test");
        rule.setReply("reply_test");
        rule.setMove("move_test");
        rule.setChanges("changes_test");
        rule.setRequirements("requirements_test");
        rule.setPosition("0,0|0,0|0,0");
        TSDomain.domain.sessionCommandList.doCommandPhrase("command_test");
	}

}