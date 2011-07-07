
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// import common.*;

public class ContextWizardWindow extends JFrame {
    private static final long serialVersionUID = 1L;
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
    
    public ContextWizardWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 325));
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
        delphiPanel.setLayout(null);
        // -- delphiPanel.setLayout(BoxLayout(contentPane, BoxLayout.Y_AXIS));
        // Font buttonFont = new Font("Arial Narrow", Font.PLAIN, 9);
        // Insets buttonMargin = new Insets(1, 1, 1, 1);
        this.setBounds(291, 193, 320, 240  + 80); // extra for title bar and menu
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnCloseQuery = FormCloseQuery;
        //  --------------- UNHANDLED ATTRIBUTE: this.ActiveControl = goBack;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.BorderStyle = bsDialog;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 100;
        
        
        this.goBack = new JButton("<< Back");
        this.goBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                goBackClick(event);
            }
        });
        this.goBack.setMnemonic(KeyEvent.VK_B);
        this.goBack.setBounds(229, 344, 70, 21);
        
        this.goNext = new JButton("Next >>");
        this.goNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                goNextClick(event);
            }
        });
        this.goNext.setMnemonic(KeyEvent.VK_N);
        this.goNext.setBounds(305, 344, 70, 21);
        
        this.cancel = new JButton("Cancel");
        this.cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                cancelClick(event);
            }
        });
        this.cancel.setMnemonic(KeyEvent.VK_C);
        this.cancel.setBounds(414, 344, 70, 21);
        
        this.helpButton = new JButton("Help");
        this.helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                helpButtonClick(event);
            }
        });
        this.helpButton.setMnemonic(KeyEvent.VK_H);
        this.helpButton.setBounds(6, 344, 70, 21);
        
        this.notebook = new JTabbedPane();
        this.notebook.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent event) {
                notebookPageChanged(event);
            }
        });
        
        this.page1 = new JPanel(null);
        this.Label1 = new JLabel("Welcome to the New Contexts Wizard!");
        this.Label1.setBounds(60, 24, 246, 12);
        
        this.Label2 = new JLabel("This wizard will help you quickly create a set of new rules based on contexts you enter.");
        this.Label2.setBounds(24, 62, 626, 17);
        
        this.Label3 = new JLabel("Click the Next button to begin.");
        this.Label3.setBounds(24, 300, 145, 21);
        
        this.Label7 = new JLabel("You can enter a descriptive reply for each new context you enter here. The descriptive replies will be accessed with a common command such as \"look\".");
        this.Label7.setBounds(24, 167, 359, 77);
        
        this.Label9 = new JLabel("A reply is what the computer says after you say a command.");
        this.Label9.setBounds(69, 205, 440, 17);
        
        Image Image1Image = toolkit.createImage("../resources/ContextWizardForm_Image1.png");
        this.Image1 = new ImagePanel(new ImageIcon(Image1Image));
        this.Image1.setBounds(24, 12, 32, 32);
        
        this.Label10 = new JLabel("A command is what you say to the computer.");
        this.Label10.setBounds(69, 87, 330, 17);
        
        this.Label26 = new JLabel("You can click Cancel at any time to close the wizard without making any new rules.");
        this.Label26.setBounds(24, 317, 600, 17);
        
        this.Label8 = new JLabel("A context is the single most important requirement for the user of a command, usually a physical location.");
        this.Label8.setBounds(69, 117, 289, 57);
        
        this.commandStartPageImage = new ImagePanel(); // No image was set
        this.commandStartPageImage.setBounds(48, 86, 16, 16);
        
        this.contextStartPageImage = new ImagePanel(); // No image was set
        this.contextStartPageImage.setBounds(48, 117, 16, 16);
        
        this.replyStartPageImage = new ImagePanel(); // No image was set
        this.replyStartPageImage.setBounds(48, 204, 16, 16);
        
        this.page1.add(Label1);
        this.page1.add(Label2);
        this.page1.add(Label3);
        this.page1.add(Label7);
        this.page1.add(Label9);
        this.page1.add(Image1);
        this.page1.add(Label10);
        this.page1.add(Label26);
        this.page1.add(Label8);
        this.page1.add(commandStartPageImage);
        this.page1.add(contextStartPageImage);
        this.page1.add(replyStartPageImage);
        this.notebook.addTab("Start", null, this.page1);
        
        this.page1.setBounds(0, 0, 316, 307);
        
        this.page2 = new JPanel(null);
        this.Label5 = new JLabel("Enter or paste the contexts you want to create in the area below, separating each context from its descriptive reply by a pipe bar. For example, \"house | You are in a house\".");
        this.Label5.setBounds(24, 8, 440, 57);
        
        this.Label6 = new JLabel(" When you are finished entering contexts, click Next.");
        this.Label6.setBounds(24, 323, 378, 17);
        
        this.Label21 = new JLabel("Descriptions are optional. It''s okay to wrap entries on more than one line. Use carriage returns to separate entries.");
        this.Label21.setBounds(24, 36, 312, 57);
        
        this.Label22 = new JLabel("Context | Descriptive Reply");
        this.Label22.setBounds(8, 69, 134, 12);
        
        Image Image3Image = toolkit.createImage("../resources/ContextWizardForm_Image3.png");
        this.Image3 = new ImagePanel(new ImageIcon(Image3Image));
        this.Image3.setBounds(12, 8, 8, 16);
        
        this.NewContextsMemo = new JTextArea(10, 10);
        this.NewContextsMemo.setBounds(6, 84, 479, 233);
        //  --------------- UNHANDLED ATTRIBUTE: this.NewContextsMemo.ScrollBars = ssVertical;
        
        this.page2.add(Label5);
        this.page2.add(Label6);
        this.page2.add(Label21);
        this.page2.add(Label22);
        this.page2.add(Image3);
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setBounds(6, 84, 479, 233);;
        scroller1.setViewportView(NewContextsMemo);
        this.page2.add(scroller1);
        this.notebook.addTab("EnterContexts", null, this.page2);
        
        this.page2.setBounds(0, 0, 316, 307);
        
        this.page3 = new JPanel(null);
        this.DescribeLabel = new JLabel(" What command should the user to say to access these descriptive replies?");
        this.DescribeLabel.setBounds(24, 13, 359, 12);
        
        this.DescribeLabelExtra = new JLabel("Some generic examples are:");
        this.DescribeLabelExtra.setBounds(54, 70, 207, 17);
        
        this.Label4 = new JLabel("If you have not entered a description for a context, the wizard will add a default description of ''There is nothing of interest here.'' ");
        this.Label4.setBounds(54, 170, 303, 77);
        
        Image DescribeImageImage = toolkit.createImage("../resources/ContextWizardForm_DescribeImage.png");
        this.DescribeImage = new ImagePanel(new ImageIcon(DescribeImageImage));
        this.DescribeImage.setBounds(12, 12, 8, 16);
        
        this.commandImage = new ImagePanel(); // No image was set
        this.commandImage.setBounds(54, 40, 16, 16);
        
        this.Label15 = new JLabel("\"look\", \"listen\", \"smell\", \"feel\", \"taste\", and \"sense\".");
        this.Label15.setBounds(80, 93, 362, 17);
        
        this.Label16 = new JLabel("You should stick with \"look\" unless you are doing something special. You can change individual commands later (in the editor) to deal with specific situations.");
        this.Label16.setBounds(52, 117, 309, 77);
        
        this.DescribeEdit = new JTextField("look");
        this.DescribeEdit.setBounds(74, 38, 291, 22);
        
        this.page3.add(DescribeLabel);
        this.page3.add(DescribeLabelExtra);
        this.page3.add(Label4);
        this.page3.add(DescribeImage);
        this.page3.add(commandImage);
        this.page3.add(Label15);
        this.page3.add(Label16);
        this.page3.add(DescribeEdit);
        this.notebook.addTab("GenerateDescriptions", null, this.page3);
        
        this.page3.setBounds(0, 0, 316, 307);
        
        this.page4 = new JPanel(null);
        this.Label13 = new JLabel("You have completed the information the wizard needs to generate a new set of rules based on your the contexts and descriptions you have entered.");
        this.Label13.setBounds(24, 58, 375, 57);
        
        this.Label14 = new JLabel("Click Finish to create the new rules and close the wizard.");
        this.Label14.setBounds(24, 249, 301, 21);
        
        this.Label18 = new JLabel("Congratulations!");
        this.Label18.setBounds(64, 24, 113, 12);
        
        this.Label19 = new JLabel("Click Back to review your choices. Click Cancel to close the wizard without making any new rules.");
        this.Label19.setBounds(24, 304, 358, 37);
        
        Image Image2Image = toolkit.createImage("../resources/ContextWizardForm_Image2.png");
        this.Image2 = new ImagePanel(new ImageIcon(Image2Image));
        this.Image2.setBounds(24, 15, 32, 32);
        
        this.Label11 = new JLabel("After you finish the wizard, you can choose Undo from the Edit menu to remove your new rules.");
        this.Label11.setBounds(24, 271, 334, 57);
        
        this.Label12 = new JLabel("The text you entered here will also be saved in the log file (even if you cancel using the wizard).");
        this.Label12.setBounds(24, 92, 365, 37);
        
        this.page4.add(Label13);
        this.page4.add(Label14);
        this.page4.add(Label18);
        this.page4.add(Label19);
        this.page4.add(Image2);
        this.page4.add(Label11);
        this.page4.add(Label12);
        this.notebook.addTab("Finish", null, this.page4);
        
        this.page4.setBounds(0, 0, 316, 307);
        
        this.notebook.add(page1);
        this.notebook.add(page2);
        this.notebook.add(page3);
        this.notebook.add(page4);
        this.notebook.setBounds(0, 0, 320, 341);
        //  --------------- UNHANDLED ATTRIBUTE: this.notebook.PageIndex = 0;
        
        delphiPanel.add(goBack);
        delphiPanel.add(goNext);
        delphiPanel.add(cancel);
        delphiPanel.add(helpButton);
        delphiPanel.add(notebook);
        return delphiPanel;
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
                ContextWizardWindow thisClass = new ContextWizardWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

}
