package com.kurtz_fernhout.storyharp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

public class GenericCutCopyPastePopupMenu extends MouseAdapter implements ActionListener {
	JPopupMenu popup;
	private JMenuItem cutMenuItem;
	private JMenuItem copyMenuItem;
	private JMenuItem pasteMenuItem;
	JTextComponent textComponent;
	
	GenericCutCopyPastePopupMenu(JTextComponent textComponent) {
		this.textComponent = textComponent;
	    //Create the popup menu.
	    popup = new JPopupMenu();
	    cutMenuItem = new JMenuItem("cut");
	    cutMenuItem.addActionListener(this);
	    popup.add(cutMenuItem);
	    copyMenuItem = new JMenuItem("copy");
	    copyMenuItem.addActionListener(this);
	    popup.add(copyMenuItem);
	    pasteMenuItem = new JMenuItem("paste");
	    pasteMenuItem.addActionListener(this);
	    popup.add(pasteMenuItem);
	    
		textComponent.addMouseListener(this);
	}
	
    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cutMenuItem) {
			textComponent.cut();
		} else if (e.getSource() == copyMenuItem) {
			textComponent.copy();
		} else if (e.getSource() == pasteMenuItem) {
			textComponent.paste();
		} else {
			System.out.println("unexpected action in GenericCutCopyPastePopupMenu");
		}
	}

}
