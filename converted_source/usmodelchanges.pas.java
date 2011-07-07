// unit usmodelchanges

from conversion_common import *;
import usruleeditorform;
import usworld;
import delphi_compatability;


class TModelChange extends TObject {
    
    //////////////////////////////////// TModelChange /////////////////////////////////////
    public void doChange() {
        pass
        // subclasses should override
    }
    
    public void undoChange() {
        pass
        // subclasses should override
    }
    
    public void redoChange() {
        this.doChange();
        // sublasses may override and should call inherited doChange
    }
    
}
class TRuleFieldChange extends TModelChange {
    public TSRule rule;
    public int fieldType;
    public String oldText;
    public String newText;
    
    //////////////////////////////// TRuleFieldChange ////////////////////////////////////
    public void createWithRuleFieldTypeOldTextNewText(TSRule rule, int fieldType, String newText) {
        this.create;
        this.rule = rule;
        this.fieldType = fieldType;
        if (rule != null) {
            this.oldText = rule.getTextForField(fieldType);
        }
        this.newText = newText;
    }
    
    public void doChange() {
        if (this.rule != null) {
            this.rule.setTextForField(this.fieldType, this.newText);
        }
    }
    
    public void undoChange() {
        if (this.rule != null) {
            this.rule.setTextForField(this.fieldType, this.oldText);
        }
        //	RuleEditorForm.updateForFieldChange(fieldType);
    }
    
    public void redoChange() {
        if (this.rule != null) {
            this.rule.setTextForField(this.fieldType, this.newText);
        }
        //	RuleEditorForm.updateForFieldChange(fieldType);
    }
    
}
