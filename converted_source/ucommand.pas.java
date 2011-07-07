// unit Ucommand

from conversion_common import *;
import delphi_compatability;

// FIX enumerated type TrackPhase
class TrackPhase:
    trackPress, trackMove, trackRelease = range(3)


// FIX enumerated type KfCommandChangeType
class KfCommandChangeType:
    commandDone, commandUndone = range(2)


// const
kMinMouseMoveDistance = 2;



class KfCommand extends TObject {
    public TCommandEvent notifyProcedure;
    public boolean canUndo;
    public boolean done;
    public boolean commandChangesFile;
    
    //KfCommand
    public void create() {
        super.create();
        this.canUndo = true;
        this.done = false;
        // default commandChangesPlantFile to true, since most commands change file,
        //    if command does not change file, set to false after call to inherited create 
        this.commandChangesFile = true;
    }
    
    public void destroy() {
        //sublass could override
        super.destroy();
    }
    
    public void doCommand() {
        this.done = true;
        //subclass should override and call inherited
    }
    
    public void undoCommand() {
        this.done = false;
        //sublass should override and call inherited
    }
    
    public void redoCommand() {
        this.doCommand();
        //sublass may override and call inherited doCommand
        //could call inherited redo, but then watch out that do will be done too!
    }
    
    public String description() {
        result = "";
        result = "*command description*";
        return result;
    }
    
    public KfCommand TrackMouse(TrackPhase aTrackPhase, TPoint anchorPoint, TPoint previousPoint, TPoint nextPoint, boolean mouseDidMove, boolean rightButtonDown) {
        result = new KfCommand();
        //sublasses should override if needed
        result = this;
        return result;
    }
    
    // notify cannot be done within the do, undo, redo of command because
    // command list will not have finished updating itself
    public void doNotify() {
        if (delphi_compatability.Assigned(this.notifyProcedure)) {
            if (this.done) {
                // commandChangesFile and
                this.notifyProcedure(this, KfCommandChangeType.commandDone);
            } else {
                this.notifyProcedure(this, KfCommandChangeType.commandUndone);
            }
        }
    }
    
}
class KfCommandList extends TObject {
    public TCommandEvent notifyProcedure;
    public TList commandList;
    public long lastDoneCommandIndex;
    public long undoLimit;
    public KfCommand mouseCommand;
    public TPoint anchorPoint;
    public TPoint previousPoint;
    public boolean rightButtonDown;
    
    //KfCommandList
    public void create() {
        super.create();
        this.commandList = delphi_compatability.TList().Create();
        this.lastDoneCommandIndex = -1;
        this.undoLimit = 100;
    }
    
    public void clear() {
        int i = 0;
        
        if (this.commandList.Count > 0) {
            for (i = 0; i <= this.commandList.Count - 1; i++) {
                UNRESOLVED.TObject(this.commandList[i]).free;
            }
        }
        this.commandList.Clear();
        this.lastDoneCommandIndex = -1;
    }
    
    public void destroy() {
        this.clear();
        this.commandList.free;
        //if mouseCommand <> nil then error condition - ignoring for now - not released
        //could only happend if quitting somehow in middle of action
        super.destroy();
    }
    
    public KfCommand command(long index) {
        result = new KfCommand();
        result = KfCommand(this.commandList.Items[index]);
        return result;
    }
    
    public void setNewUndoLimit(long newLimit) {
        this.undoLimit = newLimit;
        this.freeCommandsAboveLimit(this.undoLimit);
    }
    
    //free any command more than the number passed in
    public void freeCommandsAboveLimit(long theLimit) {
        KfCommand theCommand = new KfCommand();
        
        while ((this.commandList.Count > theLimit) && (this.commandList.Count > 0)) {
            theCommand = this.command(0);
            this.commandList.Delete(0);
            theCommand.free;
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
        if (!delphi_compatability.Assigned(newCommand.notifyProcedure)) {
            //now do this command
            newCommand.notifyProcedure = this.notifyProcedure;
        }
        //may fail in which case won't add
        newCommand.doCommand();
        this.lastDoneCommandIndex += 1;
        this.commandList.Add(newCommand);
        newCommand.doNotify();
    }
    
    //added nextMouseCommand in these three functions to deal with unhandled exceptions occurring
    //during mouse commands.  This way, the command will not be further processed.
    //This may occasionally leak - the mouse command should be the one responsible for freeing
    //itself and returning nil if a problem occurs
    //returns whether the command finished tracking without freeing itself
    public boolean mouseDown(KfCommand newCommand, Tpoint point) {
        result = false;
        KfCommand nextMouseCommand = new KfCommand();
        
        result = false;
        if (this.mouseCommand != null) {
            //check if need to clear mouse command
            this.mouseUp(Point);
        }
        this.mouseCommand = null;
        if (newCommand != null) {
            //save mouse command and start it
            this.anchorPoint = Point;
            this.previousPoint = Point;
            nextMouseCommand = newCommand;
            if (!delphi_compatability.Assigned(newCommand.notifyProcedure)) {
                newCommand.notifyProcedure = this.notifyProcedure;
            }
            this.mouseCommand = nextMouseCommand.TrackMouse(TrackPhase.trackPress, this.anchorPoint, this.previousPoint, Point, false, this.rightButtonDown);
            result = (this.mouseCommand != null);
        }
        return result;
    }
    
    public void mouseMove(TPoint point) {
        boolean mouseDidMove = false;
        KfCommand nextMouseCommand = new KfCommand();
        TPoint pointMovedTo = new TPoint();
        
        nextMouseCommand = this.mouseCommand;
        this.mouseCommand = null;
        if (nextMouseCommand != null) {
            mouseDidMove = (abs(this.previousPoint.X - Point.x) > kMinMouseMoveDistance) || (abs(this.previousPoint.Y - Point.y) > kMinMouseMoveDistance);
            if (mouseDidMove) {
                pointMovedTo = Point;
            } else {
                pointMovedTo = this.previousPoint;
            }
            this.mouseCommand = nextMouseCommand.TrackMouse(TrackPhase.trackMove, this.anchorPoint, this.previousPoint, pointMovedTo, mouseDidMove, this.rightButtonDown);
        }
        this.previousPoint = pointMovedTo;
    }
    
    public void mouseUp(TPoint point) {
        boolean mouseDidMove = false;
        KfCommand nextMouseCommand = new KfCommand();
        TPoint pointMovedTo = new TPoint();
        
        nextMouseCommand = this.mouseCommand;
        this.mouseCommand = null;
        if (nextMouseCommand != null) {
            mouseDidMove = (abs(Point.x - this.anchorPoint.X) > kMinMouseMoveDistance) || (abs(Point.y - this.anchorPoint.Y) > kMinMouseMoveDistance);
            if (mouseDidMove) {
                pointMovedTo = Point;
            } else {
                pointMovedTo = this.anchorPoint;
            }
            nextMouseCommand = nextMouseCommand.TrackMouse(TrackPhase.trackRelease, this.anchorPoint, this.previousPoint, Point, mouseDidMove, this.rightButtonDown);
            if (nextMouseCommand != null) {
                this.doCommand(nextMouseCommand);
            }
        }
    }
    
    public boolean isUndoEnabled() {
        result = false;
        result = this.lastDoneCommandIndex >= 0;
        return result;
    }
    
    public boolean isRedoEnabled() {
        result = false;
        result = this.lastDoneCommandIndex < (this.commandList.Count - 1);
        return result;
    }
    
    public String undoDescription() {
        result = "";
        if (this.lastDoneCommandIndex >= 0) {
            result = this.command(this.lastDoneCommandIndex).description();
        } else {
            result = "";
        }
        return result;
    }
    
    public String redoDescription() {
        result = "";
        if (this.lastDoneCommandIndex < (this.commandList.Count - 1)) {
            result = this.command(this.lastDoneCommandIndex + 1).description();
        } else {
            result = "";
        }
        return result;
    }
    
    public void undoLast() {
        KfCommand aCommand = new KfCommand();
        
        if (this.lastDoneCommandIndex >= 0) {
            aCommand = this.command(this.lastDoneCommandIndex);
            aCommand.undoCommand();
            this.lastDoneCommandIndex -= 1;
            aCommand.doNotify();
        }
    }
    
    public void redoLast() {
        KfCommand aCommand = new KfCommand();
        
        if (this.lastDoneCommandIndex < (this.commandList.Count - 1)) {
            aCommand = this.command(this.lastDoneCommandIndex + 1);
            aCommand.redoCommand();
            this.lastDoneCommandIndex += 1;
            aCommand.doNotify();
        }
    }
    
    public void removeCommand(KfCommand aCommand) {
        if (aCommand.done) {
            // assume this command has been undone previously 
            throw new GeneralException.create("KfCommandList.removeCommand: command not undone");
        }
        this.commandList.Remove(aCommand);
    }
    
    public KfCommand lastCommand() {
        result = new KfCommand();
        result = null;
        if (this.lastDoneCommandIndex >= 0) {
            result = this.command(this.lastDoneCommandIndex);
        }
        return result;
    }
    
    public void clearRedoableCommands() {
        long i = 0;
        KfCommand theCommand = new KfCommand();
        
        if (this.isRedoEnabled()) {
            for (i = this.commandList.Count - 1; i >= this.lastDoneCommandIndex + 1; i--) {
                theCommand = this.command(i);
                this.commandList.Delete(i);
                theCommand.free;
            }
        }
    }
    
    //call if change last command
    public void lastCommandChanged() {
        this.clearRedoableCommands();
    }
    
}
