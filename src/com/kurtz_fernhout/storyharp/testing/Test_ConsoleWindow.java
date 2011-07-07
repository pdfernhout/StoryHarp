package com.kurtz_fernhout.storyharp.testing;

import java.awt.Color;

import javax.swing.AbstractButton;

import org.junit.Test;
import org.uispec4j.MenuItem;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;

import com.kurtz_fernhout.storyharp.TConsoleWindow;


public class Test_ConsoleWindow extends UISpecTestCase {
	protected void setUp() throws Exception {
		setAdapter(new MainClassAdapter(TConsoleWindow.class, new String[0]));
	}
	
	@Test public void testTranscript() {
		// TConsoleWindow window = new TConsoleWindow();
		// window.setVisible(true);
		Window window = getMainWindow();
		TConsoleWindow consoleWindow = (TConsoleWindow) window.getAwtComponent();
		//Button okButton = window.getButton("OK");
		//okButton.click();
		consoleWindow.clearTranscript();
		consoleWindow.addLineToTranscript("Testing 1 2 3", Color.orange);
		consoleWindow.scrollTranscriptEndIntoView();
	}
	
	@Test public void testMenus() {
		Window window = getMainWindow();
		
		MenuItem undoMenuItem = window.getMenuBar().getMenu("Edit").getSubMenu("Undo");
		//undoMenuItem.click();
		assertFalse(undoMenuItem.isEnabled());
		
		MenuItem reundoMenuItem = window.getMenuBar().getMenu("Edit").getSubMenu("Redo");
		//reundoMenuItem.click();
		assertFalse(reundoMenuItem.isEnabled());
		
		MenuItem copyMenuItem = window.getMenuBar().getMenu("Edit").getSubMenu("Copy");
		copyMenuItem.click();
		
		MenuItem showTranscriptMenuItem = window.getMenuBar().getMenu("Settings").getSubMenu("Show Transcript");
		assertFalse(((AbstractButton) (showTranscriptMenuItem.getAwtComponent())).isSelected());
		showTranscriptMenuItem.click();
		showTranscriptMenuItem.click();
		showTranscriptMenuItem.click();
	}
	
}
