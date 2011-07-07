package com.kurtz_fernhout.storyharp;

import java.awt.Point;
import java.util.Vector;

public class TSMapDragCommand extends KfCommand {
    public Vector<TSDragRecord> dragRecords;
    
    public TSMapDragCommand() {
        this.dragRecords = new Vector<TSDragRecord>();
        TSDomain.domain.world.addDragRecordsToList(this.dragRecords);
    }
    
    public void doCommand() {
        super.doCommand();
        for (int i = 0; i < this.dragRecords.size(); i++) {
            this.dragRecords.get(i).doDrag();
        }
        this.doNotify();
        super.doCommand();
    }
    
    public void undoCommand() {
        super.undoCommand();
        TSDomain.domain.world.deselectAllExcept(null);
        for (int i = 0; i < this.dragRecords.size(); i++) {
            this.dragRecords.get(i).draggedNode.selected = true;
            this.dragRecords.get(i).undoDrag();
        }
        this.doNotify();
        super.undoCommand();
    }
    
    public void redoCommand() {
    	// needs to be do and not redo
        super.doCommand();
        TSDomain.domain.world.deselectAllExcept(null);
        for (int i = 0; i < this.dragRecords.size(); i++) {
            this.dragRecords.get(i).draggedNode.selected = true;
            this.dragRecords.get(i).doDrag();
        }
        this.doNotify();
        super.doCommand();
    }
    
    public String description() {
        String result = "";
        if (this.dragRecords.size() > 1) {
            result = "Drag nodes";
        } else if (this.dragRecords.size() == 1) {
            result = "Drag " + this.dragRecords.get(0).draggedNode.displayName();
        } else {
            result = "Drag";
        }
        return result;
    }
    
    public KfCommand TrackMouse(TrackPhase aTrackPhase, Point anchorPoint, Point previousPoint, Point nextPoint, boolean mouseDidMove, boolean rightButtonDown) {
        KfCommand result = this;
        if (aTrackPhase == TrackPhase.trackPress) {
            if (this.dragRecords.size() == 0) {
                return null;
            }
        } else if (aTrackPhase == TrackPhase.trackMove) {
            if (mouseDidMove) {
                Point delta = new Point(nextPoint.x - previousPoint.x, nextPoint.y - previousPoint.y);
                for (int i = 0; i < this.dragRecords.size(); i++) {
                    this.dragRecords.get(i).offset(delta);
                }
                this.doNotify();
            }
        } else if (aTrackPhase == TrackPhase.trackRelease) {
            if (!mouseDidMove) {
                if ((this.dragRecords.get(0).draggedNode.position.x != this.dragRecords.get(0).originalLocation.x) || (this.dragRecords.get(0).draggedNode.position.y != this.dragRecords.get(0).originalLocation.y)) {
                    for (int i = 0; i < this.dragRecords.size(); i++) {
                        this.dragRecords.get(i).undoDrag();
                    }
                    this.doNotify();
                }
                result = null;
            } else {
                Point delta = new Point(nextPoint.x - previousPoint.x, nextPoint.y - previousPoint.y);
                if ((delta.x != 0) || (delta.y != 0)) {
                    for (int i = 0; i < this.dragRecords.size(); i++) {
                        this.dragRecords.get(i).offset(delta);
                    }
                    this.doNotify();
                }
            }
        } else {
        	System.out.println("unexpected track phase: " + aTrackPhase);
        }
        return result;
    }
    
}
