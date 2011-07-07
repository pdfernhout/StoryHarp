package com.kurtz_fernhout.storyharp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Segment;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.kurtz_fernhout.storyharp.KfCommand.CommandChangedListener;
import com.kurtz_fernhout.storyharp.KfCommand.KfCommandChangeType;
import com.kurtz_fernhout.storyharp.TSVariable.TSVariableState;
import com.l2fprod.common.swing.JFontChooser;

public class TConsoleWindow extends JFrame implements CommandChangedListener {
	// const
	public static final int kIconSize = 16;

    private static final long serialVersionUID = 1L;
    JSplitPane SplitterVariables;
    JPanel StatusBar;
    JPanel PanelConsole;
    JSplitPane SplitterConsole;
    DefaultListModel visibleCommandsListModel = new DefaultListModel();
    JList VisibleCommandsList;
    JTextPane TranscriptEdit;
    JPanel PanelVariables;
    JPanel VariablesControlPanel;
    SpeedButton ContextButton;
    SpeedButton MoveButton;
    SpeedButton RequirementsButton;
    SpeedButton ChangesButton;
    SpeedButton CommandButton;
    SpeedButton ShowOnlyTrueVariablesButton;
    DefaultComboBoxModel focusComboBoxModel = new DefaultComboBoxModel();
    JComboBox FocusComboBox;
    DefaultListModel variablesListModel = new DefaultListModel();
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
    JMenuItem MenuHelpRegisterLine;
    JMenuItem MenuHelpRegister;
    JMenuItem AfterRegisterMenuSeparator;
    JMenuItem MenuHelpAbout;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
    
	private JPanel ButtonPanel;
	private JLabel StatusBarLabel1;
	private JLabel StatusBarLabel2;
	private JPanel StatusBarLabel1Panel;
	private JPanel StatusBarLabel2Panel;
    
    public boolean lastSaveProceeded;
    public boolean debugWorldLoaded;
    public boolean registered;
    public TSSpeechSystem speechSystem = new TSSpeechSystem();
    public boolean locationCacheValid;
    public Rectangle contextRect = new Rectangle();
    public Rectangle requirementsRect = new Rectangle();
    public Rectangle commandRect = new Rectangle();
    public Rectangle moveRect = new Rectangle();
    public Rectangle changesRect = new Rectangle();
    public boolean isOnlyPlayer;
    public boolean openedEditorThisSession;

	private JScrollPane TranscriptScroller;
    
    public TConsoleWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
        // for testing; so application connects to this new window; required in update views
        Application.ConsoleForm = this;
        this.FormCreate();
        Font transcriptFont = new Font(TSDomain.domain.options.playerFontName, Font.PLAIN, TSDomain.domain.options.playerFontSize);
        this.TranscriptEdit.setFont(transcriptFont);
        //PDF PORT FIX -- cannot do this until the window is open and can have a graphics context: this.VariablesListBox.setFixedCellHeight((int) Math.max(Math.round(transcriptFont.getStringBounds("W", ((Graphics2D)this.VariablesListBox.getGraphics()).getFontRenderContext()).getWidth()) + 2, 16));
        this.VisibleCommandsList.setFont(transcriptFont);

    }
 
    public void FormCreate() {
    	// PDF PORT FIX: Review these items
        //Application.helpFile = UFileSupport.getExePathAndSeparator() + "StoryHarp.hlp";
        //Application.OnShowHint = this.DoShowHint;
        //Application.ShowHint = true;
        this.isOnlyPlayer = false;
        //this.speechSystem = usspeech.TSSpeechSystem().create();
        TSDomain.domain.sessionCommandList.commandChangedListener = this;
        //Application.setFormSize(this, TSDomain.domain.options.consoleWindowRect);
        this.updateForChangeToDomainOptions();
        //if (TSDomain.domain.options.consoleBottomHeight > 0) {
        //    this.VisibleCommandsList.Height = TSDomain.domain.options.consoleBottomHeight;
        //    this.VisibleCommandsList.y = this.PanelConsole.ClientHeight - this.VisibleCommandsList.Height;
        //}
        //if ((TSDomain.domain.options.consoleRightWidth > 0)) {
        //    // if PanelVariables.visible then
        //    //   begin
       //     this.PanelVariables.Width = TSDomain.domain.options.consoleRightWidth;
        //    this.PanelVariables.x = this.ClientWidth - this.PanelVariables.Width;
        //}
        //   end;
        this.updateViews();
        this.updateForShowingTranscript();
    }
    
    public JMenuBar getMainMenu() {
        if (MainMenu != null) return MainMenu;
        this.MainMenu = new JMenuBar();
        this.MenuFile = new JMenu("Session");
        this.MenuFile.setMnemonic('S');
        this.MainMenu.add(MenuFile);
        this.MenuFileOpenWorld = new JMenuItem("Open World...");
        this.MenuFileOpenWorld.setMnemonic(KeyEvent.VK_W);
        MenuFileOpenWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileOpenWorldClick(event);
            }
        });
        this.MenuFile.add(MenuFileOpenWorld);
        
        this.MenuFileOpenSession = new JMenuItem("Open Saved Session...");
        this.MenuFileOpenSession.setMnemonic(KeyEvent.VK_O);
        MenuFileOpenSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileOpenSessionClick(event);
            }
        });
        this.MenuFile.add(MenuFileOpenSession);
        
        this.MenuFileNewSession = new JMenuItem("Restart");
        this.MenuFileNewSession.setMnemonic(KeyEvent.VK_R);
        MenuFileNewSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileNewSessionClick(event);
            }
        });
        this.MenuFile.add(MenuFileNewSession);
        
        this.MenuFile.addSeparator();
        
        this.MenuFileOpenPictureWindow = new JMenuItem("Open Picture Window");
        this.MenuFileOpenPictureWindow.setMnemonic(KeyEvent.VK_P);
        MenuFileOpenPictureWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileOpenPictureWindowClick(event);
            }
        });
        this.MenuFile.add(MenuFileOpenPictureWindow);
        
        this.MenuFile.addSeparator();
        
        this.MenuFileSaveSession = new JMenuItem("Save");
        this.MenuFileSaveSession.setMnemonic(KeyEvent.VK_S);
        MenuFileSaveSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileSaveSessionClick(event);
            }
        });
        this.MenuFile.add(MenuFileSaveSession);
        
        this.MenuFileSaveSessionAs = new JMenuItem("Save As...");
        this.MenuFileSaveSessionAs.setMnemonic(KeyEvent.VK_A);
        MenuFileSaveSessionAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileSaveSessionAsClick(event);
            }
        });
        this.MenuFile.add(MenuFileSaveSessionAs);
        
        this.MenuFile.addSeparator();
        
        this.MenuFileExit = new JMenuItem("Exit");
        this.MenuFileExit.setMnemonic(KeyEvent.VK_X);
        MenuFileExit.addActionListener(new java.awt.event.ActionListener() {
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
        MenuEditUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditUndoClick(event);
            }
        });
        this.MenuEdit.add(MenuEditUndo);
        this.MenuEditUndo.setEnabled(false);
        
        this.MenuEditRedo = new JMenuItem("Redo");
        this.MenuEditRedo.setMnemonic(KeyEvent.VK_R);
        MenuEditRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditRedoClick(event);
            }
        });
        this.MenuEdit.add(MenuEditRedo);
        this.MenuEditRedo.setEnabled(false);
        
        this.MenuEdit.addSeparator();
        
        this.MenuEditCopy = new JMenuItem("Copy");
        this.MenuEditCopy.setMnemonic(KeyEvent.VK_C);
        MenuEditCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditCopyClick(event);
            }
        });
        this.MenuEdit.add(MenuEditCopy);
        
        this.MenuEdit.addSeparator();
        
        this.MenuEditRepeatLast = new JMenuItem("Repeat Last");
        this.MenuEditRepeatLast.setMnemonic(KeyEvent.VK_P);
        MenuEditRepeatLast.addActionListener(new java.awt.event.ActionListener() {
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
        MenuOptionsSpeak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsSpeakClick(event);
            }
        });
        this.MenuOptions.add(MenuOptionsSpeak);
        this.MenuOptionsSpeak.setSelected(false);
        
        this.MenuOptionsSounds = new JCheckBoxMenuItem("Play Sounds");
        this.MenuOptionsSounds.setMnemonic(KeyEvent.VK_N);
        MenuOptionsSounds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsSoundsClick(event);
            }
        });
        this.MenuOptions.add(MenuOptionsSounds);
        this.MenuOptionsSounds.setSelected(false);
        
        this.MenuOptionsPlayMusic = new JCheckBoxMenuItem("Play Music");
        this.MenuOptionsPlayMusic.setMnemonic(KeyEvent.VK_M);
        MenuOptionsPlayMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsPlayMusicClick(event);
            }
        });
        this.MenuOptions.add(MenuOptionsPlayMusic);
        this.MenuOptionsPlayMusic.setSelected(false);
        
        this.MenuSettingsShowTranscript = new JCheckBoxMenuItem("Show Transcript");
        this.MenuSettingsShowTranscript.setMnemonic(KeyEvent.VK_T);
        MenuSettingsShowTranscript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuSettingsShowTranscriptClick(event);
            }
        });
        this.MenuOptions.add(MenuSettingsShowTranscript);
        this.MenuSettingsShowTranscript.setSelected(false);
        
        this.MenuSettingsShowPictures = new JCheckBoxMenuItem("Show Pictures");
        this.MenuSettingsShowPictures.setMnemonic(KeyEvent.VK_P);
        MenuSettingsShowPictures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuSettingsShowPicturesClick(event);
            }
        });
        this.MenuOptions.add(MenuSettingsShowPictures);
        this.MenuSettingsShowPictures.setSelected(false);
        
        this.MenuOptions.addSeparator();
        
        this.MediaPath1 = new JMenuItem("Extra Sound && Music Directory...");
        this.MediaPath1.setMnemonic(KeyEvent.VK_D);
        MediaPath1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MediaPath1Click(event);
            }
        });
        this.MenuOptions.add(MediaPath1);
        
        this.MenuSettingsPlayerFont = new JMenuItem("Player Font...");
        this.MenuSettingsPlayerFont.setMnemonic(KeyEvent.VK_F);
        MenuSettingsPlayerFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuSettingsPlayerFontClick(event);
            }
        });
        this.MenuOptions.add(MenuSettingsPlayerFont);
        
        this.MenuOptions.addSeparator();
        
        this.MenuSettingsSayOptionsAfterLook = new JCheckBoxMenuItem("Say Options After \"Look'\"");
        this.MenuSettingsSayOptionsAfterLook.setMnemonic(KeyEvent.VK_O);
        MenuSettingsSayOptionsAfterLook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuSettingsSayOptionsAfterLookClick(event);
            }
        });
        this.MenuOptions.add(MenuSettingsSayOptionsAfterLook);
        this.MenuSettingsSayOptionsAfterLook.setSelected(true);
        
        this.MenuOptionsVoiceUndo = new JCheckBoxMenuItem("Use Voice to Undo");
        this.MenuOptionsVoiceUndo.setMnemonic(KeyEvent.VK_U);
        MenuOptionsVoiceUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsVoiceUndoClick(event);
            }
        });
        this.MenuOptions.add(MenuOptionsVoiceUndo);
        
        this.MenuOptionsVoiceRedo = new JCheckBoxMenuItem("Use Voice to Redo");
        this.MenuOptionsVoiceRedo.setMnemonic(KeyEvent.VK_R);
        MenuOptionsVoiceRedo.addActionListener(new java.awt.event.ActionListener() {
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
        MenuEditStory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditStoryClick(event);
            }
        });
        this.MenuDevelopment.add(MenuEditStory);
        
        this.MenuDevelopment.addSeparator();
        
        this.MenuOptionsShowVariables = new JCheckBoxMenuItem("Show Variables");
        this.MenuOptionsShowVariables.setMnemonic(KeyEvent.VK_V);
        MenuOptionsShowVariables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsShowVariablesClick(event);
            }
        });
        this.MenuDevelopment.add(MenuOptionsShowVariables);
        this.MenuOptionsShowVariables.setSelected(true);
        
        this.MenuOptionsUpdateEditorSelections = new JCheckBoxMenuItem("Update Editor Selection After Command Done");
        this.MenuOptionsUpdateEditorSelections.setMnemonic(KeyEvent.VK_U);
        MenuOptionsUpdateEditorSelections.addActionListener(new java.awt.event.ActionListener() {
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
        MenuHelpContents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpContentsClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpContents);
        
        this.MenuHelpPlayingStories = new JMenuItem("Playing Stories");
        this.MenuHelpPlayingStories.setMnemonic(KeyEvent.VK_P);
        MenuHelpPlayingStories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpPlayingStoriesClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpPlayingStories);
        
        this.MenuHelp.addSeparator();
        
        this.MenuHelpRegister = new JMenuItem("Register...");
        this.MenuHelpRegister.setMnemonic(KeyEvent.VK_R);
        MenuHelpRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpRegisterClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpRegister);
        
        this.MenuHelp.addSeparator();
        
        this.MenuHelpAbout = new JMenuItem("About...");
        this.MenuHelpAbout.setMnemonic(KeyEvent.VK_A);
        MenuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpAboutClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpAbout);
        
        return MainMenu;
    }
        
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
        delphiPanel.setLayout(new BorderLayout());

        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent event) {
                FormKeyDown(event);
            }
        });

        //  --------------- UNHANDLED ATTRIBUTE: this.ActiveControl = VisibleCommandsList;

        this.setVisible(false);
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        
        
        this.SplitterVariables = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterVariables.ResizeAnchor = akRight;
        SplitterVariables.setDividerLocation(400);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterVariables.MinSize = 60;
        
        this.StatusBar = new JPanel();
        this.StatusBar.setLayout(new BoxLayout(StatusBar, BoxLayout.X_AXIS));
        
        //  --------------- UNHANDLED ATTRIBUTE: this.StatusBar.SimplePanel = False;
        this.StatusBar.setVisible(true);
        StatusBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        this.StatusBarLabel1Panel = new JPanel();
        StatusBarLabel1Panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        this.StatusBarLabel1 = new JLabel("Running");
        //this.StatusBarLabel1Panel.setPreferredSize(new Dimension(90, 20));
        this.StatusBarLabel1Panel.add(StatusBarLabel1);
        
        this.StatusBarLabel2Panel = new JPanel();
        StatusBarLabel2Panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        this.StatusBarLabel2 = new JLabel("Unregistered");
        //this.StatusBarLabel2Panel.setPreferredSize(new Dimension(50, 20));
        this.StatusBarLabel2Panel.add(StatusBarLabel2);
        
        this.StatusBar.add(StatusBarLabel1Panel);
        this.StatusBar.add(StatusBarLabel2Panel);

        this.SplitterConsole = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterConsole.MinSize = 60;
        SplitterConsole.setDividerLocation(120);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterConsole.ResizeAnchor = akBottom;
        
        this.VisibleCommandsList = new JList(visibleCommandsListModel);
        //this.VisibleCommandsList.setFixedCellHeight(14);
        VisibleCommandsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                VisibleCommandsListClick(event);
            }
        });
        
        this.TranscriptEdit = new JTextPane();
        //  --------------- UNHANDLED ATTRIBUTE: this.TranscriptEdit.OnChange = SynEdit1Change;
        //TranscriptEdit.setLineWrap(true);
        //TranscriptEdit.setWrapStyleWord(true);
        //  --------------- UNHANDLED ATTRIBUTE: this.TranscriptEdit.Name = 'TranscriptEdit';
        //  --------------- UNHANDLED ATTRIBUTE: this.TranscriptEdit.Cursor = crIBeam;
        //  --------------- UNHANDLED ATTRIBUTE: this.TranscriptEdit.ScrollBars = ssAutoBoth;
        
        this.PanelConsole = new JPanel(new BorderLayout());
        this.PanelConsole.add(SplitterConsole, BorderLayout.CENTER);
        SplitterVariables.setLeftComponent(PanelConsole);
        JScrollPane scroller1 = new JScrollPane();

        scroller1.setViewportView(VisibleCommandsList);
        SplitterConsole.setBottomComponent(scroller1);
        this.TranscriptScroller = new JScrollPane();

	    TranscriptScroller.setViewportView(TranscriptEdit);
        SplitterConsole.setTopComponent(TranscriptScroller);
        
        Image ContextButtonImage = toolkit.createImage("resources/ConsoleForm_ContextButton.png");
        this.ContextButton = new SpeedButton(new ImageIcon(ContextButtonImage));
        this.ContextButton.setSelected(true);
        ContextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                VariableButtonClick(event);
            }
        });
        this.ContextButton.setToolTipText("Include any variable used as context");
        //  --------------- UNHANDLED ATTRIBUTE: this.ContextButton.GroupIndex = 2;
        
        Image MoveButtonImage = toolkit.createImage("resources/ConsoleForm_MoveButton.png");
        this.MoveButton = new SpeedButton(new ImageIcon(MoveButtonImage));
        this.MoveButton.setSelected(true);
        MoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                VariableButtonClick(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.MoveButton.Layout = blGlyphRight;
        this.MoveButton.setToolTipText("Include any variable used as move");
        //  --------------- UNHANDLED ATTRIBUTE: this.MoveButton.GroupIndex = 3;
        
        Image RequirementsButtonImage = toolkit.createImage("resources/ConsoleForm_RequirementsButton.png");
        this.RequirementsButton = new SpeedButton(new ImageIcon(RequirementsButtonImage));
        this.RequirementsButton.setSelected(true);
        RequirementsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                VariableButtonClick(event);
            }
        });
        this.RequirementsButton.setToolTipText("Include any variable used in requirements");
        //  --------------- UNHANDLED ATTRIBUTE: this.RequirementsButton.GroupIndex = 4;
        
        Image ChangesButtonImage = toolkit.createImage("resources/ConsoleForm_ChangesButton.png");
        this.ChangesButton = new SpeedButton(new ImageIcon(ChangesButtonImage));
        this.ChangesButton.setSelected(true);
        ChangesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                VariableButtonClick(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.ChangesButton.Layout = blGlyphRight;
        this.ChangesButton.setToolTipText("Include any variable used in changes");
        //  --------------- UNHANDLED ATTRIBUTE: this.ChangesButton.GroupIndex = 5;
        
        Image CommandButtonImage = toolkit.createImage("resources/ConsoleForm_CommandButton.png");
        this.CommandButton = new SpeedButton(new ImageIcon(CommandButtonImage));
        CommandButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                VariableButtonClick(event);
            }
        });
        this.CommandButton.setToolTipText("Include any variable used as command");
        //  --------------- UNHANDLED ATTRIBUTE: this.CommandButton.GroupIndex = 6;
        
        Image ShowOnlyTrueVariablesButtonImage = toolkit.createImage("resources/ConsoleForm_ShowOnlyTrueVariablesButton.png");
        this.ShowOnlyTrueVariablesButton = new SpeedButton(new ImageIcon(ShowOnlyTrueVariablesButtonImage));
        this.ShowOnlyTrueVariablesButton.setSelected(true);
        ShowOnlyTrueVariablesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                VariableButtonClick(event);
            }
        });
        this.ShowOnlyTrueVariablesButton.setToolTipText("Show only variables that are true");
        //  --------------- UNHANDLED ATTRIBUTE: this.ShowOnlyTrueVariablesButton.GroupIndex = 1;
        
        this.FocusComboBox = new JComboBox(focusComboBoxModel);
        FocusComboBox.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent e) {
        		// PDF PORT FIX -- FocusComboBoxChange(e);
        	}
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.FocusComboBox.setText('FocusComboBox');
        //  --------------- UNHANDLED ATTRIBUTE: this.FocusComboBox.MaxLength = 0;
        
        this.VariablesControlPanel = new JPanel();

        this.VariablesControlPanel.setLayout(new BoxLayout(VariablesControlPanel, BoxLayout.Y_AXIS));
        
        this.ButtonPanel = new JPanel(new FlowLayout());
        this.ButtonPanel.add(ContextButton);
        this.ButtonPanel.add(MoveButton);
        this.ButtonPanel.add(RequirementsButton);
        this.ButtonPanel.add(ChangesButton);
        this.ButtonPanel.add(CommandButton);
        this.ButtonPanel.add(ShowOnlyTrueVariablesButton);
        
        this.VariablesControlPanel.add(FocusComboBox, BorderLayout.CENTER);
        this.VariablesControlPanel.add(ButtonPanel, BorderLayout.SOUTH);

        //  --------------- UNHANDLED ATTRIBUTE: this.VariablesControlPanel.OnResize = VariablesControlPanelResize;
        
        this.VariablesListBox = new JList(variablesListModel);
        this.VariablesListBox.setFixedCellHeight(16);
        this.VariablesListBox.setCellRenderer(new VariableListCellRenderer());
        //  --------------- UNHANDLED ATTRIBUTE: this.VariablesListBox.Style = lbOwnerDrawFixed;
        VariablesListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                VariablesListBoxMouseUp(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.VariablesListBox.Sorted = True;
        //  --------------- UNHANDLED ATTRIBUTE: this.VariablesListBox.OnDrawItem = VariablesListBoxDrawItem;
        
        Image contextPictureImage = toolkit.createImage("resources/ConsoleForm_contextPicture.png");
        this.contextPicture = new ImagePanel(new ImageIcon(contextPictureImage));
        this.contextPicture.setBounds(4, 4, 16, 16);
        
        Image contextSymbolImage = toolkit.createImage("resources/ConsoleForm_contextSymbol.png");
        this.contextSymbol = new ImagePanel(new ImageIcon(contextSymbolImage));
        this.contextSymbol.setBounds(4, 24, 16, 16);
        
        Image commandPictureImage = toolkit.createImage("resources/ConsoleForm_commandPicture.png");
        this.commandPicture = new ImagePanel(new ImageIcon(commandPictureImage));
        this.commandPicture.setBounds(24, 4, 16, 16);
        
        Image commandSymbolImage = toolkit.createImage("resources/ConsoleForm_commandSymbol.png");
        this.commandSymbol = new ImagePanel(new ImageIcon(commandSymbolImage));
        this.commandSymbol.setBounds(24, 24, 16, 16);
        
        Image movePictureImage = toolkit.createImage("resources/ConsoleForm_movePicture.png");
        this.movePicture = new ImagePanel(new ImageIcon(movePictureImage));
        this.movePicture.setBounds(44, 4, 16, 16);
        
        Image moveSymbolImage = toolkit.createImage("resources/ConsoleForm_moveSymbol.png");
        this.moveSymbol = new ImagePanel(new ImageIcon(moveSymbolImage));
        this.moveSymbol.setBounds(44, 24, 16, 16);
        
        Image requirementsPictureImage = toolkit.createImage("resources/ConsoleForm_requirementsPicture.png");
        this.requirementsPicture = new ImagePanel(new ImageIcon(requirementsPictureImage));
        this.requirementsPicture.setBounds(64, 4, 16, 16);
        
        Image requirementsSymbolImage = toolkit.createImage("resources/ConsoleForm_requirementsSymbol.png");
        this.requirementsSymbol = new ImagePanel(new ImageIcon(requirementsSymbolImage));
        this.requirementsSymbol.setBounds(64, 24, 16, 16);
        
        Image changesPictureImage = toolkit.createImage("resources/ConsoleForm_changesPicture.png");
        this.changesPicture = new ImagePanel(new ImageIcon(changesPictureImage));
        this.changesPicture.setBounds(84, 4, 16, 16);
        
        Image changesSymbolImage = toolkit.createImage("resources/ConsoleForm_changesSymbol.png");
        this.changesSymbol = new ImagePanel(new ImageIcon(changesSymbolImage));
        this.changesSymbol.setBounds(84, 24, 16, 16);
        
        Image plusPictureImage = toolkit.createImage("resources/ConsoleForm_plusPicture.png");
        this.plusPicture = new ImagePanel(new ImageIcon(plusPictureImage));
        this.plusPicture.setBounds(104, 4, 16, 16);
        
        Image plusSymbolImage = toolkit.createImage("resources/ConsoleForm_plusSymbol.png");
        this.plusSymbol = new ImagePanel(new ImageIcon(plusSymbolImage));
        this.plusSymbol.setBounds(104, 24, 16, 16);
        
        Image replyPictureImage = toolkit.createImage("resources/ConsoleForm_replyPicture.png");
        this.replyPicture = new ImagePanel(new ImageIcon(replyPictureImage));
        this.replyPicture.setBounds(124, 4, 16, 16);
        
        Image replySymbolImage = toolkit.createImage("resources/ConsoleForm_replySymbol.png");
        this.replySymbol = new ImagePanel(new ImageIcon(replySymbolImage));
        this.replySymbol.setBounds(124, 24, 16, 16);
        
        this.picturesAndSymbolsPanel = new JPanel(null);
        // -- this.picturesAndSymbolsPanel.setLayout(new BoxLayout(picturesAndSymbolsPanel, BoxLayout.Y_AXIS));
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
        this.picturesAndSymbolsPanel.setVisible(false);
        // PDF CHECK: This does not seem to be used
//        picturesAndSymbolsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent event) {
//                picturesAndSymbolsPanelClick(event);
//            }
//        });
        
        this.PanelVariables = new JPanel(new BorderLayout());
        
        this.PanelVariables.add(VariablesControlPanel, BorderLayout.NORTH);
        
        SplitterVariables.setRightComponent(PanelVariables);
        JScrollPane scroller3 = new JScrollPane();
        scroller3.setViewportView(VariablesListBox);
        this.PanelVariables.add(scroller3, BorderLayout.CENTER);
        
        //this.PanelVariables.add(picturesAndSymbolsPanel, BorderLayout.WEST);
        
        this.PanelVariables.setVisible(true);
        
        delphiPanel.add(SplitterVariables, BorderLayout.CENTER);
        delphiPanel.add(StatusBar, BorderLayout.SOUTH);
        return delphiPanel;
    }
    
        
    public void FormKeyDown(KeyEvent event) {
        System.out.println("FormKeyDown");
    }
        
    public void MediaPath1Click(ActionEvent event) {
        Application.ExtraMediaDirectoryWindow.setVisible(true);
    }
        
    public void MenuEditCopyClick(ActionEvent event) {
    	this.TranscriptEdit.copy();
    }
        
    public void MenuEditRedoClick(ActionEvent event) {
    	TSDomain.domain.sessionCommandList.redoLast();
    }
        
    public void MenuEditRepeatLastClick(ActionEvent event) {
        this.speechSystem.haltSpeechAndSound();
        this.speechSystem.sayTextWithMacros(this.speechSystem.lastSaidTextWithMacros);
    }
        
    public void MenuEditStoryClick(ActionEvent event) {
        Application.RuleEditorForm.setVisible(true);
        this.openedEditorThisSession = true;
    }
        
    public void MenuEditUndoClick(ActionEvent event) {
    	TSDomain.domain.sessionCommandList.undoLast();
    }
        
    public void MenuFileExitClick(ActionEvent event) {
        if (!this.askForSaveSessionAndProceed()) {
            return;
        }
        if (!Application.RuleEditorForm.askForSaveWorldAndProceed()) {
            return;
        }
        if (!this.cleanUpBeforeExit()) {
            return;
        }
        Application.Terminate();
    }

    // returns false if user cancels
    public boolean cleanUpBeforeExit() {
        int response = 0;
        
        boolean result = true;
        TSDomain.domain.options.consoleWindowRect = Rect(this.x, this.y, this.Width, this.Height);
        TSDomain.domain.options.consoleBottomHeight = this.VisibleCommandsList.Height;
        TSDomain.domain.options.consoleRightWidth = this.PanelVariables.Width;
        TSDomain.domain.options.editorWindowRect = Rect(Application.RuleEditorForm.x, Application.RuleEditorForm.y, Application.RuleEditorForm.Width, Application.RuleEditorForm.Height);
        TSDomain.domain.options.editorPanelEditorHeight = Application.RuleEditorForm.PanelEditor.Height;
        TSDomain.domain.options.editorPanelRequirementsChangesHeight = Application.RuleEditorForm.PanelRequirementsChanges.Height;
        if (Application.RuleEditorForm.ListPages.getSelectedComponent() == Application.RuleEditorForm.TabSheetTable) {
            TSDomain.domain.options.pageShowing = TSDomain.kPageTable;
        } else if (Application.RuleEditorForm.ListPages.getSelectedComponent() == Application.RuleEditorForm.TabSheetMap) {
            TSDomain.domain.options.pageShowing = TSDomain.kPageMap;
        } else if (Application.RuleEditorForm.ListPages.getSelectedComponent() == Application.RuleEditorForm.TabSheetBrowse) {
            TSDomain.domain.options.pageShowing = TSDomain.kPageBrowser;
        } else {
            // default
            TSDomain.domain.options.pageShowing = TSDomain.kPageTable;
        }
        TSDomain.domain.options.editorPanelFirstListWidth = Application.RuleEditorForm.PanelFirstList.Width;
        TSDomain.domain.options.pictureWindowRect = Rect(Application.PictureForm.x, Application.PictureForm.y, Application.PictureForm.Width, Application.PictureForm.Height);
        TSDomain.domain.options.logFileWindowRect = Rect(Application.ChangeLogForm.x, Application.ChangeLogForm.y, Application.ChangeLogForm.Width, Application.ChangeLogForm.Height);
        if (TSDomain.domain.useIniFile) {
            if (!this.storeIniFile()) {
                return false;
            }
        }
        UNRESOLVED.Randomize();
        for (int i = 0; i <= 30; i++) {
            UNRESOLVED.random();
        }
        if ((!TSDomain.domain.playerOnly) && (!TSDomain.domain.registered) && (TSDomain.domain.accumulatedUnregisteredTime > 4.0 / 24.0) && (UNRESOLVED.random < usdomain.min(TSDomain.domain.accumulatedUnregisteredTime, 0.9))) {
            uabout.UnregisteredAboutForm.initializeWithWhetherClosingProgram(true);
            response = uabout.UnregisteredAboutForm.ShowModal();
            this.updateForRegistrationChange();
            if (response == mrCancel) {
                return false;
            }
        }
        Application.HelpCommand(UNRESOLVED.HELP_QUIT, 0);
        return result;
    }
    

    
    public boolean storeIniFile() {
        boolean fileSavedOK = false;
        boolean choseAnotherFileName = false;
        byte buttonPressed = 0;
        TSaveDialog saveDialog = new TSaveDialog();
        
        boolean result = true;
        fileSavedOK = false;
        choseAnotherFileName = false;
        while (!fileSavedOK) {
            try {
                TSDomain.domain.storeProfileInformation();
                fileSavedOK = true;
            } catch (Exception e) {
                fileSavedOK = false;
            }
            if (!fileSavedOK) {
                buttonPressed = MessageDialog("Could not save settings to:\n\n " + TSDomain.domain.iniFileName + "\n\nWould you like to save them to another file?", delphi_compatability.TMsgDlgType.mtError, mbYesNoCancel, 0);
                switch (buttonPressed) {
                    case delphi_compatability.IDYES:
                        saveDialog = delphi_compatability.TSaveDialog().Create(Application);
                        try {
                            saveDialog.FileName = TSDomain.domain.iniFileName;
                            saveDialog.Filter = "Ini files (*.ini)|*.ini|All files (*.*)|*.*";
                            saveDialog.DefaultExt = "ini";
                            saveDialog.Options = saveDialog.Options; // PDF PORT FIX: + {delphi_compatability.TOpenOption.ofPathMustExist, delphi_compatability.TOpenOption.ofOverwritePrompt, delphi_compatability.TOpenOption.ofHideReadOnly, delphi_compatability.TOpenOption.ofNoReadOnlyReturn, };
                            result = saveDialog.Execute();
                            if (result) {
                                TSDomain.domain.iniFileName = saveDialog.FileName;
                                choseAnotherFileName = true;
                            }
                        } finally {
                            saveDialog.free();
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
        }
        if (fileSavedOK && choseAnotherFileName) {
            UFileSupport.ShowMessage("Your settings have been saved in: \n" + TSDomain.domain.iniFileName + "\nBut StoryHarp will load the original settings file again at startup.\nTo use this settings file at startup, search in the help system\nfor \"alternate settings file\".");
        }
        return result;
    }
           
    public void MenuFileNewSessionClick(ActionEvent event) {
        if (!this.askForSaveSessionAndProceed()) {
            return;
        }
        this.speechSystem.haltSpeechAndSound();
        // turns it off
        this.speechSystem.speakSound("music");
        TSDomain.domain.newSession();
        this.clearTranscript();
        // pdf v 1.2 moved above previous two
        TSDomain.domain.world.updateAvailable();
        this.updateViews();
        this.updateTitles();
        if (TSDomain.domain.world.rules.size() > 0) {
            this.speechSystem.doCommand(TSDomain.domain.world.rules.get(0).command.phrase);
        }
    }
        
    public void MenuFileOpenPictureWindowClick(ActionEvent event) {
    	Application.PictureForm.setVisible(true);
    }
        
    public void MenuFileOpenSessionClick(ActionEvent event) {
        String fileNameWithPath = "";
        
        if (!this.askForSaveSessionAndProceed()) {
            return;
        }
        fileNameWithPath = UFileSupport.getFileOpenInfo(UFileSupport.kFileTypeSession, TSDomain.domain.sessionFileName, "Choose a session file", UFileSupport.kOtherExtNotOK);
        if (fileNameWithPath.equals("")) {
            return;
        }
        this.speechSystem.haltSpeechAndSound();
        // turns it off
        this.speechSystem.speakSound("music");
        this.openSessionOrWorldFile(fileNameWithPath);
        this.updateTitles();
    }
        
    public void MenuFileOpenWorldClick(ActionEvent event) {
        if (!this.askForSaveSessionAndProceed()) {
            return;
        }
        String fileNameWithPath = UFileSupport.getFileOpenInfo(UFileSupport.kFileTypeWorld, TSDomain.domain.sessionFileName, "Choose a world file", UFileSupport.kOtherExtNotOK);
        if (fileNameWithPath.equals("")) {
            return;
        }
        this.speechSystem.haltSpeechAndSound();
        // turns it off
        this.speechSystem.speakSound("music");
        this.openSessionOrWorldFile(fileNameWithPath);
        this.updateTitles();
    }
        
    public void MenuFileSaveSessionClick(ActionEvent event) {
        SaveFileNamesStructure fileInfo = new SaveFileNamesStructure();
        
        if (TSDomain.kUnsavedSessionFileName.equalsIgnoreCase(UFileSupport.ExtractFileName(TSDomain.domain.sessionFileName))) {
            this.MenuFileSaveSessionAsClick(null);
            return;
        }
        this.lastSaveProceeded = UFileSupport.getFileSaveInfo(UFileSupport.kFileTypeSession, UFileSupport.kDontAskForFileName, TSDomain.domain.sessionFileName, fileInfo);
        if (!this.lastSaveProceeded) {
            return;
        }
        try {
            UFileSupport.startFileSave(fileInfo);
            TSDomain.domain.saveSession(fileInfo.tempFile);
            fileInfo.writingWasSuccessful = true;
        } finally {
            this.lastSaveProceeded = UFileSupport.cleanUpAfterFileSave(fileInfo);
        }
        if (this.lastSaveProceeded) {
            TSDomain.domain.sessionChangeCount = 0;
            TSDomain.domain.sessionCommandList.clear();
            TSDomain.domain.options.mostRecentSession = fileInfo.newFile;
            this.updateMenus();
        }
    }
    
    public void MenuFileSaveSessionAsClick(ActionEvent event) {
        SaveFileNamesStructure fileInfo = new SaveFileNamesStructure();
        
        this.lastSaveProceeded = UFileSupport.getFileSaveInfo(UFileSupport.kFileTypeSession, UFileSupport.kAskForFileName, TSDomain.domain.sessionFileName, fileInfo);
        if (!this.lastSaveProceeded) {
            return;
        }
        try {
            UFileSupport.startFileSave(fileInfo);
            TSDomain.domain.saveSession(fileInfo.tempFile);
            fileInfo.writingWasSuccessful = true;
        } finally {
            this.lastSaveProceeded = UFileSupport.cleanUpAfterFileSave(fileInfo);
        }
        if (this.lastSaveProceeded) {
            TSDomain.domain.sessionChangeCount = 0;
            TSDomain.domain.sessionCommandList.clear();
            TSDomain.domain.sessionFileName = fileInfo.newFile;
            TSDomain.domain.options.mostRecentSession = fileInfo.newFile;
            this.updateMenus();
        }
        this.updateTitles();
    }
        
    public void MenuHelpAboutClick(ActionEvent event) {
//        if (TSDomain.domain.registered) {
//            if (usabout.AboutForm == null) {
//                return;
//            }
//            usabout.AboutForm.setUpAsSplashOrAbout(usabout.kAsAbout);
//            usabout.AboutForm.ShowModal();
//        } else {
//            if (uabout.UnregisteredAboutForm == null) {
//                return;
//            }
//            uabout.UnregisteredAboutForm.initializeWithWhetherClosingProgram(false);
//            uabout.UnregisteredAboutForm.ShowModal();
//            this.updateForRegistrationChange();
//        }
    }
        
    public void MenuHelpContentsClick(ActionEvent event) {
        Application.HelpJump("Contents");
    }
        
    public void MenuHelpPlayingStoriesClick(ActionEvent event) {
        Application.HelpJump("Playing_StoryHarp_Audioventures");
    }
        
    public void MenuHelpRegisterClick(ActionEvent event) {
        Application.RegistrationForm.ShowModal();
        this.updateForRegistrationChange();
    }
        
    public void MenuOptionsPlayMusicClick(ActionEvent event) {
        TSDomain.domain.options.playerPlayMusic = !TSDomain.domain.options.playerPlayMusic;
        this.MenuOptionsPlayMusic.setSelected(TSDomain.domain.options.playerPlayMusic);
        if (!TSDomain.domain.options.playerPlayMusic) {
        	this.speechSystem.haltMusic();
        }
    }
        
    public void MenuOptionsShowVariablesClick(ActionEvent event) {
        TSDomain.domain.options.showVariables = !TSDomain.domain.options.showVariables;
        this.MenuOptionsShowVariables.setSelected(TSDomain.domain.options.showVariables);
//        if (TSDomain.domain.options.showVariables) {
//            this.setSize(this.getWidth() + this.PanelVariables.getWidth(), this.getHeight());
//        } else {
//            this.setSize(this.getWidth() - this.PanelVariables.getWidth(), this.getHeight());
//        }
        this.updateForChangeToShowingVariables();
    }
        
    public void updateForChangeToShowingVariables() {
        if (TSDomain.domain.options.showVariables) {
            this.updateVariables();
            this.setSize(this.getWidth() + this.PanelVariables.getWidth(), this.getHeight());
            this.PanelVariables.setVisible(true);
            //this.SplitterVariables.setVisible(true);
            System.out.println("divider: " + this.PanelVariables.getWidth());
            this.SplitterVariables.setDividerLocation(this.getWidth() - Math.max(350, this.PanelVariables.getWidth()));
        } else {
            this.PanelVariables.setVisible(false);
            this.setSize(this.getWidth() - this.PanelVariables.getWidth(), this.getHeight());
            //this.SplitterVariables.setVisible(false);
        }
    }

    public void MenuOptionsSoundsClick(ActionEvent event) {
        TSDomain.domain.options.playerPlaySounds = !TSDomain.domain.options.playerPlaySounds;
        this.MenuOptionsSounds.setSelected(TSDomain.domain.options.playerPlaySounds);
        if (!TSDomain.domain.options.playerPlaySounds) {
            this.speechSystem.haltSound();
        }
    }
        
    public void MenuOptionsSpeakClick(ActionEvent event) {
        TSDomain.domain.options.playerSpeak = !TSDomain.domain.options.playerSpeak;
        this.MenuOptionsSpeak.setSelected(TSDomain.domain.options.playerSpeak);
        if (!TSDomain.domain.options.playerSpeak) {
            this.speechSystem.haltSpeech();
        }
    }
        
    public void MenuOptionsUpdateEditorSelectionsClick(ActionEvent event) {
        TSDomain.domain.options.updateEditorAfterCommandDone = !TSDomain.domain.options.updateEditorAfterCommandDone;
        this.MenuOptionsUpdateEditorSelections.setSelected(TSDomain.domain.options.updateEditorAfterCommandDone);
    }
        
    public void MenuOptionsVoiceRedoClick(ActionEvent event) {
        TSDomain.domain.options.useVoiceToRedo = !TSDomain.domain.options.useVoiceToRedo;
        this.MenuOptionsVoiceRedo.setSelected(TSDomain.domain.options.useVoiceToRedo);
    }
        
    public void MenuOptionsVoiceUndoClick(ActionEvent event) {
        TSDomain.domain.options.useVoiceToUndo = !TSDomain.domain.options.useVoiceToUndo;
        this.MenuOptionsVoiceUndo.setSelected(TSDomain.domain.options.useVoiceToUndo);
    }
        
    public void MenuSettingsPlayerFontClick(ActionEvent event) {
    	Font currentFont = new Font(TSDomain.domain.options.playerFontName, Font.PLAIN, TSDomain.domain.options.playerFontSize);
    	Font newFont = JFontChooser.showDialog(null, "Choose a font for the player", currentFont);
        if (newFont != null) {
            TSDomain.domain.options.playerFontName = newFont.getName();
            TSDomain.domain.options.playerFontSize = newFont.getSize();
            Font transcriptFont = new Font(TSDomain.domain.options.playerFontName, Font.PLAIN, TSDomain.domain.options.playerFontSize);
            this.TranscriptEdit.setFont(transcriptFont);
            // ignoring style
            // make existing text change font 
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setFontFamily(set, TSDomain.domain.options.playerFontName);
            StyleConstants.setFontSize(set, TSDomain.domain.options.playerFontSize);
            StyledDocument document = this.TranscriptEdit.getStyledDocument();
            document.setCharacterAttributes(0, document.getLength(), set, false);
            this.VariablesListBox.setFixedCellHeight((int) Math.max(Math.round(transcriptFont.getStringBounds("W", ((Graphics2D)this.VariablesListBox.getGraphics()).getFontRenderContext()).getWidth()) + 2, 16));
            this.VisibleCommandsList.setFont(transcriptFont);
        }
    }
        
    public void MenuSettingsSayOptionsAfterLookClick(ActionEvent event) {
        TSDomain.domain.options.sayOptionsAfterLook = !TSDomain.domain.options.sayOptionsAfterLook;
        this.MenuSettingsSayOptionsAfterLook.setSelected(TSDomain.domain.options.sayOptionsAfterLook);
    }
        
    public void MenuSettingsShowPicturesClick(ActionEvent event) {
        TSDomain.domain.options.showPictures = !TSDomain.domain.options.showPictures;
        this.MenuSettingsShowPictures.setSelected(TSDomain.domain.options.showPictures);
        if (!TSDomain.domain.options.showPictures) {
            Application.PictureForm.setVisible(false);
        } else {
        	Application.PictureForm.setVisible(true);
        }
    }
        
    public void MenuSettingsShowTranscriptClick(ActionEvent event) {
        TSDomain.domain.options.showTranscript = !TSDomain.domain.options.showTranscript;
        this.updateForShowingTranscript();
        // PDF PORT this.PanelConsoleResize(this);
    }
    
    public void updateForShowingTranscript() {
        this.MenuSettingsShowTranscript.setSelected(TSDomain.domain.options.showTranscript);
        if (TSDomain.domain.options.showTranscript) {
            this.SplitterVariables.setDividerLocation(this.SplitterVariables.getDividerLocation() - this.TranscriptScroller.getHeight());
            this.TranscriptScroller.setVisible(true);
            //this.SplitterConsole.setVisible(true);
            //if (TSDomain.domain.options.consoleBottomHeight > 0) {
            //    this.VisibleCommandsList.Height = TSDomain.domain.options.consoleBottomHeight;
            //    this.VisibleCommandsList.y = this.PanelConsole.ClientHeight - this.VisibleCommandsList.Height;
            //}
        } else {
            this.TranscriptScroller.setVisible(false);
            this.SplitterVariables.setDividerLocation(this.SplitterVariables.getDividerLocation() + this.TranscriptScroller.getHeight());
            //this.SplitterConsole.setVisible(false);
            //this.VisibleCommandsList.y = this.SplitterConsole.Height;
            //this.VisibleCommandsList.Height = this.PanelConsole.ClientHeight - this.SplitterConsole.Height;
        }
    }
    
    public void VariableButtonClick(ActionEvent event) {
    	this.updateVariables();
    }
        
    public void VariablesListBoxMouseUp(MouseEvent event) {
        System.out.println("VariablesListBoxMouseUp");
        
        if (this.VariablesListBox.getSelectedIndex() < 0) {
            return;
        }
        if (this.VariablesListBox.getSelectedIndex() > this.variablesListModel.size() - 1) {
            return;
        }
        TSVariable variable = (TSVariable)this.variablesListModel.get(this.VariablesListBox.getSelectedIndex());
        int x = event.getX();
        if (x < 16) {
            TSDomain.domain.sessionCommandList.toggleVariable(variable);
            return;
        }
        if ((Application.RuleEditorForm == null) || (!Application.RuleEditorForm.isVisible())) {
            return;
        }
        if ((x > this.contextRect.x) && (x <= this.contextRect.x + this.contextRect.width)) {
            if (variable.contextUseages <= 0) {
                return;
            }
            Application.RuleEditorForm.setOrganizeByField(TSVariableDisplayOptions.kRuleContext);
        } else if ((x > this.requirementsRect.x) && (x <= this.requirementsRect.x + this.contextRect.width)) {
            if (variable.requirementsUseages <= 0) {
                return;
            }
            Application.RuleEditorForm.setOrganizeByField(TSVariableDisplayOptions.kRuleRequirements);
        } else if ((x > this.commandRect.x) && (x <= this.commandRect.x + this.contextRect.width)) {
            if (variable.commandUseages <= 0) {
                return;
            }
            Application.RuleEditorForm.setOrganizeByField(TSVariableDisplayOptions.kRuleCommand);
        } else if ((x > this.moveRect.x) && (x <= this.moveRect.x + this.contextRect.width)) {
            if (variable.moveUseages <= 0) {
                return;
            }
            Application.RuleEditorForm.setOrganizeByField(TSVariableDisplayOptions.kRuleMove);
        } else if ((x > this.changesRect.x) && (x <= this.changesRect.x + this.contextRect.width)) {
            if (variable.changesUseages <= 0) {
                return;
            }
            Application.RuleEditorForm.setOrganizeByField(TSVariableDisplayOptions.kRuleChanges);
        } else {
            return;
        }
        Application.RuleEditorForm.ListPages.setSelectedComponent(Application.RuleEditorForm.TabSheetBrowse);
        Application.RuleEditorForm.FirstListBox.setSelectedIndex(Application.RuleEditorForm.FirstListBoxModel.indexOf(variable.phrase));
        Application.RuleEditorForm.loadSecondListBox();
    }
        
    public void VisibleCommandsListClick(MouseEvent event) {
        String theCommand = "";
        
        if (this.VisibleCommandsList.getSelectedIndex() < 0) {
            return;
        }
        theCommand = (String) this.VisibleCommandsList.getSelectedValue();
        this.speechSystem.doCommand(theCommand);
        if (TSDomain.domain.options.updateEditorAfterCommandDone) {
        	Application.RuleEditorForm.trackLastCommand();
        }
    }
        
    public void picturesAndSymbolsPanelClick(MouseEvent event) {
        System.out.println("picturesAndSymbolsPanelClick");
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TConsoleWindow thisClass = new TConsoleWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    // --------------------------- TRANSCRIPT ------------------------------------- 
    public void clearTranscript() {
        this.TranscriptEdit.setText("");
        // is this the right place? cfk
        Application.PictureForm.clearPictures();
    }
    
    public void addLineToTranscript(String text, Color newColor) {
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, newColor);
        StyleConstants.setFontFamily(set, TSDomain.domain.options.playerFontName);
        StyleConstants.setFontSize(set, TSDomain.domain.options.playerFontSize);
        Document document = this.TranscriptEdit.getDocument();
        try {
			document.insertString(document.getLength(), text + "\n", set);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
    }
    
    public void scrollTranscriptEndIntoView() {
    	// suggested at: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4201999
    	// int endPosition = Math.max(0, this.TranscriptEdit.getText().length() - 1);
    	int endPosition = this.TranscriptEdit.getText().length();
    	this.TranscriptEdit.setCaretPosition(endPosition);
    }
    
    public void reportMode(String status) {
    	this.StatusBarLabel1.setText(status);
    }
    
    public void updateForRegistrationChange() {
        boolean unregisteredEditing = false;
        
        unregisteredEditing = (!TSDomain.domain.registered) && (!TSDomain.domain.playerOnly);
        this.MenuHelpRegister.setVisible(unregisteredEditing);
        this.MenuHelpRegisterLine.setVisible(unregisteredEditing);
        // PDF PORT: CHANGE?  this.StatusBar.setVisible(unregisteredEditing);
        if (Application.RuleEditorForm != null) {
            Application.RuleEditorForm.MenuHelpRegister.setVisible(unregisteredEditing);
            Application.RuleEditorForm.AfterRegisterMenuSeparator.setVisible(unregisteredEditing);
        }
        if (TSDomain.domain.registered) {
        	this.StatusBarLabel2.setText("Registered to " + TSDomain.domain.registrationName);
        } else if (!TSDomain.domain.playerOnly) {
        	this.StatusBarLabel2.setText("Unregistered");
        } else {
        	this.StatusBarLabel2.setText("Player-only mode");
        }
    }
    
    public void playerOnly() {
        this.isOnlyPlayer = true;
        Application.RuleEditorForm.setVisible(false);
        this.MenuDevelopment.setVisible(false);
        this.PanelVariables.setVisible(false);
        this.MenuHelpRegister.setVisible(false);
        this.MenuHelpRegisterLine.setVisible(false);
        this.StatusBarLabel2.setText("Player-only mode");
    }
   
    public void updateTitles() {
        this.setTitle("StoryHarp - " + UFileSupport.ExtractFileName(TSDomain.domain.worldFileName) + " (" + UFileSupport.ExtractFileName(TSDomain.domain.sessionFileName) + ")");
        Application.RuleEditorForm.setTitle("StoryHarp World Editor - " + UFileSupport.ExtractFileName(TSDomain.domain.worldFileName));
    }
    
    // will need seperate one for sessions...
    public boolean askForSaveSessionAndProceed() {
        if (!TSDomain.domain.isSessionFileChanged()) {
            return true;
        }
        // PDF PORT FIX: MAYBE NOT NEEDED: Application.BringToFront();
        //cfk fix - put help context in
        boolean messageBoxResult = UFileSupport.Confirm("Save changes to session " + UFileSupport.ExtractFileName(TSDomain.domain.sessionFileName) + "?");
        if (messageBoxResult) {
            this.MenuFileSaveSessionClick(null);
            return this.lastSaveProceeded;
        }
        return true;

    }
    
    public void openSessionOrWorldFile(String fileNameWithPath) {
        String extension = UFileSupport.ExtractFileExt(fileNameWithPath);
        if (extension.toUpperCase().equals("." + TSDomain.kWorldExtension.toUpperCase())) {
            if (!Application.RuleEditorForm.askForSaveWorldAndProceed()) {
                return;
            }
            Application.RuleEditorForm.openWorldFile(fileNameWithPath);
            TSDomain.domain.world.setInitialFocus();
            this.clearTranscript();
            TSDomain.domain.world.updateAvailable();
            // pdf v1.2 moved after updateAvailable
            this.updateViews();
            if (TSDomain.domain.world.rules.size() > 0) {
                this.speechSystem.doCommand(TSDomain.domain.world.rules.get(0).command.phrase);
            }
            return;
        }
        if (extension.toUpperCase() != "." + TSDomain.kSessionExtension.toUpperCase()) {
            UFileSupport.ShowMessage("Unsupported file extension for " + fileNameWithPath + "(" + extension + ").");
            return;
        }
        try {
        	UFileSupport.startWaitMessage("Opening " + UFileSupport.ExtractFileName(fileNameWithPath));
            String oldWorldFileName = TSDomain.domain.worldFileName;
            TSDomain.domain.loadSession(fileNameWithPath);
            String newWorldFileName = TSDomain.domain.worldFileName;
            
            if (!oldWorldFileName.equals(newWorldFileName)) {
            	// the world file was changed to go with the new session; may need to do updating
                Application.RuleEditorForm.updateForWorldFileChanged();
            }
            TSDomain.domain.world.updateAvailable();
        } catch (Exception E) {
        	UFileSupport.stopWaitMessage();
            UFileSupport.ShowMessage(E.getMessage());
            UFileSupport.ShowMessage("Could not load file " + fileNameWithPath);
            TSDomain.domain.newWorld();
            this.updateViews();
            return;
        }
        UFileSupport.stopWaitMessage();
        this.clearTranscript();
        this.updateViews();
    }
    
    public void updateViews() {
        this.updateMenus();
        this.speechSystem.listenForAvailableCommands();
        if (this.PanelVariables.isVisible()) {
            this.updateVariables();
        }
    }
    
    public void updateMenus() {
        this.MenuFileSaveSession.setEnabled(TSDomain.domain.isSessionFileChanged());
        this.MenuFileSaveSessionAs.setEnabled(true);
        this.updateMenusForUndoRedo();
        //MenuFileCloseSession.enabled := domain.isWorldFileLoaded;
    }
    
    public void updateMenusForUndoRedo() {
        if (TSDomain.domain.sessionCommandList.isUndoEnabled()) {
            this.MenuEditUndo.setEnabled(true);
            this.MenuEditUndo.setText("Undo " + TSDomain.domain.sessionCommandList.undoDescription());
        } else {
            this.MenuEditUndo.setEnabled(false);
            this.MenuEditUndo.setText("Can't undo");
        }
        if (TSDomain.domain.sessionCommandList.isRedoEnabled()) {
            this.MenuEditRedo.setEnabled(true);
            this.MenuEditRedo.setText("Redo " + TSDomain.domain.sessionCommandList.redoDescription());
        } else {
            this.MenuEditRedo.setEnabled(false);
            this.MenuEditRedo.setText("Can't redo");
        }
    }
    
    public void updateVariables() {
        TSVariable oldTop = null;
        int oldTopIndex = this.VariablesListBox.getFirstVisibleIndex();
        // Rectangle oldVisibleRect = this.VariablesListBox.getVisibleRect();
        if ((oldTopIndex >= 0) && (variablesListModel.size() > oldTopIndex)) {
            oldTop = (TSVariable) variablesListModel.get(oldTopIndex);
        }
        this.variablesListModel.clear();
        if (!this.locationCacheValid) {
        	focusComboBoxModel.removeAllElements();
        }
        if (TSDomain.domain.world.variables.size() > 0) {
            for (int i = 0; i < TSDomain.domain.world.variables.size(); i++) {
            	TSVariable variable = TSDomain.domain.world.variables.get(i);
                if (!this.ShowOnlyTrueVariablesButton.isSelected() || (variable.getState() == TSVariable.TSVariableState.kPresent)) {
                    if ((this.ContextButton.isSelected() && (variable.contextUseages > 0)) || 
                    		(this.MoveButton.isSelected() && (variable.moveUseages > 0)) || 
                    		(this.RequirementsButton.isSelected() && (variable.requirementsUseages > 0)) || 
                    		(this.ChangesButton.isSelected() && (variable.changesUseages > 0)) || 
                    		(this.CommandButton.isSelected() && (variable.commandUseages > 0))) {
                    	variablesListModel.addElement(variable);
                    }
                }
                if (!this.locationCacheValid) {
                    if ((variable.contextUseages > 0) || (variable.moveUseages > 0)) {
                    	focusComboBoxModel.addElement(variable);
                    }
                }
            }
        }
        this.VariablesListBox.repaint();
        this.locationCacheValid = true;
        if (oldTopIndex >= 0) {
            if ((oldTop != null) && (variablesListModel.size() > oldTopIndex) && (variablesListModel.get(oldTopIndex) == oldTop)) {
                //this.VariablesListBox.ensureIndexIsVisible(oldTopIndex);
                // Could try this instead:
                // this.VariablesListBox.scrollRectToVisible(oldVisibleRect);
            }
        }
        if (TSDomain.domain.world.focus == null) {
            return;
        }
        this.FocusComboBox.setSelectedItem(TSDomain.domain.world.focus);
    }

	public void commandChanged(KfCommand command, KfCommandChangeType changeType) {
        if  (changeType == KfCommandChangeType.commandDone) {
            TSDomain.domain.sessionChangeCount += 1;
        } else if (changeType == KfCommandChangeType.commandUndone) {
            TSDomain.domain.sessionChangeCount -= 1;
        }
        // may also change save availability
        this.updateMenus();
    }		

	// Variables list box drawing related ===============================================
	
	public void drawRectangle(Graphics2D g, Rectangle rectangle) {
	    g.setColor(Color.gray);
	    g.drawRect(rectangle.x, rectangle.y, rectangle.width + 1, rectangle.height + 1);
	}

	public void drawGraphicCentered(Icon icon, Component c, Graphics2D g, Rectangle rectangle) {
		int x = rectangle.x + rectangle.width / 2 - icon.getIconWidth() / 2;
		int y = rectangle.y + rectangle.height / 2 - icon.getIconHeight() / 2;
		icon.paintIcon(c, g, x, y);
	}

	public static void copyRectToFrom(Rectangle destination, Rectangle source) {
		destination.x = source.x;
		destination.y = source.y;
		destination.width = source.width;
		destination.height = source.height;
	}
	
	public void carveOffRect(Rectangle bigRect, Rectangle littleRect, int width, boolean fromRight) {
		copyRectToFrom(littleRect, bigRect);
		bigRect.width = bigRect.width - width;
        littleRect.width = width;
	    if (fromRight) {
	        littleRect.x = bigRect.x + bigRect.width;
	    } else {
	        bigRect.x = littleRect.x + width;
	    }
	}
	
	public class VariableListCellRenderer extends JPanel implements ListCellRenderer {
		private static final long serialVersionUID = 1L;
		int index;
		boolean isSelected;
		boolean cellHasFocus;
	    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	    	this.index = index;
	    	this.isSelected = isSelected;
	    	this.cellHasFocus = cellHasFocus;
	    	int y = index * kIconSize;
	    	this.setBounds(0, y, VariablesListBox.getWidth(), kIconSize);
	    	this.setPreferredSize(new Dimension(0, kIconSize));
	    	return this;
	    }
	    
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Rectangle aRect = new Rectangle(0, 0, VariablesListBox.getWidth(), kIconSize);
			VariablesListBoxDrawItem((Graphics2D) g, index, aRect, isSelected);
			}

	}

    public void VariablesListBoxDrawItem(Graphics2D g, int index, Rectangle Rect, boolean isSelected) {
        if (Application.terminated) {
            return;
        }
        if ((variablesListModel.size() <= 0) || (index < 0) || (index > TSDomain.domain.world.variables.size() - 1)) {
            return;
        }
        
        boolean selected = isSelected; 
        
        TSVariable variable = (TSVariable)variablesListModel.get(index);
        if (variable == null) {
            return;
        }
        
        // set up rectangles 
        Rectangle bigRect = new Rectangle(Rect);
        
        //PDF PORT issue: used to be clientheight
        // not sure why this is being done, only happens if box can display all that is in list
        // thinking it must be to handle scroll bars appearing?
        if (variablesListModel.size() <= (this.VariablesListBox.getHeight() / kIconSize)) {
        	Rectangle wastedRect = new Rectangle();
            carveOffRect(bigRect, wastedRect, kIconSize, true);
        }
        
        Rectangle stateRect = new Rectangle();

        carveOffRect(bigRect, stateRect, kIconSize, false);
        carveOffRect(bigRect, this.commandRect, kIconSize, true);
        carveOffRect(bigRect, this.changesRect, kIconSize, true);
        carveOffRect(bigRect, this.requirementsRect, kIconSize, true);
        carveOffRect(bigRect, this.moveRect, kIconSize, true);
        carveOffRect(bigRect, this.contextRect, kIconSize, true);
        
        Rectangle stringRect = new Rectangle(bigRect);
        
        // fill first box with white, rest with clHighlight if selected 
        g.setColor(this.VariablesListBox.getBackground());
        g.fillRect(Rect.x, Rect.y, Rect.width, Rect.height);
        if (variable.getState() == TSVariableState.kPresent) {
            drawGraphicCentered(this.ShowOnlyTrueVariablesButton.getIcon(), this.VariablesListBox, g, stateRect);
        }
        if (variable.contextUseages > 0) {
            drawGraphicCentered(this.ContextButton.getIcon(), this.VariablesListBox, g, this.contextRect);
        }
        if (variable.requirementsUseages > 0) {
            drawGraphicCentered(this.RequirementsButton.getIcon(), this.VariablesListBox, g, this.requirementsRect);
        }
        if (variable.commandUseages > 0) {
            drawGraphicCentered(this.CommandButton.getIcon(), this.VariablesListBox, g, this.commandRect);
        }
        if (variable.moveUseages > 0) {
            drawGraphicCentered(this.MoveButton.getIcon(), this.VariablesListBox, g, this.moveRect);
        }
        if (variable.changesUseages > 0) {
            drawGraphicCentered(this.ChangesButton.getIcon(), this.VariablesListBox, g, this.changesRect);
        }
        g.setFont(this.VariablesListBox.getFont());
        if (selected) {
            //g.setColor(UNRESOLVED.clHighlight);
        	g.setColor(this.VariablesListBox.getSelectionBackground());
        	g.setBackground(this.VariablesListBox.getSelectionBackground());
        } else {
        	//g.setColor(UNRESOLVED.clWindow);
        	g.setColor(this.VariablesListBox.getBackground());
        	g.setBackground(this.VariablesListBox.getBackground());
        }
        //g.setColor(Color.orange);
        g.fillRect(stringRect.x, stringRect.y, stringRect.width, stringRect.height);
        if (selected) {
            //g.setColor(UNRESOLVED.clHighlightText);
            g.setColor(this.VariablesListBox.getSelectionForeground());
        } else {
        	 //g.setColor(UNRESOLVED.clBtnText);
        	g.setColor(this.VariablesListBox.getForeground());
        }
        // margin for text 
        stringRect.x = stringRect.x + 5;
        String phrase = variable.phrase;
        String displayString = phrase + ".....................................................................................................................................................................................................";
        g.setClip(stringRect.x, stringRect.y, stringRect.width, stringRect.height);
        int baseline = g.getFontMetrics().getAscent();
        g.drawString(displayString, stringRect.x, stringRect.y + baseline);
    }
    
    
    public void setButtonGlyphs(boolean buttonSymbols) {
        if (buttonSymbols) {
            this.ContextButton.setIcon(this.contextSymbol.imageIcon);
            this.CommandButton.setIcon(this.commandSymbol.imageIcon);
            this.MoveButton.setIcon(this.moveSymbol.imageIcon);
            this.RequirementsButton.setIcon(this.requirementsSymbol.imageIcon);
            this.ChangesButton.setIcon(this.changesSymbol.imageIcon);
            this.ShowOnlyTrueVariablesButton.setIcon(this.plusSymbol.imageIcon);
         	Application.RuleEditorForm.ReplyButton.setIcon(this.replySymbol.imageIcon);
        } else {
            this.ContextButton.setIcon(this.contextPicture.imageIcon);
            this.CommandButton.setIcon(this.commandPicture.imageIcon);
            this.MoveButton.setIcon(this.movePicture.imageIcon);
            this.RequirementsButton.setIcon(this.requirementsPicture.imageIcon);
            this.ChangesButton.setIcon(this.changesPicture.imageIcon);
            this.ShowOnlyTrueVariablesButton.setIcon(this.plusPicture.imageIcon);
            Application.RuleEditorForm.ReplyButton.setIcon(this.replyPicture.imageIcon);
        }
    }
    
    public void updateForChangeToDomainOptions() {
        this.MenuOptionsSpeak.setSelected(TSDomain.domain.options.playerSpeak);
        this.MenuOptionsSounds.setSelected(TSDomain.domain.options.playerPlaySounds);
        this.MenuOptionsPlayMusic.setSelected(TSDomain.domain.options.playerPlayMusic);
        this.MenuSettingsShowTranscript.setSelected(TSDomain.domain.options.showTranscript);
        this.MenuSettingsShowPictures.setSelected(TSDomain.domain.options.showPictures);
        this.MenuSettingsSayOptionsAfterLook.setSelected(TSDomain.domain.options.sayOptionsAfterLook);
        this.MenuOptionsVoiceUndo.setSelected(TSDomain.domain.options.useVoiceToUndo);
        this.MenuOptionsVoiceRedo.setSelected(TSDomain.domain.options.useVoiceToRedo);
        this.MenuOptionsShowVariables.setSelected(TSDomain.domain.options.showVariables);
        // PDF PORT FIX: Font issues
//        this.Font.Name = TSDomain.domain.options.playerFontName;
//        this.Font.Size = TSDomain.domain.options.playerFontSize;
//        this.VariablesListBox.ItemHeight = Math.max(this.Canvas.TextHeight("W") + 2, 16);
//        this.TranscriptEdit.font.name = TSDomain.domain.options.playerFontName;
//        this.TranscriptEdit.font.size = TSDomain.domain.options.playerFontSize;
        this.TranscriptEdit.repaint();
        this.updateForChangeToShowingVariables();
        this.MenuOptionsUpdateEditorSelections.setSelected(TSDomain.domain.options.updateEditorAfterCommandDone);
    }
    
////     PDF PORT: FIX ALL THIS STUFF:
//    public void MediaPlayerNotify(TObject Sender) {
//        if (TSDomain.domain.options.loopMusic && (!this.MediaPlayer.FileName.equals("")) && (this.MediaPlayer.notifyValue == UNRESOLVED.nvSuccessful)) {
//            this.MediaPlayer.Notify = true;
//            this.MediaPlayer.Play();
//        }
//    }
//
//    public void WMGetMinMaxInfo(Tmessage MSG) {
//        super.WMGetMinMaxInfo();
//        //FIX unresolved WITH expression: UNRESOLVED.PMinMaxInfo(MSG.lparam).PDF_FIX_POINTER_ACCESS
//        UNRESOLVED.ptMinTrackSize.x = 285;
//        UNRESOLVED.ptMinTrackSize.y = 250;
//        //ptMaxTrackSize.x := 558;
//    }
//    
//    public void FormActivate(TObject Sender) {
//        if (Application.terminated) {
//            return;
//        }
//        // pdf v1.2 modified from just updating variables
//        TSDomain.domain.world.updateAvailable();
//        this.updateViews();
//    }
//
//    // --------------------------- HINTS ------------------------------------- 
//    public void DoShowHint(ansistring HintStr, boolean CanShow, THintInfo HintInfo) {
//        System.out.println("method DoShowHint had assigned to var parameter HintStr not added to return; fixup manually");
//        long itemAtPos = 0;
//        long col = 0;
//        long row = 0;
//        TListBox listBox = new TListBox();
//        TSRule rule = new TSRule();
//        
//        if (Application.terminated) {
//            return CanShow;
//        }
//        if (HintInfo.HintControl instanceof delphi_compatability.TListBox) {
//            listBox = (delphi_compatability.TListBox)HintInfo.HintControl;
//            if ((listBox == this.VisibleCommandsList) || (listBox == this.VariablesListBox) || (listBox == Application.RuleEditorForm.FirstListBox) || (listBox == Application.RuleEditorForm.SecondListBox)) {
//                //true = must be on an item
//                itemAtPos = listBox.ItemAtPos(HintInfo.CursorPos, true);
//                if ((itemAtPos >= 0)) {
//                    HintStr = listBoxModel.get(itemAtPos);
//                    HintInfo.CursorRect = listBox.ItemRect(itemAtPos);
//                }
//            }
//        } else if (HintInfo.HintControl == Application.RuleEditorForm.RuleGrid) {
//            // PDF PORT col, row = Application.RuleEditorForm.RuleGrid.MouseToCell(HintInfo.CursorPos.x, HintInfo.CursorPos.y, col, row);
//            rule = null;
//            if ((row > 0) && (row <= TSDomain.domain.world.rules.size())) {
//                rule = usworld.TSRule(TSDomain.domain.world.rules[row - 1]);
//            }
//            if (rule == null) {
//                return CanShow;
//            }
//            HintStr = rule.getTextForField(col);
//            HintInfo.CursorRect = Application.RuleEditorForm.RuleGrid.CellRect(col, row);
//        }
//        HintInfo.HintMaxWidth = 250;
//        return CanShow;
//    }
//    
//
//    // ---------------------- FILE MENU -------------------------
//
//    public void WMDropFiles(TWMDropFiles Msg) {
//        // PDF PORT CFileName = [0] * (range(0, UNRESOLVED.MAX_PATH + 1) + 1);
//        
//        try {
//            if (UNRESOLVED.DragQueryFile(Msg.Drop, 0, CFileName, UNRESOLVED.MAX_PATH) > 0) {
//                if ((UNRESOLVED.pos(".WLD", uppercase(CFileName)) <= 0) && (UNRESOLVED.pos(".SES", uppercase(CFileName)) <= 0)) {
//                    return;
//                }
//                if (!this.askForSaveSessionAndProceed()) {
//                    return;
//                }
//                this.speechSystem.haltSpeechAndSound();
//                // turns it off
//                this.speechSystem.speakSound("music");
//                this.openSessionOrWorldFile(CFileName);
//                this.updateTitles();
//                Msg.Result = 0;
//            }
//        } finally {
//            UNRESOLVED.DragFinish(Msg.Drop);
//        }
//    }
//
//
//    public void WMQueryEndSession(TWMQueryEndSession message) {
//        super.WMQueryEndSession();
//        if (this.speechSystem.usingSpeech) {
//            //looks like bug in Agent that if shutting down will hang
//            UFileSupport.ShowMessage("Please close StoryHarp before shutting down Windows.");
//            message.result = 0;
//            return;
//        }
//        if (!this.askForSaveSessionAndProceed()) {
//            // prevents windows from shutting down
//            message.result = 0;
//        } else if (!Application.RuleEditorForm.askForSaveWorldAndProceed()) {
//            message.result = 0;
//        } else if (!this.cleanUpBeforeExit()) {
//            message.result = 0;
//        }
//    }
//    
//    public void WMEndSession(TWMEndSession message) {
//        super.WMEndSession();
//        // speechSystem.systemShuttingDown := true;
//    }
//    
//    public void FormClose(TObject Sender, TCloseAction Action) {
//        if (!this.askForSaveSessionAndProceed()) {
//            // same as exit, but can't call exit because we have to set the action flag 
//            Action = delphi_compatability.TCloseAction.caNone;
//            return;
//        }
//        if (!Application.RuleEditorForm.askForSaveWorldAndProceed()) {
//            Action = delphi_compatability.TCloseAction.caNone;
//            return;
//        }
//        if (!this.cleanUpBeforeExit()) {
//            Action = delphi_compatability.TCloseAction.caNone;
//        }
//    }
// 
//    public void FocusComboBoxKeyUp(TObject Sender, byte Key, TShiftState Shift) {
//        this.FocusComboBoxChange(Sender);
//        return Key;
//    }
//    
//    public void FocusComboBoxChange(ActionEvent e2) {
//        int index = this.focusComboBoxModel.getIndexOf(this.FocusComboBox.getSelectedItem());
//        if (index < 0) {
//            return;
//        }
//        TSVariable newFocus = (TSVariable)this.focusComboBoxModel.getElementAt(index);
//        if ((newFocus == TSDomain.domain.world.focus) && (newFocus.getState() == TSVariableState.kPresent)) {
//            return;
//        }
//        // PDF PORT MAYBE int oldSelStart = this.FocusComboBox.SelStart;
//        TSDomain.domain.sessionCommandList.moveFocus(newFocus);
//        // PDF PORT MAYBE this.FocusComboBox.SelStart = oldSelStart;
//    }
//    
//    public void FormKeyDown(TObject Sender, byte Key, TShiftState Shift) {
//        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
//            this.speechSystem.haltSpeechAndSound();
//        }
//        return Key;
//    }
//  
//    public void FormShow(TObject Sender) {
//        UNRESOLVED.DragAcceptFiles(this.Handle, true);
//    }
//    

}
