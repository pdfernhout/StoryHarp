
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// import common.*;

public class AboutWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    ImagePanel Image1;
    JLabel Label1;
    JLabel Label2;
    JLabel Label3;
    JLabel Label4;
    JLabel versionLabel;
    JLabel Label6;
    JLabel Label7;
    JLabel Label8;
    JLabel registeredToLabel;
    JButton OKButton;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
    
    public AboutWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 325));
        this.setContentPane(getMainContentPane());
        this.setTitle("About StoryHarp");
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
        this.setBounds(431, 279, 320, 172  + 80); // extra for title bar and menu
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 96;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.BorderStyle = bsDialog;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnCreate = FormCreate;
        
        
        Image Image1Image = toolkit.createImage("../resources/AboutForm_Image1.png");
        this.Image1 = new ImagePanel(new ImageIcon(Image1Image));
        this.Image1.setBounds(4, 4, 80, 129);
        
        this.Label1 = new JLabel("Audioventure Authoring System");
        this.Label1.setBounds(92, 26, 154, 14);
        
        this.Label2 = new JLabel("Copyright 1998 Paul D. Fernhout and Cynthia F. Kurtz");
        this.Label2.setBounds(4, 138, 256, 14);
        
        this.Label3 = new JLabel("All Rights Reserved");
        this.Label3.setBounds(4, 154, 95, 14);
        
        this.Label4 = new JLabel("http://www.kurtz-fernhout.com");
        this.Label4.setBounds(92, 106, 152, 14);
        
        this.versionLabel = new JLabel("Version 1.31");
        this.versionLabel.setBounds(92, 42, 62, 14);
        
        this.Label6 = new JLabel("By Kurtz-Fernhout Software");
        this.Label6.setBounds(92, 90, 138, 14);
        
        this.Label7 = new JLabel("StoryHarp");
        this.Label7.setBounds(92, 4, 78, 19);
        
        this.Label8 = new JLabel("TM");
        this.Label8.setBounds(171, 0, 14, 14);
        
        this.registeredToLabel = new JLabel("Registered to: ");
        this.registeredToLabel.setBounds(92, 64, 70, 14);
        
        this.OKButton = new JButton("OK");
        this.OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                OKButtonClick(event);
            }
        });
        this.OKButton.setMnemonic(KeyEvent.VK_O);
        this.getRootPane().setDefaultButton(this.OKButton);
        this.OKButton.setBounds(255, 8, 60, 25);
        
        delphiPanel.add(Image1);
        delphiPanel.add(Label1);
        delphiPanel.add(Label2);
        delphiPanel.add(Label3);
        delphiPanel.add(Label4);
        delphiPanel.add(versionLabel);
        delphiPanel.add(Label6);
        delphiPanel.add(Label7);
        delphiPanel.add(Label8);
        delphiPanel.add(registeredToLabel);
        delphiPanel.add(OKButton);
        return delphiPanel;
    }
    
        
    public void OKButtonClick(ActionEvent event) {
        System.out.println("OKButtonClick");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AboutWindow thisClass = new AboutWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

}
