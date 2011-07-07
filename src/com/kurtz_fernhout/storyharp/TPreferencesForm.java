package com.kurtz_fernhout.storyharp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

import com.l2fprod.common.swing.JFontChooser;

public class TPreferencesForm extends JDialog {
    private static final long serialVersionUID = 1L;
    SpeedButton helpButton;
    JButton Close;
    JButton cancel;
    JPanel Panel1;
    JTextField tableFontNameEdit;
    JButton changeTableFont;
    JLabel tableFontLabel;
    JPanel backgroundColorPanel;
    JPanel textColorPanel;
    JCheckBox showMapCommands;
    JButton changeBackgroundColor;
    JLabel backgroundColorForSelectedItemsLabel;
    JButton changeTextColor;
    JLabel textColorForSelectedItemsLabel;
    JTextField mapFontNameEdit;
    JButton changeMapFont;
    JLabel mapFontLabel;
    JTextField browserFontNameEdit;
    JButton changeBrowserFont;
    JLabel browserFontLabel;
    JPanel mapCommandsColorPanel;
    JButton ChangeMapCommandsColor;
    JLabel textColorForCommandsInMapLabel;
    JCheckBox symbolButtons;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
	private JPanel windowButtons;
	
    public DomainOptionsStructure options = new DomainOptionsStructure();  //  @jve:decl-index=0:
	public boolean okPressed;
    
    public TPreferencesForm() {
        super();
        this.okPressed = false;
        this.setModal(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
        this.FormActivate();
    }
    
    private void initialize() {
        this.setSize(new Dimension(599, 314));
        this.setContentPane(getMainContentPane());
        this.setTitle("Editor Preferences");
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
        delphiPanel = new JPanel();
        delphiPanel.setLayout(new BorderLayout());
        // -- delphiPanel.setLayout(BoxLayout(contentPane, BoxLayout.Y_AXIS));
        // Font buttonFont = new Font("Arial Narrow", Font.PLAIN, 9);
        // Insets buttonMargin = new Insets(1, 1, 1, 1);
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 13;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 96;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.BorderStyle = bsDialog;
        
        this.helpButton = new SpeedButton("Help");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                helpButtonClick(event);
            }
        });
        this.helpButton.setMnemonic(KeyEvent.VK_H);
        
        this.Close = new JButton("OK");
        Close.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent e) {
        		ClosePressed();
        	}
        });
        this.getRootPane().setDefaultButton(this.Close);
        //  --------------- UNHANDLED ATTRIBUTE: this.Close.ModalResult = 1;
        
        this.cancel = new JButton("Cancel");
        //  --------------- UNHANDLED ATTRIBUTE: this.cancel.ModalResult = 2;
        cancel.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent e) {
        		CancelPressed();
        	}
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.cancel.Cancel = True;
        
        this.windowButtons = new JPanel();
        this.windowButtons.setLayout(new BoxLayout(windowButtons, BoxLayout.Y_AXIS));

        this.windowButtons.add(Close);
        this.windowButtons.add(cancel);
        this.windowButtons.add(helpButton);

        this.tableFontNameEdit = new JTextField("");
        this.tableFontNameEdit.setEditable(false);
        
        this.changeTableFont = new JButton("Change...");
        changeTableFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                changeTableFontClick(event);
            }
        });
        
        this.tableFontLabel = new JLabel("Table font");
        //, displayedMnemonic=KeyEvent.VK_T, labelFor=this.changeTableFont);
        
        this.backgroundColorPanel = new JPanel(null);
        // -- this.backgroundColorPanel.setLayout(new BoxLayout(this.backgroundColorPanel, BoxLayout.Y_AXIS));
        this.backgroundColorPanel.setPreferredSize(new Dimension(53, 21));
        this.backgroundColorPanel.setSize(new Dimension(53, 21));
        
        this.textColorPanel = new JPanel(null);
        // -- this.textColorPanel.setLayout(new BoxLayout(this.textColorPanel, BoxLayout.Y_AXIS));
        this.textColorPanel.setPreferredSize(new Dimension(53, 21));
        this.textColorPanel.setSize(new Dimension(53, 21));
        
        this.showMapCommands = new JCheckBox("Show map commands with prefix \">\"");
        showMapCommands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                showMapCommandsClick(event);
            }
        });
        this.showMapCommands.setMnemonic(KeyEvent.VK_C);
        
        this.changeBackgroundColor = new JButton("Change...");
        changeBackgroundColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                changeBackgroundColorClick(event);
            }
        });
        
        this.backgroundColorForSelectedItemsLabel = new JLabel("Background color for selected items");
        //, displayedMnemonic=KeyEvent.VK_C, labelFor=this.changeBackgroundColor);
        
        this.changeTextColor = new JButton("Change...");
        changeTextColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                changeTextColorClick(event);
            }
        });
        
        this.textColorForSelectedItemsLabel = new JLabel("Text color for selected items");
        //, displayedMnemonic=KeyEvent.VK_X, labelFor=this.changeTextColor);
        
        this.mapFontNameEdit = new JTextField("");
        this.mapFontNameEdit.setEditable(false);
        
        this.changeMapFont = new JButton("Change...");
        changeMapFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                changeMapFontClick(event);
            }
        });
        
        this.mapFontLabel = new JLabel("Map font");
        //, displayedMnemonic=KeyEvent.VK_M, labelFor=this.changeMapFont);
        
        this.browserFontNameEdit = new JTextField("");
        this.browserFontNameEdit.setEditable(false);
        
        this.changeBrowserFont = new JButton("Change...");
        changeBrowserFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                changeBrowserFontClick(event);
            }
        });
        
        this.browserFontLabel = new JLabel("Browser font");
        //, displayedMnemonic=KeyEvent.VK_B, labelFor=this.changeBrowserFont);
        
        this.mapCommandsColorPanel = new JPanel(null);
        // -- this.mapCommandsColorPanel.setLayout(new BoxLayout(this.mapCommandsColorPanel, BoxLayout.Y_AXIS));
        this.mapCommandsColorPanel.setPreferredSize(new Dimension(53, 21));
        this.mapCommandsColorPanel.setSize(new Dimension(53, 21));
        
        this.ChangeMapCommandsColor = new JButton("Change...");
        ChangeMapCommandsColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                ChangeMapCommandsColorClick(event);
            }
        });
        
        this.textColorForCommandsInMapLabel = new JLabel("Text color for commands in map");
        //, displayedMnemonic=KeyEvent.VK_O, labelFor=this.ChangeMapCommandsColor);
        
        this.symbolButtons = new JCheckBox("Use symbols instead of pictures on buttons");
        symbolButtons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                symbolButtonsClick(event);
            }
        });
        this.symbolButtons.setMnemonic(KeyEvent.VK_S);
        
        this.Panel1 = new JPanel(new MigLayout());
        // -- this.Panel1.setLayout(new BoxLayout(this.Panel1, BoxLayout.Y_AXIS));
        this.Panel1.add(tableFontLabel, "right");
        this.Panel1.add(tableFontNameEdit, "growx, pushx");
        this.Panel1.add(changeTableFont, "wrap");

        this.Panel1.add(mapFontLabel, "right");
        this.Panel1.add(mapFontNameEdit, "growx");
        this.Panel1.add(changeMapFont, "wrap");
 
        this.Panel1.add(browserFontLabel, "right");
        this.Panel1.add(browserFontNameEdit, "growx");
        this.Panel1.add(changeBrowserFont, "wrap");

        this.Panel1.add(backgroundColorForSelectedItemsLabel, "span 2, split 2, right");
        this.Panel1.add(backgroundColorPanel, "wmin 53");
        this.Panel1.add(changeBackgroundColor, "wrap");
        
        this.Panel1.add(textColorForSelectedItemsLabel, "span 2, split 2, right");
        this.Panel1.add(textColorPanel, "wmin 53");
        this.Panel1.add(changeTextColor, "wrap");
        
        this.Panel1.add(textColorForCommandsInMapLabel, "span 2, split 2, right");
        this.Panel1.add(mapCommandsColorPanel, "wmin 53");
        this.Panel1.add(ChangeMapCommandsColor, "wrap");
        
        this.Panel1.add(showMapCommands, "span");
        this.Panel1.add(symbolButtons, "span");
        
        delphiPanel.add(Panel1, BorderLayout.CENTER);
        delphiPanel.add(windowButtons, BorderLayout.EAST);
        return delphiPanel;
    }
    
        
    protected void CancelPressed() {
    	this.setVisible(false);
	}

	protected void ClosePressed() {
		this.okPressed = false;
		this.setVisible(false);	
	}

	public void ChangeMapCommandsColorClick(ActionEvent event) {
    	Color newColor = JColorChooser.showDialog(this, "Choose text color for commands in map", this.mapCommandsColorPanel.getBackground());
        if (newColor != null) {
            this.mapCommandsColorPanel.setBackground(newColor);
            this.options.commandTextColorInMap = newColor;
        }
    }
        
    public void changeBackgroundColorClick(ActionEvent event) {
    	Color newColor = JColorChooser.showDialog(this, "Choose background color", this.backgroundColorPanel.getBackground());
        if (newColor != null) {
            this.backgroundColorPanel.setBackground(newColor);
            this.options.selectedItemColor = newColor;
        }
    }
    
    public void changeTextColorClick(ActionEvent event) {
    	Color newColor = JColorChooser.showDialog(this, "Choose text color for selected items", this.textColorPanel.getBackground());
        if (newColor != null) {
            this.textColorPanel.setBackground(newColor);
            this.options.selectedTextColor = newColor;
        }
    }
        
    public void changeBrowserFontClick(ActionEvent event) {
    	Font currentFont = new Font(this.options.browserFontName, Font.PLAIN, this.options.browserFontSize);
    	Font newFont = JFontChooser.showDialog(null, "Choose a browser font", currentFont);
        if (newFont != null) {
            this.options.browserFontName = newFont.getName();
            this.options.browserFontSize = newFont.getSize();
            this.browserFontNameEdit.setText(this.options.browserFontName + ", " + String.valueOf(this.options.browserFontSize));
        }
    }
        
    public void changeMapFontClick(ActionEvent event) {
    	Font currentFont = new Font(this.options.mapFontName, Font.PLAIN, this.options.mapFontSize);
    	Font newFont = JFontChooser.showDialog(null, "Choose a map font", currentFont);
        if (newFont != null) {
            this.options.mapFontName = newFont.getName();
            this.options.mapFontSize = newFont.getSize();
            this.browserFontNameEdit.setText(this.options.mapFontName + ", " + String.valueOf(this.options.mapFontSize));
        }
    }
        
    public void changeTableFontClick(ActionEvent event) {
    	Font currentFont = new Font(this.options.tableFontName, Font.PLAIN, this.options.tableFontSize);
    	Font newFont = JFontChooser.showDialog(null, "Choose a table font", currentFont);
        if (newFont != null) {
            this.options.tableFontName = newFont.getName();
            this.options.tableFontSize = newFont.getSize();
            this.browserFontNameEdit.setText(this.options.tableFontName + ", " + String.valueOf(this.options.tableFontSize));
        }
    }
        
    public void helpButtonClick(ActionEvent event) {
        Application.HelpJump("Changing_editor_preferences");
    }
        
    public void showMapCommandsClick(ActionEvent event) {
        this.options.showCommandPrefixInMap = this.showMapCommands.isSelected();
    }
        
    public void symbolButtonsClick(ActionEvent event) {
        this.options.buttonSymbols = this.symbolButtons.isSelected();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	TPreferencesForm thisClass = new TPreferencesForm();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.options = TSDomain.domain.options.clone();
                thisClass.setVisible(true);
            }
        });
    }

    public void FormActivate() {
        this.tableFontNameEdit.setText(this.options.tableFontName + ", " + String.valueOf(this.options.tableFontSize));
        this.mapFontNameEdit.setText(this.options.mapFontName + ", " + String.valueOf(this.options.mapFontSize));
        this.browserFontNameEdit.setText(this.options.browserFontName + ", " + String.valueOf(this.options.browserFontSize));

        this.backgroundColorPanel.setBackground(this.options.selectedItemColor);
        this.textColorPanel.setBackground(this.options.selectedTextColor);
        // maybe bug in original that was missing the next line; added
        this.mapCommandsColorPanel.setBackground(this.options.commandTextColorInMap);
        
        this.showMapCommands.setSelected(this.options.showCommandPrefixInMap);
        this.symbolButtons.setSelected(this.options.buttonSymbols);
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"
