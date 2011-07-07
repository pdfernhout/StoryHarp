package com.kurtz_fernhout.storyharp;

import java.awt.Point;
import java.util.Vector;

import com.kurtz_fernhout.storyharp.KfCommand.CommandChangedListener;
import com.kurtz_fernhout.storyharp.KfCommand.TrackPhase;

public class KfCommandList {
	static final int kMinMouseMoveDistance = 2;

	// this listener will get copied to each command if it is present
    public CommandChangedListener commandChangedListener;
    
    public Vector<KfCommand> commandList;
    public int lastDoneCommandIndex;
    public int undoLimit;
    public KfCommand mouseCommand;
    public Point anchorPoint;
    public Point previousPoint;
    public boolean rightButtonDown;
    
    public KfCommandList() {
        super();
        this.commandList = new Vector<KfCommand>();
        this.lastDoneCommandIndex = -1;
        this.undoLimit = 100;
    }
    
    public void clear() {
        this.commandList.clear();
        this.lastDoneCommandIndex = -1;
    }
    
    public KfCommand command(int index) {
        return this.commandList.elementAt(index);
    }
    
    public void setNewUndoLimit(int newLimit) {
        this.undoLimit = newLimit;
        this.freeCommandsAboveLimit(this.undoLimit);
    }
    
    //free any command more than the number passed in
    public void freeCommandsAboveLimit(long theLimit) {
        while ((this.commandList.size() > theLimit) && (this.commandList.size() > 0)) {
            this.commandList.remove(0);
            this.lastDoneCommandIndex -= 1;
            if (this.lastDoneCommandIndex < -1) {
                this.lastDoneCommandIndex = -1;
            }
        }
    }
    
    public void doCommand(KfCommand newCommand) {
        //remove any extra commands after the current
        //do this first to free memory for command
        this.clearRedoableCommands();
        //see if too many commands are stored and if so, scroll them
        this.freeCommandsAboveLimit(this.undoLimit - 1);
        if (newCommand.commandChangedListener == null) {
            newCommand.commandChangedListener = this.commandChangedListener;
        }
        //now do this command
        //may fail in which case won't add
        newCommand.doCommand();
        this.lastDoneCommandIndex += 1;
        this.commandList.add(newCommand);
        newCommand.doNotify();
    }
    
    //added nextMouseCommand in these three functions to deal with unhandled exceptions occurring
    //during mouse commands.  This way, the command will not be further processed.
    //This may occasionally leak - the mouse command should be the one responsible for freeing
    //itself and returning nil if a problem occurs
    //returns whether the command finished tracking without freeing itself
    public boolean mouseDown(KfCommand newCommand, Point point) {
        boolean result = false;
        KfCommand nextMouseCommand = null;
        
        result = false;
        if (this.mouseCommand != null) {
            //check if need to clear mouse command
            this.mouseUp(point);
        }
        this.mouseCommand = null;
        if (newCommand != null) {
            //save mouse command and start it
            this.anchorPoint = point;
            this.previousPoint = point;
            nextMouseCommand = newCommand;
            if (newCommand.commandChangedListener == null) {
                newCommand.commandChangedListener = this.commandChangedListener;
            }
            this.mouseCommand = nextMouseCommand.TrackMouse(TrackPhase.trackPress, this.anchorPoint, this.previousPoint, point, false, this.rightButtonDown);
            result = (this.mouseCommand != null);
        }
        return result;
    }
    
    public void mouseMove(Point point) {
        boolean mouseDidMove = false;
        Point pointMovedTo = null;
        KfCommand nextMouseCommand = this.mouseCommand;
        this.mouseCommand = null;
        if (nextMouseCommand != null) {
            mouseDidMove = (Math.abs(this.previousPoint.x - point.x) > kMinMouseMoveDistance) || (Math.abs(this.previousPoint.y - point.y) > kMinMouseMoveDistance);
            if (mouseDidMove) {
                pointMovedTo = point;
            } else {
                pointMovedTo = this.previousPoint;
            }
            this.mouseCommand = nextMouseCommand.TrackMouse(TrackPhase.trackMove, this.anchorPoint, this.previousPoint, pointMovedTo, mouseDidMove, this.rightButtonDown);
        }
        this.previousPoint = pointMovedTo;
    }
    
    public void mouseUp(Point point) {
        boolean mouseDidMove = false;
        KfCommand nextMouseCommand = this.mouseCommand;
        this.mouseCommand = null;
        if (nextMouseCommand != null) {
            mouseDidMove = (Math.abs(point.x - this.anchorPoint.x) > kMinMouseMoveDistance) || (Math.abs(point.y - this.anchorPoint.y) > kMinMouseMoveDistance);
            nextMouseCommand = nextMouseCommand.TrackMouse(TrackPhase.trackRelease, this.anchorPoint, this.previousPoint, point, mouseDidMove, this.rightButtonDown);
            if (nextMouseCommand != null) {
                this.doCommand(nextMouseCommand);
            }
        }
    }
    
    public boolean isUndoEnabled() {
        return this.lastDoneCommandIndex >= 0;
    }
    
    public boolean isRedoEnabled() {
        return this.lastDoneCommandIndex < (this.commandList.size() - 1);
    }
    
    public String undoDescription() {
        String result = "";
        if (this.lastDoneCommandIndex >= 0) {
            result = this.command(this.lastDoneCommandIndex).description();
        } else {
            result = "";
        }
        return result;
    }
    
    public String redoDescription() {
        String result = "";
        if (this.lastDoneCommandIndex < (this.commandList.size() - 1)) {
            result = this.command(this.lastDoneCommandIndex + 1).description();
        } else {
            result = "";
        }
        return result;
    }
    
    public void undoLast() {
        KfCommand aCommand = null;
        if (this.lastDoneCommandIndex >= 0) {
            aCommand = this.command(this.lastDoneCommandIndex);
            aCommand.undoCommand();
            this.lastDoneCommandIndex -= 1;
            aCommand.doNotify();
        }
    }
    
    public void redoLast() {
        KfCommand aCommand = null;
        if (this.lastDoneCommandIndex < (this.commandList.size() - 1)) {
            aCommand = this.command(this.lastDoneCommandIndex + 1);
            aCommand.redoCommand();
            this.lastDoneCommandIndex += 1;
            aCommand.doNotify();
        }
    }
    
    public void removeCommand(KfCommand aCommand) throws GeneralException {
        if (aCommand.done) {
            // assume this command has been undone previously 
            throw new GeneralException("KfCommandList.removeCommand: command not undone");
        }
        this.commandList.remove(aCommand);
    }
    
    public KfCommand lastCommand() {
        KfCommand result = null;
        if (this.lastDoneCommandIndex >= 0) {
            result = this.command(this.lastDoneCommandIndex);
        }
        return result;
    }
    
    public void clearRedoableCommands() {
        long i = 0;
        if (this.isRedoEnabled()) {
            for (i = this.commandList.size() - 1; i > this.lastDoneCommandIndex; i--) {
                this.commandList.remove(i);
            }
        }
    }
    
    //call if change last command
    public void lastCommandChanged() {
        this.clearRedoableCommands();
    }
    
}
