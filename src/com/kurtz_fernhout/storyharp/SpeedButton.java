package com.kurtz_fernhout.storyharp;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

class SpeedButton extends JToggleButton {
    private static final long serialVersionUID = 1L;
    
    public SpeedButton(ImageIcon imageIcon) {
    	super(imageIcon);
    }
    
    public SpeedButton(String label, ImageIcon imageIcon) {
    	super(label, imageIcon);
    }
    
    public SpeedButton(String label) {
    	super(label);
    }
}