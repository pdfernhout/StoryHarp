package com.kurtz_fernhout.storyharp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

public class TAboutForm extends JDialog {
    private static final long serialVersionUID = 1L;
    
    // const
    public static final boolean kAsSplash = true;
    public static final boolean kAsAbout = false;
    
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
    
    public TAboutForm() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
        this.FormCreate();
    }
    
    private void initialize() {
        this.setSize(new Dimension(534, 265));
        this.setContentPane(getMainContentPane());
        this.setTitle("About StoryHarp");
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        //setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 2 - this.getWidth() / 2, screenHeight / 2 - this.getHeight() / 2);
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
        this.setVisible(false);
    }
    
    public JPanel getDelphiPanel() {
        if (delphiPanel != null) return delphiPanel;
        Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        delphiPanel = new JPanel();
        delphiPanel.setLayout(new MigLayout());

        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 96;
        //  --------------- UNHANDLED ATTRIBUTE: this.BorderStyle = bsDialog;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnCreate = FormCreate;
        
        
        Image Image1Image = toolkit.createImage("resources/AboutForm_Image1.png");
        this.Image1 = new ImagePanel(new ImageIcon(Image1Image));
        
        this.Label1 = new JLabel("Audioventure Authoring System");
        
        this.Label2 = new JLabel("Copyright 1998, 1999, 2009 Paul D. Fernhout and Cynthia F. Kurtz");
        
        this.Label3 = new JLabel("All Rights Reserved");
        
        this.Label4 = new JLabel("http://www.kurtz-fernhout.com");
        
        this.versionLabel = new JLabel("Version 2.00");
        
        this.Label6 = new JLabel("By Kurtz-Fernhout Software");
        
        this.Label7 = new JLabel("StoryHarp");
        
        this.Label8 = new JLabel("TM");
        
        this.registeredToLabel = new JLabel("Registered to: ");
        
        this.OKButton = new JButton("OK");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                OKButtonClick(event);
            }
        });
        this.OKButton.setMnemonic(KeyEvent.VK_O);
        this.getRootPane().setDefaultButton(this.OKButton);
        
        delphiPanel.add(Image1, "spany 6, spanx 1");
        delphiPanel.add(Label7, "split 2");
        delphiPanel.add(Label8, "");
        delphiPanel.add(OKButton, "skip 1, wrap");
        delphiPanel.add(Label1, "wrap");
        delphiPanel.add(versionLabel, "wrap");
        delphiPanel.add(registeredToLabel, "wrap");
        delphiPanel.add(Label6, "wrap");
        delphiPanel.add(Label4, "wrap");
        delphiPanel.add(Label2, "gaptop 30, spanx 3, wrap");
        delphiPanel.add(Label3, "spanx 3, wrap");
        return delphiPanel;
    }
    
        
    public void OKButtonClick(ActionEvent event) {
        this.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	TAboutForm thisClass = new TAboutForm();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setUpAsSplashOrAbout(true);
                thisClass.setVisible(true);
            }
        });
    }
    
    public void setUpAsSplashOrAbout(boolean splash) {
        if (splash) {
            this.OKButton.setVisible(false);
            this.setTitle("");
            this.setUndecorated(true);
            this.showNameString("");
        } else {
            this.setTitle("About StoryHarp");
            this.setUndecorated(false);
            this.OKButton.setVisible(true);
            this.showNameString(TSDomain.domain.registrationName);
        }
    }
    
    public void showNameString(String aName) {
        if (aName.equals("")) {
            this.registeredToLabel.setText("");
        } else if (TSDomain.domain.playerOnly) {
            this.registeredToLabel.setText("Player-only mode");
        } else if (!TSDomain.domain.registered) {
            this.registeredToLabel.setText("Unregistered");
        } else {
            if ((aName.length() > 1) && (aName.charAt(aName.length() - 1) == ',')) {
                aName = aName.substring(0, aName.length() - 1);
            }
            this.registeredToLabel.setText("Registered to: " + aName);
        }
    }
    
    public void FormCreate() {
        this.versionLabel.setText(Application.gVersionName);
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
