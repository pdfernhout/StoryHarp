package com.kurtz_fernhout.storyharp;

import java.awt.Point;

public class TSDragRecord {
    public TSDraggableObject draggedNode;
    public Point originalLocation;
    public Point newLocation;
    
    public TSDragRecord(TSDraggableObject node) {
        this.draggedNode = node;
        this.originalLocation = this.draggedNode.position;
        this.newLocation = this.originalLocation;
    }
    
    public void doDrag() {
        this.draggedNode.position = this.newLocation;
    }
    
    public void undoDrag() {
        this.draggedNode.position = this.originalLocation;
    }
    
    public void offset(Point delta) {
        this.newLocation = new Point(this.newLocation.x + delta.x, this.newLocation.y + delta.y);
        this.draggedNode.position = this.newLocation;
    }
    
}
