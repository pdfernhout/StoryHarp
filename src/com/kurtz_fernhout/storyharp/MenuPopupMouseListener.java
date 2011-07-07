package com.kurtz_fernhout.storyharp;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;


public class MenuPopupMouseListener implements MouseListener {

	JPopupMenu popupMenu;
	
	public MenuPopupMouseListener(JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger()) {
			this.popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
