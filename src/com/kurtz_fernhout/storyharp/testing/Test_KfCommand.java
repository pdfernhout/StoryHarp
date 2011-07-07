package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.kurtz_fernhout.storyharp.KfCommand;

public class Test_KfCommand {

	@Test
	public void testKfCommand() {
		KfCommand command = new KfCommand();
		assertTrue(command != null);
		assertTrue(!command.done);
		command.doCommand();
		assertTrue(command.done);
		command.undoCommand();
		assertTrue(!command.done);
		command.redoCommand();
		assertTrue(command.done);
		command.undoCommand();
		assertTrue(!command.done);
	}

}