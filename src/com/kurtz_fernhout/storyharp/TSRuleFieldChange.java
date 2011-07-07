package com.kurtz_fernhout.storyharp;

public class TSRuleFieldChange extends KfCommand {
    public TSRule rule;
    public int field;
    public String oldValue;
    public String newValue;
    
    // TSRuleFieldChange ------------------------------------------
    public TSRuleFieldChange(TSRule rule, int field, String newValue) {
        super();
        this.rule = rule;
        this.field = field;
        this.oldValue = rule.getTextForField(field);
        this.newValue = newValue;
    }
    
    public void updateEditorForChange() {
        Application.RuleEditorForm.rule = this.rule;
        Application.RuleEditorForm.loadAllRuleFields();
        if ((this.field == TSVariableDisplayOptions.kRuleContext) || (this.field == TSVariableDisplayOptions.kRuleMove)) {
            Application.ConsoleForm.locationCacheValid = false;
        }
        if (this.field == TSVariableDisplayOptions.kRuleRequirements) {
            //wrapper entries will get freed if list box - so reset them
            Application.RuleEditorForm.fillListBox(Application.RuleEditorForm.RequirementsListBoxModel, this.rule.requirements);
        } else if (this.field == TSVariableDisplayOptions.kRuleChanges) {
            Application.RuleEditorForm.fillListBox(Application.RuleEditorForm.ChangesListBoxModel, this.rule.changes);
        }
        Application.RuleEditorForm.RuleGrid.repaint();
        Application.RuleEditorForm.MapPaintBoxChanged();
        // could optimize to only do in certain cases
        Application.RuleEditorForm.SecondListBox.repaint();
        if (Application.RuleEditorForm.organizeByField == this.field) {
            // could optimize to only do when browser visible
            Application.RuleEditorForm.setOrganizeByField(Application.RuleEditorForm.organizeByField);
        }
    }
    
    public void doCommand() {
        TSDomain.domain.world.lastVariableCreated = "";
        this.rule.setTextForField(this.field, this.newValue);
        if (this.field != TSVariableDisplayOptions.kRuleReply) {
            // log changes
            Application.ChangeLogForm.addToLog(TSDomain.domain.world.lastVariableCreated);
        } else {
            Application.ChangeLogForm.addToLog(this.newValue);
        }
        this.updateEditorForChange();
        super.doCommand();
    }
    
    public void undoCommand() {
        this.rule.setTextForField(this.field, this.oldValue);
        this.updateEditorForChange();
        Application.RuleEditorForm.selectEditorField(this.field);
        super.undoCommand();
    }
    
    public void redoCommand() {
        this.rule.setTextForField(this.field, this.newValue);
        this.updateEditorForChange();
        Application.RuleEditorForm.selectEditorField(this.field);
        super.doCommand();
    }
    
    public String description() {
        String result = "";
        //  result := 'rule ' + String.valueOf(domain.world.rules.indexOf(rule) + 1) + ' change of ' + TSRule.headerForField(field);
        result = "Change " + TSRule.headerForField(this.field) + " For Rule " + String.valueOf(TSDomain.domain.world.rules.indexOf(this.rule) + 1);
        return result;
    }
    
}