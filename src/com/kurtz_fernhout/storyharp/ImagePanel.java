package com.kurtz_fernhout.storyharp;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ImagePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    Icon imageIcon;
    
    public ImagePanel() {
    }
    
    public ImagePanel(ImageIcon imageIcon) {
    	//System.out.println("imageicon: " + imageIcon);
    	//System.out.println("imageicon image: " + imageIcon.getImage());
    	this.imageIcon = imageIcon;
    	this.updateSize();
    }
    
    public void loadFromFileName(String fileName) {
    	// System.out.println("Loading file name: " + fileName);
    	File file = new File(fileName);
    	// if (file.exists()) System.out.println("Exists");
    	BufferedImage bufferedImage = null;
    	try {
			bufferedImage = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			imageIcon = new ImageIcon();
			return;
		}
    	imageIcon = new ImageIcon(bufferedImage);
    	this.updateSize();
    	// checkImageLoadedStatus(fileName);
    	this.repaint();
    }
    
    public void updateSize() {
    	Dimension dimension = null;
    	
    	if (imageIcon != null) {
    		dimension = new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
    	} else {
    		dimension = new Dimension(0, 0);
    	}
		this.setSize(dimension);
		this.setPreferredSize(dimension);
    }

	@SuppressWarnings("unused")
	private void checkImageLoadedStatus(String fileName) {
		// status check from: http://forums.sun.com/thread.jspa?forumID=20&threadID=734531
    	int status = ((ImageIcon) imageIcon).getImageLoadStatus();
        String s;
        switch(status)
        {
            case MediaTracker.ABORTED:
                s = "ABORTED";
                break;
            case MediaTracker.ERRORED:
                s = "ERRORED";
                break;
            case MediaTracker.COMPLETE:
                s = "COMPLETE";
                break;
            default:
                s = "unexpected status: " + status;
        }
        System.out.println("image load status for " + "\"" + fileName + "\" = " + s);
	}
    
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (imageIcon != null) {
			imageIcon.paintIcon(this, g, 0, 0);
		}
	}
}