package com.kurtz_fernhout.storyharp;

import java.awt.Color;
import java.util.Vector;

import com.kurtz_fernhout.storyharp.TSVariable.TSVariableState;

public class TSDoCommandPhrase extends TSAbstractMoveFocusCommand {
    public String commandPhrase;
    public Vector<TSChangedVariableWrapper> changedVariables;
    public String oldLastSaidTextWithMacros;
    public String newLastSaidTextWithMacros;
    public int oldFirstCommandDoneForLastCommandPhrase;
    public int newFirstCommandDoneForLastCommandPhrase;
    
    // ----------------------------- TSDoCommandPhrase -------------------------------
    public TSDoCommandPhrase(String commandPhrase) {
    	super(null);
    	System.out.println("making command TSDoCommandPhrase");
        this.commandPhrase = commandPhrase;
        this.changedVariables = new Vector<TSChangedVariableWrapper>();
        // determine what would need to change - including new focus and all variables
        this.newFocus = TSDomain.domain.world.emptyEntry;
        this.oldLastSaidTextWithMacros = Application.ConsoleForm.speechSystem.lastSaidTextWithMacros;
        this.newLastSaidTextWithMacros = "";
        this.oldFirstCommandDoneForLastCommandPhrase = TSDomain.domain.world.firstCommandDoneForLastCommandPhrase;
        this.newFirstCommandDoneForLastCommandPhrase = -1;
        if (TSDomain.domain.world.rules.size() > 0) {
            for (int i = 0; i < TSDomain.domain.world.rules.size(); i++) {
            	TSRule rule = TSDomain.domain.world.rules.get(i);
                if (rule.available && (rule.command.phrase.equalsIgnoreCase(commandPhrase))) {
                    Application.RuleEditorForm.lastCommand = rule;
                    if (this.newFirstCommandDoneForLastCommandPhrase == -1) {
                        this.newFirstCommandDoneForLastCommandPhrase = i;
                    }
                    // Kludge to deal with variable arg used under Delphi for multivalued return
                    TSVariableHolder holder = new TSVariableHolder();
                    holder.variable = this.newFocus;
                    this.newLastSaidTextWithMacros = rule.recordReplyMoveChanges(this.changedVariables, this.newLastSaidTextWithMacros, holder);
                    this.newFocus = holder.variable;
                }
            }
        }
        if ((this.commandPhrase.length() > 1) && (this.commandPhrase.startsWith("$"))) {
            // Eliminate leading $
            this.commandPhrase = this.commandPhrase.substring(1);
        }
        this.setNewFocus(this.newFocus);
    }
    
    public void doCommand() {
        Application.ConsoleForm.addLineToTranscript("> " + this.commandPhrase, Color.red);
        Application.ConsoleForm.addLineToTranscript(Application.ConsoleForm.speechSystem.stripMacros(this.newLastSaidTextWithMacros), Color.blue);
        Application.ConsoleForm.scrollTranscriptEndIntoView();
        Application.ConsoleForm.speechSystem.sayTextWithMacros(this.newLastSaidTextWithMacros);
        //common with undo
        Application.ConsoleForm.speechSystem.lastSaidTextWithMacros = this.newLastSaidTextWithMacros;
        TSDomain.domain.world.firstCommandDoneForLastCommandPhrase = this.newFirstCommandDoneForLastCommandPhrase;
        if (this.newFocus != TSDomain.domain.world.emptyEntry) {
            this.oldFocus.setState(TSVariableState.kAbsent);
            TSDomain.domain.world.focus = this.newFocus;
            this.newFocus.setState(TSVariableState.kPresent);
        }
        for (int i = 0; i < this.changedVariables.size(); i++) {
            this.changedVariables.get(i).doChange();
        }
        this.updateForChanges();
        Application.ConsoleForm.speechSystem.checkForSayOptionsMacro();
        super.doCommand();
    }
    
    public void undoCommand() {
        String undoPhrase = "";
        
        Application.ConsoleForm.addLineToTranscript("> undo", Color.red);
        //  undoPhrase := 'It is as if "' + commandPhrase + '" had never been said.';
        undoPhrase = "(You decide not to say \"" + this.commandPhrase + "\")";
        Application.ConsoleForm.addLineToTranscript(undoPhrase, Color.blue);
        Application.ConsoleForm.scrollTranscriptEndIntoView();
        Application.ConsoleForm.speechSystem.speakText(undoPhrase);
        Application.ConsoleForm.speechSystem.lastSaidTextWithMacros = this.oldLastSaidTextWithMacros;
        TSDomain.domain.world.firstCommandDoneForLastCommandPhrase = this.oldFirstCommandDoneForLastCommandPhrase;
        for (int i = this.changedVariables.size() - 1; i >= 0; i--) {
            this.changedVariables.get(i).undoChange();
        }
        if (this.newFocus != TSDomain.domain.world.emptyEntry) {
            this.newFocus.setState(this.newFocusOldState);
            TSDomain.domain.world.focus = this.oldFocus;
            this.oldFocus.setState(this.oldFocusOldState);
        }
        this.updateForChanges();
        super.undoCommand();
    }
    
    public void redoCommand() {
        String redoPhrase = "";
        
        Application.ConsoleForm.addLineToTranscript("> redo", Color.red);
        redoPhrase = "(You decide to say \"" + this.commandPhrase + "\" anyway)";
        Application.ConsoleForm.addLineToTranscript(redoPhrase, Color.blue);
        Application.ConsoleForm.scrollTranscriptEndIntoView();
        Application.ConsoleForm.speechSystem.speakText(redoPhrase);
        //common with do
        Application.ConsoleForm.speechSystem.lastSaidTextWithMacros = this.newLastSaidTextWithMacros;
        TSDomain.domain.world.firstCommandDoneForLastCommandPhrase = this.newFirstCommandDoneForLastCommandPhrase;
        if (this.newFocus != TSDomain.domain.world.emptyEntry) {
            this.oldFocus.setState(TSVariableState.kAbsent);
            TSDomain.domain.world.focus = this.newFocus;
            this.newFocus.setState(TSVariableState.kPresent);
        }
        for (int i = 0; i < this.changedVariables.size(); i++) {
            this.changedVariables.get(i).doChange();
        }
        this.updateForChanges();
        super.doCommand();
    }
    
    public String description() {
        return "command: " + this.commandPhrase;
    }
}
