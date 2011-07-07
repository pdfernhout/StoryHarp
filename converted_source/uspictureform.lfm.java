
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
// import common.*;

public class PictureWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    JPanel controlsPanel;
    SpeedButton FirstPictureButton;
    SpeedButton PreviousPictureButton;
    SpeedButton NextPictureButton;
    SpeedButton LastPictureButton;
    JLabel numbersLabel;
    JScrollPane PictureScrollBox;
    ImagePanel PictureImage;
    JTextArea CaptionRichEdit;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
    
    public PictureWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(705, 325));
        this.setContentPane(getMainContentPane());
        this.setTitle("StoryHarp Pictures");
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
        this.setBounds(1837, 304, 249, 282  + 80); // extra for title bar and menu
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnDestroy = FormDestroy;
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnCreate = FormCreate;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnResize = FormResize;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 100;
        
        
        Image FirstPictureButtonImage = toolkit.createImage("../resources/PictureForm_FirstPictureButton.png");
        this.FirstPictureButton = new SpeedButton(new ImageIcon(FirstPictureButtonImage));
        this.FirstPictureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                FirstPictureButtonClick(event);
            }
        });
        this.FirstPictureButton.setBounds(2, 2, 25, 25);
        this.FirstPictureButton.setEnabled(false);
        
        Image PreviousPictureButtonImage = toolkit.createImage("../resources/PictureForm_PreviousPictureButton.png");
        this.PreviousPictureButton = new SpeedButton(new ImageIcon(PreviousPictureButtonImage));
        this.PreviousPictureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                PreviousPictureButtonClick(event);
            }
        });
        this.PreviousPictureButton.setBounds(30, 2, 25, 25);
        this.PreviousPictureButton.setEnabled(false);
        
        Image NextPictureButtonImage = toolkit.createImage("../resources/PictureForm_NextPictureButton.png");
        this.NextPictureButton = new SpeedButton(new ImageIcon(NextPictureButtonImage));
        this.NextPictureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                NextPictureButtonClick(event);
            }
        });
        this.NextPictureButton.setBounds(58, 2, 25, 25);
        this.NextPictureButton.setEnabled(false);
        
        Image LastPictureButtonImage = toolkit.createImage("../resources/PictureForm_LastPictureButton.png");
        this.LastPictureButton = new SpeedButton(new ImageIcon(LastPictureButtonImage));
        this.LastPictureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                LastPictureButtonClick(event);
            }
        });
        this.LastPictureButton.setBounds(86, 2, 25, 25);
        this.LastPictureButton.setEnabled(false);
        
        this.numbersLabel = new JLabel("3 of 4");
        this.numbersLabel.setBounds(116, 8, 43, 17);
        
        this.controlsPanel = new JPanel(null);
        // -- this.controlsPanel.setLayout(new BoxLayout(this.controlsPanel, BoxLayout.Y_AXIS));
        this.controlsPanel.add(FirstPictureButton);
        this.controlsPanel.add(PreviousPictureButton);
        this.controlsPanel.add(NextPictureButton);
        this.controlsPanel.add(LastPictureButton);
        this.controlsPanel.add(numbersLabel);
        this.controlsPanel.setBounds(0, 216, 237, 29);
        
        this.PictureImage = new ImagePanel(); // No image was set
        this.PictureImage.setBounds(0, 0, 229, 209);
        
        this.PictureScrollBox = new JScrollPane(this.PictureImage);
        this.PictureScrollBox.setBounds(0, 0, 237, 217);
        
        this.CaptionRichEdit = new JTextArea(10, 10);
        this.CaptionRichEdit.setBounds(-32, 248, 200, 150);
        //  --------------- UNHANDLED ATTRIBUTE: this.CaptionRichEdit.Name = 'CaptionRichEdit';
        //  --------------- UNHANDLED ATTRIBUTE: this.CaptionRichEdit.Cursor = crIBeam;
        
        delphiPanel.add(controlsPanel);
        delphiPanel.add(PictureScrollBox);
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setBounds(-32, 248, 200, 150);;
        scroller1.setViewportView(CaptionRichEdit);
        delphiPanel.add(scroller1);
        return delphiPanel;
    }
    
        
    public void FirstPictureButtonClick(ActionEvent event) {
        System.out.println("FirstPictureButtonClick");
    }
        
    public void LastPictureButtonClick(ActionEvent event) {
        System.out.println("LastPictureButtonClick");
    }
        
    public void NextPictureButtonClick(ActionEvent event) {
        System.out.println("NextPictureButtonClick");
    }
        
    public void PreviousPictureButtonClick(ActionEvent event) {
        System.out.println("PreviousPictureButtonClick");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PictureWindow thisClass = new PictureWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

}
