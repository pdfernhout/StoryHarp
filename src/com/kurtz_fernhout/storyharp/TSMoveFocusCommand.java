package com.kurtz_fernhout.storyharp;

import com.kurtz_fernhout.storyharp.TSVariable.TSVariableState;

public class TSMoveFocusCommand extends TSAbstractMoveFocusCommand {
    
    public TSMoveFocusCommand(TSVariable newFocus) {
		super(newFocus);
	}

    public void doCommand() {
        this.oldFocus.setState(TSVariableState.kAbsent);
        TSDomain.domain.world.focus = this.newFocus;
        this.newFocus.setState(TSVariableState.kPresent);
        this.updateForChanges();
        super.doCommand();
    }
    
    public void undoCommand() {
        this.newFocus.setState(this.newFocusOldState);
        TSDomain.domain.world.focus = this.oldFocus;
        this.oldFocus.setState(this.oldFocusOldState);
        this.updateForChanges();
        super.undoCommand();
    }
    
    public String description() {
        return "move focus to " + this.newFocus.phrase;
    }
    
}