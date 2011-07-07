package com.kurtz_fernhout.storyharp;

public class TSCommandList extends KfCommandList {
    
    // ----------------------------- TSCommandList -------------------------------
    public TSToggleVariableCommand toggleVariable(TSVariable variable) {
        TSToggleVariableCommand result = new TSToggleVariableCommand(variable);
        this.doCommand(result);
        return result;
    }
    
    public TSMoveFocusCommand moveFocus(TSVariable newFocus) {
        TSMoveFocusCommand result = new TSMoveFocusCommand(newFocus);
        this.doCommand(result);
        return result;
    }
    
    public TSDoCommandPhrase doCommandPhrase(String commandPhrase) {
        TSDoCommandPhrase result = new TSDoCommandPhrase(commandPhrase);
        this.doCommand(result);
        return result;
    }
    
    public TSRuleFieldChange ruleFieldChange(TSRule rule, int field, String newValue) {
        if ((field == TSVariableDisplayOptions.kRuleContext) || (field == TSVariableDisplayOptions.kRuleMove)) {
            if (rule.getTextForField(field).startsWith("new context ")) {
                if (TSDomain.domain.world.findVariable(newValue) == null) {
                	TSVariable newContextOrMove = TSDomain.domain.world.findOrCreateVariable(newValue, false);
                    newContextOrMove.position = rule.context.position;
                }
            }
        }
        TSRuleFieldChange result = new TSRuleFieldChange(rule, field, newValue);
        this.doCommand(result);
        return result;
    }
    
}