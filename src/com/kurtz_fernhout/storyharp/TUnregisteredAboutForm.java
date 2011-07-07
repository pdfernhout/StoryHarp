package com.kurtz_fernhout.storyharp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class TUnregisteredAboutForm extends JDialog {
    private static final long serialVersionUID = 1L;
    JLabel noDistributeLabel;
    JLabel timeWarningLabel;
    JLabel Label1;
    ImagePanel Image1;
    JLabel Label2;
    JLabel Label3;
    JLabel Label4;
    JLabel Label5;
    JLabel versionLabel;
    JLabel Label7;
    JLabel Label8;
    JLabel Label9;
    JLabel registeredToLabel;
    JButton close;
    JButton registerIt;
    JButton readLicense;
    JPanel TimePanel;
    JLabel hoursLabel;
    JButton cancel;
    JButton whyRegister;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
    
    public TUnregisteredAboutForm() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
        this.versionLabel.setText(Application.gVersionName);
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 325));
        this.setContentPane(getMainContentPane());
        this.setTitle("Thank you for evaluating StoryHarp!");
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
        this.setBounds(343, 170, 320, 240  + 80); // extra for title bar and menu
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.ActiveControl = registerIt;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.BorderStyle = bsDialog;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnCreate = FormCreate;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 100;
        
        
        this.noDistributeLabel = new JLabel("<html>Under no circumstances are you licensed to distribute any output or data files created using an unregistered copy of StoryHarp.");
        this.noDistributeLabel.setBounds(8, 303, 318, 26);
        
        this.timeWarningLabel = new JLabel("<html>If you have been using StoryHarp (other than in player-only mode) for 24 hours or more (in total), you are legally required to register it.");
        this.timeWarningLabel.setBounds(8, 203, 318, 40);
        
        this.Label1 = new JLabel(" We will remind you to register StoryHarp by showing you this window when you leave the program. This reminder will disappear on registration. ");
        this.Label1.setBounds(8, 255, 281, 77);
        
        Image Image1Image = toolkit.createImage("resources/UnregisteredAboutForm_Image1.png");
        this.Image1 = new ImagePanel(new ImageIcon(Image1Image));
        this.Image1.setBounds(8, 4, 80, 129);
        
        this.Label2 = new JLabel("Audioventure Authoring System");
        this.Label2.setBounds(96, 26, 230, 17);
        
        this.Label3 = new JLabel("Copyright 1998 Paul D. Fernhout and Cynthia F. Kurtz");
        this.Label3.setBounds(8, 138, 393, 17);
        
        this.Label4 = new JLabel("All Rights Reserved");
        this.Label4.setBounds(8, 154, 143, 17);
        
        this.Label5 = new JLabel("http://www.kurtz-fernhout.com");
        this.Label5.setBounds(96, 106, 218, 17);
        
        this.versionLabel = new JLabel("Version 1.3");
        this.versionLabel.setBounds(96, 42, 82, 17);
        
        this.Label7 = new JLabel("By Kurtz-Fernhout Software");
        this.Label7.setBounds(96, 90, 203, 17);
        
        this.Label8 = new JLabel("StoryHarp");
        this.Label8.setBounds(96, 4, 73, 15);
        
        this.Label9 = new JLabel("TM");
        this.Label9.setBounds(175, 0, 24, 17);
        
        this.registeredToLabel = new JLabel("Unregistered Evaluation Copy");
        this.registeredToLabel.setBounds(96, 64, 146, 12);
        
        this.close = new JButton("Close");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                closeClick(event);
            }
        });
        this.close.setMnemonic(KeyEvent.VK_C);
        this.close.setBounds(330, 4, 90, 21);
        
        this.registerIt = new JButton("Register");
        registerIt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                registerItClick(event);
            }
        });
        this.registerIt.setMnemonic(KeyEvent.VK_R);
        this.registerIt.setBounds(330, 28, 90, 23);
        
        this.readLicense = new JButton("Read License");
        readLicense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                readLicenseClick(event);
            }
        });
        this.readLicense.setMnemonic(KeyEvent.VK_L);
        this.readLicense.setBounds(330, 93, 90, 21);
        
        this.hoursLabel = new JLabel("You have been using StoryHarp for 8 hours.");
        this.hoursLabel.setBounds(6, 4, 214, 12);
        hoursLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                hoursLabelClick(event);
            }
        });
        
        this.TimePanel = new JPanel(null);
        // -- this.TimePanel.setLayout(new BoxLayout(this.TimePanel, BoxLayout.Y_AXIS));
        this.TimePanel.add(hoursLabel);
        this.TimePanel.setBounds(8, 173, 316, 22);
        
        this.cancel = new JButton("Cancel");
        this.cancel.setMnemonic(KeyEvent.VK_A);
        this.cancel.setBounds(330, 129, 90, 21);
        //  --------------- UNHANDLED ATTRIBUTE: this.cancel.ModalResult = 2;
        //  --------------- UNHANDLED ATTRIBUTE: this.cancel.Cancel = True;
        
        this.whyRegister = new JButton("Why Register?");
        whyRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                whyRegisterClick(event);
            }
        });
        this.whyRegister.setMnemonic(KeyEvent.VK_W);
        this.whyRegister.setBounds(330, 66, 90, 23);
        
        delphiPanel.add(noDistributeLabel);
        delphiPanel.add(timeWarningLabel);
        delphiPanel.add(Label1);
        delphiPanel.add(Image1);
        delphiPanel.add(Label2);
        delphiPanel.add(Label3);
        delphiPanel.add(Label4);
        delphiPanel.add(Label5);
        delphiPanel.add(versionLabel);
        delphiPanel.add(Label7);
        delphiPanel.add(Label8);
        delphiPanel.add(Label9);
        delphiPanel.add(registeredToLabel);
        delphiPanel.add(close);
        delphiPanel.add(registerIt);
        delphiPanel.add(readLicense);
        delphiPanel.add(TimePanel);
        delphiPanel.add(cancel);
        delphiPanel.add(whyRegister);
        return delphiPanel;
    }
    
        
    public void closeClick(ActionEvent event) {
        this.setVisible(false);
    }
        
    public void hoursLabelClick(MouseEvent event) {
        //var
        //  hours, minutes: smallint;
        //  hourString: string;
        // only for cfk testing -- remove later
        //
        //  if inputQuery('Testing', 'Enter number of hours', hourString) then
        //    hours := strToInt(hourString);
        //  if inputQuery('Testing', 'Enter number of minutes', hourString) then
        //    minutes := strToInt(hourString);
        //
        //  hoursLabel.caption := 'You have been evaluating StoryHarp for ';
        //  if hours >= 24 then
        //    hoursLabel.caption := hoursLabel.caption + 'more than 24 hours.'
        //  else if (minutes < 1) and (hours < 1) then
        //    hoursLabel.caption := hoursLabel.caption + 'less than one minute.'
        //  else if (minutes = 1) and (hours < 1) then
        //    hoursLabel.caption := hoursLabel.caption + 'one minute.'
        //  else if hours < 1 then
        //    hoursLabel.caption := hoursLabel.caption + String.valueOf(minutes) + ' minutes.'
        //  else if hours = 1 then
        //    hoursLabel.caption := hoursLabel.caption + String.valueOf(hours) + ' hour and ' + String.valueOf(minutes) + ' minutes.'
        //  else
        //    hoursLabel.caption := hoursLabel.caption + String.valueOf(hours) + ' hours and ' + String.valueOf(minutes) + ' minutes.';
        //  
    }
        
    public void readLicenseClick(ActionEvent event) {
    	Application.HelpJump("License");
    }
        
    public void registerItClick(ActionEvent event) {
        if (Application.RegistrationForm.ShowModal() == mrOK) {
            this.setVisible(false);
        }
    }
        
    public void whyRegisterClick(ActionEvent event) {
    	Application.HelpJump("Why_register?");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	TUnregisteredAboutForm thisClass = new TUnregisteredAboutForm();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }
    
    public void initializeWithWhetherClosingProgram(boolean closingProgram) {
        if (TSDomain.domain.playerOnly) {
            this.ClientHeight = this.TimePanel.y - 1;
            this.setTitle("Thank you for using the StoryHarp player!");
            this.registeredToLabel.setText("Player-only mode");
            this.whyRegister.setVisible(false);
            this.registerIt.setVisible(false);
            this.readLicense.y = this.registerIt.y;
        }
        if (closingProgram) {
            this.close.setText("Quit");
        } else {
            this.close.setText("Close");
        }
        this.cancel.setVisible(closingProgram);
        this.FormActivate();
    }
    
    public void FormActivate() {
        TDateTime timeBetween = new TDateTime();
        byte smallHours = 0;
        byte minutes = 0;
        byte seconds = 0;
        byte milliseconds = 0;
        int randomNumber = 0;
        int hours = 0;
        int i = 0;
        
        if (TSDomain.domain.playerOnly) {
            return;
        }
        this.ActiveControl = this.registerIt;
        UNRESOLVED.randomize;
        for (i = 0; i <= 100; i++) {
            UNRESOLVED.random;
        }
        randomNumber = UNRESOLVED.random(2);
        switch (randomNumber) {
            case 0:
                this.close.y = 4;
                this.registerIt.y = this.close.y + this.close.Height + 3;
                break;
            case 1:
                this.registerIt.y = 4;
                this.close.y = this.registerIt.y + this.registerIt.Height + 3;
                break;
        this.hoursLabel.setText("You have been using StoryHarp for ");
        timeBetween = usdomain.max((UNRESOLVED.Now - TSDomain.domain.startTimeThisSession), 0) + TSDomain.domain.accumulatedUnregisteredTime;
        UNRESOLVED.DecodeTime(timeBetween, smallHours, minutes, seconds, milliseconds);
        hours = smallHours;
        if (timeBetween >= 1.0) {
            hours = hours + trunc(timeBetween) * 24;
        }
        if ((minutes < 1) && (hours < 1)) {
            // hoursLabel.caption := hoursLabel.caption + 'more than 24 hours.'
            this.hoursLabel.setText(this.hoursLabel.Caption + "less than one minute.");
        } else if ((minutes == 1) && (hours < 1)) {
            this.hoursLabel.setText(this.hoursLabel.Caption + "one minute.");
        } else if (hours < 1) {
            this.hoursLabel.setText(this.hoursLabel.Caption + String.valueOf(minutes) + " minutes.");
        } else if (hours == 1) {
            this.hoursLabel.setText(this.hoursLabel.Caption + String.valueOf(hours) + " hour and " + String.valueOf(minutes) + " minutes.");
        } else {
            this.hoursLabel.setText(this.hoursLabel.Caption + String.valueOf(hours) + " hours and " + String.valueOf(minutes) + " minutes.");
        }
        if (hours >= 24.0) {
            this.timeWarningLabel.Font.Color = Color.green;
            this.timeWarningLabel.Font.Style = {UNRESOLVED.fsBold, };
        }
    }

}
