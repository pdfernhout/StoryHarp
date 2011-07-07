// unit usconsoleform

from conversion_common import *;
import uspictureform;
import usabout;
import uabout;
import usmediadirform;
import uschangelog;
import usruleeditorform;
import ufilesupport;
import usdomain;
import uregister;
import quickfillcombobox;
import ucommand;
import usspeech;
import usworld;
import delphi_compatability;

// var
TConsoleForm ConsoleForm = new TConsoleForm();


// const
kIconSize = 16;


//$R *.DFM
public int localIntMin(int a, int b) {
    result = 0;
    result = a;
    if (b < a) {
        result = b;
    }
    return result;
}

public int localIntMax(int a, int b) {
    result = 0;
    result = a;
    if (b > a) {
        result = b;
    }
    return result;
}

public void drawRectangle(TCanvas canvas, TRect rectangle) {
    canvas.Brush.Style = delphi_compatability.TFPBrushStyle.bsClear;
    canvas.Pen.Color = delphi_compatability.clGray;
    canvas.Rectangle(rectangle.Left, rectangle.Top, rectangle.Right + 1, rectangle.Bottom + 1);
}

public void drawGraphicCentered(TGraphic graphic, TCanvas canvas, TRect rectangle) {
    canvas.Draw(rectangle.Left + (rectangle.Right - rectangle.Left) / 2 - graphic.Width / 2, rectangle.Top + (rectangle.Bottom - rectangle.Top) / 2 - graphic.Height / 2, graphic);
}

public void carveOffRect(TRect bigRect, TRect littleRect, int width, boolean fromRight) {
    raise "method carveOffRect had assigned to var parameter littleRect not added to return; fixup manually"
    littleRect = bigRect;
    if (fromRight) {
        bigRect.Right = bigRect.Right - width;
        littleRect.Left = bigRect.Right;
    } else {
        bigRect.Left = bigRect.Left + width;
        littleRect.Right = bigRect.Left;
    }
}

// const
kProgramName = "StoryHarp";



// Windows,
class TConsoleForm extends TForm {
    public TStatusBar StatusBar;
    public TPanel PanelConsole;
    public TSplitter SplitterConsole;
    public TRichEdit TranscriptEdit;
    public TListBox VisibleCommandsList;
    public TMainMenu MainMenu;
    public TMenuItem MenuFile;
    public TMenuItem MenuFileOpenSession;
    public TMenuItem MenuFileNewSession;
    public TMenuItem MenuFileSaveSession;
    public TMenuItem MenuFileSaveSessionAs;
    public TMenuItem N3;
    public TMenuItem MenuFileExit;
    public TMenuItem MenuEdit;
    public TMenuItem MenuEditUndo;
    public TMenuItem MenuEditRedo;
    public TMenuItem N5;
    public TMenuItem MenuEditCopy;
    public TMenuItem MenuOptions;
    public TMenuItem MenuOptionsSpeak;
    public TMenuItem MenuOptionsSounds;
    public TMenuItem MenuHelp;
    public TMenuItem MenuHelpContents;
    public TMenuItem MenuHelpPlayingStories;
    public TMenuItem MenuHelpRegisterLine;
    public TMenuItem MenuHelpAbout;
    public TPanel PanelVariables;
    public TPanel VariablesControlPanel;
    public TSpeedButton ContextButton;
    public TSpeedButton MoveButton;
    public TSpeedButton RequirementsButton;
    public TSpeedButton ChangesButton;
    public TSpeedButton CommandButton;
    public TSpeedButton ShowOnlyTrueVariablesButton;
    public TQuickFillComboBox FocusComboBox;
    public TListBox VariablesListBox;
    public TSplitter SplitterVariables;
    public TMenuItem MenuHelpRegister;
    public TMenuItem MenuEditStory;
    public TMenuItem MenuOptionsShowVariables;
    public TMenuItem MenuOptionsVoiceUndo;
    public TMenuItem MenuOptionsVoiceRedo;
    public TMenuItem MenuOptionsUpdateEditorSelections;
    public TMenuItem MenuFileOpenWorld;
    public TMenuItem N1;
    public TMenuItem N4;
    public TMenuItem MenuDevelopment;
    public TMenuItem N7;
    public TMenuItem MenuHelpAgent;
    public TMenuItem AfterRegisterMenuSeparator;
    public TMenuItem MenuOptionsPlayMusic;
    public TMenuItem MediaPath1;
    public TMenuItem N2;
    public TMenuItem MenuSettingsSayOptionsAfterLook;
    public TMediaPlayer MediaPlayer1;
    public TPanel picturesAndSymbolsPanel;
    public TImage contextPicture;
    public TImage contextSymbol;
    public TImage commandPicture;
    public TImage commandSymbol;
    public TImage movePicture;
    public TImage moveSymbol;
    public TImage requirementsPicture;
    public TImage requirementsSymbol;
    public TImage changesPicture;
    public TImage changesSymbol;
    public TImage plusPicture;
    public TImage plusSymbol;
    public TMenuItem MenuSettingsCharacter;
    public TImage replyPicture;
    public TImage replySymbol;
    public TMenuItem N8;
    public TMenuItem MenuEditRepeatLast;
    public TMenuItem MenuSettingsShowTranscript;
    public TMenuItem MenuSettingsPlayerFont;
    public TFontDialog FontDialog;
    public TMenuItem MenuSettingsShowPictures;
    public TMenuItem N6;
    public TMenuItem MenuFileOpenPictureWindow;
    public boolean lastSaveProceeded;
    public boolean debugWorldLoaded;
    public boolean registered;
    public TSSpeechSystem speechSystem;
    public boolean locationCacheValid;
    public TRect contextRect;
    public TRect requirementsRect;
    public TRect commandRect;
    public TRect moveRect;
    public TRect changesRect;
    public boolean isOnlyPlayer;
    public boolean openedEditorThisSession;
    
    public void WMGetMinMaxInfo(Tmessage MSG) {
        super.WMGetMinMaxInfo();
        //FIX unresolved WITH expression: UNRESOLVED.PMinMaxInfo(MSG.lparam).PDF_FIX_POINTER_ACCESS
        UNRESOLVED.ptMinTrackSize.x = 285;
        UNRESOLVED.ptMinTrackSize.y = 250;
        //ptMaxTrackSize.x := 558;
    }
    
    public void playerOnly() {
        this.isOnlyPlayer = true;
        usruleeditorform.RuleEditorForm.Hide();
        this.MenuDevelopment.visible = false;
        this.PanelVariables.Visible = false;
        this.MenuHelpRegister.visible = false;
        this.MenuHelpRegisterLine.visible = false;
        this.StatusBar.Panels[1].Text = "Player-only mode";
    }
    
    public void FormCreate(TObject Sender) {
        delphi_compatability.Application.helpFile = ExtractFilePath(delphi_compatability.Application.exeName) + "StoryHarp.hlp";
        delphi_compatability.Application.OnShowHint = this.DoShowHint;
        delphi_compatability.Application.ShowHint = true;
        this.isOnlyPlayer = false;
        this.speechSystem = usspeech.TSSpeechSystem().create();
        usdomain.domain.sessionCommandList.notifyProcedure = this.commandChangedNotification;
        usdomain.domain.setFormSize(this, usdomain.domain.options.consoleWindowRect);
        this.updateForChangeToDomainOptions();
        if (usdomain.domain.options.consoleBottomHeight > 0) {
            this.VisibleCommandsList.Height = usdomain.domain.options.consoleBottomHeight;
            this.VisibleCommandsList.Top = this.PanelConsole.ClientHeight - this.VisibleCommandsList.Height;
        }
        if ((usdomain.domain.options.consoleRightWidth > 0)) {
            // if PanelVariables.visible then
            //   begin
            this.PanelVariables.Width = usdomain.domain.options.consoleRightWidth;
            this.PanelVariables.Left = this.ClientWidth - this.PanelVariables.Width;
        }
        //   end;
        this.updateViews();
        this.updateForShowingTranscript();
    }
    
    public void setButtonGlyphs(boolean buttonSymbols) {
        if (buttonSymbols) {
            this.ContextButton.Glyph = this.contextSymbol.Picture.Bitmap;
            this.CommandButton.Glyph = this.commandSymbol.Picture.Bitmap;
            this.MoveButton.Glyph = this.moveSymbol.Picture.Bitmap;
            this.RequirementsButton.Glyph = this.requirementsSymbol.Picture.Bitmap;
            this.ChangesButton.Glyph = this.changesSymbol.Picture.Bitmap;
            this.ShowOnlyTrueVariablesButton.Glyph = this.plusSymbol.Picture.Bitmap;
            usruleeditorform.RuleEditorForm.replyPicture.Picture.Bitmap = this.replySymbol.Picture.Bitmap;
        } else {
            this.ContextButton.Glyph = this.contextPicture.Picture.Bitmap;
            this.CommandButton.Glyph = this.commandPicture.Picture.Bitmap;
            this.MoveButton.Glyph = this.movePicture.Picture.Bitmap;
            this.RequirementsButton.Glyph = this.requirementsPicture.Picture.Bitmap;
            this.ChangesButton.Glyph = this.changesPicture.Picture.Bitmap;
            this.ShowOnlyTrueVariablesButton.Glyph = this.plusPicture.Picture.Bitmap;
            usruleeditorform.RuleEditorForm.replyPicture.Picture.Bitmap = this.replyPicture.Picture.Bitmap;
        }
    }
    
    public void FormDestroy(TObject Sender) {
        this.speechSystem.free;
        this.speechSystem = null;
    }
    
    public void FormActivate(TObject Sender) {
        if (delphi_compatability.Application.terminated) {
            return;
        }
        // pdf v1.2 modified from just updating variables
        usdomain.domain.world.updateAvailable();
        this.updateViews();
    }
    
    public void VisibleCommandsListClick(TObject Sender) {
        String theCommand = "";
        
        if (this.VisibleCommandsList.ItemIndex < 0) {
            return;
        }
        theCommand = this.VisibleCommandsList.Items[this.VisibleCommandsList.ItemIndex];
        this.speechSystem.doCommand(theCommand);
        if (usdomain.domain.options.updateEditorAfterCommandDone) {
            usruleeditorform.RuleEditorForm.trackLastCommand();
        }
    }
    
    // --------------------------- HINTS ------------------------------------- 
    public void DoShowHint(ansistring HintStr, boolean CanShow, THintInfo HintInfo) {
        raise "method DoShowHint had assigned to var parameter HintStr not added to return; fixup manually"
        long itemAtPos = 0;
        long col = 0;
        long row = 0;
        TListBox listBox = new TListBox();
        TSRule rule = new TSRule();
        
        if (delphi_compatability.Application.terminated) {
            return CanShow;
        }
        if (HintInfo.HintControl instanceof delphi_compatability.TListBox) {
            listBox = (delphi_compatability.TListBox)HintInfo.HintControl;
            if ((listBox == this.VisibleCommandsList) || (listBox == this.VariablesListBox) || (listBox == usruleeditorform.RuleEditorForm.FirstListBox) || (listBox == usruleeditorform.RuleEditorForm.SecondListBox)) {
                //true = must be on an item
                itemAtPos = listBox.ItemAtPos(HintInfo.CursorPos, true);
                if ((itemAtPos >= 0)) {
                    HintStr = listBox.Items[itemAtPos];
                    HintInfo.CursorRect = listBox.ItemRect(itemAtPos);
                }
            }
        } else if (HintInfo.HintControl == usruleeditorform.RuleEditorForm.RuleGrid) {
            col, row = usruleeditorform.RuleEditorForm.RuleGrid.MouseToCell(HintInfo.CursorPos.X, HintInfo.CursorPos.Y, col, row);
            rule = null;
            if ((row > 0) && (row <= usdomain.domain.world.rules.Count)) {
                rule = usworld.TSRule(usdomain.domain.world.rules[row - 1]);
            }
            if (rule == null) {
                return CanShow;
            }
            HintStr = rule.getTextForField(col);
            HintInfo.CursorRect = usruleeditorform.RuleEditorForm.RuleGrid.CellRect(col, row);
        }
        HintInfo.HintMaxWidth = 250;
        return CanShow;
    }
    
    // --------------------------- TRANSCRIPT ------------------------------------- 
    public void clearTranscript() {
        this.TranscriptEdit.Text = "";
        // is this the right place? cfk
        uspictureform.PictureForm.clearPictures();
    }
    
    public void addLineToTranscript(String text, TColor newColor) {
        this.TranscriptEdit.selStart = len(this.TranscriptEdit.Text);
        //FIX unresolved WITH expression: self.TranscriptEdit.SelAttributes
        this.Color = newColor;
        this.Name = usdomain.domain.options.playerFontName;
        delphi_compatability.SIZE = usdomain.domain.options.playerFontSize;
        this.TranscriptEdit.selText = text + Character(13) + Character(10);
    }
    
    public void scrollTranscriptEndIntoView() {
        this.TranscriptEdit.SelStart = len(this.TranscriptEdit.Text);
        this.TranscriptEdit.SelLength = 0;
        this.TranscriptEdit.Perform(UNRESOLVED.EM_SCROLLCARET, 0, 0);
    }
    
    // --------------------------- SUPPORT --------------------------------------- 
    // will need seperate one for sessions...
    public boolean askForSaveSessionAndProceed() {
        result = false;
        int messageBoxResult = 0;
        
        result = true;
        if (!usdomain.domain.isSessionFileChanged()) {
            return result;
        }
        delphi_compatability.Application.BringToFront();
        //cfk fix - put help context in
        messageBoxResult = MessageDialog("Save changes to session " + ExtractFileName(usdomain.domain.sessionFileName) + "?", mtConfirmation, mbYesNoCancel, 0);
        switch (messageBoxResult) {
            case delphi_compatability.IDCANCEL:
                result = false;
                break;
            case delphi_compatability.IDYES:
                this.MenuFileSaveSessionClick(this);
                result = this.lastSaveProceeded;
                break;
            case delphi_compatability.IDNO:
                result = true;
                break;
            default:
                ShowMessage("Error with save request dialog.");
                result = true;
                break;
        return result;
    }
    
    // returns false if user cancels
    public boolean cleanUpBeforeExit() {
        result = false;
        int response = 0;
        int i = 0;
        
        result = true;
        usdomain.domain.options.consoleWindowRect = Rect(this.Left, this.Top, this.Width, this.Height);
        usdomain.domain.options.consoleBottomHeight = this.VisibleCommandsList.Height;
        usdomain.domain.options.consoleRightWidth = this.PanelVariables.Width;
        usdomain.domain.options.editorWindowRect = Rect(usruleeditorform.RuleEditorForm.Left, usruleeditorform.RuleEditorForm.Top, usruleeditorform.RuleEditorForm.Width, usruleeditorform.RuleEditorForm.Height);
        usdomain.domain.options.editorPanelEditorHeight = usruleeditorform.RuleEditorForm.PanelEditor.Height;
        usdomain.domain.options.editorPanelRequirementsChangesHeight = usruleeditorform.RuleEditorForm.PanelRequirementsChanges.Height;
        if (usruleeditorform.RuleEditorForm.ListPages.ActivePage == usruleeditorform.RuleEditorForm.TabSheetTable) {
            usdomain.domain.options.pageShowing = usdomain.kPageTable;
        } else if (usruleeditorform.RuleEditorForm.ListPages.ActivePage == usruleeditorform.RuleEditorForm.TabSheetMap) {
            usdomain.domain.options.pageShowing = usdomain.kPageMap;
        } else if (usruleeditorform.RuleEditorForm.ListPages.ActivePage == usruleeditorform.RuleEditorForm.TabSheetBrowse) {
            usdomain.domain.options.pageShowing = usdomain.kPageBrowser;
        } else {
            // default
            usdomain.domain.options.pageShowing = usdomain.kPageTable;
        }
        usdomain.domain.options.editorPanelFirstListWidth = usruleeditorform.RuleEditorForm.PanelFirstList.Width;
        usdomain.domain.options.pictureWindowRect = Rect(uspictureform.PictureForm.Left, uspictureform.PictureForm.Top, uspictureform.PictureForm.Width, uspictureform.PictureForm.Height);
        usdomain.domain.options.logFileWindowRect = Rect(uschangelog.ChangeLogForm.Left, uschangelog.ChangeLogForm.Top, uschangelog.ChangeLogForm.Width, uschangelog.ChangeLogForm.Height);
        if (usdomain.domain.useIniFile) {
            if (!this.storeIniFile()) {
                result = false;
                return result;
            }
        }
        UNRESOLVED.Randomize;
        for (i = 0; i <= 30; i++) {
            UNRESOLVED.random;
        }
        if ((!usdomain.domain.playerOnly) && (!usdomain.domain.registered) && (usdomain.domain.accumulatedUnregisteredTime > 4.0 / 24.0) && (UNRESOLVED.random < usdomain.min(usdomain.domain.accumulatedUnregisteredTime, 0.9))) {
            uabout.UnregisteredAboutForm.initializeWithWhetherClosingProgram(true);
            response = uabout.UnregisteredAboutForm.ShowModal();
            this.updateForRegistrationChange();
            if (response == mrCancel) {
                result = false;
                return result;
            }
        }
        delphi_compatability.Application.HelpCommand(UNRESOLVED.HELP_QUIT, 0);
        return result;
    }
    
    public void updateForRegistrationChange() {
        boolean unregisteredEditing = false;
        
        unregisteredEditing = (!usdomain.domain.registered) && (!usdomain.domain.playerOnly);
        this.MenuHelpRegister.visible = unregisteredEditing;
        this.MenuHelpRegisterLine.visible = unregisteredEditing;
        this.StatusBar.visible = unregisteredEditing;
        if (usruleeditorform.RuleEditorForm != null) {
            usruleeditorform.RuleEditorForm.MenuHelpRegister.visible = unregisteredEditing;
            usruleeditorform.RuleEditorForm.AfterRegisterMenuSeparator.visible = unregisteredEditing;
        }
        if (usdomain.domain.registered) {
            this.StatusBar.Panels[1].Text = "Registered to " + usdomain.domain.registrationName;
        } else if (!usdomain.domain.playerOnly) {
            this.StatusBar.Panels[1].Text = "Unregistered";
        } else {
            this.StatusBar.Panels[1].Text = "Player-only mode";
        }
    }
    
    public void reportMode(String status) {
        this.StatusBar.Panels[0].Text = status;
        this.StatusBar.refresh;
    }
    
    public boolean storeIniFile() {
        result = false;
        boolean fileSavedOK = false;
        boolean choseAnotherFileName = false;
        byte buttonPressed = 0;
        TSaveDialog saveDialog = new TSaveDialog();
        
        result = true;
        fileSavedOK = false;
        choseAnotherFileName = false;
        while (!fileSavedOK) {
            try {
                usdomain.domain.storeProfileInformation();
                fileSavedOK = true;
            } catch (Exception e) {
                fileSavedOK = false;
            }
            if (!fileSavedOK) {
                buttonPressed = MessageDialog("Could not save settings to " + chr(13) + chr(13) + "  " + usdomain.domain.iniFileName + chr(13) + chr(13) + "Would you like to save them to another file?", delphi_compatability.TMsgDlgType.mtError, mbYesNoCancel, 0);
                switch (buttonPressed) {
                    case delphi_compatability.IDYES:
                        saveDialog = delphi_compatability.TSaveDialog().Create(delphi_compatability.Application);
                        try {
                            saveDialog.FileName = usdomain.domain.iniFileName;
                            saveDialog.Filter = "Ini files (*.ini)|*.ini|All files (*.*)|*.*";
                            saveDialog.DefaultExt = "ini";
                            saveDialog.Options = saveDialog.Options + {delphi_compatability.TOpenOption.ofPathMustExist, delphi_compatability.TOpenOption.ofOverwritePrompt, delphi_compatability.TOpenOption.ofHideReadOnly, delphi_compatability.TOpenOption.ofNoReadOnlyReturn, };
                            result = saveDialog.Execute();
                            if (result) {
                                usdomain.domain.iniFileName = saveDialog.FileName;
                                choseAnotherFileName = true;
                            }
                        } finally {
                            saveDialog.free;
                        }
                        if (!result) {
                            return result;
                        }
                        break;
                    case delphi_compatability.IDNO:
                        return result;
                        break;
                    case delphi_compatability.IDCANCEL:
                        result = false;
                        return result;
                        break;
            }
        }
        if (fileSavedOK && choseAnotherFileName) {
            ShowMessage("Your settings have been saved in " + chr(13) + chr(13) + "  " + usdomain.domain.iniFileName + chr(13) + chr(13) + "But StoryHarp will load the original settings file again at startup." + chr(13) + "To use this settings file at startup, search in the help system" + chr(13) + "for \"alternate settings file\".");
        }
        return result;
    }
    
    // ---------------------- FILE MENU -------------------------
    public void MenuFileOpenSessionClick(TObject Sender) {
        String fileNameWithPath = "";
        
        if (!this.askForSaveSessionAndProceed()) {
            return;
        }
        fileNameWithPath = ufilesupport.getFileOpenInfo(ufilesupport.kFileTypeSession, usdomain.domain.sessionFileName, "Choose a session file", ufilesupport.kOtherExtNotOK);
        if (fileNameWithPath == "") {
            return;
        }
        this.speechSystem.haltSpeechAndSound();
        // turns it off
        this.speechSystem.speakSound("music");
        this.openSessionOrWorldFile(fileNameWithPath);
        this.updateTitles();
    }
    
    public void MenuFileOpenWorldClick(TObject Sender) {
        String fileNameWithPath = "";
        
        if (!this.askForSaveSessionAndProceed()) {
            return;
        }
        fileNameWithPath = ufilesupport.getFileOpenInfo(ufilesupport.kFileTypeWorld, usdomain.domain.sessionFileName, "Choose a world file", ufilesupport.kOtherExtNotOK);
        if (fileNameWithPath == "") {
            return;
        }
        this.speechSystem.haltSpeechAndSound();
        // turns it off
        this.speechSystem.speakSound("music");
        this.openSessionOrWorldFile(fileNameWithPath);
        this.updateTitles();
    }
    
    public void WMDropFiles(TWMDropFiles Msg) {
         CFileName = [0] * (range(0, UNRESOLVED.MAX_PATH + 1) + 1);
        
        try {
            if (UNRESOLVED.DragQueryFile(Msg.Drop, 0, CFileName, UNRESOLVED.MAX_PATH) > 0) {
                if ((UNRESOLVED.pos(".WLD", uppercase(CFileName)) <= 0) && (UNRESOLVED.pos(".SES", uppercase(CFileName)) <= 0)) {
                    return;
                }
                if (!this.askForSaveSessionAndProceed()) {
                    return;
                }
                this.speechSystem.haltSpeechAndSound();
                // turns it off
                this.speechSystem.speakSound("music");
                this.openSessionOrWorldFile(CFileName);
                this.updateTitles();
                Msg.Result = 0;
            }
        } finally {
            UNRESOLVED.DragFinish(Msg.Drop);
        }
    }
    
    public void openSessionOrWorldFile(String fileNameWithPath) {
        String extension = "";
        
        extension = ExtractFileExt(fileNameWithPath);
        if (uppercase(extension) == "." + uppercase(usdomain.kWorldExtension)) {
            if (!usruleeditorform.RuleEditorForm.askForSaveWorldAndProceed()) {
                return;
            }
            usruleeditorform.RuleEditorForm.openWorldFile(fileNameWithPath);
            usdomain.domain.world.setInitialFocus();
            this.clearTranscript();
            usdomain.domain.world.updateAvailable();
            // pdf v1.2 moved after updateAvailable
            this.updateViews();
            if (usdomain.domain.world.rules.Count > 0) {
                this.speechSystem.doCommand(usworld.TSRule(usdomain.domain.world.rules[0]).command.phrase);
            }
            return;
        }
        if (uppercase(extension) != "." + uppercase(usdomain.kSessionExtension)) {
            ShowMessage("Unsupported file extension for " + fileNameWithPath + "(" + extension + ").");
            return;
        }
        try {
            ufilesupport.startWaitMessage("Opening " + ExtractFileName(fileNameWithPath));
            usdomain.domain.loadSession(fileNameWithPath);
            usdomain.domain.world.updateAvailable();
        } catch (Exception E) {
            ufilesupport.stopWaitMessage();
            ShowMessage(E.message);
            ShowMessage("Could not load file " + fileNameWithPath);
            usdomain.domain.newWorld();
            this.updateViews();
            return;
        }
        ufilesupport.stopWaitMessage();
        this.clearTranscript();
        this.updateViews();
    }
    
    public void MenuFileNewSessionClick(TObject Sender) {
        if (!this.askForSaveSessionAndProceed()) {
            return;
        }
        this.speechSystem.haltSpeechAndSound();
        // turns it off
        this.speechSystem.speakSound("music");
        usdomain.domain.newSession();
        this.clearTranscript();
        // pdf v 1.2 moved above previous two
        usdomain.domain.world.updateAvailable();
        this.updateViews();
        this.updateTitles();
        if (usdomain.domain.world.rules.Count > 0) {
            this.speechSystem.doCommand(usworld.TSRule(usdomain.domain.world.rules[0]).command.phrase);
        }
    }
    
    public void MenuFileSaveSessionClick(TObject Sender) {
        SaveFileNamesStructure fileInfo = new SaveFileNamesStructure();
        
        if (UNRESOLVED.pos(uppercase(usdomain.kUnsavedSessionFileName), uppercase(ExtractFileName(usdomain.domain.sessionFileName))) > 0) {
            this.MenuFileSaveSessionAsClick(this);
            return;
        }
        this.lastSaveProceeded = ufilesupport.getFileSaveInfo(ufilesupport.kFileTypeSession, ufilesupport.kDontAskForFileName, usdomain.domain.sessionFileName, fileInfo);
        if (!this.lastSaveProceeded) {
            return;
        }
        try {
            ufilesupport.startFileSave(fileInfo);
            usdomain.domain.saveSession(fileInfo.tempFile);
            fileInfo.writingWasSuccessful = true;
        } finally {
            this.lastSaveProceeded = ufilesupport.cleanUpAfterFileSave(fileInfo);
        }
        if (this.lastSaveProceeded) {
            usdomain.domain.sessionChangeCount = 0;
            usdomain.domain.sessionCommandList.clear();
            usdomain.domain.options.mostRecentSession = fileInfo.newFile;
            this.updateMenus();
        }
    }
    
    public void MenuFileSaveSessionAsClick(TObject Sender) {
        SaveFileNamesStructure fileInfo = new SaveFileNamesStructure();
        
        this.lastSaveProceeded = ufilesupport.getFileSaveInfo(ufilesupport.kFileTypeSession, ufilesupport.kAskForFileName, usdomain.domain.sessionFileName, fileInfo);
        if (!this.lastSaveProceeded) {
            return;
        }
        try {
            ufilesupport.startFileSave(fileInfo);
            usdomain.domain.saveSession(fileInfo.tempFile);
            fileInfo.writingWasSuccessful = true;
        } finally {
            this.lastSaveProceeded = ufilesupport.cleanUpAfterFileSave(fileInfo);
        }
        if (this.lastSaveProceeded) {
            usdomain.domain.sessionChangeCount = 0;
            usdomain.domain.sessionCommandList.clear();
            usdomain.domain.sessionFileName = fileInfo.newFile;
            usdomain.domain.options.mostRecentSession = fileInfo.newFile;
            this.updateMenus();
        }
        this.updateTitles();
    }
    
    public void MenuFileExitClick(TObject Sender) {
        if (!this.askForSaveSessionAndProceed()) {
            return;
        }
        if (!usruleeditorform.RuleEditorForm.askForSaveWorldAndProceed()) {
            return;
        }
        if (!this.cleanUpBeforeExit()) {
            return;
        }
        delphi_compatability.Application.Terminate();
    }
    
    public void WMQueryEndSession(TWMQueryEndSession message) {
        super.WMQueryEndSession();
        if (this.speechSystem.usingSpeech) {
            //looks like bug in Agent that if shutting down will hang
            MessageDialog("Please close StoryHarp before shutting down Windows.", mtInformation, {mbOK, }, 0);
            message.result = 0;
            return;
        }
        if (!this.askForSaveSessionAndProceed()) {
            // prevents windows from shutting down
            message.result = 0;
        } else if (!usruleeditorform.RuleEditorForm.askForSaveWorldAndProceed()) {
            message.result = 0;
        } else if (!this.cleanUpBeforeExit()) {
            message.result = 0;
        }
    }
    
    public void WMEndSession(TWMEndSession message) {
        super.WMEndSession();
        // speechSystem.systemShuttingDown := true;
    }
    
    public void FormClose(TObject Sender, TCloseAction Action) {
        if (!this.askForSaveSessionAndProceed()) {
            // same as exit, but can't call exit because we have to set the action flag 
            Action = delphi_compatability.TCloseAction.caNone;
            return;
        }
        if (!usruleeditorform.RuleEditorForm.askForSaveWorldAndProceed()) {
            Action = delphi_compatability.TCloseAction.caNone;
            return;
        }
        if (!this.cleanUpBeforeExit()) {
            Action = delphi_compatability.TCloseAction.caNone;
        }
    }
    
    // ---------------------- EDIT MENU -------------------------
    public void MenuEditUndoClick(TObject Sender) {
        usdomain.domain.sessionCommandList.undoLast();
    }
    
    public void MenuEditRedoClick(TObject Sender) {
        usdomain.domain.sessionCommandList.redoLast();
    }
    
    public void MenuEditCopyClick(TObject Sender) {
        String clip = "";
        
        clip = this.TranscriptEdit.selText;
        UNRESOLVED.Clipboard.setTextBuf(clip);
    }
    
    // --------------- OPTIONS MENU ---------------
    public void MenuOptionsSpeakClick(TObject Sender) {
        usdomain.domain.options.playerSpeak = !usdomain.domain.options.playerSpeak;
        this.MenuOptionsSpeak.Checked = usdomain.domain.options.playerSpeak;
        if (!usdomain.domain.options.playerSpeak) {
            this.speechSystem.haltSpeech();
        }
    }
    
    public void MenuOptionsSoundsClick(TObject Sender) {
        usdomain.domain.options.playerPlaySounds = !usdomain.domain.options.playerPlaySounds;
        this.MenuOptionsSounds.Checked = usdomain.domain.options.playerPlaySounds;
        if (!usdomain.domain.options.playerPlaySounds) {
            this.speechSystem.haltSound();
        }
    }
    
    public void MenuOptionsPlayMusicClick(TObject Sender) {
        usdomain.domain.options.playerPlayMusic = !usdomain.domain.options.playerPlayMusic;
        this.MenuOptionsPlayMusic.Checked = usdomain.domain.options.playerPlayMusic;
        if (!usdomain.domain.options.playerPlayMusic) {
            usruleeditorform.RuleEditorForm.MediaPlayer.close;
            usruleeditorform.RuleEditorForm.MediaPlayer.fileName = "";
        }
    }
    
    public void MenuSettingsShowTranscriptClick(TObject Sender) {
        usdomain.domain.options.showTranscript = !usdomain.domain.options.showTranscript;
        this.updateForShowingTranscript();
        this.PanelConsoleResize(this);
    }
    
    public void MenuSettingsShowPicturesClick(TObject Sender) {
        usdomain.domain.options.showPictures = !usdomain.domain.options.showPictures;
        this.MenuSettingsShowPictures.checked = usdomain.domain.options.showPictures;
        if (!usdomain.domain.options.showPictures) {
            uspictureform.PictureForm.Hide();
        } else {
            uspictureform.PictureForm.Show();
        }
    }
    
    public void updateForShowingTranscript() {
        this.MenuSettingsShowTranscript.checked = usdomain.domain.options.showTranscript;
        if (usdomain.domain.options.showTranscript) {
            this.TranscriptEdit.visible = true;
            this.SplitterConsole.Visible = true;
            if (usdomain.domain.options.consoleBottomHeight > 0) {
                this.VisibleCommandsList.Height = usdomain.domain.options.consoleBottomHeight;
                this.VisibleCommandsList.Top = this.PanelConsole.ClientHeight - this.VisibleCommandsList.Height;
            }
        } else {
            this.TranscriptEdit.visible = false;
            this.SplitterConsole.Visible = false;
            this.VisibleCommandsList.Top = this.SplitterConsole.Height;
            this.VisibleCommandsList.Height = this.PanelConsole.ClientHeight - this.SplitterConsole.Height;
        }
    }
    
    // --------------- DEVELOPMENT MENU ---------------
    public void MenuWindowRuleEditorClick(TObject Sender) {
        usruleeditorform.RuleEditorForm.Show();
    }
    
    // --------------- HELP MENU ---------------
    public void MenuHelpContentsClick(TObject Sender) {
        delphi_compatability.Application.HelpCommand(UNRESOLVED.HELP_FINDER, 0);
    }
    
    public void MenuHelpPlayingStoriesClick(TObject Sender) {
        delphi_compatability.Application.HelpJump("Playing_StoryHarp_Audioventures");
    }
    
    public void MenuHelpAgentClick(TObject Sender) {
        delphi_compatability.Application.HelpJump("Installing_and_Using_Microsoft_Agent");
    }
    
    public void MenuHelpRegisterClick(TObject Sender) {
        uregister.RegistrationForm.ShowModal();
        this.updateForRegistrationChange();
    }
    
    public void MenuHelpAboutClick(TObject Sender) {
        if (usdomain.domain.registered) {
            if (usabout.AboutForm == null) {
                return;
            }
            usabout.AboutForm.setUpAsSplashOrAbout(usabout.kAsAbout);
            usabout.AboutForm.ShowModal();
        } else {
            if (uabout.UnregisteredAboutForm == null) {
                return;
            }
            uabout.UnregisteredAboutForm.initializeWithWhetherClosingProgram(false);
            uabout.UnregisteredAboutForm.ShowModal();
            this.updateForRegistrationChange();
        }
    }
    
    // ----------------------------- MENU UPDATING ------------------------------ 
    public void updateForChangeToDomainOptions() {
        this.MenuOptionsSpeak.checked = usdomain.domain.options.playerSpeak;
        this.MenuOptionsSounds.Checked = usdomain.domain.options.playerPlaySounds;
        this.MenuOptionsPlayMusic.Checked = usdomain.domain.options.playerPlayMusic;
        this.MenuSettingsShowTranscript.checked = usdomain.domain.options.showTranscript;
        this.MenuSettingsShowPictures.checked = usdomain.domain.options.showPictures;
        this.MenuSettingsSayOptionsAfterLook.checked = usdomain.domain.options.sayOptionsAfterLook;
        this.MenuOptionsVoiceUndo.Checked = usdomain.domain.options.useVoiceToUndo;
        this.MenuOptionsVoiceRedo.Checked = usdomain.domain.options.useVoiceToRedo;
        this.MenuOptionsShowVariables.Checked = usdomain.domain.options.showVariables;
        this.Font.Name = usdomain.domain.options.playerFontName;
        this.Font.Size = usdomain.domain.options.playerFontSize;
        this.VariablesListBox.ItemHeight = localIntMax(this.Canvas.TextHeight("W") + 2, 16);
        this.TranscriptEdit.font.name = usdomain.domain.options.playerFontName;
        this.TranscriptEdit.font.size = usdomain.domain.options.playerFontSize;
        this.TranscriptEdit.invalidate;
        this.updateForChangeToShowingVariables();
        this.MenuOptionsUpdateEditorSelections.checked = usdomain.domain.options.updateEditorAfterCommandDone;
    }
    
    public void updateForChangeToShowingVariables() {
        if (usdomain.domain.options.showVariables) {
            this.updateVariables();
            this.PanelVariables.Show();
            this.SplitterVariables.Show();
            this.SplitterVariables.Left = this.PanelConsole.Width;
        } else {
            this.PanelVariables.Hide();
            this.SplitterVariables.Hide();
        }
    }
    
    public void updateMenusForUndoRedo() {
        if (usdomain.domain.sessionCommandList.isUndoEnabled()) {
            this.MenuEditUndo.enabled = true;
            this.MenuEditUndo.caption = "&Undo " + usdomain.domain.sessionCommandList.undoDescription();
        } else {
            this.MenuEditUndo.enabled = false;
            this.MenuEditUndo.caption = "Can't undo";
        }
        if (usdomain.domain.sessionCommandList.isRedoEnabled()) {
            this.MenuEditRedo.enabled = true;
            this.MenuEditRedo.caption = "&Redo " + usdomain.domain.sessionCommandList.redoDescription();
        } else {
            this.MenuEditRedo.enabled = false;
            this.MenuEditRedo.caption = "Can't redo";
        }
    }
    
    public void commandChangedNotification(KfCommand command, KfCommandChangeType state) {
        switch (state) {
            case ucommand.KfCommandChangeType.commandDone:
                usdomain.domain.sessionChangeCount += 1;
                break;
            case ucommand.KfCommandChangeType.commandUndone:
                usdomain.domain.sessionChangeCount -= 1;
                break;
        // may also change save availability
        this.updateMenus();
    }
    
    public void updateMenus() {
        this.MenuFileSaveSession.enabled = usdomain.domain.isSessionFileChanged();
        this.MenuFileSaveSessionAs.enabled = true;
        this.updateMenusForUndoRedo();
        //MenuFileCloseSession.enabled := domain.isWorldFileLoaded;
    }
    
    public void updateViews() {
        this.updateMenus();
        this.speechSystem.listenForAvailableCommands();
        if (this.PanelVariables.Visible) {
            this.updateVariables();
        }
    }
    
    //----------------------- variables ----------------------------------
    public void updateVariables() {
        int i = 0;
        TSVariable variable = new TSVariable();
        String oldTop = "";
        int oldTopIndex = 0;
        
        oldTop = "";
        oldTopIndex = this.VariablesListBox.TopIndex;
        if ((oldTopIndex >= 0) && (this.VariablesListBox.Items.Count > oldTopIndex)) {
            oldTop = this.VariablesListBox.Items[oldTopIndex];
        }
        this.VariablesListBox.Clear();
        if (!this.locationCacheValid) {
            this.FocusComboBox.Clear();
        }
        if (usdomain.domain.world.variables.Count > 0) {
            for (i = 0; i <= usdomain.domain.world.variables.Count - 1; i++) {
                variable = usworld.TSVariable(usdomain.domain.world.variables[i]);
                if (!this.ShowOnlyTrueVariablesButton.Down || (variable.getState() == usworld.TSVariableState.kPresent)) {
                    if ((this.ContextButton.Down && (variable.contextUseages > 0)) || (this.MoveButton.Down && (variable.moveUseages > 0)) || (this.RequirementsButton.Down && (variable.requirementsUseages > 0)) || (this.ChangesButton.Down && (variable.changesUseages > 0)) || (this.CommandButton.Down && (variable.commandUseages > 0))) {
                        this.VariablesListBox.Items.AddObject(variable.phrase, variable);
                    }
                }
                if (!this.locationCacheValid) {
                    if ((variable.contextUseages > 0) || (variable.moveUseages > 0)) {
                        this.FocusComboBox.Items.AddObject(variable.phrase, variable);
                    }
                }
            }
        }
        this.VariablesListBox.Invalidate();
        this.VariablesListBox.Refresh();
        this.locationCacheValid = true;
        if (oldTopIndex >= 0) {
            if ((oldTop != "") && (this.VariablesListBox.Items.Count > oldTopIndex) && (this.VariablesListBox.Items[oldTopIndex] == oldTop)) {
                this.VariablesListBox.TopIndex = oldTopIndex;
            }
        }
        if (usdomain.domain.world.focus == null) {
            return;
        }
        this.FocusComboBox.ItemIndex = this.FocusComboBox.Items.IndexOf(usdomain.domain.world.focus.phrase);
    }
    
    public void VariablesListBoxDrawItem(TWinControl Control, int index, TRect Rect, TOwnerDrawState State) {
        boolean selected = false;
        TRect wastedRect = new TRect();
        TRect bigRect = new TRect();
        TRect stateRect = new TRect();
        TRect stringRect = new TRect();
        TSVariable variable = new TSVariable();
        String displayString = "";
        
        if (delphi_compatability.Application.terminated) {
            return;
        }
        if ((this.VariablesListBox.Items.Count <= 0) || (index < 0) || (index > usdomain.domain.world.variables.Count - 1)) {
            return;
        }
        selected = (delphi_compatability.TOwnerDrawStateType.odSelected in State);
        variable = (usworld.TSVariable)this.VariablesListBox.Items.Objects[index];
        if (variable == null) {
            return;
        }
        // set up rectangles 
        bigRect = Rect;
        if (this.VariablesListBox.Items.Count <= (this.VariablesListBox.ClientHeight / kIconSize)) {
            carveOffRect(bigRect, wastedRect, kIconSize, true);
        }
        carveOffRect(bigRect, stateRect, kIconSize, false);
        carveOffRect(bigRect, this.commandRect, kIconSize, true);
        carveOffRect(bigRect, this.changesRect, kIconSize, true);
        carveOffRect(bigRect, this.requirementsRect, kIconSize, true);
        carveOffRect(bigRect, this.moveRect, kIconSize, true);
        carveOffRect(bigRect, this.contextRect, kIconSize, true);
        stringRect = bigRect;
        // fill first box with white, rest with clHighlight if selected 
        this.VariablesListBox.Canvas.Brush.Style = delphi_compatability.TFPBrushStyle.bsSolid;
        this.VariablesListBox.Canvas.Brush.Color = delphi_compatability.clWindow;
        this.VariablesListBox.Canvas.FillRect(Rect);
        if (variable.getState() == usworld.TSVariableState.kPresent) {
            drawGraphicCentered(this.ShowOnlyTrueVariablesButton.Glyph, this.VariablesListBox.Canvas, stateRect);
        }
        if (variable.contextUseages > 0) {
            drawGraphicCentered(this.ContextButton.Glyph, this.VariablesListBox.Canvas, this.contextRect);
        }
        if (variable.requirementsUseages > 0) {
            drawGraphicCentered(this.RequirementsButton.Glyph, this.VariablesListBox.Canvas, this.requirementsRect);
        }
        if (variable.commandUseages > 0) {
            drawGraphicCentered(this.CommandButton.Glyph, this.VariablesListBox.Canvas, this.commandRect);
        }
        if (variable.moveUseages > 0) {
            drawGraphicCentered(this.MoveButton.Glyph, this.VariablesListBox.Canvas, this.moveRect);
        }
        if (variable.changesUseages > 0) {
            drawGraphicCentered(this.ChangesButton.Glyph, this.VariablesListBox.Canvas, this.changesRect);
        }
        this.VariablesListBox.Canvas.Font = this.VariablesListBox.Font;
        if (selected) {
            this.VariablesListBox.Canvas.Brush.Color = UNRESOLVED.clHighlight;
            this.VariablesListBox.Canvas.Font.Color = UNRESOLVED.clHighlightText;
        } else {
            this.VariablesListBox.Canvas.Brush.Color = delphi_compatability.clWindow;
            this.VariablesListBox.Canvas.Font.Color = UNRESOLVED.clBtnText;
        }
        this.VariablesListBox.Canvas.FillRect(stringRect);
        // margin for text 
        stringRect.Left = stringRect.Left + 5;
        displayString = this.VariablesListBox.Items[index] + "..............................................." + ".............................................................................................";
        UNRESOLVED.drawText(this.VariablesListBox.Canvas.Handle, displayString, len(displayString), stringRect, delphi_compatability.DT_LEFT);
    }
    
    public void VariableButtonClick(TObject Sender) {
        this.updateVariables();
    }
    
    public void FormResize(TObject Sender) {
        this.VariablesListBox.Invalidate();
        if (this.PanelVariables.Width > this.ClientWidth - 60 - this.SplitterVariables.Width) {
            this.PanelVariables.Left = 60 + this.SplitterVariables.Width;
            this.PanelVariables.Width = this.ClientWidth - 60 - this.SplitterVariables.Width;
        }
        this.FocusComboBox.Width = this.VariablesControlPanel.ClientWidth - this.FocusComboBox.Left - 4;
    }
    
    public void PanelConsoleResize(TObject Sender) {
        if (this.TranscriptEdit.visible) {
            if (this.VisibleCommandsList.Height > this.PanelConsole.ClientHeight - 60 - this.SplitterConsole.Height) {
                this.VisibleCommandsList.Top = 60 + this.SplitterConsole.Height;
                this.VisibleCommandsList.Height = this.PanelConsole.ClientHeight - 60 - this.SplitterConsole.Height;
            }
        } else {
            this.VisibleCommandsList.Top = this.SplitterConsole.Height;
            this.VisibleCommandsList.Height = this.PanelConsole.ClientHeight - this.SplitterConsole.Height;
        }
    }
    
    public void FocusComboBoxKeyUp(TObject Sender, byte Key, TShiftState Shift) {
        this.FocusComboBoxChange(Sender);
        return Key;
    }
    
    public void FocusComboBoxChange(TObject Sender) {
        TSVariable newFocus = new TSVariable();
        int index = 0;
        int oldSelStart = 0;
        
        index = this.FocusComboBox.Items.IndexOf(this.FocusComboBox.Text);
        if (index < 0) {
            return;
        }
        newFocus = (usworld.TSVariable)this.FocusComboBox.Items.Objects[index];
        if ((newFocus == usdomain.domain.world.focus) && (newFocus.getState() == usworld.TSVariableState.kPresent)) {
            return;
        }
        oldSelStart = this.FocusComboBox.SelStart;
        usdomain.domain.sessionCommandList.moveFocus(newFocus);
        this.FocusComboBox.SelStart = oldSelStart;
    }
    
    public void VariablesListBoxMouseUp(TObject Sender, TMouseButton Button, TShiftState Shift, int X, int Y) {
        TSVariable variable = new TSVariable();
        
        if (this.VariablesListBox.ItemIndex < 0) {
            return;
        }
        if (this.VariablesListBox.ItemIndex > this.VariablesListBox.Items.Count - 1) {
            return;
        }
        variable = usworld.TSVariable(this.VariablesListBox.Items.Objects[this.VariablesListBox.ItemIndex]);
        if (X < 16) {
            usdomain.domain.sessionCommandList.toggleVariable(variable);
            return;
        }
        if ((usruleeditorform.RuleEditorForm == null) || (!usruleeditorform.RuleEditorForm.Visible)) {
            return;
        }
        if ((X > this.contextRect.Left) && (X <= this.contextRect.Right)) {
            if (variable.contextUseages <= 0) {
                return;
            }
            usruleeditorform.RuleEditorForm.setOrganizeByField(usworld.kRuleContext);
        } else if ((X > this.requirementsRect.Left) && (X <= this.requirementsRect.Right)) {
            if (variable.requirementsUseages <= 0) {
                return;
            }
            usruleeditorform.RuleEditorForm.setOrganizeByField(usworld.kRuleRequirements);
        } else if ((X > this.commandRect.Left) && (X <= this.commandRect.Right)) {
            if (variable.commandUseages <= 0) {
                return;
            }
            usruleeditorform.RuleEditorForm.setOrganizeByField(usworld.kRuleCommand);
        } else if ((X > this.moveRect.Left) && (X <= this.moveRect.Right)) {
            if (variable.moveUseages <= 0) {
                return;
            }
            usruleeditorform.RuleEditorForm.setOrganizeByField(usworld.kRuleMove);
        } else if ((X > this.changesRect.Left) && (X <= this.changesRect.Right)) {
            if (variable.changesUseages <= 0) {
                return;
            }
            usruleeditorform.RuleEditorForm.setOrganizeByField(usworld.kRuleChanges);
        } else {
            return;
        }
        usruleeditorform.RuleEditorForm.ListPages.ActivePage = usruleeditorform.RuleEditorForm.TabSheetBrowse;
        usruleeditorform.RuleEditorForm.FirstListBox.ItemIndex = usruleeditorform.RuleEditorForm.FirstListBox.Items.IndexOf(variable.phrase);
        usruleeditorform.RuleEditorForm.loadSecondListBox();
    }
    
    public void MenuOptionsShowVariablesClick(TObject Sender) {
        usdomain.domain.options.showVariables = !usdomain.domain.options.showVariables;
        this.MenuOptionsShowVariables.Checked = usdomain.domain.options.showVariables;
        if (usdomain.domain.options.showVariables) {
            this.ClientWidth = this.ClientWidth + this.PanelVariables.Width + this.SplitterVariables.Width;
        } else {
            this.ClientWidth = this.ClientWidth - this.PanelVariables.Width - this.SplitterVariables.Width;
        }
        this.updateForChangeToShowingVariables();
    }
    
    public void MenuEditStoryClick(TObject Sender) {
        usruleeditorform.RuleEditorForm.Show();
        this.openedEditorThisSession = true;
    }
    
    public void MenuOptionsVoiceUndoClick(TObject Sender) {
        usdomain.domain.options.useVoiceToUndo = !usdomain.domain.options.useVoiceToUndo;
        this.MenuOptionsVoiceUndo.Checked = usdomain.domain.options.useVoiceToUndo;
    }
    
    public void MenuOptionsVoiceRedoClick(TObject Sender) {
        usdomain.domain.options.useVoiceToRedo = !usdomain.domain.options.useVoiceToRedo;
        this.MenuOptionsVoiceRedo.Checked = usdomain.domain.options.useVoiceToRedo;
    }
    
    public void MenuOptionsUpdateEditorSelectionsClick(TObject Sender) {
        usdomain.domain.options.updateEditorAfterCommandDone = !usdomain.domain.options.updateEditorAfterCommandDone;
        this.MenuOptionsUpdateEditorSelections.checked = usdomain.domain.options.updateEditorAfterCommandDone;
    }
    
    //kProgramName = 'TeleTale';
    //kProgramName = 'Audioventure'; 
    // kProgramName = 'PhraseMaze';
    // kProgramName = 'TalkingTales';
    // kProgramName = 'TalkTales';
    // kProgramName = 'Voiceventure';
    // kProgramName = 'VoicePaths';
    // kProgramName = 'VoiceWalk';
    // kProgramName = 'VoiceTracks';
    // kProgramName = 'VoiceTrip';
    // kProgramName = 'VoiceVenture';
    // kProgramName = 'VoiceTrek';
    // kProgramName = 'Voicecapade';
    // kProgramName = 'VoicePaths';
    // kProgramName = 'Storyspeak';
    // kProgramName = 'Storytalk';
    //
    // At the dawn of the third millenium,
    // the laws of space and time keep humans close to Sol.
    // Most of them live in billions of space habitats called 'gardens'.
    // These are their stories...
    // 
    //
    // At the dawn of the third millenium,
    // the laws of space and time keep humans close to Sol.
    // Most of them live in billions of space habitats called 'gardens'.
    // These are their stories...
    // 
    public void updateTitles() {
        this.Caption = "StoryHarp - " + ExtractFileName(usdomain.domain.worldFileName) + " (" + ExtractFileName(usdomain.domain.sessionFileName) + ")";
        usruleeditorform.RuleEditorForm.Caption = "StoryHarp World Editor - " + ExtractFileName(usdomain.domain.worldFileName);
    }
    
    public void PanelVariablesResize(TObject Sender) {
        this.VariablesControlPanel.Height = this.FocusComboBox.Height + this.ContextButton.Height + 4 * 3;
    }
    
    public void VariablesControlPanelResize(TObject Sender) {
        this.FocusComboBox.SetBounds(4, 4, this.VariablesControlPanel.ClientWidth - this.FocusComboBox.Left - 4, this.FocusComboBox.Height);
        this.ContextButton.Top = this.FocusComboBox.Top + this.FocusComboBox.Height + 4;
        this.MoveButton.Top = this.ContextButton.Top;
        this.RequirementsButton.Top = this.ContextButton.Top;
        this.ChangesButton.Top = this.ContextButton.Top;
        this.CommandButton.Top = this.ContextButton.Top;
        this.ShowOnlyTrueVariablesButton.Top = this.ContextButton.Top;
    }
    
    public void MenuSettingsCharacterClick(TObject Sender) {
        String fileNameWithPath = "";
        
        fileNameWithPath = ufilesupport.getFileOpenInfo(ufilesupport.kFileTypeAgentCharacter, usdomain.kDefaultAgentCharacterFileName, "Choose a Microsoft Agent character file", ufilesupport.kOtherExtNotOK);
        if (fileNameWithPath == "") {
            return;
        }
        this.speechSystem.loadAgentCharacter(fileNameWithPath);
    }
    
    public void MediaPath1Click(TObject Sender) {
        usmediadirform.ExtraMediaDirectoryForm.ShowModal();
    }
    
    public void MenuSettingsSayOptionsAfterLookClick(TObject Sender) {
        usdomain.domain.options.sayOptionsAfterLook = !usdomain.domain.options.sayOptionsAfterLook;
        this.MenuSettingsSayOptionsAfterLook.checked = usdomain.domain.options.sayOptionsAfterLook;
    }
    
    public void FormKeyDown(TObject Sender, byte Key, TShiftState Shift) {
        if (Key == delphi_compatability.VK_ESCAPE) {
            this.speechSystem.haltSpeechAndSound();
        }
        return Key;
    }
    
    public void MenuEditRepeatLastClick(TObject Sender) {
        this.speechSystem.haltSpeechAndSound();
        this.speechSystem.sayTextWithMacros(this.speechSystem.lastSaidTextWithMacros);
    }
    
    public void FormShow(TObject Sender) {
        UNRESOLVED.DragAcceptFiles(this.Handle, true);
    }
    
    public void MenuSettingsPlayerFontClick(TObject Sender) {
        this.FontDialog.Font.Name = usdomain.domain.options.playerFontName;
        this.FontDialog.Font.Size = usdomain.domain.options.playerFontSize;
        if (this.FontDialog.Execute()) {
            usdomain.domain.options.playerFontName = this.FontDialog.Font.Name;
            usdomain.domain.options.playerFontSize = this.FontDialog.Font.Size;
            this.Font.Name = usdomain.domain.options.playerFontName;
            this.Font.Size = usdomain.domain.options.playerFontSize;
            this.VariablesListBox.ItemHeight = localIntMax(this.Canvas.TextHeight("W") + 2, 16);
            this.TranscriptEdit.font.name = usdomain.domain.options.playerFontName;
            this.TranscriptEdit.font.size = usdomain.domain.options.playerFontSize;
            // make existing text change font 
            this.TranscriptEdit.selStart = 0;
            this.TranscriptEdit.selLength = len(this.TranscriptEdit.Text);
            this.TranscriptEdit.SelAttributes.name = usdomain.domain.options.playerFontName;
            this.TranscriptEdit.SelAttributes.size = usdomain.domain.options.playerFontSize;
            this.TranscriptEdit.selLength = 0;
            this.PanelVariablesResize(this);
            this.VariablesControlPanelResize(this);
        }
    }
    
    public void MenuFileOpenPictureWindowClick(TObject Sender) {
        uspictureform.PictureForm.Show();
    }
    
}
