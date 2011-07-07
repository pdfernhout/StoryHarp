package com.kurtz_fernhout.storyharp;

import java.util.Vector;

public class TSIndexChangeRuleWrapper {
    public TSRule rule;
    public int oldIndex;
    public int newIndex;
    
    public TSIndexChangeRuleWrapper(TSRule rule, int newIndex) {
        this.rule = rule;
        this.oldIndex = rule.world.rules.indexOf(rule);
        this.newIndex = newIndex;
    }
    
    public static void ruleListItemMove(Vector<TSRule> ruleList, int oldIndex, int newIndex) {
    	if (newIndex == oldIndex) return;
    	TSRule item = ruleList.get(oldIndex);	
    	// a little wasteful as move stuff back and forth
    	ruleList.remove(oldIndex);
    	ruleList.add(newIndex, item);
    }
    
    public void doChange() {
        if (this.oldIndex == this.newIndex) {
            return;
        }
        if (this.newIndex >= 0) {
        	ruleListItemMove(this.rule.world.rules, this.oldIndex, this.newIndex);
        } else {
            this.rule.world.rules.remove(this.oldIndex);
        }
    }
    
    public void undoChange() {
        if (this.oldIndex == this.newIndex) {
            return;
        }
        if (this.newIndex >= 0) {
        	ruleListItemMove(this.rule.world.rules, this.newIndex, this.oldIndex);
        } else {
            this.rule.world.rules.add(this.oldIndex, this.rule);
        }
    }
    
}
