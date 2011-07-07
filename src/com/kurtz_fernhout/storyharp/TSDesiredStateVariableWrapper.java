package com.kurtz_fernhout.storyharp;

import com.kurtz_fernhout.storyharp.TSVariable.TSVariableState;

public class TSDesiredStateVariableWrapper {
    public TSVariable variable;
    public TSVariableState desiredState;
    
     public String leader() {
        String result = "";
        if (this.desiredState == TSVariableState.kAbsent) {
            result = "~";
        } else {
            result = "";
        }
        return result;
    }
    
    public String displayLeader() {
        String result = "";
        if (this.desiredState == TSVariableState.kAbsent) {
            result = "~";
        } else {
            result = "  ";
        }
        return result;
    }
    
    public void invertDesiredState() {
        if (this.desiredState == TSVariableState.kAbsent) {
            this.desiredState = TSVariableState.kPresent;
        } else {
            this.desiredState = TSVariableState.kAbsent;
        }
    }
    
    public String displayString() {
        return this.displayLeader() + this.variable.phrase;
    }
    
    public String toString() {
    	return this.displayString();
    }
    
}