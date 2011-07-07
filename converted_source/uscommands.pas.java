// unit uscommands

from conversion_common import *;
import uschangelog;
import usruleeditorform;
import usspeech;
import usconsoleform;
import usdomain;
import usvariablecommands;
import usfocuscommands;
import usworld;
import ucommand;
import delphi_compatability;


//TEditChangeCommand = class(KfCommand)
//  	entryField: TObject;
//    oldSelectionInformation: TSelectionInformation;
//    newSelectionInformation: TSelectionInformation;
//		closed: boolean;
//		procedure recordChangesMadeToEdit;
//		constructor createWithEdit(Sender: TObject;  const lastSelectionInformation: TSelectionInformation);
// 		procedure doCommand; override;
//		procedure undoCommand; override;
//		procedure redoCommand; override;
//		function description: string; override;
//		end;
//
//  	entryField: TObject;
//    newSelectionInformation: TSelectionInformation;
//		constructor createWithRule(Sender: TObject;  const lastSelectionInformation: TSelectionInformation);
//   
class TSRuleFieldChange extends KfCommand {
    public TSRule rule;
    public int field;
    public String oldValue;
    public String newValue;
    
    // TSRuleFieldChange ------------------------------------------
    public void createWithRule(TSRule rule, int field, String newValue) {
        this.create();
        this.rule = rule;
        this.field = field;
        this.oldValue = rule.getTextForField(field);
        this.newValue = newValue;
    }
    
    public void updateEditorForChange() {
        usruleeditorform.RuleEditorForm.rule = this.rule;
        usruleeditorform.RuleEditorForm.loadAllRuleFields();
        if ((this.field == usworld.kRuleContext) || (this.field == usworld.kRuleMove)) {
            usconsoleform.ConsoleForm.locationCacheValid = false;
        }
        if (this.field == usworld.kRuleRequirements) {
            //wrapper entries will get freed if list box - so reset them
            usruleeditorform.RuleEditorForm.fillListBox(usruleeditorform.RuleEditorForm.RequirementsListBox, this.rule.requirements);
        } else if (this.field == usworld.kRuleChanges) {
            usruleeditorform.RuleEditorForm.fillListBox(usruleeditorform.RuleEditorForm.ChangesListBox, this.rule.changes);
        }
        usruleeditorform.RuleEditorForm.RuleGrid.Invalidate();
        usruleeditorform.RuleEditorForm.MapPaintBoxChanged();
        // could optimize to only do in certain cases
        usruleeditorform.RuleEditorForm.SecondListBox.Invalidate();
        if (usruleeditorform.RuleEditorForm.organizeByField == this.field) {
            // could optimize to only do when browser visible
            usruleeditorform.RuleEditorForm.setOrganizeByField(usruleeditorform.RuleEditorForm.organizeByField);
        }
    }
    
    public void doCommand() {
        usdomain.domain.world.lastVariableCreated = "";
        this.rule.setTextForField(this.field, this.newValue);
        if (this.field != usworld.kRuleReply) {
            // log changes
            uschangelog.ChangeLogForm.addToLog(usdomain.domain.world.lastVariableCreated);
        } else {
            uschangelog.ChangeLogForm.addToLog(this.newValue);
        }
        this.updateEditorForChange();
        super.doCommand();
    }
    
    public void undoCommand() {
        this.rule.setTextForField(this.field, this.oldValue);
        this.updateEditorForChange();
        usruleeditorform.RuleEditorForm.selectEditorField(this.field);
        super.undoCommand();
    }
    
    public void redoCommand() {
        this.rule.setTextForField(this.field, this.newValue);
        this.updateEditorForChange();
        usruleeditorform.RuleEditorForm.selectEditorField(this.field);
        super.doCommand();
    }
    
    public String description() {
        result = "";
        //  result := 'rule ' + IntToStr(domain.world.rules.indexOf(rule) + 1) + ' change of ' + TSRule.headerForField(field);
        result = "Change " + usworld.TSRule.headerForField(this.field) + " For Rule " + IntToStr(usdomain.domain.world.rules.IndexOf(this.rule) + 1);
        return result;
    }
    
}
class TSNewRulesCommand extends KfCommand {
    public TList rules;
    public String creator;
    
    // TSNewRulesCommand ------------------------------------------
    public void create() {
        this.rules = delphi_compatability.TList().Create();
    }
    
    public void destroy() {
        int i = 0;
        
        if (!this.done) {
            if (this.rules != null) {
                for (i = 0; i <= this.rules.Count - 1; i++) {
                    UNRESOLVED.TObject(this.rules[i]).free;
                }
            }
        }
        this.rules.free;
        this.rules = null;
    }
    
    public void addRule(TSRule rule) {
        this.rules.Add(rule);
    }
    
    public void doCommand() {
        //already added at start
        super.doCommand();
        usruleeditorform.RuleEditorForm.updateForRuleChange();
        usruleeditorform.RuleEditorForm.scrollGridSelectionsIntoView(usruleeditorform.kFromBottom);
    }
    
    public void undoCommand() {
        int i = 0;
        TSRule rule = new TSRule();
        
        for (i = 0; i <= this.rules.Count - 1; i++) {
            rule = usworld.TSRule(this.rules[i]);
            usdomain.domain.world.rules.Remove(rule);
            rule.selected = false;
            rule.removeUseages();
        }
        super.undoCommand();
        if (this.rules.IndexOf(usruleeditorform.RuleEditorForm.rule) >= 0) {
            usruleeditorform.RuleEditorForm.editRule(null);
        }
        usruleeditorform.RuleEditorForm.updateForRuleChange();
    }
    
    public void redoCommand() {
        int i = 0;
        TSRule rule = new TSRule();
        
        usdomain.domain.world.deselectAllExcept(null);
        for (i = 0; i <= this.rules.Count - 1; i++) {
            rule = usworld.TSRule(this.rules[i]);
            rule.selected = true;
            usdomain.domain.world.rules.Add(rule);
            rule.addUseages();
        }
        super.doCommand();
        usruleeditorform.RuleEditorForm.updateForRuleChange();
        usruleeditorform.RuleEditorForm.scrollGridSelectionsIntoView(usruleeditorform.kFromBottom);
        //if rules.count > 0 then
        //  RuleEditorForm.editRule(rules[rules.count - 1]);
    }
    
    public String description() {
        result = "";
        if (this.rules.Count > 1) {
            result = "new rules";
        } else if (this.rules.Count == 1) {
            result = "new rule";
        } else {
            result = "new rule";
        }
        if (this.creator != "") {
            result = result + " from " + this.creator;
        }
        if (this.creator == "duplicating") {
            result = "duplicate rule";
        }
        return result;
    }
    
}
class TSDeleteRulesCommand extends KfCommand {
    public TList ruleWrappers;
    
    // TSDeleteRulesCommand ------------------------------------------
    public void create() {
        this.ruleWrappers = delphi_compatability.TList().Create();
    }
    
    public void destroy() {
        int i = 0;
        TSRule rule = new TSRule();
        TSIndexChangeRuleWrapper wrapper = new TSIndexChangeRuleWrapper();
        
        if (this.ruleWrappers != null) {
            for (i = 0; i <= this.ruleWrappers.Count - 1; i++) {
                wrapper = usworld.TSIndexChangeRuleWrapper(this.ruleWrappers[i]);
                rule = wrapper.rule;
                if (this.done) {
                    rule.free;
                }
                wrapper.free;
            }
        }
        this.ruleWrappers.free;
        this.ruleWrappers = null;
    }
    
    public void addRule(TSRule rule, int newIndex) {
        TSIndexChangeRuleWrapper wrapper = new TSIndexChangeRuleWrapper();
        
        wrapper = usworld.TSIndexChangeRuleWrapper().createWithRuleNewIndex(rule, newIndex);
        this.ruleWrappers.Add(wrapper);
    }
    
    public void doCommand() {
        int i = 0;
        TSIndexChangeRuleWrapper wrapper = new TSIndexChangeRuleWrapper();
        
        for (i = this.ruleWrappers.Count - 1; i >= 0; i--) {
            wrapper = usworld.TSIndexChangeRuleWrapper(this.ruleWrappers[i]);
            if ((wrapper.rule == usruleeditorform.RuleEditorForm.rule)) {
                usruleeditorform.RuleEditorForm.editRule(null);
            }
            wrapper.rule.removeUseages();
            wrapper.doChange();
        }
        super.doCommand();
        usruleeditorform.RuleEditorForm.updateForRuleChange();
    }
    
    public void undoCommand() {
        int i = 0;
        TSIndexChangeRuleWrapper wrapper = new TSIndexChangeRuleWrapper();
        
        usdomain.domain.world.deselectAllExcept(null);
        for (i = 0; i <= this.ruleWrappers.Count - 1; i++) {
            wrapper = usworld.TSIndexChangeRuleWrapper(this.ruleWrappers[i]);
            wrapper.rule.addUseages();
            wrapper.undoChange();
            wrapper.rule.selected = true;
        }
        if (this.ruleWrappers.Count > 0) {
            usruleeditorform.RuleEditorForm.editRule(usworld.TSIndexChangeRuleWrapper(this.ruleWrappers[0]).rule);
        }
        super.undoCommand();
        usruleeditorform.RuleEditorForm.updateForRuleChange();
        usruleeditorform.RuleEditorForm.scrollGridSelectionsIntoView(usruleeditorform.kFromTop);
    }
    
    public void redoCommand() {
        int i = 0;
        TSIndexChangeRuleWrapper wrapper = new TSIndexChangeRuleWrapper();
        
        usdomain.domain.world.deselectAllExcept(null);
        for (i = this.ruleWrappers.Count - 1; i >= 0; i--) {
            wrapper = usworld.TSIndexChangeRuleWrapper(this.ruleWrappers[i]);
            if ((wrapper.rule == usruleeditorform.RuleEditorForm.rule)) {
                usruleeditorform.RuleEditorForm.editRule(null);
            }
            wrapper.rule.removeUseages();
            wrapper.doChange();
        }
        super.doCommand();
        usruleeditorform.RuleEditorForm.updateForRuleChange();
    }
    
    public String description() {
        result = "";
        if (this.ruleWrappers.Count > 1) {
            result = "delete rules";
        } else if (this.ruleWrappers.Count == 1) {
            result = "delete rule";
        } else {
            result = "delete rule";
        }
        return result;
    }
    
}
class TSMoveRulesCommand extends KfCommand {
    public TList ruleWrappers;
    public String action;
    
    // TSMoveRulesCommand ------------------------------------------
    public void create() {
        this.ruleWrappers = delphi_compatability.TList().Create();
    }
    
    public void destroy() {
        int i = 0;
        TSIndexChangeRuleWrapper wrapper = new TSIndexChangeRuleWrapper();
        
        if (this.ruleWrappers != null) {
            for (i = 0; i <= this.ruleWrappers.Count - 1; i++) {
                wrapper = usworld.TSIndexChangeRuleWrapper(this.ruleWrappers[i]);
                wrapper.free;
            }
        }
        this.ruleWrappers.free;
        this.ruleWrappers = null;
    }
    
    public void addRule(TSRule rule, int newIndex) {
        TSIndexChangeRuleWrapper wrapper = new TSIndexChangeRuleWrapper();
        
        wrapper = usworld.TSIndexChangeRuleWrapper().createWithRuleNewIndex(rule, newIndex);
        this.ruleWrappers.Add(wrapper);
    }
    
    public void doCommand() {
        int i = 0;
        TSIndexChangeRuleWrapper wrapper = new TSIndexChangeRuleWrapper();
        
        for (i = 0; i <= this.ruleWrappers.Count - 1; i++) {
            wrapper = usworld.TSIndexChangeRuleWrapper(this.ruleWrappers[i]);
            wrapper.doChange();
        }
        super.doCommand();
        usruleeditorform.RuleEditorForm.RuleGrid.Invalidate();
        if (this.action == "raise") {
            usruleeditorform.RuleEditorForm.scrollGridSelectionsIntoView(usruleeditorform.kFromTop);
        } else {
            usruleeditorform.RuleEditorForm.scrollGridSelectionsIntoView(usruleeditorform.kFromBottom);
        }
        usruleeditorform.RuleEditorForm.updateRuleNumberLabel();
    }
    
    public void undoCommand() {
        int i = 0;
        TSIndexChangeRuleWrapper wrapper = new TSIndexChangeRuleWrapper();
        
        usdomain.domain.world.deselectAllExcept(null);
        for (i = this.ruleWrappers.Count - 1; i >= 0; i--) {
            wrapper = usworld.TSIndexChangeRuleWrapper(this.ruleWrappers[i]);
            wrapper.rule.selected = true;
            wrapper.undoChange();
        }
        super.undoCommand();
        usruleeditorform.RuleEditorForm.RuleGrid.Invalidate();
        if (this.action == "raise") {
            usruleeditorform.RuleEditorForm.scrollGridSelectionsIntoView(usruleeditorform.kFromBottom);
        } else {
            usruleeditorform.RuleEditorForm.scrollGridSelectionsIntoView(usruleeditorform.kFromTop);
        }
        usruleeditorform.RuleEditorForm.updateRuleNumberLabel();
    }
    
    public void redoCommand() {
        int i = 0;
        TSIndexChangeRuleWrapper wrapper = new TSIndexChangeRuleWrapper();
        
        usdomain.domain.world.deselectAllExcept(null);
        for (i = 0; i <= this.ruleWrappers.Count - 1; i++) {
            wrapper = usworld.TSIndexChangeRuleWrapper(this.ruleWrappers[i]);
            wrapper.rule.selected = true;
            wrapper.doChange();
        }
        super.doCommand();
        usruleeditorform.RuleEditorForm.RuleGrid.Invalidate();
        if (this.action == "raise") {
            usruleeditorform.RuleEditorForm.scrollGridSelectionsIntoView(usruleeditorform.kFromTop);
        } else {
            usruleeditorform.RuleEditorForm.scrollGridSelectionsIntoView(usruleeditorform.kFromBottom);
        }
        usruleeditorform.RuleEditorForm.updateRuleNumberLabel();
    }
    
    public String description() {
        result = "";
        if (this.ruleWrappers.Count > 1) {
            result = "rules";
        } else if (this.ruleWrappers.Count == 1) {
            result = "rule";
        } else {
            result = "rule";
        }
        if (this.action != "") {
            result = this.action + " " + result;
        } else {
            result = "move " + result;
        }
        return result;
    }
    
}
class TSMapDragCommand extends KfCommand {
    public TList dragRecords;
    public TCommandEvent notifyProcedure;
    
    // TSMapDragCommand ------------------------------------------
    public void create() {
        this.dragRecords = delphi_compatability.TList().Create();
        usdomain.domain.world.addDragRecordsToList(this.dragRecords);
    }
    
    public void destroy() {
        int i = 0;
        
        if (this.dragRecords != null) {
            for (i = 0; i <= this.dragRecords.Count - 1; i++) {
                UNRESOLVED.TObject(this.dragRecords[i]).free;
            }
        }
        this.dragRecords.free;
        this.dragRecords = null;
    }
    
    public void doCommand() {
        int i = 0;
        
        for (i = 0; i <= this.dragRecords.Count - 1; i++) {
            usworld.TSDragRecord(this.dragRecords[i]).doDrag();
        }
        if (delphi_compatability.Assigned(this.notifyProcedure)) {
            this.notifyProcedure(this, ucommand.KfCommandChangeType.commandDone);
        }
        super.doCommand();
    }
    
    public void undoCommand() {
        int i = 0;
        
        usdomain.domain.world.deselectAllExcept(null);
        for (i = 0; i <= this.dragRecords.Count - 1; i++) {
            usworld.TSDragRecord(this.dragRecords[i]).draggedNode.selected = true;
            usworld.TSDragRecord(this.dragRecords[i]).undoDrag();
        }
        if (delphi_compatability.Assigned(this.notifyProcedure)) {
            this.notifyProcedure(this, ucommand.KfCommandChangeType.commandUndone);
        }
        super.undoCommand();
    }
    
    public void redoCommand() {
        int i = 0;
        
        usdomain.domain.world.deselectAllExcept(null);
        for (i = 0; i <= this.dragRecords.Count - 1; i++) {
            usworld.TSDragRecord(this.dragRecords[i]).draggedNode.selected = true;
            usworld.TSDragRecord(this.dragRecords[i]).doDrag();
        }
        if (delphi_compatability.Assigned(this.notifyProcedure)) {
            this.notifyProcedure(this, ucommand.KfCommandChangeType.commandDone);
        }
        super.doCommand();
    }
    
    public String description() {
        result = "";
        if (this.dragRecords.Count > 1) {
            result = "Drag nodes";
        } else if (this.dragRecords.Count == 1) {
            result = "Drag " + usworld.TSDragRecord(this.dragRecords[0]).draggedNode.displayName();
        } else {
            result = "Drag";
        }
        return result;
    }
    
    public KfCommand TrackMouse(TrackPhase aTrackPhase, TPoint anchorPoint, TPoint previousPoint, TPoint nextPoint, boolean mouseDidMove, boolean rightButtonDown) {
        result = new KfCommand();
        int i = 0;
        TPoint delta = new TPoint();
        
        result = this;
        switch (aTrackPhase) {
            case ucommand.TrackPhase.trackPress:
                if (this.dragRecords.Count == 0) {
                    result = null;
                    this.free;
                    return result;
                }
                break;
            case ucommand.TrackPhase.trackMove:
                if (mouseDidMove) {
                    delta = Point(nextPoint.X - previousPoint.X, nextPoint.Y - previousPoint.Y);
                    for (i = 0; i <= this.dragRecords.Count - 1; i++) {
                        usworld.TSDragRecord(this.dragRecords[i]).offset(delta);
                    }
                    if (delphi_compatability.Assigned(this.notifyProcedure)) {
                        this.notifyProcedure(this, ucommand.KfCommandChangeType.commandDone);
                    }
                }
                break;
            case ucommand.TrackPhase.trackRelease:
                if (!mouseDidMove) {
                    if ((usworld.TSDragRecord(this.dragRecords[0]).draggedNode.position.X != usworld.TSDragRecord(this.dragRecords[0]).originalLocation.X) || (usworld.TSDragRecord(this.dragRecords[0]).draggedNode.position.Y != usworld.TSDragRecord(this.dragRecords[0]).originalLocation.Y)) {
                        for (i = 0; i <= this.dragRecords.Count - 1; i++) {
                            usworld.TSDragRecord(this.dragRecords[i]).undoDrag();
                        }
                        if (delphi_compatability.Assigned(this.notifyProcedure)) {
                            this.notifyProcedure(this, ucommand.KfCommandChangeType.commandDone);
                        }
                    }
                    result = null;
                    this.free;
                } else {
                    delta = Point(nextPoint.X - previousPoint.X, nextPoint.Y - previousPoint.Y);
                    if ((delta.X != 0) || (delta.Y != 0)) {
                        for (i = 0; i <= this.dragRecords.Count - 1; i++) {
                            usworld.TSDragRecord(this.dragRecords[i]).offset(delta);
                        }
                        if (delphi_compatability.Assigned(this.notifyProcedure)) {
                            this.notifyProcedure(this, ucommand.KfCommandChangeType.commandDone);
                        }
                    }
                }
                break;
        return result;
    }
    
}
class TSCommandList extends KfCommandList {
    
    // ----------------------------- TSCommandList -------------------------------
    public TSToggleVariableCommand toggleVariable(TSVariable variable) {
        result = new TSToggleVariableCommand();
        result = usvariablecommands.TSToggleVariableCommand().createWithVariable(variable);
        this.doCommand(result);
        return result;
    }
    
    public TSMoveFocusCommand moveFocus(TSVariable newFocus) {
        result = new TSMoveFocusCommand();
        result = usvariablecommands.TSMoveFocusCommand().createWithNewFocus(newFocus);
        this.doCommand(result);
        return result;
    }
    
    public TSDoCommandPhrase doCommandPhrase(String commandPhrase) {
        result = new TSDoCommandPhrase();
        result = usvariablecommands.TSDoCommandPhrase().createWithCommandPhrase(commandPhrase);
        this.doCommand(result);
        return result;
    }
    
    public TSRuleFieldChange ruleFieldChange(TSRule rule, int field, String newValue) {
        result = new TSRuleFieldChange();
        TSVariable newContextOrMove = new TSVariable();
        
        if ((field == usworld.kRuleContext) || (field == usworld.kRuleMove)) {
            if (UNRESOLVED.Pos("new context ", rule.getTextForField(field)) == 1) {
                if (usdomain.domain.world.findVariable(newValue) == null) {
                    newContextOrMove = usdomain.domain.world.findOrCreateVariable(newValue, false);
                    newContextOrMove.position = rule.context.position;
                }
            }
        }
        result = TSRuleFieldChange().createWithRule(rule, field, newValue);
        this.doCommand(result);
        return result;
    }
    
}
