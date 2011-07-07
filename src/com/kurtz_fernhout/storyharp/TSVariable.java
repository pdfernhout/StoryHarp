package com.kurtz_fernhout.storyharp;

public class TSVariable extends TSDraggableObject {
	public enum TSVariableState {kPresent, kAbsent}
	
    TWorld world;
    public String phrase;
    public TSVariableState state;
    public int contextUseages;
    public int requirementsUseages;
    public int commandUseages;
    public int moveUseages;
    public int changesUseages;
    public int indexInVariables;
    
    public TSVariable() {
    	this.phrase = "";
    	this.state = TSVariableState.kAbsent;
    }
    
    public String toString() {
    	return this.phrase;
    }
    
    public String displayName() {
        return this.phrase;
    }
    
    public void setPhrase(String aPhrase) {
        this.phrase = aPhrase;
    }
    
    public void setState(TSVariableState newState) {
        this.state = newState;
    }
    
    public TSVariableState getState() {
        return this.state;
    }
    
    public boolean hasUseagesForField(int col) {
        boolean result = false;
        switch (col) {
            case TSVariableDisplayOptions.kRuleContext:
                result = (this.contextUseages > 0) || (this.moveUseages > 0);
                break;
            case TSVariableDisplayOptions.kRuleCommand:
                result = this.commandUseages > 0;
                break;
            case TSVariableDisplayOptions.kRuleReply:
                result = false;
                break;
            case TSVariableDisplayOptions.kRuleMove:
                result = this.moveUseages > 0;
                break;
            case TSVariableDisplayOptions.kRuleRequirements:
                result = this.requirementsUseages > 0;
                break;
            case TSVariableDisplayOptions.kRuleChanges:
                result = this.changesUseages > 0;
                break;
        }
        return result;
    }
    
    public boolean meetsDisplayOptions(TSVariableDisplayOptions displayOptions) {
        boolean result = false;
        int i = 0;
        for (i = 0; i <= TSVariableDisplayOptions.kLastRuleField; i++) {
            if (i == TSVariableDisplayOptions.kRuleCommand) {
                //don't display commands for now - used to display rules
                continue;
            }
            if (this.hasUseagesForField(i) && displayOptions.displayOptions[i]) {
                result = true;
                return result;
            }
        }
        return result;
    }
}