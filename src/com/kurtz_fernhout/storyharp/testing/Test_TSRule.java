package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.*;
import org.junit.Test;
import com.kurtz_fernhout.storyharp.TSRule;

public class Test_TSRule {
	@Test public void testTSRule() {
		TSRule rule = new TSRule();
		assertTrue(rule != null);
		String name = rule.displayName();
		assertTrue(name != null);
	}
}
