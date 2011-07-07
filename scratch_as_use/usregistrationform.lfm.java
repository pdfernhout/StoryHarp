
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// import common.*;

public class RegistrationWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    JLabel Label1;
    JLabel Label2;
    JLabel DateInstalledLabel;
    JLabel DaysSinceInstallationLabel;
    JTextField UserNameEdit;
    JTextField RegistrationCodeEdit;
    JButton OKButton;
    JButton CancelButton;
    JTextArea DisplayMemo;
    JButton EnableEditorButton;
    JButton OnlineRegistrationButton;
    JButton AbortButton;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
    
    public RegistrationWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 325));
        this.setContentPane(getMainContentPane());
        this.setTitle("Registration");
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
        this.setBounds(492, 209, 561, 439  + 80); // extra for title bar and menu
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 16;
        //  --------------- UNHANDLED ATTRIBUTE: this.ActiveControl = UserNameEdit;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.BorderStyle = bsDialog;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 100;
        
        
        this.Label1 = new JLabel("User name");
        this.Label1.setBounds(48, 268, 81, 17);
        
        this.Label2 = new JLabel("Registration code");
        this.Label2.setBounds(12, 304, 127, 17);
        
        this.DateInstalledLabel = new JLabel("DateInstalledLabel");
        this.DateInstalledLabel.setBounds(20, 232, 134, 17);
        this.DateInstalledLabel.setVisible(false);
        
        this.DaysSinceInstallationLabel = new JLabel("DaysSinceInstallationLabel");
        this.DaysSinceInstallationLabel.setBounds(220, 232, 193, 17);
        this.DaysSinceInstallationLabel.setVisible(false);
        
        this.UserNameEdit = new JTextField("");
        this.UserNameEdit.setBounds(124, 264, 217, 24);
        UserNameEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent event) {
                UserNameEditKeyPress(event);
            }
        });
        
        this.RegistrationCodeEdit = new JTextField("");
        this.RegistrationCodeEdit.setBounds(124, 300, 217, 24);
        RegistrationCodeEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent event) {
                RegistrationCodeEditKeyPress(event);
            }
        });
        
        this.OKButton = new JButton("Register");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                OKButtonClick(event);
            }
        });
        this.OKButton.setBounds(408, 308, 109, 25);
        
        this.CancelButton = new JButton("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                CancelButtonClick(event);
            }
        });
        this.CancelButton.setBounds(412, 228, 103, 25);
        
        this.DisplayMemo = new JTextArea(10, 10);
        this.DisplayMemo.setBounds(16, 12, 485, 205);
        this.DisplayMemo.setEditable(false);
        
        this.EnableEditorButton = new JButton("Enable editor");
        EnableEditorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                EnableEditorButtonClick(event);
            }
        });
        this.EnableEditorButton.setBounds(408, 276, 107, 25);
        
        this.OnlineRegistrationButton = new JButton("Pay now online");
        OnlineRegistrationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                OnlineRegistrationButtonClick(event);
            }
        });
        this.OnlineRegistrationButton.setBounds(408, 336, 109, 25);
        
        this.AbortButton = new JButton("Abort");
        this.AbortButton.setBounds(324, 336, 75, 25);
        this.AbortButton.setEnabled(false);
        
        delphiPanel.add(Label1);
        delphiPanel.add(Label2);
        delphiPanel.add(DateInstalledLabel);
        delphiPanel.add(DaysSinceInstallationLabel);
        delphiPanel.add(UserNameEdit);
        delphiPanel.add(RegistrationCodeEdit);
        delphiPanel.add(OKButton);
        delphiPanel.add(CancelButton);
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setBounds(16, 12, 485, 205);;
        scroller1.setViewportView(DisplayMemo);
        delphiPanel.add(scroller1);
        delphiPanel.add(EnableEditorButton);
        delphiPanel.add(OnlineRegistrationButton);
        delphiPanel.add(AbortButton);
        return delphiPanel;
    }
    
        
    public void CancelButtonClick(ActionEvent event) {
        System.out.println("CancelButtonClick");
    }
        
    public void EnableEditorButtonClick(ActionEvent event) {
        System.out.println("EnableEditorButtonClick");
    }
        
    public void OKButtonClick(ActionEvent event) {
        System.out.println("OKButtonClick");
    }
        
    public void OnlineRegistrationButtonClick(ActionEvent event) {
        System.out.println("OnlineRegistrationButtonClick");
    }
        
    public void RegistrationCodeEditKeyPress(KeyEvent event) {
        System.out.println("RegistrationCodeEditKeyPress");
    }
        
    public void UserNameEditKeyPress(KeyEvent event) {
        System.out.println("UserNameEditKeyPress");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RegistrationWindow thisClass = new RegistrationWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

}
