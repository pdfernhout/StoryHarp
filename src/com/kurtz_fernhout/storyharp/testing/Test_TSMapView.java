package com.kurtz_fernhout.storyharp.testing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import com.kurtz_fernhout.storyharp.TSDomain;
import com.kurtz_fernhout.storyharp.TSMapView;
import com.kurtz_fernhout.storyharp.TSVariableDisplayOptions;

public class Test_TSMapView extends JPanel {

	private static final long serialVersionUID = 1L;
	TSMapView mapView = new TSMapView();

	public Test_TSMapView() {
		super();
		initialize();
		//TSDomain.domain.loadWorld("Astronomy Test.wld");
		TSDomain.domain.loadWorld("data/House and Yard.wld");
	}

	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		TSVariableDisplayOptions displayOptions = new TSVariableDisplayOptions();
		displayOptions.displayOptions[0] = true;
		displayOptions.displayOptions[1] = true;
		displayOptions.displayOptions[2] = true;
		displayOptions.displayOptions[3] = true;
		displayOptions.displayOptions[4] = true;
		displayOptions.displayOptions[5] = true;
		mapView.displayOn((Graphics2D) g, displayOptions, null, null, null);
		// To allow some fiddiling to move it over; resize the window to scroll
		mapView.scroll.x += 1;
	}

}
