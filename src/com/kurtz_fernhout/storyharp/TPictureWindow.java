package com.kurtz_fernhout.storyharp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class TPictureWindow extends JFrame {
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
    
    public Vector<String> pictureNames = new Vector<String>();  //  @jve:decl-index=0:
    public Vector<String> commands = new Vector<String>();  //  @jve:decl-index=0:
    public Vector<String> replies = new Vector<String>();
    public int selectedPictureIndex = 0;
	private JPanel jPanel1 = null;
    
    public TPictureWindow() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
        
        // PDF PORT FIX: Application.setFormSize(this, TSDomain.domain.options.pictureWindowRect);
        this.numbersLabel.setText("");
    }
    
    private void initialize() {
        this.setSize(new Dimension(558, 411));
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
        delphiPanel.setLayout(new BorderLayout());
        
        Image FirstPictureButtonImage = toolkit.createImage("resources/PictureForm_FirstPictureButton.png");
        this.FirstPictureButton = new SpeedButton(new ImageIcon(FirstPictureButtonImage));
        this.FirstPictureButton.setName("first");
        FirstPictureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                FirstPictureButtonClick(event);
            }
        });
        this.FirstPictureButton.setEnabled(false);
        
        Image PreviousPictureButtonImage = toolkit.createImage("resources/PictureForm_PreviousPictureButton.png");
        this.PreviousPictureButton = new SpeedButton(new ImageIcon(PreviousPictureButtonImage));
        this.PreviousPictureButton.setName("previous");
        PreviousPictureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                PreviousPictureButtonClick(event);
            }
        });
        this.PreviousPictureButton.setEnabled(false);
        
        Image NextPictureButtonImage = toolkit.createImage("resources/PictureForm_NextPictureButton.png");
        this.NextPictureButton = new SpeedButton(new ImageIcon(NextPictureButtonImage));
        this.NextPictureButton.setName("next");
        NextPictureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                NextPictureButtonClick(event);
            }
        });
        this.NextPictureButton.setEnabled(false);
        
        Image LastPictureButtonImage = toolkit.createImage("resources/PictureForm_LastPictureButton.png");
        this.LastPictureButton = new SpeedButton(new ImageIcon(LastPictureButtonImage));
        this.LastPictureButton.setName("last");
        LastPictureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                LastPictureButtonClick(event);
            }
        });
        this.LastPictureButton.setEnabled(false);
        
        this.numbersLabel = new JLabel("0 of 0");
        this.numbersLabel.setName("label");
        
        this.controlsPanel = new JPanel(new FlowLayout());
        // -- this.controlsPanel.setLayout(new BoxLayout(this.controlsPanel, BoxLayout.Y_AXIS));
        this.controlsPanel.add(FirstPictureButton);
        this.controlsPanel.add(PreviousPictureButton);
        this.controlsPanel.add(NextPictureButton);
        this.controlsPanel.add(LastPictureButton);
        this.controlsPanel.add(numbersLabel);
        
        this.PictureImage = new ImagePanel(); // No image was set
        
        this.PictureScrollBox = new JScrollPane();
        this.PictureScrollBox.setViewportView(PictureImage);
        
        delphiPanel.add(PictureScrollBox, BorderLayout.CENTER);
        
        delphiPanel.add(getJPanel1(), BorderLayout.SOUTH);
        
        return delphiPanel;
    }
    
        
    public void FirstPictureButtonClick(ActionEvent event) {
        this.selectedPictureIndex = 0;
        this.loadSelectedPicture();
    }
        
    public void LastPictureButtonClick(ActionEvent event) {
        this.selectedPictureIndex = this.pictureNames.size() - 1;
        this.loadSelectedPicture();
    }
        
    public void NextPictureButtonClick(ActionEvent event) {
        this.selectedPictureIndex = Math.min(this.pictureNames.size() - 1, this.selectedPictureIndex + 1);
        this.loadSelectedPicture();
    }
        
    public void PreviousPictureButtonClick(ActionEvent event) {
        this.selectedPictureIndex = Math.max(0, this.selectedPictureIndex - 1);
        this.loadSelectedPicture();
    }

    /**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel(new BorderLayout());
			
			jPanel1.add(controlsPanel, BorderLayout.NORTH);
			
	        this.CaptionRichEdit = new JTextArea(10, 10);
	        JScrollPane scroller1 = new JScrollPane();
	        scroller1.setPreferredSize(new Dimension(120, 80));
	        scroller1.setViewportView(CaptionRichEdit);
			jPanel1.add(scroller1, BorderLayout.CENTER);
			
			JPanel spacerPanel = new JPanel();
			spacerPanel.setPreferredSize(new Dimension(16, 16));
			jPanel1.add(spacerPanel, BorderLayout.SOUTH);
		}
		return jPanel1;
	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TPictureWindow thisClass = new TPictureWindow();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                String[] strings = new String[] {"data/test1.BMP", "data/test2.BMP", "data/test3.BMP", "data/spacegarden.bmp", "resources/AboutForm_Image1.png"};
                for (int i = 0; i < strings.length; i++) {
                	thisClass.addPictureFromFile(strings[i], "Reply for: " + strings[i]);
                }
                thisClass.setVisible(true);
            }
        });
    }
    
    public void clearPictures() {
        this.pictureNames.clear();
        this.FirstPictureButton.setEnabled(false);
        this.PreviousPictureButton.setEnabled(false);
        this.LastPictureButton.setEnabled(false);
        this.NextPictureButton.setEnabled(false);
        this.numbersLabel.setText("0 of 0");
        this.setVisible(false);
    }
    
    public void updateViews() {
        this.setTitle("StoryHarp Pictures - " + UFileSupport.ExtractFileName(TSDomain.domain.worldFileName));
    }
    
    public void addPictureFromFile(String aFileName, String reply) {
        if (!TSDomain.domain.options.showPictures) {
            return;
        }
        if (this.pictureNames.indexOf(aFileName) < 0) {
            // assumes file existence has been verified by caller (speech system)
            this.pictureNames.add(aFileName);
            //commands.add(command);
            this.replies.add(reply);
            this.selectedPictureIndex = this.pictureNames.size() - 1;
            this.loadSelectedPicture();
        } else {
            this.selectedPictureIndex = this.pictureNames.indexOf(aFileName);
            this.loadSelectedPicture();
        }
    }

    
    public void loadSelectedPicture() {
        String fileName = this.pictureNames.get(this.selectedPictureIndex);
        try {
        	UCursor.cursor_startWait();
            try {
                this.PictureImage.loadFromFileName(fileName);
                this.PictureScrollBox.getViewport().validate();
            } catch (Exception e) {
                UFileSupport.ShowMessage("Picture file " + fileName + " not found or could not load.");
                UCursor.cursor_stopWait();
                return;
            }
        } finally {
        	UCursor.cursor_stopWait();
        }
        
        this.numbersLabel.setText(String.valueOf(this.selectedPictureIndex + 1) + " of " + String.valueOf(this.pictureNames.size()));
        this.CaptionRichEdit.setText(this.replies.get(this.selectedPictureIndex));
        
        this.FirstPictureButton.setEnabled(this.selectedPictureIndex > 0);
        this.PreviousPictureButton.setEnabled(this.FirstPictureButton.isEnabled());
        this.LastPictureButton.setEnabled(this.selectedPictureIndex < this.pictureNames.size() - 1);
        this.NextPictureButton.setEnabled(this.LastPictureButton.isEnabled());
        
        if (!this.isVisible()) {
            this.setVisible(true);
        }
        this.toFront();
    }
    

   
}  //  @jve:decl-index=0:visual-constraint="10,10"
