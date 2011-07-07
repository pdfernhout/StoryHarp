package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.kurtz_fernhout.storyharp.TSSpeechSystem;

public class Test_TSSpeechSystem {
	TSSpeechSystem speechSystem = null;

	@Before
	public void setUp() throws Exception {
		speechSystem = new TSSpeechSystem();
	}

	@Test
	public void testJoinSentences() {
		String result = speechSystem.joinSentences("Sentence one.", "Sentence two.");
		assertEquals(result, "Sentence one. Sentence two.");
	}

	@Test
	public void testDoCommand() {
		// PDF FIX speechSystem.doCommand("look");
	}

	@Test
	public void testSayOptions() {
		speechSystem.sayOptions();
	}

	@Test
	public void testSpeakSound() throws InterruptedException {
		speechSystem.usingSpeech = true;
		speechSystem.speakSound("music data/short.mid");
		Thread.sleep(4000);
		speechSystem.speakSound("sound data/flute1.wav");
		Thread.sleep(2000);
		speechSystem.speakSound("sound data/flute2.wav");
		speechSystem.speakSound("music data/short.mid");
		Thread.sleep(4000);
		speechSystem.speakSound("sound data/flute3.wav");
		Thread.sleep(2000);
	}

}
