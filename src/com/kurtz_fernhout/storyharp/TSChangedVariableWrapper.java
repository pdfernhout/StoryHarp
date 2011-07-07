package com.kurtz_fernhout.storyharp;

import com.kurtz_fernhout.storyharp.TSVariable.TSVariableState;

public class TSChangedVariableWrapper {
    public TSVariable variable;
    public TSVariableState oldState;
    public TSVariableState newState;
    
    public TSChangedVariableWrapper(TSVariable variable, TSVariableState newState) {
        this.variable = variable;
        this.newState = newState;
        this.oldState = variable.getState();
    }
    
    public void doChange() {
        this.variable.setState(this.newState);
    }
    
    public void undoChange() {
        this.variable.setState(this.oldState);
    }
    
}