package com.kurtz_fernhout.storyharp.testing;

import org.junit.Test;
import org.uispec4j.TextBox;
import org.uispec4j.ToggleButton;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;

import com.kurtz_fernhout.storyharp.TPictureWindow;


public class Test_TPictureWindow  extends UISpecTestCase {
	protected void setUp() throws Exception {
		setAdapter(new MainClassAdapter(TPictureWindow.class, new String[0]));
	}
	@Test public void test() {
		Window window = getMainWindow();
		ToggleButton firstButton = window.getToggleButton("first");
		ToggleButton nextButton = window.getToggleButton("next");
		ToggleButton previousButton = window.getToggleButton("previous");
		ToggleButton lastButton = window.getToggleButton("last");
		// buttons should be disabled until data is put in -- but the main puts in five pictures
		assertTrue(firstButton.isEnabled());
		TextBox label = window.getTextBox("label");
		assertEquals(label.getText(), "5 of 5");
		firstButton.click();
		assertEquals(label.getText(), "1 of 5");
		nextButton.click();
		assertEquals(label.getText(), "2 of 5");
		nextButton.click();
		assertEquals(label.getText(), "3 of 5");
		previousButton.click();
		assertEquals(label.getText(), "2 of 5");
		lastButton.click();
		assertEquals(label.getText(), "5 of 5");
		previousButton.click();
		assertEquals(label.getText(), "4 of 5");
		TPictureWindow pictureWindow = (TPictureWindow) window.getAwtComponent();
		pictureWindow.clearPictures();
		assertFalse(firstButton.isEnabled());
		assertFalse(nextButton.isEnabled());
		assertFalse(previousButton.isEnabled());
		assertFalse(lastButton.isEnabled());
		assertFalse(window.isVisible());
		// would fail: firstButton.click();
		assertEquals(label.getText(), "0 of 0");
		// this makes the window visible and needs to be intercepted
//		pictureWindow.addPictureFromFile("data/test1.BMP", "contents of: data/test1.BMP");
//		assertFalse(firstButton.isEnabled());
//		assertFalse(nextButton.isEnabled());
//		assertFalse(previousButton.isEnabled());
//		assertTrue(lastButton.isEnabled());
	}
}
