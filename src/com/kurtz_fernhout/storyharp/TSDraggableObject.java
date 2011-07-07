package com.kurtz_fernhout.storyharp;

import java.awt.Point;
import java.awt.Rectangle;

// position is the center position

public class TSDraggableObject {
    public Point position = new Point();
    public Point extent = new Point();
    public boolean selected;
    
    public String displayName() {
        return "Error - override needed";
    }
    
    public void setPosition(String value) {
        String firstNumber = value.substring(0, value.indexOf(","));
        String secondNumber = value.substring(value.indexOf(",") + 1);
        this.position.x = Integer.parseInt(firstNumber);
        this.position.y = Integer.parseInt(secondNumber);
    }
    
    public Rectangle bounds() {
        Point topLeft = new Point(this.position.x - this.extent.x / 2, this.position.y - this.extent.y / 2);
        return new Rectangle(topLeft.x, topLeft.y, this.extent.x, this.extent.y);
    }
    
    public Point center() {
    	// PDF PORT FIX BUG -- THIS SEEMS WRONG; SHOULD BE CALCULATING SOMETHING BASED ON RECTANGLE
    	// Must be that position is the center
        return this.position;
    }
    
}