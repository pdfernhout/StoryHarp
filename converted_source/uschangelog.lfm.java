
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// import common.*;

public class ChangeLogWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    JPanel bottomPanel;
    JButton CopySelectedTextButton;
    JButton UpdateButton;
    JButton changeLogFile;
    JButton clearLogFile;
    JButton helpButton;
    JTextArea LogContentsRichEdit;
    // UNHANDLED_TYPE OpenDialog;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
    
    public ChangeLogWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 325));
        this.setContentPane(getMainContentPane());
        this.setTitle("Log file");
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
        this.setBounds(387, 632, 320, 240  + 80); // extra for title bar and menu
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.ActiveControl = CopySelectedTextButton;
        //  --------------- UNHANDLED ATTRIBUTE: this.KeyPreview = True;
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent event) {
                FormKeyUp(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnCreate = FormCreate;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 100;
        
        
        this.CopySelectedTextButton = new JButton("Copy selection");
        this.CopySelectedTextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                CopySelectedTextButtonClick(event);
            }
        });
        this.CopySelectedTextButton.setMnemonic(KeyEvent.VK_C);
        this.CopySelectedTextButton.setBounds(6, 5, 87, 21);
        
        this.UpdateButton = new JButton("Reload");
        this.UpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                UpdateButtonClick(event);
            }
        });
        this.UpdateButton.setMnemonic(KeyEvent.VK_R);
        this.UpdateButton.setBounds(96, 5, 55, 21);
        
        this.changeLogFile = new JButton("Change...");
        this.changeLogFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                changeLogFileClick(event);
            }
        });
        this.changeLogFile.setMnemonic(KeyEvent.VK_A);
        this.changeLogFile.setBounds(200, 5, 56, 21);
        
        this.clearLogFile = new JButton("Clear");
        this.clearLogFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                clearLogFileClick(event);
            }
        });
        this.clearLogFile.setMnemonic(KeyEvent.VK_L);
        this.clearLogFile.setBounds(154, 5, 43, 21);
        
        this.helpButton = new JButton("Help");
        this.helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                helpButtonClick(event);
            }
        });
        this.helpButton.setMnemonic(KeyEvent.VK_H);
        this.helpButton.setBounds(259, 5, 43, 21);
        
        this.bottomPanel = new JPanel(null);
        // -- this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, BoxLayout.Y_AXIS));
        this.bottomPanel.add(CopySelectedTextButton);
        this.bottomPanel.add(UpdateButton);
        this.bottomPanel.add(changeLogFile);
        this.bottomPanel.add(clearLogFile);
        this.bottomPanel.add(helpButton);
        this.bottomPanel.setBounds(0, 210, 320, 30);
        //  --------------- UNHANDLED ATTRIBUTE: this.bottomPanel.OnResize = bottomPanelResize;
        
        this.LogContentsRichEdit = new JTextArea(10, 10);
        this.LogContentsRichEdit.setBounds(8, 8, 312, 200);
        //  --------------- UNHANDLED ATTRIBUTE: this.LogContentsRichEdit.Name = 'LogContentsRichEdit';
        //  --------------- UNHANDLED ATTRIBUTE: this.LogContentsRichEdit.Cursor = crIBeam;
        
        //  ------- UNHANDLED TYPE TOpenDialog: OpenDialog 
        //  --------------- UNHANDLED ATTRIBUTE: this.OpenDialog.Title = 'Open existing file';
        //  --------------- UNHANDLED ATTRIBUTE: this.OpenDialog.FilterIndex = 0;
        //  --------------- UNHANDLED ATTRIBUTE: this.OpenDialog.top = 188;
        //  --------------- UNHANDLED ATTRIBUTE: this.OpenDialog.Options = [ofOverwritePrompt, ofHideReadOnly];
        //  --------------- UNHANDLED ATTRIBUTE: this.OpenDialog.left = 372;
        
        delphiPanel.add(bottomPanel);
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setBounds(8, 8, 312, 200);;
        scroller1.setViewportView(LogContentsRichEdit);
        delphiPanel.add(scroller1);
        return delphiPanel;
    }
    
        
    public void CopySelectedTextButtonClick(ActionEvent event) {
        System.out.println("CopySelectedTextButtonClick");
    }
        
    public void FormKeyUp(KeyEvent event) {
        System.out.println("FormKeyUp");
    }
        
    public void UpdateButtonClick(ActionEvent event) {
        System.out.println("UpdateButtonClick");
    }
        
    public void changeLogFileClick(ActionEvent event) {
        System.out.println("changeLogFileClick");
    }
        
    public void clearLogFileClick(ActionEvent event) {
        System.out.println("clearLogFileClick");
    }
        
    public void helpButtonClick(ActionEvent event) {
        System.out.println("helpButtonClick");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ChangeLogWindow thisClass = new ChangeLogWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

}
