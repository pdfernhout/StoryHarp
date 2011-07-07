package com.kurtz_fernhout.storyharp;

import java.util.Vector;

public class TSMoveRulesCommand extends KfCommand {
	public Vector<TSIndexChangeRuleWrapper> ruleWrappers;
    public String action;
    
    // TSMoveRulesCommand ------------------------------------------
    public TSMoveRulesCommand() {
    	super();
        this.ruleWrappers = new Vector<TSIndexChangeRuleWrapper>();
    }
    
    public void addRule(TSRule rule, int newIndex) {
        TSIndexChangeRuleWrapper wrapper = new TSIndexChangeRuleWrapper(rule, newIndex);
        this.ruleWrappers.add(wrapper);
    }
    
    public void doCommand() {
        for (int i = 0; i < this.ruleWrappers.size(); i++) {
        	TSIndexChangeRuleWrapper wrapper = this.ruleWrappers.get(i);
            wrapper.doChange();
        }
        super.doCommand();
        Application.RuleEditorForm.RuleGrid.repaint();
        if (this.action.equals("raise")) {
            Application.RuleEditorForm.scrollGridSelectionsIntoView(TRuleEditorForm.kFromTop);
        } else {
            Application.RuleEditorForm.scrollGridSelectionsIntoView(TRuleEditorForm.kFromBottom);
        }
        Application.RuleEditorForm.updateRuleNumberLabel();
    }
    
    public void undoCommand() {
        TSDomain.domain.world.deselectAllExcept(null);
        for (int i = this.ruleWrappers.size() - 1; i >= 0; i--) {
        	TSIndexChangeRuleWrapper wrapper = this.ruleWrappers.get(i);
            wrapper.rule.selected = true;
            wrapper.undoChange();
        }
        super.undoCommand();
        Application.RuleEditorForm.RuleGrid.repaint();
        if (this.action.equals("raise")) {
            Application.RuleEditorForm.scrollGridSelectionsIntoView(TRuleEditorForm.kFromBottom);
        } else {
            Application.RuleEditorForm.scrollGridSelectionsIntoView(TRuleEditorForm.kFromTop);
        }
        Application.RuleEditorForm.updateRuleNumberLabel();
    }
    
    public void redoCommand() {
        TSDomain.domain.world.deselectAllExcept(null);
        for (int i = 0; i < this.ruleWrappers.size(); i++) {
        	TSIndexChangeRuleWrapper wrapper = this.ruleWrappers.get(i);
            wrapper.rule.selected = true;
            wrapper.doChange();
        }
        super.doCommand();
        Application.RuleEditorForm.RuleGrid.repaint();
        if (this.action.equals("raise")) {
            Application.RuleEditorForm.scrollGridSelectionsIntoView(TRuleEditorForm.kFromTop);
        } else {
            Application.RuleEditorForm.scrollGridSelectionsIntoView(TRuleEditorForm.kFromBottom);
        }
        Application.RuleEditorForm.updateRuleNumberLabel();
    }
    
    public String description() {
        String result = "";
        if (this.ruleWrappers.size() > 1) {
            result = "rules";
        } else if (this.ruleWrappers.size() == 1) {
            result = "rule";
        } else {
            result = "rule";
        }
        if (!this.action.equals("")) {
            result = this.action + " " + result;
        } else {
            result = "move " + result;
        }
        return result;
    }
    
}