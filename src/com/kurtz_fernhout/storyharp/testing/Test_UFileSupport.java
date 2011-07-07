package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.kurtz_fernhout.storyharp.UFileSupport;
import com.kurtz_fernhout.storyharp.SaveFileNamesStructure;

public class Test_UFileSupport {
	@Test public void testCreation() {
		SaveFileNamesStructure saveFileNames = new SaveFileNamesStructure();
		assertTrue(saveFileNames != null);
	}
	
	@Test public void rectangles() {
		Rectangle rectangle1 = new Rectangle(10, 20, 30, 40);
		String string1 = UFileSupport.rectToString(rectangle1);
		Rectangle rectangle2 = UFileSupport.stringToRect(string1);
		String string2 = UFileSupport.rectToString(rectangle2);
		assertEquals(rectangle1.x, rectangle2.x);
		assertEquals(rectangle1.y, rectangle2.y);
		assertEquals(rectangle1.width, rectangle2.width);
		assertEquals(rectangle1.height, rectangle2.height);
		assertEquals(string1, string2);
	}
	
	@Test public void points() {
		Point point1 = new Point(10, 20);
		String string1 = UFileSupport.pointToString(point1);
		Point point2 = UFileSupport.stringToPoint(string1);
		String string2 = UFileSupport.pointToString(point2);
		assertEquals(point1.x, point2.x);
		assertEquals(point1.y, point2.y);
		assertEquals(string1, string2);
	}
	
	@Test public void booleans() {
		assertTrue(UFileSupport.strToBool("true"));
		assertFalse(UFileSupport.strToBool("false"));
		assertEquals(UFileSupport.boolToStr(false), "false");
		assertEquals(UFileSupport.boolToStr(true), "true");
	}
	
	@Test public void fileNameStuff() {
		// test exists
		assertTrue(UFileSupport.FileExists("."));
		
		// test deleting
		
		File tempFile = null;
		try {
			tempFile = File.createTempFile("temporary_for_testing", ".tmp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(UFileSupport.FileExists(tempFile.getAbsolutePath()));
		assertTrue(UFileSupport.DeleteFile(tempFile.getAbsolutePath()));
		assertFalse(UFileSupport.FileExists(tempFile.getAbsolutePath()));
		assertFalse(UFileSupport.DeleteFile(tempFile.getAbsolutePath()));
		
		// test renaming 
		
		try {
			tempFile = File.createTempFile("temporary_for_testing", ".tmp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String newTempFileName = tempFile.getAbsolutePath() + ".rename_test";
		
		assertTrue(UFileSupport.FileExists(tempFile.getAbsolutePath()));
		assertFalse(UFileSupport.FileExists(newTempFileName));
		assertTrue(UFileSupport.RenameFile(tempFile.getAbsolutePath(), newTempFileName));
		assertFalse(UFileSupport.FileExists(tempFile.getAbsolutePath()));
		assertTrue(UFileSupport.FileExists(newTempFileName));
		
		// rename should fail if done a second time
		assertFalse(UFileSupport.RenameFile(tempFile.getAbsolutePath(), newTempFileName));
		
		assertTrue(UFileSupport.DeleteFile(newTempFileName));
		assertFalse(UFileSupport.FileExists(newTempFileName));
		
		// test other functions
		
		String fileName = "test_fileNameStuff.temp";
		String fileExtension = ".temp";
		String differentFileName = "test_fileNameStuff.different";
		String differentFileExtension = ".different";
		
		
		String shortPath = "tmp_for_testing";
		String fileNameWithShortPath = shortPath + "/" + fileName;
		String differentFileNameWithShortPath = shortPath + "/" + differentFileName;
		
		File fileForFileName = new File(fileName);
		File fileForShortPath = new File(shortPath);
		File fileForFileNameWithShortPath = new File(fileNameWithShortPath);
		
		assertFalse(UFileSupport.FileExists(fileNameWithShortPath));
		
		assertEquals(UFileSupport.ExtractFileName(fileNameWithShortPath), fileName);
		
		assertEquals(UFileSupport.ExpandFileName(fileName), fileForFileName.getAbsolutePath());
		
		assertEquals(UFileSupport.ExpandFileName(fileNameWithShortPath), fileForFileNameWithShortPath.getAbsolutePath());
		
		assertEquals(UFileSupport.ExtractFileDir(fileNameWithShortPath), fileForShortPath.getAbsolutePath());
		
		assertEquals(UFileSupport.ExtractFilePath(fileNameWithShortPath), fileForShortPath.getAbsolutePath() + File.separator);
		
		assertEquals(UFileSupport.ExtractFileExt(fileNameWithShortPath), fileExtension);
		
		assertEquals(UFileSupport.ChangeFileExt(fileName, differentFileExtension), differentFileName);
		assertEquals(UFileSupport.ChangeFileExt(fileNameWithShortPath, differentFileExtension), differentFileNameWithShortPath);
	}
	
	@Test public void fileOpen() {
		UFileSupport.getFileOpenInfo(UFileSupport.kFileTypeWorld, "test_output.test", "The Title", UFileSupport.kOtherExtNotOK);
	}
	
	@Test public void fileSave() {
		SaveFileNamesStructure fileInfo = new SaveFileNamesStructure();
		UFileSupport.getFileSaveInfo(UFileSupport.kFileTypeWorld, UFileSupport.kAskForFileName, "test_output.wld", fileInfo);
	}
	
	@Test public void fileSearchTest() {
		String searchResult = UFileSupport.findFileInDirectoryRecursive("Astronomy Test.wld", ".");
		assertFalse(searchResult.equals(""));
		searchResult = UFileSupport.findFileInDirectoryRecursive("Template.java", ".");
		assertFalse(searchResult.equals(""));
	}


}
