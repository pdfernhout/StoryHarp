package com.kurtz_fernhout.storyharp;

import com.kurtz_fernhout.storyharp.TSVariable.TSVariableState;

public class TSToggleVariableCommand extends KfCommand {
    public TSVariable variable;
    public TSVariableState oldState;
    public TSVariableState newState;
    
    // ----------------------------- TSToggleVariableCommand --------------------- 
    public TSToggleVariableCommand(TSVariable variable) {
        super();
        this.variable = variable;
        this.oldState = variable.getState();
        if (this.oldState == TSVariableState.kPresent) {
            this.newState = TSVariableState.kAbsent;
        } else {
            this.newState = TSVariableState.kPresent;
        }
    }
    
    public void setVariableStateWithUpdate(TSVariableState state) {
        this.variable.setState(state);
        if (Application.ConsoleForm.ShowOnlyTrueVariablesButton.isSelected()) {
            Application.ConsoleForm.updateVariables();
            Application.ConsoleForm.VariablesListBox.setSelectedIndex(Application.ConsoleForm.variablesListModel.indexOf(this.variable));
            Application.ConsoleForm.VariablesListBox.repaint();
        } else {
            Application.ConsoleForm.VariablesListBox.repaint();
        }
        TSDomain.domain.world.updateAvailable();
        Application.ConsoleForm.speechSystem.listenForAvailableCommands();
    }
    
    public void doCommand() {
        this.setVariableStateWithUpdate(this.newState);
        super.doCommand();
    }
    
    public void undoCommand() {
        this.setVariableStateWithUpdate(this.oldState);
        super.undoCommand();
    }
    
    public String description() {
        String result = "";
        if (this.newState == TSVariableState.kPresent) {
            result = "toggle \"" + this.variable.phrase + "\" to true";
        } else {
            result = "toggle \"" + this.variable.phrase + "\" to false";
        }
        return result;
    }
    
}