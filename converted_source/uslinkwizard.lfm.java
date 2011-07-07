
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// import common.*;

public class LinkWizardWindow extends JFrame {
    private static final long serialVersionUID = 1L;
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
    JList FirstContextBox;
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
    
    public LinkWizardWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 325));
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
        delphiPanel.setLayout(null);
        // -- delphiPanel.setLayout(BoxLayout(contentPane, BoxLayout.Y_AXIS));
        // Font buttonFont = new Font("Arial Narrow", Font.PLAIN, 9);
        // Insets buttonMargin = new Insets(1, 1, 1, 1);
        this.setBounds(341, 317, 492, 299  + 80); // extra for title bar and menu
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnCloseQuery = FormCloseQuery;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 96;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.BorderStyle = bsDialog;
        
        
        this.notebook = new JTabbedPane();
        this.notebook.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent event) {
                notebookPageChanged(event);
            }
        });
        
        this.page1 = new JPanel(null);
        this.Label10 = new JLabel("This wizard will help you quickly link up two contexts by creating rules with commands to move between them.");
        this.Label10.setBounds(24, 58, 367, 28);
        
        Image Image1Image = toolkit.createImage("../resources/LinkWizardForm_Image1.png");
        this.Image1 = new ImagePanel(new ImageIcon(Image1Image));
        this.Image1.setBounds(24, 12, 32, 32);
        
        this.Label4 = new JLabel("Welcome to the New Moves Wizard!");
        this.Label4.setBounds(64, 20, 229, 16);
        
        this.Label17 = new JLabel("Click the Next button to begin.");
        this.Label17.setBounds(24, 232, 165, 14);
        
        this.Label26 = new JLabel("You can click Cancel at any time to close the wizard without making any new rules.");
        this.Label26.setBounds(24, 250, 403, 14);
        
        this.commandStartPageImage = new ImagePanel(); // No image was set
        this.commandStartPageImage.setBounds(48, 94, 16, 16);
        
        this.Label2 = new JLabel("A command is what you say to the computer.");
        this.Label2.setBounds(69, 95, 219, 14);
        
        this.contextStartPageImage = new ImagePanel(); // No image was set
        this.contextStartPageImage.setBounds(48, 125, 16, 16);
        
        this.Label5 = new JLabel("A context is the single most important requirement for the use of a command, usually a physical location.");
        this.Label5.setBounds(69, 125, 321, 28);
        
        this.replyStartPageImage = new ImagePanel(); // No image was set
        this.replyStartPageImage.setBounds(48, 189, 16, 16);
        
        this.Label6 = new JLabel("A reply is what the computer says after you say a command.");
        this.Label6.setBounds(69, 190, 296, 14);
        
        this.Label7 = new JLabel("You can enter a reply for each command.");
        this.Label7.setBounds(24, 168, 201, 14);
        
        this.page1.add(Label10);
        this.page1.add(Image1);
        this.page1.add(Label4);
        this.page1.add(Label17);
        this.page1.add(Label26);
        this.page1.add(commandStartPageImage);
        this.page1.add(Label2);
        this.page1.add(contextStartPageImage);
        this.page1.add(Label5);
        this.page1.add(replyStartPageImage);
        this.page1.add(Label6);
        this.page1.add(Label7);
        this.notebook.addTab("Introduction", null, this.page1);
        
        
        this.page2 = new JPanel(null);
        this.Label11 = new JLabel("Choose two contexts to move between. The order doesn''t matter.");
        this.Label11.setBounds(24, 12, 318, 14);
        
        Image Image3Image = toolkit.createImage("../resources/LinkWizardForm_Image3.png");
        this.Image3 = new ImagePanel(new ImageIcon(Image3Image));
        this.Image3.setBounds(12, 12, 8, 16);
        
        Image Image2Image = toolkit.createImage("../resources/LinkWizardForm_Image2.png");
        this.Image2 = new ImagePanel(new ImageIcon(Image2Image));
        this.Image2.setBounds(255, 111, 32, 16);
        
        this.Label9 = new JLabel("You can also choose these two contexts by selecting them in the Map (Shift-click to select the second context) before you open the wizard.");
        this.Label9.setBounds(56, 237, 342, 28);
        
        this.firstContextImage = new ImagePanel(); // No image was set
        this.firstContextImage.setBounds(36, 36, 16, 16);
        
        this.secondContextImage = new ImagePanel(); // No image was set
        this.secondContextImage.setBounds(280, 40, 16, 16);
        
        this.FirstContextBox = new JList(new DefaultListModel());
        this.FirstContextBox.setFixedCellHeight(14);
        this.FirstContextBox.setBounds(56, 36, 184, 193);
        
        this.SecondContextBox = new JList(new DefaultListModel());
        this.SecondContextBox.setFixedCellHeight(14);
        this.SecondContextBox.setBounds(300, 40, 184, 193);
        
        this.page2.add(Label11);
        this.page2.add(Image3);
        this.page2.add(Image2);
        this.page2.add(Label9);
        this.page2.add(firstContextImage);
        this.page2.add(secondContextImage);
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setBounds(56, 36, 184, 193);;
        scroller1.setViewportView(FirstContextBox);
        this.page2.add(scroller1);
        JScrollPane scroller2 = new JScrollPane();
        scroller2.setBounds(300, 40, 184, 193);;
        scroller2.setViewportView(SecondContextBox);
        this.page2.add(scroller2);
        this.notebook.addTab("Contexts", null, this.page2);
        
        
        this.page3 = new JPanel(null);
        this.ForwardLabel = new JLabel("ForwardLabel");
        this.ForwardLabel.setBounds(56, 33, 68, 14);
        
        this.Label13 = new JLabel(" What command should the user say to move from:");
        this.Label13.setBounds(24, 12, 245, 14);
        
        this.forwardReplyLabel = new JLabel(" What should the computer reply after the user says the move command?");
        this.forwardReplyLabel.setBounds(24, 144, 354, 14);
        
        this.forwardReplyNote = new JLabel("Leave this blank to get a default reply of \"You\" plus the command phrase. For example, for \"go east\" the default would be \"You go east\".");
        this.forwardReplyNote.setBounds(56, 229, 357, 28);
        
        Image Image4Image = toolkit.createImage("../resources/LinkWizardForm_Image4.png");
        this.Image4 = new ImagePanel(new ImageIcon(Image4Image));
        this.Image4.setBounds(12, 12, 8, 16);
        
        Image forwardReplyArrowImage = toolkit.createImage("../resources/LinkWizardForm_forwardReplyArrow.png");
        this.forwardReplyArrow = new ImagePanel(new ImageIcon(forwardReplyArrowImage));
        this.forwardReplyArrow.setBounds(12, 144, 8, 16);
        
        this.Label8 = new JLabel("Leave this blank if you don''t want to move this way. Examples are \"move forward\", \"go east\", \"leap up\", \"enter the building\", and \"activate the transporter\". ");
        this.Label8.setBounds(56, 81, 297, 42);
        
        this.forwardCommandImage = new ImagePanel(); // No image was set
        this.forwardCommandImage.setBounds(56, 56, 16, 16);
        
        this.forwardReplyImage = new ImagePanel(); // No image was set
        this.forwardReplyImage.setBounds(56, 165, 16, 16);
        
        this.ForwardEdit = new JTextField("");
        this.ForwardEdit.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent event) {
                ForwardEditChange(event);
            }
        });
        this.ForwardEdit.setBounds(76, 53, 402, 22);
        
        this.ForwardMemo = new JTextArea(10, 10);
        this.ForwardMemo.setBounds(76, 165, 402, 60);
        
        this.page3.add(ForwardLabel);
        this.page3.add(Label13);
        this.page3.add(forwardReplyLabel);
        this.page3.add(forwardReplyNote);
        this.page3.add(Image4);
        this.page3.add(forwardReplyArrow);
        this.page3.add(Label8);
        this.page3.add(forwardCommandImage);
        this.page3.add(forwardReplyImage);
        this.page3.add(ForwardEdit);
        JScrollPane scroller3 = new JScrollPane();
        scroller3.setBounds(76, 165, 402, 60);;
        scroller3.setViewportView(ForwardMemo);
        this.page3.add(scroller3);
        this.notebook.addTab("Forward", null, this.page3);
        
        
        this.page4 = new JPanel(null);
        this.BackwardLabel = new JLabel("BackwardLabel");
        this.BackwardLabel.setBounds(56, 33, 76, 14);
        this.BackwardLabel.putClientProperty("tag", 52);
        
        this.Label12 = new JLabel(" What command should the user say to move from:");
        this.Label12.setBounds(24, 12, 245, 14);
        
        this.backwardReplyLabel = new JLabel(" What should the computer reply after the user says the move command?");
        this.backwardReplyLabel.setBounds(24, 144, 354, 14);
        
        Image Image6Image = toolkit.createImage("../resources/LinkWizardForm_Image6.png");
        this.Image6 = new ImagePanel(new ImageIcon(Image6Image));
        this.Image6.setBounds(12, 12, 8, 16);
        
        Image backwardReplyArrowImage = toolkit.createImage("../resources/LinkWizardForm_backwardReplyArrow.png");
        this.backwardReplyArrow = new ImagePanel(new ImageIcon(backwardReplyArrowImage));
        this.backwardReplyArrow.setBounds(12, 144, 8, 16);
        
        this.Label1 = new JLabel("Leave this blank if you don''t want to move this way. Examples are \"move forward\", \"go east\", \"leap up\", \"enter the building\", and \"activate the transporter\". ");
        this.Label1.setBounds(56, 81, 297, 42);
        
        this.backwardReplyNote = new JLabel("Leave this blank to get a default reply of \"You\" plus the command phrase. For example, for \"go east\" the default would be \"You go east\".");
        this.backwardReplyNote.setBounds(56, 229, 357, 28);
        
        this.backwardCommandImage = new ImagePanel(); // No image was set
        this.backwardCommandImage.setBounds(56, 56, 16, 16);
        
        this.backwardReplyImage = new ImagePanel(); // No image was set
        this.backwardReplyImage.setBounds(56, 165, 16, 16);
        
        this.BackwardEdit = new JTextField("");
        this.BackwardEdit.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent event) {
                BackwardEditChange(event);
            }
        });
        this.BackwardEdit.setBounds(76, 53, 402, 22);
        
        this.BackwardMemo = new JTextArea(10, 10);
        this.BackwardMemo.setBounds(76, 165, 402, 60);
        
        this.page4.add(BackwardLabel);
        this.page4.add(Label12);
        this.page4.add(backwardReplyLabel);
        this.page4.add(Image6);
        this.page4.add(backwardReplyArrow);
        this.page4.add(Label1);
        this.page4.add(backwardReplyNote);
        this.page4.add(backwardCommandImage);
        this.page4.add(backwardReplyImage);
        this.page4.add(BackwardEdit);
        JScrollPane scroller4 = new JScrollPane();
        scroller4.setBounds(76, 165, 402, 60);;
        scroller4.setViewportView(BackwardMemo);
        this.page4.add(scroller4);
        this.notebook.addTab("Backward", null, this.page4);
        
        
        this.page5 = new JPanel(null);
        this.Label18 = new JLabel("Congratulations!");
        this.Label18.setBounds(66, 24, 104, 16);
        
        this.Label14 = new JLabel("You have completed the information the wizard needs to generate two new rules to link the two contexts you have chosen.");
        this.Label14.setBounds(24, 58, 369, 28);
        
        this.Label19 = new JLabel("Click Back to review your choices. Click Cancel to close the wizard without making any new rules.");
        this.Label19.setBounds(24, 235, 366, 28);
        
        this.Label3 = new JLabel("Click Finish to create the new rules and close the wizard.");
        this.Label3.setBounds(24, 187, 315, 14);
        
        Image Image8Image = toolkit.createImage("../resources/LinkWizardForm_Image8.png");
        this.Image8 = new ImagePanel(new ImageIcon(Image8Image));
        this.Image8.setBounds(24, 12, 32, 32);
        
        this.Label15 = new JLabel("The reply texts you entered here will also be saved in the log file (even if you cancel using the wizard).");
        this.Label15.setBounds(24, 136, 311, 28);
        
        this.Label16 = new JLabel("After you finish the wizard, you can choose Undo from the Edit menu to remove your new rules.");
        this.Label16.setBounds(24, 204, 337, 28);
        
        this.forwardSummary = new JLabel("nether regions -> go to elevator -> elevator");
        this.forwardSummary.setBounds(52, 94, 207, 14);
        
        this.backwardSummary = new JLabel("elevator -> go to nether regions -> nether regions");
        this.backwardSummary.setBounds(52, 110, 238, 14);
        
        this.page5.add(Label18);
        this.page5.add(Label14);
        this.page5.add(Label19);
        this.page5.add(Label3);
        this.page5.add(Image8);
        this.page5.add(Label15);
        this.page5.add(Label16);
        this.page5.add(forwardSummary);
        this.page5.add(backwardSummary);
        this.notebook.addTab("Conclusion", null, this.page5);
        
        
        this.notebook.add(page1);
        this.notebook.add(page2);
        this.notebook.add(page3);
        this.notebook.add(page4);
        this.notebook.add(page5);
        this.notebook.setBounds(0, 0, 492, 273);
        
        this.helpButton = new JButton("Help");
        this.helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                helpButtonClick(event);
            }
        });
        this.helpButton.setMnemonic(KeyEvent.VK_H);
        this.helpButton.setBounds(6, 276, 70, 21);
        
        this.goBack = new JButton("<< Back");
        this.goBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                goBackClick(event);
            }
        });
        this.goBack.setMnemonic(KeyEvent.VK_B);
        this.goBack.setBounds(229, 276, 70, 21);
        
        this.goNext = new JButton("Next >>");
        this.goNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                goNextClick(event);
            }
        });
        this.goNext.setMnemonic(KeyEvent.VK_N);
        this.goNext.setBounds(305, 276, 70, 21);
        
        this.cancel = new JButton("Cancel");
        this.cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                cancelClick(event);
            }
        });
        this.cancel.setMnemonic(KeyEvent.VK_C);
        this.cancel.setBounds(413, 276, 70, 21);
        
        delphiPanel.add(notebook);
        delphiPanel.add(helpButton);
        delphiPanel.add(goBack);
        delphiPanel.add(goNext);
        delphiPanel.add(cancel);
        return delphiPanel;
    }
    
        
    public void BackwardEditChange(CaretEvent event) {
        System.out.println("BackwardEditChange");
    }
        
    public void ForwardEditChange(CaretEvent event) {
        System.out.println("ForwardEditChange");
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
                LinkWizardWindow thisClass = new LinkWizardWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

}
