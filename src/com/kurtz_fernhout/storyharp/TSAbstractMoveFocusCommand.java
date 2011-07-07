package com.kurtz_fernhout.storyharp;

import com.kurtz_fernhout.storyharp.TSVariable.TSVariableState;

// need to have abstract base so TSDoCommandPhrase can defer updating till after changes
public class TSAbstractMoveFocusCommand extends KfCommand {
    public TSVariable oldFocus;
    public TSVariableState oldFocusOldState;
    public TSVariable newFocus;
    public TSVariableState newFocusOldState;
    
    // ----------------------------- TSAbstractMoveFocusCommand --------------------- 
    public TSAbstractMoveFocusCommand(TSVariable newFocus) {
        super();
        setNewFocus(newFocus);
    }

	public void setNewFocus(TSVariable newFocus) {
		if (newFocus == null) return;
		// the old states are stored for undo in case author has been toggling them individually
        this.newFocus = newFocus;
        this.newFocusOldState = newFocus.getState();
        if (TSDomain.domain.world.focus != null) {
            this.oldFocus = TSDomain.domain.world.focus;
            this.oldFocusOldState = this.oldFocus.getState();
        } else {
            this.oldFocus = newFocus;
            this.oldFocusOldState = newFocus.getState();
        }
	}
    
    public void updateForChanges() {
        TSDomain.domain.world.updateAvailable();
        Application.ConsoleForm.speechSystem.listenForAvailableCommands();
        Application.ConsoleForm.updateVariables();
        Application.ConsoleForm.VariablesListBox.repaint();
    }
    
    public boolean shiftsFocus() {
        return (this.newFocus != TSDomain.domain.world.emptyEntry) && (this.newFocus != this.oldFocus);
    }
    
}