package com.kurtz_fernhout.storyharp;

import java.awt.Point;

public class KfCommand {
	public enum TrackPhase {trackPress, trackMove, trackRelease }
	public enum KfCommandChangeType {commandDone, commandUndone}
	
	public interface CommandChangedListener {
		public void commandChanged(KfCommand command, KfCommandChangeType changeType);
	}
	
    public CommandChangedListener commandChangedListener;
    public String commandChangeType = "";
    public boolean canUndo;
    public boolean done;
    public boolean commandChangesFile;
    
    public KfCommand() {
        super();
        this.canUndo = true;
        this.done = false;
        // default commandChangesFile to true, since most commands change file,
        // if command does not change file, set to false after call to inherited constructor 
        this.commandChangesFile = true;
    }
    
    public void doCommand() {
        this.done = true;
        //subclass should override and call inherited
    }
    
    public void undoCommand() {
        this.done = false;
        //subclass should override and call inherited
    }
    
    public void redoCommand() {
        this.doCommand();
        //subclass may override and call inherited doCommand
        //could call inherited redo, but then watch out that do will be done too!
    }
    
    public String description() {
        return "*command description*";
    }
    
    public KfCommand TrackMouse(TrackPhase aTrackPhase, Point anchorPoint, Point previousPoint, Point nextPoint, boolean mouseDidMove, boolean rightButtonDown) {
        //subclasses should override if needed
        return this;
    }
    
    // doNotify cannot be done within the do, undo, redo of command because
    // the command list will not have finished updating itself.
    // It is called by the command list instead -- unless it is a mouse command.
    public void doNotify() {
        if (commandChangedListener != null) {
            if (this.done) {
                // commandChangesFile and
                this.commandChangedListener.commandChanged(this, KfCommandChangeType.commandDone);
            } else {
                this.commandChangedListener.commandChanged(this, KfCommandChangeType.commandUndone);
            }
        }
    }
    
}