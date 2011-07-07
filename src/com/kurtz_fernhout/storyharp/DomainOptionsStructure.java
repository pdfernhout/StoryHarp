package com.kurtz_fernhout.storyharp;

import java.awt.Color;
import java.awt.Rectangle;

public class DomainOptionsStructure implements Cloneable {
	
	// files
	public String extraMediaDirectory;
	public String logFileName;
	public String agentCharacterFileName;
	public String mostRecentSession;
	public String mostRecentWorld;
	
	// player
	public boolean playerSpeak;
	public boolean playerPlaySounds;
	public boolean playerPlayMusic;
	public boolean showTranscript = true;
	public boolean showPictures;
	public boolean sayOptionsAfterLook;
	public boolean useVoiceToUndo;
	public boolean useVoiceToRedo;
	public boolean showVariables;
	public boolean updateEditorAfterCommandDone;
	public int playerFontSize;
	public String playerFontName;
	
	// Media player
    public boolean loopMusic;
	
	// Agent
	public boolean suppressAgentNotPresentWarning;
	
	// editor
	public Color selectedItemColor;
	public Color selectedTextColor;
	public Color commandTextColorInMap;
	public boolean showCommandsInMap;
	public boolean showCommandPrefixInMap;
	public String tableFontName;
	public String mapFontName;
	public String browserFontName;
	public int tableFontSize;
	public int mapFontSize;
	public int browserFontSize;
	public boolean showRuleEditor;
	public boolean showButtonBar;
	public int browseBy;
	public int pageShowing;
	public boolean buttonSymbols;
	
	// windows
	public Rectangle consoleWindowRect;
	public Rectangle editorWindowRect;
	public Rectangle logFileWindowRect;
	public int consoleBottomHeight;
	public int consoleRightWidth;
	public int editorPanelEditorHeight;
	public int editorPanelRequirementsChangesHeight;
	public int editorPanelFirstListWidth;
	public Rectangle pictureWindowRect;
	
	public DomainOptionsStructure clone() {
		try {
			return (DomainOptionsStructure) super.clone();
			// the rectangles are not made copies of, but maybe does not matter for how this is used
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
