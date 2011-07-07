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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;

import net.miginfocom.swing.MigLayout;

public class TContextWizardForm extends JDialog {
    private static final long serialVersionUID = 1L;
    
    // const
    public static final int kStartPage = 0;
    public static final int kContextsPage = 1;
    public static final int kDescriptionPage = 2;
    public static final int kFinishPage = 3;
    
    JButton goBack;
    JButton goNext;
    JButton cancel;
    JButton helpButton;
    JTabbedPane notebook;
    JPanel page1;
    JLabel Label1;
    JLabel Label2;
    JLabel Label3;
    JLabel Label7;
    JLabel Label9;
    ImagePanel Image1;
    JLabel Label10;
    JLabel Label26;
    JLabel Label8;
    ImagePanel commandStartPageImage;
    ImagePanel contextStartPageImage;
    ImagePanel replyStartPageImage;
    JPanel page2;
    JLabel Label5;
    JLabel Label6;
    JLabel Label21;
    JLabel Label22;
    ImagePanel Image3;
    JTextArea NewContextsMemo;
    JPanel page3;
    JLabel DescribeLabel;
    JLabel DescribeLabelExtra;
    JLabel Label4;
    ImagePanel DescribeImage;
    ImagePanel commandImage;
    JLabel Label15;
    JLabel Label16;
    JTextField DescribeEdit;
    JPanel page4;
    JLabel Label13;
    JLabel Label14;
    JLabel Label18;
    JLabel Label19;
    ImagePanel Image2;
    JLabel Label11;
    JLabel Label12;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
	private JPanel navigationPanel;	
	
	public boolean generated;
    
    public TContextWizardForm() {
        super();
        this.setModal(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 467));
        this.setContentPane(getMainContentPane());
        this.setTitle("New Contexts Wizard");
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
        //  --------------- UNHANDLED ATTRIBUTE: this.ActiveControl = goBack;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.BorderStyle = bsDialog;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 100;
        
        
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
        
        this.helpButton = new JButton("Help");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                helpButtonClick(event);
            }
        });
        this.helpButton.setMnemonic(KeyEvent.VK_H);
        
        this.notebook = new JTabbedPane();
        notebook.setTabPlacement(JTabbedPane.BOTTOM);
        notebook.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent event) {
                notebookPageChanged(event);
            }
        });
        
        this.page1 = new JPanel(new MigLayout());
        this.Label1 = new JLabel("Welcome to the New Contexts Wizard!");
        
        this.Label2 = new JLabel("<html>This wizard will help you quickly create a set of new rules based on contexts you enter.");
        
        this.Label3 = new JLabel("Click the Next button to begin.");
        
        this.Label7 = new JLabel("<html>You can enter a descriptive reply for each new context you enter. The descriptive replies will be accessed with a common command such as \"look\".");
        
        this.Label9 = new JLabel("A reply is what the computer says after you say a command.");
        
        Image Image1Image = toolkit.createImage("resources/ContextWizardForm_Image1.png");
        this.Image1 = new ImagePanel(new ImageIcon(Image1Image));
        
        this.Label10 = new JLabel("A command is what you say to the computer.");
        
        this.Label26 = new JLabel("<html>You can click Cancel at any time to close the wizard without making any new rules.");
        
        this.Label8 = new JLabel("<html>A context is the single most important requirement for the user of a command, usually a physical location.");
        
        this.commandStartPageImage = new ImagePanel(); // No image was set
        
        this.contextStartPageImage = new ImagePanel(); // No image was set
        
        this.replyStartPageImage = new ImagePanel(); // No image was set
        
        this.page1.add(Image1, "split");
        this.page1.add(Label1, "wrap");
        this.page1.add(Label2, "wrap");
        this.page1.add(Label10, "wrap");
        this.page1.add(commandStartPageImage, "split");
        this.page1.add(Label8, "wrap");
        this.page1.add(contextStartPageImage, "split");
        this.page1.add(Label7, "wrap");
        this.page1.add(replyStartPageImage, "split");
        this.page1.add(Label9, "wrap");
        this.page1.add(Label3, "wrap");
        this.page1.add(Label26, "wrap");
        this.notebook.addTab("Start", null, this.page1);
        
        this.page2 = new JPanel(new MigLayout());
        this.Label5 = new JLabel("<html>Enter or paste the contexts you want to create in the area below.<br>Separate each context from its descriptive reply by a pipe bar (\"|\").<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;For example: \"house | You are in a house\".<br>Descriptions are optional.<br>Use carriage returns to separate entries. It is okay if entries wrap onto multiple lines.<br>When you are finished entering contexts, click Next.");
        
        this.Label22 = new JLabel("Context | Descriptive Reply");
        
        Image Image3Image = toolkit.createImage("resources/ContextWizardForm_Image3.png");
        this.Image3 = new ImagePanel(new ImageIcon(Image3Image));
        
        this.NewContextsMemo = new JTextArea(10, 10);
        //  --------------- UNHANDLED ATTRIBUTE: this.NewContextsMemo.ScrollBars = ssVertical;
        NewContextsMemo.setWrapStyleWord(true);
        NewContextsMemo.setLineWrap(true);
        
        this.page2.add(Image3, "top, split");
        this.page2.add(Label5, "growx, wrap");
        this.page2.add(Label22, "gaptop 20, wrap");
        
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setViewportView(NewContextsMemo);
        this.page2.add(scroller1, "grow, push");
        this.notebook.addTab("EnterContexts", null, this.page2);
        
        this.page3 = new JPanel();
        page3.setLayout(new MigLayout());
        this.DescribeLabel = new JLabel(" What command should the user to say to access these descriptive replies?");
        
        this.DescribeLabelExtra = new JLabel("Some generic examples are:");
        
        this.Label4 = new JLabel("<html>If you have not entered a description for a context, the wizard will add a default description of \"There is nothing of interest here.\"");
        
        Image DescribeImageImage = toolkit.createImage("resources/ContextWizardForm_DescribeImage.png");
        this.DescribeImage = new ImagePanel(new ImageIcon(DescribeImageImage));
        
        this.commandImage = new ImagePanel(); // No image was set
        
        this.Label15 = new JLabel("     \"look\", \"listen\", \"smell\", \"feel\", \"taste\", and \"sense\".");
        
        this.Label16 = new JLabel("<html>You should stick with \"look\" unless you are doing something special. You can change individual commands later (in the editor) to deal with specific situations.");
        
        this.DescribeEdit = new JTextField("look");
        
        this.page3.add(DescribeImage, "split");
        this.page3.add(DescribeLabel, "wrap");
        this.page3.add(commandImage, "split");
        this.page3.add(DescribeEdit, "growx, wrap");
        this.page3.add(DescribeLabelExtra, "wrap");
        this.page3.add(Label15, "wrap");
        this.page3.add(Label16, "wrap");
        this.page3.add(Label4, "wrap");
        this.notebook.addTab("GenerateDescriptions", null, this.page3);
        
        this.page4 = new JPanel(new MigLayout());
        this.Label13 = new JLabel("<html>You have completed the information the wizard needs to generate a new set of rules based on your the contexts and descriptions you have entered.");
        
        this.Label14 = new JLabel("<html>Click Finish to create the new rules and close the wizard.");
        
        this.Label18 = new JLabel("Congratulations!");
        
        this.Label19 = new JLabel("<html>You can click Back to review your choices.<br>Or, you can click Cancel to close the wizard without making any new rules.");
        
        Image Image2Image = toolkit.createImage("resources/ContextWizardForm_Image2.png");
        this.Image2 = new ImagePanel(new ImageIcon(Image2Image));
        
        this.Label11 = new JLabel("<html>After you finish the wizard, you can choose Undo from the Edit menu to remove your new rules.");
        
        this.Label12 = new JLabel("<html>The text you entered here will also be saved in the log file (even if you cancel using the wizard).");
        
        this.page4.add(Image2, "split");
        this.page4.add(Label18, "wrap");
        this.page4.add(Label13, "wrap");
        this.page4.add(Label12, "wrap");
        this.page4.add(Label14, "wrap");
        this.page4.add(Label11, "wrap");
        this.page4.add(Label19, "wrap");
        this.notebook.addTab("Finish", null, this.page4);
        
        this.notebook.add(page1);
        this.notebook.add(page2);
        this.notebook.add(page3);
        this.notebook.add(page4);
        
        this.navigationPanel = new JPanel();
        
        navigationPanel.add(helpButton);
        navigationPanel.add(goBack);
        navigationPanel.add(goNext);
        navigationPanel.add(cancel);
        
        delphiPanel.add(notebook, BorderLayout.CENTER);
        delphiPanel.add(navigationPanel, BorderLayout.SOUTH);
        
        // give some fields popup edit menus
        new GenericCutCopyPastePopupMenu(NewContextsMemo);
        new GenericCutCopyPastePopupMenu(DescribeEdit);
        
        return delphiPanel;
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
        
    public void goNextClick(ActionEvent event) {
        if (this.notebook.getSelectedIndex() == kFinishPage) {
            this.generateRules();
            if (this.generated) {
            	this.setVisible(false);
            }
        } else if (this.notebook.getSelectedIndex() == kContextsPage) {
            if ((this.NewContextsMemo.getText().trim().equals(""))) {
                UFileSupport.ShowMessage("You must enter one or more contexts to proceed.");
                return;
            }
        } else if (this.notebook.getSelectedIndex() == kDescriptionPage) {
            if ((this.DescribeEdit.getText().trim().equals(""))) {
                UFileSupport.ShowMessage("You must enter a command to be used to describe these contexts.");
                return;
            }
        }
        if (this.notebook.getSelectedIndex() < kFinishPage) {
            this.notebook.setSelectedIndex(this.notebook.getSelectedIndex() + 1);
        }
    }
        
    public void helpButtonClick(ActionEvent event) {
        Application.HelpJump("Making_new_rules_using_the_new_contexts_wizard");
    }
        
    public void notebookPageChanged(ChangeEvent event) {
        this.goBack.setEnabled(this.notebook.getSelectedIndex() > kStartPage);
        if (this.notebook.getSelectedIndex() != kFinishPage) {
            this.goNext.setText("Next >>");
        } else {
            this.goNext.setText("Finish");
        }
    }

    /*
    cave|You are in a big cave.
    forest|You are in a lively forest.
    spring|You are standing near a burbling spring.
    
    well house|You are in a well house for a small spring.
    grate|You are standing above a grate.
    forest|You are wandering around in dense forest.
    glade|You are in a forest glade.
    stream|You are walking along a dry stream bed.
    */
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	TContextWizardForm thisClass = new TContextWizardForm();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    public void generateRules() {
        Application.ChangeLogForm.addToLog(this.NewContextsMemo.getText());
        Point position = Application.RuleEditorForm.goodPosition();
        StringReader stream = new StringReader(this.NewContextsMemo.getText());
        
        //String previousContext = "";
        TSNewRulesCommand newRulesCommand = new TSNewRulesCommand();
        newRulesCommand.creator = "new context wizard";
        try {
            int character = stream.read();
            while (character != -1) {
                String line = "";
                String contextName = "";
                String contextDescription = "";
                boolean pipeRead = false;
                // PDF PORT FIX: ISSUE WITH EMPTY CHARACTER: Should it be a space?
                while (character != -1 && character != '\n') {
                    line = line + character;
                    if (pipeRead) {
                        contextDescription = contextDescription + Character.toString((char)character);
                    } else if (character == '|') {
                        pipeRead = true;
                    } else {
                        contextName = contextName + Character.toString((char)character);
                    }
                    character = stream.read();
                }
                if (!contextName.trim().equals("")) {
                    if ((!this.DescribeEdit.getText().trim().equals(""))) {
                        //UFileSupport.ShowMessage('name: ' + contextName);
                        //UFileSupport.ShowMessage('description: ' + contextDescription);
                        TSRule newRule = TSDomain.domain.world.newRule();
                        newRulesCommand.addRule(newRule);
                        Application.RuleEditorForm.lastChoice = newRule;
                        newRule.position = position;
                        newRule.setContext(contextName.trim());
                        newRule.setCommand(this.DescribeEdit.getText().trim());
                        if (!contextDescription.trim().equals("")) {
                            newRule.setReply(contextDescription.trim());
                        } else {
                            newRule.setReply("There is nothing of interest here.");
                        }
                    }
                    //previousContext = contextName.trim();
                    position.y = position.y + 60;
                }
                while (character == '\n') {
                    character = stream.read();
                }
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
    
    public boolean initializeAndCheckIfOKToUse() {
        this.generated = false;
        this.notebook.setSelectedIndex(kStartPage);
        this.NewContextsMemo.setText("");
        this.FormActivate();
        return true;
    }

    public void FormActivate() {
        this.contextStartPageImage.imageIcon = Application.ConsoleForm.ContextButton.getIcon();
        this.commandStartPageImage.imageIcon = Application.ConsoleForm.CommandButton.getIcon();
        this.replyStartPageImage.imageIcon = Application.RuleEditorForm.ReplyButton.getIcon();
        this.commandImage.imageIcon = Application.ConsoleForm.CommandButton.getIcon();
    }
    
    public boolean FormCloseQuery() {
        if (this.generated) {
            return true;
        }
        if (!this.NewContextsMemo.getText().trim().equals("")) {
            boolean result = UFileSupport.Confirm("You are about to close this wizard" + "\n" + "and lose any work done with it." + "\n" + "\n" + "Is this OK?");
            if (!result) {
                return false;
            }
            Application.ChangeLogForm.addToLog(this.NewContextsMemo.getText());
        }
        return true;
    }
    

}  //  @jve:decl-index=0:visual-constraint="10,10"
