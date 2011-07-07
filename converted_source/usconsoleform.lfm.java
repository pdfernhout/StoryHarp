
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// import common.*;

public class ConsoleWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    JSplitPane SplitterVariables;
    JPanel StatusBar;
    JPanel PanelConsole;
    JSplitPane SplitterConsole;
    JList VisibleCommandsList;
    JTextArea TranscriptEdit;
    JPanel PanelVariables;
    JPanel VariablesControlPanel;
    SpeedButton ContextButton;
    SpeedButton MoveButton;
    SpeedButton RequirementsButton;
    SpeedButton ChangesButton;
    SpeedButton CommandButton;
    SpeedButton ShowOnlyTrueVariablesButton;
    JComboBox FocusComboBox;
    JList VariablesListBox;
    JPanel picturesAndSymbolsPanel;
    ImagePanel contextPicture;
    ImagePanel contextSymbol;
    ImagePanel commandPicture;
    ImagePanel commandSymbol;
    ImagePanel movePicture;
    ImagePanel moveSymbol;
    ImagePanel requirementsPicture;
    ImagePanel requirementsSymbol;
    ImagePanel changesPicture;
    ImagePanel changesSymbol;
    ImagePanel plusPicture;
    ImagePanel plusSymbol;
    ImagePanel replyPicture;
    ImagePanel replySymbol;
    JMenuBar MainMenu;
    JMenu MenuFile;
    JMenuItem MenuFileOpenWorld;
    JMenuItem MenuFileOpenSession;
    JMenuItem MenuFileNewSession;
    JMenuItem N6;
    JMenuItem MenuFileOpenPictureWindow;
    JMenuItem N4;
    JMenuItem MenuFileSaveSession;
    JMenuItem MenuFileSaveSessionAs;
    JMenuItem N3;
    JMenuItem MenuFileExit;
    JMenu MenuEdit;
    JMenuItem MenuEditUndo;
    JMenuItem MenuEditRedo;
    JMenuItem N5;
    JMenuItem MenuEditCopy;
    JMenuItem N8;
    JMenuItem MenuEditRepeatLast;
    JMenu MenuOptions;
    JCheckBoxMenuItem MenuOptionsSpeak;
    JCheckBoxMenuItem MenuOptionsSounds;
    JCheckBoxMenuItem MenuOptionsPlayMusic;
    JCheckBoxMenuItem MenuSettingsShowTranscript;
    JCheckBoxMenuItem MenuSettingsShowPictures;
    JMenuItem N1;
    JMenuItem MenuSettingsCharacter;
    JMenuItem MediaPath1;
    JMenuItem MenuSettingsPlayerFont;
    JMenuItem N2;
    JCheckBoxMenuItem MenuSettingsSayOptionsAfterLook;
    JMenuItem MenuOptionsVoiceUndo;
    JMenuItem MenuOptionsVoiceRedo;
    JMenu MenuDevelopment;
    JMenuItem MenuEditStory;
    JMenuItem N7;
    JCheckBoxMenuItem MenuOptionsShowVariables;
    JCheckBoxMenuItem MenuOptionsUpdateEditorSelections;
    JMenu MenuHelp;
    JMenuItem MenuHelpContents;
    JMenuItem MenuHelpPlayingStories;
    JMenuItem MenuHelpAgent;
    JMenuItem MenuHelpRegisterLine;
    JMenuItem MenuHelpRegister;
    JMenuItem AfterRegisterMenuSeparator;
    JMenuItem MenuHelpAbout;
    // UNHANDLED_TYPE FontDialog;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
    
    public ConsoleWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    public JMenuBar getMainMenu() {
        if (MainMenu != null) return MainMenu;
        this.MainMenu = new JMenuBar();
        this.MenuFile = new JMenu("Session");
        this.MenuFile.setMnemonic('S');
        this.MainMenu.add(MenuFile);
        this.MenuFileOpenWorld = new JMenuItem("Open World...");
        this.MenuFileOpenWorld.setMnemonic(KeyEvent.VK_W);
        this.MenuFileOpenWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileOpenWorldClick(event);
            }
        });
        this.MenuFile.add(MenuFileOpenWorld);
        
        this.MenuFileOpenSession = new JMenuItem("Open Saved Session...");
        this.MenuFileOpenSession.setMnemonic(KeyEvent.VK_O);
        this.MenuFileOpenSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileOpenSessionClick(event);
            }
        });
        this.MenuFile.add(MenuFileOpenSession);
        
        this.MenuFileNewSession = new JMenuItem("Restart");
        this.MenuFileNewSession.setMnemonic(KeyEvent.VK_R);
        this.MenuFileNewSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileNewSessionClick(event);
            }
        });
        this.MenuFile.add(MenuFileNewSession);
        
        this.MenuFile.addSeparator();
        
        this.MenuFileOpenPictureWindow = new JMenuItem("Open Picture Window");
        this.MenuFileOpenPictureWindow.setMnemonic(KeyEvent.VK_P);
        this.MenuFileOpenPictureWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileOpenPictureWindowClick(event);
            }
        });
        this.MenuFile.add(MenuFileOpenPictureWindow);
        
        this.MenuFile.addSeparator();
        
        this.MenuFileSaveSession = new JMenuItem("Save");
        this.MenuFileSaveSession.setMnemonic(KeyEvent.VK_S);
        this.MenuFileSaveSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileSaveSessionClick(event);
            }
        });
        this.MenuFile.add(MenuFileSaveSession);
        
        this.MenuFileSaveSessionAs = new JMenuItem("Save As...");
        this.MenuFileSaveSessionAs.setMnemonic(KeyEvent.VK_A);
        this.MenuFileSaveSessionAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileSaveSessionAsClick(event);
            }
        });
        this.MenuFile.add(MenuFileSaveSessionAs);
        
        this.MenuFile.addSeparator();
        
        this.MenuFileExit = new JMenuItem("Exit");
        this.MenuFileExit.setMnemonic(KeyEvent.VK_X);
        this.MenuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileExitClick(event);
            }
        });
        this.MenuFile.add(MenuFileExit);
        
        
        
        this.MenuEdit = new JMenu("Edit");
        this.MenuEdit.setMnemonic('E');
        this.MainMenu.add(MenuEdit);
        this.MenuEditUndo = new JMenuItem("Undo");
        this.MenuEditUndo.setMnemonic(KeyEvent.VK_U);
        this.MenuEditUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditUndoClick(event);
            }
        });
        this.MenuEdit.add(MenuEditUndo);
        this.MenuEditUndo.setEnabled(false);
        
        this.MenuEditRedo = new JMenuItem("Redo");
        this.MenuEditRedo.setMnemonic(KeyEvent.VK_R);
        this.MenuEditRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditRedoClick(event);
            }
        });
        this.MenuEdit.add(MenuEditRedo);
        this.MenuEditRedo.setEnabled(false);
        
        this.MenuEdit.addSeparator();
        
        this.MenuEditCopy = new JMenuItem("Copy");
        this.MenuEditCopy.setMnemonic(KeyEvent.VK_C);
        this.MenuEditCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditCopyClick(event);
            }
        });
        this.MenuEdit.add(MenuEditCopy);
        
        this.MenuEdit.addSeparator();
        
        this.MenuEditRepeatLast = new JMenuItem("Repeat Last");
        this.MenuEditRepeatLast.setMnemonic(KeyEvent.VK_P);
        this.MenuEditRepeatLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditRepeatLastClick(event);
            }
        });
        this.MenuEdit.add(MenuEditRepeatLast);
        
        
        
        this.MenuOptions = new JMenu("Settings");
        this.MenuOptions.setMnemonic('S');
        this.MainMenu.add(MenuOptions);
        this.MenuOptionsSpeak = new JCheckBoxMenuItem("Speak");
        this.MenuOptionsSpeak.setMnemonic(KeyEvent.VK_S);
        this.MenuOptionsSpeak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsSpeakClick(event);
            }
        });
        this.MenuOptions.add(MenuOptionsSpeak);
        this.MenuOptionsSpeak.setSelected(false);
        
        this.MenuOptionsSounds = new JCheckBoxMenuItem("Play Sounds");
        this.MenuOptionsSounds.setMnemonic(KeyEvent.VK_N);
        this.MenuOptionsSounds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsSoundsClick(event);
            }
        });
        this.MenuOptions.add(MenuOptionsSounds);
        this.MenuOptionsSounds.setSelected(false);
        
        this.MenuOptionsPlayMusic = new JCheckBoxMenuItem("Play Music");
        this.MenuOptionsPlayMusic.setMnemonic(KeyEvent.VK_M);
        this.MenuOptionsPlayMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsPlayMusicClick(event);
            }
        });
        this.MenuOptions.add(MenuOptionsPlayMusic);
        this.MenuOptionsPlayMusic.setSelected(false);
        
        this.MenuSettingsShowTranscript = new JCheckBoxMenuItem("Show Transcript");
        this.MenuSettingsShowTranscript.setMnemonic(KeyEvent.VK_T);
        this.MenuSettingsShowTranscript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuSettingsShowTranscriptClick(event);
            }
        });
        this.MenuOptions.add(MenuSettingsShowTranscript);
        this.MenuSettingsShowTranscript.setSelected(false);
        
        this.MenuSettingsShowPictures = new JCheckBoxMenuItem("Show Pictures");
        this.MenuSettingsShowPictures.setMnemonic(KeyEvent.VK_P);
        this.MenuSettingsShowPictures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuSettingsShowPicturesClick(event);
            }
        });
        this.MenuOptions.add(MenuSettingsShowPictures);
        this.MenuSettingsShowPictures.setSelected(false);
        
        this.MenuOptions.addSeparator();
        
        this.MenuSettingsCharacter = new JMenuItem("Agent Character...");
        this.MenuSettingsCharacter.setMnemonic(KeyEvent.VK_C);
        this.MenuSettingsCharacter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuSettingsCharacterClick(event);
            }
        });
        this.MenuOptions.add(MenuSettingsCharacter);
        
        this.MediaPath1 = new JMenuItem("Extra Sound && Music Directory...");
        this.MediaPath1.setMnemonic(KeyEvent.VK_D);
        this.MediaPath1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MediaPath1Click(event);
            }
        });
        this.MenuOptions.add(MediaPath1);
        
        this.MenuSettingsPlayerFont = new JMenuItem("Player Font...");
        this.MenuSettingsPlayerFont.setMnemonic(KeyEvent.VK_F);
        this.MenuSettingsPlayerFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuSettingsPlayerFontClick(event);
            }
        });
        this.MenuOptions.add(MenuSettingsPlayerFont);
        
        this.MenuOptions.addSeparator();
        
        this.MenuSettingsSayOptionsAfterLook = new JCheckBoxMenuItem("Say Options After ''Look''");
        this.MenuSettingsSayOptionsAfterLook.setMnemonic(KeyEvent.VK_O);
        this.MenuSettingsSayOptionsAfterLook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuSettingsSayOptionsAfterLookClick(event);
            }
        });
        this.MenuOptions.add(MenuSettingsSayOptionsAfterLook);
        this.MenuSettingsSayOptionsAfterLook.setSelected(true);
        
        this.MenuOptionsVoiceUndo = new JMenuItem("Use Voice to Undo");
        this.MenuOptionsVoiceUndo.setMnemonic(KeyEvent.VK_U);
        this.MenuOptionsVoiceUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsVoiceUndoClick(event);
            }
        });
        this.MenuOptions.add(MenuOptionsVoiceUndo);
        
        this.MenuOptionsVoiceRedo = new JMenuItem("Use Voice to Redo");
        this.MenuOptionsVoiceRedo.setMnemonic(KeyEvent.VK_R);
        this.MenuOptionsVoiceRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsVoiceRedoClick(event);
            }
        });
        this.MenuOptions.add(MenuOptionsVoiceRedo);
        
        
        
        this.MenuDevelopment = new JMenu("Development");
        this.MenuDevelopment.setMnemonic('D');
        this.MainMenu.add(MenuDevelopment);
        this.MenuEditStory = new JMenuItem("World Editor...");
        this.MenuEditStory.setMnemonic(KeyEvent.VK_W);
        this.MenuEditStory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditStoryClick(event);
            }
        });
        this.MenuDevelopment.add(MenuEditStory);
        
        this.MenuDevelopment.addSeparator();
        
        this.MenuOptionsShowVariables = new JCheckBoxMenuItem("Show Variables");
        this.MenuOptionsShowVariables.setMnemonic(KeyEvent.VK_V);
        this.MenuOptionsShowVariables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsShowVariablesClick(event);
            }
        });
        this.MenuDevelopment.add(MenuOptionsShowVariables);
        this.MenuOptionsShowVariables.setSelected(true);
        
        this.MenuOptionsUpdateEditorSelections = new JCheckBoxMenuItem("Update Editor Selection After Command Done");
        this.MenuOptionsUpdateEditorSelections.setMnemonic(KeyEvent.VK_U);
        this.MenuOptionsUpdateEditorSelections.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsUpdateEditorSelectionsClick(event);
            }
        });
        this.MenuDevelopment.add(MenuOptionsUpdateEditorSelections);
        this.MenuOptionsUpdateEditorSelections.setSelected(true);
        
        
        
        this.MenuHelp = new JMenu("Help");
        this.MenuHelp.setMnemonic('H');
        this.MainMenu.add(MenuHelp);
        this.MenuHelpContents = new JMenuItem("Help Topics");
        this.MenuHelpContents.setMnemonic(KeyEvent.VK_H);
        this.MenuHelpContents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpContentsClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpContents);
        
        this.MenuHelpPlayingStories = new JMenuItem("Playing Stories");
        this.MenuHelpPlayingStories.setMnemonic(KeyEvent.VK_P);
        this.MenuHelpPlayingStories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpPlayingStoriesClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpPlayingStories);
        
        this.MenuHelpAgent = new JMenuItem("Using Agent");
        this.MenuHelpAgent.setMnemonic(KeyEvent.VK_U);
        this.MenuHelpAgent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpAgentClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpAgent);
        
        this.MenuHelp.addSeparator();
        
        this.MenuHelpRegister = new JMenuItem("Register...");
        this.MenuHelpRegister.setMnemonic(KeyEvent.VK_R);
        this.MenuHelpRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpRegisterClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpRegister);
        
        this.MenuHelp.addSeparator();
        
        this.MenuHelpAbout = new JMenuItem("About...");
        this.MenuHelpAbout.setMnemonic(KeyEvent.VK_A);
        this.MenuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpAboutClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpAbout);
        
        
        
        return MainMenu;
    }
        //  --------------- UNHANDLED ATTRIBUTE: this.MainMenu.top = 96;
        //  --------------- UNHANDLED ATTRIBUTE: this.MainMenu.left = 300;
        
    private void initialize() {
        this.setSize(new Dimension(705, 325));
        this.setJMenuBar(getMainMenu());
        this.setContentPane(getMainContentPane());
        this.setTitle("StoryHarp");
    }
    
    private JPanel getMainContentPane() {
        if (mainContentPane == null) {
            mainContentPane = new JPanel();
            mainContentPane.setLayout(new BorderLayout());
            mainContentPane.add(getDelphiPanel(), BorderLayout.CENTER);
        }
        return mainContentPane;
    }
    public void OnClose(ActionEvent event) {
        System.out.println("Closed");
    }
    
    public JPanel getDelphiPanel() {
        if (delphiPanel != null) return delphiPanel;
        Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        delphiPanel = new JPanel();
        delphiPanel.setLayout(null);
        // -- delphiPanel.setLayout(BoxLayout(contentPane, BoxLayout.Y_AXIS));
        // Font buttonFont = new Font("Arial Narrow", Font.PLAIN, 9);
        // Insets buttonMargin = new Insets(1, 1, 1, 1);
        this.setBounds(1329, 182, 757, 664  + 80); // extra for title bar and menu
        //  --------------- UNHANDLED ATTRIBUTE: this.Menu = MainMenu;
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnShow = FormShow;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 100;
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent event) {
                FormKeyDown(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.OnCreate = FormCreate;
        //  --------------- UNHANDLED ATTRIBUTE: this.ActiveControl = VisibleCommandsList;
        //  --------------- UNHANDLED ATTRIBUTE: this.KeyPreview = True;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnDestroy = FormDestroy;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnClose = FormClose;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnResize = FormResize;
        this.setVisible(false);
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        
        
        this.SplitterVariables = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.SplitterVariables.setBounds(548, 0, 2, 610);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterVariables.ResizeAnchor = akRight;
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterVariables.MinSize = 60;
        
        this.StatusBar = new JPanel(null);
        this.StatusBar.setBounds(0, 610, 757, 24);
        //  --------------- UNHANDLED ATTRIBUTE: this.StatusBar.SimplePanel = False;
        this.StatusBar.setVisible(false);
        //  --------------- UNHANDLED ATTRIBUTE: this.StatusBar.Panels = [{'Text': "'Running'", 'Width': '90'}, {'Text': "'Unregistered'", 'Width': '50'}];
        
        this.SplitterConsole = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.SplitterConsole.setBounds(0, 402, 548, 208);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterConsole.MinSize = 60;
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterConsole.ResizeAnchor = akBottom;
        
        this.VisibleCommandsList = new JList(new DefaultListModel());
        this.VisibleCommandsList.setFixedCellHeight(14);
        this.VisibleCommandsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                VisibleCommandsListClick(event);
            }
        });
        this.VisibleCommandsList.setBounds(0, 257, 548, 145);
        
        this.TranscriptEdit = new JTextArea(10, 10);
        this.TranscriptEdit.setBounds(-24, 8, 572, 248);
        //  --------------- UNHANDLED ATTRIBUTE: this.TranscriptEdit.OnChange = SynEdit1Change;
        //  --------------- UNHANDLED ATTRIBUTE: this.TranscriptEdit.Name = 'TranscriptEdit';
        //  --------------- UNHANDLED ATTRIBUTE: this.TranscriptEdit.Cursor = crIBeam;
        //  --------------- UNHANDLED ATTRIBUTE: this.TranscriptEdit.ScrollBars = ssAutoBoth;
        
        this.PanelConsole = new JPanel(null);
        // -- this.PanelConsole.setLayout(new BoxLayout(this.PanelConsole, BoxLayout.Y_AXIS));
        this.PanelConsole.add(SplitterConsole);
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setBounds(0, 257, 548, 145);;
        scroller1.setViewportView(VisibleCommandsList);
        this.PanelConsole.add(scroller1);
        JScrollPane scroller2 = new JScrollPane();
        scroller2.setBounds(-24, 8, 572, 248);;
        scroller2.setViewportView(TranscriptEdit);
        this.PanelConsole.add(scroller2);
        this.PanelConsole.setBounds(0, 0, 548, 610);
        //  --------------- UNHANDLED ATTRIBUTE: this.PanelConsole.OnResize = PanelConsoleResize;
        
        Image ContextButtonImage = toolkit.createImage("../resources/ConsoleForm_ContextButton.png");
        this.ContextButton = new SpeedButton(new ImageIcon(ContextButtonImage));
        this.ContextButton.setSelected(true);
        this.ContextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                VariableButtonClick(event);
            }
        });
        this.ContextButton.setBounds(8, 40, 24, 24);
        this.ContextButton.setToolTipText("Include any variable used as context");
        //  --------------- UNHANDLED ATTRIBUTE: this.ContextButton.GroupIndex = 2;
        
        Image MoveButtonImage = toolkit.createImage("../resources/ConsoleForm_MoveButton.png");
        this.MoveButton = new SpeedButton(new ImageIcon(MoveButtonImage));
        this.MoveButton.setSelected(true);
        this.MoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                VariableButtonClick(event);
            }
        });
        this.MoveButton.setBounds(40, 40, 24, 24);
        //  --------------- UNHANDLED ATTRIBUTE: this.MoveButton.Layout = blGlyphRight;
        this.MoveButton.setToolTipText("Include any variable used as move");
        //  --------------- UNHANDLED ATTRIBUTE: this.MoveButton.GroupIndex = 3;
        
        Image RequirementsButtonImage = toolkit.createImage("../resources/ConsoleForm_RequirementsButton.png");
        this.RequirementsButton = new SpeedButton(new ImageIcon(RequirementsButtonImage));
        this.RequirementsButton.setSelected(true);
        this.RequirementsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                VariableButtonClick(event);
            }
        });
        this.RequirementsButton.setBounds(72, 40, 24, 24);
        this.RequirementsButton.setToolTipText("Include any variable used in requirements");
        //  --------------- UNHANDLED ATTRIBUTE: this.RequirementsButton.GroupIndex = 4;
        
        Image ChangesButtonImage = toolkit.createImage("../resources/ConsoleForm_ChangesButton.png");
        this.ChangesButton = new SpeedButton(new ImageIcon(ChangesButtonImage));
        this.ChangesButton.setSelected(true);
        this.ChangesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                VariableButtonClick(event);
            }
        });
        this.ChangesButton.setBounds(104, 40, 24, 24);
        //  --------------- UNHANDLED ATTRIBUTE: this.ChangesButton.Layout = blGlyphRight;
        this.ChangesButton.setToolTipText("Include any variable used in changes");
        //  --------------- UNHANDLED ATTRIBUTE: this.ChangesButton.GroupIndex = 5;
        
        Image CommandButtonImage = toolkit.createImage("../resources/ConsoleForm_CommandButton.png");
        this.CommandButton = new SpeedButton(new ImageIcon(CommandButtonImage));
        this.CommandButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                VariableButtonClick(event);
            }
        });
        this.CommandButton.setBounds(138, 40, 24, 24);
        this.CommandButton.setToolTipText("Include any variable used as command");
        //  --------------- UNHANDLED ATTRIBUTE: this.CommandButton.GroupIndex = 6;
        
        Image ShowOnlyTrueVariablesButtonImage = toolkit.createImage("../resources/ConsoleForm_ShowOnlyTrueVariablesButton.png");
        this.ShowOnlyTrueVariablesButton = new SpeedButton(new ImageIcon(ShowOnlyTrueVariablesButtonImage));
        this.ShowOnlyTrueVariablesButton.setSelected(true);
        this.ShowOnlyTrueVariablesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                VariableButtonClick(event);
            }
        });
        this.ShowOnlyTrueVariablesButton.setBounds(169, 40, 24, 24);
        this.ShowOnlyTrueVariablesButton.setToolTipText("Show only variables that are true");
        //  --------------- UNHANDLED ATTRIBUTE: this.ShowOnlyTrueVariablesButton.GroupIndex = 1;
        
        this.FocusComboBox = new JComboBox(new DefaultComboBoxModel());
        this.FocusComboBox.setBounds(10, 8, 183, 25);
        //  --------------- UNHANDLED ATTRIBUTE: this.FocusComboBox.Text = 'FocusComboBox';
        //  --------------- UNHANDLED ATTRIBUTE: this.FocusComboBox.MaxLength = 0;
        
        this.VariablesControlPanel = new JPanel(null);
        // -- this.VariablesControlPanel.setLayout(new BoxLayout(this.VariablesControlPanel, BoxLayout.Y_AXIS));
        this.VariablesControlPanel.add(ContextButton);
        this.VariablesControlPanel.add(MoveButton);
        this.VariablesControlPanel.add(RequirementsButton);
        this.VariablesControlPanel.add(ChangesButton);
        this.VariablesControlPanel.add(CommandButton);
        this.VariablesControlPanel.add(ShowOnlyTrueVariablesButton);
        this.VariablesControlPanel.add(FocusComboBox);
        this.VariablesControlPanel.setBounds(0, 0, 207, 73);
        //  --------------- UNHANDLED ATTRIBUTE: this.VariablesControlPanel.OnResize = VariablesControlPanelResize;
        
        this.VariablesListBox = new JList(new DefaultListModel());
        this.VariablesListBox.setFixedCellHeight(16);
        this.VariablesListBox.setBounds(0, 73, 207, 537);
        //  --------------- UNHANDLED ATTRIBUTE: this.VariablesListBox.Style = lbOwnerDrawFixed;
        this.VariablesListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                VariablesListBoxMouseUp(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.VariablesListBox.Sorted = True;
        //  --------------- UNHANDLED ATTRIBUTE: this.VariablesListBox.OnDrawItem = VariablesListBoxDrawItem;
        
        Image contextPictureImage = toolkit.createImage("../resources/ConsoleForm_contextPicture.png");
        this.contextPicture = new ImagePanel(new ImageIcon(contextPictureImage));
        this.contextPicture.setBounds(4, 4, 16, 16);
        
        Image contextSymbolImage = toolkit.createImage("../resources/ConsoleForm_contextSymbol.png");
        this.contextSymbol = new ImagePanel(new ImageIcon(contextSymbolImage));
        this.contextSymbol.setBounds(4, 24, 16, 16);
        
        Image commandPictureImage = toolkit.createImage("../resources/ConsoleForm_commandPicture.png");
        this.commandPicture = new ImagePanel(new ImageIcon(commandPictureImage));
        this.commandPicture.setBounds(24, 4, 16, 16);
        
        Image commandSymbolImage = toolkit.createImage("../resources/ConsoleForm_commandSymbol.png");
        this.commandSymbol = new ImagePanel(new ImageIcon(commandSymbolImage));
        this.commandSymbol.setBounds(24, 24, 16, 16);
        
        Image movePictureImage = toolkit.createImage("../resources/ConsoleForm_movePicture.png");
        this.movePicture = new ImagePanel(new ImageIcon(movePictureImage));
        this.movePicture.setBounds(44, 4, 16, 16);
        
        Image moveSymbolImage = toolkit.createImage("../resources/ConsoleForm_moveSymbol.png");
        this.moveSymbol = new ImagePanel(new ImageIcon(moveSymbolImage));
        this.moveSymbol.setBounds(44, 24, 16, 16);
        
        Image requirementsPictureImage = toolkit.createImage("../resources/ConsoleForm_requirementsPicture.png");
        this.requirementsPicture = new ImagePanel(new ImageIcon(requirementsPictureImage));
        this.requirementsPicture.setBounds(64, 4, 16, 16);
        
        Image requirementsSymbolImage = toolkit.createImage("../resources/ConsoleForm_requirementsSymbol.png");
        this.requirementsSymbol = new ImagePanel(new ImageIcon(requirementsSymbolImage));
        this.requirementsSymbol.setBounds(64, 24, 16, 16);
        
        Image changesPictureImage = toolkit.createImage("../resources/ConsoleForm_changesPicture.png");
        this.changesPicture = new ImagePanel(new ImageIcon(changesPictureImage));
        this.changesPicture.setBounds(84, 4, 16, 16);
        
        Image changesSymbolImage = toolkit.createImage("../resources/ConsoleForm_changesSymbol.png");
        this.changesSymbol = new ImagePanel(new ImageIcon(changesSymbolImage));
        this.changesSymbol.setBounds(84, 24, 16, 16);
        
        Image plusPictureImage = toolkit.createImage("../resources/ConsoleForm_plusPicture.png");
        this.plusPicture = new ImagePanel(new ImageIcon(plusPictureImage));
        this.plusPicture.setBounds(104, 4, 16, 16);
        
        Image plusSymbolImage = toolkit.createImage("../resources/ConsoleForm_plusSymbol.png");
        this.plusSymbol = new ImagePanel(new ImageIcon(plusSymbolImage));
        this.plusSymbol.setBounds(104, 24, 16, 16);
        
        Image replyPictureImage = toolkit.createImage("../resources/ConsoleForm_replyPicture.png");
        this.replyPicture = new ImagePanel(new ImageIcon(replyPictureImage));
        this.replyPicture.setBounds(124, 4, 16, 16);
        
        Image replySymbolImage = toolkit.createImage("../resources/ConsoleForm_replySymbol.png");
        this.replySymbol = new ImagePanel(new ImageIcon(replySymbolImage));
        this.replySymbol.setBounds(124, 24, 16, 16);
        
        this.picturesAndSymbolsPanel = new JPanel(null);
        // -- this.picturesAndSymbolsPanel.setLayout(new BoxLayout(this.picturesAndSymbolsPanel, BoxLayout.Y_AXIS));
        this.picturesAndSymbolsPanel.add(contextPicture);
        this.picturesAndSymbolsPanel.add(contextSymbol);
        this.picturesAndSymbolsPanel.add(commandPicture);
        this.picturesAndSymbolsPanel.add(commandSymbol);
        this.picturesAndSymbolsPanel.add(movePicture);
        this.picturesAndSymbolsPanel.add(moveSymbol);
        this.picturesAndSymbolsPanel.add(requirementsPicture);
        this.picturesAndSymbolsPanel.add(requirementsSymbol);
        this.picturesAndSymbolsPanel.add(changesPicture);
        this.picturesAndSymbolsPanel.add(changesSymbol);
        this.picturesAndSymbolsPanel.add(plusPicture);
        this.picturesAndSymbolsPanel.add(plusSymbol);
        this.picturesAndSymbolsPanel.add(replyPicture);
        this.picturesAndSymbolsPanel.add(replySymbol);
        this.picturesAndSymbolsPanel.setBounds(22, 248, 155, 57);
        this.picturesAndSymbolsPanel.setVisible(false);
        this.picturesAndSymbolsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                picturesAndSymbolsPanelClick(event);
            }
        });
        
        this.PanelVariables = new JPanel(null);
        // -- this.PanelVariables.setLayout(new BoxLayout(this.PanelVariables, BoxLayout.Y_AXIS));
        this.PanelVariables.add(VariablesControlPanel);
        JScrollPane scroller3 = new JScrollPane();
        scroller3.setBounds(0, 73, 207, 537);;
        scroller3.setViewportView(VariablesListBox);
        this.PanelVariables.add(scroller3);
        this.PanelVariables.add(picturesAndSymbolsPanel);
        this.PanelVariables.setBounds(550, 0, 207, 610);
        //  --------------- UNHANDLED ATTRIBUTE: this.PanelVariables.OnResize = PanelVariablesResize;
        this.PanelVariables.setVisible(false);
        
        //  ------- UNHANDLED TYPE TFontDialog: FontDialog 
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Title = 'Select a font';
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.top = 96;
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Font.Name = 'MS Sans Serif';
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Font.Height = -11;
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.left = 258;
        
        delphiPanel.add(SplitterVariables);
        delphiPanel.add(StatusBar);
        delphiPanel.add(PanelConsole);
        delphiPanel.add(PanelVariables);
        return delphiPanel;
    }
    
        
    public void FormKeyDown(KeyEvent event) {
        System.out.println("FormKeyDown");
    }
        
    public void MediaPath1Click(ActionEvent event) {
        System.out.println("MediaPath1Click");
    }
        
    public void MenuEditCopyClick(ActionEvent event) {
        System.out.println("MenuEditCopyClick");
    }
        
    public void MenuEditRedoClick(ActionEvent event) {
        System.out.println("MenuEditRedoClick");
    }
        
    public void MenuEditRepeatLastClick(ActionEvent event) {
        System.out.println("MenuEditRepeatLastClick");
    }
        
    public void MenuEditStoryClick(ActionEvent event) {
        System.out.println("MenuEditStoryClick");
    }
        
    public void MenuEditUndoClick(ActionEvent event) {
        System.out.println("MenuEditUndoClick");
    }
        
    public void MenuFileExitClick(ActionEvent event) {
        System.out.println("MenuFileExitClick");
    }
        
    public void MenuFileNewSessionClick(ActionEvent event) {
        System.out.println("MenuFileNewSessionClick");
    }
        
    public void MenuFileOpenPictureWindowClick(ActionEvent event) {
        System.out.println("MenuFileOpenPictureWindowClick");
    }
        
    public void MenuFileOpenSessionClick(ActionEvent event) {
        System.out.println("MenuFileOpenSessionClick");
    }
        
    public void MenuFileOpenWorldClick(ActionEvent event) {
        System.out.println("MenuFileOpenWorldClick");
    }
        
    public void MenuFileSaveSessionAsClick(ActionEvent event) {
        System.out.println("MenuFileSaveSessionAsClick");
    }
        
    public void MenuFileSaveSessionClick(ActionEvent event) {
        System.out.println("MenuFileSaveSessionClick");
    }
        
    public void MenuHelpAboutClick(ActionEvent event) {
        System.out.println("MenuHelpAboutClick");
    }
        
    public void MenuHelpAgentClick(ActionEvent event) {
        System.out.println("MenuHelpAgentClick");
    }
        
    public void MenuHelpContentsClick(ActionEvent event) {
        System.out.println("MenuHelpContentsClick");
    }
        
    public void MenuHelpPlayingStoriesClick(ActionEvent event) {
        System.out.println("MenuHelpPlayingStoriesClick");
    }
        
    public void MenuHelpRegisterClick(ActionEvent event) {
        System.out.println("MenuHelpRegisterClick");
    }
        
    public void MenuOptionsPlayMusicClick(ActionEvent event) {
        System.out.println("MenuOptionsPlayMusicClick");
    }
        
    public void MenuOptionsShowVariablesClick(ActionEvent event) {
        System.out.println("MenuOptionsShowVariablesClick");
    }
        
    public void MenuOptionsSoundsClick(ActionEvent event) {
        System.out.println("MenuOptionsSoundsClick");
    }
        
    public void MenuOptionsSpeakClick(ActionEvent event) {
        System.out.println("MenuOptionsSpeakClick");
    }
        
    public void MenuOptionsUpdateEditorSelectionsClick(ActionEvent event) {
        System.out.println("MenuOptionsUpdateEditorSelectionsClick");
    }
        
    public void MenuOptionsVoiceRedoClick(ActionEvent event) {
        System.out.println("MenuOptionsVoiceRedoClick");
    }
        
    public void MenuOptionsVoiceUndoClick(ActionEvent event) {
        System.out.println("MenuOptionsVoiceUndoClick");
    }
        
    public void MenuSettingsCharacterClick(ActionEvent event) {
        System.out.println("MenuSettingsCharacterClick");
    }
        
    public void MenuSettingsPlayerFontClick(ActionEvent event) {
        System.out.println("MenuSettingsPlayerFontClick");
    }
        
    public void MenuSettingsSayOptionsAfterLookClick(ActionEvent event) {
        System.out.println("MenuSettingsSayOptionsAfterLookClick");
    }
        
    public void MenuSettingsShowPicturesClick(ActionEvent event) {
        System.out.println("MenuSettingsShowPicturesClick");
    }
        
    public void MenuSettingsShowTranscriptClick(ActionEvent event) {
        System.out.println("MenuSettingsShowTranscriptClick");
    }
        
    public void VariableButtonClick(ActionEvent event) {
        System.out.println("VariableButtonClick");
    }
        
    public void VariablesListBoxMouseUp(MouseEvent event) {
        System.out.println("VariablesListBoxMouseUp");
    }
        
    public void VisibleCommandsListClick(MouseEvent event) {
        System.out.println("VisibleCommandsListClick");
    }
        
    public void picturesAndSymbolsPanelClick(MouseEvent event) {
        System.out.println("picturesAndSymbolsPanelClick");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ConsoleWindow thisClass = new ConsoleWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

}
