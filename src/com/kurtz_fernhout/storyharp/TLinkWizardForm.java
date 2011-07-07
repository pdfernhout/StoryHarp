package com.kurtz_fernhout.storyharp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.ChangeEvent;

import net.miginfocom.swing.MigLayout;

import java.awt.Rectangle;

public class TLinkWizardForm extends JDialog {
    private static final long serialVersionUID = 1L;
    
    // const
    public static final int kStartPage = 0;
    public static final int kContextsPage = 1;
    public static final int kForwardPage = 2;
    public static final int kBackwardPage = 3;
    public static final int kFinishPage = 4;
    
    JTabbedPane notebook;
    JPanel page1;
    JLabel Label10;
    ImagePanel Image1;
    JLabel Label4;
    JLabel Label17;
    JLabel Label26;
    ImagePanel commandStartPageImage;
    JLabel Label2;
    ImagePanel contextStartPageImage;
    JLabel Label5;
    ImagePanel replyStartPageImage;
    JLabel Label6;
    JLabel Label7;
    JPanel page2;
    JLabel Label11;
    ImagePanel Image3;
    ImagePanel Image2;
    JLabel Label9;
    ImagePanel firstContextImage;
    ImagePanel secondContextImage;
    DefaultListModel FirstContextBoxModel;
    JList FirstContextBox;
    DefaultListModel SecondContextBoxModel;
    JList SecondContextBox;
    JPanel page3;
    JLabel ForwardLabel;
    JLabel Label13;
    JLabel forwardReplyLabel;
    JLabel forwardReplyNote;
    ImagePanel Image4;
    ImagePanel forwardReplyArrow;
    JLabel Label8;
    ImagePanel forwardCommandImage;
    ImagePanel forwardReplyImage;
    JTextField ForwardEdit;
    JTextArea ForwardMemo;
    JPanel page4;
    JLabel BackwardLabel;
    JLabel Label12;
    JLabel backwardReplyLabel;
    ImagePanel Image6;
    ImagePanel backwardReplyArrow;
    JLabel Label1;
    JLabel backwardReplyNote;
    ImagePanel backwardCommandImage;
    ImagePanel backwardReplyImage;
    JTextField BackwardEdit;
    JTextArea BackwardMemo;
    JPanel page5;
    JLabel Label18;
    JLabel Label14;
    JLabel Label19;
    JLabel Label3;
    ImagePanel Image8;
    JLabel Label15;
    JLabel Label16;
    JLabel forwardSummary;
    JLabel backwardSummary;
    JButton helpButton;
    JButton goBack;
    JButton goNext;
    JButton cancel;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
	private JPanel navigationPanel;
	
	public boolean generated;
    
    public TLinkWizardForm() {
        super();
        this.setModal(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 436));
        this.setContentPane(getMainContentPane());
        this.setTitle("New Moves Wizard");
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
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 96;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.BorderStyle = bsDialog;
        
        
        this.notebook = new JTabbedPane();
        notebook.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent event) {
                notebookPageChanged(event);
            }
        });
        
        this.page1 = new JPanel(new MigLayout());
        
        this.Label10 = new JLabel("<html>This wizard will help you quickly link up two contexts by creating rules with commands to move between them.");
        
        Image Image1Image = toolkit.createImage("resources/LinkWizardForm_Image1.png");
        this.Image1 = new ImagePanel(new ImageIcon(Image1Image));
        
        this.Label4 = new JLabel("Welcome to the New Moves Wizard!");
        
        this.Label17 = new JLabel("Click the Next button to begin.");
        
        this.Label26 = new JLabel("You can click Cancel at any time to close the wizard without making any new rules.");
        
        this.commandStartPageImage = new ImagePanel(); // No image was set
        
        this.Label2 = new JLabel("A command is what you say to the computer.");
        
        this.contextStartPageImage = new ImagePanel(); // No image was set
        
        this.Label5 = new JLabel("<html>A context is the single most important requirement for the use of a command, usually a physical location.");
        
        this.replyStartPageImage = new ImagePanel(); // No image was set
        
        this.Label6 = new JLabel("A reply is what the computer says after you say a command.");
        
        this.Label7 = new JLabel("You can enter a reply for each command.");
        
        this.page1.add(Image1, "split");
        this.page1.add(Label4, "wrap");
        this.page1.add(Label10, "wrap");
        this.page1.add(commandStartPageImage, "split");
        this.page1.add(Label2, "wrap");
        this.page1.add(contextStartPageImage, "split");
        this.page1.add(Label5, "wrap");
        this.page1.add(Label7, "wrap");
        this.page1.add(replyStartPageImage, "split");
        this.page1.add(Label6, "wrap");
        this.page1.add(Label17, "wrap");
        this.page1.add(Label26, "wrap");
        
        this.notebook.addTab("Introduction", null, this.page1);
        
        this.page2 = new JPanel(new MigLayout());
        this.Label11 = new JLabel("Choose two contexts to move between. The order does not matter.");
        
        Image Image3Image = toolkit.createImage("resources/LinkWizardForm_Image3.png");
        this.Image3 = new ImagePanel(new ImageIcon(Image3Image));
        
        Image Image2Image = toolkit.createImage("resources/LinkWizardForm_Image2.png");
        this.Image2 = new ImagePanel(new ImageIcon(Image2Image));
        
        this.Label9 = new JLabel("<html>You can also choose these two contexts by selecting them in the Map (Shift-click to select the second context) before you open the wizard.");
        
        this.firstContextImage = new ImagePanel(); // No image was set
        
        this.secondContextImage = new ImagePanel(); // No image was set
        
        this.FirstContextBoxModel = new DefaultListModel();
        this.FirstContextBox = new JList(FirstContextBoxModel);
        this.FirstContextBox.setFixedCellHeight(14);
        
        this.SecondContextBoxModel = new DefaultListModel();
        this.SecondContextBox = new JList(SecondContextBoxModel);
        this.SecondContextBox.setFixedCellHeight(14);
        
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setViewportView(FirstContextBox);
        
        JScrollPane scroller2 = new JScrollPane();
        scroller2.setViewportView(SecondContextBox);
        
        this.page2.add(Image3, "split");
        this.page2.add(Label11, "wrap");
        this.page2.add(firstContextImage, "top, split");
        this.page2.add(scroller1, "push, grow");
        this.page2.add(Image2, "");
        this.page2.add(secondContextImage, "top, split");
        this.page2.add(scroller2, "push, grow, wrap");
        this.page2.add(Label9, "wrap");
        
        this.notebook.addTab("Contexts", null, this.page2);
        
        this.page3 = new JPanel(new MigLayout());
        this.ForwardLabel = new JLabel("ForwardLabel");
        
        this.Label13 = new JLabel(" What command should the user say to move from:");
        
        this.forwardReplyLabel = new JLabel(" What should the computer reply after the user says the move command?");
        
        this.forwardReplyNote = new JLabel("<html>Leave this blank to get a default reply of \"You\" plus the command phrase.<br>For example, for \"go east\" the default would be \"You go east\".");
        
        Image Image4Image = toolkit.createImage("resources/LinkWizardForm_Image4.png");
        this.Image4 = new ImagePanel(new ImageIcon(Image4Image));
        
        Image forwardReplyArrowImage = toolkit.createImage("resources/LinkWizardForm_forwardReplyArrow.png");
        this.forwardReplyArrow = new ImagePanel(new ImageIcon(forwardReplyArrowImage));
        
        this.Label8 = new JLabel("<html>Leave this blank if you do not want to move this way.<br>Examples are \"move forward\", \"go east\", \"leap up\", \"enter the building\", and \"activate the transporter\". ");
        
        this.forwardCommandImage = new ImagePanel(); // No image was set
        
        this.forwardReplyImage = new ImagePanel(); // No image was set
        
        this.ForwardEdit = new JTextField("");
        ForwardEdit.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent event) {
                ForwardEditChange(event);
            }
        });
        
        this.ForwardMemo = new JTextArea(10, 10);
        
        JScrollPane scroller3 = new JScrollPane();
        scroller3.setViewportView(ForwardMemo);
        
        this.page3.add(Image4, "split");
        this.page3.add(ForwardLabel, "wrap");
        this.page3.add(forwardCommandImage, "split");
        this.page3.add(ForwardEdit, "growx, pushx, wrap");
        this.page3.add(Label8, "wrap");
        this.page3.add(forwardReplyArrow, "split");
        this.page3.add(forwardReplyLabel, "wrap");
        this.page3.add(forwardReplyImage, "top, split");
        this.page3.add(scroller3, "push, grow, wrap");
        this.page3.add(forwardReplyNote, "wrap");
        
        this.notebook.addTab("Forward", null, this.page3);
        
        
        this.page4 = new JPanel(new MigLayout());
        this.BackwardLabel = new JLabel("BackwardLabel");
        this.BackwardLabel.putClientProperty("tag", 52);
        
        this.Label12 = new JLabel(" What command should the user say to move from:");
        
        this.backwardReplyLabel = new JLabel(" What should the computer reply after the user says the move command?");
        
        Image Image6Image = toolkit.createImage("resources/LinkWizardForm_Image6.png");
        this.Image6 = new ImagePanel(new ImageIcon(Image6Image));
        
        Image backwardReplyArrowImage = toolkit.createImage("resources/LinkWizardForm_backwardReplyArrow.png");
        this.backwardReplyArrow = new ImagePanel(new ImageIcon(backwardReplyArrowImage));
        
        this.Label1 = new JLabel("<html>Leave this blank if you do not want to move this way.<br>Examples are \"move forward\", \"go east\", \"leap up\", \"enter the building\", and \"activate the transporter\". ");
        
        this.backwardReplyNote = new JLabel("<html>Leave this blank to get a default reply of \"You\" plus the command phrase. For example, for \"go east\" the default would be \"You go east\".");
        
        this.backwardCommandImage = new ImagePanel(); // No image was set
        
        this.backwardReplyImage = new ImagePanel(); // No image was set
        
        this.BackwardEdit = new JTextField("");
        BackwardEdit.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent event) {
                BackwardEditChange(event);
            }
        });
        
        this.BackwardMemo = new JTextArea(10, 10);
        
        JScrollPane scroller4 = new JScrollPane();
        scroller4.setViewportView(BackwardMemo);
        
        this.page4.add(Image6, "split");
        this.page4.add(Label12, "wrap");
        this.page4.add(BackwardLabel, "wrap");
        this.page4.add(backwardCommandImage, "split");
        this.page4.add(BackwardEdit, "wrap, growx");
        this.page4.add(Label1, "wrap");
        this.page4.add(backwardReplyArrow, "split");
        this.page4.add(backwardReplyLabel, "wrap");
        this.page4.add(backwardReplyImage, "split");
        this.page4.add(scroller4, "wrap, push, grow");
        this.page4.add(backwardReplyNote, "wrap");

        this.notebook.addTab("Backward", null, this.page4);
        
        
        this.page5 = new JPanel(new MigLayout());
        this.Label18 = new JLabel("Congratulations!");
        
        this.Label14 = new JLabel("<html>You have completed the information the wizard needs to generate two new rules to link the two contexts you have chosen.");
        
        this.Label19 = new JLabel("<html>You can click Back to review your choices. Or, you can click Cancel to close the wizard without making any new rules.");
        
        this.Label3 = new JLabel("Click Finish to create the new rules and close the wizard.");
        
        Image Image8Image = toolkit.createImage("resources/LinkWizardForm_Image8.png");
        this.Image8 = new ImagePanel(new ImageIcon(Image8Image));
        
        this.Label15 = new JLabel("<html>The reply texts you entered here will also be saved in the log file (even if you cancel using the wizard).");
        
        this.Label16 = new JLabel("After you finish the wizard, you can choose Undo from the Edit menu to remove your new rules.");
        
        this.forwardSummary = new JLabel("nether regions -> go to elevator -> elevator");
        
        this.backwardSummary = new JLabel("elevator -> go to nether regions -> nether regions");
        
        this.page5.add(Image8, "split");
        this.page5.add(Label18, "wrap");
        this.page5.add(Label14, "wrap");
        this.page5.add(forwardSummary, "gapleft 30, wrap");
        this.page5.add(backwardSummary, "gapleft 30, wrap");
        this.page5.add(Label15, "wrap");
        this.page5.add(Label3, "wrap");
        this.page5.add(Label16, "wrap");
        this.page5.add(Label19, "wrap");
        
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
        
    public void cancelClick(ActionEvent event) {
        this.setVisible(false);
    }
        
    public void goBackClick(ActionEvent event) {
        if (this.notebook.getSelectedIndex() > kStartPage) {
            this.notebook.setSelectedIndex(this.notebook.getSelectedIndex() - 1);
        }
    }
        
    public void goNextClick(ActionEvent event) {
        String first = this.firstContext().trim();
        String second = this.secondContext().trim();
        if (this.notebook.getSelectedIndex() == kFinishPage) {
            this.generateRules();
            this.setVisible(false);
        } else if (this.notebook.getSelectedIndex() == kContextsPage) {
            if ((first.equals("")) || (second.equals(""))) {
                UFileSupport.ShowMessage("Both contexts must be entered to proceed.");
                return;
            }
            if (first == second) {
                UFileSupport.ShowMessage("The two contexts must have different names.");
                return;
            }
            this.ForwardLabel.setText(first + " --> " + second);
            this.BackwardLabel.setText(second + " --> " + first);
        }
        if (this.notebook.getSelectedIndex() == kForwardPage) {
            if ((this.ForwardEdit.getText().trim().equals("")) && (!this.ForwardMemo.getText().trim().equals(""))) {
                UFileSupport.ShowMessage("You must enter a command phrase if you enter a reply.");
                return;
            }
        }
        if (this.notebook.getSelectedIndex() == kBackwardPage) {
            if ((this.BackwardEdit.getText().trim().equals("")) && (!this.BackwardMemo.getText().trim().equals(""))) {
                UFileSupport.ShowMessage("You must enter a command phrase if you enter a reply.");
                return;
            }
            if ((this.BackwardEdit.getText().trim().equals("")) && (this.ForwardEdit.getText().trim().equals(""))) {
                UFileSupport.ShowMessage("You must enter a command on this page or the previous page to generate a link.");
                return;
            }
        }
        if (this.notebook.getSelectedIndex() < kFinishPage) {
            this.notebook.setSelectedIndex(this.notebook.getSelectedIndex() + 1);
        }
    }
        
    public void helpButtonClick(ActionEvent event) {
    	Application.HelpJump("Making_new_rules_using_the_new_moves_wizard");
    }
        
    public void notebookPageChanged(ChangeEvent event) {
    	if (this.goBack == null) return;
        this.goBack.setEnabled(this.notebook.getSelectedIndex() > kStartPage);
        if (this.notebook.getSelectedIndex() != kFinishPage) {
            this.goNext.setText("Next >>");
        } else {
            this.goNext.setText("Finish");
            if (this.ForwardEdit.getText().trim().equals("")) {
                this.forwardSummary.setText("");
            } else {
                this.forwardSummary.setText(this.firstContext() + "  ---  " + this.ForwardEdit.getText().trim() + "  -->  " + this.secondContext());
            }
            if (this.BackwardEdit.getText().trim().equals("")) {
                this.backwardSummary.setText("");
            } else {
                this.backwardSummary.setText(this.secondContext() + "  ---  " + this.BackwardEdit.getText().trim() + "  -->  " + this.firstContext());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	TLinkWizardForm thisClass = new TLinkWizardForm();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    public boolean initializeAndCheckIfOKToUse() {
        this.generated = false;
        this.notebook.setSelectedIndex(kStartPage);
        Application.addWorldContextsToListBox(this.FirstContextBoxModel);
        if (this.FirstContextBoxModel.size() < 2) {
            UFileSupport.ShowMessage("You must create at least two contexts before using the link wizard.");
            return false;
        }
        Application.addWorldContextsToListBox(this.SecondContextBoxModel);
        int contextCount = 0;
        this.ForwardEditChange(null);
        this.BackwardEditChange(null);
        this.ForwardEdit.setText("");
        this.ForwardMemo.setText("");
        this.BackwardEdit.setText("");
        this.BackwardMemo.setText("");
        for (int i = 0; i < TSDomain.domain.world.variables.size(); i++) {
            // must be done last because of exit
            TSVariable variable = TSDomain.domain.world.variables.get(i);
            if (variable.selected) {
                if (contextCount == 0) {
                    for (int j = 0; j < this.FirstContextBoxModel.size(); j++) {
                        if (((TSVariable)this.FirstContextBoxModel.get(j)).phrase.equals(variable.phrase)) {
                            this.FirstContextBox.setSelectedIndex(j);
                            contextCount += 1;
                            break;
                        }
                    }
                } else {
                    for (int j = 0; j < this.SecondContextBoxModel.size(); j++) {
                        if (((TSVariable)this.SecondContextBoxModel.get(j)).phrase.equals(variable.phrase)) {
                            this.SecondContextBox.setSelectedIndex(j);
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    public void ForwardEditChange(CaretEvent event) {
        boolean haveText = !this.ForwardEdit.getText().equals("");
        this.forwardReplyArrow.setVisible(haveText);
        this.forwardReplyLabel.setEnabled(haveText);
        this.forwardReplyImage.setVisible(haveText);
        this.ForwardMemo.setVisible(haveText);
        this.forwardReplyNote.setEnabled(haveText);
    }
    
    public void BackwardEditChange(CaretEvent event) {
        boolean haveText = !this.BackwardEdit.getText().equals("");
        this.backwardReplyArrow.setVisible(haveText);
        this.backwardReplyLabel.setEnabled(haveText);
        this.backwardReplyImage.setVisible(haveText);
        this.BackwardMemo.setVisible(haveText);
        this.backwardReplyNote.setEnabled(haveText);
    }
	
    public boolean FormCloseQuery() {
        if (this.generated) {
            return true;
        }
        if ((!this.ForwardEdit.getText().trim().equals("")) || (!this.ForwardMemo.getText().trim().equals("")) || (!this.BackwardEdit.getText().trim().equals("")) || (!this.BackwardMemo.getText().trim().equals(""))) {
            boolean result = UFileSupport.Confirm("You are about to close this wizard" + "\n" + "and lose any work done with it." + "\n" + "\n" + "Is this OK?");
            if (!result) {
                return false;
            }
            Application.ChangeLogForm.addToLog(this.ForwardMemo.getText());
            Application.ChangeLogForm.addToLog(this.BackwardMemo.getText());
        }
        return true;
    }
    
    public String firstContext() {
        String result = "";
        if (this.FirstContextBox.getSelectedIndex() >= 0) {
            result = ((TSVariable)this.FirstContextBoxModel.get(this.FirstContextBox.getSelectedIndex())).phrase;
        }
        return result;
    }
    
    public String secondContext() {
        String result = "";
        if (this.SecondContextBox.getSelectedIndex() >= 0) {
            result = ((TSVariable)this.SecondContextBoxModel.get(this.SecondContextBox.getSelectedIndex())).phrase;
        }
        return result;
    }
    
    public void generateRules() {
        TSNewRulesCommand newRulesCommand = new TSNewRulesCommand();
        TSRule newRule = new TSRule();
        
        Application.ChangeLogForm.addToLog(this.ForwardMemo.getText());
        Application.ChangeLogForm.addToLog(this.BackwardMemo.getText());
        newRulesCommand = new TSNewRulesCommand();
        newRulesCommand.creator = "link wizard";
        newRule = this.makeLink(this.firstContext().trim(), this.secondContext().trim(), this.ForwardEdit.getText().trim(), this.ForwardMemo.getText().trim());
        if (newRule != null) {
            newRulesCommand.addRule(newRule);
        }
        newRule = this.makeLink(this.secondContext().trim(), this.firstContext().trim(), this.BackwardEdit.getText().trim(), this.BackwardMemo.getText().trim());
        if (newRule != null) {
            newRulesCommand.addRule(newRule);
        }
        if (newRulesCommand.rules.size() > 0) {
            TSDomain.domain.worldCommandList.doCommand(newRulesCommand);
        }
        Application.RuleEditorForm.updateForRuleChange();
        Application.RuleEditorForm.adjustScrollBars();
        this.generated = true;
    }
    
    public TSRule makeLink(String firstContext, String secondContext, String command, String reply) {
        int dx = 0;
        int dy = 0;
        
        TSRule result = null;
        if (firstContext.equals("")) {
            return result;
        }
        if (command.equals("")) {
            return result;
        }
        result = TSDomain.domain.world.newRule();
        Application.RuleEditorForm.lastChoice = result;
        result.setContext(firstContext);
        result.setCommand(command);
        if (!reply.trim().equals("")) {
            result.setReply(reply);
        } else {
            result.setReply("You " + command + ".");
        }
        result.setMove(secondContext);
        result.position.x = (result.context.position.x + result.move.position.x) / 2;
        result.position.y = (result.context.position.y + result.move.position.y) / 2;
        dx = result.context.position.x - result.move.position.x;
        dy = result.context.position.y - result.move.position.y;
        if (Math.abs(dy) >= Math.abs(dx)) {
            if (dy >= 0) {
                //now determine offset
                result.position.x = result.position.x - 100;
            } else {
                result.position.x = result.position.x + 100;
            }
        } else {
            if (dx < 0) {
                result.position.y = result.position.y - 30;
            } else {
                result.position.y = result.position.y + 30;
            }
        }
        return result;
    }
    
    public void FormActivate() {
        this.contextStartPageImage.imageIcon = Application.ConsoleForm.ContextButton.getIcon();
        this.commandStartPageImage.imageIcon = Application.ConsoleForm.CommandButton.getIcon();
        this.replyStartPageImage.imageIcon = Application.RuleEditorForm.ReplyButton.getIcon();
        this.firstContextImage.imageIcon = Application.ConsoleForm.ContextButton.getIcon();
        this.secondContextImage.imageIcon = Application.ConsoleForm.ContextButton.getIcon();
        this.forwardCommandImage.imageIcon = Application.ConsoleForm.CommandButton.getIcon();
        this.forwardReplyImage.imageIcon = Application.RuleEditorForm.ReplyButton.getIcon();
        this.backwardCommandImage.imageIcon = Application.ConsoleForm.CommandButton.getIcon();
        this.backwardReplyImage.imageIcon = Application.RuleEditorForm.ReplyButton.getIcon();
    }
    

}  //  @jve:decl-index=0:visual-constraint="10,10"
