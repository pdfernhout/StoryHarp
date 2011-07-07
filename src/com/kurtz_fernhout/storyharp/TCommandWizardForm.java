package com.kurtz_fernhout.storyharp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;

import net.miginfocom.swing.MigLayout;
import java.awt.Rectangle;

public class TCommandWizardForm extends JDialog {
    private static final long serialVersionUID = 1L;
    
    // const
    public static final int kStartPage = 0;
    public static final int kContextPage = 1;
    public static final int kCommandsPage = 2;
    public static final int kSequencePage = 3;
    public static final int kFinishPage = 4;
    
    JTabbedPane notebook;
    JPanel page1;
    JLabel Label1;
    JLabel Label2;
    ImagePanel Image1;
    JLabel Label7;
    JLabel Label17;
    JLabel Label26;
    JLabel Label3;
    ImagePanel commandStartPageImage;
    JLabel Label5;
    ImagePanel contextStartPageImage;
    JLabel Label6;
    ImagePanel replyStartPageImage;
    JLabel Label10;
    JLabel Label19;
    ImagePanel requirementsStartPageImage;
    ImagePanel changesStartPageImage;
    JLabel Label24;
    JLabel Label25;
    JPanel page2;
    ImagePanel Image3;
    JLabel Label8;
    JLabel Label11;
    ImagePanel contextListImage;
    DefaultListModel ContextBoxModel;
    JList ContextBox;
    JPanel page3;
    ImagePanel Image2;
    JLabel Label9;
    JLabel Label12;
    JLabel Label4;
    JLabel Label14;
    JLabel newCommandsForContextLabel;
    JTextArea NewCommandsMemo;
    JPanel page4;
    JLabel PrefixLabel;
    ImagePanel Image4;
    JLabel Label13;
    ImagePanel prefixArrow;
    JLabel prefixNote;
    JLabel Label15;
    JLabel Label27;
    JCheckBox GenerateSequence;
    JTextField PrefixEdit;
    JLabel endSequenceLabel;
    ImagePanel sequenceEndArrow;
    JRadioButton endSequenceLoopToFirst;
    JRadioButton endSequenceRemoveTheLastCommand;
    JRadioButton endSequenceLeaveLastCommand;
    JPanel page5;
    JLabel Label18;
    JLabel Label16;
    JLabel Label20;
    ImagePanel Image8;
    JLabel Label21;
    JLabel Label22;
    JLabel Label23;
    JButton helpButton;
    JButton goBack;
    JButton goNext;
    JButton cancel;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
	private JPanel navigationPanel;
	
    public boolean generated;

	private String lastFloodedContextPrefix;
    
    public TCommandWizardForm() {
        super();
        this.setModal(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 452));
        this.setContentPane(getMainContentPane());
        this.setTitle("New Commands Wizard");
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

        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnCloseQuery = FormCloseQuery;
        //  --------------- UNHANDLED ATTRIBUTE: this.ActiveControl = ContextBox;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.BorderStyle = bsDialog;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 100;
        
        
        this.notebook = new JTabbedPane();
        notebook.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent event) {
                notebookPageChanged(event);
            }
        });
        
        this.page1 = new JPanel(new MigLayout());
        this.Label1 = new JLabel("<html>This wizard will help you quickly create a set of new rules based on one context and a list of commands you enter.");
        
        this.Label2 = new JLabel("<html>You can also link your new commands in a sequence so that only one command is available at any time. This is done by generating requirements and changes.");
        
        Image Image1Image = toolkit.createImage("resources/CommandWizardForm_Image1.png");
        this.Image1 = new ImagePanel(new ImageIcon(Image1Image));
        
        this.Label7 = new JLabel("Welcome to the New Commands Wizard!");
        
        this.Label17 = new JLabel("Click the Next button to begin.");
        
        this.Label26 = new JLabel("<html>You can click Cancel at any time to close the wizard without making any new rules.");
        
        this.Label3 = new JLabel("You can add extra requirements or changes later using the editor.");
        
        this.commandStartPageImage = new ImagePanel(); // No image was set
        
        this.Label5 = new JLabel("A command is what you say to the computer.");
        
        this.contextStartPageImage = new ImagePanel(); // No image was set
        
        this.Label6 = new JLabel("<html>A context is the single most important requirement for the use of a command, usually a physical location.");
        
        this.replyStartPageImage = new ImagePanel(); // No image was set
        
        this.Label10 = new JLabel("A reply is what the computer says after you say a command.");
        
        this.Label19 = new JLabel("You can enter a reply for each command.");
        
        this.requirementsStartPageImage = new ImagePanel(); // No image was set
        
        this.changesStartPageImage = new ImagePanel(); // No image was set
        
        this.Label24 = new JLabel("<html>A requirement is variable state necessary for a command to be available. Variables can be true or false.");
        
        this.Label25 = new JLabel("A change is a new variable state resulting from a command.");
        
        page1.add(Image1, "split, wmin 32, hmin 32");
        page1.add(Label7, "wrap");
        page1.add(Label1, "wrap");
        page1.add(commandStartPageImage, "split");
        page1.add(Label5, "wrap");
        page1.add(contextStartPageImage, "split");
        page1.add(Label6, "wrap");
        page1.add(Label19, "wrap");
        page1.add(replyStartPageImage, "split");
        page1.add(Label10, "wrap");
        page1.add(Label2, "wrap");
        page1.add(requirementsStartPageImage, "split");
        page1.add(Label24, "wrap");
        page1.add(changesStartPageImage, "split");
        page1.add(Label25, "wrap");
        page1.add(Label3, "wrap");
        page1.add(Label17, "wrap");
        page1.add(Label26, "wrap"); 
        
        this.notebook.addTab("Introduction", null, this.page1);
        
        this.page2 = new JPanel(new MigLayout());
        Image Image3Image = toolkit.createImage("resources/CommandWizardForm_Image3.png");
        this.Image3 = new ImagePanel(new ImageIcon(Image3Image));
        
        this.Label8 = new JLabel("Choose the context you want your new commands to use.");
        
        this.Label11 = new JLabel("<html>You can also choose a context by selecting it in the Map view before you open the wizard.");
        
        this.contextListImage = new ImagePanel(); // No image was set
        
        this.ContextBoxModel = new DefaultListModel();
        this.ContextBox = new JList(ContextBoxModel);
        this.ContextBox.setFixedCellHeight(14);
        
        this.page2.add(Image3, "split");
        this.page2.add(Label8, "wrap");
        
        this.page2.add(contextListImage, "split");
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setViewportView(ContextBox);
        
        this.page2.add(scroller1, "grow, push, wrap");
        this.page2.add(Label11, "wrap");
        
        this.notebook.addTab("Context", null, this.page2);
        
        this.page3 = new JPanel(new MigLayout());
        
        Image Image2Image = toolkit.createImage("resources/CommandWizardForm_Image2.png");
        this.Image2 = new ImagePanel(new ImageIcon(Image2Image));
        
        this.Label9 = new JLabel("Command | Reply");
        
        this.Label12 = new JLabel(" When you are finished entering commands, click Next.");
        
        this.Label4 = new JLabel("<html>Enter or paste the commands you want to create in the area below, separating each command from its reply by a pipe bar (\"|\").<br>&nbsp;&nbsp;&nbsp;&nbsp;For example, \"open the door | You open the rusty door.\"<br>Descriptions are optional.<br>Use carriage returns to separate entries. It is okay to wrap entries on more than one line.");
        
        this.newCommandsForContextLabel = new JLabel("New commands for: context");
        
        this.NewCommandsMemo = new JTextArea(10, 10);
        
        this.page3.add(newCommandsForContextLabel, "wrap");
        this.page3.add(Image2, "split, top");
        this.page3.add(Label4, "wrap");
        this.page3.add(Label9, "gaptop 20, wrap");

        JScrollPane scroller2 = new JScrollPane();
        scroller2.setViewportView(NewCommandsMemo);
        this.page3.add(scroller2, "push, grow, wrap");
        
        this.page3.add(Label12, "wrap");
        this.notebook.addTab("Commands", null, this.page3);       
        
        this.page4 = new JPanel(new MigLayout());
        this.PrefixLabel = new JLabel("What prefix do you want to use for the requirements that create the sequence?");
        
        Image Image4Image = toolkit.createImage("resources/CommandWizardForm_Image4.png");
        this.Image4 = new ImagePanel(new ImageIcon(Image4Image));
        
        this.Label13 = new JLabel("<html>Do you want to link your new commands in sequence, so that each becomes available only after the previous one is said?");
        
        Image prefixArrowImage = toolkit.createImage("resources/CommandWizardForm_prefixArrow.png");
        this.prefixArrow = new ImagePanel(new ImageIcon(prefixArrowImage));
        
        this.prefixNote = new JLabel("<html>Examples are: \"talking to sailor\", \"in boarding house\".<br>By default the prefix is the same as the context.");
        
        this.Label15 = new JLabel("<html>Sequences are useful when the user has to do several things in a certain order, such as steps in an assembly process or parts of a conversation.");
        
        this.Label27 = new JLabel("Creating sequences is an advanced topic; see the help system for details.");
        
        this.GenerateSequence = new JCheckBox("Yes, link the commands in a sequence.");
        this.GenerateSequence.setSelected(true);
        GenerateSequence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                GenerateSequenceClick(event);
            }
        });
        
        this.PrefixEdit = new JTextField("");
        
        this.endSequenceLabel = new JLabel(" When the last command has been said,");
        
        Image sequenceEndArrowImage = toolkit.createImage("resources/CommandWizardForm_sequenceEndArrow.png");
        this.sequenceEndArrow = new ImagePanel(new ImageIcon(sequenceEndArrowImage));
        
        this.endSequenceLoopToFirst = new JRadioButton("loop to the first command in the sequence");
        this.endSequenceLoopToFirst.setSelected(true);
        // ----- manually fix radio button group by putting multiple buttons in the same group
        ButtonGroup group = new ButtonGroup();
        group.add(this.endSequenceLoopToFirst);
        
        this.endSequenceRemoveTheLastCommand = new JRadioButton("remove the last command");
        // ----- manually fix radio button group by putting multiple buttons in the same group
        group.add(this.endSequenceRemoveTheLastCommand);
        
        this.endSequenceLeaveLastCommand = new JRadioButton("leave the last command available");
        // ----- manually fix radio button group by putting multiple buttons in the same group
        group.add(this.endSequenceLeaveLastCommand);
        
        this.page4.add(Image4, "split");
        this.page4.add(Label13, "wrap");
        this.page4.add(GenerateSequence, "wrap");
        this.page4.add(Label15, "wrap");
        this.page4.add(Label27, "wrap");        

        this.page4.add(prefixArrow, "split");
        this.page4.add(PrefixLabel, "wrap");
        this.page4.add(PrefixEdit, "growx, wrap");
        this.page4.add(prefixNote, "wrap, gapbottom 20");
        
        this.page4.add(sequenceEndArrow, "split");
        this.page4.add(endSequenceLabel, "wrap");
        this.page4.add(endSequenceLoopToFirst, "wrap");
        this.page4.add(endSequenceLeaveLastCommand, "wrap");
        this.page4.add(endSequenceRemoveTheLastCommand, "wrap");
        
        this.notebook.addTab("Sequence", null, this.page4);
        
        this.page5 = new JPanel(new MigLayout());
        this.Label18 = new JLabel("Congratulations!");
        
        this.Label16 = new JLabel("<html>You have completed the information the wizard needs to generate a new set of rules based on the commands you have entered.");
        
        this.Label20 = new JLabel("<html>You can click Back to review your choices. Or, click Cancel to close the wizard without making any new rules.");
        
        Image Image8Image = toolkit.createImage("resources/CommandWizardForm_Image8.png");
        this.Image8 = new ImagePanel(new ImageIcon(Image8Image));
        
        this.Label21 = new JLabel("<html>The command and reply texts you entered here will also be saved in the log file (even if you cancel using the wizard).");
        
        this.Label22 = new JLabel("<html>Click Finish to create the new rules and close the wizard.");
        
        this.Label23 = new JLabel("<html>After you finish the wizard, you can choose Undo from the Edit menu to remove your new rules.");
        
        this.page5.add(Image8, "split");
        this.page5.add(Label18, "wrap");
        this.page5.add(Label16, "wrap");
        this.page5.add(Label21, "wrap");
        this.page5.add(Label22, "wrap");
        this.page5.add(Label23, "wrap");
        this.page5.add(Label20, "wrap");
        
        this.notebook.addTab("Conclusion", null, this.page5);
        
        this.notebook.add(page1);
        this.notebook.add(page2);
        this.notebook.add(page3);
        this.notebook.add(page4);
        this.notebook.add(page5);
        notebook.setTabPlacement(JTabbedPane.BOTTOM);
        
        this.helpButton = new JButton("Help");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                helpButtonClick(event);
            }
        });
        this.helpButton.setMnemonic(KeyEvent.VK_H);
        
        this.goBack = new JButton("<< Back");
        goBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                goBackClick(event);
            }
        });
        this.goBack.setMnemonic(KeyEvent.VK_B);
        
        this.goNext = new JButton("Next >>");
        goNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                goNextClick(event);
            }
        });
        this.goNext.setMnemonic(KeyEvent.VK_N);
        
        this.cancel = new JButton("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                cancelClick(event);
            }
        });
        this.cancel.setMnemonic(KeyEvent.VK_C);
        
        this.navigationPanel = new JPanel();
                
        navigationPanel.add(helpButton);
        navigationPanel.add(goBack);
        navigationPanel.add(goNext);
        navigationPanel.add(cancel);
        
        delphiPanel.add(notebook, BorderLayout.CENTER);
        delphiPanel.add(navigationPanel, BorderLayout.SOUTH);
        return delphiPanel;
    }
    
        
    public void GenerateSequenceClick(ActionEvent event) {
        boolean doSequence = false;
        
        doSequence = this.GenerateSequence.isSelected();
        this.prefixArrow.setVisible(doSequence);
        this.PrefixLabel.setEnabled(doSequence);
        this.PrefixEdit.setVisible(doSequence);
        this.prefixNote.setEnabled(doSequence);
        this.sequenceEndArrow.setVisible(doSequence);
        this.endSequenceLabel.setEnabled(doSequence);
        this.endSequenceLoopToFirst.setEnabled(doSequence);
        this.endSequenceLeaveLastCommand.setEnabled(doSequence);
        this.endSequenceRemoveTheLastCommand.setEnabled(doSequence);
    }
        
    public void cancelClick(ActionEvent event) {
    	if (FormCloseQuery()) {
    		this.setVisible(false);
    	}
    }
        
    public void goBackClick(ActionEvent event) {
        if (this.notebook.getSelectedIndex() > kStartPage) {
            this.notebook.setSelectedIndex(this.notebook.getSelectedIndex() - 1);
        }
    }
    
    public String getSelectedContext() {
    	TSVariable variable = (TSVariable) this.ContextBox.getSelectedValue();
    	if (variable == null) return "";
    	return variable.phrase;
    }
        
    public void goNextClick(ActionEvent event) {
        if (this.notebook.getSelectedIndex() == kFinishPage) {
            this.generateRules();
            this.setVisible(false);
        } else if (this.notebook.getSelectedIndex() == kContextPage) {
            if ((this.ContextBox.getSelectedIndex() < 0)) {
                UFileSupport.ShowMessage("You must select a context to proceed.");
                return;
            }
            if ((this.PrefixEdit.getText().trim().equals("")) || (this.PrefixEdit.getText().trim().equals(this.lastFloodedContextPrefix))) {
                this.PrefixEdit.setText(this.getSelectedContext());
                this.lastFloodedContextPrefix = this.getSelectedContext();
            }
            this.newCommandsForContextLabel.setText("New commands for: " + this.getSelectedContext());
        } else if (this.notebook.getSelectedIndex() == kCommandsPage) {
            if ((this.NewCommandsMemo.getText().trim().equals(""))) {
                UFileSupport.ShowMessage("You must enter one or more commands to proceed.");
                return;
            }
        } else if (this.notebook.getSelectedIndex() == kSequencePage) {
            if (this.GenerateSequence.isSelected()) {
                if ((this.PrefixEdit.getText().trim().equals(""))) {
                    UFileSupport.ShowMessage("You must enter a prefix to proceed.");
                    return;
                }
            }
        }
        if (this.notebook.getSelectedIndex() < kFinishPage) {
            this.notebook.setSelectedIndex(this.notebook.getSelectedIndex() + 1);
        }
    }
        
    public void helpButtonClick(ActionEvent event) {
    	Application.HelpJump("Making_new_rules_using_the_new_commands_wizard");
    } 
        
    public void notebookPageChanged(ChangeEvent event) {
    	if (this.goBack == null) return;
        this.goBack.setEnabled(this.notebook.getSelectedIndex() > kStartPage);
        if (this.notebook.getSelectedIndex() != kFinishPage) {
            this.goNext.setText("Next >>");
        } else {
            this.goNext.setText("Finish");
        }
    }

    
    /* 
    talk to the grue|The grue won't listen.
    talk to the grue|The grue seems to be getting very agitated.
    talk to the grue|The grue seems about to fly into a rage.
    talk to the grue|The grue devours you (except your bones of course).
    grue pit
    */
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	TCommandWizardForm thisClass = new TCommandWizardForm();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

	public boolean initializeAndCheckIfOKToUse() {
        this.generated = false;
        this.notebook.setSelectedIndex(kStartPage);
        Application.addWorldContextsToListBox(this.ContextBoxModel);
        if (this.ContextBox.getModel().getSize() < 1) {
            UFileSupport.ShowMessage("You must create at least one context before using the command wizard.");
            return false;
        }
        //self.GenerateSequenceClick(self);
        this.NewCommandsMemo.setText("");
        this.GenerateSequence.setSelected(false);
        this.PrefixEdit.setText("");
        this.endSequenceLoopToFirst.setSelected(true);
        this.lastFloodedContextPrefix = "";
        for (int i = 0; i < TSDomain.domain.world.variables.size(); i++) {
        	TSVariable variable = TSDomain.domain.world.variables.get(i);
            if (variable.selected) {
                for (int j = 0; j < this.ContextBox.getModel().getSize(); j++) {
                    if (this.ContextBoxModel.get(j).equals(variable.phrase)) {
                        this.ContextBox.setSelectedIndex(j);
                        break;
                    }
                }
            }
        }
        this.FormActivate();
        return true;
	}
	
    public void generateRules() {
        Application.ChangeLogForm.addToLog(this.NewCommandsMemo.getText());
        Point position = Application.RuleEditorForm.goodPosition();
        StringReader stream = new StringReader(this.NewCommandsMemo.getText());
        int index = 1;
        TSNewRulesCommand newRulesCommand = new TSNewRulesCommand();
        newRulesCommand.creator = "command sequence wizard";
        TSRule newRule = null;
        String prefix = "";
        try {
            int character = stream.read();
            while (character != -1) {
                String commandPhrase = "";
                String commandResponse = "";
                boolean pipeRead = false;
                prefix = this.PrefixEdit.getText().trim();
                // PDF PORT: Issue here with an empty character; is is supposed to be a space?
                while (character != -1 && character != '\n') {
                    if (pipeRead) {
                        commandResponse = commandResponse + Character.toString((char)character);;
                    } else if (character == '|') {
                        pipeRead = true;
                    } else {
                        commandPhrase = commandPhrase + Character.toString((char)character);
                    }
                    character = stream.read();
                }
                //  	UFileSupport.ShowMessage('name: ' + contextName);
                //  UFileSupport.ShowMessage('description: ' + contextDescription);
                String context = "";
                if (this.ContextBox.getSelectedIndex() >= 0) {
                    context = this.getSelectedContext();
                }
                if (commandResponse.trim().equals("")) {
                    commandResponse = "Nothing happens.";
                }
                if ((!commandPhrase.trim().equals("")) && (!context.trim().equals(""))) {
                    newRule = TSDomain.domain.world.newRule();
                    newRulesCommand.addRule(newRule);
                    Application.RuleEditorForm.lastChoice = newRule;
                    newRule.position = position;
                    newRule.setContext(context.trim());
                    newRule.setCommand(commandPhrase.trim());
                    newRule.setReply(commandResponse.trim());
                    if (this.GenerateSequence.isSelected() && (!prefix.equals(""))) {
                    	String requirements = null;
                    	String changes = null;
                        if (index == 1) {
                            requirements = "~" + prefix + " started";
                            changes = prefix + " started & " + prefix + " " + String.valueOf(index) + "0";
                        } else {
                            requirements = prefix + " " + String.valueOf(index - 1) + "0";
                            changes = "~" + prefix + " " + String.valueOf(index - 1) + "0 & " + prefix + " " + String.valueOf(index) + "0";
                        }
                        newRule.setRequirements(requirements);
                        newRule.setChanges(changes);
                    }
                    position.y = position.y + 60;
                    index += 1;
                }
                while (character == '\n') {
                    character = stream.read();
                }
            }
            if (this.GenerateSequence.isSelected() && (!prefix.equals("")) && (newRule != null) && (index > 2)) {
                // cleanup for last rule
                String changes = newRule.changesString;
                if (this.endSequenceLoopToFirst.isSelected()) {
                    changes = "~" + prefix + " " + String.valueOf(index - 2) + "0 & " + "~" + prefix + " started";
                } else if (this.endSequenceLeaveLastCommand.isSelected()) {
                    changes = "";
                } else if (this.endSequenceRemoveTheLastCommand.isSelected()) {
                    changes = "~" + prefix + " " + String.valueOf(index - 2) + "0";
                }
                newRule.setChanges(changes);
            }
        } catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
            stream.close();
        }
        if (newRulesCommand.rules.size() > 0) {
            TSDomain.domain.worldCommandList.doCommand(newRulesCommand);
        }
        Application.RuleEditorForm.updateForRuleChange();
        Application.RuleEditorForm.adjustScrollBars();
        this.generated = true;
    }
    
    public boolean FormCloseQuery() {
        if (this.generated) {
            return true;
        }
        if (!this.NewCommandsMemo.getText().trim().equals("")) {
            boolean result = UFileSupport.Confirm("You are about to close this wizard" + "\n" + "and lose any work done with it." + "\n" + "\n" + "Is this OK?");
            if (!result) {
                return false;
            }
            Application.ChangeLogForm.addToLog(this.NewCommandsMemo.getText());
        }
        return true;
    }
    
    public void FormActivate() {
        this.contextStartPageImage.imageIcon = Application.ConsoleForm.ContextButton.getIcon();
        this.commandStartPageImage.imageIcon = Application.ConsoleForm.CommandButton.getIcon();
        this.replyStartPageImage.imageIcon = Application.RuleEditorForm.ReplyButton.getIcon();
        this.requirementsStartPageImage.imageIcon = Application.ConsoleForm.RequirementsButton.getIcon();
        this.changesStartPageImage.imageIcon = Application.ConsoleForm.ChangesButton.getIcon();
        this.contextListImage.imageIcon = Application.ConsoleForm.ContextButton.getIcon();
    }

}  //  @jve:decl-index=0:visual-constraint="19,-13"
