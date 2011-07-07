
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// import common.*;

public class CommandWizardWindow extends JFrame {
    private static final long serialVersionUID = 1L;
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
    JPanel sequenceEndPanel;
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
    
    public CommandWizardWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 325));
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
        delphiPanel.setLayout(null);
        // -- delphiPanel.setLayout(BoxLayout(contentPane, BoxLayout.Y_AXIS));
        // Font buttonFont = new Font("Arial Narrow", Font.PLAIN, 9);
        // Insets buttonMargin = new Insets(1, 1, 1, 1);
        this.setBounds(370, 308, 320, 240  + 80); // extra for title bar and menu
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnCloseQuery = FormCloseQuery;
        //  --------------- UNHANDLED ATTRIBUTE: this.ActiveControl = ContextBox;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.BorderStyle = bsDialog;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 100;
        
        
        this.notebook = new JTabbedPane();
        this.notebook.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent event) {
                notebookPageChanged(event);
            }
        });
        
        this.page1 = new JPanel(null);
        this.Label1 = new JLabel("This wizard will help you quickly create a set of new rules based on one context and a list of commands you enter.");
        this.Label1.setBounds(24, 52, 411, 37);
        
        this.Label2 = new JLabel("You can also link your new commands in a sequence so that only one command is available at any time. This is done by generating requirements and changes.");
        this.Label2.setBounds(24, 183, 402, 57);
        
        Image Image1Image = toolkit.createImage("../resources/CommandWizardForm_Image1.png");
        this.Image1 = new ImagePanel(new ImageIcon(Image1Image));
        this.Image1.setBounds(24, 12, 32, 32);
        
        this.Label7 = new JLabel("Welcome to the New Commands Wizard!");
        this.Label7.setBounds(64, 20, 246, 12);
        
        this.Label17 = new JLabel("Click the Next button to begin.");
        this.Label17.setBounds(24, 297, 145, 21);
        
        this.Label26 = new JLabel("You can click Cancel at any time to close the wizard without making any new rules.");
        this.Label26.setBounds(24, 315, 600, 17);
        
        this.Label3 = new JLabel("You can add extra requirements or changes later using the editor.");
        this.Label3.setBounds(24, 266, 474, 17);
        
        this.commandStartPageImage = new ImagePanel(); // No image was set
        this.commandStartPageImage.setBounds(48, 81, 16, 16);
        
        this.Label5 = new JLabel("A command is what you say to the computer.");
        this.Label5.setBounds(69, 82, 330, 17);
        
        this.contextStartPageImage = new ImagePanel(); // No image was set
        this.contextStartPageImage.setBounds(48, 105, 16, 16);
        
        this.Label6 = new JLabel("A context is the single most important requirement for the use of a command, usually a physical location.");
        this.Label6.setBounds(69, 105, 284, 57);
        
        this.replyStartPageImage = new ImagePanel(); // No image was set
        this.replyStartPageImage.setBounds(48, 156, 16, 16);
        
        this.Label10 = new JLabel("A reply is what the computer says after you say a command.");
        this.Label10.setBounds(69, 157, 440, 17);
        
        this.Label19 = new JLabel("You can enter a reply for each command.");
        this.Label19.setBounds(24, 138, 301, 17);
        
        this.requirementsStartPageImage = new ImagePanel(); // No image was set
        this.requirementsStartPageImage.setBounds(48, 215, 16, 16);
        
        this.changesStartPageImage = new ImagePanel(); // No image was set
        this.changesStartPageImage.setBounds(48, 245, 16, 16);
        
        this.Label24 = new JLabel("A requirement is variable state necessary for a command to be available. Variables can be true or false.");
        this.Label24.setBounds(72, 216, 298, 57);
        
        this.Label25 = new JLabel("A change is a new variable state resulting from a command.");
        this.Label25.setBounds(72, 246, 434, 17);
        
        this.page1.add(Label1);
        this.page1.add(Label2);
        this.page1.add(Image1);
        this.page1.add(Label7);
        this.page1.add(Label17);
        this.page1.add(Label26);
        this.page1.add(Label3);
        this.page1.add(commandStartPageImage);
        this.page1.add(Label5);
        this.page1.add(contextStartPageImage);
        this.page1.add(Label6);
        this.page1.add(replyStartPageImage);
        this.page1.add(Label10);
        this.page1.add(Label19);
        this.page1.add(requirementsStartPageImage);
        this.page1.add(changesStartPageImage);
        this.page1.add(Label24);
        this.page1.add(Label25);
        this.notebook.addTab("Introduction", null, this.page1);
        
        this.page1.setBounds(0, 0, 316, 307);
        
        this.page2 = new JPanel(null);
        Image Image3Image = toolkit.createImage("../resources/CommandWizardForm_Image3.png");
        this.Image3 = new ImagePanel(new ImageIcon(Image3Image));
        this.Image3.setBounds(12, 12, 8, 16);
        
        this.Label8 = new JLabel("Choose the context you want your new commands to use.");
        this.Label8.setBounds(24, 12, 424, 17);
        
        this.Label11 = new JLabel("You can also choose a context by selecting it in the Map view before you open the wizard.");
        this.Label11.setBounds(56, 301, 327, 37);
        
        this.contextListImage = new ImagePanel(); // No image was set
        this.contextListImage.setBounds(36, 36, 16, 16);
        
        this.ContextBox = new JList(new DefaultListModel());
        this.ContextBox.setFixedCellHeight(14);
        this.ContextBox.setBounds(56, 35, 421, 262);
        
        this.page2.add(Image3);
        this.page2.add(Label8);
        this.page2.add(Label11);
        this.page2.add(contextListImage);
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setBounds(56, 35, 421, 262);;
        scroller1.setViewportView(ContextBox);
        this.page2.add(scroller1);
        this.notebook.addTab("Context", null, this.page2);
        
        this.page2.setBounds(0, 0, 316, 307);
        
        this.page3 = new JPanel(null);
        Image Image2Image = toolkit.createImage("../resources/CommandWizardForm_Image2.png");
        this.Image2 = new ImagePanel(new ImageIcon(Image2Image));
        this.Image2.setBounds(12, 28, 8, 16);
        
        this.Label9 = new JLabel("Command | Reply");
        this.Label9.setBounds(8, 88, 90, 12);
        
        this.Label12 = new JLabel(" When you are finished entering commands, click Next.");
        this.Label12.setBounds(24, 320, 397, 17);
        
        this.Label4 = new JLabel("Enter or paste the commands you want to create in the area below, separating each command from its reply by a pipe bar. For example, \"open the door | You open the rusty door.\"");
        this.Label4.setBounds(24, 29, 445, 57);
        
        this.Label14 = new JLabel("Descriptions are optional. It''s okay to wrap entries on more than one line. Use carriage returns to separate entries.");
        this.Label14.setBounds(24, 57, 312, 57);
        
        this.newCommandsForContextLabel = new JLabel("New commands for: context");
        this.newCommandsForContextLabel.setBounds(12, 8, 137, 12);
        
        this.NewCommandsMemo = new JTextArea(10, 10);
        this.NewCommandsMemo.setBounds(8, 108, 477, 209);
        
        this.page3.add(Image2);
        this.page3.add(Label9);
        this.page3.add(Label12);
        this.page3.add(Label4);
        this.page3.add(Label14);
        this.page3.add(newCommandsForContextLabel);
        JScrollPane scroller2 = new JScrollPane();
        scroller2.setBounds(8, 108, 477, 209);;
        scroller2.setViewportView(NewCommandsMemo);
        this.page3.add(scroller2);
        this.notebook.addTab("Commands", null, this.page3);
        
        this.page3.setBounds(0, 0, 316, 307);
        
        this.page4 = new JPanel(null);
        this.PrefixLabel = new JLabel(" What prefix do you want to use for the requirements that create the sequence?");
        this.PrefixLabel.setBounds(24, 120, 578, 17);
        
        Image Image4Image = toolkit.createImage("../resources/CommandWizardForm_Image4.png");
        this.Image4 = new ImagePanel(new ImageIcon(Image4Image));
        this.Image4.setBounds(12, 12, 8, 16);
        
        this.Label13 = new JLabel("Do you want to link your new commands in sequence, so that each becomes available only after the previous one is said?");
        this.Label13.setBounds(24, 12, 420, 57);
        
        Image prefixArrowImage = toolkit.createImage("../resources/CommandWizardForm_prefixArrow.png");
        this.prefixArrow = new ImagePanel(new ImageIcon(prefixArrowImage));
        this.prefixArrow.setBounds(12, 119, 8, 16);
        
        this.prefixNote = new JLabel("Examples are: \"talking to sailor\", \"in boarding house\". By default the prefix is the same as the context.");
        this.prefixNote.setBounds(56, 167, 251, 57);
        
        this.Label15 = new JLabel("Sequences are useful when the user has to do several things in a certain order, such as steps in an assembly process or parts of a conversation.");
        this.Label15.setBounds(56, 67, 352, 57);
        
        this.Label27 = new JLabel("Creating sequences is an advanced topic; see the help system for details.");
        this.Label27.setBounds(56, 94, 532, 17);
        
        this.GenerateSequence = new JCheckBox("Yes, link the commands in a sequence.");
        this.GenerateSequence.setSelected(true);
        this.GenerateSequence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                GenerateSequenceClick(event);
            }
        });
        this.GenerateSequence.setBounds(56, 46, 307, 28);
        
        this.PrefixEdit = new JTextField("");
        this.PrefixEdit.setBounds(56, 141, 333, 22);
        
        this.endSequenceLabel = new JLabel(" When the last command has been said,");
        this.endSequenceLabel.setBounds(24, 10, 293, 17);
        
        Image sequenceEndArrowImage = toolkit.createImage("../resources/CommandWizardForm_sequenceEndArrow.png");
        this.sequenceEndArrow = new ImagePanel(new ImageIcon(sequenceEndArrowImage));
        this.sequenceEndArrow.setBounds(12, 11, 8, 16);
        
        this.endSequenceLoopToFirst = new JRadioButton("loop to the first command in the sequence");
        this.endSequenceLoopToFirst.setSelected(true);
        // ----- manually fix radio button group by putting multiple buttons in the same group
        ButtonGroup group = new ButtonGroup();
        group.add(this.endSequenceLoopToFirst);
        this.endSequenceLoopToFirst.setBounds(56, 30, 329, 28);
        
        this.endSequenceRemoveTheLastCommand = new JRadioButton("remove the last command");
        // ----- manually fix radio button group by putting multiple buttons in the same group
        group.add(this.endSequenceRemoveTheLastCommand);
        this.endSequenceRemoveTheLastCommand.setBounds(56, 70, 213, 28);
        
        this.endSequenceLeaveLastCommand = new JRadioButton("leave the last command available");
        // ----- manually fix radio button group by putting multiple buttons in the same group
        group.add(this.endSequenceLeaveLastCommand);
        this.endSequenceLeaveLastCommand.setBounds(56, 52, 264, 28);
        
        this.sequenceEndPanel = new JPanel(null);
        // -- this.sequenceEndPanel.setLayout(new BoxLayout(this.sequenceEndPanel, BoxLayout.Y_AXIS));
        this.sequenceEndPanel.add(endSequenceLabel);
        this.sequenceEndPanel.add(sequenceEndArrow);
        this.sequenceEndPanel.add(endSequenceLoopToFirst);
        this.sequenceEndPanel.add(endSequenceRemoveTheLastCommand);
        this.sequenceEndPanel.add(endSequenceLeaveLastCommand);
        this.sequenceEndPanel.setBounds(0, 199, 490, 97);
        
        this.page4.add(PrefixLabel);
        this.page4.add(Image4);
        this.page4.add(Label13);
        this.page4.add(prefixArrow);
        this.page4.add(prefixNote);
        this.page4.add(Label15);
        this.page4.add(Label27);
        this.page4.add(GenerateSequence);
        this.page4.add(PrefixEdit);
        this.page4.add(sequenceEndPanel);
        this.notebook.addTab("Sequence", null, this.page4);
        
        this.page4.setBounds(0, 0, 316, 307);
        
        this.page5 = new JPanel(null);
        this.Label18 = new JLabel("Congratulations!");
        this.Label18.setBounds(66, 24, 113, 12);
        
        this.Label16 = new JLabel("You have completed the information the wizard needs to generate a new set of rules based on the commands you have entered.");
        this.Label16.setBounds(24, 58, 367, 57);
        
        this.Label20 = new JLabel("Click Back to review your choices. Click Cancel to close the wizard without making any new rules.");
        this.Label20.setBounds(24, 295, 358, 37);
        
        Image Image8Image = toolkit.createImage("../resources/CommandWizardForm_Image8.png");
        this.Image8 = new ImagePanel(new ImageIcon(Image8Image));
        this.Image8.setBounds(24, 16, 32, 32);
        
        this.Label21 = new JLabel("The command and reply texts you entered here will also be saved in the log file (even if you cancel using the wizard).");
        this.Label21.setBounds(24, 96, 381, 57);
        
        this.Label22 = new JLabel("Click Finish to create the new rules and close the wizard.");
        this.Label22.setBounds(24, 239, 301, 21);
        
        this.Label23 = new JLabel("After you finish the wizard, you can choose Undo from the Edit menu to remove your new rules.");
        this.Label23.setBounds(24, 263, 334, 57);
        
        this.page5.add(Label18);
        this.page5.add(Label16);
        this.page5.add(Label20);
        this.page5.add(Image8);
        this.page5.add(Label21);
        this.page5.add(Label22);
        this.page5.add(Label23);
        this.notebook.addTab("Conclusion", null, this.page5);
        
        this.page5.setBounds(0, 0, 316, 307);
        
        this.notebook.add(page1);
        this.notebook.add(page2);
        this.notebook.add(page3);
        this.notebook.add(page4);
        this.notebook.add(page5);
        this.notebook.setBounds(0, 0, 320, 341);
        //  --------------- UNHANDLED ATTRIBUTE: this.notebook.PageIndex = 0;
        
        this.helpButton = new JButton("Help");
        this.helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                helpButtonClick(event);
            }
        });
        this.helpButton.setMnemonic(KeyEvent.VK_H);
        this.helpButton.setBounds(6, 346, 70, 21);
        
        this.goBack = new JButton("<< Back");
        this.goBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                goBackClick(event);
            }
        });
        this.goBack.setMnemonic(KeyEvent.VK_B);
        this.goBack.setBounds(229, 346, 70, 21);
        
        this.goNext = new JButton("Next >>");
        this.goNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                goNextClick(event);
            }
        });
        this.goNext.setMnemonic(KeyEvent.VK_N);
        this.goNext.setBounds(305, 346, 70, 21);
        
        this.cancel = new JButton("Cancel");
        this.cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                cancelClick(event);
            }
        });
        this.cancel.setMnemonic(KeyEvent.VK_C);
        this.cancel.setBounds(416, 346, 70, 21);
        
        delphiPanel.add(notebook);
        delphiPanel.add(helpButton);
        delphiPanel.add(goBack);
        delphiPanel.add(goNext);
        delphiPanel.add(cancel);
        return delphiPanel;
    }
    
        
    public void GenerateSequenceClick(ActionEvent event) {
        System.out.println("GenerateSequenceClick");
    }
        
    public void cancelClick(ActionEvent event) {
        System.out.println("cancelClick");
    }
        
    public void goBackClick(ActionEvent event) {
        System.out.println("goBackClick");
    }
        
    public void goNextClick(ActionEvent event) {
        System.out.println("goNextClick");
    }
        
    public void helpButtonClick(ActionEvent event) {
        System.out.println("helpButtonClick");
    }
        
    public void notebookPageChanged(ChangeEvent event) {
        System.out.println("notebookPageChanged");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CommandWizardWindow thisClass = new CommandWizardWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

}
