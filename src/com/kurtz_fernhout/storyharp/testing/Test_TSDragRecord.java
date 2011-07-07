package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.kurtz_fernhout.storyharp.TSDragRecord;
import com.kurtz_fernhout.storyharp.TSDraggableObject;


public class Test_TSDragRecord {
	@Test public void testDragRecord() {
		TSDraggableObject draggableObject = new TSDraggableObject();
		TSDragRecord dragRecord = new TSDragRecord(draggableObject);
		assertTrue(dragRecord != null);
	}
}
