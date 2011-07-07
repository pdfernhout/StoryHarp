package com.kurtz_fernhout.storyharp;

import java.util.Vector;

public class TSDeleteRulesCommand extends KfCommand {
    public Vector<TSIndexChangeRuleWrapper> ruleWrappers;
    
    // TSDeleteRulesCommand ------------------------------------------
    public TSDeleteRulesCommand() {
    	super();
        this.ruleWrappers = new Vector<TSIndexChangeRuleWrapper>();
    }
    
    public void addRule(TSRule rule, int newIndex) {
        TSIndexChangeRuleWrapper wrapper = new TSIndexChangeRuleWrapper(rule, newIndex);
        this.ruleWrappers.add(wrapper);
    }
    
    public void doCommand() {
        for (int i = this.ruleWrappers.size() - 1; i >= 0; i--) {
        	TSIndexChangeRuleWrapper wrapper = this.ruleWrappers.get(i);
            if ((wrapper.rule == Application.RuleEditorForm.rule)) {
                Application.RuleEditorForm.editRule(null);
            }
            wrapper.rule.removeUseages();
            wrapper.doChange();
        }
        super.doCommand();
        Application.RuleEditorForm.updateForRuleChange();
    }
    
    public void undoCommand() {
        TSDomain.domain.world.deselectAllExcept(null);
        for (int i = 0; i < this.ruleWrappers.size(); i++) {
        	TSIndexChangeRuleWrapper wrapper = this.ruleWrappers.get(i);
            wrapper.rule.addUseages();
            wrapper.undoChange();
            wrapper.rule.selected = true;
        }
        if (this.ruleWrappers.size() > 0) {
            Application.RuleEditorForm.editRule(this.ruleWrappers.get(0).rule);
        }
        super.undoCommand();
        Application.RuleEditorForm.updateForRuleChange();
        Application.RuleEditorForm.scrollGridSelectionsIntoView(TRuleEditorForm.kFromTop);
    }
    
    public void redoCommand() {
        TSDomain.domain.world.deselectAllExcept(null);
        for (int i = this.ruleWrappers.size() - 1; i >= 0; i--) {
        	TSIndexChangeRuleWrapper wrapper = this.ruleWrappers.get(i);
            if ((wrapper.rule == Application.RuleEditorForm.rule)) {
                Application.RuleEditorForm.editRule(null);
            }
            wrapper.rule.removeUseages();
            wrapper.doChange();
        }
        super.doCommand();
        Application.RuleEditorForm.updateForRuleChange();
    }
    
    public String description() {
        String result = "";
        if (this.ruleWrappers.size() > 1) {
            result = "delete rules";
        } else if (this.ruleWrappers.size() == 1) {
            result = "delete rule";
        } else {
            result = "delete rule";
        }
        return result;
    }
    
}