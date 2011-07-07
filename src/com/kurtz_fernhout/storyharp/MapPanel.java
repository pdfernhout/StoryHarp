package com.kurtz_fernhout.storyharp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class MapPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	// this rectingle should be null when no selectin is in progress
	public Rectangle selectionRectangle;
	
	public boolean isSelectionInProgress() {
		return this.selectionRectangle != null;
	}
	
	public void updateSelectionCorner(Point corner) {
		selectionRectangle.width = corner.x - selectionRectangle.x;
//		if (selectionRectangle.width < 0) {
//			selectionRectangle.width = Math.abs(selectionRectangle.width);
//			//selectionRectangle.x -= selectionRectangle.width;
//			selectionRectangle.x = corner.x;
//		}
//		
		selectionRectangle.height = corner.y - selectionRectangle.y;
//		if (selectionRectangle.height < 0) {
//			selectionRectangle.height = Math.abs(selectionRectangle.height);
//			//selectionRectangle.y -= selectionRectangle.height;
//			selectionRectangle.y = corner.y;
//		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        TSVariableDisplayOptions displayOptions = new TSVariableDisplayOptions();
        
//        if (Application.RuleEditorForm.ListPages.getSelectedComponent() != Application.RuleEditorForm.TabSheetMap) {
//            return;
//        }
        TSMapView mapView = Application.RuleEditorForm.currentGraphView();
        if (mapView == null) {
            return;
        }
        for (int i = 0; i <= 5; i++) {
            displayOptions.displayOptions[i] = false;
        }
        displayOptions.displayOptions[TSVariableDisplayOptions.kRuleContext] = true;
        displayOptions.displayOptions[TSVariableDisplayOptions.kRuleMove] = true;
        displayOptions.displayOptions[TSVariableDisplayOptions.kRuleCommand] = Application.RuleEditorForm.MenuMapsShowCommands.isSelected();
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        //mapView.scroll = new Point(-this.MapScrollBarHorizontal.Position, -this.MapScrollBarVertical.Position);
        mapView.displayOn((Graphics2D) g, displayOptions, Application.RuleEditorForm.lastChoice, Application.RuleEditorForm.previousChoice, Application.RuleEditorForm.rule);
	    if (selectionRectangle != null) {
	    	g.setColor(Color.white);
	    	g.setXORMode(Color.black);
	    	int x = selectionRectangle.x;
	    	int y = selectionRectangle.y;
	    	int width = selectionRectangle.width;
	    	int height = selectionRectangle.height;
	    	if (width < 0) {
	    		width = -width;
	    		x -= width;
	    	}
	    	if (height < 0) {
	    		height = -height;
	    		y -= height;
	    	}
	    	g.drawRect(x, y, width, height);
	    }
	}
}
