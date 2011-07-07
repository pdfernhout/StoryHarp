package com.kurtz_fernhout.storyharp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.kurtz_fernhout.storyharp.KfCommand.CommandChangedListener;
import com.kurtz_fernhout.storyharp.KfCommand.KfCommandChangeType;
import com.l2fprod.common.swing.JFontChooser;

import net.miginfocom.swing.MigLayout;

// PDF PORT NOTES:
// NOTE: Should work more on selecting;
// need to search for all uses of .selected = and make sure the RuleGridSelectionModel is updated
// Also, not drawing any indicator for the "focused" rule anymore;
// need to get the draw methods for the first and second list box working again, and also maybe RuleGrid

public class TRuleEditorForm extends JFrame implements CommandChangedListener {
    private static final long serialVersionUID = 1L;
    public static final boolean kFromBottom = true;
    public static final boolean kFromTop = false;
    public static final int kGap = 4;
    public static final int kGap2 = kGap * 2;
    public static final int kGap3 = kGap * 3;
	private static final int kLeftRightBorderSize = 250;
	private static final int kTopBottomBorderSize = 250;
    
    JSplitPane SplitterEdit;
    JPanel PanelEditor;
    JSplitPane SplitterRequirementsChanges;
    JPanel PanelRequirementsChanges;
    JButton RequirementsSpeedButton;
    JButton ChangesSpeedButton;
    JList RequirementsListBox;
    DefaultListModel RequirementsListBoxModel;
    JList ChangesListBox;
    DefaultListModel ChangesListBoxModel;
    JTextField RequirementsEdit;
    JTextField ChangesEdit;
    JPanel PanelRest;
    JButton ReplyButton;
    JButton CommandSpeedButton;
    JButton MoveSpeedButton;
    JButton ContextSpeedButton;
    JLabel RuleNumberLabel;
    ImagePanel replyPicture;
    JTextField ContextEdit;
    JTextField CommandEdit;
    JTextArea ReplyMemo;
    JTextField MoveEdit;
    JPanel PanelTop;
    JTabbedPane ListPages;
    JPanel TabSheetTable;
    JTable RuleGrid;
    JPanel TabSheetMap;
    JPanel PanelMap;
    MapPanel MapImage;
    JPanel TabSheetBrowse;
    JPanel PanelLists;
    JSplitPane SplitterLists;
    JPanel PanelFirstList;
    ImagePanel firstListBoxImage;
    JLabel firstListBoxLabel;
    DefaultListModel FirstListBoxModel;
    JList FirstListBox;
    JPanel PanelSecondList;
    ImagePanel SecondListBoxImage;
    JLabel SecondListBoxLabel;
    DefaultListModel SecondListBoxModel;
    JList SecondListBox;
    JPanel PanelButtonBar;
    JButton NewRuleButton;
    JButton DuplicateRuleButton;
    JButton DeleteRuleButton;
    JButton MoveUpButton;
    JButton MoveDownButton;
    JButton insertSound;
    JButton InsertMusicButton;
    // UNHANDLED_TYPE WaveFileOpenDialog;
    // UNHANDLED_TYPE ImageList;
    JMenuBar MainMenu1;
    JMenu MenuFile;
    JMenuItem MenuFileNewWorld;
    JMenuItem MenuFileOpenWorld;
    JMenuItem N7;
    JMenuItem MenuFileSaveWorld;
    JMenuItem MenuFileSaveWorldAs;
    JMenuItem N4;
    JMenuItem MenuWorldSwitchToPlayer;
    JMenuItem N8;
    JMenuItem MenuFileExit;
    JMenu MenuEdit;
    JMenuItem MenuEditUndo;
    JMenuItem MenuEditRedo;
    JMenuItem N1;
    JMenuItem MenuEditCut;
    JMenuItem MenuEditCopy;
    JMenuItem MenuEditPaste;
    JMenuItem N12;
    JMenuItem MenuEditInsertSound;
    JMenuItem MenuEditInsertMusic;
    JMenuItem MenuEditInsertPicture;
    JMenuItem N13;
    JMenuItem MenuEditPreferences;
    JMenu MenuRule;
    JMenuItem MenuRuleNew;
    JMenuItem MenuRuleDuplicate;
    JMenuItem MenuRuleDelete;
    JMenuItem N3;
    JMenuItem MenuRuleRaise;
    JMenuItem MenuRuleLower;
    JMenuItem N11;
    JMenuItem MenuFileMergeWithWorld;
    JMenuItem MenuFileExport;
    JMenuItem N5;
    JMenuItem MenuRuleTestReply;
    JMenu MenuMaps;
    JCheckBoxMenuItem MenuDisplayShowButtonBar;
    JCheckBoxMenuItem MenuOptionsShowRuleEditor;
    JCheckBoxMenuItem MenuMapsShowCommands;
    JMenuItem N10;
    JRadioButtonMenuItem MenuBrowseByContext;
    JRadioButtonMenuItem MenuBrowseByCommand;
    JRadioButtonMenuItem MenuBrowseByMove;
    JRadioButtonMenuItem MenuBrowseByRequirements;
    JRadioButtonMenuItem MenuBrowseByChanges;
    JMenu Wizards1;
    JMenuItem MenuToolsSearch;
    JMenuItem N2;
    JMenuItem MenuMapsQuickContexts;
    JMenuItem MenuMapLinkWizard;
    JMenuItem MenuMapQuickCommands;
    JMenuItem N9;
    JMenuItem MenuToolsGenerateJava;
    JMenuItem N14;
    JMenuItem MenuEditLogFile;
    JMenu MenuHelp;
    JMenuItem MenuHelpContents;
    JMenuItem MenuHelpBasicConcepts;
    JMenuItem MenuHelpTutorial;
    JMenuItem MenuHelpEditingWorlds;
    JMenuItem N6;
    JMenuItem MenuHelpRegister;
    JMenuItem AfterRegisterMenuSeparator;
    JMenuItem MenuHelpAbout;
    JPopupMenu EditPopupMenu;
    JMenuItem PopupCut;
    JMenuItem PopupCopy;
    JMenuItem PopupPaste;
    JPopupMenu MapPopupMenu;
    JMenuItem PopupNewContext;
    JMenuItem PopupNewCommand;
    JMenuItem PopupNewLink;
    public JPanel mainContentPane;  //  @jve:decl-index=0:visual-constraint="10,66"
    public JPanel delphiPanel;
	private JPanel PanelFirstListForLabel;
	private JPanel PanelSecondListForLabel;
	private JScrollPane MapScroller;
	
    public TSRule rule;
    // PDF PORT: APPARENTLY NOT USED: public TSelectionInformation selectionInformation = new TSelectionInformation();
    public int organizeByField;
    public boolean wasLoaded;
    public boolean lastSaveProceeded;
    public boolean lastClickAtLeft;
    public boolean ignoreNextEnter;
    public int indexEdited;
    public TSRule lastCommand;
    public int lastSingleRuleIndex;
    public int lastBrowserSingleRuleIndex;
    public boolean buttonSymbols;
    public boolean startingUp;
    public boolean actionInProgress;
    public TSDraggableObject previousChoice;
    public TSDraggableObject lastChoice;
    public Point lastMapMouseDownPosition = new Point();
    public int numNewContextsMadeByPopupMenuThisSession;
    public int numNewCommandsMadeByPopupMenuThisSession;
	private RuleListSelectionModel RuleGridSelectionModel;
    
    public TRuleEditorForm() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
        FormCreate();
    }
    
    public JMenuBar getMainMenu1() {
        if (MainMenu1 != null) return MainMenu1;
        this.MainMenu1 = new JMenuBar();
        this.MenuFile = new JMenu("World");
        this.MenuFile.setMnemonic('W');
        this.MainMenu1.add(MenuFile);
        this.MenuFileNewWorld = new JMenuItem("New");
        this.MenuFileNewWorld.setMnemonic(KeyEvent.VK_N);
        MenuFileNewWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileNewWorldClick(event);
            }
        });
        this.MenuFile.add(MenuFileNewWorld);
        
        this.MenuFileOpenWorld = new JMenuItem("Open...");
        this.MenuFileOpenWorld.setMnemonic(KeyEvent.VK_O);
        MenuFileOpenWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileOpenWorldClick(event);
            }
        });
        this.MenuFile.add(MenuFileOpenWorld);
        
        this.MenuFile.addSeparator();
        
        this.MenuFileSaveWorld = new JMenuItem("Save");
        this.MenuFileSaveWorld.setMnemonic(KeyEvent.VK_S);
        MenuFileSaveWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileSaveWorldClick(event);
            }
        });
        this.MenuFile.add(MenuFileSaveWorld);
        
        this.MenuFileSaveWorldAs = new JMenuItem("Save As...");
        this.MenuFileSaveWorldAs.setMnemonic(KeyEvent.VK_A);
        MenuFileSaveWorldAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileSaveWorldAsClick(event);
            }
        });
        this.MenuFile.add(MenuFileSaveWorldAs);
        
        this.MenuFile.addSeparator();
        
        this.MenuWorldSwitchToPlayer = new JMenuItem("Switch to Player...");
        this.MenuWorldSwitchToPlayer.setMnemonic(KeyEvent.VK_P);
        MenuWorldSwitchToPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuWorldSwitchToPlayerClick(event);
            }
        });
        this.MenuFile.add(MenuWorldSwitchToPlayer);
        
        this.MenuFile.addSeparator();
        
        this.MenuFileExit = new JMenuItem("Exit");
        this.MenuFileExit.setMnemonic(KeyEvent.VK_X);
        MenuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileExitClick(event);
            }
        });
        this.MenuFile.add(MenuFileExit);
        
        
        MenuFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                MenuEditClick(event);
            }
        });
        
        this.MenuEdit = new JMenu("Edit");
        this.MenuEdit.setMnemonic('E');
        this.MainMenu1.add(MenuEdit);
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
        
        this.MenuEditCut = new JMenuItem("Cut                 Ctrl+X");
        this.MenuEditCut.setMnemonic(KeyEvent.VK_T);
        MenuEditCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditCutClick(event);
            }
        });
        this.MenuEdit.add(MenuEditCut);
        
        this.MenuEditCopy = new JMenuItem("Copy              Ctrl+C");
        this.MenuEditCopy.setMnemonic(KeyEvent.VK_C);
        MenuEditCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditCopyClick(event);
            }
        });
        this.MenuEdit.add(MenuEditCopy);
        
        this.MenuEditPaste = new JMenuItem("Paste             Ctrl+V");
        this.MenuEditPaste.setMnemonic(KeyEvent.VK_P);
        MenuEditPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditPasteClick(event);
            }
        });
        this.MenuEdit.add(MenuEditPaste);
        
        this.MenuEdit.addSeparator();
        
        this.MenuEditInsertSound = new JMenuItem("Insert Sound...");
        this.MenuEditInsertSound.setMnemonic(KeyEvent.VK_S);
        MenuEditInsertSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditInsertSoundClick(event);
            }
        });
        this.MenuEdit.add(MenuEditInsertSound);
        
        this.MenuEditInsertMusic = new JMenuItem("Insert Music...");
        this.MenuEditInsertMusic.setMnemonic(KeyEvent.VK_M);
        MenuEditInsertMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditInsertMusicClick(event);
            }
        });
        this.MenuEdit.add(MenuEditInsertMusic);
        
        this.MenuEditInsertPicture = new JMenuItem("Insert Picture...");
        this.MenuEditInsertPicture.setMnemonic(KeyEvent.VK_I);
        MenuEditInsertPicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditInsertPictureClick(event);
            }
        });
        this.MenuEdit.add(MenuEditInsertPicture);
        
        this.MenuEdit.addSeparator();
        
        this.MenuEditPreferences = new JMenuItem("Editor Preferences...");
        this.MenuEditPreferences.setMnemonic(KeyEvent.VK_E);
        MenuEditPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditPreferencesClick(event);
            }
        });
        this.MenuEdit.add(MenuEditPreferences);
        
        
        MenuEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                MenuEditClick(event);
            }
        });
        
        this.MenuRule = new JMenu("Rule");
        this.MenuRule.setMnemonic('R');
        this.MainMenu1.add(MenuRule);
        this.MenuRuleNew = new JMenuItem("New Rule");
        this.MenuRuleNew.setMnemonic(KeyEvent.VK_N);
        MenuRuleNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuRuleNewClick(event);
            }
        });
        this.MenuRule.add(MenuRuleNew);
        
        this.MenuRuleDuplicate = new JMenuItem("Duplicate");
        this.MenuRuleDuplicate.setMnemonic(KeyEvent.VK_D);
        MenuRuleDuplicate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuRuleDuplicateClick(event);
            }
        });
        this.MenuRule.add(MenuRuleDuplicate);
        
        this.MenuRuleDelete = new JMenuItem("Delete");
        this.MenuRuleDelete.setMnemonic(KeyEvent.VK_E);
        MenuRuleDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuRuleDeleteClick(event);
            }
        });
        this.MenuRule.add(MenuRuleDelete);
        
        this.MenuRule.addSeparator();
        
        this.MenuRuleRaise = new JMenuItem("Raise");
        this.MenuRuleRaise.setMnemonic(KeyEvent.VK_R);
        MenuRuleRaise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuRuleRaiseClick(event);
            }
        });
        this.MenuRule.add(MenuRuleRaise);
        
        this.MenuRuleLower = new JMenuItem("Lower");
        this.MenuRuleLower.setMnemonic(KeyEvent.VK_L);
        MenuRuleLower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuRuleLowerClick(event);
            }
        });
        this.MenuRule.add(MenuRuleLower);
        
        this.MenuRule.addSeparator();
        
        this.MenuFileMergeWithWorld = new JMenuItem("Import From...");
        this.MenuFileMergeWithWorld.setMnemonic(KeyEvent.VK_I);
        MenuFileMergeWithWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileMergeWithWorldClick(event);
            }
        });
        this.MenuRule.add(MenuFileMergeWithWorld);
        
        this.MenuFileExport = new JMenuItem("Export Selected To...");
        this.MenuFileExport.setMnemonic(KeyEvent.VK_X);
        MenuFileExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileExportClick(event);
            }
        });
        this.MenuRule.add(MenuFileExport);
        
        this.MenuRule.addSeparator();
        
        this.MenuRuleTestReply = new JMenuItem("Test Reply");
        this.MenuRuleTestReply.setMnemonic(KeyEvent.VK_T);
        MenuRuleTestReply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuRuleTestReplyClick(event);
            }
        });
        this.MenuRule.add(MenuRuleTestReply);
        
        this.MenuMaps = new JMenu("Options");
        this.MenuMaps.setMnemonic('O');
        this.MainMenu1.add(MenuMaps);
        this.MenuDisplayShowButtonBar = new JCheckBoxMenuItem("Show Button Bar");
        this.MenuDisplayShowButtonBar.setMnemonic(KeyEvent.VK_B);
        MenuDisplayShowButtonBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuDisplayShowButtonBarClick(event);
            }
        });
        this.MenuMaps.add(MenuDisplayShowButtonBar);
        this.MenuDisplayShowButtonBar.setSelected(true);
        
        this.MenuOptionsShowRuleEditor = new JCheckBoxMenuItem("Show Rule Editor");
        this.MenuOptionsShowRuleEditor.setMnemonic(KeyEvent.VK_E);
        MenuOptionsShowRuleEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsShowRuleEditorClick(event);
            }
        });
        this.MenuMaps.add(MenuOptionsShowRuleEditor);
        this.MenuOptionsShowRuleEditor.setSelected(true);
        
        this.MenuMapsShowCommands = new JCheckBoxMenuItem("Show Commands in Map");
        this.MenuMapsShowCommands.setMnemonic(KeyEvent.VK_S);
        MenuMapsShowCommands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuMapsShowCommandsClick(event);
            }
        });
        this.MenuMaps.add(MenuMapsShowCommands);
        this.MenuMapsShowCommands.setSelected(true);
        
        this.MenuMaps.addSeparator();
        
        ButtonGroup group = new ButtonGroup();
        this.MenuBrowseByContext = new JRadioButtonMenuItem("Browse by Context");
        group.add(this.MenuBrowseByContext);
        this.MenuBrowseByContext.setMnemonic(KeyEvent.VK_C);
        this.MenuBrowseByContext.putClientProperty("tag", 0);
        MenuBrowseByContext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.MenuMaps.add(MenuBrowseByContext);
        this.MenuBrowseByContext.setSelected(false);
        
        this.MenuBrowseByCommand = new JRadioButtonMenuItem("Browse by Command");
        group.add(this.MenuBrowseByCommand);
        this.MenuBrowseByCommand.setMnemonic(KeyEvent.VK_O);
        MenuBrowseByCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.MenuMaps.add(MenuBrowseByCommand);
        this.MenuBrowseByCommand.setSelected(false);
        this.MenuBrowseByCommand.putClientProperty("tag", 1);
        
        this.MenuBrowseByMove = new JRadioButtonMenuItem("Browse by Move");
        group.add(this.MenuBrowseByMove);
        this.MenuBrowseByMove.setMnemonic(KeyEvent.VK_M);
        MenuBrowseByMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.MenuMaps.add(MenuBrowseByMove);
        this.MenuBrowseByMove.setSelected(false);
        this.MenuBrowseByMove.putClientProperty("tag", 3);
        
        this.MenuBrowseByRequirements = new JRadioButtonMenuItem("Browse by Requirements");
        group.add(this.MenuBrowseByRequirements);
        this.MenuBrowseByRequirements.setMnemonic(KeyEvent.VK_R);
        MenuBrowseByRequirements.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.MenuMaps.add(MenuBrowseByRequirements);
        this.MenuBrowseByRequirements.setSelected(false);
        this.MenuBrowseByRequirements.putClientProperty("tag", 4);
        
        this.MenuBrowseByChanges = new JRadioButtonMenuItem("Browse by Changes");
        group.add(this.MenuBrowseByChanges);
        this.MenuBrowseByChanges.setMnemonic(KeyEvent.VK_H);
        MenuBrowseByChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.MenuMaps.add(MenuBrowseByChanges);
        this.MenuBrowseByChanges.setSelected(false);
        this.MenuBrowseByChanges.putClientProperty("tag", 5);
        
        
        
        this.Wizards1 = new JMenu("Tools");
        this.Wizards1.setMnemonic('T');
        this.MainMenu1.add(Wizards1);
        this.MenuToolsSearch = new JMenuItem("Find...");
        this.MenuToolsSearch.setMnemonic(KeyEvent.VK_F);
        MenuToolsSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuToolsSearchClick(event);
            }
        });
        this.Wizards1.add(MenuToolsSearch);
        
        this.Wizards1.addSeparator();
        
        this.MenuMapsQuickContexts = new JMenuItem("New Contexts Wizard...");
        this.MenuMapsQuickContexts.setMnemonic(KeyEvent.VK_C);
        MenuMapsQuickContexts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuMapsQuickContextsClick(event);
            }
        });
        this.Wizards1.add(MenuMapsQuickContexts);
        
        this.MenuMapLinkWizard = new JMenuItem("New Moves Wizard...");
        this.MenuMapLinkWizard.setMnemonic(KeyEvent.VK_M);
        MenuMapLinkWizard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuMapLinkWizardClick(event);
            }
        });
        this.Wizards1.add(MenuMapLinkWizard);
        
        this.MenuMapQuickCommands = new JMenuItem("New Commands Wizard...");
        this.MenuMapQuickCommands.setMnemonic(KeyEvent.VK_O);
        MenuMapQuickCommands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuMapQuickCommandsClick(event);
            }
        });
        this.Wizards1.add(MenuMapQuickCommands);
        
        this.Wizards1.addSeparator();
        
        this.MenuToolsGenerateJava = new JMenuItem("Generate Java...");
        MenuToolsGenerateJava.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuToolsGenerateJavaClick(event);
            }
        });
        this.Wizards1.add(MenuToolsGenerateJava);
        
        this.Wizards1.addSeparator();
        
        this.MenuEditLogFile = new JMenuItem("Log File...");
        this.MenuEditLogFile.setMnemonic(KeyEvent.VK_L);
        MenuEditLogFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditLogFileClick(event);
            }
        });
        this.Wizards1.add(MenuEditLogFile);
        
        
        
        this.MenuHelp = new JMenu("Help");
        this.MenuHelp.setMnemonic('H');
        this.MainMenu1.add(MenuHelp);
        this.MenuHelpContents = new JMenuItem("Help Topics");
        this.MenuHelpContents.setMnemonic(KeyEvent.VK_H);
        MenuHelpContents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpContentsClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpContents);
        
        this.MenuHelpBasicConcepts = new JMenuItem("Basic Concepts");
        this.MenuHelpBasicConcepts.setMnemonic(KeyEvent.VK_C);
        MenuHelpBasicConcepts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpBasicConceptsClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpBasicConcepts);
        
        this.MenuHelpTutorial = new JMenuItem("Authoring Tutorial");
        this.MenuHelpTutorial.setMnemonic(KeyEvent.VK_T);
        MenuHelpTutorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpTutorialClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpTutorial);
        
        this.MenuHelpEditingWorlds = new JMenuItem("Editing Worlds");
        this.MenuHelpEditingWorlds.setMnemonic(KeyEvent.VK_W);
        MenuHelpEditingWorlds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpEditingWorldsClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpEditingWorlds);
        
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
        
        
        
        return MainMenu1;
    }
        //  --------------- UNHANDLED ATTRIBUTE: this.MainMenu1.y = 222;
        //  --------------- UNHANDLED ATTRIBUTE: this.MainMenu1.x = 472;
        
    public JPopupMenu getEditPopupMenu() {
        if (EditPopupMenu != null) return EditPopupMenu;
        this.EditPopupMenu = new JPopupMenu();
        this.PopupCut = new JMenuItem("Cut");
        this.PopupCut.setMnemonic(KeyEvent.VK_T);
        PopupCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditCutClick(event);
            }
        });
        this.EditPopupMenu.add(PopupCut);
        
        this.PopupCopy = new JMenuItem("Copy");
        this.PopupCopy.setMnemonic(KeyEvent.VK_C);
        PopupCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditCopyClick(event);
            }
        });
        this.EditPopupMenu.add(PopupCopy);
        
        this.PopupPaste = new JMenuItem("Paste");
        this.PopupPaste.setMnemonic(KeyEvent.VK_P);
        PopupPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditPasteClick(event);
            }
        });
        this.EditPopupMenu.add(PopupPaste);
        
        return EditPopupMenu;
    }
        //  --------------- UNHANDLED ATTRIBUTE: this.EditPopupMenu.y = 307;
        //  --------------- UNHANDLED ATTRIBUTE: this.EditPopupMenu.x = 516;
        
    public JPopupMenu getMapPopupMenu() {
        if (MapPopupMenu != null) return MapPopupMenu;
        this.MapPopupMenu = new JPopupMenu();
        this.PopupNewContext = new JMenuItem("New Context");
        this.PopupNewContext.setMnemonic(KeyEvent.VK_C);
        PopupNewContext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                PopupNewContextClick(event);
            }
        });
        this.MapPopupMenu.add(PopupNewContext);
        
        this.PopupNewCommand = new JMenuItem("New Command");
        this.PopupNewCommand.setMnemonic(KeyEvent.VK_O);
        PopupNewCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                PopupNewCommandClick(event);
            }
        });
        this.MapPopupMenu.add(PopupNewCommand);
        
        this.PopupNewLink = new JMenuItem("New Move");
        this.PopupNewLink.setMnemonic(KeyEvent.VK_M);
        PopupNewLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                PopupNewLinkClick(event);
            }
        });
        this.MapPopupMenu.add(PopupNewLink);
        
        return MapPopupMenu;
    }
        //  --------------- UNHANDLED ATTRIBUTE: this.MapPopupMenu.y = 167;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapPopupMenu.x = 532;
        
    private void initialize() {
        this.setSize(new Dimension(738, 705));
        this.setJMenuBar(getMainMenu1());
        this.setContentPane(getMainContentPane());
        this.setTitle("StoryHarp World Editor");
    }
    
    private JPanel getMainContentPane() {
        if (mainContentPane == null) {
            mainContentPane = new JPanel();
            mainContentPane.setLayout(new BorderLayout());
            mainContentPane.setSize(new Dimension(688, 336));
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
        delphiPanel = new JPanel(new BorderLayout());

        //  --------------- UNHANDLED ATTRIBUTE: this.OnResize = FormResize;
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnShow = FormShow;
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent event) {
                FormKeyDown(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.OnCreate = FormCreate;
        //  --------------- UNHANDLED ATTRIBUTE: this.ActiveControl = ContextEdit;
        //  --------------- UNHANDLED ATTRIBUTE: this.KeyPreview = True;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnDeactivate = FormDeactivate;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 100;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        
        
        this.SplitterEdit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        SplitterEdit.setDividerLocation(300);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterEdit.MinSize = 1;
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterEdit.ResizeAnchor = akBottom;
        
        this.SplitterRequirementsChanges = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterRequirementsChanges.MinSize = 60;
        SplitterRequirementsChanges.setDividerLocation(200);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterRequirementsChanges.OnMoved = SplitterRequirementsChangesMoved;
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterRequirementsChanges.ResizeAnchor = akBottom;
        
        Image RequirementsSpeedButtonImage = toolkit.createImage("resources/RuleEditorForm_RequirementsSpeedButton.png");
        this.RequirementsSpeedButton = new JButton("Requirements", new ImageIcon(RequirementsSpeedButtonImage));
        RequirementsSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.RequirementsSpeedButton.setToolTipText("Browse all rules with the selected requirement");
        this.RequirementsSpeedButton.putClientProperty("tag", 4);
        
        Image ChangesSpeedButtonImage = toolkit.createImage("resources/RuleEditorForm_ChangesSpeedButton.png");
        this.ChangesSpeedButton = new JButton("Changes", new ImageIcon(ChangesSpeedButtonImage));
        ChangesSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.ChangesSpeedButton.setToolTipText("Browse all rules with the selected change");
        this.ChangesSpeedButton.putClientProperty("tag", 5);
        
        this.RequirementsListBoxModel = new DefaultListModel();
        this.RequirementsListBox = new JList(RequirementsListBoxModel);
        this.RequirementsListBox.setFixedCellHeight(14);
        this.RequirementsListBox.setBounds(117, 4, 329, 33);
        RequirementsListBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                EditEnterCommit(event);
            }
        });
        RequirementsListBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent event) {
                ListBoxExit(event);
            }
        });
        RequirementsListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                ListBoxMouseUp(event);
            }
        });
        RequirementsListBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent event) {
                ListBoxKeyUp(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.RequirementsListBox.OnDblClick = ListBoxDblClick;
        //  --------------- UNHANDLED ATTRIBUTE: this.RequirementsListBox.ExtendedSelect = False;
        
        this.ChangesListBoxModel = new DefaultListModel();
        this.ChangesListBox = new JList(ChangesListBoxModel);
        this.ChangesListBox.setFixedCellHeight(14);
        this.ChangesListBox.setBounds(117, 44, 329, 36);
        ChangesListBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                EditEnterCommit(event);
            }
        });
        ChangesListBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent event) {
                ListBoxExit(event);
            }
        });
        ChangesListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                ListBoxMouseUp(event);
            }
        });
        ChangesListBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent event) {
                ListBoxKeyUp(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.ChangesListBox.OnDblClick = ListBoxDblClick;
        //  --------------- UNHANDLED ATTRIBUTE: this.ChangesListBox.ExtendedSelect = False;
        
        this.RequirementsEdit = new JTextField("");
        RequirementsEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent event) {
                ListBoxEditExit(event);
            }
        });
        RequirementsEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent event) {
                ListBoxEditKeyPress(event);
            }
        });
        this.RequirementsEdit.setVisible(false);
        
        this.ChangesEdit = new JTextField("");
        ChangesEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent event) {
                ListBoxEditExit(event);
            }
        });
        ChangesEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent event) {
                ListBoxEditKeyPress(event);
            }
        });
        this.ChangesEdit.setVisible(false);
        
        this.PanelRequirementsChanges = new JPanel(new MigLayout());
        
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setViewportView(RequirementsListBox);
        
        JScrollPane scroller2 = new JScrollPane();
        scroller2.setViewportView(ChangesListBox);
        
        PanelRequirementsChanges.add(RequirementsSpeedButton, "right, top");
        PanelRequirementsChanges.add(scroller1, "span, grow, push");
        PanelRequirementsChanges.add(ChangesSpeedButton, "right, top");
        PanelRequirementsChanges.add(scroller2, "span, grow, push");
        //PanelRequirementsChanges.add(RequirementsEdit, gridBagConstraints4);
        //PanelRequirementsChanges.add(ChangesEdit, gridBagConstraints5);
        
        this.ReplyButton = new JButton("Reply");
        
        Image CommandSpeedButtonImage = toolkit.createImage("resources/RuleEditorForm_CommandSpeedButton.png");
        this.CommandSpeedButton = new JButton("Command", new ImageIcon(CommandSpeedButtonImage));
        CommandSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.CommandSpeedButton.setToolTipText("Browse all rules with this command");
        this.CommandSpeedButton.putClientProperty("tag", 1);
        
        Image MoveSpeedButtonImage = toolkit.createImage("resources/RuleEditorForm_MoveSpeedButton.png");
        this.MoveSpeedButton = new JButton("Move", new ImageIcon(MoveSpeedButtonImage));
        MoveSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.MoveSpeedButton.setToolTipText("Browse all rules with this move");
        this.MoveSpeedButton.putClientProperty("tag", 3);
        
        Image ContextSpeedButtonImage = toolkit.createImage("resources/RuleEditorForm_ContextSpeedButton.png");
        this.ContextSpeedButton = new JButton("Context", new ImageIcon(ContextSpeedButtonImage));
        this.ContextSpeedButton.putClientProperty("tag", 0);
        ContextSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.ContextSpeedButton.setToolTipText("Browse all rules with this context");
        
        this.RuleNumberLabel = new JLabel("#1234");
        this.RuleNumberLabel.setToolTipText("The index of the edited rule in the table");

        Image ReplyButtonImage = toolkit.createImage("resources/ConsoleForm_replyPicture.png");
        this.replyPicture = new ImagePanel(new ImageIcon(ReplyButtonImage));
        this.replyPicture.setPreferredSize(new Dimension(16, 16));
        this.ReplyButton.setToolTipText("Test saying the reply");
        ReplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                replyPictureMouseUp(event);
            }
        });
        
        this.ContextEdit = new JTextField("");
        ContextEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                EditEnterCommit(event);
            }
        });
        
        this.CommandEdit = new JTextField("");
        CommandEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                EditEnterCommit(event);
            }
        });
        
        this.ReplyMemo = new JTextArea();
        ReplyMemo.setWrapStyleWord(true);
        ReplyMemo.setLineWrap(true);
        ReplyMemo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                EditEnterCommit(event);
            }
        });
        ReplyMemo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                ReplyMemoMouseUp(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.ReplyMemo.ScrollBars = ssVertical;
        
        this.MoveEdit = new JTextField("");
        MoveEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                EditEnterCommit(event);
            }
        });
        
        this.PanelRest = new JPanel(new MigLayout());
        
        this.PanelRest.add(new JLabel("Rule number:"), "right");
        this.PanelRest.add(RuleNumberLabel, "wrap");
        
        this.PanelRest.add(ContextSpeedButton, "right, top");
        this.PanelRest.add(ContextEdit, "spanx, growx");
        
        this.PanelRest.add(CommandSpeedButton, "right, top");
        this.PanelRest.add(CommandEdit, "spanx, growx");
        
        //this.PanelRest.add(replyPicture, "flowx");
        ReplyButton.setIcon(replyPicture.imageIcon);
        this.PanelRest.add(ReplyButton, "right, top");
        JScrollPane scroller3 = new JScrollPane();
        scroller3.setViewportView(ReplyMemo);
        this.PanelRest.add(scroller3, "span, grow, push");
        
        this.PanelRest.add(MoveSpeedButton, "right, top");
        this.PanelRest.add(MoveEdit, "spanx, growx");

        SplitterRequirementsChanges.setTopComponent(PanelRest);
        SplitterRequirementsChanges.setBottomComponent(PanelRequirementsChanges);
        
        this.PanelEditor = new JPanel(new BorderLayout());
        // -- this.PanelEditor.setLayout(new BoxLayout(this.PanelEditor, BoxLayout.Y_AXIS));
        this.PanelEditor.add(SplitterRequirementsChanges, BorderLayout.CENTER);
        
        this.ListPages = new JTabbedPane();
        this.ListPages.setTabPlacement(JTabbedPane.BOTTOM);
        
        
        this.TabSheetTable = new JPanel(new BorderLayout());
        
        this.RuleGrid = new JTable(new RuleTableModel());
        this.RuleGridSelectionModel = new RuleListSelectionModel();
        this.RuleGrid.setSelectionModel(RuleGridSelectionModel);
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.OnDrawCell = RuleGridDrawCell;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.ColWidths = ['', '', '6', '0', '4', '6'];
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.GridLineWidth = 0;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.VisibleRowCount = 1;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.RowCount = 2;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.VisibleColCount = 4;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.ColCount = 6;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.DefaultRowHeight = 16;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.FixedColor = clBtnFace;
        RuleGrid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                RuleGridMouseUp(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.ScrollBars = ssAutoBoth;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.FixedCols = 0;
        RuleGrid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent event) {
                RuleGridMouseDown(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.Options = [goFixedVertLine, goFixedHorzLine, goVertLine, goHorzLine, goColSizing, goRowSelect];
        
        JScrollPane ruleGridScroller = new JScrollPane();
        ruleGridScroller.setViewportView(RuleGrid);
        this.TabSheetTable.add(ruleGridScroller, BorderLayout.CENTER);
        this.ListPages.addTab(" Table", null, TabSheetTable);
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetTable.Width = 522;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetTable.y = 2;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetTable.Height = 1;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetTable.x = 2;
        
        this.TabSheetMap = new JPanel(new BorderLayout());
        this.MapImage = new MapPanel(); // No image was set
        
        this.MapImage.setSize(new Dimension(1000, 1000));
        this.MapImage.setPreferredSize(new Dimension(1000, 1000));
        MapImage.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent event) {
                MapImageMouseMove(event);
            }
            public void mouseDragged(java.awt.event.MouseEvent event) {
                MapImageMouseMove(event);
            }
        });
        MapImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                MapImageMouseUp(event);
            }
            public void mousePressed(java.awt.event.MouseEvent event) {
                MapImageMouseDown(event);
            }
        });
        
        this.PanelMap = new JPanel(new BorderLayout());
        // -- this.PanelMap.setLayout(new BoxLayout(this.PanelMap, BoxLayout.Y_AXIS));
        this.MapScroller = new JScrollPane();
        this.MapScroller.setViewportView(MapImage);
        this.MapScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.MapScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.PanelMap.add(MapScroller, BorderLayout.CENTER);
        
        this.TabSheetMap.add(PanelMap, BorderLayout.CENTER);
        this.ListPages.addTab("  Map", null, TabSheetMap);
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetMap.Width = 522;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetMap.y = 2;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetMap.Height = 1;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetMap.x = 2;
        
        this.TabSheetBrowse = new JPanel(new BorderLayout());
        this.SplitterLists = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterLists.MinSize = 80;
        SplitterLists.setDividerLocation(300);
        
        this.firstListBoxImage = new ImagePanel(); // No image was set
        this.firstListBoxImage.setPreferredSize(new Dimension(16, 16));
        firstListBoxImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                firstListBoxImageClick(event);
            }
        });
        
        this.firstListBoxLabel = new JLabel("Contexts");
        
        this.FirstListBoxModel = new DefaultListModel();
        this.FirstListBox = new JList(FirstListBoxModel);
        this.FirstListBox.setFixedCellHeight(16);
        //  --------------- UNHANDLED ATTRIBUTE: this.FirstListBox.Style = lbOwnerDrawFixed;
        FirstListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent event) {
                FirstListBoxMouseDown(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.FirstListBox.Sorted = True;
        //  --------------- UNHANDLED ATTRIBUTE: this.FirstListBox.OnDrawItem = FirstListBoxDrawItem;
        
        this.PanelFirstList = new JPanel(new BorderLayout());
        
        // -- this.PanelFirstList.setLayout(new BoxLayout(this.PanelFirstList, BoxLayout.Y_AXIS));
        
        this.PanelFirstListForLabel = new JPanel(new FlowLayout());
        this.PanelFirstListForLabel.add(firstListBoxImage);
        this.PanelFirstListForLabel.add(firstListBoxLabel);
        this.PanelFirstList.add(PanelFirstListForLabel, BorderLayout.NORTH);
        
        JScrollPane scroller4 = new JScrollPane();
        scroller4.setBounds(4, 20, 245, 245);
        scroller4.setViewportView(FirstListBox);
        this.PanelFirstList.add(scroller4, BorderLayout.CENTER);

        SplitterLists.setLeftComponent(PanelFirstList);

        this.SecondListBoxImage = new ImagePanel(); // No image was set
        this.SecondListBoxImage.setPreferredSize(new Dimension(16, 16));
        
        this.SecondListBoxLabel = new JLabel("Commands");
        
        this.SecondListBoxModel = new DefaultListModel();
        this.SecondListBox = new JList(SecondListBoxModel);
        this.SecondListBox.setFixedCellHeight(16);
        //  --------------- UNHANDLED ATTRIBUTE: this.SecondListBox.Style = lbOwnerDrawFixed;
        SecondListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent event) {
                SecondListBoxMouseDown(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.SecondListBox.Sorted = True;
        //  --------------- UNHANDLED ATTRIBUTE: this.SecondListBox.OnDrawItem = SecondListBoxDrawItem;
        
        this.PanelSecondList = new JPanel(new BorderLayout());
        // -- this.PanelSecondList.setLayout(new BoxLayout(this.PanelSecondList, BoxLayout.Y_AXIS));
        
        this.PanelSecondListForLabel = new JPanel(new FlowLayout());
        this.PanelSecondListForLabel.add(SecondListBoxImage);
        this.PanelSecondListForLabel.add(SecondListBoxLabel);
        this.PanelSecondList.add(PanelSecondListForLabel, BorderLayout.NORTH);

        JScrollPane scroller5 = new JScrollPane();
        scroller5.setBounds(4, 20, 313, 245);;
        scroller5.setViewportView(SecondListBox);
        this.PanelSecondList.add(scroller5, BorderLayout.CENTER);

        SplitterLists.setRightComponent(PanelSecondList);
        
        this.PanelLists = new JPanel(new BorderLayout());
        // -- this.PanelLists.setLayout(new BoxLayout(this.PanelLists, BoxLayout.Y_AXIS));
        this.PanelLists.add(SplitterLists, BorderLayout.CENTER);
        this.PanelLists.setBounds(0, 0, 522, 1);
        //  --------------- UNHANDLED ATTRIBUTE: this.PanelLists.OnResize = PanelListsResize;
        //  --------------- UNHANDLED ATTRIBUTE: this.PanelLists.setText('PanelLists');
        
        this.TabSheetBrowse.add(PanelLists, BorderLayout.CENTER);
        this.ListPages.addTab("Browser", null, TabSheetBrowse);
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetBrowse.Width = 522;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetBrowse.y = 2;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetBrowse.Height = 1;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetBrowse.x = 2;
        
        //  --------------- UNHANDLED ATTRIBUTE: this.ListPages.ActivePage = TabSheetMap;
        //  --------------- UNHANDLED ATTRIBUTE: this.ListPages.TabPosition = tpBottom;
        //  --------------- UNHANDLED ATTRIBUTE: this.ListPages.OnChange = ListPagesChange;
        //  --------------- UNHANDLED ATTRIBUTE: this.ListPages.TabIndex = 1;
        
        Image NewRuleButtonImage = toolkit.createImage("resources/RuleEditorForm_NewRuleButton.png");
        this.NewRuleButton = new JButton("New", new ImageIcon(NewRuleButtonImage));
        NewRuleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                NewRuleButtonClick(event);
            }
        });
        this.NewRuleButton.setToolTipText("Make a new rule");
        
        Image DuplicateRuleButtonImage = toolkit.createImage("resources/RuleEditorForm_DuplicateRuleButton.png");
        this.DuplicateRuleButton = new JButton("Duplicate", new ImageIcon(DuplicateRuleButtonImage));
        DuplicateRuleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                DuplicateRuleButtonClick(event);
            }
        });
        this.DuplicateRuleButton.setToolTipText("Duplicate the rule showing in the rule editor panel");
        
        Image DeleteRuleButtonImage = toolkit.createImage("resources/RuleEditorForm_DeleteRuleButton.png");
        this.DeleteRuleButton = new JButton("Delete", new ImageIcon(DeleteRuleButtonImage));
        DeleteRuleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                DeleteRuleButtonClick(event);
            }
        });
        this.DeleteRuleButton.setToolTipText("Delete all selected rules");
        
        Image MoveUpButtonImage = toolkit.createImage("resources/RuleEditorForm_MoveUpButton.png");
        this.MoveUpButton = new JButton("Raise", new ImageIcon(MoveUpButtonImage));
        MoveUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MoveUpButtonClick(event);
            }
        });
        this.MoveUpButton.setToolTipText("Raise all selected rules");
        
        Image MoveDownButtonImage = toolkit.createImage("resources/RuleEditorForm_MoveDownButton.png");
        this.MoveDownButton = new JButton("Lower", new ImageIcon(MoveDownButtonImage));
        MoveDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MoveDownButtonClick(event);
            }
        });
        this.MoveDownButton.setToolTipText("Lower all selected rules");
        
        Image insertSoundImage = toolkit.createImage("resources/RuleEditorForm_insertSound.png");
        this.insertSound = new JButton("Sound", new ImageIcon(insertSoundImage));
        insertSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                insertSoundClick(event);
            }
        });
        // ----- NumGlyphs was 2. Should split image file manually and use setPressedIcon(Icon)
        this.insertSound.setToolTipText("Insert a sound into a reply");
        
        Image InsertMusicButtonImage = toolkit.createImage("resources/RuleEditorForm_InsertMusicButton.png");
        this.InsertMusicButton = new JButton("Music", new ImageIcon(InsertMusicButtonImage));
        InsertMusicButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                InsertMusicButtonClick(event);
            }
        });
        this.InsertMusicButton.setToolTipText("Insert music into a reply");
        
        this.PanelButtonBar = new JPanel(new FlowLayout());
        this.PanelButtonBar.add(NewRuleButton);
        this.PanelButtonBar.add(DuplicateRuleButton);
        this.PanelButtonBar.add(DeleteRuleButton);
        this.PanelButtonBar.add(MoveUpButton);
        this.PanelButtonBar.add(MoveDownButton);
        this.PanelButtonBar.add(insertSound);
        this.PanelButtonBar.add(InsertMusicButton);
        
        this.PanelTop = new JPanel(new BorderLayout());
        // -- this.PanelTop.setLayout(new BoxLayout(this.PanelTop, BoxLayout.Y_AXIS));
        this.PanelTop.add(ListPages, BorderLayout.CENTER);
        this.PanelTop.add(PanelButtonBar, BorderLayout.SOUTH);
        SplitterEdit.setTopComponent(PanelTop);
        SplitterEdit.setBottomComponent(PanelEditor);
        
        //  ------- UNHANDLED TYPE TOpenDialog: WaveFileOpenDialog 
        //  --------------- UNHANDLED ATTRIBUTE: this.WaveFileOpenDialog.Title = 'Open existing file';
        //  --------------- UNHANDLED ATTRIBUTE: this.WaveFileOpenDialog.FilterIndex = 0;
        //  --------------- UNHANDLED ATTRIBUTE: this.WaveFileOpenDialog.y = 220;
        //  --------------- UNHANDLED ATTRIBUTE: this.WaveFileOpenDialog.Filter = 'Wave files|*.wav';
        //  --------------- UNHANDLED ATTRIBUTE: this.WaveFileOpenDialog.x = 508;
        
        //  ------- UNHANDLED TYPE TImageList: ImageList 
        //  --------------- UNHANDLED ATTRIBUTE: this.ImageList.y = 258;
        //  --------------- UNHANDLED ATTRIBUTE: this.ImageList.x = 508;
        
        RequirementsListBox.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        ChangesListBox.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        RequirementsEdit.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        ChangesEdit.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        ContextEdit.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        CommandEdit.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        ReplyMemo.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        MoveEdit.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        MapImage.addMouseListener(new MenuPopupMouseListener(this.getMapPopupMenu()));
        delphiPanel.add(SplitterEdit, BorderLayout.CENTER);
        return delphiPanel;
    }
    
        
    public void DeleteRuleButtonClick(ActionEvent event) {
    	this.MenuRuleDeleteClick(null);
    }
        
    public void DuplicateRuleButtonClick(ActionEvent event) {
    	this.MenuRuleDuplicateClick(null);
    }
        
    public void EditEnterCommit(FocusEvent event) {
        this.commitChangesToRule();
    }
        
    public void FirstListBoxMouseDown(MouseEvent event) {
        this.FirstListBox.repaint();
        this.loadSecondListBox();
    }
        
    public void FormKeyDown(KeyEvent event) {
    	int KeyCode = event.getKeyCode();
    	char key = event.getKeyChar();
        if (KeyCode == KeyEvent.VK_ESCAPE) {
            Application.ConsoleForm.speechSystem.haltSpeechAndSound();
        } else if (event.isControlDown() && ((key == 'c') || (key == 'C'))) {
            this.MenuEditCopyClick(null);
        } else if (event.isControlDown() && ((key == 'v') || (key == 'V'))) {
            this.MenuEditPasteClick(null);
        } else if (event.isControlDown() && ((key == 'x') || (key == 'X'))) {
            this.MenuEditCutClick(null);
        }
    }
        
    public void InsertMusicButtonClick(ActionEvent event) {
    	this.MenuEditInsertMusicClick(null);
    }
        
    public void ListBoxEditExit(FocusEvent event) {
        JTextField edit = (JTextField)event.getComponent();
        this.ListBoxEditExit(edit);
    }
        
	public void ListBoxEditExit(JTextField edit) {
		int oldIndex = 0;
		int oldTopIndex = 0;
		edit.setVisible(false);
		JList listBox = null;
		DefaultListModel listBoxModel = null;
		if (edit == this.RequirementsEdit) {
		    listBox = this.RequirementsListBox;
		    listBoxModel = this.RequirementsListBoxModel;
		} else if (edit == this.ChangesEdit) {
		    listBox = this.ChangesListBox;
		    listBoxModel = this.ChangesListBoxModel;
		} else {
		    return;
		}
		if ((this.indexEdited < 0) || (this.indexEdited > listBoxModel.size() - 1)) {
		    return;
		}
		if ((this.indexEdited == listBoxModel.size() - 1)) {
		    if ((!edit.getText().trim().equals(""))) {
		        listBoxModel.set(listBoxModel.size() - 1, edit.getText());
		        oldIndex = this.indexEdited;
		        oldTopIndex = listBox.getFirstVisibleIndex();
		        listBoxModel.addElement("");
		        this.commitChangesToRule();
		        //listBox.setSelectedIndex(index);
		        //if index - oldTopIndex + 1 > (listBox.clientHeight + listBox.itemHeight - 1) div listBox.itemHeight then
		        //	listBox.TopIndex := oldTopIndex + 1
		        //else
		        listBox.setSelectedIndex(oldIndex);
		        // PDF PORT MAYBE: listBox.TopIndex = oldTopIndex;
		        // MAYBE: listBox.ensureIndexIsVisible(oldTopIndex);
		    }
		} else {
		    oldIndex = this.indexEdited;
		    oldTopIndex = listBox.getFirstVisibleIndex();
		    TSDesiredStateVariableWrapper desiredStateWrapper = (TSDesiredStateVariableWrapper)listBoxModel.get(oldIndex);
		    if (desiredStateWrapper != null) {
		        listBoxModel.set(oldIndex, desiredStateWrapper.displayLeader() + edit.getText());
		    }
		    this.commitChangesToRule();
		    listBox.setSelectedIndex(oldIndex);
		 // PDF PORT MAYBE: listBox.TopIndex = oldTopIndex;
		}
		this.indexEdited = -1;
		edit.setText("");
    }
        
    public void ListBoxEditKeyPress(KeyEvent event) {
        JTextField edit = (JTextField) event.getSource();
        JList listBox = null;
        if (edit == this.RequirementsEdit) {
            listBox = this.RequirementsListBox;
        } else if (edit == this.ChangesEdit) {
            listBox = this.ChangesListBox;
        } else {
            return;
        }
        DefaultListModel listBoxModel = (DefaultListModel) listBox.getModel();
        if ((event.getKeyCode() == KeyEvent.VK_ENTER)) {
            //enter
        	// PDF PORT: IS this OK? Was this.FocusControl(listBox);
            listBox.requestFocusInWindow();
            this.ignoreNextEnter = true;
        } else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //escape
        	TSDesiredStateVariableWrapper desiredStateWrapper = null;
            if ((this.indexEdited >= 0) && (this.indexEdited < listBoxModel.size())) {
                desiredStateWrapper = (TSDesiredStateVariableWrapper)listBoxModel.get(this.indexEdited);
            }
            if (desiredStateWrapper != null) {
                edit.setText(desiredStateWrapper.variable.phrase);
            } else {
                edit.setText("");
            }
            listBox.requestFocusInWindow();
        }
        //
        //  if Key = Char(1) then
        //    begin
        //    if (ExtraChangesEntry.items.size() = 0) or
        //    		(NewExtraChanges.setText(ExtraChangesEntry.items[0])) then
        //    	begin
        //    	NewExtraChanges.setText("");
        //  		ResponseEditorForm.FocusControl(ExtraChangesEntry);
        //    	Key := Char(0);
        //    	end;
        //    end;
        // 
    }
        
    public void ListBoxExit(FocusEvent event) {
    	((JList)event.getComponent()).clearSelection();
    }

    public void ListBoxKeyUp(KeyEvent event) {
        JList listBox = (JList) event.getSource();
        ListBoxKeyUp(listBox, event.getKeyCode());
    }
        
    private void ListBoxKeyUp(JList listBox, int keyCode) {
        DefaultListModel listBoxModel = (DefaultListModel) listBox.getModel();
        if ((keyCode == KeyEvent.VK_DELETE) || (keyCode == KeyEvent.VK_BACK_SPACE)) {
            if (listBox.getSelectedIndex() < 0) {
                return;
            }
            if (listBox.getSelectedIndex() >= listBoxModel.size() - 1) {
                return;
            }
            listBoxModel.remove(listBox.getSelectedIndex());
            this.commitChangesToRule();
        } else if ((keyCode == KeyEvent.VK_ENTER)) {
            if (!this.ignoreNextEnter) {
                this.ListBoxDblClick(new ComponentEvent(listBox, 0));
            }
            this.ignoreNextEnter = false;
        }
    }

	public void ListBoxMouseUp(MouseEvent event) {
        this.lastClickAtLeft = false;
        JList listBox = (JList)event.getSource();
        DefaultListModel listBoxModel = (DefaultListModel)listBox.getModel();
		if ((listBox.getSelectedIndex() < 0) || (listBox.getSelectedIndex() >= listBoxModel.size())) {
            return;
        }
        if (listBox.getSelectedIndex() == listBoxModel.size() - 1) {
            this.ListBoxDblClick(event);
        } else if (event.getX() < 10) {
            int oldIndex = listBox.getSelectedIndex();
            int oldTopIndex = listBox.getFirstVisibleIndex();
            this.lastClickAtLeft = true;
            Object wrapperObject = listBoxModel.get(listBox.getSelectedIndex());
            TSDesiredStateVariableWrapper desiredStateWrapper = (TSDesiredStateVariableWrapper)wrapperObject;
            // temporarily invert to get new display string
            desiredStateWrapper.invertDesiredState();
            // listBoxModel.set(listBox.getSelectedIndex(), desiredStateWrapper.displayString());
            listBoxModel.set(listBox.getSelectedIndex(), desiredStateWrapper);
            // restore to current state
            desiredStateWrapper.invertDesiredState();
            // now create command for undo
            this.commitChangesToRule();
            listBox.setSelectedIndex(oldIndex);
            // PDF PORT MAYBE: listBox.TopIndex = oldTopIndex;
        }
    }
        
    public void MenuDisplayShowButtonBarClick(ActionEvent event) {
        this.commitChangesToRule();
        TSDomain.domain.options.showButtonBar = !TSDomain.domain.options.showButtonBar;
        this.MenuDisplayShowButtonBar.setSelected(TSDomain.domain.options.showButtonBar);
        this.PanelButtonBar.setVisible(TSDomain.domain.options.showButtonBar);
    }
        
    public void MenuEditClick(MouseEvent event) {
        // keep the undo menu up to date...
        this.commitChangesToRule();
    }
        
    public void MenuEditCopyClick(ActionEvent event) {
        String clip = "";
        
        if (this.rule == null) {
            // should not commit rule because may be in floating edit
            return;
        }
        clip = "";
        if (this.RequirementsListBox.isFocusOwner()) {
            if (this.RequirementsListBox.getSelectedIndex() < 0) {
                return;
            }
            if (this.RequirementsListBox.getSelectedIndex() < this.RequirementsListBoxModel.size()) {
                clip = this.RequirementsListBoxModel.get(this.RequirementsListBox.getSelectedIndex()).toString().trim();
            }
            if (clip.startsWith("~")) {
                clip = clip.substring(1);
            }
        } else if (this.ChangesListBox.isFocusOwner()) {
            if (this.ChangesListBox.getSelectedIndex() < 0) {
                return;
            }
            if (this.ChangesListBox.getSelectedIndex() < this.ChangesListBoxModel.size()) {
                clip = this.ChangesListBoxModel.get(this.ChangesListBox.getSelectedIndex()).toString().trim();
            }
            if (clip.startsWith("~")) {
                clip = clip.substring(2);
            }
        } else if (this.getFocusOwner() instanceof JTextArea) {
        	((JTextArea)(this.getFocusOwner())).copy();
        	return;
        } else if (this.getFocusOwner() instanceof JTextField) {
        	((JTextField)(this.getFocusOwner())).copy();
        	return;
        }
        StringSelection data = new StringSelection(clip);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(data, data);
    }
        
    public void MenuEditCutClick(ActionEvent event) {
        if (this.rule == null) {
            // should not commit rule because may be in floating edit
            return;
        }
        String clip = "";
        int key = KeyEvent.VK_DELETE;
        if (this.RequirementsListBox.isFocusOwner()) {
            if (this.RequirementsListBox.getSelectedIndex() < this.RequirementsListBoxModel.size()) {
                if (this.RequirementsListBox.getSelectedIndex() < 0) {
                    return;
                }
                if (this.RequirementsListBox.getSelectedIndex() < this.RequirementsListBoxModel.size()) {
                    clip = this.RequirementsListBoxModel.get(this.RequirementsListBox.getSelectedIndex()).toString().trim();
                }
                if (clip.startsWith("~")) {
                    clip = clip.substring(1);
                }
                this.ListBoxKeyUp(this.RequirementsListBox, key);
            }
        } else if (this.ChangesListBox.isFocusOwner()) {
            if (this.ChangesListBox.getSelectedIndex() < this.ChangesListBoxModel.size()) {
                if (this.ChangesListBox.getSelectedIndex() < 0) {
                    return;
                }
                if (this.ChangesListBox.getSelectedIndex() < this.ChangesListBoxModel.size()) {
                    clip = this.ChangesListBoxModel.get(this.ChangesListBox.getSelectedIndex()).toString().trim();
                }
                if (clip.startsWith("~")) {
                    clip = clip.substring(2);
                }
                this.ListBoxKeyUp(this.ChangesListBox, key);
            }
        } else if (this.getFocusOwner() instanceof JTextArea) {
        	((JTextArea)(this.getFocusOwner())).cut();
        	return;
        } else if (this.getFocusOwner() instanceof JTextField) {
        	((JTextField)(this.getFocusOwner())).cut();
        	return;
        }
        StringSelection data = new StringSelection(clip);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(data, data);
    }
       
	public void MenuEditInsertMusicClick(ActionEvent event) {
        String fileNameWithPath = "";
        String shortFileName = "";
        
        this.commitChangesToRule();
        if (!this.ReplyMemo.isFocusOwner()) {
            UFileSupport.ShowMessage("The reply field must be selected to insert a music file.");
            return;
        }
        fileNameWithPath = UFileSupport.getFileOpenInfo(UFileSupport.kFileTypeMusic, UFileSupport.kNoSuggestedFile, "Choose a music file", UFileSupport.kOtherExtOK);
        if (fileNameWithPath.equals("")) {
            return;
        }
        shortFileName = UFileSupport.ExtractFileName(fileNameWithPath);
        // no longer trimming extensions
        this.ReplyMemo.replaceSelection(" {" + TSSpeechSystem.kPlayMusicMacroStart + shortFileName + "} ");
    }
        
    public void MenuEditInsertPictureClick(ActionEvent event) {
        String fileNameWithPath = "";
        String shortFileName = "";
        
        this.commitChangesToRule();
        if (!this.ReplyMemo.isFocusOwner()) {
            UFileSupport.ShowMessage("The reply field must be selected to insert a picture file.");
            return;
        }
        fileNameWithPath = UFileSupport.getFileOpenInfo(UFileSupport.kFileTypeBitmap, UFileSupport.kNoSuggestedFile, "Choose a bitmap file", UFileSupport.kOtherExtNotOK);
        if (fileNameWithPath.equals("")) {
            return;
        }
        shortFileName = UFileSupport.ExtractFileName(fileNameWithPath);
        // no longer abbreviating BMP extensions
        this.ReplyMemo.replaceSelection(" {" + TSSpeechSystem.kShowPictureMacroStart + shortFileName + "} ");
    }
        
    public void MenuEditInsertSoundClick(ActionEvent event) {
        String fileNameWithPath = "";
        String shortFileName = "";
        
        this.commitChangesToRule();
        if (!this.ReplyMemo.isFocusOwner()) {
            UFileSupport.ShowMessage("The reply field must be selected to insert a sound file.");
            return;
        }
        fileNameWithPath = UFileSupport.getFileOpenInfo(UFileSupport.kFileTypeSound, UFileSupport.kNoSuggestedFile, "Choose a sound file", UFileSupport.kOtherExtOK);
        if (fileNameWithPath.equals("")) {
            return;
        }
        // no longer removing WAV extension.
        shortFileName = UFileSupport.ExtractFileName(fileNameWithPath);
        this.ReplyMemo.replaceSelection(" {" + TSSpeechSystem.kPlaySoundMacroStart + shortFileName + "} ");
    }
        
    public void MenuEditLogFileClick(ActionEvent event) {
        this.commitChangesToRule();
        Application.ChangeLogForm.loadChangeLog();
        Application.ChangeLogForm.setVisible(true);
        Application.ChangeLogForm.repaint();
        Application.ChangeLogForm.scrollLogEndIntoView();
    }
        
    public void MenuEditPasteClick(ActionEvent event) {

        if (this.rule == null) {
            // should not commit rule because may be in floating edit
            return;
        }
        String clip = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();    
        Transferable clipData = clipboard.getContents(clipboard);
        if (clipData != null) {
          try {
            if (clipData.isDataFlavorSupported(DataFlavor.stringFlavor)) {
              clip = (String)(clipData.getTransferData(DataFlavor.stringFlavor));
            } else {
            	return;
            }
          } catch (UnsupportedFlavorException ufe) {
            System.err.println("Flavor unsupported: " + ufe);
            return;
          } catch (IOException ioe) {
            System.err.println("Data not available: " + ioe);
            return;
          }
        }

        if (this.RequirementsListBox.isFocusOwner()) {
            if (this.RequirementsListBoxModel.size() < 1) {
                return;
            }
            this.indexEdited = this.RequirementsListBox.getSelectedIndex();
            if (this.indexEdited < 0) {
                this.indexEdited = this.RequirementsListBoxModel.size() - 1;
            }
            if (this.indexEdited > this.RequirementsListBoxModel.size() - 1) {
                this.indexEdited = this.RequirementsListBoxModel.size() - 1;
            }
            this.RequirementsEdit.setText(clip);
            this.ListBoxEditExit(this.RequirementsEdit);
        } else if (this.ChangesListBox.isFocusOwner()) {
            if (this.ChangesListBoxModel.size() < 1) {
                return;
            }
            this.indexEdited = this.ChangesListBox.getSelectedIndex();
            if (this.indexEdited < 0) {
                this.indexEdited = this.ChangesListBoxModel.size() - 1;
            }
            if (this.indexEdited > this.ChangesListBoxModel.size() - 1) {
                this.indexEdited = this.ChangesListBoxModel.size() - 1;
            }
            this.ChangesEdit.setText(clip);
            this.ListBoxEditExit(this.ChangesEdit);
        } else if (this.getFocusOwner() instanceof JTextArea) {
            ((JTextArea)this.getFocusOwner()).replaceSelection(clip);
        } else if (this.getFocusOwner() instanceof JTextField) {
            // PDF PORT - removed unneeded parens
            ((JTextField)this.getFocusOwner()).replaceSelection(clip);
        } else {
        	Toolkit.getDefaultToolkit().beep();
        }

    }
        
    public void MenuEditPreferencesClick(ActionEvent event) {
        this.commitChangesToRule();
        if (Application.PreferencesForm == null) {
            return;
        }
        Application.PreferencesForm.options = TSDomain.domain.options.clone();
        Application.PreferencesForm.setVisible(true);
        if (Application.PreferencesForm.okPressed) {
            TSDomain.domain.options = Application.PreferencesForm.options;
            this.updateForChangeToDomainOptions();
        }
    }
        
    public void MenuEditRedoClick(ActionEvent event) {
        this.commitChangesToRule();
        TSDomain.domain.worldCommandList.redoLast();
    }
        
    public void MenuEditUndoClick(ActionEvent event) {
    	// PDF PORT: IS THIS A BUG in the original? Maybe can't redo that change?
        this.commitChangesToRule();
        TSDomain.domain.worldCommandList.undoLast();
    }
        
    public void MenuFileExitClick(ActionEvent event) {
        this.commitChangesToRule();
        if (!this.askForSaveWorldAndProceed()) {
            return;
        }
        if (!Application.ConsoleForm.askForSaveSessionAndProceed()) {
            return;
        }
        Application.ConsoleForm.cleanUpBeforeExit();
        Application.Terminate();
    }
        
    public void MenuFileExportClick(ActionEvent event) {
        SaveFileNamesStructure fileInfo = new SaveFileNamesStructure();
        
        this.commitChangesToRule();
        this.lastSaveProceeded = UFileSupport.getFileSaveInfo(UFileSupport.kFileTypeWorld, UFileSupport.kAskForFileName, "export" + "." + TSDomain.kWorldExtension, fileInfo);
        if (!this.lastSaveProceeded) {
            return;
        }
        try {
            UFileSupport.startFileSave(fileInfo);
            Application.ConsoleForm.reportMode("Saving");
            TSDomain.domain.world.saveWorldToFile(fileInfo.tempFile, TWorld.kSaveOnlySelectedRules);
            fileInfo.writingWasSuccessful = true;
        } finally {
            this.lastSaveProceeded = UFileSupport.cleanUpAfterFileSave(fileInfo);
            Application.ConsoleForm.reportMode("Running");
        }
    }
        
    public void MenuFileMergeWithWorldClick(ActionEvent event) {
        String fileNameWithPath = "";
        int oldRuleCount = 0;
        int oldVariablesCount = 0;
        int i = 0;
        TSNewRulesCommand newRulesCommand = new TSNewRulesCommand();
        TSRule rule = new TSRule();
        TSVariable variable = new TSVariable();
        
        this.commitChangesToRule();
        fileNameWithPath = UFileSupport.getFileOpenInfo(UFileSupport.kFileTypeWorld, TSDomain.domain.worldFileName, "Choose a world file to merge into this one", UFileSupport.kOtherExtNotOK);
        if (fileNameWithPath.equals("")) {
            return;
        }
        oldRuleCount = TSDomain.domain.world.rules.size();
        oldVariablesCount = TSDomain.domain.world.variables.size();
        newRulesCommand = new TSNewRulesCommand();
        newRulesCommand.creator = "merging " + UFileSupport.ExtractFileName(fileNameWithPath);
        Application.ConsoleForm.reportMode("Loading");
        try {
            UFileSupport.startWaitMessage("Opening " + UFileSupport.ExtractFileName(fileNameWithPath));
            TSDomain.domain.mergeWorld(fileNameWithPath);
        } catch (Exception E) {
            UFileSupport.stopWaitMessage();
            UFileSupport.ShowMessage(E.getMessage());
            UFileSupport.ShowMessage("Could not correctly merge in file " + fileNameWithPath);
            for (i = oldRuleCount; i < TSDomain.domain.world.rules.size(); i++) {
                // clear out those new rules and variables
                rule = TSDomain.domain.world.rules.get(i);
                TSDomain.domain.world.rules.remove(rule);
            }
            for (i = oldVariablesCount; i < TSDomain.domain.world.variables.size(); i++) {
                variable = TSDomain.domain.world.variables.get(i);
                TSDomain.domain.world.variables.remove(variable);
            }
            this.updateViews();
            return;
        }
        UFileSupport.stopWaitMessage();
        Application.ConsoleForm.reportMode("Running");
        Application.ConsoleForm.locationCacheValid = false;
        TSDomain.domain.world.deselectAllExcept(null);
        for (i = oldRuleCount; i < TSDomain.domain.world.rules.size(); i++) {
            // select new items
            rule = TSDomain.domain.world.rules.get(i);
            newRulesCommand.addRule(rule);
            rule.selected = true;
        }
        for (i = oldVariablesCount; i < TSDomain.domain.world.variables.size(); i++) {
            variable = TSDomain.domain.world.variables.get(i);
            variable.selected = true;
        }
        TSDomain.domain.worldCommandList.doCommand(newRulesCommand);
        if (TSDomain.domain.world.rules.size() > 0) {
            this.editRule(TSDomain.domain.world.rules.get(TSDomain.domain.world.rules.size() - 1));
        } else {
            this.editRule(null);
        }
        this.scrollGridSelectionsIntoView(kFromTop);
        this.updateForRuleChange();
        this.updateViews();
    }
        
    public void MenuFileNewWorldClick(ActionEvent event) {
        this.commitChangesToRule();
        if (!this.askForSaveWorldAndProceed()) {
            return;
        }
        if (!Application.ConsoleForm.askForSaveSessionAndProceed()) {
            return;
        }
        this.rule = null;
        TSDomain.domain.newWorld();
        this.updateForWorldFileChanged();
    }
        
    public void MenuFileOpenWorldClick(ActionEvent event) {
        String fileNameWithPath = "";
        
        this.commitChangesToRule();
        if (!this.askForSaveWorldAndProceed()) {
            return;
        }
        if (!Application.ConsoleForm.askForSaveSessionAndProceed()) {
            return;
        }
        fileNameWithPath = UFileSupport.getFileOpenInfo(UFileSupport.kFileTypeWorld, TSDomain.domain.worldFileName, "Choose a world file", UFileSupport.kOtherExtNotOK);
        if (fileNameWithPath.equals("")) {
            return;
        }
        this.openWorldFile(fileNameWithPath);
        TSDomain.domain.world.setInitialFocus();
        TSDomain.domain.world.updateAvailable();
        this.updateViews();
        Application.ConsoleForm.updateTitles();
        Application.ConsoleForm.speechSystem.haltSpeechAndSound();
        // turns it off
        Application.ConsoleForm.speechSystem.speakSound("music");
        if (TSDomain.domain.world.rules.size() > 0) {
            Application.ConsoleForm.speechSystem.doCommand(TSDomain.domain.world.rules.get(0).command.phrase);
        }
    }
        
    public void MenuFileSaveWorldAsClick(ActionEvent event) {
        SaveFileNamesStructure fileInfo = new SaveFileNamesStructure();
        
        this.commitChangesToRule();
        this.lastSaveProceeded = UFileSupport.getFileSaveInfo(UFileSupport.kFileTypeWorld, UFileSupport.kAskForFileName, TSDomain.domain.worldFileName, fileInfo);
        if (!this.lastSaveProceeded) {
            return;
        }
        try {
            UFileSupport.startFileSave(fileInfo);
            TSDomain.domain.saveWorld(fileInfo.tempFile);
            fileInfo.writingWasSuccessful = true;
        } finally {
            this.lastSaveProceeded = UFileSupport.cleanUpAfterFileSave(fileInfo);
        }
        TSDomain.domain.worldFileName = fileInfo.newFile;
        TSDomain.domain.options.mostRecentWorld = fileInfo.newFile;
        TSDomain.domain.resetWorldChangeCount();
        TSDomain.domain.worldCommandList.clear();
        this.updateMenus();
        Application.ConsoleForm.updateTitles();
    }
        
    public void MenuFileSaveWorldClick(ActionEvent event) {
        SaveFileNamesStructure fileInfo = new SaveFileNamesStructure();
        
        this.commitChangesToRule();
        if (UFileSupport.ExtractFileName(TSDomain.domain.worldFileName).toUpperCase().contains(TSDomain.kUnsavedWorldFileName.toUpperCase())) {
            this.MenuFileSaveWorldAsClick(null);
            return;
        }
        this.lastSaveProceeded = UFileSupport.getFileSaveInfo(UFileSupport.kFileTypeWorld, UFileSupport.kDontAskForFileName, TSDomain.domain.worldFileName, fileInfo);
        if (!this.lastSaveProceeded) {
            return;
        }
        try {
            UFileSupport.startFileSave(fileInfo);
            TSDomain.domain.saveWorld(fileInfo.tempFile);
            fileInfo.writingWasSuccessful = true;
        } finally {
            this.lastSaveProceeded = UFileSupport.cleanUpAfterFileSave(fileInfo);
        }
        TSDomain.domain.resetWorldChangeCount();
        TSDomain.domain.worldCommandList.clear();
        TSDomain.domain.options.mostRecentWorld = fileInfo.newFile;
        this.updateMenus();
    }

    //kludge for popup edits...
    
    public void openWorldFile(String fileNameWithPath) {
        try {
            UFileSupport.startWaitMessage("Opening " + UFileSupport.ExtractFileName(fileNameWithPath));
            this.lastChoice = null;
            this.previousChoice = null;
            Application.ConsoleForm.reportMode("Loading");
            TSDomain.domain.loadWorld(fileNameWithPath);
            UFileSupport.stopWaitMessage();
        } catch (Exception E) {
            UFileSupport.stopWaitMessage();
            UFileSupport.ShowMessage(E.getMessage());
            UFileSupport.ShowMessage("Could not load file " + fileNameWithPath);
            TSDomain.domain.newWorld();
        }  
        this.updateForWorldFileChanged();
        Application.ConsoleForm.reportMode("Running");
    }

 
    public void MenuHelpAboutClick(ActionEvent event) {
        this.commitChangesToRule();
        Application.ConsoleForm.MenuHelpAboutClick(null);
    }
        
    public void MenuHelpBasicConceptsClick(ActionEvent event) {
        this.commitChangesToRule();
        Application.HelpJump("A_summary_based_on_definitions");
    }
        
    public void MenuHelpContentsClick(ActionEvent event) {
        this.commitChangesToRule();
        Application.HelpJump("<Contents>");
    }
        
    public void MenuHelpEditingWorldsClick(ActionEvent event) {
        this.commitChangesToRule();
        Application.HelpJump("Editing_worlds");
    }
        
    public void MenuHelpRegisterClick(ActionEvent event) {
        this.commitChangesToRule();
        Application.ConsoleForm.MenuHelpRegisterClick(null);
    }
        
    public void MenuHelpTutorialClick(ActionEvent event) {
        this.commitChangesToRule();
        Application.HelpJump("Basic_Tutorial");
    }
        
    public void MenuMapLinkWizardClick(ActionEvent event) {
        this.commitChangesToRule();
        this.switchToPage(this.TabSheetMap);
        if (Application.LinkWizardForm.initializeAndCheckIfOKToUse()) {
        	Application.LinkWizardForm.setVisible(true);
        }
    }
        
    public void MenuMapQuickCommandsClick(ActionEvent event) {
        this.commitChangesToRule();
        this.switchToPage(this.TabSheetMap);
        if (Application.CommandWizardForm.initializeAndCheckIfOKToUse()) {
        	Application.CommandWizardForm.setVisible(true);
        }
    }
        
    public void MenuMapsQuickContextsClick(ActionEvent event) {
        this.commitChangesToRule();
        this.switchToPage(this.TabSheetMap);
        if (Application.ContextWizardForm.initializeAndCheckIfOKToUse()) {
        	Application.ContextWizardForm.setVisible(true);
        }
    }
        
    public void MenuMapsShowCommandsClick(ActionEvent event) {
        this.commitChangesToRule();
        TSDomain.domain.options.showCommandsInMap = !TSDomain.domain.options.showCommandsInMap;
        this.MenuMapsShowCommands.setSelected(TSDomain.domain.options.showCommandsInMap);
        if (!this.switchToPage(this.TabSheetMap)) {
            this.MapPaintBoxChanged();
        }
    }
        
    public void MenuOptionsShowRuleEditorClick(ActionEvent event) {
        this.commitChangesToRule();
        TSDomain.domain.options.showRuleEditor = !TSDomain.domain.options.showRuleEditor;
        this.MenuOptionsShowRuleEditor.setSelected(TSDomain.domain.options.showRuleEditor);
        if (TSDomain.domain.options.showRuleEditor) {
            this.PanelEditor.setVisible(true);
            this.PanelEditor.setEnabled(true);
            this.SplitterEdit.setDividerLocation(TSDomain.domain.options.editorPanelEditorHeight);
        } else {
            //TSDomain.domain.options.editorPanelEditorHeight = this.PanelEditor.getHeight();
            TSDomain.domain.options.editorPanelEditorHeight = this.SplitterEdit.getDividerLocation();
            this.PanelEditor.setVisible(false);
            this.PanelEditor.setEnabled(false);
        }
    }
        
    public void MenuRuleDeleteClick(ActionEvent event) {
        this.commitChangesToRule();
        if ((this.rule != null) && (this.rule.selected)) {
            this.editRule(null);
        }
        this.deleteSelectedRules();
        this.previousChoice = null;
        this.lastChoice = null;
        this.updateForRuleChange();
    }
        
    public void MenuRuleDuplicateClick(ActionEvent event) {
        TSRule newRule = new TSRule();
        TSNewRulesCommand newRulesCommand = new TSNewRulesCommand();
        
        this.commitChangesToRule();
        if (this.rule == null) {
            return;
        }
        newRulesCommand = new TSNewRulesCommand();
        newRulesCommand.creator = "duplicating";
        newRule = TSDomain.domain.world.newRule();
        newRulesCommand.addRule(newRule);
        newRule.setContext(this.rule.context.phrase);
        newRule.setCommand(this.rule.command.phrase);
        newRule.setReply(this.rule.reply);
        newRule.setMove(this.rule.move.phrase);
        newRule.setRequirements(this.rule.requirementsString);
        newRule.setChanges(this.rule.changesString);
        TSDomain.domain.world.deselectAllExcept(newRule);
        newRule.selected = true;
        TSDomain.domain.worldCommandList.doCommand(newRulesCommand);
        this.editRule(newRule);
        this.updateForRuleChange();
    }
        
    public void MenuRuleLowerClick(ActionEvent event) {
        this.commitChangesToRule();
        this.switchToPage(this.TabSheetTable);
        this.lowerSelectedRules();
    }
        
    public void MenuRuleNewClick(ActionEvent event) {
        TSRule newRule = new TSRule();
        TSVariable variable = new TSVariable();
        TSNewRulesCommand newRulesCommand = new TSNewRulesCommand();
        
        this.commitChangesToRule();
        newRulesCommand = new TSNewRulesCommand();
        newRule = TSDomain.domain.world.newRule();
        newRulesCommand.addRule(newRule);
        //
        //  if ListPages.getSelectedComponent() = TabSheetTable then
        //    begin
        //
        //    end
        // 	else if ListPages.getSelectedComponent() = TabSheetBrowse then
        //    begin
        //    end
        //	else if ListPages.getSelectedComponent() = TabSheetMap then
        //    begin
        //  	if lastChoice is TSVariable then
        //    	newRule.setContext(TSVariable(lastChoice).phrase)
        //  	else if lastChoice is TSRule then
        //    	newRule.setContext(TSRule(lastChoice).context.phrase);
        //    end;
        //    
        variable = TSDomain.domain.world.firstSelectedVariable();
        if (variable != null) {
            newRule.setContext(variable.phrase);
        } else if (this.rule != null) {
            newRule.setContext(this.rule.context.phrase);
        }
        TSDomain.domain.world.deselectAllExcept(newRule);
        newRule.selected = true;
        TSDomain.domain.worldCommandList.doCommand(newRulesCommand);
        this.editRule(newRule);
        if (!newRule.context.phrase.trim().equals("")) {
            this.CommandEdit.requestFocusInWindow();
        } else {
            this.ContextEdit.requestFocusInWindow();
        }
    }
        
    public void MenuRuleRaiseClick(ActionEvent event) {
        this.commitChangesToRule();
        this.switchToPage(this.TabSheetTable);
        this.raiseSelectedRules();
    }
        
    public void MenuRuleTestReplyClick(ActionEvent event) {
        boolean oldSpeak = false;
        boolean oldPlayMusic = false;
        boolean oldPlaySounds = false;
        
        this.commitChangesToRule();
        Application.ConsoleForm.speechSystem.haltSpeechAndSound();
        if (this.rule == null) {
            return;
        }
        // temporarily turn on these options to test the reply
        oldSpeak = TSDomain.domain.options.playerSpeak;
        oldPlaySounds = TSDomain.domain.options.playerPlaySounds;
        oldPlayMusic = TSDomain.domain.options.playerPlayMusic;
        TSDomain.domain.options.playerSpeak = true;
        TSDomain.domain.options.playerPlaySounds = true;
        TSDomain.domain.options.playerPlayMusic = true;
        try {
            Application.ConsoleForm.speechSystem.sayTextWithMacros(this.rule.reply);
        } finally {
            TSDomain.domain.options.playerSpeak = oldSpeak;
            TSDomain.domain.options.playerPlaySounds = oldPlaySounds;
            TSDomain.domain.options.playerPlayMusic = oldPlayMusic;
        }
    }
        
    public void MenuToolsGenerateJavaClick(ActionEvent event) {
        boolean response = UFileSupport.Confirm("StoryHarp will compile the world file " + "\n" + "\n" + "    " + TSDomain.domain.worldFileName + "\n" + "\n" + "into a Java applet source code file " + "\n" + "\n" + "    " + UFileSupport.GetCurrentDir() + "/Story.java" + "\n" + "\n" + "To produce a Java applet, you will need to compile" + "\n" + "the Java source with a Java development system." + "\n" + "\n" + "See the help system under \"Java\" for details. Proceed?");
        if (!response) {
            return;
        }
        TSJavaWriter javaWriter = new TSJavaWriter();
        javaWriter.writeJavaProgram(TSDomain.domain.world, "Story.java");
        UFileSupport.ShowMessage("File " + UFileSupport.GetCurrentDir() + "/Story.java was written.");
    }
        
    public void MenuToolsSearchClick(ActionEvent event) {
        this.commitChangesToRule();
        Application.SearchDialog.setVisible(true);
    }
        
    public void MenuWorldSwitchToPlayerClick(ActionEvent event) {
        this.commitChangesToRule();
        Application.ConsoleForm.setVisible(true);
    }
        
    public void MoveDownButtonClick(ActionEvent event) {
    	this.MenuRuleLowerClick(null);
    }
        
    public void MoveUpButtonClick(ActionEvent event) {
    	this.MenuRuleRaiseClick(null);
    }
        
    public void NewRuleButtonClick(ActionEvent event) {
    	this.MenuRuleNewClick(null);
    }
        
    public void PopupNewCommandClick(ActionEvent event) {
        TSRule rule = new TSRule();
        TSRule newRule = new TSRule();
        TSNewRulesCommand newRulesCommand = new TSNewRulesCommand();
        TSVariable variable = new TSVariable();
        int i = 0;
        int newRuleCount = 0;
        
        if (Application.terminated) {
            return;
        }
        this.commitChangesToRule();
        while (TSDomain.domain.world.findVariable("new command " + String.valueOf(this.numNewCommandsMadeByPopupMenuThisSession)) != null) {
            this.numNewCommandsMadeByPopupMenuThisSession += 1;
        }
        newRule = null;
        newRuleCount = 0;
        for (i = 0; i < TSDomain.domain.world.variables.size(); i++) {
            variable = TSDomain.domain.world.variables.get(i);
            if (variable.selected) {
                newRulesCommand = new TSNewRulesCommand();
                newRule = TSDomain.domain.world.newRule();
                newRulesCommand.addRule(newRule);
                newRule.setContext(variable.phrase);
                newRule.setCommand("new command " + String.valueOf(this.numNewCommandsMadeByPopupMenuThisSession));
                newRule.setReply("Nothing happens.");
                newRule.position = new Point(this.lastMapMouseDownPosition.x, this.lastMapMouseDownPosition.y + 30 * newRuleCount);
                TSDomain.domain.worldCommandList.doCommand(newRulesCommand);
                newRuleCount += 1;
            }
        }
        for (i = 0; i < TSDomain.domain.world.rules.size(); i++) {
            rule = TSDomain.domain.world.rules.get(i);
            if (rule.selected) {
                newRulesCommand = new TSNewRulesCommand();
                newRule = TSDomain.domain.world.newRule();
                newRulesCommand.addRule(newRule);
                newRule.setContext(rule.context.phrase);
                newRule.setCommand("new command " + String.valueOf(this.numNewCommandsMadeByPopupMenuThisSession));
                newRule.setReply("Nothing happens.");
                newRule.position = new Point(this.lastMapMouseDownPosition.x, this.lastMapMouseDownPosition.y + 30 * newRuleCount);
                TSDomain.domain.worldCommandList.doCommand(newRulesCommand);
                newRuleCount += 1;
            }
        }
        if (newRule == null) {
        	UFileSupport.ShowMessage("To make a new command," + "\n" + "select at least one context or command" + "\n" + "and right-click where you want to place the new command.");
            this.MapPaintBoxChanged();
            return;
        }
        TSDomain.domain.world.deselectAllExcept(newRule);
        newRule.selected = true;
        this.editRule(newRule);
        this.CommandEdit.requestFocusInWindow();
        this.CommandEdit.selectAll();
        this.MapPaintBoxChanged();
    }
        
    public void PopupNewContextClick(ActionEvent event) {
        TSRule newRule = new TSRule();
        TSNewRulesCommand newRulesCommand = new TSNewRulesCommand();
        
        if (Application.terminated) {
            return;
        }
        this.commitChangesToRule();
        newRulesCommand = new TSNewRulesCommand();
        newRule = TSDomain.domain.world.newRule();
        newRulesCommand.addRule(newRule);
        while (TSDomain.domain.world.findVariable("new context " + String.valueOf(this.numNewContextsMadeByPopupMenuThisSession)) != null) {
            this.numNewContextsMadeByPopupMenuThisSession += 1;
        }
        newRule.setContext("new context " + String.valueOf(this.numNewContextsMadeByPopupMenuThisSession));
        newRule.setCommand("look");
        newRule.setReply("There is nothing of interest here.");
        newRule.position = new Point(this.lastMapMouseDownPosition.x + 30, this.lastMapMouseDownPosition.y + 30);
        newRule.context.position = this.lastMapMouseDownPosition;
        TSDomain.domain.world.deselectAllExcept(newRule);
        newRule.selected = true;
        TSDomain.domain.worldCommandList.doCommand(newRulesCommand);
        this.editRule(newRule);
        this.ContextEdit.requestFocusInWindow();
        this.ContextEdit.selectAll();
        this.MapPaintBoxChanged();
    }
        
    public void PopupNewLinkClick(ActionEvent event) {
        TSDraggableObject draggableNode = new TSDraggableObject();
        TSVariable contextToMoveTo = new TSVariable();
        TSNewRulesCommand newRulesCommand = new TSNewRulesCommand();
        TSVariable variable = new TSVariable();
        TSRule rule = new TSRule();
        TSRule newRule = new TSRule();
        TSMapView mapView = new TSMapView();
        TSVariableDisplayOptions displayOptions = new TSVariableDisplayOptions();
        int i = 0;
        boolean atLeastOneRuleChanged = false;
        
        if (Application.terminated) {
            return;
        }
        this.commitChangesToRule();
        mapView = this.currentGraphView();
        if (mapView == null) {
            return;
        }
        for (i = 0; i <= 5; i++) {
            displayOptions.displayOptions[i] = false;
        }
        displayOptions.displayOptions[TSVariableDisplayOptions.kRuleContext] = true;
        displayOptions.displayOptions[TSVariableDisplayOptions.kRuleMove] = true;
        displayOptions.displayOptions[TSVariableDisplayOptions.kRuleCommand] = this.MenuMapsShowCommands.isSelected();
        draggableNode = mapView.nearestNode(this.lastMapMouseDownPosition, displayOptions);
        if ((draggableNode == null) || !(draggableNode instanceof TSVariable)) {
        	UFileSupport.ShowMessage("To build a link," + "\n" + "select at least one context or command" + "\n" + "and right-click on a context.");
            this.MapPaintBoxChanged();
            return;
        }
        contextToMoveTo = (TSVariable)draggableNode;
        newRule = null;
        for (i = 0; i < TSDomain.domain.world.variables.size(); i++) {
            variable = (TSVariable)(TSDomain.domain.world.variables.get(i));
            if (variable.selected) {
                newRulesCommand = new TSNewRulesCommand();
                newRule = TSDomain.domain.world.newRule();
                newRulesCommand.addRule(newRule);
                newRule.setContext(variable.phrase);
                newRule.setCommand("move to " + contextToMoveTo.phrase);
                newRule.setReply("You move to " + contextToMoveTo.phrase + ".");
                newRule.setMove(contextToMoveTo.phrase);
                newRule.position.x = (variable.position.x + contextToMoveTo.position.x) / 2;
                newRule.position.y = (variable.position.y + contextToMoveTo.position.y) / 2;
                TSDomain.domain.worldCommandList.doCommand(newRulesCommand);
            }
        }
        atLeastOneRuleChanged = false;
        for (i = 0; i < TSDomain.domain.world.rules.size(); i++) {
            rule = TSDomain.domain.world.rules.get(i);
            if (rule.selected) {
                if (contextToMoveTo.phrase != rule.move.phrase) {
                    TSDomain.domain.worldCommandList.ruleFieldChange(rule, TSVariableDisplayOptions.kRuleMove, contextToMoveTo.phrase);
                }
                atLeastOneRuleChanged = true;
            }
        }
        if (newRule != null) {
            TSDomain.domain.world.deselectAllExcept(newRule);
            newRule.selected = true;
            this.editRule(newRule);
            this.CommandEdit.requestFocusInWindow();
            this.CommandEdit.selectAll();
        }
        if ((newRule != null) || (atLeastOneRuleChanged)) {
            this.MapPaintBoxChanged();
        } else {
        	UFileSupport.ShowMessage("To build a link," + "\n" + "select at least one context or command" + "\n" + "and right-click on a context.");
            this.MapPaintBoxChanged();
        }
    }
        
    public void ReplyMemoMouseUp(MouseEvent event) {
        // more fine grained tracking of changes to this field...
        this.commitChangesToRule();
    }
        
    public void RuleGridMouseDown(MouseEvent event) {
        if (this.RuleGrid.getSelectedRow() == -1) {
            //if ssCtrl in Shift then
            // 	begin
            //	RuleGrid.beginDrag(true);
            //		RuleGrid.FGridState := gsNormal;
            //	end;
            return;
        }
        if (this.RuleGrid.getSelectedRow() > TSDomain.domain.world.rules.size()) {
            return;
        }
        //RuleGrid.beginDrag(false);
        this.commitChangesToRule();
        int index = this.RuleGrid.getSelectedRow();
        TSRule rule = TSDomain.domain.world.rules.get(index);
        boolean mustRedraw = false;
        if (event.isShiftDown()) {
            if ((this.lastSingleRuleIndex >= 0) && (this.lastSingleRuleIndex <= TSDomain.domain.world.rules.size() - 1) && (this.lastSingleRuleIndex != index)) {
                // shift
                TSDomain.domain.world.deselectAllExcept(rule);
                if (this.lastSingleRuleIndex < index) {
                    for (int i = this.lastSingleRuleIndex; i <= index; i++) {
                        TSDomain.domain.world.rules.get(i).selected = true;
                    }
                } else if (this.lastSingleRuleIndex > index) {
                    for (int i = this.lastSingleRuleIndex; i >= index; i--) {
                        TSDomain.domain.world.rules.get(i).selected = true;
                    }
                }
                mustRedraw = true;
            }
            // control
        } else if (event.isControlDown()) {
            rule.selected = !rule.selected;
            // some sort of bug in updating occasionally otherwise
            mustRedraw = true;
            // just click
        } else {
            if (!rule.selected) {
                mustRedraw = TSDomain.domain.world.deselectAllExcept(rule);
                rule.selected = true;
                this.lastSingleRuleIndex = index;
            } else {
                // do nothing except maybe drag...
            }
        }
        if (rule.selected && (this.rule != rule) && !event.isControlDown() && !event.isShiftDown()) {
            this.editRule(rule);
            mustRedraw = true;
        }
        if (mustRedraw) {
            this.RuleGrid.repaint();
        }
    }
        
    public void RuleGridMouseUp(MouseEvent event) {
        // pass
        //	if ssCtrl in Shift then
        // 	begin
        //	RuleGrid.beginDrag(false);
        //		RuleGrid.FGridState := gsNormal;
        //		end;
        //  self.commitChangesToRule;
        //  if RuleGrid.row = 0 then exit;
        //  if RuleGrid.row > domain.world.rules.size() then exit;
        //  self.editRule(TSRule(domain.world.rules[RuleGrid.row - 1])); 
    }
        
    public void SecondListBoxMouseDown(MouseEvent event) {
        if (this.SecondListBox.getSelectedIndex() < 0) {
            return;
        }
        int index = this.SecondListBox.getSelectedIndex(); //.ItemAtPos(new Point(event.getX(), event.getY()), true);
        if (index < 0) {
            TSDomain.domain.world.deselectAllExcept(null);
            this.FirstListBox.repaint();
            this.SecondListBox.repaint();
            return;
        }
        TSRule rule = (TSRule)this.SecondListBoxModel.get(this.SecondListBox.getSelectedIndex());
        if (rule == null) {
            return;
        }
        if (event.isShiftDown()) {
            if ((this.lastBrowserSingleRuleIndex >= 0) && (this.lastBrowserSingleRuleIndex <= this.SecondListBoxModel.size() - 1) && (this.lastSingleRuleIndex != index)) {
                // shift
                TSDomain.domain.world.deselectAllExcept(rule);
                if (this.lastBrowserSingleRuleIndex < index) {
                    for (int i = this.lastBrowserSingleRuleIndex; i <= index; i++) {
                    	TSRule shiftRule = (TSRule)this.SecondListBoxModel.get(i);
                        shiftRule.selected = true;
                    }
                } else if (this.lastBrowserSingleRuleIndex > index) {
                    for (int i = this.lastBrowserSingleRuleIndex; i >= index; i--) {
                    	TSRule shiftRule = (TSRule)this.SecondListBoxModel.get(i);
                        shiftRule.selected = true;
                    }
                }
            }
            // control
        } else if (event.isControlDown()) {
            // just click
            rule.selected = !rule.selected;
        } else {
            if (!rule.selected) {
                TSDomain.domain.world.deselectAllExcept(rule);
                rule.selected = true;
                this.lastBrowserSingleRuleIndex = index;
            } else {
                // do nothing except maybe drag...
            }
        }
        if (rule.selected && (this.rule != rule) && !event.isControlDown() && !event.isShiftDown()) {
            this.editRule(rule);
        }
        this.FirstListBox.repaint();
        this.SecondListBox.repaint();
        this.updateForRuleChange();
    }
        
    public void SpeedButtonClick(ActionEvent event) {
        this.commitChangesToRule();
        this.switchToPage(this.TabSheetBrowse);
        JComponent button = (JComponent)(event.getSource());
        int tagValue = ((Integer)button.getClientProperty("tag"));
        this.setOrganizeByField(tagValue);
    }
        
    public void firstListBoxImageClick(MouseEvent event) {
        switch (this.organizeByField) {
        case TSVariableDisplayOptions.kRuleContext:
            this.setOrganizeByField(TSVariableDisplayOptions.kRuleCommand);
            break;
        case TSVariableDisplayOptions.kRuleCommand:
            this.setOrganizeByField(TSVariableDisplayOptions.kRuleMove);
            break;
        case TSVariableDisplayOptions.kRuleMove:
            this.setOrganizeByField(TSVariableDisplayOptions.kRuleRequirements);
            break;
        case TSVariableDisplayOptions.kRuleRequirements:
            this.setOrganizeByField(TSVariableDisplayOptions.kRuleChanges);
            break;
        case TSVariableDisplayOptions.kRuleChanges:
            this.setOrganizeByField(TSVariableDisplayOptions.kRuleContext);
            break;
        }
    }
    
    public void fillListBox(DefaultListModel listModel, Vector<TSDesiredStateVariableWrapper> list) {
        // OK for requirements because is parent class
    	listModel.clear();
        if (this.rule != null) {
            for (int i = 0; i < list.size(); i++) {
            	TSDesiredStateVariableWrapper wrapper = list.get(i);
            	listModel.addElement(wrapper);
            }
            // keep one empty one at the end for adding
            listModel.addElement("");
        }
    }
        
    public void insertSoundClick(ActionEvent event) {
        this.MenuEditInsertSoundClick(null);
    }
        
    public void replyPictureMouseUp(ActionEvent event) {
        this.MenuRuleTestReplyClick(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	// Force Application to make the forms
            	//TRuleEditorForm thisClass = new TRuleEditorForm();
            	TRuleEditorForm thisClass = Application.RuleEditorForm;
            	//Application.RuleEditorForm = thisClass;
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
                TSDomain.domain.loadWorld("data/House and Yard.wld");
                thisClass.adjustScrollBars();
            }
        });
    }

    
    public void updateRuleNumberLabel() {
        if (this.rule != null) {
            this.RuleNumberLabel.setText("#" + String.valueOf(TSDomain.domain.world.rules.indexOf(this.rule) + 1));
        } else {
            this.RuleNumberLabel.setText("");
        }
    }
    
    
    public void setButtonGlyphs() {
        if (Application.ConsoleForm == null) {
            return;
        }
        this.ContextSpeedButton.setIcon(Application.ConsoleForm.ContextButton.getIcon());
        this.CommandSpeedButton.setIcon(Application.ConsoleForm.CommandButton.getIcon());
        this.MoveSpeedButton.setIcon(Application.ConsoleForm.MoveButton.getIcon());
        this.ReplyButton.setIcon(Application.ConsoleForm.replyPicture.imageIcon);
        this.RequirementsSpeedButton.setIcon(Application.ConsoleForm.RequirementsButton.getIcon());
        this.ChangesSpeedButton.setIcon(Application.ConsoleForm.ChangesButton.getIcon());
    }
    
    public boolean askForSaveWorldAndProceed() {
        if (!TSDomain.domain.isWorldFileChanged()) {
            return true;
        }
        boolean confirmResult = UFileSupport.Confirm("Save changes to world " + UFileSupport.ExtractFileName(TSDomain.domain.worldFileName) + "?");
        if (confirmResult) {
            this.MenuFileSaveWorldClick(null);
            return this.lastSaveProceeded;
        } else {
        	return true;
        }
   }

    public void commitChangesToRule() {
        this.RequirementsEdit.setVisible(false);
        this.ChangesEdit.setVisible(false);
        if (this.rule == null) {
            return;
        }
        if (!this.ContextEdit.getText().equals(this.rule.context.phrase)) {
            TSDomain.domain.worldCommandList.ruleFieldChange(this.rule, TSVariableDisplayOptions.kRuleContext, this.ContextEdit.getText());
        }
        if (!this.CommandEdit.getText().equals(this.rule.command.phrase)) {
            TSDomain.domain.worldCommandList.ruleFieldChange(this.rule, TSVariableDisplayOptions.kRuleCommand, this.CommandEdit.getText());
        }
        if (!this.ReplyMemo.getText().equals(this.rule.reply)) {
            TSDomain.domain.worldCommandList.ruleFieldChange(this.rule, TSVariableDisplayOptions.kRuleReply, this.ReplyMemo.getText());
        }
        if (!this.MoveEdit.getText().equals(this.rule.move.phrase)) {
            TSDomain.domain.worldCommandList.ruleFieldChange(this.rule, TSVariableDisplayOptions.kRuleMove, this.MoveEdit.getText());
        }
        String requirementsLogicalStatement = this.logicalStatementForListBox(this.RequirementsListBox);
        if (!requirementsLogicalStatement.equals(this.rule.decompileRequirements())) {
            TSDomain.domain.worldCommandList.ruleFieldChange(this.rule, TSVariableDisplayOptions.kRuleRequirements, requirementsLogicalStatement);
        }
        String changesLogicalStatement = this.logicalStatementForListBox(this.ChangesListBox);
        if (!changesLogicalStatement.equals(this.rule.decompileChanges())) {
            TSDomain.domain.worldCommandList.ruleFieldChange(this.rule, TSVariableDisplayOptions.kRuleChanges, changesLogicalStatement);
        }
    }
    
    public String logicalStatementForListBox(JList listBox) {
        String result = "";
        // use 1 less then the end because last is always blank for adding
        for (int i = 0; i < listBox.getModel().getSize() - 1; i++) {
        	Object itemObject = listBox.getModel().getElementAt(i);
        	//System.out.println("item object: " + itemObject.getClass());
        	String item = "";
        	if (itemObject instanceof TSDesiredStateVariableWrapper) {
        		item = ((TSDesiredStateVariableWrapper)itemObject).displayString();
        	} else {
        		System.out.println("logicalStatementForListBox: Unexpected Different type");
        	}
            if (!result.equals("")) {
                result = result + " & " + item.trim();
            } else {
                result = item.trim();
            }
        }
        return result;
    }

    public void deleteSelectedRules() {
        TSDeleteRulesCommand command = new TSDeleteRulesCommand();
        for (int i = 0; i < TSDomain.domain.world.rules.size(); i++) {
        	TSRule rule = TSDomain.domain.world.rules.get(i);
            if ((rule.selected)) {
                command.addRule(rule, -1);
            }
        }
        if (command.ruleWrappers.size() > 0) {
            TSDomain.domain.worldCommandList.doCommand(command);
        }
    }
    
    public void raiseSelectedRules() {
        TSMoveRulesCommand command = new TSMoveRulesCommand();
        command.action = "raise";
        boolean moving = false;
        //skip first
        for (int i = 1; i < TSDomain.domain.world.rules.size(); i++) {
        	TSRule rule = TSDomain.domain.world.rules.get(i);
            if (rule.selected) {
                if (!moving) {
                	TSRule higherRule = TSDomain.domain.world.rules.get(i - 1);
                    if (!higherRule.selected) {
                        moving = true;
                    }
                }
                if (moving) {
                    command.addRule(rule, i - 1);
                }
            } else {
                moving = true;
            }
        }
        if (command.ruleWrappers.size() > 0) {
            TSDomain.domain.worldCommandList.doCommand(command);
        } 
    }
    
    public void lowerSelectedRules() {
        TSMoveRulesCommand command = new TSMoveRulesCommand();
        command.action = "lower";
        boolean moving = false;
        for (int i = TSDomain.domain.world.rules.size() - 2; i >= 0; i--) {
            //skip first
            TSRule rule = TSDomain.domain.world.rules.get(i);
            if (rule.selected) {
                if (!moving) {
                	TSRule lowerRule = TSDomain.domain.world.rules.get(i + 1);
                    if (!lowerRule.selected) {
                        moving = true;
                    }
                }
                if (moving) {
                    command.addRule(rule, i + 1);
                }
            } else {
                moving = true;
            }
        }
        if (command.ruleWrappers.size() > 0) {
            TSDomain.domain.worldCommandList.doCommand(command);
        } 
    }
    
    public Font fontForNameAndSize(String name, int size) {
    	if (size <= 0) size = TSDomain.kDefaultFontSize;
    	if (name == null || name.equals("")) name = TSDomain.kDefaultFontName;
    	return new Font(name, Font.PLAIN, size);
    }
    
    public void updateForChangeToDomainOptions() {
        // table -- also set default row height for table
    	Font tableFont = this.fontForNameAndSize(TSDomain.domain.options.tableFontName, TSDomain.domain.options.tableFontSize);
        this.RuleGrid.setFont(tableFont);
        // PDF PORT FIX: this.RuleGrid.DefaultRowHeight = this.RuleGrid.Canvas.TextHeight("W") + 2;
        Font mapFont = this.fontForNameAndSize(TSDomain.domain.options.mapFontName, TSDomain.domain.options.mapFontSize);
        this.MapImage.setFont(mapFont);
        Font browserFont = this.fontForNameAndSize(TSDomain.domain.options.browserFontName, TSDomain.domain.options.browserFontSize);
        this.FirstListBox.setFont(browserFont);
        this.SecondListBox.setFont(browserFont);
        // options menu
        this.MenuDisplayShowButtonBar.setSelected(TSDomain.domain.options.showButtonBar);
        this.PanelButtonBar.setVisible(TSDomain.domain.options.showButtonBar);
        if (this.MenuOptionsShowRuleEditor.isSelected() != TSDomain.domain.options.showRuleEditor) {
            this.MenuOptionsShowRuleEditor.setSelected(TSDomain.domain.options.showRuleEditor);
            if (TSDomain.domain.options.showRuleEditor) {
                this.PanelEditor.setVisible(true);
                this.PanelEditor.setEnabled(true);
            } else {
                this.PanelEditor.setVisible(false);
                this.PanelEditor.setEnabled(false);
            }
        }
        this.MenuMapsShowCommands.setSelected(TSDomain.domain.options.showCommandsInMap);
        if ((TSDomain.domain.options.buttonSymbols != this.buttonSymbols) || (this.startingUp)) {
            this.buttonSymbols = TSDomain.domain.options.buttonSymbols;
            if (!this.startingUp) {
            // PDF PORT: CREATES A STARTUP INITIALIZATION PROBLEM: 
            	Application.ConsoleForm.setButtonGlyphs(TSDomain.domain.options.buttonSymbols);
            }
            this.setButtonGlyphs();
            this.repaint();
            // replyPicture.visible := not buttonSymbols;
            this.setOrganizeByField(TSDomain.domain.options.browseBy);
        }
        if (TSDomain.domain.options.browseBy != this.organizeByField) {
            this.setOrganizeByField(TSDomain.domain.options.browseBy);
        }
        // updating
        this.updateForRuleChange();
        this.MapPaintBoxChanged();
    	this.adjustScrollBars();
        this.updateViews();
    }
    
    public void updateMenus() {
        // unfinished - maybe want better save as testing
        this.MenuFileSaveWorld.setEnabled(TSDomain.domain.isWorldFileChanged());
        this.MenuFileSaveWorldAs.setEnabled(true);
        this.updateMenusForUndoRedo();
    }
    
    public void updateViews() {
        if (Application.terminated) {
            return;
        }
        this.updateMenus();
        Application.ConsoleForm.updateViews();
        Application.PictureForm.updateViews();
    }
    
    public void updateForRuleChange() {
    	// PDF PORT: maybe need to do something with this to tell table it might have more rows or less rows
//        this.RuleGrid.RowCount = TSDomain.domain.world.rules.size() + 1;
//        if (this.RuleGrid.RowCount > 1) {
//            this.RuleGrid.FixedRows = 1;
//        }
    	this.RuleGridSelectionModel.fireValueChangedForAll();
        this.RuleGrid.repaint();
        Application.ConsoleForm.updateVariables();
        Application.ConsoleForm.VariablesListBox.repaint();
        this.MapPaintBoxChanged();
        this.setOrganizeByField(this.organizeByField);
        //if (domain.world.focus = nil) or (domain.world.focus = domain.world.emptyEntry) then
        //  domain.world.setInitialFocus;
        TSDomain.domain.world.updateAvailable();
        Application.ConsoleForm.updateViews();
        this.updateRuleNumberLabel();
    }
    
    public void updateMenusForUndoRedo() {
        if (TSDomain.domain.worldCommandList.isUndoEnabled()) {
            this.MenuEditUndo.setEnabled(true);
            this.MenuEditUndo.setText("Undo " + TSDomain.domain.worldCommandList.undoDescription());
        } else {
            this.MenuEditUndo.setEnabled(false);
            this.MenuEditUndo.setText("Can't undo");
        }
        if (TSDomain.domain.worldCommandList.isRedoEnabled()) {
            this.MenuEditRedo.setEnabled(true);
            this.MenuEditRedo.setText("Redo " + TSDomain.domain.worldCommandList.redoDescription());
        } else {
            this.MenuEditRedo.setEnabled(false);
            this.MenuEditRedo.setText("Can't redo");
        }
    }
    
    public void updateForWorldFileChanged() {
        this.rule = null;
        Application.ConsoleForm.locationCacheValid = false;
        Application.ConsoleForm.clearTranscript();
        this.updateForRuleChange();
        this.updateViews();
        this.MapPaintBoxChanged();
        this.adjustScrollBars();
        this.previousChoice = null;
        this.lastChoice = null;  
        this.editRule(null);
        Application.ConsoleForm.speechSystem.haltSpeechAndSound();
        // turns it off
        Application.ConsoleForm.speechSystem.speakSound("music");
    }    

    public TSMapView currentGraphView() {
        return TSDomain.domain.mapView;
    }
    
    public void mapChangedNotification(KfCommand command, KfCommandChangeType state) {
        this.MapPaintBoxChanged();
    }
    
    public void MapPaintBoxChanged() {
    	this.MapImage.repaint();
    }
     
    public void MapListChange(Object Sender) {
        this.lastChoice = null;
        this.previousChoice = null;
        this.MapPaintBoxChanged();
        this.adjustScrollBars();
    }    
    
    public void editRule(TSRule rule) {
        this.commitChangesToRule();
        this.rule = rule;
        this.loadAllRuleFields();
    }
    
    public void loadAllRuleFields() {
        int newIndex = 0;
        
        TSDomain.domain.beginUpdate();
        if (this.rule != null) {
            // if cacheInvalid then fillChoices;
            this.ContextEdit.setText(this.rule.context.phrase);
            this.CommandEdit.setText(this.rule.command.phrase);
            this.MoveEdit.setText(this.rule.move.phrase);
            this.ReplyMemo.setText(this.rule.reply);
            this.fillListBox(this.RequirementsListBoxModel, this.rule.requirements);
            this.fillListBox(this.ChangesListBoxModel, this.rule.changes);
        } else {
            this.ContextEdit.setText("");
            this.CommandEdit.setText("");
            this.MoveEdit.setText("");
            this.ReplyMemo.setText("");
            this.RequirementsListBoxModel.clear();
            this.ChangesListBoxModel.clear();
        }
        this.updateRuleNumberLabel();
        boolean enabled = this.rule != null;
        this.ContextEdit.setEnabled(enabled);
        this.CommandEdit.setEnabled(enabled);
        this.MoveEdit.setEnabled(enabled);
        this.ReplyMemo.setEnabled(enabled);
        this.RequirementsListBox.setEnabled(enabled);
        this.ChangesListBox.setEnabled(enabled);
        //RecordSelectionInformation(selectionInformation, self.ActiveControl as TWinControl);
        TSDomain.domain.endUpdate();
        newIndex = ((DefaultListModel)this.SecondListBox.getModel()).indexOf(this.rule);
        if (this.SecondListBox.getSelectedIndex() != newIndex) {
            this.SecondListBox.setSelectedIndex(newIndex);
        }
    }
    
    public void commandChanged(KfCommand command, KfCommandChangeType state) {
    	if (command.commandChangeType.equals("mapChanged")) {
    		this.mapChangedNotification(command, state);
    		return;
    	}
        if (state == KfCommandChangeType.commandDone) {
                TSDomain.domain.worldChangeDone();
                this.updateMenus();
        } else if (state == KfCommandChangeType.commandUndone) {
                TSDomain.domain.worldChangeUndone();
                this.updateMenus();
        } else {
        	System.out.println("Unexpected KfCommandChangeType: " + state);
        }
    }
    
    public void trackLastCommand() {
        this.editRule(this.lastCommand);
        this.updateForRuleChange();
    }

    public void selectEditorField(int field) {
        if (this.rule == null) {
            return;
        }
        switch (field) {
            case TSVariableDisplayOptions.kRuleContext:
                this.ContextEdit.requestFocusInWindow();
                this.ContextEdit.selectAll();
                break;
            case TSVariableDisplayOptions.kRuleCommand:
                this.CommandEdit.requestFocusInWindow();
                this.CommandEdit.selectAll();
                break;
            case TSVariableDisplayOptions.kRuleReply:
                this.ReplyMemo.requestFocusInWindow();
                this.ReplyMemo.selectAll();
                break;
            case TSVariableDisplayOptions.kRuleMove:
                this.MoveEdit.requestFocusInWindow();
                this.MoveEdit.selectAll();
                break;
            case TSVariableDisplayOptions.kRuleRequirements:
                this.RequirementsListBox.requestFocusInWindow();
                if (this.RequirementsListBoxModel.size() > 0) {
                    this.RequirementsListBox.setSelectedIndex(0);
                }
                break;
            case TSVariableDisplayOptions.kRuleChanges:
                this.ChangesListBox.requestFocusInWindow();
                if (this.ChangesListBoxModel.size() > 0) {
                    this.ChangesListBox.setSelectedIndex(0);
                }
                break;
        }
    }
    
    public void updateForFieldChange(int fieldType) {
        if (this.rule == null) {
            return;
        }
        switch (fieldType) {
            case TSVariableDisplayOptions.kRuleContext:
                this.ContextEdit.setText(this.rule.context.phrase);
                break;
            case TSVariableDisplayOptions.kRuleCommand:
                this.CommandEdit.setText(this.rule.command.phrase);
                break;
            case TSVariableDisplayOptions.kRuleReply:
                this.ReplyMemo.setText(this.rule.reply);
                break;
            case TSVariableDisplayOptions.kRuleMove:
                this.MoveEdit.setText(this.rule.move.phrase);
                break;
            case TSVariableDisplayOptions.kRuleRequirements:
                this.fillListBox(this.RequirementsListBoxModel, this.rule.requirements);
                break;
            case TSVariableDisplayOptions.kRuleChanges:
                this.fillListBox(this.ChangesListBoxModel, this.rule.changes);
                break;
        }
    }
    
    // table
    public void scrollGridSelectionsIntoView(boolean direction) {
        int firstSelectedRuleIndex = -1;
        if (direction == kFromBottom) {
            for (int i = TSDomain.domain.world.rules.size() - 1; i >= 0; i--) {
            	TSRule rule = TSDomain.domain.world.rules.get(i);
                if (!rule.selected) {
                    continue;
                }
                firstSelectedRuleIndex = i;
                break;
            }
        } else {
            for (int i = 0; i < TSDomain.domain.world.rules.size(); i++) {
            	TSRule rule = TSDomain.domain.world.rules.get(i);
                if (!rule.selected) {
                    continue;
                }
                firstSelectedRuleIndex = i;
                break;
            }
        }
        if (firstSelectedRuleIndex == -1) {
            return;
        }
//        if ((this.RuleGrid.TopRow <= firstSelectedRuleIndex) && (this.RuleGrid.TopRow + this.RuleGrid.VisibleRowCount > firstSelectedRuleIndex)) {
//            return;
//        }
//        if (direction == kFromBottom) {
//            this.RuleGrid.TopRow = Math.max(1, firstSelectedRuleIndex - this.RuleGrid.VisibleRowCount + 1);
//        } else {
//            this.RuleGrid.TopRow = Math.max(1, firstSelectedRuleIndex);
//        }
        // PDF PORT -- NO LONGER CAN DO SCROLLING DIRECTION?
        Rectangle rectangle = this.RuleGrid.getCellRect(firstSelectedRuleIndex, 0, true);
    }
        
    // ------------------------------------------------------ @Creation/destruction
    public void FormCreate() {
        this.numNewContextsMadeByPopupMenuThisSession = 1;
        this.numNewCommandsMadeByPopupMenuThisSession = 1;
        Application.setFormSize(this, TSDomain.domain.options.editorWindowRect);
        // PDF PORT
//        if (TSDomain.domain.options.editorPanelEditorHeight > 0) {
//            this.PanelEditor.Height = TSDomain.domain.options.editorPanelEditorHeight;
//            this.PanelEditor.y = this.ClientHeight - this.PanelEditor.Height;
//        }
//        if (TSDomain.domain.options.editorPanelRequirementsChangesHeight > 0) {
//            this.PanelRequirementsChanges.Height = TSDomain.domain.options.editorPanelRequirementsChangesHeight;
//            this.PanelRequirementsChanges.y = this.PanelEditor.ClientHeight - this.PanelRequirementsChanges.Height;
//        }
        switch (TSDomain.domain.options.pageShowing) {
            case TSDomain.kPageTable:
                this.ListPages.setSelectedComponent(this.TabSheetTable);
                break;
            case TSDomain.kPageMap:
                this.ListPages.setSelectedComponent(this.TabSheetMap);
                break;
            case TSDomain.kPageBrowser:
                this.ListPages.setSelectedComponent(this.TabSheetBrowse);
                break;
        }
        // PDF PORT
//        if (TSDomain.domain.options.editorPanelFirstListWidth > 0) {
//            this.PanelFirstList.Width = TSDomain.domain.options.editorPanelFirstListWidth;
//        }
        TSDomain.domain.worldCommandList.commandChangedListener = this;
        this.startingUp = true;
        this.updateForChangeToDomainOptions();
        this.startingUp = false;
    }
    
    public boolean switchToPage(JPanel newPage) {
        if (this.ListPages.getSelectedComponent() == newPage) {
            return false;
        }
        this.ListPages.setSelectedComponent(newPage);
        this.ListPagesChange(this);
        return true;
    }
    
    public void FormActivate(Object Sender) {
        if (Application.terminated) {
            return;
        }
        if (this.FirstListBoxModel.getSize() == 0) {
            //  self.updateForRuleChange;
            //	self.loadFirstListBox;
            //	self.loadSecondListBox;
            this.setOrganizeByField(this.organizeByField);
            //if secondListBox.items.size() > 0 then
        }
        this.wasLoaded = true;
        if (TSDomain.domain.world.rules.size() + 1 >= 2) {
            this.RuleGrid.RowCount = TSDomain.domain.world.rules.size() + 1;
        }
    }

    
//    public void WMEraseBkgnd(TWMEraseBkgnd m) {
//        // since we're going to be painting the whole form, handling this
//        // message will suppress the unnecessary repainting of the background
//        // which can result in flicker.
//        m.Result = UNRESOLVED.LRESULT(false);
//    }
    
    public void FormDeactivate(Object Sender) {
        //	UFileSupport.ShowMessage('deactivate');
        // unfinished self.makeChangeCommandIfNeeded;
        this.commitChangesToRule();
    }
    
//    public void WMDropFiles(TWMDropFiles Msg) {
//        String CFileName = "";
//        
//        try {
//            if (UNRESOLVED.DragQueryFile(Msg.Drop, 0, CFileName, UNRESOLVED.MAX_PATH) > 0) {
//                if (!CFileName.toUpperCase().endsWith(".WLD")) {
//                    return;
//                }
//                this.commitChangesToRule();
//                if (!this.askForSaveWorldAndProceed()) {
//                    return;
//                }
//                if (!Application.ConsoleForm.askForSaveSessionAndProceed()) {
//                    return;
//                }
//                this.openWorldFile(CFileName);
//                TSDomain.domain.world.setInitialFocus();
//                TSDomain.domain.world.updateAvailable();
//                this.updateViews();
//                Application.ConsoleForm.updateTitles();
//                Application.ConsoleForm.speechSystem.haltSpeechAndSound();
//                // turns it off
//                Application.ConsoleForm.speechSystem.speakSound("music");
//                if (TSDomain.domain.world.rules.size() > 0) {
//                    Application.ConsoleForm.speechSystem.doCommand((TSDomain.domain.world.rules.get(0)).command.phrase);
//                }
//                Msg.Result = 0;
//            }
//        } finally {
//            UNRESOLVED.DragFinish(Msg.Drop);
//        }
//    }
    

    public void MenuMapFontClick(Object Sender) {
        this.switchToPage(this.TabSheetMap);
    	Font newFont = JFontChooser.showDialog(null, "Choose a font for the player", this.MapImage.getFont());
        if (newFont != null) {
            this.MapImage.setFont(newFont);
            this.MapPaintBoxChanged();
            this.FirstListBox.setFont(newFont);
            this.SecondListBox.setFont(newFont);
            this.RuleGrid.setFont(newFont);
        }
    }
    
//    public void ListBoxDragOver(Object Sender, Object Source, int x, int y, TDragState State, boolean Accept) {
//        //pass
//        //
//        return Accept;
//    }
    
    public void EditDragDrop(Object Sender, Object Source, int x, int y) {
        if (Source == this.MapImage) {
            if (this.lastChoice == null) {
                return;
            }
            JTextField edit = (JTextField)Sender; // event.getSource();
            if (this.lastChoice instanceof TSRule) {
                edit.setText(((TSRule)this.lastChoice).command.phrase);
            } else {
                edit.setText(((TSVariable)this.lastChoice).phrase);
            }
        } else if (Source == this.FirstListBox) {
            if (this.FirstListBox.getSelectedIndex() < 0) {
                return;
            }
            JTextField edit = (JTextField)Sender; // event.getSource();
            // PDF PORT -- GUESSED AT CONVERSION
            edit.setText(this.FirstListBoxModel.get(this.FirstListBox.getSelectedIndex()).toString());
        }
        this.commitChangesToRule();
    }
    
//        public void listBoxNewStatement(TListBox listBox, String newStatement) {
//            if (listBox == this.RequirementsListBox) {
//                this.rule.setRequirements(newStatement);
//                this.fillListBox(listBox, this.rule.requirements);
//            } else if (listBox == this.ChangesListBox) {
//                this.rule.setChanges(newStatement);
//                this.fillListBox(listBox, this.rule.changes);
//            }
//            //not undoable
//            TSDomain.domain.worldChangeDone();
//            this.updateMenus();
//        }
    
    public void ListBoxDragDrop(Object Sender, Object Source, int x, int y) {
        String theText = "";
        JList listBox = (JList) Sender;
        DefaultListModel listBoxModel = (DefaultListModel) listBox.getModel();
        if (Source == this.MapImage) {
            if (this.lastChoice == null) {
                return;
            }
            if (this.lastChoice instanceof TSRule) {
                theText = ((TSRule)this.lastChoice).command.phrase;
            } else {
                theText = ((TSVariable)this.lastChoice).phrase;
            }
            listBoxModel.add(listBoxModel.size() - 1, theText);
            this.commitChangesToRule();
        } else if (Source == this.FirstListBox) {
            if (this.FirstListBox.getSelectedIndex() < 0) {
                return;
            }
            // PDF PORT: not so sure about list box contents and if string is OK
            theText = this.FirstListBoxModel.get(this.FirstListBox.getSelectedIndex()).toString();
            listBoxModel.add(listBoxModel.size() - 1, theText);
            this.commitChangesToRule();
        } else {
            return;
        }
    }

    
    public void positionEditForListBox(JTextField edit, JList listBox) {
        int tildeWidth = 0;
        
        int y = listBox.getY() + 2 + (listBox.getSelectedIndex() - listBox.getFirstVisibleIndex()) * listBox.getFixedCellHeight();
        // PDF PORT: tildeWidth = listBox.Canvas.TextWidth("~");
        int x = listBox.getX() + 2 + tildeWidth;
        int width = listBox.getWidth() - 4 - tildeWidth;
        int height = listBox.getFixedCellHeight() + 2;
        edit.setBounds(new Rectangle(x, y, width, height));
        edit.setVisible(true);
        edit.requestFocusInWindow();
    }
    
    public void ListBoxDblClick(ComponentEvent event) {
        if (this.lastClickAtLeft) {
            return;
        }
        this.indexEdited = -1;
        JList listBox = (JList)event.getSource();
        DefaultListModel listBoxModel = (DefaultListModel) listBox.getModel();
        JTextField edit = null;
        if (listBox == this.RequirementsListBox) {
            edit = this.RequirementsEdit;
        } else if (listBox == this.ChangesListBox) {
            edit = this.ChangesEdit;
        } else {
            return;
        }
        if ((listBox.getSelectedIndex() < 0) || (listBox.getSelectedIndex() >= listBoxModel.size())) {
            return;
        }
        if (listBox.getSelectedIndex() < listBoxModel.size() - 1) {
            Object wrapperObject = listBoxModel.get(listBox.getSelectedIndex());
            TSDesiredStateVariableWrapper desiredStateWrapper = (TSDesiredStateVariableWrapper)wrapperObject;
            edit.setText(desiredStateWrapper.variable.phrase);
        } else {
            edit.setText("");
        }
        this.indexEdited = listBox.getSelectedIndex();
        this.positionEditForListBox(edit, listBox);
    }
    
    public void ListPagesChange(Object Sender) {
        if (this.ListPages.getSelectedComponent() == this.TabSheetTable) {
            if ((this.rule == null) || !this.rule.selected) {
                // pass
            } else {
                //domain.world.deselectAllExcept(nil) pdf change - maybe contexts selected in map
                this.scrollGridSelectionsIntoView(kFromTop);
                this.RuleGrid.repaint();
            }
        } else if (this.ListPages.getSelectedComponent() == this.TabSheetMap) {
            this.MapPaintBoxChanged();
            this.scrollMapSelectionIntoView();
        } else if (this.ListPages.getSelectedComponent() == this.TabSheetBrowse) {
            this.setOrganizeByField(this.organizeByField);
        }
    }
    
//    public void EditDragOver(Object Sender, Object Source, int x, int y, TDragState State, boolean Accept) {
//        //pass
//        //
//        return Accept;
//    }
//    
//    public void TabSheetMapDragOver(Object Sender, Object Source, int x, int y, TDragState State, boolean Accept) {
//        //pass
//        //
//        return Accept;
//    }
//    
//    public void TabSheetMapDragDrop(Object Sender, Object Source, int x, int y) {
//        //pass
//        //
//    }
//
//    
//    public void ListPagesDragOver(Object Sender, Object Source, int x, int y, TDragState State, boolean Accept) {
//        // pass
//        //
//        return Accept;
//    }
//    
    public String lastChoiceText() {
        String result = "";
        if (this.lastChoice == null) {
            return result;
        }
        if (this.lastChoice instanceof TSVariable) {
            result = this.lastChoice.displayName();
            return result;
        }
        result = ((TSRule)this.lastChoice).command.phrase;
        return result;
    }
    
    public boolean makeChoice(TSDraggableObject choice, boolean multiSelect) {
        //whether must redraw
        boolean result = false;
        if (multiSelect) {
            if (choice != null) {
                choice.selected = !choice.selected;
            }
            result = true;
        } else {
            if ((choice == null) || !choice.selected) {
                result = TSDomain.domain.world.deselectAllExcept(choice);
                if (choice != null) {
                    choice.selected = true;
                }
            } else {
                // do nothing except maybe drag...
            }
        }
        if (this.lastChoice == choice) {
            return result;
        }
        result = true;
        if ((choice != null) && choice.selected) {
            this.previousChoice = this.lastChoice;
            this.lastChoice = choice;
            if (this.previousChoice instanceof TSRule) {
                this.previousChoice = choice;
            }
            if (this.lastChoice instanceof TSRule) {
                this.previousChoice = choice;
            }
        } else if ((choice != null) && !choice.selected) {
            if (this.previousChoice == choice) {
                this.previousChoice = null;
            }
            if (this.lastChoice == choice) {
                this.lastChoice = null;
            }
        }
        return result;
    }
    
    public void MapImageDblClick(Object Sender) {
        if (this.lastChoice == null) {
            //var
            //row: integer;
            //count: integer;
            //rule: TSRule;
            //ruleIndex: integer; 
            return;
        }
        if (this.lastChoice instanceof TSRule) {
            this.editRule((TSRule)this.lastChoice);
        }
        //
        //  else
        //    begin
        //    count := 1;
        //    ruleIndex := domain.world.rules.indexOf(self.rule);
        //    while (count <= domain.world.rules.size()) do
        //      begin
        //      row := (count + ruleIndex) mod domain.world.rules.size();
        //      rule := domain.world.rules[row];
        //      // unfinished - need to check requirements & changes
        //      if (rule.context.phrase = self.lastChoiceText) or
        //      		(rule.command.phrase = self.lastChoiceText) or
        //          (rule.move.phrase = self.lastChoiceText) then
        //        begin
        //      	self.editRule(rule);
        //        exit;
        //        end;
        //      inc(count);
        //      end;
        //    end;
        //    
    }
    
    public void searchForAndSelectRule(String aText, boolean ignoreCase, boolean goDown) {
        int row = 0;
        int count = 0;
        TSRule rule = new TSRule();
        int ruleIndex = 0;
        boolean match = false;
        String matchText = "";
        
        count = 1;
        ruleIndex = TSDomain.domain.world.rules.indexOf(this.rule);
        if (ignoreCase) {
            matchText = aText.toLowerCase();
        } else {
            matchText = aText;
        }
        while ((count <= TSDomain.domain.world.rules.size())) {
            if (goDown) {
                row = (ruleIndex + count) % TSDomain.domain.world.rules.size();
            } else {
                row = ((TSDomain.domain.world.rules.size() * 2) + (ruleIndex - count)) % TSDomain.domain.world.rules.size();
            }
            rule = TSDomain.domain.world.rules.get(row);
            if (ignoreCase) {
                // unfinished - need to check requirements & changes
                match = (rule.context.phrase.toLowerCase().contains(matchText)) || 
                (rule.command.phrase.toLowerCase().contains(matchText)) || 
                (rule.reply.toLowerCase().contains(matchText)) || 
                (rule.move.phrase.toLowerCase().contains(matchText)) || 
                (rule.requirementsString.toLowerCase().contains(matchText)) || 
                (rule.changesString.toLowerCase().contains(matchText));
            } else {
                match = (rule.context.phrase.contains(matchText)) || 
                (rule.command.phrase.contains(matchText)) || 
                (rule.reply.contains(matchText)) || 
                (rule.move.phrase.contains(matchText)) || 
                (rule.requirementsString.contains(matchText)) || 
                (rule.changesString.contains(matchText));
            }
            if (match) {
                TSDomain.domain.world.deselectAllExcept(rule);
                this.editRule(rule);
                this.updateForRuleChange();
                rule.selected = true;
                this.scrollGridSelectionsIntoView(kFromBottom);
                this.MapPaintBoxChanged();
                this.scrollMapSelectionIntoView();
                return;
            }
            count += 1;
        }
        UFileSupport.ShowMessage("Search string \"" + aText + "\" not found.");
    }
    
    // ------------------------------------------------------------------- @Browser
    public void loadFirstListBox() {
        this.FirstListBoxModel.clear();
        this.firstListBoxLabel.setText("All " + TSRule.headerForField(this.organizeByField).toLowerCase());
        if (!this.firstListBoxLabel.getText().endsWith("s")) {
            this.firstListBoxLabel.setText(this.firstListBoxLabel.getText() + "s");
        }
        for (int i = 0; i < TSDomain.domain.world.variables.size(); i++) {
        	TSVariable variable = TSDomain.domain.world.variables.get(i);
            if (variable.hasUseagesForField(this.organizeByField)) {
                this.FirstListBoxModel.addElement(variable);
            }
        }
    }
    
    public void loadSecondListBox() {
        int displayFieldType = 0;
        
        this.SecondListBoxModel.clear();
        if (this.organizeByField == TSVariableDisplayOptions.kRuleCommand) {
            displayFieldType = TSVariableDisplayOptions.kRuleContext;
            this.SecondListBoxImage.imageIcon = this.ContextSpeedButton.getIcon();
        } else {
            displayFieldType = TSVariableDisplayOptions.kRuleCommand;
            this.SecondListBoxImage.imageIcon = this.CommandSpeedButton.getIcon();
        }
        String selectedItemString = TSRule.headerForField(this.organizeByField).toLowerCase();
        if (selectedItemString.endsWith("s")) {
            // remove plural 's' for singular use
            selectedItemString = selectedItemString.substring(0, selectedItemString.length() - 1);
        }
        this.SecondListBoxLabel.setText(TSRule.headerForField(displayFieldType) + "s with selected " + selectedItemString);
        if (this.FirstListBox.getSelectedIndex() < 0) {
            return;
        }
        TSVariable variable = (TSVariable)this.FirstListBoxModel.get(this.FirstListBox.getSelectedIndex());
        for (int i = 0; i < TSDomain.domain.world.rules.size(); i++) {
        	TSRule rule = TSDomain.domain.world.rules.get(i);
            if (rule.usesVariableFor(variable, this.organizeByField)) {
            	// Want to display: rule.variableForField(displayFieldType).phrase
            	// PDF bad kludge to get to display
            	rule.listString = rule.variableForField(displayFieldType).phrase;
                this.SecondListBoxModel.addElement(rule);
            }
        }
    }
    
    public void FirstListBoxDrawItem(JList Control, int index, Rectangle Rect, Object State) {
        int i = 0;
        boolean focused = false;
        TSRule rule = new TSRule();
        TSVariable variable = new TSVariable();
        
        if ((index < 0) || (index > this.FirstListBoxModel.size() - 1)) {
            return;
        }
        focused = false;
        boolean selected = false;
        // PDF PORT: selected = delphi_compatability.TOwnerDrawStateType.odSelected == State
        if (selected) {
            for (i = 0; i < this.SecondListBoxModel.size(); i++) {
                rule = (TSRule)this.SecondListBoxModel.get(i);
                focused = (rule == this.rule) && rule.selected;
                if (focused) {
                    break;
                }
            }
        }
        variable = (TSVariable)this.FirstListBoxModel.get(index);
        if (variable == null) {
            return;
        }
        this.drawBrowserListBoxItem(this.FirstListBox, variable.phrase, index, Rect, selected, focused);
    }
    
    public void SecondListBoxDrawItem(JList Control, int index, Rectangle Rect, Object State) {
        boolean selected = false;
        boolean focused = false;
        TSRule rule = new TSRule();
        int displayFieldType = 0;
        String displayString = "";
        
        if ((index < 0) || (index > this.SecondListBoxModel.size() - 1)) {
            return;
        }
        rule = (TSRule)this.SecondListBoxModel.get(index);
        selected = rule.selected;
        focused = rule == this.rule;
        if (this.organizeByField == TSVariableDisplayOptions.kRuleCommand) {
            displayFieldType = TSVariableDisplayOptions.kRuleContext;
        } else {
            displayFieldType = TSVariableDisplayOptions.kRuleCommand;
        }
        displayString = rule.variableForField(displayFieldType).phrase;
        this.drawBrowserListBoxItem(this.SecondListBox, displayString, index, Rect, selected, focused);
    }
    
    public void drawBrowserListBoxItem(JList Control, String displayString, int index, Rectangle Rect, boolean selected, boolean focused) {
//        if (Application.terminated) {
//            //cText: array[0..255] of Char;
//            return;
//        }
//        JList listBox = Control;
//        DefaultListModel listBoxModel = (DefaultListModel) listBox.getModel();
//        if (listBox == null) {
//            return;
//        }
//        if ((index < 0) || (index > listBoxModel.size() - 1)) {
//            return;
//        }
//        setCanvasLineColorForSelection(listBox.Canvas, selected, focused, false);
//        listBox.Canvas.FillRect(Rect);
//        //strPCopy(cText, displayString);
//        // margin for text 
//        Rect.x = Rect.x + 2;
//        setCanvasFontColorAndStyleForSelection(this.RuleGrid.Canvas, selected, focused, false);
//        UNRESOLVED.DrawText(listBox.Canvas.Handle, displayString, displayString.length(), Rect, delphi_compatability.DT_LEFT);
    }

    public void setOrganizeByField(int newValue) {
        TSVariable variable = new TSVariable();
        
        if ((newValue < 0) || (newValue > TSVariableDisplayOptions.kLastRuleField)) {
            return;
        }
        TSDomain.domain.options.browseBy = newValue;
        this.MenuBrowseByContext.setSelected(newValue == TSVariableDisplayOptions.kRuleContext);
        this.MenuBrowseByCommand.setSelected(newValue == TSVariableDisplayOptions.kRuleCommand);
        this.MenuBrowseByMove.setSelected(newValue == TSVariableDisplayOptions.kRuleMove);
        this.MenuBrowseByRequirements.setSelected(newValue == TSVariableDisplayOptions.kRuleRequirements);
        this.MenuBrowseByChanges.setSelected(newValue == TSVariableDisplayOptions.kRuleChanges);
        if (newValue == TSVariableDisplayOptions.kRuleContext) {
            this.firstListBoxImage.imageIcon = (ImageIcon) Application.ConsoleForm.ContextButton.getIcon();
        }
        if (newValue == TSVariableDisplayOptions.kRuleCommand) {
            this.firstListBoxImage.imageIcon = Application.ConsoleForm.CommandButton.getIcon();
        }
        if (newValue == TSVariableDisplayOptions.kRuleMove) {
            this.firstListBoxImage.imageIcon = Application.ConsoleForm.MoveButton.getIcon();
        }
        if (newValue == TSVariableDisplayOptions.kRuleRequirements) {
            this.firstListBoxImage.imageIcon = Application.ConsoleForm.RequirementsButton.getIcon();
        }
        if (newValue == TSVariableDisplayOptions.kRuleChanges) {
            this.firstListBoxImage.imageIcon = Application.ConsoleForm.ChangesButton.getIcon();
        }
        // if organizeByField <> newValue then
        this.organizeByField = newValue;
        this.loadFirstListBox();
        if (this.rule != null) {
            variable = this.rule.variableForFieldWithSelections(this.organizeByField, this.RequirementsListBox.getSelectedIndex(), this.ChangesListBox.getSelectedIndex());
            this.FirstListBox.setSelectedIndex(this.FirstListBoxModel.indexOf(variable));
        }
        this.loadSecondListBox();
        this.SecondListBox.setSelectedIndex(this.SecondListBoxModel.indexOf(this.rule));
    }
    
//    public void WMGetMinMaxInfo(TMessage MSG) {
//        super.WMGetMinMaxInfo();
//        //FIX unresolved WITH expression: UNRESOLVED.PMinMaxInfo(MSG.lparam).PDF_FIX_POINTER_ACCESS
//        UNRESOLVED.ptMinTrackSize.x = 300;
//        UNRESOLVED.ptMinTrackSize.y = 100 + kMinListHeight + kMinRestHeight + kMinRequirementsChangesHeight + kSplitterHeight + kSplitterHeight;
//    }
//    
//    public void FormShow(Object Sender) {
//        UNRESOLVED.DragAcceptFiles(this.Handle, true);
//    }

    public void MapImageMouseDown(MouseEvent event) {
        boolean multipleSelect = false;
        String showString = "";
        Point textSize = new Point();
        Point centerPosition = new Point();
        
        if (Application.terminated) {
            return;
        } 
        this.commitChangesToRule();
        // in Java in a scroller, the point is automatically adjusted
        this.lastMapMouseDownPosition = new Point(event.getX(), event.getY());
        TSMapView mapView = this.currentGraphView();
        if (mapView == null) {
            return;
        }
        this.PanelMap.requestFocusInWindow();
        
        TSVariableDisplayOptions displayOptions = new TSVariableDisplayOptions();
        for (int i = 0; i <= 5; i++) {
            displayOptions.displayOptions[i] = false;
        }
        displayOptions.displayOptions[TSVariableDisplayOptions.kRuleContext] = true;
        displayOptions.displayOptions[TSVariableDisplayOptions.kRuleMove] = true;
        displayOptions.displayOptions[TSVariableDisplayOptions.kRuleCommand] = this.MenuMapsShowCommands.isSelected();
        TSDraggableObject draggedNode = mapView.nearestNode(event.getPoint(), displayOptions);
        if ((event.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
            if (draggedNode != null) {
                showString = draggedNode.displayName();
                centerPosition = new Point(draggedNode.center().x, draggedNode.center().y);
            } else {
                showString = "new item";
                centerPosition = new Point(event.getX(), event.getY());
            }
            // PDF PORT
//            this.MapImage.Canvas.Brush.Color = Color.cyan;
//            this.MapImage.Canvas.Pen.Style = delphi_compatability.TFPPenStyle.psSolid;
//            this.MapImage.Canvas.Font.Style = UNRESOLVED.fsBold;
//            textSize = new Point(this.MapImage.Canvas.TextWidth(showString), this.MapImage.Canvas.TextHeight("W"));
//            this.MapImage.Canvas.Rectangle(centerPosition.x - textSize.x / 2 - 2, centerPosition.y - textSize.y / 2 - 2, centerPosition.x + textSize.x / 2 + 2, centerPosition.y + textSize.y / 2 + 2);
//            this.MapImage.Canvas.TextOut(centerPosition.x - textSize.x / 2, centerPosition.y - textSize.y / 2, showString);
//            this.MapImage.Canvas.Font.Style = None;
        } 
        if ((event.getModifiers() & InputEvent.BUTTON1_MASK) != InputEvent.BUTTON1_MASK) {
            return;
        }
        multipleSelect = event.isShiftDown();
        this.MapImage.selectionRectangle = null;
        if (draggedNode == null) {
            this.makeChoice(null, multipleSelect);
            this.MapImage.selectionRectangle = new Rectangle(event.getX(), event.getY(), 1, 1);
            this.MapPaintBoxChanged();
            return;
        }
        //MapPaintBoxChanged;
        this.makeChoice(draggedNode, multipleSelect);
        if (event.isControlDown()) {
            this.MapPaintBoxChanged();
            // PDF PORT: this.MapImage.BeginDrag(true);
            return;
        }
        if (!multipleSelect) {
            this.MapImageDblClick(event);
        }
        this.MapPaintBoxChanged();
        // finds selected nodes in domain
        TSMapDragCommand newCommand = new TSMapDragCommand();
        newCommand.commandChangeType = "mapChanged";
        this.actionInProgress = TSDomain.domain.worldCommandList.mouseDown(newCommand, new Point(event.getPoint()));

    }

    public void MapImageMouseMove(MouseEvent event) {
        if (this.actionInProgress) {
            TSDomain.domain.worldCommandList.mouseMove(new Point(event.getX(), event.getY()));
        } else if (this.MapImage.isSelectionInProgress()) {
        	this.MapImage.updateSelectionCorner(event.getPoint());
        	this.MapPaintBoxChanged();
        }
    }

    public void MapImageMouseUp(MouseEvent event) {
        if (this.actionInProgress) {
            TSDomain.domain.worldCommandList.mouseUp(new Point(event.getX(), event.getY()));
            this.actionInProgress = false;
            this.adjustScrollBars();
        } else if (this.MapImage.isSelectionInProgress()) {
        	this.MapImage.updateSelectionCorner(event.getPoint());
            if (!event.isControlDown()) {
                TSDomain.domain.world.deselectAllExcept(null);
            }
            TSDomain.domain.world.selectInRectangle(this.MapImage.selectionRectangle);
            this.MapImage.selectionRectangle = null;
            this.MapPaintBoxChanged();
        }
    }

    // Accommodates growth
    // figure out the bounds of self.MapImage
    public void adjustScrollBars() {
    	//System.out.println("adjustScrollBars");
        int top = 0;
        int height = 0;
        int left = 0;
        int width = 0;
        
        TSMapView graphView = this.currentGraphView();
        if (graphView != null) {
        	Rectangle mapBoundsRect = TSDomain.domain.world.boundsRect();
            //System.out.println("mapboundsrect: " + mapBoundsRect);
            left = mapBoundsRect.x;
            top = mapBoundsRect.y;
            width = mapBoundsRect.width;
            height = mapBoundsRect.height;
        } else {
            left = 0;
            top = 0;
            width = this.MapImage.getWidth();
            height = this.MapImage.getHeight();
        }
        left = left - kLeftRightBorderSize;
        top = top - kTopBottomBorderSize;
        width = width + kLeftRightBorderSize;
        height = height + kTopBottomBorderSize;
        
        if (height < this.MapScroller.getHeight()) {
            height = this.MapScroller.getHeight();
        }
        if (width < this.MapScroller.getWidth()) {
            width = this.MapScroller.getWidth();
        }
        
        //if (left < 0) width -= left;
        //if (top < 0) height -= top;
        
        //int xPosition = this.MapScroller.getHorizontalScrollBar().getValue();
        //int yPosition = this.MapScroller.getVerticalScrollBar().getValue();
        //System.out.println("scroller x: " + xPosition);
        this.MapImage.setSize(width, height);
        this.MapImage.setPreferredSize(new Dimension(width, height));
        // SEEMS TO WORK OK NOW __ CAN PROBABLY REMOVE THE REST, except for:
        // PDF PORT: COULD HANDLE CREATING SPACE AT THE TOP AND THE LEFT SIDES
        //this.MapImage.invalidate();
//        this.MapScroller.getHorizontalScrollBar().setValue(xPosition);
//        this.MapScroller.getVerticalScrollBar().setValue(yPosition);
//        
//        xPosition = this.MapScroller.getHorizontalScrollBar().getValue();
//        yPosition = this.MapScroller.getVerticalScrollBar().getValue();
//        System.out.println("scroller x: " + xPosition);
//        
//        System.out.println("scroller x maximum: " + this.MapScroller.getHorizontalScrollBar().getMaximum());
//        
//        this.MapScroller.getHorizontalScrollBar().setMaximum(width);
//        this.MapScroller.getVerticalScrollBar().setMaximum(height);
//        
//        this.MapScroller.getHorizontalScrollBar().setValues(xPosition, 0, 100, width);
        
//        width = width - this.MapImage.getWidth();
//        height = height - this.MapImage.getHeight();

//        if (xPosition < left) {
//            //if xPosition < left then xPosition := left;
//            //  if xPosition > right then xPosition := right;
//            //  if yPosition < top then yPosition := top;
//            //  if yPosition > bottom then yPosition := bottom;
//            left = xPosition;
//        }
//        if (xPosition > width) {
//            width = xPosition;
//        }
//        if (yPosition < top) {
//            top = yPosition;
//        }
//        if (yPosition > height) {
//            height = yPosition;
//        }
//        this.MapScrollBarHorizontal.SetParams(xPosition, left, width);
//        this.MapScrollBarHorizontal.LargeChange = this.MapImage.Width;
//        //LocalIntMax(1, LocalIntMin((right - left) div 10, MapPaintBox.width));
//        this.MapScrollBarVertical.SetParams(yPosition, top, height);
//        this.MapScrollBarVertical.LargeChange = this.MapImage.Height;
//        //LocalIntMax(1, LocalIntMin((bottom - top) div 10, MapPaintBox.height));
    }
    
    public void MapScrollBarVerticalChange(Object Sender) {
        this.MapPaintBoxChanged();
    }
    
    public void MapScrollBarHorizontalChange(Object Sender) {
        this.MapPaintBoxChanged();
    }
    
    public Point goodPosition() {
        Point result = null;
        if (this.lastChoice != null) {
            if (this.previousChoice != null) {
                //var
                //  	mapBoundsRect: Rectangle;
                //    selection: TSDraggableObject; 
                result = new Point((this.previousChoice.position.x + this.lastChoice.position.x) / 2, (this.previousChoice.position.y + this.lastChoice.position.y) / 2 + 30);
            } else {
                result = new Point(this.lastChoice.position.x, this.lastChoice.position.y + 30);
            }
        } else {
            // mapBoundsRect := domain.world.boundsRect;
            //    result.x := (mapBoundsRect.x - mapBoundsRect.right) div 2;
            //    result.y := mapBoundsRect.bottom + 30;  
            result = new Point(this.MapScroller.getHorizontalScrollBar().getValue() + this.MapImage.getWidth() / 2, this.MapScroller.getVerticalScrollBar().getValue() + this.MapImage.getHeight() / 2);
        }
        result.x = result.x + new Random().nextInt(200) - 100;
        result.y = result.y + new Random().nextInt(200) - 100;
        //if (domain <> nil) and (domain.world <> nil) then
        //    begin
        //    selection := domain.world.firstSelectedObject;
        //    if selection <> nil then
        //      begin
        //      result.x := selection.position.x;
        //      result.y := selection.position.y + 30;
        //      end;
        //    end;
        //  result := new Point(MapScrollBarHorizontal.position + MapImage.width div 2, MapScrollBarVertical.position +  MapImage.height div 2);
        //  //result.x := result.x + random(200) - 100;
        //  //result.y := result.y + random(200) - 100;
        //result := new Point(MapScrollBarHorizontal.position + MapImage.width div 2, MapScrollBarVertical.position +  MapImage.height div 2);
        //  if (domain <> nil) and (domain.world <> nil) then
        //    begin
        //    selection := domain.world.firstSelectedObject;
        //    if selection <> nil then
        //      begin
        //      result.x := selection.position.x;
        //      result.y := selection.position.y;
        //      end;
        //    end;
        //  result.x := result.x + random(200) - 100;
        //  result.y := result.y + random(200) - 100;   
        return result;
    }
            
    public void scrollMapSelectionIntoView() {
        TSDraggableObject upperLeftObject = null;
        TSVariable firstContextVariable = null;
        
        upperLeftObject = null;
        Rectangle visibleRect = this.MapScroller.getViewport().getViewRect();
        for (int i = 0; i < TSDomain.domain.world.rules.size(); i++) {
            TSRule rule = TSDomain.domain.world.rules.get(i);
            if (rule.selected) {
            	if (visibleRect.contains(rule.bounds())) {
            		return;
            	}
                if (upperLeftObject == null) {
                    upperLeftObject = rule;
                } else if (upperLeftObject.bounds().y > rule.bounds().y) {
                    upperLeftObject = rule;
                } else if (upperLeftObject.bounds().x > rule.bounds().x) {
                    upperLeftObject = rule;
                }
            }
        }
        firstContextVariable = null;
        for (int i = 0; i < TSDomain.domain.world.variables.size(); i++) {
        	TSVariable variable = TSDomain.domain.world.variables.get(i);
            if ((firstContextVariable == null) && (variable.hasUseagesForField(TSVariableDisplayOptions.kRuleContext))) {
                firstContextVariable = variable;
            }
            if (variable.selected) {
            	if (visibleRect.contains(variable.bounds())) {
            		return;
            	}
                if (upperLeftObject == null) {
                    upperLeftObject = variable;
                } else if (upperLeftObject.bounds().y > variable.bounds().y) {
                    upperLeftObject = variable;
                } else if (upperLeftObject.bounds().x > variable.bounds().x) {
                    upperLeftObject = variable;
                }
            }
        }
        if (upperLeftObject == null) {
            upperLeftObject = firstContextVariable;
        }
        if (upperLeftObject == null) {
            return;
        }
        this.MapImage.scrollRectToVisible(upperLeftObject.bounds());
    }

}  //  @jve:decl-index=0:visual-constraint="6,3"
