// unit usworld

from conversion_common import *;
import usdomain;
import uscommands;
import ufilesupport;
import usruleeditorform;
import usconsoleform;
import usmain;
import delphi_compatability;

// const
kSaveAllRules = false;
kSaveOnlySelectedRules = true;


// FIX enumerated type TSVariableState
class TSVariableState:
    kPresent, kAbsent = range(2)



// const
kRuleContext = 0;
kRuleCommand = 1;
kRuleReply = 2;
kRuleMove = 3;
kRuleRequirements = 4;
kRuleChanges = 5;
kLastRuleField = 5;


public String findCompleteWorldFileName(String worldFileNameRead) {
    result = "";
    if (!FileExists(worldFileNameRead)) {
        result = ufilesupport.getFileOpenInfo(ufilesupport.kFileTypeWorld, worldFileNameRead, "Search for world file: " + worldFileNameRead, ufilesupport.kOtherExtNotOK);
    } else {
        result = worldFileNameRead;
    }
    return result;
}

public void swapIntegers(int a, int b) {
    int temp = 0;
    
    temp = a;
    a = b;
    b = temp;
    return a, b;
}


class TSDraggableObject extends TObject {
    public TPoint position;
    public TPoint extent;
    public boolean selected;
    
    // need to be sequence from zero
    //////////////////// TObjectWithPosition ///////////////////////
    public String displayName() {
        result = "";
        result = "Error - override needed";
        return result;
    }
    
    public void setPosition(String value) {
        String firstNumber = "";
        String secondNumber = "";
        
        firstNumber = UNRESOLVED.Copy(value, 1, UNRESOLVED.pos(",", value) - 1);
        secondNumber = UNRESOLVED.Copy(value, len(firstNumber) + 2, len(value));
        //position.x := 0;
        //position.y := 0;
        try {
            this.position.X = StrToInt(firstNumber);
            this.position.Y = StrToInt(secondNumber);
        } catch (Exception e) {
            // pass
        }
    }
    
    public TRect bounds() {
        result = new TRect();
        TPoint topLeft = new TPoint();
        
        topLeft = Point(this.position.X - this.extent.X / 2, this.position.Y - this.extent.Y / 2);
        result = Rect(topLeft.X, topLeft.Y, topLeft.X + this.extent.X, topLeft.Y + this.extent.Y);
        return result;
    }
    
    public TPoint center() {
        result = new TPoint();
        result = this.position;
        return result;
    }
    
}
class TSDragRecord extends TObject {
    public TSDraggableObject draggedNode;
    public TPoint originalLocation;
    public TPoint newLocation;
    
    //////////////////// TSDragRecord ///////////////////////
    public void createWithNode(TSDraggableObject node) {
        this.create;
        this.draggedNode = node;
        this.originalLocation = this.draggedNode.position;
        this.newLocation = this.originalLocation;
    }
    
    public void doDrag() {
        this.draggedNode.position = this.newLocation;
    }
    
    public void undoDrag() {
        this.draggedNode.position = this.originalLocation;
    }
    
    public void offset(TPoint delta) {
        this.newLocation = Point(this.newLocation.X + delta.X, this.newLocation.Y + delta.Y);
        this.draggedNode.position = this.newLocation;
    }
    
}
class TSVariable extends TSDraggableObject {
    public TWorld world;
    public String phrase;
    public TSVariableState state;
    public int contextUseages;
    public int requirementsUseages;
    public int commandUseages;
    public int moveUseages;
    public int changesUseages;
    public int indexInVariables;
    
    // for java creation
    //////////////////// TSVariable ///////////////////////
    public String displayName() {
        result = "";
        result = this.phrase;
        return result;
    }
    
    public void setPhrase(String aPhrase) {
        this.phrase = aPhrase;
    }
    
    public void setState(TSVariableState newState) {
        this.state = newState;
    }
    
    public TSVariableState getState() {
        result = new TSVariableState();
        result = this.state;
        return result;
    }
    
    public boolean hasUseagesForField(int col) {
        result = false;
        result = false;
        switch (col) {
            case kRuleContext:
                result = (this.contextUseages > 0) || (this.moveUseages > 0);
                break;
            case kRuleCommand:
                result = this.commandUseages > 0;
                break;
            case kRuleReply:
                result = false;
                break;
            case kRuleMove:
                result = this.moveUseages > 0;
                break;
            case kRuleRequirements:
                result = this.requirementsUseages > 0;
                break;
            case kRuleChanges:
                result = this.changesUseages > 0;
                break;
        return result;
    }
    
    public boolean meetsDisplayOptions(TSVariableDisplayOptions displayOptions) {
        result = false;
        int i = 0;
        
        result = false;
        for (i = 0; i <= 5; i++) {
            if (i == kRuleCommand) {
                //don't display commands for now - used to display rules
                continue;
            }
            if (this.hasUseagesForField(i) && displayOptions[i]) {
                result = true;
                return result;
            }
        }
        return result;
    }
    
}
class TSDesiredStateVariableWrapper extends TObject {
    public TSVariable variable;
    public TSVariableState desiredState;
    
    /////////////////////////// TSDesiredStateVariableWrapperleader /////////////////////////////////////
    public String leader() {
        result = "";
        if (this.desiredState == TSVariableState.kAbsent) {
            result = "~";
        } else {
            result = "";
        }
        return result;
    }
    
    public String displayLeader() {
        result = "";
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
        result = "";
        result = this.displayLeader() + this.variable.phrase;
        return result;
    }
    
}
class TSChangedVariableWrapper extends TObject {
    public TSVariable variable;
    public TSVariableState oldState;
    public TSVariableState newState;
    
    /////////////////////////// TSChangedVariableWrapper /////////////////////////////////////
    public void createWithVariableNewState(TSVariable variable, TSVariableState newState) {
        this.create;
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
class TSRule extends TSDraggableObject {
    public TWorld world;
    public TSVariable context;
    public TList requirements;
    public TSVariable command;
    public String reply;
    public TSVariable move;
    public TList changes;
    public boolean available;
    public String requirementsString;
    public String changesString;
    public boolean useagesRemoved;
    
    //////////////////// TSRule ////////////////////////
    public String displayName() {
        result = "";
        result = "";
        if (usdomain.domain.options.showCommandPrefixInMap) {
            result = result + "> ";
        }
        result = result + this.command.phrase;
        return result;
    }
    
    public void create() {
        this.requirements = delphi_compatability.TList().Create();
        this.changes = delphi_compatability.TList().Create();
    }
    
    public void addUseages() {
        int i = 0;
        
        this.context.contextUseages += 1;
        for (i = 0; i <= this.requirements.Count - 1; i++) {
            TSDesiredStateVariableWrapper(this.requirements[i]).variable.requirementsUseages += 1;
        }
        this.command.commandUseages += 1;
        this.move.moveUseages += 1;
        for (i = 0; i <= this.changes.Count - 1; i++) {
            TSDesiredStateVariableWrapper(this.changes[i]).variable.changesUseages += 1;
        }
        this.useagesRemoved = false;
    }
    
    public void removeUseages() {
        int i = 0;
        
        this.context.contextUseages -= 1;
        for (i = 0; i <= this.requirements.Count - 1; i++) {
            TSDesiredStateVariableWrapper(this.requirements[i]).variable.requirementsUseages -= 1;
        }
        this.command.commandUseages -= 1;
        this.move.moveUseages -= 1;
        for (i = 0; i <= this.changes.Count - 1; i++) {
            TSDesiredStateVariableWrapper(this.changes[i]).variable.changesUseages -= 1;
        }
        this.useagesRemoved = true;
    }
    
    public void destroy() {
        int i = 0;
        
        if (!this.useagesRemoved) {
            if (this.context != null) {
                this.context.contextUseages -= 1;
            }
            if (this.requirements != null) {
                for (i = 0; i <= this.requirements.Count - 1; i++) {
                    TSDesiredStateVariableWrapper(this.requirements[i]).variable.requirementsUseages -= 1;
                    TSDesiredStateVariableWrapper(this.requirements[i]).free;
                }
            }
            if (this.command != null) {
                this.command.commandUseages -= 1;
            }
            if (this.move != null) {
                this.move.moveUseages -= 1;
            }
            if (this.changes != null) {
                for (i = 0; i <= this.changes.Count - 1; i++) {
                    TSDesiredStateVariableWrapper(this.changes[i]).variable.changesUseages -= 1;
                    TSDesiredStateVariableWrapper(this.changes[i]).free;
                }
            }
        } else {
            if (this.requirements != null) {
                for (i = 0; i <= this.requirements.Count - 1; i++) {
                    TSDesiredStateVariableWrapper(this.requirements[i]).free;
                }
            }
            if (this.changes != null) {
                for (i = 0; i <= this.changes.Count - 1; i++) {
                    TSDesiredStateVariableWrapper(this.changes[i]).free;
                }
            }
        }
        this.requirements.free;
        this.requirements = null;
        this.changes.free;
        this.changes = null;
        super.destroy();
    }
    
    public void setContext(String aString) {
        if (this.context != null) {
            this.context.contextUseages -= 1;
        }
        this.context = this.world.findOrCreateVariable(aString, false);
        this.context.contextUseages += 1;
    }
    
    public void setRequirements(String aString) {
        int i = 0;
        
        this.requirementsString = aString;
        if (this.requirements.Count > 0) {
            for (i = 0; i <= this.requirements.Count - 1; i++) {
                TSDesiredStateVariableWrapper(this.requirements[i]).variable.requirementsUseages -= 1;
                TSDesiredStateVariableWrapper(this.requirements[i]).free;
            }
        }
        this.requirements.Clear();
        this.compile(aString, this.requirements);
        if (this.requirements.Count > 0) {
            for (i = 0; i <= this.requirements.Count - 1; i++) {
                TSDesiredStateVariableWrapper(this.requirements[i]).variable.requirementsUseages += 1;
            }
        }
    }
    
    public void setCommand(String aString) {
        if (this.command != null) {
            this.command.commandUseages -= 1;
        }
        this.command = this.world.findOrCreateVariable(aString, false);
        this.command.commandUseages += 1;
    }
    
    public void setReply(String aString) {
        int i = 0;
        String safeString = "";
        
        safeString = aString;
        for (i = 1; i <= len(safeString); i++) {
            if (safeString[i] == chr(13)) {
                safeString[i] = " ";
            }
            if (safeString[i] == chr(10)) {
                safeString[i] = " ";
            }
        }
        this.reply = safeString;
    }
    
    public void setMove(String aString) {
        if (this.move != null) {
            this.move.moveUseages -= 1;
        }
        this.move = this.world.findOrCreateVariable(aString, false);
        this.move.moveUseages += 1;
    }
    
    public void setChanges(String aString) {
        int i = 0;
        
        this.changesString = aString;
        if (this.changes.Count > 0) {
            for (i = 0; i <= this.changes.Count - 1; i++) {
                TSDesiredStateVariableWrapper(this.changes[i]).variable.changesUseages -= 1;
                TSDesiredStateVariableWrapper(this.changes[i]).free;
            }
        }
        this.changes.Clear();
        this.compile(aString, this.changes);
        if (this.changes.Count > 0) {
            for (i = 0; i <= this.changes.Count - 1; i++) {
                TSDesiredStateVariableWrapper(this.changes[i]).variable.changesUseages += 1;
            }
        }
    }
    
    public void compile(String aString, TList list) {
        String phrase = "";
        int position = 0;
        String remaining = "";
        TSVariable variable = new TSVariable();
        TSVariableState desiredState = new TSVariableState();
        TSDesiredStateVariableWrapper wrapper = new TSDesiredStateVariableWrapper();
        
        remaining = trim(aString);
        while ((len(trim(remaining)) > 0)) {
            position = UNRESOLVED.pos("&", remaining);
            if (position > 0) {
                phrase = UNRESOLVED.Copy(remaining, 1, position - 1);
            } else {
                phrase = remaining;
            }
            phrase = trim(phrase);
            if (UNRESOLVED.pos("~", phrase) == 1) {
                desiredState = TSVariableState.kAbsent;
            } else {
                desiredState = TSVariableState.kPresent;
            }
            if (desiredState == TSVariableState.kAbsent) {
                phrase = trim(UNRESOLVED.Copy(phrase, 2, len(phrase)));
            }
            variable = this.world.findOrCreateVariable(phrase, false);
            if (list == this.requirements) {
                // need to distinguish for context list
                wrapper = TSDesiredStateVariableWrapper.create;
            } else {
                wrapper = TSDesiredStateVariableWrapper.create;
            }
            wrapper.variable = variable;
            wrapper.desiredState = desiredState;
            list.Add(wrapper);
            if (position > 0) {
                remaining = UNRESOLVED.Copy(remaining, position + 1, len(remaining));
            } else {
                remaining = "";
            }
        }
    }
    
    public String decompile(TList list) {
        result = "";
        int i = 0;
        TSDesiredStateVariableWrapper wrapper = new TSDesiredStateVariableWrapper();
        String item = "";
        
        result = "";
        for (i = 0; i <= list.Count - 1; i++) {
            // OK to cast requirements as this
            wrapper = TSDesiredStateVariableWrapper(list[i]);
            item = wrapper.leader() + wrapper.variable.phrase;
            if (result != "") {
                result = result + " & " + item;
            } else {
                result = item;
            }
        }
        return result;
    }
    
    public String decompileRequirements() {
        result = "";
        result = this.decompile(this.requirements);
        return result;
    }
    
    public String decompileChanges() {
        result = "";
        result = this.decompile(this.changes);
        return result;
    }
    
    public void updateAvailable() {
        int i = 0;
        TSDesiredStateVariableWrapper wrapper = new TSDesiredStateVariableWrapper();
        
        // assuming all field set up correctly and not nil
        this.available = false;
        if (this.context == this.world.emptyEntry) {
            //for now - really should do anyway - assuming unfinished
            return;
        }
        if (this.context.state != TSVariableState.kPresent) {
            return;
        }
        i = 0;
        while (i < this.requirements.Count) {
            wrapper = TSDesiredStateVariableWrapper(this.requirements.Items[i]);
            if (wrapper.variable.getState() != wrapper.desiredState) {
                return;
            }
            i = i + 1;
        }
        this.available = true;
    }
    
    public void recordReplyMoveChanges(TList changedVariablesList, String totalReply, TSVariable contextToFocusTo) {
        raise "method recordReplyMoveChanges had assigned to var parameter contextToFocusTo not added to return; fixup manually"
        int i = 0;
        TSDesiredStateVariableWrapper desiredStateWrapper = new TSDesiredStateVariableWrapper();
        TSChangedVariableWrapper changedVariableWrapper = new TSChangedVariableWrapper();
        
        if ((totalReply != "") && (this.reply != "")) {
            totalReply = totalReply + " ";
        }
        totalReply = totalReply + this.reply;
        if (this.move != this.world.emptyEntry) {
            contextToFocusTo = this.move;
        }
        i = 0;
        while (i < this.changes.Count) {
            desiredStateWrapper = TSDesiredStateVariableWrapper(this.changes[i]);
            changedVariableWrapper = TSChangedVariableWrapper().createWithVariableNewState(desiredStateWrapper.variable, desiredStateWrapper.desiredState);
            changedVariablesList.Add(changedVariableWrapper);
            i = i + 1;
        }
        return totalReply;
    }
    
    public void setTextForField(int col, String text) {
        switch (col) {
            case kRuleContext:
                this.setContext(text);
                break;
            case kRuleCommand:
                this.setCommand(text);
                break;
            case kRuleReply:
                this.setReply(text);
                break;
            case kRuleMove:
                this.setMove(text);
                break;
            case kRuleRequirements:
                this.setRequirements(text);
                break;
            case kRuleChanges:
                this.setChanges(text);
                break;
    }
    
    public String getTextForField(int col) {
        result = "";
        switch (col) {
            case kRuleContext:
                result = this.context.phrase;
                break;
            case kRuleCommand:
                result = this.command.phrase;
                break;
            case kRuleReply:
                result = this.reply;
                break;
            case kRuleMove:
                result = this.move.phrase;
                break;
            case kRuleRequirements:
                result = this.requirementsString;
                break;
            case kRuleChanges:
                result = this.changesString;
                break;
        return result;
    }
    
    public String headerForField(int col) {
        result = "";
        switch (col) {
            case kRuleContext:
                result = "Context";
                break;
            case kRuleCommand:
                result = "Command";
                break;
            case kRuleReply:
                result = "Reply";
                break;
            case kRuleMove:
                result = "Move";
                break;
            case kRuleRequirements:
                result = "Requirements";
                break;
            case kRuleChanges:
                result = "Changes";
                break;
        return result;
    }
    
    public boolean usesVariableInList(TSVariable variable, TList list) {
        result = false;
        int i = 0;
        TSDesiredStateVariableWrapper wrapper = new TSDesiredStateVariableWrapper();
        
        result = true;
        for (i = 0; i <= list.Count - 1; i++) {
            wrapper = TSDesiredStateVariableWrapper(list.Items[i]);
            if (wrapper.variable == variable) {
                return result;
            }
        }
        result = false;
        return result;
    }
    
    public boolean usesVariableFor(TSVariable variable, int col) {
        result = false;
        result = false;
        switch (col) {
            case kRuleContext:
                result = this.context == variable;
                break;
            case kRuleCommand:
                result = this.command == variable;
                break;
            case kRuleReply:
                // error
                result = false;
                break;
            case kRuleMove:
                result = this.move == variable;
                break;
            case kRuleRequirements:
                result = this.usesVariableInList(variable, this.requirements);
                break;
            case kRuleChanges:
                result = this.usesVariableInList(variable, this.changes);
                break;
        return result;
    }
    
    public TSVariable variableInList(int n, TList list) {
        result = new TSVariable();
        TSDesiredStateVariableWrapper wrapper = new TSDesiredStateVariableWrapper();
        
        if (n < 0) {
            n = 0;
        }
        if (list.Count > n) {
            wrapper = TSDesiredStateVariableWrapper(list.Items[n]);
            result = wrapper.variable;
        } else {
            result = this.world.emptyEntry;
        }
        return result;
    }
    
    public TSVariable variableForFieldWithSelections(int col, int requirementsIndex, int changesIndex) {
        result = new TSVariable();
        result = this.world.emptyEntry;
        switch (col) {
            case kRuleContext:
                result = this.context;
                break;
            case kRuleCommand:
                result = this.command;
                break;
            case kRuleReply:
                // error
                result = this.world.emptyEntry;
                break;
            case kRuleMove:
                result = this.move;
                break;
            case kRuleRequirements:
                result = this.variableInList(requirementsIndex, this.requirements);
                break;
            case kRuleChanges:
                result = this.variableInList(changesIndex, this.changes);
                break;
        return result;
    }
    
    public TSVariable variableForField(int col) {
        result = new TSVariable();
        result = this.variableForFieldWithSelections(col, 0, 0);
        return result;
    }
    
    public void setPosition(String value) {
        String firstPart = "";
        String secondPart = "";
        String thirdPart = "";
        String rest = "";
        
        firstPart = UNRESOLVED.Copy(value, 1, UNRESOLVED.pos("|", value) - 1);
        rest = UNRESOLVED.Copy(value, len(firstPart) + 2, len(value));
        secondPart = UNRESOLVED.Copy(rest, 1, UNRESOLVED.pos("|", rest) - 1);
        thirdPart = UNRESOLVED.Copy(rest, len(secondPart) + 2, len(rest));
        super.setPosition(firstPart);
        this.context.setPosition(secondPart);
        this.move.setPosition(thirdPart);
    }
    
}
class TSIndexChangeRuleWrapper extends TObject {
    public TSRule rule;
    public int oldIndex;
    public int newIndex;
    
    //////////////////// TSIndexChangeRuleWrapper ///////////////////////////
    public void createWithRuleNewIndex(TSRule rule, int newIndex) {
        this.rule = rule;
        this.oldIndex = rule.world.rules.IndexOf(rule);
        this.newIndex = newIndex;
    }
    
    public void doChange() {
        if (this.oldIndex == this.newIndex) {
            return;
        }
        if (this.newIndex >= 0) {
            this.rule.world.rules.Move(this.oldIndex, this.newIndex);
        } else {
            this.rule.world.rules.Delete(this.oldIndex);
        }
    }
    
    public void undoChange() {
        if (this.oldIndex == this.newIndex) {
            return;
        }
        if (this.newIndex >= 0) {
            this.rule.world.rules.Move(this.newIndex, this.oldIndex);
        } else {
            this.rule.world.rules.Insert(this.oldIndex, this.rule);
        }
    }
    
}
class TWorld extends TObject {
    public TSVariable emptyEntry;
    public TList variables;
    public TList rules;
    public TSVariable focus;
    public TSVariable previousFocus;
    public int firstCommandDoneForLastCommandPhrase;
    public String lastVariableCreated;
    
    //////////////////// TWorld ///////////////////////////
    public void Create() {
        this.variables = delphi_compatability.TList().Create();
        this.rules = delphi_compatability.TList().Create();
        this.emptyEntry = TSVariable.create;
    }
    
    public void resetVariableValues() {
        int i = 0;
        
        if (this.variables != null) {
            if (this.variables.Count > 0) {
                for (i = 0; i <= this.variables.Count - 1; i++) {
                    TSVariable(this.variables.Items[i]).state = TSVariableState.kAbsent;
                }
            }
        }
    }
    
    public void resetVariablesAndRules() {
        int i = 0;
        
        if (this.rules != null) {
            if (this.rules.Count > 0) {
                for (i = 0; i <= this.rules.Count - 1; i++) {
                    TSRule(this.rules.Items[i]).free;
                }
            }
            this.rules.Clear();
        }
        if (this.variables != null) {
            if (this.variables.Count > 0) {
                for (i = 0; i <= this.variables.Count - 1; i++) {
                    TSVariable(this.variables.Items[i]).free;
                }
            }
            this.variables.Clear();
        }
    }
    
    public void Destroy() {
        this.resetVariablesAndRules();
        this.rules.free;
        this.rules = null;
        this.variables.free;
        this.variables = null;
        this.emptyEntry.free;
        this.emptyEntry = null;
        super.Destroy();
    }
    
    public TSRule newRule() {
        result = new TSRule();
        result = TSRule().create();
        result.world = this;
        result.context = this.emptyEntry;
        result.command = this.emptyEntry;
        result.move = this.emptyEntry;
        if (usruleeditorform.RuleEditorForm != null) {
            result.position = usruleeditorform.RuleEditorForm.goodPosition();
        }
        this.rules.Add(result);
        return result;
    }
    
    public TSVariable findVariable(String aString) {
        result = new TSVariable();
        int i = 0;
        
        result = null;
        if (aString == "") {
            result = this.emptyEntry;
            return result;
        }
        if (this.variables.Count > 0) {
            for (i = 0; i <= this.variables.Count - 1; i++) {
                if (UNRESOLVED.AnsiCompareText(TSVariable(this.variables.Items[i]).phrase, aString) == 0) {
                    result = TSVariable(this.variables.Items[i]);
                    if (TSVariable(this.variables.Items[i]).phrase != aString) {
                        // take on the case of the last choice if not the same
                        TSVariable(this.variables.Items[i]).setPhrase(aString);
                    }
                    return result;
                }
            }
        }
        return result;
    }
    
    public TSVariable findOrCreateVariable(String aString, boolean madeByMacro) {
        result = new TSVariable();
        result = this.findVariable(trim(aString));
        if (result != null) {
            return result;
        }
        result = TSVariable.create;
        result.world = this;
        result.setPhrase(trim(aString));
        // directly set for now - otherwise circular error on startup...
        result.state = TSVariableState.kAbsent;
        if (usruleeditorform.RuleEditorForm != null) {
            result.position = usruleeditorform.RuleEditorForm.goodPosition();
        }
        this.variables.Add(result);
        this.lastVariableCreated = aString;
        return result;
    }
    
    public void setInitialFocus() {
        if (this.rules.Count > 0) {
            this.focus = TSRule(this.rules[0]).context;
            this.previousFocus = TSRule(this.rules[0]).context;
            this.focus.state = TSVariableState.kPresent;
            this.updateAvailable();
        } else {
            this.focus = null;
            this.previousFocus = null;
        }
    }
    
    public void newWorld() {
        this.resetVariablesAndRules();
        this.focus = null;
        this.previousFocus = null;
    }
    
    public boolean loadWorldFromFile(String name) {
        result = false;
        TextFile WorldFile = new TextFile();
        String value = "";
        TSRule rule = new TSRule();
        int count = 0;
        String header = "";
        
        result = false;
        usconsoleform.ConsoleForm.reportMode("Loading");
        AssignFile(WorldFile, name);
        Reset(WorldFile);
        try {
            // done by caller to allow merges
            //self.resetVariablesAndRules;
            count = 0;
            // unfinished - need better error checking
            UNRESOLVED.readln(WorldFile, header);
            if ((header != "; world file version 1.0") && (header != "; StoryHarp world file version 1.3")) {
                ShowMessage("File header for world file is not correct");
                return result;
            }
            while (!UNRESOLVED.eof(WorldFile)) {
                UNRESOLVED.readln(WorldFile, value);
                if ((value != "") && (value[1] == ";")) {
                    continue;
                }
                if (value != "====================") {
                    return result;
                }
                if (count == 0) {
                    // context
                    UNRESOLVED.readln(WorldFile, value);
                    // command
                    UNRESOLVED.readln(WorldFile, value);
                    // reply
                    UNRESOLVED.readln(WorldFile, value);
                    // move to
                    UNRESOLVED.readln(WorldFile, value);
                    // extra changes
                    UNRESOLVED.readln(WorldFile, value);
                    // extra requirements
                    UNRESOLVED.readln(WorldFile, value);
                    // map positions
                    UNRESOLVED.readln(WorldFile, value);
                } else {
                    rule = this.newRule();
                    UNRESOLVED.readln(WorldFile, value);
                    rule.setContext(trim(value));
                    UNRESOLVED.readln(WorldFile, value);
                    rule.setCommand(trim(value));
                    UNRESOLVED.readln(WorldFile, value);
                    rule.setReply(trim(value));
                    UNRESOLVED.readln(WorldFile, value);
                    rule.setMove(trim(value));
                    UNRESOLVED.readln(WorldFile, value);
                    rule.setChanges(trim(value));
                    UNRESOLVED.readln(WorldFile, value);
                    rule.setRequirements(trim(value));
                    UNRESOLVED.readln(WorldFile, value);
                    rule.setPosition(trim(value));
                }
                count = count + 1;
            }
        } finally {
            CloseFile(WorldFile);
            usconsoleform.ConsoleForm.reportMode("Running");
        }
        result = true;
        return result;
    }
    
    public void saveWorldToFile(String name, boolean saveOnlySelectedRules) {
        int i = 0;
        TextFile WorldFile = new TextFile();
        TSRule rule = new TSRule();
        
        AssignFile(WorldFile, name);
        Rewrite(WorldFile);
        usconsoleform.ConsoleForm.reportMode("Saving");
        try {
            // 1.0 had all lower case
            // 1.3 supports mixed case
            writeln(WorldFile, "; StoryHarp world file version 1.3");
            writeln(WorldFile, "====================");
            for (i = 0; i <= 5; i++) {
                writeln(WorldFile, TSRule.headerForField(i));
            }
            writeln(WorldFile, "map positions");
            for (i = 0; i <= this.rules.Count - 1; i++) {
                rule = TSRule(this.rules.Items[i]);
                if (saveOnlySelectedRules && !rule.selected) {
                    continue;
                }
                writeln(WorldFile, "====================");
                writeln(WorldFile, rule.context.phrase);
                writeln(WorldFile, rule.command.phrase);
                writeln(WorldFile, rule.reply);
                writeln(WorldFile, rule.move.phrase);
                writeln(WorldFile, rule.changesString);
                writeln(WorldFile, rule.requirementsString);
                writeln(WorldFile, rule.position.X, ",", rule.position.Y, "|", rule.context.position.X, ",", rule.context.position.Y, "|", rule.move.position.X, ",", rule.move.position.Y);
            }
            Flush(WorldFile);
        } finally {
            CloseFile(WorldFile);
            usconsoleform.ConsoleForm.reportMode("Running");
        }
    }
    
    public void newSession() {
        this.resetVariableValues();
        this.setInitialFocus();
    }
    
    public boolean loadSessionFromFile(String name, String worldFileName) {
        result = false;
        TextFile SessionFile = new TextFile();
        TSVariable variable = new TSVariable();
        String header = "";
        String worldFileNameRead = "";
        String variableNameRead = "";
        String focusNameRead = "";
        String previousFocusNameRead = "";
        String completeWorldFileName = "";
        
        result = false;
        AssignFile(SessionFile, name);
        Reset(SessionFile);
        try {
            this.resetVariableValues();
            // unfinished - need better error checking
            UNRESOLVED.readln(SessionFile, header);
            if (header != "; session file version 1.0") {
                ShowMessage("File header for session file is not correct");
                return result;
            }
            UNRESOLVED.readln(SessionFile, header);
            if (header != "============ Variables for world =================") {
                return result;
            }
            UNRESOLVED.readln(SessionFile, worldFileNameRead);
            if (worldFileNameRead != worldFileName) {
                completeWorldFileName = findCompleteWorldFileName(worldFileNameRead);
                if (completeWorldFileName != "") {
                    usruleeditorform.RuleEditorForm.openWorldFile(completeWorldFileName);
                    // to counteract resetting session when load world
                    usdomain.domain.sessionFileName = name;
                } else {
                    return result;
                }
            }
            UNRESOLVED.readln(SessionFile, header);
            if (header != "============ Focus ===============================") {
                return result;
            }
            UNRESOLVED.readln(SessionFile, focusNameRead);
            UNRESOLVED.readln(SessionFile, previousFocusNameRead);
            UNRESOLVED.readln(SessionFile, header);
            if (header != "============ Variables ===========================") {
                return result;
            }
            while (!UNRESOLVED.eof(SessionFile)) {
                UNRESOLVED.readln(SessionFile, variableNameRead);
                variableNameRead = trim(variableNameRead);
                variable = this.findOrCreateVariable(variableNameRead, false);
                variable.state = TSVariableState.kPresent;
            }
        } finally {
            CloseFile(SessionFile);
        }
        this.focus = this.findOrCreateVariable(focusNameRead, false);
        this.previousFocus = this.findOrCreateVariable(previousFocusNameRead, false);
        this.updateAvailable();
        result = true;
        return result;
    }
    
    public void saveSessionToFile(String name, String worldFileName) {
        int i = 0;
        TextFile SessionFile = new TextFile();
        TSVariable variable = new TSVariable();
        
        AssignFile(SessionFile, name);
        Rewrite(SessionFile);
        try {
            writeln(SessionFile, "; session file version 1.0");
            writeln(SessionFile, "============ Variables for world =================");
            writeln(SessionFile, worldFileName);
            writeln(SessionFile, "============ Focus ===============================");
            writeln(SessionFile, this.focus.phrase);
            writeln(SessionFile, this.previousFocus.phrase);
            writeln(SessionFile, "============ Variables ===========================");
            for (i = 0; i <= this.variables.Count - 1; i++) {
                variable = TSVariable(this.variables.Items[i]);
                if (variable.state == TSVariableState.kPresent) {
                    writeln(SessionFile, variable.phrase);
                }
            }
            Flush(SessionFile);
        } finally {
            CloseFile(SessionFile);
        }
    }
    
    public void updateAvailable() {
        TSRule rule = new TSRule();
        int i = 0;
        
        if (this.rules.Count > 0) {
            for (i = 0; i <= this.rules.Count - 1; i++) {
                rule = TSRule(this.rules.Items[i]);
                rule.updateAvailable();
            }
        }
    }
    
    public void setFocusTo(TSVariable contextToFocusOn) {
        if (contextToFocusOn != null) {
            this.previousFocus = this.focus;
            this.previousFocus.setState(TSVariableState.kAbsent);
            this.focus = contextToFocusOn;
            this.focus.setState(TSVariableState.kPresent);
        }
    }
    
    // retruns whether should redraw grid
    public boolean deselectAllExcept(TSDraggableObject exceptObject) {
        result = false;
        TSRule rule = new TSRule();
        TSVariable variable = new TSVariable();
        int i = 0;
        
        result = false;
        for (i = 0; i <= this.rules.Count - 1; i++) {
            rule = TSRule(this.rules[i]);
            if ((rule.selected) && (rule != exceptObject)) {
                rule.selected = false;
                result = true;
            }
        }
        for (i = 0; i <= this.variables.Count - 1; i++) {
            variable = TSVariable(this.variables[i]);
            if ((variable.selected) && (variable != exceptObject)) {
                variable.selected = false;
                result = true;
            }
        }
        return result;
    }
    
    public void addDragRecordsToList(TList dragRecords) {
        TSRule rule = new TSRule();
        TSVariable variable = new TSVariable();
        int i = 0;
        
        for (i = 0; i <= this.rules.Count - 1; i++) {
            rule = TSRule(this.rules[i]);
            if ((rule.selected)) {
                dragRecords.Add(TSDragRecord().createWithNode(rule));
            }
        }
        for (i = 0; i <= this.variables.Count - 1; i++) {
            variable = TSVariable(this.variables[i]);
            if ((variable.selected)) {
                dragRecords.Add(TSDragRecord().createWithNode(variable));
            }
        }
    }
    
    public void deleteSelectedRules() {
        TSRule rule = new TSRule();
        int i = 0;
        TSDeleteRulesCommand command = new TSDeleteRulesCommand();
        
        command = uscommands.TSDeleteRulesCommand().create();
        for (i = 0; i <= this.rules.Count - 1; i++) {
            rule = TSRule(this.rules[i]);
            if ((rule.selected)) {
                command.addRule(rule, -1);
            }
        }
        if (command.ruleWrappers.Count > 0) {
            usdomain.domain.worldCommandList.doCommand(command);
        } else {
            command.free;
        }
    }
    
    public void raiseSelectedRules() {
        TSRule rule = new TSRule();
        TSRule higherRule = new TSRule();
        int i = 0;
        TSMoveRulesCommand command = new TSMoveRulesCommand();
        boolean moving = false;
        
        command = uscommands.TSMoveRulesCommand().create();
        command.action = "raise";
        moving = false;
        for (i = 1; i <= this.rules.Count - 1; i++) {
            //skip first
            rule = TSRule(this.rules[i]);
            if (rule.selected) {
                if (!moving) {
                    higherRule = TSRule(this.rules[i - 1]);
                    if (!higherRule.selected) {
                        moving = true;
                    }
                }
                if (moving) {
                    command.addRule(rule, i - 1);
                }
            } else {
                moving = true;
            }
        }
        if (command.ruleWrappers.Count > 0) {
            usdomain.domain.worldCommandList.doCommand(command);
        } else {
            command.free;
        }
    }
    
    public void lowerSelectedRules() {
        TSRule rule = new TSRule();
        TSRule lowerRule = new TSRule();
        int i = 0;
        TSMoveRulesCommand command = new TSMoveRulesCommand();
        boolean moving = false;
        
        command = uscommands.TSMoveRulesCommand().create();
        command.action = "lower";
        moving = false;
        for (i = this.rules.Count - 2; i >= 0; i--) {
            //skip first
            rule = TSRule(this.rules[i]);
            if (rule.selected) {
                if (!moving) {
                    lowerRule = TSRule(this.rules[i + 1]);
                    if (!lowerRule.selected) {
                        moving = true;
                    }
                }
                if (moving) {
                    command.addRule(rule, i + 1);
                }
            } else {
                moving = true;
            }
        }
        if (command.ruleWrappers.Count > 0) {
            usdomain.domain.worldCommandList.doCommand(command);
        } else {
            command.free;
        }
    }
    
    public void selectAvailable() {
        TSRule rule = new TSRule();
        int i = 0;
        
        for (i = 0; i <= this.rules.Count - 1; i++) {
            rule = TSRule(this.rules[i]);
            rule.selected = rule.available;
        }
    }
    
    public TSRule firstAvailable() {
        result = new TSRule();
        TSRule rule = new TSRule();
        int i = 0;
        
        for (i = 0; i <= this.rules.Count - 1; i++) {
            rule = TSRule(this.rules[i]);
            if (rule.available) {
                result = rule;
                return result;
            }
        }
        result = null;
        return result;
    }
    
    public void selectInRectangle(TRect rect) {
        TRect intersection = new TRect();
        int i = 0;
        TSRule rule = new TSRule();
        TSVariable variable = new TSVariable();
        
        if (Rect.right < Rect.left) {
            Rect.left, Rect.right = swapIntegers(Rect.left, Rect.right);
        }
        if (Rect.bottom < Rect.top) {
            Rect.top, Rect.bottom = swapIntegers(Rect.top, Rect.bottom);
        }
        for (i = 0; i <= this.rules.Count - 1; i++) {
            rule = TSRule(this.rules[i]);
            delphi_compatability.IntersectRect(intersection, rule.bounds(), Rect);
            if (!delphi_compatability.IsRectEmpty(intersection)) {
                rule.selected = true;
            }
        }
        for (i = 0; i <= this.variables.Count - 1; i++) {
            variable = TSVariable(this.variables[i]);
            delphi_compatability.IntersectRect(intersection, variable.bounds(), Rect);
            if (!delphi_compatability.IsRectEmpty(intersection)) {
                variable.selected = true;
            }
        }
    }
    
    public TSVariable firstSelectedVariable() {
        result = new TSVariable();
        int i = 0;
        TSVariable variable = new TSVariable();
        
        result = null;
        for (i = 0; i <= this.variables.Count - 1; i++) {
            variable = TSVariable(this.variables[i]);
            if (variable.selected) {
                result = variable;
                return result;
            }
        }
        return result;
    }
    
    public TSDraggableObject firstSelectedObject() {
        result = new TSDraggableObject();
        int i = 0;
        TSVariable variable = new TSVariable();
        TSRule rule = new TSRule();
        
        result = null;
        for (i = 0; i <= this.variables.Count - 1; i++) {
            variable = TSVariable(this.variables[i]);
            if (variable.selected) {
                result = variable;
                return result;
            }
        }
        for (i = 0; i <= this.rules.Count - 1; i++) {
            rule = TSRule(this.rules[i]);
            if (rule.selected) {
                result = rule;
                return result;
            }
        }
        return result;
    }
    
    public void addContextsToCombBox(TComboBox comboBox) {
        int i = 0;
        TSVariable variable = new TSVariable();
        
        comboBox.Clear();
        for (i = 0; i <= this.variables.Count - 1; i++) {
            variable = TSVariable(this.variables[i]);
            if (variable.contextUseages > 0) {
                comboBox.Items.AddObject(variable.phrase, variable);
            }
        }
    }
    
    public void addContextsToListBox(TListBox listBox) {
        int i = 0;
        TSVariable variable = new TSVariable();
        
        listBox.Clear();
        for (i = 0; i <= this.variables.Count - 1; i++) {
            variable = TSVariable(this.variables[i]);
            if (variable.contextUseages > 0) {
                listBox.Items.AddObject(variable.phrase, variable);
            }
        }
    }
    
    public TRect boundsRect() {
        result = new TRect();
        TSDraggableObject node = new TSDraggableObject();
        int i = 0;
        
        result.Top = 0;
        result.Bottom = 0;
        result.Left = 0;
        result.Right = 0;
        for (i = 0; i <= this.variables.Count - 1; i++) {
            node = this.variables[i];
            if (result.Left > node.position.X) {
                result.Left = node.position.X;
            }
            if (result.Right < node.position.X + node.extent.X) {
                result.Right = node.position.X + node.extent.X;
            }
            if (result.Top > node.position.Y) {
                result.Top = node.position.Y;
            }
            if (result.Bottom < node.position.Y + node.extent.Y) {
                result.Bottom = node.position.Y + node.extent.Y;
            }
        }
        for (i = 0; i <= this.rules.Count - 1; i++) {
            node = this.rules[i];
            if (result.Left > node.position.X) {
                result.Left = node.position.X;
            }
            if (result.Right < node.position.X + node.extent.X) {
                result.Right = node.position.X + node.extent.X;
            }
            if (result.Top > node.position.Y) {
                result.Top = node.position.Y;
            }
            if (result.Bottom < node.position.Y + node.extent.Y) {
                result.Bottom = node.position.Y + node.extent.Y;
            }
        }
        return result;
    }
    
    public void updateVariablesForIndexInVariables() {
        int i = 0;
        TSVariable variable = new TSVariable();
        
        for (i = 0; i <= this.variables.Count - 1; i++) {
            variable = TSVariable(this.variables[i]);
            variable.indexInVariables = i;
        }
    }
    
}
