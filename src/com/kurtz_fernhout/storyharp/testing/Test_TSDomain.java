package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.kurtz_fernhout.storyharp.TSDomain;

public class Test_TSDomain {
	@Test public void testCreation() {
		String[] args = {"/I"};
		TSDomain domain = new TSDomain(args);
		assertTrue(domain != null);
		String testString = "Test123\0\1\017\020\377";
		String encodedString = domain.weirdHexEncode(testString);
		String decodedString = domain.weirdHexUnencode(encodedString);
		assertEquals(testString, decodedString);
	}
	
	@Test public void testEncoding() {
		String[] args = {"/I"};
		TSDomain domain = new TSDomain(args);
		assertTrue(domain != null);
		String testString = "Test123\0\1\017\020\377";
		String encodedString = domain.weirdHexEncode(testString);
		String decodedString = domain.weirdHexUnencode(encodedString);
		assertEquals(testString, decodedString);
	}
	
	@Test public void testIniWritingAndReading() {
		String[] args = {"/I"};
		TSDomain domain = new TSDomain(args);
		assertTrue(domain != null);
		domain.options.playerSpeak = false;
		boolean storeResult = domain.storeProfileInformation();
		assertTrue("is ini file written", storeResult);
		domain.getProfileInformation();
		// this should have the previously stored value
		assertFalse(domain.options.playerSpeak);
	}
	
	@Test public void testProfileDefaultReading() {
		String[] args = {"/I"};
		TSDomain domain = new TSDomain(args);
		assertTrue(domain != null);
		domain.iniFileName = "should_not_be_present_during_test.ini";
		// this should be defaulted back to true when INI is defaulted
		domain.options.playerSpeak = false;
		domain.getProfileInformation();
		assertTrue(domain.options.playerSpeak);
	}

	@Test public void testRuleChange() {
		String[] args = {"/I"};
		TSDomain domain = new TSDomain(args);
		assertTrue(domain != null);
		//domain.
	}

}
