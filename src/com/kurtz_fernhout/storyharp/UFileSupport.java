package com.kurtz_fernhout.storyharp;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class UFileSupport {
	// var
	public static boolean iniFileChanged = false;
	public static boolean plantFileChanged = false;
	// const
	public final static int kFileTypeAny = 0;
	public final static int kFileTypePlant = 1;
	public final static int kFileTypeTabbedText = 2;
	public final static int kFileTypeStrategy = 3;
	public final static int kFileTypeIni = 4;
	public final static int kFileTypeExceptionList = 5;
	public final static int kFileTypeBitmap = 6;
	public final static int kFileTypeTdo = 7;
	public final static int kFileTypeDXF = 8;
	public final static int kFileTypeWorld = 9;
	public final static int kFileTypeSession = 10;
	public final static int kFileTypeSound = 11;
	public final static int kFileTypeMusic = 12;
	public final static int kFileTypeAgentCharacter = 13;
	
	public final static boolean kWritingWasSuccessful = true;
	public final static boolean kWritingFailed = false;
	
	public final static String kNoSuggestedFile = "";
	
	public final static boolean kAskForFileName = true;
	public final static boolean kDontAskForFileName = false;
	
	public final static boolean kOtherExtOK = true;
	public final static boolean kOtherExtNotOK = false;


	public static String getExeName() {
		return "StoryHarp";
	}
	
	public static String getExePathAndSeparator() {
		return UFileSupport.GetCurrentDir() + File.separator;
	}
	
	public static String getResourcesPathAndSeparator() {
		return UFileSupport.GetCurrentDir() + File.separator + "resources" + File.separator;
	}
	
	public static int DelphiIntForColor(Color color) {
		return (color.getBlue() << 16) | (color.getGreen() << 8) | color.getRed();
	}
	
	// unfinished - maybe unneeded
	public static void startWaitMessage(String waitMessage) {
	}

	public static void stopWaitMessage() {
	}

	// conversion
	public static String boolToStr(boolean value) {
	    String result = "";
	    if (value) {
	        result = "true";
	    } else {
	        result = "false";
	    }
	    return result;
	}

	public static boolean strToBool(String booleanString) {
	    boolean result = false;
	    if (booleanString.equals("")) {
	        return result;
	    }
	    if (booleanString.toUpperCase().equals("TRUE")) {
	        result = true;
	    } else if (booleanString.toUpperCase().equals("FALSE")) {
	        result = false;
	    }
	    return result;
	}

	public static String rectToString(Rectangle aRect) {
		// PDF PORT issue: Delphi had bottom and right; not sure whether or where to change
	    String result = String.valueOf(aRect.x) + " " + String.valueOf(aRect.y) + " " + String.valueOf(aRect.width) + " " + String.valueOf(aRect.height);
	    return result;
	}

	public static Rectangle stringToRect(String aString) {
		// split on any white space
	    String[] values = aString.split("\\s+");
	    if (values.length != 4) {
	    	System.out.println("Expected four values for a rectangle: " + aString);
	    	return new Rectangle(0, 0, 0, 0);
	    }
	    int x = Integer.parseInt(values[0]);
	    int y = Integer.parseInt(values[1]);
	    int width = Integer.parseInt(values[2]);
	    int height = Integer.parseInt(values[3]);
	    return new Rectangle(x, y, width, height);
	}

	public static String pointToString(Point aPoint) {
	    return String.valueOf(aPoint.x) + "  " + String.valueOf(aPoint.y);
	}

	public static Point stringToPoint(String aString) {
		// split on any white space
	    String[] values = aString.split("\\s+");
	    if (values.length != 2) {
	    	System.out.println("Expected two values for a point: " + aString + " got: " + values.length);
	    	return new Point(0, 0);
	    }
	    int x = Integer.parseInt(values[0]);
	    int y = Integer.parseInt(values[1]);
	    return new Point(x, y);
	}

	// file i/o 
	// ---------------------------------------------------------------------------- file i/o 
	
	// eliminate the spaces from the string
	public static String makeFileNameFrom(String aString) {
	    String result = "";
	    
	    for (char c: aString.toCharArray()) {
	    	if (c != ' ') {
	    		result += Character.toString(c);
	    	}
	    }
	    return result;
	}

	public static String nameStringForFileType(int fileType) {
	    String result = "";
	    switch (fileType) {
	        case kFileTypeAny:
	            result = "";
	            break;
	        case kFileTypePlant:
	            result = "Plant";
	            break;
	        case kFileTypeTabbedText:
	            result = "Tabbed text";
	            break;
	        case kFileTypeStrategy:
	            result = "Strategy";
	            break;
	        case kFileTypeIni:
	            result = "Ini";
	            break;
	        case kFileTypeExceptionList:
	            result = "Exception list";
	            break;
	        case kFileTypeBitmap:
	            result = "Bitmap";
	            break;
	        case kFileTypeTdo:
	            result = "3D object";
	            break;
	        case kFileTypeDXF:
	            result = "DXF";
	            break;
	        case kFileTypeWorld:
	            result = "World";
	            break;
	        case kFileTypeSession:
	            result = "Session";
	            break;
	        case kFileTypeSound:
	            result = "Sound";
	            break;
	        case kFileTypeMusic:
	            result = "Music";
	            break;
	        case kFileTypeAgentCharacter:
	            result = "Character";
	            break;
	    }
	    return result;
	}

	public static String extensionForFileType(int fileType) {
	    String result = "";
	    switch (fileType) {
	        case kFileTypeAny:
	            result = "*";
	            break;
	        case kFileTypePlant:
	            result = "pla";
	            break;
	        case kFileTypeTabbedText:
	            result = "tab";
	            break;
	        case kFileTypeStrategy:
	            result = "str";
	            break;
	        case kFileTypeIni:
	            result = "ini";
	            break;
	        case kFileTypeExceptionList:
	            result = "nex";
	            break;
	        case kFileTypeBitmap:
	            result = "bmp";
	            break;
	        case kFileTypeTdo:
	            result = "tdo";
	            break;
	        case kFileTypeDXF:
	            result = "dxf";
	            break;
	        case kFileTypeWorld:
	            result = "wld";
	            break;
	        case kFileTypeSession:
	            result = "ses";
	            break;
	        case kFileTypeSound:
	            result = "wav";
	            break;
	        case kFileTypeMusic:
	            result = "mid";
	            break;
	        case kFileTypeAgentCharacter:
	            result = "acs";
	            break;
	    }
	    return result;
	}

	public static String descriptionStringForFileType(int fileType) {
	    String result = "";
	    String extension = "";
	    
	    extension = extensionForFileType(fileType);
	    if (fileType == kFileTypeAny) {
	        result = "All files (*.*)";
	    } else {
	        result = nameStringForFileType(fileType) + " files (*." + extension + ")";
	    }
	    return result;
	}

	public static String getFileOpenInfo(int fileType, String suggestedFile, String aTitle, boolean allowOtherExtensions) {
	    String result = "";
	    String fullSuggestedFileName = "";
	    JFileChooser openDialog = new JFileChooser();
	    String nameString = "";
	    
	    // PDF PORT: assuming only want files? Could be made an option
	    openDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (suggestedFile.equals("")) {
            //openDialog.setSelectedFile(new File("Untitled." + extensionForFileType(fileType)));
        } else {
            fullSuggestedFileName = ExpandFileName(suggestedFile);
            // PDF PORT: The next comment is no longer true; should this be fixed?
            // if directory does not exist, will leave as it was 
            openDialog.setCurrentDirectory(new File(ExtractFilePath(fullSuggestedFileName)));
            if (FileExists(fullSuggestedFileName)) {
                openDialog.setSelectedFile(new File(fullSuggestedFileName));
            }
        }
        
        nameString = nameStringForFileType(fileType);
        if (aTitle.length() > 0) {
            openDialog.setDialogTitle(aTitle);
        } else if ("AEIOU".contains(nameString.subSequence(0, 1))) {
        	openDialog.setDialogTitle("Choose an " + nameString + " file");
        } else {
        	openDialog.setDialogTitle("Choose a " + nameString + " file");
        }
        
        String defaultExtension = extensionForFileType(fileType);
        //openDialog.DefaultExt = extensionForFileType(fileType);
        openDialog.setFileFilter(new ExtensionsFileFilter(extensionForFileType(fileType), descriptionStringForFileType(fileType)));
       
        // openDialog.Options = openDialog.Options + {
        // delphi_compatability.TOpenOption.ofPathMustExist, 
        // delphi_compatability.TOpenOption.ofFileMustExist, 
        // PDF PORT: This would have been a bug -- you could not read files from a CDROM or other read only source
        // delphi_compatability.TOpenOption.ofHideReadOnly, };
        	
        openDialog.setFileHidingEnabled(true);
        
        JFrame parent = null;
        int dialogResult = openDialog.showOpenDialog(parent);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
        	String chosenFileName = openDialog.getSelectedFile().getAbsolutePath();
        	String fileName = ExtractFileName(chosenFileName);
            if ((!allowOtherExtensions) && (!ExtractFileExt(fileName).equals("." + defaultExtension))) {
                ShowMessage("The file (" + fileName + ") does not have the correct extension (" + defaultExtension.substring(1) + ").");
                return result;
            } else {
                result = chosenFileName;
            }
        }

	    return result;
	}

	public static boolean fileNameIsOkayForSaving(String suggestedFileName) {
	    if (suggestedFileName.length() == 0) {
	        return false;
	    }
	    File file = new File(suggestedFileName);
	    File directory = new File(file.getParent());
	    if (!directory.exists()) {
	        // check if directory exists 
	        ShowMessage("The directory " + directory.getAbsolutePath() + " does not exist.");
	        return false;
	    }
	    if (file.exists() && !file.canWrite()) {
	        // if file exists and is writable, it's ok because Save (not Save As) should not ask to rewrite 
	        // if file exists but is read-only, quit  
	        ShowMessage("The file " + file.getAbsolutePath() + " exists and is read-only.");
	        return false;
	    }
	    return true;
	}

	public static void ShowMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public static boolean Confirm(String prompt) {
		int dialogResult = JOptionPane.showConfirmDialog(null, prompt, "Confirm?", JOptionPane.YES_NO_OPTION);
		return dialogResult == JOptionPane.YES_OPTION;
	}
	
	public static boolean getFileSaveInfo(int fileType, boolean askForFileName, String suggestedFile, SaveFileNamesStructure fileInfo) {
	    JFileChooser saveDialog = new JFileChooser();
	    String tryBackupName = "";
	    String tryTempName = "";
	    String fullSuggestedFileName = "";
	    String prompt = "";
	    String extension = "";
	    
        // default info 
        fileInfo.tempFile = "";
        fileInfo.newFile = "";
        fileInfo.backupFile = "";
        fileInfo.writingWasSuccessful = false;
        
        if (!askForFileName) {
            // if this is a Save, try to set the file name from the suggestedFile given; if file name
            //    is invalid, set flag to move into Save As instead 
            askForFileName = !fileNameIsOkayForSaving(suggestedFile);
            if (!askForFileName) {
                fileInfo.newFile = ExpandFileName(suggestedFile);
            }
        }
        
        if (askForFileName) {
            if (suggestedFile.length() > 0) {
                // if this is a Save As, or if this is a Save and the file in suggestedFile is invalid,
                //    ask user for a file name 
                fullSuggestedFileName = ExpandFileName(suggestedFile);
                // if directory does not exist, will leave as it was 
                saveDialog.setCurrentDirectory(new File(ExtractFilePath(fullSuggestedFileName)));
                if (DirectoryExists(ExtractFilePath(fullSuggestedFileName))) {
                    // don't check if file exists (because saving); check if dir exists 
                    saveDialog.setSelectedFile(new File(fullSuggestedFileName));
                }
            }
            
            saveDialog.setFileFilter(new ExtensionsFileFilter(extensionForFileType(fileType), descriptionStringForFileType(fileType)));
            // String defaultExtension = extensionForFileType(fileType);
            //saveDialog.DefaultExt = extensionForFileType(fileType);
            
            // saveDialog.Options = saveDialog.Options + {
            // delphi_compatability.TOpenOption.ofPathMustExist, 
            // delphi_compatability.TOpenOption.ofOverwritePrompt, 
            // delphi_compatability.TOpenOption.ofHideReadOnly, 
            // delphi_compatability.TOpenOption.ofNoReadOnlyReturn, };
            
            saveDialog.setFileHidingEnabled(true);
            
            JFrame parent = null;
            int dialogResult = saveDialog.showSaveDialog(parent);
            if (dialogResult != JFileChooser.APPROVE_OPTION) {
            	return false;
            }
            
            String newFileName = saveDialog.getSelectedFile().getAbsolutePath();
            if (new File(newFileName).exists()) {
            	boolean confirmResult = Confirm("The file: " + ExtractFileName(newFileName) + " already exists.\nDo you want to overwrite it?");
            	if (!confirmResult) {
            		return false;
            	}
            }
            fileInfo.newFile = newFileName;
        }
        
        // set backup file name, check if read-only 
        // changed backup file extension to put tilde first because it is better to have all backup files sort together 
        // includes dot 
        extension = ExtractFileExt(fileInfo.newFile);
        extension = ".~" + extension.substring(1);
        
        tryBackupName = ChangeFileExt(fileInfo.newFile, extension);
        if (FileExists(tryBackupName)) {
            if (!(new File(tryBackupName)).canWrite()) {
                prompt = "The backup file " + tryBackupName + " is read-only. Continue?";
                if (!Confirm(prompt)) {
                    return false;
                }
            }
        } else {
            fileInfo.backupFile = tryBackupName;
        }
        for (int index = 100; index <= 999; index++) {
            // set temp file name 
            tryTempName = ChangeFileExt(fileInfo.newFile, "." + String.valueOf(index));
            if (!FileExists(tryTempName)) {
                fileInfo.tempFile = tryTempName;
                break;
            }
        }
        if (fileInfo.tempFile.equals("")) {
            // if can't find unused temp file, quit 
            ShowMessage("Could not create temporary file " + tryTempName + ".");
            return false;
        }
        // test whether temp file can be created 
        FileOutputStream tempFileHandle = null;
		try {
			tempFileHandle = new FileOutputStream(fileInfo.tempFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}
		boolean fileIsOK = false;
		if (tempFileHandle != null) {
			try {
				tempFileHandle.write(0);
				fileIsOK = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        if (tempFileHandle != null) {
            try {
				tempFileHandle.close();
			} catch (IOException e) {
				e.printStackTrace();
				fileIsOK = false;
			}
            if (!DeleteFile(fileInfo.tempFile) || !fileIsOK) {
                ShowMessage("Problem with temporary file " + fileInfo.tempFile + ".");
                return false;
            }
        } else {
            ShowMessage("Could not write to temporary file " + fileInfo.tempFile + ".");
            return false;
        }
	    return true;
	}

	public static void startFileSave(SaveFileNamesStructure fileInfo) {
	    UCursor.cursor_startWait();
	    startWaitMessage("Saving " + ExtractFileName(fileInfo.newFile) + "...");
	}

	public static boolean cleanUpAfterFileSave(SaveFileNamesStructure fileInfo) {
	    boolean result = false;
	    boolean useBackup = false;
	    boolean renamingFailed = false;
	    boolean deletingFailed = false;
	    String prompt = "";
	    
	    UCursor.cursor_stopWait();
	    stopWaitMessage();
	    useBackup = true;
	    if (!fileInfo.writingWasSuccessful) {
	        //if couldn't write, then remove temp file and exit without warning
	        DeleteFile(fileInfo.tempFile);
	        return result;
	    }
	    if (!fileInfo.backupFile.equals("")) {
	        if (FileExists(fileInfo.backupFile)) {
	            //remove backup file if exists from prior backup
	            //try to delete backup file
	            deletingFailed = !DeleteFile(fileInfo.backupFile);
	            if (deletingFailed) {
	                //couldn't delete backup file
	                prompt = "Could not write backup file " + fileInfo.backupFile + ". Continue?";
	                if (!Confirm(prompt)) {
	                    //user doesn't want to proceed - so cleanup temp file
	                    DeleteFile(fileInfo.tempFile);
	                    return result;
	                } else {
	                    useBackup = false;
	                }
	            }
	        }
	    } else {
	        useBackup = false;
	    }
	    if (FileExists(fileInfo.newFile)) {
	        if (useBackup) {
	            //if original file exists make backup if requested...
	            //rename old copy of new file to make backup
	            renamingFailed = !RenameFile(fileInfo.newFile, fileInfo.backupFile);
	            if (renamingFailed) {
	                prompt = "Could not rename old file to backup file " + fileInfo.backupFile + ". Continue?";
	                if (!Confirm(prompt)) {
	                    //user doesn't want to proceed - so cleanup temp file
	                    DeleteFile(fileInfo.tempFile);
	                    return result;
	                } else {
	                    useBackup = false;
	                }
	            }
	        }
	        if (!useBackup) {
	            //could not create backup file - so just delete old file instead of renaming
	            deletingFailed = !DeleteFile(fileInfo.newFile);
	            if (deletingFailed) {
	                ShowMessage("Could not write file " + fileInfo.newFile);
	                return result;
	            }
	        }
	    }
	    //rename temp file to newFile name
	    renamingFailed = !RenameFile(fileInfo.tempFile, fileInfo.newFile);
	    if (renamingFailed) {
	        //clean up by removing temp file
	    	ShowMessage("Could not write file " + fileInfo.newFile + " from " + fileInfo.tempFile);
	        DeleteFile(fileInfo.tempFile);
	        return result;
	    }
	    result = true;
	    return result;
	}

	// See: http://www.delphibasics.co.uk/RTL.asp?Name=ExtractFileExt
	
	public static boolean FileExists(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	public static boolean DirectoryExists(String directoryName) {
		// don't check if it is a directory
		File file = new File(directoryName);
		return file.exists();
	}

	public static boolean RenameFile(String existingFileName, String newFileName) {
		File existingFile = new File(existingFileName);
		File newFile = new File(newFileName);
		return existingFile.renameTo(newFile);
	}

	public static boolean DeleteFile(String fileName) {
		File file = new File(fileName);
		return file.delete();
	}

	public static String ExtractFileName(String fullFileNameWithPath) {
		File file = new File(fullFileNameWithPath);
		return file.getName();
	}
	
	public static String ExpandFileName(String fileName) {
		File file = new File(fileName);
		return file.getAbsolutePath();
	}

	public static String ExtractFileDir(String fullFileNameWithPath) {
		File file = new File(new File(fullFileNameWithPath).getAbsolutePath());
		return file.getParent();
	}
	
	public static String ExtractFilePath(String fullFileNameWithPath) {
		File file = new File(new File(fullFileNameWithPath).getAbsolutePath());
		return file.getParent() + File.separator;
	}

	public static String ExtractFileExt(String fileName) {
		int dotPosition = fileName.indexOf(".");
		if (dotPosition == -1) return "";
		return fileName.substring(dotPosition);
	}
	
	public static String ChangeFileExt(String fileName, String newExtension) {
		int dotPosition = fileName.indexOf(".");
		if (dotPosition == -1) return fileName + newExtension;
		return fileName.substring(0, dotPosition) + newExtension;
	}
	
	public static String GetCurrentDir() {
		try {
			return new File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
			return new File(".").getAbsolutePath();
		}
	}

	public static String getWindowsMediaDirectory() {
		String windowsDirectory = System.getenv("windir");
		if (windowsDirectory == null || windowsDirectory.equals("")) {
			windowsDirectory = File.separator;
		}
	    // PDF PORT FIX MAYBE: GetWindowsDirectory();
	    if ((!windowsDirectory.equals("")) && (windowsDirectory.charAt(windowsDirectory.length() - 1) != File.separatorChar)) {
	        windowsDirectory = windowsDirectory + File.separator;
	    }
	    return windowsDirectory + "media" + File.separator;
	}
	
	public static String findFileInDirectoryRecursive(String fileName, String searchDir) {
		File file = new File(searchDir, fileName);
		if (file.exists()) return file.getAbsolutePath();
		File directory = new File(searchDir);
		File[] list = directory.listFiles();
		if (list == null) return "";
		for (int i = 0; i < list.length; i++) {
			File otherFile = list[i];
			if (otherFile.isDirectory()) {
				if (otherFile.getName().startsWith(".")) continue;
				String otherResult = findFileInDirectoryRecursive(fileName, otherFile.getAbsolutePath());
				if (!otherResult.equals("")) return otherResult;
			}
		}
		return "";
	}

	public static String findFileRecursivelyInMediaDirectories(String fileName, String extraMediaDirectory) {
	    String result = "";

	    // search in world file directory -- new sounds probably are where world is
	    String worldFileDirectory = ExtractFilePath(TSDomain.domain.worldFileName);
	    if (worldFileDirectory.equals("")) {
	        worldFileDirectory = GetCurrentDir() + File.separator;
	    }
	    if (!worldFileDirectory.equals("")) {
	        result = findFileInDirectoryRecursive(fileName, worldFileDirectory);
	    }
	    if (!result.equals("")) {
	        return result;
	    }
	    if (!extraMediaDirectory.equals("")) {
	        // search in auxillary directory -- there might be an auxially CD-ROM
	        result = findFileInDirectoryRecursive(fileName, extraMediaDirectory);
	    }
	    if (!result.equals("")) {
	        return result;
	    }
	    // search in exe directory -- sounds might be shipped with the program
	    String resourcesDirectory = UFileSupport.getResourcesPathAndSeparator();
	    if (!resourcesDirectory.equals("")) {
	        result = findFileInDirectoryRecursive(fileName, resourcesDirectory);
	    }
	    if (!result.equals("")) {
	        return result;
	    }
	    // search in windows media directory -- default windows supplied items
	    String windowsMediaDirectory = getWindowsMediaDirectory();
	    Application.ConsoleForm.reportMode("Searching");
	    if (!windowsMediaDirectory.equals("")) {
	        result = findFileInDirectoryRecursive(fileName, windowsMediaDirectory);
	    }
	    Application.ConsoleForm.reportMode("Running");
	    return result;
	}

}
