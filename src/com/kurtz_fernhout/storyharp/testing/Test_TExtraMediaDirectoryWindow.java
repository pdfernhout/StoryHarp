package com.kurtz_fernhout.storyharp.testing;

import org.junit.Test;
import org.uispec4j.Button;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;

import com.kurtz_fernhout.storyharp.TExtraMediaDirectoryWindow;


public class Test_TExtraMediaDirectoryWindow extends UISpecTestCase {
	protected void setUp() throws Exception {
		setAdapter(new MainClassAdapter(TExtraMediaDirectoryWindow.class, new String[0]));
	}
	
	@Test public void test() {
		// TExtraMediaDirectoryWindow window = new TExtraMediaDirectoryWindow();
		// window.setVisible(true);
		Window window = getMainWindow();
		Button okButton = window.getButton("OK");
		okButton.click();
	}
}
