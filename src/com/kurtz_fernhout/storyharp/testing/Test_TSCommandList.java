package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.kurtz_fernhout.storyharp.TSCommandList;

public class Test_TSCommandList {
	@Test public void testCreation() {
		TSCommandList commandList = new TSCommandList();
		assertTrue(commandList != null);
		commandList.doCommandPhrase("test");
	}

}
