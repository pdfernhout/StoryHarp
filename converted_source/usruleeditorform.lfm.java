
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// import common.*;

public class RuleEditorWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    JSplitPane SplitterEdit;
    JPanel PanelEditor;
    JSplitPane SplitterRequirementsChanges;
    JPanel PanelRequirementsChanges;
    SpeedButton RequirementsSpeedButton;
    SpeedButton ChangesSpeedButton;
    JList RequirementsListBox;
    JList ChangesListBox;
    JTextField RequirementsEdit;
    JTextField ChangesEdit;
    JPanel PanelRest;
    JLabel Label5;
    SpeedButton CommandSpeedButton;
    SpeedButton MoveSpeedButton;
    SpeedButton ContextSpeedButton;
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
    ImagePanel MapImage;
    // UNHANDLED_TYPE MapScrollBarHorizontal;
    // UNHANDLED_TYPE MapScrollBarVertical;
    JPanel TabSheetBrowse;
    JPanel PanelLists;
    JSplitPane SplitterLists;
    JPanel PanelFirstList;
    ImagePanel firstListBoxImage;
    JLabel firstListBoxLabel;
    JList FirstListBox;
    JPanel PanelSecondList;
    ImagePanel SecondListBoxImage;
    JLabel SecondListBoxLabel;
    JList SecondListBox;
    JPanel PanelButtonBar;
    SpeedButton NewRuleButton;
    SpeedButton DuplicateRuleButton;
    SpeedButton DeleteRuleButton;
    SpeedButton MoveUpButton;
    SpeedButton MoveDownButton;
    SpeedButton insertSound;
    SpeedButton InsertMusicButton;
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
    // UNHANDLED_TYPE FontDialog;
    JPopupMenu EditPopupMenu;
    JMenuItem PopupCut;
    JMenuItem PopupCopy;
    JMenuItem PopupPaste;
    JPopupMenu MapPopupMenu;
    JMenuItem PopupNewContext;
    JMenuItem PopupNewCommand;
    JMenuItem PopupNewLink;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
    
    public RuleEditorWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    public JMenuBar getMainMenu1() {
        if (MainMenu1 != null) return MainMenu1;
        this.MainMenu1 = new JMenuBar();
        this.MenuFile = new JMenu("World");
        this.MenuFile.setMnemonic('W');
        this.MainMenu1.add(MenuFile);
        this.MenuFileNewWorld = new JMenuItem("New");
        this.MenuFileNewWorld.setMnemonic(KeyEvent.VK_N);
        this.MenuFileNewWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileNewWorldClick(event);
            }
        });
        this.MenuFile.add(MenuFileNewWorld);
        
        this.MenuFileOpenWorld = new JMenuItem("Open...");
        this.MenuFileOpenWorld.setMnemonic(KeyEvent.VK_O);
        this.MenuFileOpenWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileOpenWorldClick(event);
            }
        });
        this.MenuFile.add(MenuFileOpenWorld);
        
        this.MenuFile.addSeparator();
        
        this.MenuFileSaveWorld = new JMenuItem("Save");
        this.MenuFileSaveWorld.setMnemonic(KeyEvent.VK_S);
        this.MenuFileSaveWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileSaveWorldClick(event);
            }
        });
        this.MenuFile.add(MenuFileSaveWorld);
        
        this.MenuFileSaveWorldAs = new JMenuItem("Save As...");
        this.MenuFileSaveWorldAs.setMnemonic(KeyEvent.VK_A);
        this.MenuFileSaveWorldAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileSaveWorldAsClick(event);
            }
        });
        this.MenuFile.add(MenuFileSaveWorldAs);
        
        this.MenuFile.addSeparator();
        
        this.MenuWorldSwitchToPlayer = new JMenuItem("Switch to Player...");
        this.MenuWorldSwitchToPlayer.setMnemonic(KeyEvent.VK_P);
        this.MenuWorldSwitchToPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuWorldSwitchToPlayerClick(event);
            }
        });
        this.MenuFile.add(MenuWorldSwitchToPlayer);
        
        this.MenuFile.addSeparator();
        
        this.MenuFileExit = new JMenuItem("Exit");
        this.MenuFileExit.setMnemonic(KeyEvent.VK_X);
        this.MenuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileExitClick(event);
            }
        });
        this.MenuFile.add(MenuFileExit);
        
        
        this.MenuFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                MenuEditClick(event);
            }
        });
        
        this.MenuEdit = new JMenu("Edit");
        this.MenuEdit.setMnemonic('E');
        this.MainMenu1.add(MenuEdit);
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
        
        this.MenuEditCut = new JMenuItem("Cut                 Ctrl+X");
        this.MenuEditCut.setMnemonic(KeyEvent.VK_T);
        this.MenuEditCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditCutClick(event);
            }
        });
        this.MenuEdit.add(MenuEditCut);
        
        this.MenuEditCopy = new JMenuItem("Copy              Ctrl+C");
        this.MenuEditCopy.setMnemonic(KeyEvent.VK_C);
        this.MenuEditCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditCopyClick(event);
            }
        });
        this.MenuEdit.add(MenuEditCopy);
        
        this.MenuEditPaste = new JMenuItem("Paste             Ctrl+V");
        this.MenuEditPaste.setMnemonic(KeyEvent.VK_P);
        this.MenuEditPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditPasteClick(event);
            }
        });
        this.MenuEdit.add(MenuEditPaste);
        
        this.MenuEdit.addSeparator();
        
        this.MenuEditInsertSound = new JMenuItem("Insert Sound...");
        this.MenuEditInsertSound.setMnemonic(KeyEvent.VK_S);
        this.MenuEditInsertSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditInsertSoundClick(event);
            }
        });
        this.MenuEdit.add(MenuEditInsertSound);
        
        this.MenuEditInsertMusic = new JMenuItem("Insert Music...");
        this.MenuEditInsertMusic.setMnemonic(KeyEvent.VK_M);
        this.MenuEditInsertMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditInsertMusicClick(event);
            }
        });
        this.MenuEdit.add(MenuEditInsertMusic);
        
        this.MenuEditInsertPicture = new JMenuItem("Insert Picture...");
        this.MenuEditInsertPicture.setMnemonic(KeyEvent.VK_I);
        this.MenuEditInsertPicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditInsertPictureClick(event);
            }
        });
        this.MenuEdit.add(MenuEditInsertPicture);
        
        this.MenuEdit.addSeparator();
        
        this.MenuEditPreferences = new JMenuItem("Editor Preferences...");
        this.MenuEditPreferences.setMnemonic(KeyEvent.VK_E);
        this.MenuEditPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditPreferencesClick(event);
            }
        });
        this.MenuEdit.add(MenuEditPreferences);
        
        
        this.MenuEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                MenuEditClick(event);
            }
        });
        
        this.MenuRule = new JMenu("Rule");
        this.MenuRule.setMnemonic('R');
        this.MainMenu1.add(MenuRule);
        this.MenuRuleNew = new JMenuItem("New Rule");
        this.MenuRuleNew.setMnemonic(KeyEvent.VK_N);
        this.MenuRuleNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuRuleNewClick(event);
            }
        });
        this.MenuRule.add(MenuRuleNew);
        
        this.MenuRuleDuplicate = new JMenuItem("Duplicate");
        this.MenuRuleDuplicate.setMnemonic(KeyEvent.VK_D);
        this.MenuRuleDuplicate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuRuleDuplicateClick(event);
            }
        });
        this.MenuRule.add(MenuRuleDuplicate);
        
        this.MenuRuleDelete = new JMenuItem("Delete");
        this.MenuRuleDelete.setMnemonic(KeyEvent.VK_E);
        this.MenuRuleDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuRuleDeleteClick(event);
            }
        });
        this.MenuRule.add(MenuRuleDelete);
        
        this.MenuRule.addSeparator();
        
        this.MenuRuleRaise = new JMenuItem("Raise");
        this.MenuRuleRaise.setMnemonic(KeyEvent.VK_R);
        this.MenuRuleRaise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuRuleRaiseClick(event);
            }
        });
        this.MenuRule.add(MenuRuleRaise);
        
        this.MenuRuleLower = new JMenuItem("Lower");
        this.MenuRuleLower.setMnemonic(KeyEvent.VK_L);
        this.MenuRuleLower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuRuleLowerClick(event);
            }
        });
        this.MenuRule.add(MenuRuleLower);
        
        this.MenuRule.addSeparator();
        
        this.MenuFileMergeWithWorld = new JMenuItem("Import From...");
        this.MenuFileMergeWithWorld.setMnemonic(KeyEvent.VK_I);
        this.MenuFileMergeWithWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileMergeWithWorldClick(event);
            }
        });
        this.MenuRule.add(MenuFileMergeWithWorld);
        
        this.MenuFileExport = new JMenuItem("Export Selected To...");
        this.MenuFileExport.setMnemonic(KeyEvent.VK_X);
        this.MenuFileExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuFileExportClick(event);
            }
        });
        this.MenuRule.add(MenuFileExport);
        
        this.MenuRule.addSeparator();
        
        this.MenuRuleTestReply = new JMenuItem("Test Reply");
        this.MenuRuleTestReply.setMnemonic(KeyEvent.VK_T);
        this.MenuRuleTestReply.addActionListener(new java.awt.event.ActionListener() {
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
        this.MenuDisplayShowButtonBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuDisplayShowButtonBarClick(event);
            }
        });
        this.MenuMaps.add(MenuDisplayShowButtonBar);
        this.MenuDisplayShowButtonBar.setSelected(true);
        
        this.MenuOptionsShowRuleEditor = new JCheckBoxMenuItem("Show Rule Editor");
        this.MenuOptionsShowRuleEditor.setMnemonic(KeyEvent.VK_E);
        this.MenuOptionsShowRuleEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuOptionsShowRuleEditorClick(event);
            }
        });
        this.MenuMaps.add(MenuOptionsShowRuleEditor);
        this.MenuOptionsShowRuleEditor.setSelected(true);
        
        this.MenuMapsShowCommands = new JCheckBoxMenuItem("Show Commands in Map");
        this.MenuMapsShowCommands.setMnemonic(KeyEvent.VK_S);
        this.MenuMapsShowCommands.addActionListener(new java.awt.event.ActionListener() {
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
        this.MenuBrowseByContext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.MenuMaps.add(MenuBrowseByContext);
        this.MenuBrowseByContext.setSelected(false);
        
        this.MenuBrowseByCommand = new JRadioButtonMenuItem("Browse by Command");
        group.add(this.MenuBrowseByCommand);
        this.MenuBrowseByCommand.setMnemonic(KeyEvent.VK_O);
        this.MenuBrowseByCommand.addActionListener(new java.awt.event.ActionListener() {
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
        this.MenuBrowseByMove.addActionListener(new java.awt.event.ActionListener() {
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
        this.MenuBrowseByRequirements.addActionListener(new java.awt.event.ActionListener() {
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
        this.MenuBrowseByChanges.addActionListener(new java.awt.event.ActionListener() {
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
        this.MenuToolsSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuToolsSearchClick(event);
            }
        });
        this.Wizards1.add(MenuToolsSearch);
        
        this.Wizards1.addSeparator();
        
        this.MenuMapsQuickContexts = new JMenuItem("New Contexts Wizard...");
        this.MenuMapsQuickContexts.setMnemonic(KeyEvent.VK_C);
        this.MenuMapsQuickContexts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuMapsQuickContextsClick(event);
            }
        });
        this.Wizards1.add(MenuMapsQuickContexts);
        
        this.MenuMapLinkWizard = new JMenuItem("New Moves Wizard...");
        this.MenuMapLinkWizard.setMnemonic(KeyEvent.VK_M);
        this.MenuMapLinkWizard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuMapLinkWizardClick(event);
            }
        });
        this.Wizards1.add(MenuMapLinkWizard);
        
        this.MenuMapQuickCommands = new JMenuItem("New Commands Wizard...");
        this.MenuMapQuickCommands.setMnemonic(KeyEvent.VK_O);
        this.MenuMapQuickCommands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuMapQuickCommandsClick(event);
            }
        });
        this.Wizards1.add(MenuMapQuickCommands);
        
        this.Wizards1.addSeparator();
        
        this.MenuToolsGenerateJava = new JMenuItem("Generate Java...");
        this.MenuToolsGenerateJava.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuToolsGenerateJavaClick(event);
            }
        });
        this.Wizards1.add(MenuToolsGenerateJava);
        
        this.Wizards1.addSeparator();
        
        this.MenuEditLogFile = new JMenuItem("Log File...");
        this.MenuEditLogFile.setMnemonic(KeyEvent.VK_L);
        this.MenuEditLogFile.addActionListener(new java.awt.event.ActionListener() {
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
        this.MenuHelpContents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpContentsClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpContents);
        
        this.MenuHelpBasicConcepts = new JMenuItem("Basic Concepts");
        this.MenuHelpBasicConcepts.setMnemonic(KeyEvent.VK_C);
        this.MenuHelpBasicConcepts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpBasicConceptsClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpBasicConcepts);
        
        this.MenuHelpTutorial = new JMenuItem("Authoring Tutorial");
        this.MenuHelpTutorial.setMnemonic(KeyEvent.VK_T);
        this.MenuHelpTutorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpTutorialClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpTutorial);
        
        this.MenuHelpEditingWorlds = new JMenuItem("Editing Worlds");
        this.MenuHelpEditingWorlds.setMnemonic(KeyEvent.VK_W);
        this.MenuHelpEditingWorlds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuHelpEditingWorldsClick(event);
            }
        });
        this.MenuHelp.add(MenuHelpEditingWorlds);
        
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
        
        
        
        return MainMenu1;
    }
        //  --------------- UNHANDLED ATTRIBUTE: this.MainMenu1.top = 222;
        //  --------------- UNHANDLED ATTRIBUTE: this.MainMenu1.left = 472;
        
    public JPopupMenu getEditPopupMenu() {
        if (EditPopupMenu != null) return EditPopupMenu;
        this.EditPopupMenu = new JPopupMenu();
        this.PopupCut = new JMenuItem("Cut");
        this.PopupCut.setMnemonic(KeyEvent.VK_T);
        this.PopupCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditCutClick(event);
            }
        });
        this.EditPopupMenu.add(PopupCut);
        
        this.PopupCopy = new JMenuItem("Copy");
        this.PopupCopy.setMnemonic(KeyEvent.VK_C);
        this.PopupCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditCopyClick(event);
            }
        });
        this.EditPopupMenu.add(PopupCopy);
        
        this.PopupPaste = new JMenuItem("Paste");
        this.PopupPaste.setMnemonic(KeyEvent.VK_P);
        this.PopupPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MenuEditPasteClick(event);
            }
        });
        this.EditPopupMenu.add(PopupPaste);
        
        return EditPopupMenu;
    }
        //  --------------- UNHANDLED ATTRIBUTE: this.EditPopupMenu.top = 307;
        //  --------------- UNHANDLED ATTRIBUTE: this.EditPopupMenu.left = 516;
        
    public JPopupMenu getMapPopupMenu() {
        if (MapPopupMenu != null) return MapPopupMenu;
        this.MapPopupMenu = new JPopupMenu();
        this.PopupNewContext = new JMenuItem("New Context");
        this.PopupNewContext.setMnemonic(KeyEvent.VK_C);
        this.PopupNewContext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                PopupNewContextClick(event);
            }
        });
        this.MapPopupMenu.add(PopupNewContext);
        
        this.PopupNewCommand = new JMenuItem("New Command");
        this.PopupNewCommand.setMnemonic(KeyEvent.VK_O);
        this.PopupNewCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                PopupNewCommandClick(event);
            }
        });
        this.MapPopupMenu.add(PopupNewCommand);
        
        this.PopupNewLink = new JMenuItem("New Move");
        this.PopupNewLink.setMnemonic(KeyEvent.VK_M);
        this.PopupNewLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                PopupNewLinkClick(event);
            }
        });
        this.MapPopupMenu.add(PopupNewLink);
        
        return MapPopupMenu;
    }
        //  --------------- UNHANDLED ATTRIBUTE: this.MapPopupMenu.top = 167;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapPopupMenu.left = 532;
        
    private void initialize() {
        this.setSize(new Dimension(705, 325));
        this.setJMenuBar(getMainMenu1());
        this.setContentPane(getMainContentPane());
        this.setTitle("StoryHarp World Editor");
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
        this.setBounds(677, 196, 528, 601  + 80); // extra for title bar and menu
        //  --------------- UNHANDLED ATTRIBUTE: this.Menu = MainMenu1;
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
        this.SplitterEdit.setBounds(0, -21, 528, 592);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterEdit.MinSize = 1;
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterEdit.OnMoved = SplitterEditMoved;
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterEdit.ResizeAnchor = akBottom;
        
        this.SplitterRequirementsChanges = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.SplitterRequirementsChanges.setBounds(0, -369, 528, 592);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterRequirementsChanges.MinSize = 60;
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterRequirementsChanges.OnMoved = SplitterRequirementsChangesMoved;
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterRequirementsChanges.ResizeAnchor = akBottom;
        
        Image RequirementsSpeedButtonImage = toolkit.createImage("../resources/RuleEditorForm_RequirementsSpeedButton.png");
        this.RequirementsSpeedButton = new SpeedButton("Requirements", new ImageIcon(RequirementsSpeedButtonImage));
        this.RequirementsSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.RequirementsSpeedButton.setBounds(13, 8, 100, 24);
        this.RequirementsSpeedButton.setToolTipText("Browse all rules with the selected requirement");
        this.RequirementsSpeedButton.putClientProperty("tag", 4);
        
        Image ChangesSpeedButtonImage = toolkit.createImage("../resources/RuleEditorForm_ChangesSpeedButton.png");
        this.ChangesSpeedButton = new SpeedButton("Changes", new ImageIcon(ChangesSpeedButtonImage));
        this.ChangesSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.ChangesSpeedButton.setBounds(36, 49, 77, 24);
        this.ChangesSpeedButton.setToolTipText("Browse all rules with the selected change");
        this.ChangesSpeedButton.putClientProperty("tag", 5);
        
        this.RequirementsListBox = new JList(new DefaultListModel());
        this.RequirementsListBox.setFixedCellHeight(14);
        this.RequirementsListBox.setBounds(117, 4, 329, 33);
        this.RequirementsListBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                EditEnterCommit(event);
            }
        });
        this.RequirementsListBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent event) {
                ListBoxExit(event);
            }
        });
        this.RequirementsListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                ListBoxMouseUp(event);
            }
        });
        this.RequirementsListBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent event) {
                ListBoxKeyUp(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.RequirementsListBox.OnDblClick = ListBoxDblClick;
        //  --------------- UNHANDLED ATTRIBUTE: this.RequirementsListBox.ExtendedSelect = False;
        
        this.ChangesListBox = new JList(new DefaultListModel());
        this.ChangesListBox.setFixedCellHeight(14);
        this.ChangesListBox.setBounds(117, 44, 329, 36);
        this.ChangesListBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                EditEnterCommit(event);
            }
        });
        this.ChangesListBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent event) {
                ListBoxExit(event);
            }
        });
        this.ChangesListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                ListBoxMouseUp(event);
            }
        });
        this.ChangesListBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent event) {
                ListBoxKeyUp(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.ChangesListBox.OnDblClick = ListBoxDblClick;
        //  --------------- UNHANDLED ATTRIBUTE: this.ChangesListBox.ExtendedSelect = False;
        
        this.RequirementsEdit = new JTextField("");
        this.RequirementsEdit.setBounds(128, 157, 221, 20);
        this.RequirementsEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent event) {
                ListBoxEditExit(event);
            }
        });
        this.RequirementsEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent event) {
                ListBoxEditKeyPress(event);
            }
        });
        this.RequirementsEdit.setVisible(false);
        
        this.ChangesEdit = new JTextField("");
        this.ChangesEdit.setBounds(128, 93, 185, 20);
        this.ChangesEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent event) {
                ListBoxEditExit(event);
            }
        });
        this.ChangesEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent event) {
                ListBoxEditKeyPress(event);
            }
        });
        this.ChangesEdit.setVisible(false);
        
        this.PanelRequirementsChanges = new JPanel(null);
        // -- this.PanelRequirementsChanges.setLayout(new BoxLayout(this.PanelRequirementsChanges, BoxLayout.Y_AXIS));
        this.PanelRequirementsChanges.add(RequirementsSpeedButton);
        this.PanelRequirementsChanges.add(ChangesSpeedButton);
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setBounds(117, 4, 329, 33);;
        scroller1.setViewportView(RequirementsListBox);
        this.PanelRequirementsChanges.add(scroller1);
        JScrollPane scroller2 = new JScrollPane();
        scroller2.setBounds(117, 44, 329, 36);;
        scroller2.setViewportView(ChangesListBox);
        this.PanelRequirementsChanges.add(scroller2);
        this.PanelRequirementsChanges.add(RequirementsEdit);
        this.PanelRequirementsChanges.add(ChangesEdit);
        this.PanelRequirementsChanges.setBounds(0, -263, 528, 79);
        //  --------------- UNHANDLED ATTRIBUTE: this.PanelRequirementsChanges.OnResize = PanelRequirementsChangesResize;
        
        this.Label5 = new JLabel("Reply");
        this.Label5.setBounds(86, 64, 42, 17);
        
        Image CommandSpeedButtonImage = toolkit.createImage("../resources/RuleEditorForm_CommandSpeedButton.png");
        this.CommandSpeedButton = new SpeedButton("Command", new ImageIcon(CommandSpeedButtonImage));
        this.CommandSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.CommandSpeedButton.setBounds(28, 32, 85, 24);
        this.CommandSpeedButton.setToolTipText("Browse all rules with this command");
        this.CommandSpeedButton.putClientProperty("tag", 1);
        
        Image MoveSpeedButtonImage = toolkit.createImage("../resources/RuleEditorForm_MoveSpeedButton.png");
        this.MoveSpeedButton = new SpeedButton("Move", new ImageIcon(MoveSpeedButtonImage));
        this.MoveSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.MoveSpeedButton.setBounds(49, 104, 64, 24);
        this.MoveSpeedButton.setToolTipText("Browse all rules with this move");
        this.MoveSpeedButton.putClientProperty("tag", 3);
        
        Image ContextSpeedButtonImage = toolkit.createImage("../resources/RuleEditorForm_ContextSpeedButton.png");
        this.ContextSpeedButton = new SpeedButton("Context", new ImageIcon(ContextSpeedButtonImage));
        this.ContextSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                SpeedButtonClick(event);
            }
        });
        this.ContextSpeedButton.setBounds(40, 4, 73, 24);
        this.ContextSpeedButton.setToolTipText("Browse all rules with this context");
        
        this.RuleNumberLabel = new JLabel("#1234");
        this.RuleNumberLabel.setBounds(2, 5, 46, 17);
        this.RuleNumberLabel.setToolTipText("The index of the edited rule in the table");
        
        this.replyPicture = new ImagePanel(); // No image was set
        this.replyPicture.setBounds(67, 64, 16, 16);
        this.replyPicture.setToolTipText("Test saying the reply");
        this.replyPicture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                replyPictureMouseUp(event);
            }
        });
        
        this.ContextEdit = new JTextField("");
        this.ContextEdit.setBounds(117, 6, 329, 22);
        this.ContextEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                EditEnterCommit(event);
            }
        });
        
        this.CommandEdit = new JTextField("");
        this.CommandEdit.setBounds(117, 34, 329, 22);
        this.CommandEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                EditEnterCommit(event);
            }
        });
        
        this.ReplyMemo = new JTextArea(10, 10);
        this.ReplyMemo.setBounds(117, 60, 329, 41);
        this.ReplyMemo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                EditEnterCommit(event);
            }
        });
        this.ReplyMemo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                ReplyMemoMouseUp(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.ReplyMemo.ScrollBars = ssVertical;
        
        this.MoveEdit = new JTextField("");
        this.MoveEdit.setBounds(117, 106, 329, 22);
        this.MoveEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                EditEnterCommit(event);
            }
        });
        
        this.PanelRest = new JPanel(null);
        // -- this.PanelRest.setLayout(new BoxLayout(this.PanelRest, BoxLayout.Y_AXIS));
        this.PanelRest.add(Label5);
        this.PanelRest.add(CommandSpeedButton);
        this.PanelRest.add(MoveSpeedButton);
        this.PanelRest.add(ContextSpeedButton);
        this.PanelRest.add(RuleNumberLabel);
        this.PanelRest.add(replyPicture);
        this.PanelRest.add(ContextEdit);
        this.PanelRest.add(CommandEdit);
        JScrollPane scroller3 = new JScrollPane();
        scroller3.setBounds(117, 60, 329, 41);;
        scroller3.setViewportView(ReplyMemo);
        this.PanelRest.add(scroller3);
        this.PanelRest.add(MoveEdit);
        this.PanelRest.setBounds(0, -131, 528, 1);
        //  --------------- UNHANDLED ATTRIBUTE: this.PanelRest.OnResize = PanelRestResize;
        
        this.PanelEditor = new JPanel(null);
        // -- this.PanelEditor.setLayout(new BoxLayout(this.PanelEditor, BoxLayout.Y_AXIS));
        this.PanelEditor.add(SplitterRequirementsChanges);
        this.PanelEditor.add(PanelRequirementsChanges);
        this.PanelEditor.add(PanelRest);
        this.PanelEditor.setBounds(0, -233, 528, 223);
        
        this.ListPages = new JTabbedPane();
        this.TabSheetTable = new JPanel(null);
        this.RuleGrid = new JTable(new DefaultTableModel());
        this.RuleGrid.setBounds(0, 0, 522, 1);
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.OnDrawCell = RuleGridDrawCell;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.ColWidths = ['', '', '6', '0', '4', '6'];
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.GridLineWidth = 0;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.VisibleRowCount = 1;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.RowCount = 2;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.VisibleColCount = 4;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.ColCount = 6;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.DefaultRowHeight = 16;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.FixedColor = clBtnFace;
        this.RuleGrid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                RuleGridMouseUp(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.ScrollBars = ssAutoBoth;
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.FixedCols = 0;
        this.RuleGrid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent event) {
                RuleGridMouseDown(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.RuleGrid.Options = [goFixedVertLine, goFixedHorzLine, goVertLine, goHorzLine, goColSizing, goRowSelect];
        
        this.TabSheetTable.add(RuleGrid);
        this.ListPages.addTab(" Table", null, this.RuleGrid);
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetTable.Width = 522;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetTable.Top = 2;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetTable.Height = 1;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetTable.Left = 2;
        
        this.TabSheetMap = new JPanel(null);
        this.MapImage = new ImagePanel(); // No image was set
        this.MapImage.setBounds(0, 0, 549, 245);
        this.MapImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent event) {
                MapImageMouseMove(event);
            }
        });
        this.MapImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent event) {
                MapImageMouseMove(event);
            }
        });
        this.MapImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
                MapImageMouseUp(event);
            }
        });
        this.MapImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent event) {
                MapImageMouseDown(event);
            }
        });
        
        //  ------- UNHANDLED TYPE TScrollBar: MapScrollBarHorizontal 
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarHorizontal.SmallChange = 10;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarHorizontal.Height = 15;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarHorizontal.Width = 553;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarHorizontal.TabOrder = 0;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarHorizontal.OnChange = MapScrollBarHorizontalChange;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarHorizontal.Top = 249;
        
        //  ------- UNHANDLED TYPE TScrollBar: MapScrollBarVertical 
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarVertical.Kind = sbVertical;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarVertical.SmallChange = 10;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarVertical.Height = 16;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarVertical.Width = 15;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarVertical.TabOrder = 1;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarVertical.OnChange = MapScrollBarVerticalChange;
        //  --------------- UNHANDLED ATTRIBUTE: this.MapScrollBarVertical.Left = 557;
        
        this.PanelMap = new JPanel(null);
        // -- this.PanelMap.setLayout(new BoxLayout(this.PanelMap, BoxLayout.Y_AXIS));
        this.PanelMap.add(MapImage);
        // FIX UNHANDLED TYPE -- this.PanelMap.add(this.MapScrollBarHorizontal);
        // FIX UNHANDLED TYPE -- this.PanelMap.add(this.MapScrollBarVertical);
        this.PanelMap.setBounds(0, 0, 522, 1);
        //  --------------- UNHANDLED ATTRIBUTE: this.PanelMap.FullRepaint = False;
        //  --------------- UNHANDLED ATTRIBUTE: this.PanelMap.OnResize = PanelMapResize;
        
        this.TabSheetMap.add(PanelMap);
        this.ListPages.addTab("  Map", null, this.PanelMap);
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetMap.Width = 522;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetMap.Top = 2;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetMap.Height = 1;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetMap.Left = 2;
        
        this.TabSheetBrowse = new JPanel(null);
        this.SplitterLists = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.SplitterLists.setBounds(253, 0, 3, 1);
        //  --------------- UNHANDLED ATTRIBUTE: this.SplitterLists.MinSize = 80;
        
        this.firstListBoxImage = new ImagePanel(); // No image was set
        this.firstListBoxImage.setBounds(4, 2, 16, 16);
        this.firstListBoxImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                firstListBoxImageClick(event);
            }
        });
        
        this.firstListBoxLabel = new JLabel("Contexts");
        this.firstListBoxLabel.setBounds(24, 2, 66, 17);
        
        this.FirstListBox = new JList(new DefaultListModel());
        this.FirstListBox.setFixedCellHeight(16);
        this.FirstListBox.setBounds(4, 20, 245, 245);
        //  --------------- UNHANDLED ATTRIBUTE: this.FirstListBox.Style = lbOwnerDrawFixed;
        this.FirstListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent event) {
                FirstListBoxMouseDown(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.FirstListBox.Sorted = True;
        //  --------------- UNHANDLED ATTRIBUTE: this.FirstListBox.OnDrawItem = FirstListBoxDrawItem;
        
        this.PanelFirstList = new JPanel(null);
        // -- this.PanelFirstList.setLayout(new BoxLayout(this.PanelFirstList, BoxLayout.Y_AXIS));
        this.PanelFirstList.add(firstListBoxImage);
        this.PanelFirstList.add(firstListBoxLabel);
        JScrollPane scroller4 = new JScrollPane();
        scroller4.setBounds(4, 20, 245, 245);;
        scroller4.setViewportView(FirstListBox);
        this.PanelFirstList.add(scroller4);
        this.PanelFirstList.setBounds(0, 0, 253, 1);
        //  --------------- UNHANDLED ATTRIBUTE: this.PanelFirstList.OnResize = PanelFirstListResize;
        
        this.SecondListBoxImage = new ImagePanel(); // No image was set
        this.SecondListBoxImage.setBounds(4, 2, 16, 16);
        
        this.SecondListBoxLabel = new JLabel("Commands");
        this.SecondListBoxLabel.setBounds(22, 2, 85, 17);
        
        this.SecondListBox = new JList(new DefaultListModel());
        this.SecondListBox.setFixedCellHeight(16);
        this.SecondListBox.setBounds(4, 20, 313, 245);
        //  --------------- UNHANDLED ATTRIBUTE: this.SecondListBox.Style = lbOwnerDrawFixed;
        this.SecondListBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent event) {
                SecondListBoxMouseDown(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.SecondListBox.Sorted = True;
        //  --------------- UNHANDLED ATTRIBUTE: this.SecondListBox.OnDrawItem = SecondListBoxDrawItem;
        
        this.PanelSecondList = new JPanel(null);
        // -- this.PanelSecondList.setLayout(new BoxLayout(this.PanelSecondList, BoxLayout.Y_AXIS));
        this.PanelSecondList.add(SecondListBoxImage);
        this.PanelSecondList.add(SecondListBoxLabel);
        JScrollPane scroller5 = new JScrollPane();
        scroller5.setBounds(4, 20, 313, 245);;
        scroller5.setViewportView(SecondListBox);
        this.PanelSecondList.add(scroller5);
        this.PanelSecondList.setBounds(256, 0, 266, 1);
        //  --------------- UNHANDLED ATTRIBUTE: this.PanelSecondList.OnResize = PanelSecondListResize;
        
        this.PanelLists = new JPanel(null);
        // -- this.PanelLists.setLayout(new BoxLayout(this.PanelLists, BoxLayout.Y_AXIS));
        this.PanelLists.add(SplitterLists);
        this.PanelLists.add(PanelFirstList);
        this.PanelLists.add(PanelSecondList);
        this.PanelLists.setBounds(0, 0, 522, 1);
        //  --------------- UNHANDLED ATTRIBUTE: this.PanelLists.OnResize = PanelListsResize;
        //  --------------- UNHANDLED ATTRIBUTE: this.PanelLists.Caption = 'PanelLists';
        
        this.TabSheetBrowse.add(PanelLists);
        this.ListPages.addTab("Browser", null, this.PanelLists);
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetBrowse.Width = 522;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetBrowse.Top = 2;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetBrowse.Height = 1;
        //  --------------- UNHANDLED ATTRIBUTE: this.TabSheetBrowse.Left = 2;
        
        this.ListPages.add(TabSheetTable);
        this.ListPages.add(TabSheetMap);
        this.ListPages.add(TabSheetBrowse);
        this.ListPages.setBounds(1, -17, 526, 1);
        //  --------------- UNHANDLED ATTRIBUTE: this.ListPages.ActivePage = TabSheetMap;
        //  --------------- UNHANDLED ATTRIBUTE: this.ListPages.TabPosition = tpBottom;
        //  --------------- UNHANDLED ATTRIBUTE: this.ListPages.OnChange = ListPagesChange;
        //  --------------- UNHANDLED ATTRIBUTE: this.ListPages.TabIndex = 1;
        
        Image NewRuleButtonImage = toolkit.createImage("../resources/RuleEditorForm_NewRuleButton.png");
        this.NewRuleButton = new SpeedButton("New", new ImageIcon(NewRuleButtonImage));
        this.NewRuleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                NewRuleButtonClick(event);
            }
        });
        this.NewRuleButton.setBounds(5, 5, 61, 25);
        this.NewRuleButton.setToolTipText("Make a new rule");
        
        Image DuplicateRuleButtonImage = toolkit.createImage("../resources/RuleEditorForm_DuplicateRuleButton.png");
        this.DuplicateRuleButton = new SpeedButton("Duplicate", new ImageIcon(DuplicateRuleButtonImage));
        this.DuplicateRuleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                DuplicateRuleButtonClick(event);
            }
        });
        this.DuplicateRuleButton.setBounds(68, 5, 89, 25);
        this.DuplicateRuleButton.setToolTipText("Duplicate the rule showing in the rule editor panel");
        
        Image DeleteRuleButtonImage = toolkit.createImage("../resources/RuleEditorForm_DeleteRuleButton.png");
        this.DeleteRuleButton = new SpeedButton("Delete", new ImageIcon(DeleteRuleButtonImage));
        this.DeleteRuleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                DeleteRuleButtonClick(event);
            }
        });
        this.DeleteRuleButton.setBounds(159, 5, 73, 25);
        this.DeleteRuleButton.setToolTipText("Delete all selected rules");
        
        Image MoveUpButtonImage = toolkit.createImage("../resources/RuleEditorForm_MoveUpButton.png");
        this.MoveUpButton = new SpeedButton("Raise", new ImageIcon(MoveUpButtonImage));
        this.MoveUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MoveUpButtonClick(event);
            }
        });
        this.MoveUpButton.setBounds(238, 5, 69, 25);
        this.MoveUpButton.setToolTipText("Raise all selected rules");
        
        Image MoveDownButtonImage = toolkit.createImage("../resources/RuleEditorForm_MoveDownButton.png");
        this.MoveDownButton = new SpeedButton("Lower", new ImageIcon(MoveDownButtonImage));
        this.MoveDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                MoveDownButtonClick(event);
            }
        });
        this.MoveDownButton.setBounds(310, 5, 73, 25);
        this.MoveDownButton.setToolTipText("Lower all selected rules");
        
        Image insertSoundImage = toolkit.createImage("../resources/RuleEditorForm_insertSound.png");
        this.insertSound = new SpeedButton("Sound", new ImageIcon(insertSoundImage));
        this.insertSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                insertSoundClick(event);
            }
        });
        // ----- NumGlyphs was 2. Should split image file manually and use setPressedIcon(Icon)
        this.insertSound.setBounds(392, 5, 73, 25);
        this.insertSound.setToolTipText("Insert a sound into a reply");
        
        Image InsertMusicButtonImage = toolkit.createImage("../resources/RuleEditorForm_InsertMusicButton.png");
        this.InsertMusicButton = new SpeedButton("Music", new ImageIcon(InsertMusicButtonImage));
        this.InsertMusicButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                InsertMusicButtonClick(event);
            }
        });
        this.InsertMusicButton.setBounds(468, 5, 73, 25);
        this.InsertMusicButton.setToolTipText("Insert music into a reply");
        
        this.PanelButtonBar = new JPanel(null);
        // -- this.PanelButtonBar.setLayout(new BoxLayout(this.PanelButtonBar, BoxLayout.Y_AXIS));
        this.PanelButtonBar.add(NewRuleButton);
        this.PanelButtonBar.add(DuplicateRuleButton);
        this.PanelButtonBar.add(DeleteRuleButton);
        this.PanelButtonBar.add(MoveUpButton);
        this.PanelButtonBar.add(MoveDownButton);
        this.PanelButtonBar.add(insertSound);
        this.PanelButtonBar.add(InsertMusicButton);
        this.PanelButtonBar.setBounds(1, -34, 526, 34);
        
        this.PanelTop = new JPanel(null);
        // -- this.PanelTop.setLayout(new BoxLayout(this.PanelTop, BoxLayout.Y_AXIS));
        this.PanelTop.add(ListPages);
        this.PanelTop.add(PanelButtonBar);
        this.PanelTop.setBounds(0, -116, 528, 1);
        
        //  ------- UNHANDLED TYPE TOpenDialog: WaveFileOpenDialog 
        //  --------------- UNHANDLED ATTRIBUTE: this.WaveFileOpenDialog.Title = 'Open existing file';
        //  --------------- UNHANDLED ATTRIBUTE: this.WaveFileOpenDialog.FilterIndex = 0;
        //  --------------- UNHANDLED ATTRIBUTE: this.WaveFileOpenDialog.top = 220;
        //  --------------- UNHANDLED ATTRIBUTE: this.WaveFileOpenDialog.Filter = 'Wave files|*.wav';
        //  --------------- UNHANDLED ATTRIBUTE: this.WaveFileOpenDialog.left = 508;
        
        //  ------- UNHANDLED TYPE TImageList: ImageList 
        //  --------------- UNHANDLED ATTRIBUTE: this.ImageList.top = 258;
        //  --------------- UNHANDLED ATTRIBUTE: this.ImageList.left = 508;
        
        //  ------- UNHANDLED TYPE TFontDialog: FontDialog 
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Title = 'Select a font';
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.top = 260;
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Font.Name = 'Arial';
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Font.Height = -13;
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.left = 472;
        
        this.RequirementsListBox.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        this.ChangesListBox.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        this.RequirementsEdit.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        this.ChangesEdit.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        this.ContextEdit.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        this.CommandEdit.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        this.ReplyMemo.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        this.MoveEdit.addMouseListener(new MenuPopupMouseListener(this.getEditPopupMenu()));
        this.MapImage.addMouseListener(new MenuPopupMouseListener(this.getMapPopupMenu()));
        delphiPanel.add(SplitterEdit);
        delphiPanel.add(PanelEditor);
        delphiPanel.add(PanelTop);
        return delphiPanel;
    }
    
        
    public void DeleteRuleButtonClick(ActionEvent event) {
        System.out.println("DeleteRuleButtonClick");
    }
        
    public void DuplicateRuleButtonClick(ActionEvent event) {
        System.out.println("DuplicateRuleButtonClick");
    }
        
    public void EditEnterCommit(FocusEvent event) {
        System.out.println("EditEnterCommit");
    }
        
    public void FirstListBoxMouseDown(MouseEvent event) {
        System.out.println("FirstListBoxMouseDown");
    }
        
    public void FormKeyDown(KeyEvent event) {
        System.out.println("FormKeyDown");
    }
        
    public void InsertMusicButtonClick(ActionEvent event) {
        System.out.println("InsertMusicButtonClick");
    }
        
    public void ListBoxEditExit(FocusEvent event) {
        System.out.println("ListBoxEditExit");
    }
        
    public void ListBoxEditKeyPress(KeyEvent event) {
        System.out.println("ListBoxEditKeyPress");
    }
        
    public void ListBoxExit(FocusEvent event) {
        System.out.println("ListBoxExit");
    }
        
    public void ListBoxKeyUp(KeyEvent event) {
        System.out.println("ListBoxKeyUp");
    }
        
    public void ListBoxMouseUp(MouseEvent event) {
        System.out.println("ListBoxMouseUp");
    }
        
    public void MapImageMouseDown(MouseEvent event) {
        System.out.println("MapImageMouseDown");
    }
        
    public void MapImageMouseMove(MouseEvent event) {
        System.out.println("MapImageMouseMove");
    }
        
    public void MapImageMouseUp(MouseEvent event) {
        System.out.println("MapImageMouseUp");
    }
        
    public void MenuDisplayShowButtonBarClick(ActionEvent event) {
        System.out.println("MenuDisplayShowButtonBarClick");
    }
        
    public void MenuEditClick(MouseEvent event) {
        System.out.println("MenuEditClick");
    }
        
    public void MenuEditCopyClick(ActionEvent event) {
        System.out.println("MenuEditCopyClick");
    }
        
    public void MenuEditCutClick(ActionEvent event) {
        System.out.println("MenuEditCutClick");
    }
        
    public void MenuEditInsertMusicClick(ActionEvent event) {
        System.out.println("MenuEditInsertMusicClick");
    }
        
    public void MenuEditInsertPictureClick(ActionEvent event) {
        System.out.println("MenuEditInsertPictureClick");
    }
        
    public void MenuEditInsertSoundClick(ActionEvent event) {
        System.out.println("MenuEditInsertSoundClick");
    }
        
    public void MenuEditLogFileClick(ActionEvent event) {
        System.out.println("MenuEditLogFileClick");
    }
        
    public void MenuEditPasteClick(ActionEvent event) {
        System.out.println("MenuEditPasteClick");
    }
        
    public void MenuEditPreferencesClick(ActionEvent event) {
        System.out.println("MenuEditPreferencesClick");
    }
        
    public void MenuEditRedoClick(ActionEvent event) {
        System.out.println("MenuEditRedoClick");
    }
        
    public void MenuEditUndoClick(ActionEvent event) {
        System.out.println("MenuEditUndoClick");
    }
        
    public void MenuFileExitClick(ActionEvent event) {
        System.out.println("MenuFileExitClick");
    }
        
    public void MenuFileExportClick(ActionEvent event) {
        System.out.println("MenuFileExportClick");
    }
        
    public void MenuFileMergeWithWorldClick(ActionEvent event) {
        System.out.println("MenuFileMergeWithWorldClick");
    }
        
    public void MenuFileNewWorldClick(ActionEvent event) {
        System.out.println("MenuFileNewWorldClick");
    }
        
    public void MenuFileOpenWorldClick(ActionEvent event) {
        System.out.println("MenuFileOpenWorldClick");
    }
        
    public void MenuFileSaveWorldAsClick(ActionEvent event) {
        System.out.println("MenuFileSaveWorldAsClick");
    }
        
    public void MenuFileSaveWorldClick(ActionEvent event) {
        System.out.println("MenuFileSaveWorldClick");
    }
        
    public void MenuHelpAboutClick(ActionEvent event) {
        System.out.println("MenuHelpAboutClick");
    }
        
    public void MenuHelpBasicConceptsClick(ActionEvent event) {
        System.out.println("MenuHelpBasicConceptsClick");
    }
        
    public void MenuHelpContentsClick(ActionEvent event) {
        System.out.println("MenuHelpContentsClick");
    }
        
    public void MenuHelpEditingWorldsClick(ActionEvent event) {
        System.out.println("MenuHelpEditingWorldsClick");
    }
        
    public void MenuHelpRegisterClick(ActionEvent event) {
        System.out.println("MenuHelpRegisterClick");
    }
        
    public void MenuHelpTutorialClick(ActionEvent event) {
        System.out.println("MenuHelpTutorialClick");
    }
        
    public void MenuMapLinkWizardClick(ActionEvent event) {
        System.out.println("MenuMapLinkWizardClick");
    }
        
    public void MenuMapQuickCommandsClick(ActionEvent event) {
        System.out.println("MenuMapQuickCommandsClick");
    }
        
    public void MenuMapsQuickContextsClick(ActionEvent event) {
        System.out.println("MenuMapsQuickContextsClick");
    }
        
    public void MenuMapsShowCommandsClick(ActionEvent event) {
        System.out.println("MenuMapsShowCommandsClick");
    }
        
    public void MenuOptionsShowRuleEditorClick(ActionEvent event) {
        System.out.println("MenuOptionsShowRuleEditorClick");
    }
        
    public void MenuRuleDeleteClick(ActionEvent event) {
        System.out.println("MenuRuleDeleteClick");
    }
        
    public void MenuRuleDuplicateClick(ActionEvent event) {
        System.out.println("MenuRuleDuplicateClick");
    }
        
    public void MenuRuleLowerClick(ActionEvent event) {
        System.out.println("MenuRuleLowerClick");
    }
        
    public void MenuRuleNewClick(ActionEvent event) {
        System.out.println("MenuRuleNewClick");
    }
        
    public void MenuRuleRaiseClick(ActionEvent event) {
        System.out.println("MenuRuleRaiseClick");
    }
        
    public void MenuRuleTestReplyClick(ActionEvent event) {
        System.out.println("MenuRuleTestReplyClick");
    }
        
    public void MenuToolsGenerateJavaClick(ActionEvent event) {
        System.out.println("MenuToolsGenerateJavaClick");
    }
        
    public void MenuToolsSearchClick(ActionEvent event) {
        System.out.println("MenuToolsSearchClick");
    }
        
    public void MenuWorldSwitchToPlayerClick(ActionEvent event) {
        System.out.println("MenuWorldSwitchToPlayerClick");
    }
        
    public void MoveDownButtonClick(ActionEvent event) {
        System.out.println("MoveDownButtonClick");
    }
        
    public void MoveUpButtonClick(ActionEvent event) {
        System.out.println("MoveUpButtonClick");
    }
        
    public void NewRuleButtonClick(ActionEvent event) {
        System.out.println("NewRuleButtonClick");
    }
        
    public void PopupNewCommandClick(ActionEvent event) {
        System.out.println("PopupNewCommandClick");
    }
        
    public void PopupNewContextClick(ActionEvent event) {
        System.out.println("PopupNewContextClick");
    }
        
    public void PopupNewLinkClick(ActionEvent event) {
        System.out.println("PopupNewLinkClick");
    }
        
    public void ReplyMemoMouseUp(MouseEvent event) {
        System.out.println("ReplyMemoMouseUp");
    }
        
    public void RuleGridMouseDown(MouseEvent event) {
        System.out.println("RuleGridMouseDown");
    }
        
    public void RuleGridMouseUp(MouseEvent event) {
        System.out.println("RuleGridMouseUp");
    }
        
    public void SecondListBoxMouseDown(MouseEvent event) {
        System.out.println("SecondListBoxMouseDown");
    }
        
    public void SpeedButtonClick(ActionEvent event) {
        System.out.println("SpeedButtonClick");
    }
        
    public void firstListBoxImageClick(MouseEvent event) {
        System.out.println("firstListBoxImageClick");
    }
        
    public void insertSoundClick(ActionEvent event) {
        System.out.println("insertSoundClick");
    }
        
    public void replyPictureMouseUp(MouseEvent event) {
        System.out.println("replyPictureMouseUp");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RuleEditorWindow thisClass = new RuleEditorWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

}
