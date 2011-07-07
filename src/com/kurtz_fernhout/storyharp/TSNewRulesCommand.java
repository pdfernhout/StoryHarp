package com.kurtz_fernhout.storyharp;

import java.util.Vector;

public class TSNewRulesCommand extends KfCommand {
	public Vector<TSRule> rules;
    public String creator;
    
    // TSNewRulesCommand ------------------------------------------
    public TSNewRulesCommand() {
    	super();
        this.rules = new Vector<TSRule>();
    }
    
    public void addRule(TSRule rule) {
        this.rules.add(rule);
    }
    
    public void doCommand() {
        //already added at start
        super.doCommand();
        Application.RuleEditorForm.updateForRuleChange();
        Application.RuleEditorForm.scrollGridSelectionsIntoView(TRuleEditorForm.kFromBottom);
    }
    
    public void undoCommand() {
        for (int i = 0; i < this.rules.size(); i++) {
        	TSRule rule = this.rules.get(i);
            TSDomain.domain.world.rules.remove(rule);
            rule.selected = false;
            rule.removeUseages();
        }
        super.undoCommand();
        if (this.rules.indexOf(Application.RuleEditorForm.rule) >= 0) {
            Application.RuleEditorForm.editRule(null);
        }
        Application.RuleEditorForm.updateForRuleChange();
    }
    
    public void redoCommand() {
        TSDomain.domain.world.deselectAllExcept(null);
        for (int i = 0; i < this.rules.size(); i++) {
        	TSRule rule = this.rules.get(i);
            rule.selected = true;
            TSDomain.domain.world.rules.add(rule);
            rule.addUseages();
        }
        super.doCommand();
        Application.RuleEditorForm.updateForRuleChange();
        Application.RuleEditorForm.scrollGridSelectionsIntoView(TRuleEditorForm.kFromBottom);
        //if rules.size() > 0 then
        //  RuleEditorForm.editRule(rules[rules.size() - 1]);
    }
    
    public String description() {
        String result = "";
        if (this.rules.size() > 1) {
            result = "new rules";
        } else if (this.rules.size() == 1) {
            result = "new rule";
        } else {
            result = "new rule";
        }
        if (!this.creator.equals("")) {
            result = result + " from " + this.creator;
        }
        if (this.creator.equals("duplicating")) {
            result = "duplicate rule";
        }
        return result;
    }
    
}