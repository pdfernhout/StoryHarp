
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// import common.*;

public class ExtraMediaDirectoryWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    SpeedButton helpButton;
    JLabel Label1;
    JLabel Label2;
    JLabel Label3;
    JLabel Label4;
    JLabel Label5;
    JLabel Label6;
    JButton Close;
    JButton cancel;
    JTextField extraMediaDirectoryEdit;
    JTextField openWorldFileDirectory;
    JTextField exeDirectory;
    JTextField windowsMediaDirectory;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
    
    public ExtraMediaDirectoryWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 325));
        this.setContentPane(getMainContentPane());
        this.setTitle("Extra Sound and Music Directory");
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
        this.setBounds(441, 140, 439, 241  + 80); // extra for title bar and menu
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 96;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnActivate = FormActivate;
        //  --------------- UNHANDLED ATTRIBUTE: this.AutoScroll = False;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        
        
        this.helpButton = new SpeedButton("Help");
        this.helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                helpButtonClick(event);
            }
        });
        this.helpButton.setMnemonic(KeyEvent.VK_H);
        this.helpButton.setBounds(376, 63, 60, 21);
        
        this.Label1 = new JLabel("To find a sound or music file, I will look in these places.");
        this.Label1.setBounds(8, 8, 264, 14);
        
        this.Label2 = new JLabel("First, in the directory the open world is in, which is");
        this.Label2.setBounds(27, 30, 244, 14);
        
        this.Label3 = new JLabel("Second, in the Extra Sound and Music Directory you specify here:");
        this.Label3.setBounds(27, 75, 318, 14);
        
        this.Label4 = new JLabel("Third, in the directory where the program is, which is");
        this.Label4.setBounds(27, 121, 256, 14);
        
        this.Label5 = new JLabel("And finally in your Windows media directory, which is");
        this.Label5.setBounds(27, 172, 260, 14);
        
        this.Label6 = new JLabel("For each directory shown here, I will also look in all directories under it.");
        this.Label6.setBounds(8, 220, 343, 14);
        
        this.Close = new JButton("OK");
        this.Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                CloseClick(event);
            }
        });
        this.getRootPane().setDefaultButton(this.Close);
        this.Close.setBounds(376, 4, 60, 21);
        
        this.cancel = new JButton("Cancel");
        this.cancel.setBounds(376, 27, 60, 21);
        //  --------------- UNHANDLED ATTRIBUTE: this.cancel.ModalResult = 2;
        //  --------------- UNHANDLED ATTRIBUTE: this.cancel.Cancel = True;
        
        this.extraMediaDirectoryEdit = new JTextField("");
        this.extraMediaDirectoryEdit.setBounds(47, 92, 320, 22);
        
        this.openWorldFileDirectory = new JTextField("");
        this.openWorldFileDirectory.setEditable(false);
        this.openWorldFileDirectory.setBounds(47, 47, 320, 22);
        
        this.exeDirectory = new JTextField("");
        this.exeDirectory.setEditable(false);
        this.exeDirectory.setBounds(47, 141, 320, 22);
        
        this.windowsMediaDirectory = new JTextField("");
        this.windowsMediaDirectory.setEditable(false);
        this.windowsMediaDirectory.setBounds(47, 190, 320, 22);
        
        delphiPanel.add(helpButton);
        delphiPanel.add(Label1);
        delphiPanel.add(Label2);
        delphiPanel.add(Label3);
        delphiPanel.add(Label4);
        delphiPanel.add(Label5);
        delphiPanel.add(Label6);
        delphiPanel.add(Close);
        delphiPanel.add(cancel);
        delphiPanel.add(extraMediaDirectoryEdit);
        delphiPanel.add(openWorldFileDirectory);
        delphiPanel.add(exeDirectory);
        delphiPanel.add(windowsMediaDirectory);
        return delphiPanel;
    }
    
        
    public void CloseClick(ActionEvent event) {
        System.out.println("CloseClick");
    }
        
    public void helpButtonClick(ActionEvent event) {
        System.out.println("helpButtonClick");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ExtraMediaDirectoryWindow thisClass = new ExtraMediaDirectoryWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

}
