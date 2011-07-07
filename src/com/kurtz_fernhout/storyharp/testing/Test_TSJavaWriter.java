package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.kurtz_fernhout.storyharp.TSJavaWriter;
import com.kurtz_fernhout.storyharp.TWorld;

public class Test_TSJavaWriter {
	@Test public void testTSRule() {
		TWorld world = new TWorld();
		//world.loadWorldFromFile("/Users/pdf/Documents/workspace/StoryHarpInJava/data/testing.wld");
		world.loadWorldFromFile("/Users/pdf/Documents/workspace/StoryHarpInJava/data/House And Yard.wld");
		TSJavaWriter javaWriter = new TSJavaWriter();
		assertTrue(javaWriter != null);
		File testFile = new File("test_output.java");
		boolean writingResult = javaWriter.writeJavaProgram(world, testFile.getAbsolutePath());
		assertTrue(writingResult);
		assertTrue(testFile.exists());
		testFile.delete();
	}
}
