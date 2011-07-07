// unit ufilesupport

from conversion_common import *;
import usstream;
import ucursor;
import delphi_compatability;

// var
boolean iniFileChanged = false;
boolean plantFileChanged = false;
String gVersionName = "";


// record
class SaveFileNamesStructure {
    public String tempFile;
    public String newFile;
    public String backupFile;
    public boolean writingWasSuccessful;
}

// const
kFileTypeAny = 0;
kFileTypePlant = 1;
kFileTypeTabbedText = 2;
kFileTypeStrategy = 3;
kFileTypeIni = 4;
kFileTypeExceptionList = 5;
kFileTypeBitmap = 6;
kFileTypeTdo = 7;
kFileTypeDXF = 8;
kFileTypeWorld = 9;
kFileTypeSession = 10;
kFileTypeSound = 11;
kFileTypeMusic = 12;
kFileTypeAgentCharacter = 13;
kWritingWasSuccessful = true;
kWritingFailed = false;
kNoSuggestedFile = "";
kAskForFileName = true;
kDontAskForFileName = false;
kOtherExtOK = true;
kOtherExtNotOK = false;


// unfinished - maybe unneeded
public void startWaitMessage(String waitMessage) {
    pass
}

public void stopWaitMessage() {
    pass
}

// conversion
public String boolToStr(boolean value) {
    result = "";
    if (value) {
        result = "true";
    } else {
        result = "false";
    }
    return result;
}

public boolean strToBool(String booleanString) {
    result = false;
    result = false;
    if (booleanString == "") {
        return result;
    }
    if ((uppercase(booleanString) == "TRUE")) {
        result = true;
    } else if ((uppercase(booleanString) == "FALSE")) {
        result = false;
    }
    return result;
}

public String rectToString(TRect aRect) {
    result = "";
    result = IntToStr(aRect.Left) + " " + IntToStr(aRect.Top) + " " + IntToStr(aRect.Right) + " " + IntToStr(aRect.Bottom);
    return result;
}

public TRect stringToRect(String aString) {
    result = new TRect();
    KfStringStream stream = new KfStringStream();
    
    result = Rect(0, 0, 0, 0);
    stream = usstream.KfStringStream.create;
    try {
        stream.onStringSeparator(aString, " ");
        result.Left = StrToIntDef(stream.nextToken(), 0);
        result.Top = StrToIntDef(stream.nextToken(), 0);
        result.Right = StrToIntDef(stream.nextToken(), 0);
        result.Bottom = StrToIntDef(stream.nextToken(), 0);
    } finally {
        stream.free;
    }
    return result;
}

public String pointToString(TPoint aPoint) {
    result = "";
    result = IntToStr(aPoint.X) + "  " + IntToStr(aPoint.Y);
    return result;
}

public TPoint stringToPoint(String aString) {
    result = new TPoint();
    KfStringStream stream = new KfStringStream();
    
    result = Point(0, 0);
    stream = usstream.KfStringStream.create;
    try {
        stream.onStringSeparator(aString, " ");
        result.X = StrToIntDef(stream.nextToken(), 0);
        result.Y = StrToIntDef(stream.nextToken(), 0);
    } finally {
        stream.free;
    }
    return result;
}

// file i/o 
// ---------------------------------------------------------------------------- file i/o 
public String makeFileNameFrom(String aString) {
    result = "";
    boolean done = false;
    short spacePos = 0;
    
    result = aString;
    done = false;
    while (!done) {
        spacePos = UNRESOLVED.pos(" ", result);
        done = (spacePos <= 0);
        if (!done) {
            UNRESOLVED.delete(result, spacePos, 1);
        }
    }
    return result;
}

public String nameStringForFileType(short fileType) {
    result = "";
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
    return result;
}

public String extensionForFileType(short fileType) {
    result = "";
    result = "";
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
    return result;
}

public String filterStringForFileType(short fileType) {
    result = "";
    String extension = "";
    
    extension = extensionForFileType(fileType);
    if (fileType == kFileTypeAny) {
        result = "All files (*.*)|*.*";
    } else {
        result = nameStringForFileType(fileType) + " files (*." + extension + ")|*." + extension + "|All files (*.*)|*.*";
    }
    return result;
}

public String getFileOpenInfo(short fileType, String suggestedFile, String aTitle, boolean allowOtherExtensions) {
    result = "";
    String fullSuggestedFileName = "";
    TOpenDialog openDialog = new TOpenDialog();
    String nameString = "";
    
    result = "";
    openDialog = delphi_compatability.TOpenDialog().Create(delphi_compatability.Application);
    try {
        if (suggestedFile == "") {
            openDialog.FileName = "*." + extensionForFileType(fileType);
        } else {
            fullSuggestedFileName = ExpandFileName(suggestedFile);
            // if directory does not exist, will leave as it was 
            openDialog.InitialDir = ExtractFilePath(fullSuggestedFileName);
            if (FileExists(fullSuggestedFileName)) {
                openDialog.FileName = ExtractFileName(fullSuggestedFileName);
            }
        }
        nameString = nameStringForFileType(fileType);
        if (len(aTitle) > 0) {
            openDialog.Title = aTitle;
        } else if (nameString[1] in {"A", "E", "I", "O", "U", }) {
            openDialog.Title = "Choose an " + nameString + " file";
        } else {
            openDialog.Title = "Choose a " + nameString + " file";
        }
        openDialog.Filter = filterStringForFileType(fileType);
        openDialog.DefaultExt = extensionForFileType(fileType);
        openDialog.Options = openDialog.Options + {delphi_compatability.TOpenOption.ofPathMustExist, delphi_compatability.TOpenOption.ofFileMustExist, delphi_compatability.TOpenOption.ofHideReadOnly, };
        if (openDialog.Execute()) {
            if ((delphi_compatability.TOpenOption.ofExtensionDifferent in openDialog.Options) && (!allowOtherExtensions)) {
                ShowMessage("The file (" + openDialog.FileName + ") does not have the correct extension (" + openDialog.DefaultExt + ").");
                return result;
            } else {
                result = openDialog.FileName;
            }
        }
    } finally {
        openDialog.free;
    }
    return result;
}

public boolean fileNameIsOkayForSaving(String suggestedFile) {
    result = false;
    String fullSuggestedFileName = "";
    
    result = false;
    if (len(suggestedFile) == 0) {
        return result;
    }
    fullSuggestedFileName = ExpandFileName(suggestedFile);
    if (!UNRESOLVED.directoryExists(ExtractFilePath(fullSuggestedFileName))) {
        // check if directory exists 
        ShowMessage("The directory " + ExtractFilePath(fullSuggestedFileName) + " does not exist.");
        return result;
    }
    if (FileExists(fullSuggestedFileName) && UNRESOLVED.FileGetAttr(fullSuggestedFileName) && UNRESOLVED.faReadOnly) {
        // if file exists and is writable, it's ok because Save (not Save As) should not ask to rewrite 
        // if file exists but is read-only, quit  
        ShowMessage("The file " + fullSuggestedFileName + " exists and is read-only.");
        return result;
    }
    result = true;
    return result;
}

public boolean getFileSaveInfo(short fileType, boolean askForFileName, String suggestedFile, SaveFileNamesStructure fileInfo) {
    result = false;
    TSaveDialog saveDialog = new TSaveDialog();
    String tryBackupName = "";
    String tryTempName = "";
    String fullSuggestedFileName = "";
    String prompt = "";
    String extension = "";
    short index = 0;
    long tempFileHandle = 0;
    
    result = false;
    saveDialog = delphi_compatability.TSaveDialog().Create(delphi_compatability.Application);
    //  saveDialog := nil;
    try {
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
            if (len(suggestedFile) > 0) {
                // if this is a Save As, or if this is a Save and the file in suggestedFile is invalid,
                //    ask user for a file name 
                fullSuggestedFileName = ExpandFileName(suggestedFile);
                // if directory does not exist, will leave as it was 
                saveDialog.InitialDir = ExtractFilePath(fullSuggestedFileName);
                if (UNRESOLVED.directoryExists(ExtractFilePath(fullSuggestedFileName))) {
                    // don't check if file exists (because saving); check if dir exists 
                    saveDialog.FileName = ExtractFileName(fullSuggestedFileName);
                }
            }
            saveDialog.Filter = filterStringForFileType(fileType);
            saveDialog.DefaultExt = extensionForFileType(fileType);
            saveDialog.Options = saveDialog.Options + {delphi_compatability.TOpenOption.ofPathMustExist, delphi_compatability.TOpenOption.ofOverwritePrompt, delphi_compatability.TOpenOption.ofHideReadOnly, delphi_compatability.TOpenOption.ofNoReadOnlyReturn, };
            if (!saveDialog.Execute()) {
                saveDialog.free;
                saveDialog = null;
                return result;
            }
            fileInfo.newFile = saveDialog.FileName;
        }
        try {
            // set backup file name, check if read-only 
            // changed backup file extension to put tilde first because it is better to have all backup files sort together 
            // includes dot 
            extension = ExtractFileExt(fileInfo.newFile);
            extension = ".~" + UNRESOLVED.copy(extension, 2, 2);
        } catch (Exception e) {
            extension = ".bak";
        }
        tryBackupName = ChangeFileExt(fileInfo.newFile, extension);
        if (FileExists(tryBackupName)) {
            if ((UNRESOLVED.fileGetAttr(tryBackupName) && UNRESOLVED.faReadOnly)) {
                prompt = "The backup file " + tryBackupName + " is read-only. Continue?";
                if (MessageDialog(prompt, mtConfirmation, {mbYes, mbNo, }, 0) != mrYes) {
                    return result;
                }
            }
        } else {
            fileInfo.backupFile = tryBackupName;
        }
        for (index = 100; index <= 999; index++) {
            // set temp file name 
            tryTempName = ChangeFileExt(fileInfo.newFile, "." + IntToStr(index));
            if (!FileExists(tryTempName)) {
                fileInfo.tempFile = tryTempName;
                break;
            }
        }
        if (fileInfo.tempFile == "") {
            // if can't find unused temp file, quit 
            ShowMessage("Could not create temporary file " + tryTempName + ".");
            return result;
        }
        // test whether temp file can be created 
        tempFileHandle = UNRESOLVED.fileCreate(fileInfo.tempFile);
        if (tempFileHandle > 0) {
            UNRESOLVED.fileClose(tempFileHandle);
            if (!DeleteFile(fileInfo.tempFile)) {
                ShowMessage("Problem with temporary file " + fileInfo.tempFile + ".");
                return result;
            }
        } else {
            ShowMessage("Could not write to temporary file " + fileInfo.tempFile + ".");
            return result;
        }
        result = true;
    } finally {
        saveDialog.free;
    }
    return result;
}

public void startFileSave(SaveFileNamesStructure fileInfo) {
    ucursor.cursor_startWait();
    startWaitMessage("Saving " + ExtractFileName(fileInfo.newFile) + "...");
}

public boolean cleanUpAfterFileSave(SaveFileNamesStructure fileInfo) {
    result = false;
    boolean useBackup = false;
    boolean renamingFailed = false;
    boolean deletingFailed = false;
    String prompt = "";
    
    result = false;
    ucursor.cursor_stopWait();
    stopWaitMessage();
    useBackup = true;
    if (!fileInfo.writingWasSuccessful) {
        //if couldn't write, then remove temp file and exit without warning
        DeleteFile(fileInfo.tempFile);
        return result;
    }
    if (fileInfo.backupFile != "") {
        if (FileExists(fileInfo.backupFile)) {
            //remove backup file if exists from prior backup
            //try to delete backup file
            deletingFailed = !DeleteFile(fileInfo.backupFile);
            if (deletingFailed) {
                //couldn't delete backup file
                prompt = "Could not write backup file " + fileInfo.backupFile + ". Continue?";
                if (MessageDialog(prompt, mtConfirmation, {mbYes, mbNo, }, 0) != mrYes) {
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
                if (MessageDialog(prompt, mtConfirmation, {mbYes, mbNo, }, 0) != mrYes) {
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


