package com.kurtz_fernhout.storyharp;

import java.awt.Rectangle;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

public class Application {
	public static boolean terminated = false;
	
	// const
	public static final String gVersionName = "Version 2.00";
	public static final String kProgramName = "StoryHarp";
	
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
	
	
//	  Application.Initialize;
//
//	  Application.Title := 'StoryHarp';
//	  Application.HelpFile := 'StoryHarp.hlp';
//	  
	public static TConsoleWindow ConsoleForm = new TConsoleWindow();
	public static TAboutForm AboutForm = new TAboutForm();
	public static TPictureWindow PictureForm = new TPictureWindow();
//	  
//	  AboutForm.setUpAsSplashOrAbout(kAsSplash);
//	  AboutForm.show;
	//PDF PORT CHECHK: maybe repaint not needed
//	  AboutForm.repaint();
//
	public static TRuleEditorForm RuleEditorForm = new TRuleEditorForm();
	public static TCommandWizardForm CommandWizardForm = new TCommandWizardForm();
	public static TContextWizardForm ContextWizardForm = new TContextWizardForm();
	public static TLinkWizardForm LinkWizardForm = new TLinkWizardForm();
	public static TChangeLogForm ChangeLogForm = new TChangeLogForm();
	public static TPreferencesForm PreferencesForm = new TPreferencesForm();
	public static TExtraMediaDirectoryWindow ExtraMediaDirectoryWindow = new TExtraMediaDirectoryWindow();
	public static TSearchDialog SearchDialog = new TSearchDialog();
	//public static TUnregisteredAboutForm UnregisteredAboutForm = new TUnregisteredAboutForm();
//	  Application.CreateForm(TRegistrationForm, RegistrationForm);
//	  domain.loadFileAtStartupAndInitializeForms();
//
//	  AboutForm.hide;
//	  AboutForm.setUpAsSplashOrAbout(kAsAbout);
//
//	  Application.Run;
	
    public void loadFileAtStartupAndInitializeForms() {
        TSDomain domain = TSDomain.domain;
        
        if (domain.sessionOrWorldStartupFileName.equals("")) {
            Application.ConsoleForm.openSessionOrWorldFile(UNRESOLVED.ParamStr(1));
        } else {
            if ((!domain.options.mostRecentSession.equals("")) && (new File(domain.options.mostRecentSession).exists())) {
                Application.ConsoleForm.openSessionOrWorldFile(domain.options.mostRecentSession);
            } else if ((!domain.options.mostRecentWorld.equals("")) && (new File(domain.options.mostRecentWorld).exists())) {
                Application.ConsoleForm.openSessionOrWorldFile(domain.options.mostRecentWorld);
            }
        }
        if (domain.playerOnly) {
            Application.ConsoleForm.playerOnly();
        }
        Application.ConsoleForm.updateForRegistrationChange();
        Application.ConsoleForm.updateTitles();
        Application.RuleEditorForm.updateForRuleChange();
        Application.RuleEditorForm.MapPaintBoxChanged();
        Application.RuleEditorForm.adjustScrollBars();
        Application.RuleEditorForm.updateViews();
        Application.RuleEditorForm.editRule(null);
        
        Calendar calendar = GregorianCalendar.getInstance();
        if ((!domain.registered) && (calendar.get(Calendar.YEAR) >= 2000)) {
            UFileSupport.ShowMessage("This evaluation copy of StoryHarp is out of date.\n\n" +
            		"Please check for an updated evaluation version at:\n" +
            		"http://www.kurtz-fernhout.com\n\n" +
            		"The web site may also have updated pricing information.\n" +
            		"This message will disappear when the product is registered.");
        }
    }
    
    public static void setFormSize(JFrame aForm, Rectangle newRect) {
        if (aForm == null) {
            return;
        }
        // PDF PORT
//        if ((newRect.x != 0) || (newRect.Right != 0) || (newRect.y != 0) || (newRect.Bottom != 0)) {
//            if (newRect.x > delphi_compatability.Screen.Width - kMinWidthOnScreen) {
//                newRect.x = delphi_compatability.Screen.Width - kMinWidthOnScreen;
//            }
//            if (newRect.y > delphi_compatability.Screen.Height - kMinHeightOnScreen) {
//                newRect.y = delphi_compatability.Screen.Height - kMinHeightOnScreen;
//            }
//            aForm.Position = delphi_compatability.TPosition.poDesigned;
//            aForm.SetBounds(newRect.x, newRect.y, newRect.Right, newRect.Bottom);
//        }
    }  
    
    
    public static void addWorldContextsToListBox(DefaultListModel listModel) {
    	listModel.clear();
        for (int i = 0; i < TSDomain.domain.world.variables.size(); i++) {
        	TSVariable variable = TSDomain.domain.world.variables.get(i);
            if (variable.contextUseages > 0) {
            	listModel.addElement(variable);
            }
        }
    }

	public static void HelpJump(String helpTopic) {
		// PDF PORT FIX
		System.out.println("should open help on: " + helpTopic);
	}

	public static void Terminate() {
		// maybe want to do shutdown stuff first?
		System.exit(0);	
	}
}