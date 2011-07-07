package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import com.kurtz_fernhout.storyharp.KfCommand;
import com.kurtz_fernhout.storyharp.KfCommandList;
import com.kurtz_fernhout.storyharp.KfCommand.CommandChangedListener;
import com.kurtz_fernhout.storyharp.KfCommand.KfCommandChangeType;

public class Test_KfCommandList {

	@Test public void testKfCommandList() {
		final HashMap<String, KfCommandChangeType> hashMap = new HashMap<String, KfCommandChangeType>();
		CommandChangedListener listener = new CommandChangedListener() {
			public void commandChanged(KfCommand command, KfCommandChangeType changeType) {
				hashMap.put("commandChangeType", changeType);
			}
		};
		KfCommandList commandList = new KfCommandList();
		KfCommand command = new KfCommand();
		command.commandChangedListener = listener;
		assertTrue(command != null);
		assertTrue(hashMap.get("commandChangeType") == null);
		assertTrue(!command.done);
		commandList.doCommand(command);
		assertEquals(hashMap.get("commandChangeType"), KfCommandChangeType.commandDone);
		assertTrue(command.done);
		commandList.undoLast();
		assertEquals(hashMap.get("commandChangeType"), KfCommandChangeType.commandUndone);
		assertTrue(!command.done);
		commandList.redoLast();
		assertEquals(hashMap.get("commandChangeType"), KfCommandChangeType.commandDone);
		assertTrue(command.done);
		commandList.undoLast();
		assertEquals(hashMap.get("commandChangeType"), KfCommandChangeType.commandUndone);
		assertTrue(!command.done);
	}

}