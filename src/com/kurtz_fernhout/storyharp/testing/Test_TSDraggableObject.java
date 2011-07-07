package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kurtz_fernhout.storyharp.TSDraggableObject;

public class Test_TSDraggableObject {

	@Test
	public void testTSDraggableObject() {
		TSDraggableObject draggableObject = new TSDraggableObject();
		draggableObject.setPosition("10,20");
		assertEquals(draggableObject.position.x, 10);
		assertEquals(draggableObject.position.y, 20);
	}

}