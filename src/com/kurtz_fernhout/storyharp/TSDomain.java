package com.kurtz_fernhout.storyharp;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

public class TSDomain {
	public final static String kUnsavedWorldFileName = "untitled";
	public final static String kUnsavedSessionFileName = "untitled";
	public final static String kWorldExtension = "wld";
	public final static String kSessionExtension = "ses";
	
	public final static int kMinWidthOnScreen = 40;
	public final static int kMinHeightOnScreen = 20;
	public final static String kDefaultLogFileName = "StoryHarp.log";
	public final static int kPageTable = 0;
	public final static int kPageMap = 1;
	public final static int kPageBrowser = 2;
	public final static String kDefaultAgentCharacterFileName = "StoryHarp.acs";
	public final static String kDefaultIniFileName = "StoryHarp.ini";
	public final static int kEncryptingMultiplierForAccumulatedUnregisteredTime = 1;
	public final static String kKeyForAccumulatedUnregisteredTime = "Time scale fraction";
	
    public TWorld world;
    public TSCommandList sessionCommandList;
    public TSCommandList worldCommandList;
    public String sessionFileName;
    public String worldFileName;
    public int sessionChangeCount;
    public int worldChangeCount;
    public boolean isWorldFileLoaded;
    public TSMapView mapView;
    public int changeLock;
    public DomainOptionsStructure options = new DomainOptionsStructure();
    public String iniFileName;
    public String sessionOrWorldStartupFileName;
    public boolean playerOnly;
    public boolean useIniFile;
    public String registrationName;
    public String registrationCode;
    public boolean registered;
    public long startTimeThisSession;
    public long accumulatedUnregisteredTime;
    public boolean justRegistered;
    public String[] args;
    
    public static TSDomain domain = new TSDomain();
    
    public TSDomain(String[] args) {
        super();
        this.args = args;
        this.world = new TWorld();
        this.sessionCommandList = new TSCommandList();
        this.sessionCommandList.setNewUndoLimit(1000);
        this.worldCommandList = new TSCommandList();
        this.worldCommandList.setNewUndoLimit(1000);
        this.worldFileName = kUnsavedWorldFileName + "." + kWorldExtension;
        this.sessionFileName = kUnsavedSessionFileName + "." + kSessionExtension;
        this.sessionChangeCount = 0;
        this.worldChangeCount = 0;
        this.isWorldFileLoaded = false;
        this.mapView = new TSMapView();
        this.sessionOrWorldStartupFileName = "";
        this.playerOnly = false;
        this.useIniFile = true;
        this.iniFileName = kDefaultIniFileName;
        this.readCommandLine();
        this.readIniFile();
        // if not registered then  // registration stored in ini file
        this.startTimeThisSession = new Date().getTime();
        this.justRegistered = false;
    }
    
    public TSDomain() {
    	this(null);
    }
    
    public void readCommandLine() {
    	if (args == null) return;
    	for (String arg: args) {
    		String argUpperCase = arg.toUpperCase();
            if (arg.toUpperCase().equals("/I=")) {
                this.useIniFile = false;
            } else if (argUpperCase.equals("/I")) {
                this.useIniFile = false;
            } else if (argUpperCase.startsWith("/I=")) {
                this.iniFileName = arg.substring(4);
            } else if (argUpperCase.startsWith("/I")) {
                this.iniFileName = arg.substring(3);
            } else if (argUpperCase.startsWith("/P")) {
                this.playerOnly = true;
            } else if (this.sessionOrWorldStartupFileName.equals("") && !argUpperCase.startsWith("/")) {
                this.sessionOrWorldStartupFileName = arg;
            } else {
            	UFileSupport.ShowMessage("Improper parameter string " + arg);
            }
        }
    }
    
    public void readIniFile() {
        boolean iniFileFound = false;
        
        if (!this.useIniFile) {
            this.defaultOptions();
            return;
        }
        	
        if (this.iniFileName.indexOf("/") != -1) {
            // ini file name has path
            // alternate ini file must exist before user uses it
            iniFileFound = new File(this.iniFileName).exists();
            if (!iniFileFound) {
            	UFileSupport.ShowMessage("Could not find alternate settings file \n\n  " + this.iniFileName + "\n\nUsing standard settings file in Windows directory instead.");
                this.iniFileName = kDefaultIniFileName;
                String iniFileInSystemProfilesDirectory = this.systemProfilesDirectory() + File.separator + this.iniFileName;
                iniFileFound = new File(iniFileInSystemProfilesDirectory).exists();
                this.iniFileName = iniFileInSystemProfilesDirectory;
            }
        } else {
        	String iniFileInSystemProfilesDirectory = this.systemProfilesDirectory() + File.separator + this.iniFileName;
            iniFileFound = new File(iniFileInSystemProfilesDirectory).exists();
            this.iniFileName = iniFileInSystemProfilesDirectory;
        }
        if (iniFileFound && this.useIniFile) {
            this.getProfileInformation();
        } else {
            this.defaultOptions();
        }
    }
    
    // PDF PORT __ BUGGY, SHOULD BE 16; left for compatability
    // Also, should start at 0 and then switch to A at 10, but just goes from A
    int encodingMultiplier = 32;
    
    // A Weird form of hex encoding starting from A
    public String weirdHexEncode(String aString) {
        char letter = ' ';
        
        String result = "";
        for (int i = 0; i < aString.length(); i++) {
            // ((i+4) mod length(aString))
            letter = aString.charAt(i);
            result = result + Character.toString((char) ('A' + (letter / encodingMultiplier)));
            result = result + Character.toString((char) ('A' + (letter % encodingMultiplier)));
        }
        return result;
    }

 // A Weird form of hex encoding starting from A
    public String weirdHexUnencode(String encodedString) {
        char letter = ' ';
        char value = 0;
        String result = "";
        for (int i = 0; i < encodedString.length(); i++) {
            letter = encodedString.charAt(i);
            if (i % 2 == 0) {
                value = (char) ((letter - 'A') * encodingMultiplier);
            } else {
                value = (char) (value + (letter - 'A'));
                result = result + Character.toString(value);
            }
        }
        return result;
    }
    
    public boolean storeProfileInformation() {
        String section = "";
        Ini iniFile = new Ini();
		File file = new File(this.iniFileName);
		try {
			if (file.exists()) {
				iniFile.load(file);
			}
		} catch (InvalidFileFormatException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}

        // files
        section = "Files";
        iniFile.put(section, "Log file", this.options.logFileName);
        iniFile.put(section, "Agent character file", this.options.agentCharacterFileName);
        iniFile.put(section, "Extra media directory", this.options.extraMediaDirectory);
        iniFile.put(section, "Most recent session", this.options.mostRecentSession);
        iniFile.put(section, "Most recent world", this.options.mostRecentWorld);
        
        // player options
        section = "Player";
        iniFile.put(section, "Speak", this.options.playerSpeak);
        iniFile.put(section, "Play sounds", this.options.playerPlaySounds);
        iniFile.put(section, "Play music", this.options.playerPlayMusic);
        iniFile.put(section, "Show transcript", this.options.showTranscript);
        iniFile.put(section, "Show pictures", this.options.showPictures);
        iniFile.put(section, "Say options after look", this.options.sayOptionsAfterLook);
        iniFile.put(section, "Use voice to undo", this.options.useVoiceToUndo);
        iniFile.put(section, "Use voice to redo", this.options.useVoiceToRedo);
        iniFile.put(section, "Show variables", this.options.showVariables);
        iniFile.put(section, "Update editor after command done", this.options.updateEditorAfterCommandDone);
        iniFile.put(section, "Suppress agent not present warning", this.options.suppressAgentNotPresentWarning);
        iniFile.put(section, "Player font size", this.options.playerFontSize);
        iniFile.put(section, "Player font name", this.options.playerFontName);
        
        // editor options
        section = "Editor";
        iniFile.put(section, "Background color for selected items", UFileSupport.DelphiIntForColor(this.options.selectedItemColor));
        iniFile.put(section, "Text color for selected items", UFileSupport.DelphiIntForColor(this.options.selectedTextColor));
        iniFile.put(section, "Text color for commands in map", UFileSupport.DelphiIntForColor(this.options.commandTextColorInMap));
        iniFile.put(section, "Show commands in map", this.options.showCommandsInMap);
        iniFile.put(section, "Show command prefix in map", this.options.showCommandPrefixInMap);
        iniFile.put(section, "Table font name", this.options.tableFontName);
        iniFile.put(section, "Table font size", this.options.tableFontSize);
        iniFile.put(section, "Map font name", this.options.mapFontName);
        iniFile.put(section, "Map font size", this.options.mapFontSize);
        iniFile.put(section, "Browser font name", this.options.browserFontName);
        iniFile.put(section, "Browser font size", this.options.browserFontSize);
        iniFile.put(section, "Show rule editor", this.options.showRuleEditor);
        iniFile.put(section, "Show button bar", this.options.showButtonBar);
        iniFile.put(section, "Browse by (context, rule, reply, move, requirements, changes)", this.options.browseBy);
        iniFile.put(section, "Page showing (table, map, browser)", this.options.pageShowing);
        iniFile.put(section, "Button symbols", this.options.buttonSymbols);
        
        if (this.justRegistered) {
            // registration, embedded here to hide time scale fraction
            // always track useage
            section = "Registration";
            iniFile.put(section, "R1", "BQRESTYUBSHQYIBLJHSD");
            iniFile.put(section, "R2", "BTBTBYBUOBTRST");
            iniFile.put(section, "R3", weirdHexEncode(this.registrationCode));
            iniFile.put(section, "R4", weirdHexEncode(this.registrationName));
        }
        if ((!this.registered) && (!this.playerOnly)) {
            section = "Editor";
            long elapsedTimeThisSession = Math.max((new Date()).getTime() - this.startTimeThisSession, 0);
            this.accumulatedUnregisteredTime = this.accumulatedUnregisteredTime + elapsedTimeThisSession;
            float saveNumber = this.accumulatedUnregisteredTime * kEncryptingMultiplierForAccumulatedUnregisteredTime;
            iniFile.put(section, kKeyForAccumulatedUnregisteredTime, saveNumber);
        }
        
        // windows
        section = "Windows";
        iniFile.put(section, "Player window position", UFileSupport.rectToString(this.options.consoleWindowRect));
        iniFile.put(section, "Editor window position", UFileSupport.rectToString(this.options.editorWindowRect));
        iniFile.put(section, "Log file window position", UFileSupport.rectToString(this.options.logFileWindowRect));
        iniFile.put(section, "Player horizontal splitter", this.options.consoleBottomHeight);
        iniFile.put(section, "Player vertical splitter", this.options.consoleRightWidth);
        iniFile.put(section, "Editor top splitter", this.options.editorPanelEditorHeight);
        iniFile.put(section, "Editor bottom splitter", this.options.editorPanelRequirementsChangesHeight);
        iniFile.put(section, "Editor browser splitter", this.options.editorPanelFirstListWidth);
        iniFile.put(section, "Picture window position", UFileSupport.rectToString(this.options.pictureWindowRect));
        try {
			iniFile.store(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
    }
    
    public void getProfileInformation() {
    	String section = "";
		File file = new File(this.iniFileName);
		IniWithDefaulting iniFile = new IniWithDefaulting();
		try {
			if (file.exists()) {
				iniFile.load(file);
			}
		} catch (InvalidFileFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

        // files
        section = "Files";
        this.options.logFileName = iniFile.readString(section, "Log file", UFileSupport.getExePathAndSeparator() + kDefaultLogFileName);
        this.options.agentCharacterFileName = iniFile.readString(section, "Agent character file", UFileSupport.getResourcesPathAndSeparator() + kDefaultAgentCharacterFileName);
        this.options.extraMediaDirectory = iniFile.readString(section, "Extra media directory", "");
        this.options.mostRecentSession = iniFile.readString(section, "Most recent session", "");
        this.options.mostRecentWorld = iniFile.readString(section, "Most recent world", "");
        
        // player options
        section = "Player";
        this.options.playerSpeak = iniFile.readStringAsBoolean(section, "Speak", "true");
        this.options.playerPlaySounds = iniFile.readStringAsBoolean(section, "Play sounds", "true");
        this.options.playerPlayMusic = iniFile.readStringAsBoolean(section, "Play music", "true");
        this.options.showTranscript = iniFile.readStringAsBoolean(section, "Show transcript", "true");
        this.options.showPictures = iniFile.readStringAsBoolean(section, "Show pictures", "true");
        this.options.sayOptionsAfterLook = iniFile.readStringAsBoolean(section, "Say options after look", "true");
        this.options.useVoiceToUndo = iniFile.readStringAsBoolean(section, "Use voice to undo", "false");
        this.options.useVoiceToRedo = iniFile.readStringAsBoolean(section, "Use voice to redo", "false");
        this.options.showVariables = iniFile.readStringAsBoolean(section, "Show variables", "false");
        this.options.updateEditorAfterCommandDone = iniFile.readStringAsBoolean(section, "Update editor after command done", "true");
        this.options.suppressAgentNotPresentWarning = iniFile.readStringAsBoolean(section, "Suppress agent not present warning", "false");
        this.options.playerFontSize = iniFile.readStringAsInt(section, "Player font size", Integer.toString(kDefaultFontSize));
        this.options.playerFontName = iniFile.readString(section, "Player font name", kDefaultFontName);
        
        // editor options
        section = "Editor";
        this.options.selectedItemColor = iniFile.readStringAsColor(section, "Background color for selected items", String.valueOf(Color.yellow.getRGB()));
        this.options.selectedTextColor = iniFile.readStringAsColor(section, "Text color for selected items", String.valueOf(Color.black.getRGB()));
        this.options.commandTextColorInMap = iniFile.readStringAsColor(section, "Text color for commands in map", String.valueOf(Color.blue.getRGB()));
        this.options.showCommandsInMap = iniFile.readStringAsBoolean(section, "Show commands in map", "true");
        this.options.showCommandPrefixInMap = iniFile.readStringAsBoolean(section, "Show command prefix in map", "false");
        this.options.tableFontName = iniFile.readString(section, "Table font name", kDefaultFontName);
        this.options.tableFontSize = iniFile.readStringAsInt(section, "Table font size", Integer.toString(kDefaultFontSize));
        this.options.mapFontName = iniFile.readString(section, "Map font name", kDefaultFontName);
        this.options.mapFontSize = iniFile.readStringAsInt(section, "Map font size", Integer.toString(kDefaultFontSize));
        this.options.browserFontName = iniFile.readString(section, "Browser font name", kDefaultFontName);
        this.options.browserFontSize = iniFile.readStringAsInt(section, "Browser font size", Integer.toString(kDefaultFontSize));
        this.options.showRuleEditor = iniFile.readStringAsBoolean(section, "Show rule editor", "true");
        this.options.showButtonBar = iniFile.readStringAsBoolean(section, "Show button bar", "true");
        this.options.browseBy = iniFile.readStringAsInt(section, "Browse by (context, rule, reply, move, requirements, changes)", "0");
        this.options.pageShowing = iniFile.readStringAsInt(section, "Page showing (table, map, browser)", "0");
        this.options.buttonSymbols = iniFile.readStringAsBoolean(section, "Button symbols", "true");
        
        // windows
        section = "Windows";
        this.options.consoleWindowRect = UFileSupport.stringToRect(iniFile.readString(section, "Player window position", "150 20 200 420"));
        this.options.editorWindowRect = UFileSupport.stringToRect(iniFile.readString(section, "Editor window position", "40 30 560 400"));
        this.options.logFileWindowRect = UFileSupport.stringToRect(iniFile.readString(section, "Log file window position", "50 50 550 400"));
        this.options.consoleBottomHeight = iniFile.readStringAsInt(section, "Player horizontal splitter", "200");
        this.options.consoleRightWidth = iniFile.readStringAsInt(section, "Player vertical splitter", "200");
        this.options.editorPanelEditorHeight = iniFile.readStringAsInt(section, "Editor top splitter", "150");
        this.options.editorPanelRequirementsChangesHeight = iniFile.readStringAsInt(section, "Editor bottom splitter", "100");
        this.options.editorPanelFirstListWidth = iniFile.readStringAsInt(section, "Editor browser splitter", "200");
        this.options.pictureWindowRect = UFileSupport.stringToRect(iniFile.readString(section, "Picture window position", "200 200 200 200"));
        
        // registration
        section = "Registration";
        this.registrationName = iniFile.readString(section, "R4", "");
        this.registrationName = weirdHexUnencode(this.registrationName);
        this.registrationCode = iniFile.readString(section, "R3", "");
        this.registrationCode = weirdHexUnencode(this.registrationCode);
        // PDF PORT: this.registered = URegisterSupport.RegistrationMatch(this.registrationName, this.registrationCode);
        this.registered = true; 
        if (!this.registered) {
            section = "Editor";
            String timeString = iniFile.readString(section, kKeyForAccumulatedUnregisteredTime, "0");
            float readNumber = Math.max(Float.parseFloat(timeString), 0);
            this.accumulatedUnregisteredTime = Math.round(readNumber / kEncryptingMultiplierForAccumulatedUnregisteredTime);
        }
    }

    public static final int kDefaultFontSize = 14;
    public static final String kDefaultFontName = "Arial";
    
    public void defaultOptions() {
        // this is for first-time use, when there is no ini file
        // files
    	// PDF PORT: Not sure where to put the log file or find agent file (going away); was: UFileSupport.getExePathAndSeparator()
        this.options.logFileName = kDefaultLogFileName;
        this.options.agentCharacterFileName = kDefaultAgentCharacterFileName;
        this.options.extraMediaDirectory = "";
        this.options.mostRecentSession = "";
        this.options.mostRecentWorld = "";
        // player options
        this.options.playerSpeak = true;
        this.options.playerPlaySounds = true;
        this.options.playerPlayMusic = true;
        this.options.showTranscript = true;
        this.options.showPictures = true;
        this.options.sayOptionsAfterLook = true;
        this.options.useVoiceToUndo = false;
        this.options.useVoiceToRedo = false;
        this.options.showVariables = false;
        this.options.updateEditorAfterCommandDone = true;
        this.options.suppressAgentNotPresentWarning = false;
        this.options.playerFontSize = kDefaultFontSize;
        this.options.playerFontName = kDefaultFontName;
        // editor options
        this.options.selectedItemColor = Color.yellow;
        this.options.selectedTextColor = Color.black;
        this.options.commandTextColorInMap = Color.blue;
        this.options.showCommandsInMap = true;
        this.options.showCommandPrefixInMap = false;
        this.options.tableFontName = kDefaultFontName;
        this.options.tableFontSize = kDefaultFontSize;
        this.options.mapFontName = kDefaultFontName;
        this.options.mapFontSize = kDefaultFontSize;
        this.options.browserFontName = kDefaultFontName;
        this.options.browserFontSize = kDefaultFontSize;
        this.options.showRuleEditor = true;
        this.options.showButtonBar = true;
        this.options.browseBy = TSVariableDisplayOptions.kRuleContext;
        this.options.pageShowing = 0;
        this.options.buttonSymbols = false;
        // windows
        // PDF PORT: These window positions may need to be adjusted for Rectangle extent conversion issues
        this.options.consoleWindowRect = new Rectangle(150, 20, 200, 420);
        this.options.editorWindowRect = new Rectangle(40, 30, 560, 400);
        this.options.logFileWindowRect = new Rectangle(50, 50, 550, 400);
        this.options.consoleBottomHeight = 200;
        this.options.consoleRightWidth = 200;
        this.options.editorPanelEditorHeight = 150;
        this.options.editorPanelRequirementsChangesHeight = 100;
        this.options.editorPanelFirstListWidth = 200;
        this.options.pictureWindowRect = new Rectangle(200, 200, 200, 200);
    }
    
    public String systemProfilesDirectory() {
    	// PDF PORT FIX
        return ".";
    }
    
    public void newWorld() {
        this.sessionCommandList.clear();
        this.sessionFileName = kUnsavedSessionFileName + "." + kSessionExtension;
        this.sessionChangeCount = 0;
        this.worldCommandList.clear();
        this.worldFileName = kUnsavedWorldFileName + "." + kWorldExtension;
        this.worldChangeCount = 0;
        this.isWorldFileLoaded = false;
        this.world.newWorld();
    }
    
    public void newSession() {
        this.sessionCommandList.clear();
        this.sessionFileName = kUnsavedSessionFileName + "." + kSessionExtension;
        this.sessionChangeCount = 0;
        this.world.newSession();
    }
    
    public void loadSession(String fileName) {
        this.sessionCommandList.clear();
        this.sessionFileName = fileName;
        this.sessionChangeCount = 0;
        if (this.world.loadSessionFromFile(fileName, new File(this.worldFileName).getName())) {
            this.options.mostRecentWorld = this.worldFileName;
            this.options.mostRecentSession = fileName;
        }
    }
    
    public void saveSession(String fileName) {
        this.world.saveSessionToFile(fileName, new File(this.worldFileName).getName());
        this.options.mostRecentWorld = this.worldFileName;
    }
    
    public boolean isSessionFileChanged() {
        return this.sessionChangeCount != 0;
    }
    
    public void mergeWorld(String fileName) {
        // don't clear things
        this.world.loadWorldFromFile(fileName);
        this.worldChangeCount += 1;
    }
    
    public void saveWorld(String fileName) {
        this.world.saveWorldToFile(fileName, TWorld.kSaveAllRules);
    }
    
    public boolean isWorldFileChanged() {
        return this.worldChangeCount != 0;
    }
    
    public void beginUpdate() {
        this.changeLock += 1;
    }
    
    public void endUpdate() {
        this.changeLock -= 1;
    }
    
    public boolean ignoreChanges() {
        return this.changeLock != 0;
    }
    
    public void resetWorldChangeCount() {
        this.worldChangeCount = 0;
    }
    
    public void worldChangeDone() {
        this.worldChangeCount += 1;
    }
    
    public void worldChangeUndone() {
        this.worldChangeCount -= 1;
    }
 
    public void loadWorld(String fileName) {
        this.sessionCommandList.clear();
        this.sessionFileName = kUnsavedSessionFileName + "." + kSessionExtension;
        this.sessionChangeCount = 0;
        this.worldCommandList.clear();
        this.worldFileName = fileName;
        this.worldChangeCount = 0;
        this.world.resetVariablesAndRules();
        this.world.focus = null;
        this.world.previousFocus = null;
        if (this.world.loadWorldFromFile(fileName)) {
            this.isWorldFileLoaded = true;
            this.options.mostRecentWorld = this.worldFileName;
            this.options.mostRecentSession = "";
        }
    }
     
}
