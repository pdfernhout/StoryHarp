
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// import common.*;

public class PreferencesWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    SpeedButton helpButton;
    JButton Close;
    JButton cancel;
    JPanel Panel1;
    JTextField tableFontNameEdit;
    JButton changeTableFont;
    JLabel Label4;
    JPanel backgroundColorPanel;
    JPanel textColorPanel;
    JCheckBox showMapCommands;
    JButton changeBackgroundColor;
    JLabel Label2;
    JButton changeTextColor;
    JLabel Label3;
    JTextField mapFontNameEdit;
    JButton changeMapFont;
    JLabel Label1;
    JTextField browserFontNameEdit;
    JButton changeBrowserFont;
    JLabel Label5;
    JPanel mapCommandsColorPanel;
    JButton ChangeMapCommandsColor;
    JLabel Label6;
    JCheckBox symbolButtons;
    // UNHANDLED_TYPE ColorDialog;
    // UNHANDLED_TYPE FontDialog;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
    
    public PreferencesWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 325));
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
        Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        delphiPanel = new JPanel();
        delphiPanel.setLayout(null);
        // -- delphiPanel.setLayout(BoxLayout(contentPane, BoxLayout.Y_AXIS));
        // Font buttonFont = new Font("Arial Narrow", Font.PLAIN, 9);
        // Insets buttonMargin = new Insets(1, 1, 1, 1);
        this.setBounds(174, 399, 363, 323  + 80); // extra for title bar and menu
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 13;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 96;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.BorderStyle = bsDialog;
        
        
        this.helpButton = new SpeedButton("Help");
        this.helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                helpButtonClick(event);
            }
        });
        this.helpButton.setMnemonic(KeyEvent.VK_H);
        this.helpButton.setBounds(299, 63, 60, 21);
        
        this.Close = new JButton("OK");
        this.getRootPane().setDefaultButton(this.Close);
        this.Close.setBounds(299, 4, 60, 21);
        //  --------------- UNHANDLED ATTRIBUTE: this.Close.ModalResult = 1;
        
        this.cancel = new JButton("Cancel");
        this.cancel.setBounds(299, 27, 60, 21);
        //  --------------- UNHANDLED ATTRIBUTE: this.cancel.ModalResult = 2;
        //  --------------- UNHANDLED ATTRIBUTE: this.cancel.Cancel = True;
        
        this.tableFontNameEdit = new JTextField("");
        this.tableFontNameEdit.setEditable(false);
        this.tableFontNameEdit.setBounds(32, 24, 169, 21);
        
        this.changeTableFont = new JButton("Change...");
        this.changeTableFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                changeTableFontClick(event);
            }
        });
        this.changeTableFont.setBounds(208, 24, 69, 21);
        
        this.Label4 = new JLabel("Table font", displayedMnemonic=KeyEvent.VK_T, labelFor=this.changeTableFont);
        this.Label4.setBounds(12, 8, 48, 13);
        
        this.backgroundColorPanel = new JPanel(null);
        // -- this.backgroundColorPanel.setLayout(new BoxLayout(this.backgroundColorPanel, BoxLayout.Y_AXIS));
        this.backgroundColorPanel.setBounds(32, 157, 53, 21);
        
        this.textColorPanel = new JPanel(null);
        // -- this.textColorPanel.setLayout(new BoxLayout(this.textColorPanel, BoxLayout.Y_AXIS));
        this.textColorPanel.setBounds(32, 201, 53, 21);
        
        this.showMapCommands = new JCheckBox("Show map commands with prefix \">\"");
        this.showMapCommands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                showMapCommandsClick(event);
            }
        });
        this.showMapCommands.setMnemonic(KeyEvent.VK_C);
        this.showMapCommands.setBounds(12, 272, 201, 17);
        
        this.changeBackgroundColor = new JButton("Change...");
        this.changeBackgroundColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                changeBackgroundColorClick(event);
            }
        });
        this.changeBackgroundColor.setBounds(92, 158, 69, 21);
        
        this.Label2 = new JLabel("Background color for selected items", displayedMnemonic=KeyEvent.VK_C, labelFor=this.changeBackgroundColor);
        this.Label2.setBounds(12, 141, 169, 13);
        
        this.changeTextColor = new JButton("Change...");
        this.changeTextColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                changeTextColorClick(event);
            }
        });
        this.changeTextColor.setBounds(92, 202, 69, 21);
        
        this.Label3 = new JLabel("Text color for selected items", displayedMnemonic=KeyEvent.VK_X, labelFor=this.changeTextColor);
        this.Label3.setBounds(12, 185, 132, 13);
        
        this.mapFontNameEdit = new JTextField("");
        this.mapFontNameEdit.setEditable(false);
        this.mapFontNameEdit.setBounds(32, 68, 169, 21);
        
        this.changeMapFont = new JButton("Change...");
        this.changeMapFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                changeMapFontClick(event);
            }
        });
        this.changeMapFont.setBounds(208, 68, 69, 21);
        
        this.Label1 = new JLabel("Map font", displayedMnemonic=KeyEvent.VK_M, labelFor=this.changeMapFont);
        this.Label1.setBounds(12, 52, 42, 13);
        
        this.browserFontNameEdit = new JTextField("");
        this.browserFontNameEdit.setEditable(false);
        this.browserFontNameEdit.setBounds(32, 112, 169, 21);
        
        this.changeBrowserFont = new JButton("Change...");
        this.changeBrowserFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                changeBrowserFontClick(event);
            }
        });
        this.changeBrowserFont.setBounds(208, 112, 69, 21);
        
        this.Label5 = new JLabel("Browser font", displayedMnemonic=KeyEvent.VK_B, labelFor=this.changeBrowserFont);
        this.Label5.setBounds(12, 96, 59, 13);
        
        this.mapCommandsColorPanel = new JPanel(null);
        // -- this.mapCommandsColorPanel.setLayout(new BoxLayout(this.mapCommandsColorPanel, BoxLayout.Y_AXIS));
        this.mapCommandsColorPanel.setBounds(32, 245, 53, 21);
        
        this.ChangeMapCommandsColor = new JButton("Change...");
        this.ChangeMapCommandsColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                ChangeMapCommandsColorClick(event);
            }
        });
        this.ChangeMapCommandsColor.setBounds(92, 246, 69, 21);
        
        this.Label6 = new JLabel("Text color for commands in map", displayedMnemonic=KeyEvent.VK_O, labelFor=this.ChangeMapCommandsColor);
        this.Label6.setBounds(12, 229, 150, 13);
        
        this.symbolButtons = new JCheckBox("Use symbols instead of pictures on buttons");
        this.symbolButtons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                symbolButtonsClick(event);
            }
        });
        this.symbolButtons.setMnemonic(KeyEvent.VK_S);
        this.symbolButtons.setBounds(12, 292, 233, 17);
        
        this.Panel1 = new JPanel(null);
        // -- this.Panel1.setLayout(new BoxLayout(this.Panel1, BoxLayout.Y_AXIS));
        this.Panel1.add(tableFontNameEdit);
        this.Panel1.add(changeTableFont);
        this.Panel1.add(Label4);
        this.Panel1.add(backgroundColorPanel);
        this.Panel1.add(textColorPanel);
        this.Panel1.add(showMapCommands);
        this.Panel1.add(changeBackgroundColor);
        this.Panel1.add(Label2);
        this.Panel1.add(changeTextColor);
        this.Panel1.add(Label3);
        this.Panel1.add(mapFontNameEdit);
        this.Panel1.add(changeMapFont);
        this.Panel1.add(Label1);
        this.Panel1.add(browserFontNameEdit);
        this.Panel1.add(changeBrowserFont);
        this.Panel1.add(Label5);
        this.Panel1.add(mapCommandsColorPanel);
        this.Panel1.add(ChangeMapCommandsColor);
        this.Panel1.add(Label6);
        this.Panel1.add(symbolButtons);
        this.Panel1.setBounds(4, 4, 289, 313);
        
        //  ------- UNHANDLED TYPE TColorDialog: ColorDialog 
        //  --------------- UNHANDLED ATTRIBUTE: this.ColorDialog.Top = 104;
        //  --------------- UNHANDLED ATTRIBUTE: this.ColorDialog.Left = 308;
        
        //  ------- UNHANDLED TYPE TFontDialog: FontDialog 
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Font.Charset = DEFAULT_CHARSET;
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.MinFontSize = 0;
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Font.Style = [];
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.MaxFontSize = 0;
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Top = 136;
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Font.Name = 'Arial';
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Font.Height = -11;
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Options = [fdForceFontExist];
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Font.Color = clWindowText;
        //  --------------- UNHANDLED ATTRIBUTE: this.FontDialog.Left = 308;
        
        delphiPanel.add(helpButton);
        delphiPanel.add(Close);
        delphiPanel.add(cancel);
        delphiPanel.add(Panel1);
        return delphiPanel;
    }
    
        
    public void ChangeMapCommandsColorClick(ActionEvent event) {
        System.out.println("ChangeMapCommandsColorClick");
    }
        
    public void changeBackgroundColorClick(ActionEvent event) {
        System.out.println("changeBackgroundColorClick");
    }
        
    public void changeBrowserFontClick(ActionEvent event) {
        System.out.println("changeBrowserFontClick");
    }
        
    public void changeMapFontClick(ActionEvent event) {
        System.out.println("changeMapFontClick");
    }
        
    public void changeTableFontClick(ActionEvent event) {
        System.out.println("changeTableFontClick");
    }
        
    public void changeTextColorClick(ActionEvent event) {
        System.out.println("changeTextColorClick");
    }
        
    public void helpButtonClick(ActionEvent event) {
        System.out.println("helpButtonClick");
    }
        
    public void showMapCommandsClick(ActionEvent event) {
        System.out.println("showMapCommandsClick");
    }
        
    public void symbolButtonsClick(ActionEvent event) {
        System.out.println("symbolButtonsClick");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PreferencesWindow thisClass = new PreferencesWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

}
