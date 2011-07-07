package com.kurtz_fernhout.storyharp;

import java.util.Vector;

import com.kurtz_fernhout.storyharp.TSVariable.TSVariableState;

public class TSRule extends TSDraggableObject {
    public TWorld world;
    public TSVariable context;
    public Vector<TSDesiredStateVariableWrapper> requirements = new Vector<TSDesiredStateVariableWrapper>();
    public TSVariable command;
    public String reply = "";
    public TSVariable move;
    public Vector<TSDesiredStateVariableWrapper> changes = new Vector<TSDesiredStateVariableWrapper>();
    public boolean available;
    public String requirementsString = "";
    public String changesString = "";
    public boolean useagesRemoved;
    // Kludge for displaying in just one list
    public String listString;
    
    public String toString() {
		if (listString != null) {
    		return listString;
    	} else {
    		return super.toString();
    	}
    }
    
    public TSVariable getEmptyEntry() {
    	return this.world.emptyEntry;
    }
    
    public TSVariable findOrCreateVariable(String phrase, boolean flag) {
    	return this.world.findOrCreateVariable(phrase, flag);
    }
    
    public String displayName() {
        String result = "";
        if (TSDomain.domain.options.showCommandPrefixInMap) {
            result = result + "> ";
        }
        if (this.command == null) {
        	result = result + "UNDEFINED_PHRASE";
        } else {
        	result = result + this.command.phrase;
        }
        return result;
    }
    
    public void addUseages() {
        this.context.contextUseages += 1;
        for (int i = 0; i < this.requirements.size(); i++) {
            this.requirements.get(i).variable.requirementsUseages += 1;
        }
        this.command.commandUseages += 1;
        this.move.moveUseages += 1;
        for (int i = 0; i < this.changes.size(); i++) {
            this.changes.get(i).variable.changesUseages += 1;
        }
        this.useagesRemoved = false;
    }
    
    public void removeUseages() {  
        this.context.contextUseages -= 1;
        for (int i = 0; i < this.requirements.size(); i++) {
        	this.requirements.get(i).variable.requirementsUseages -= 1;
        }
        this.command.commandUseages -= 1;
        this.move.moveUseages -= 1;
        for (int i = 0; i < this.changes.size(); i++) {
        	this.changes.get(i).variable.changesUseages -= 1;
        }
        this.useagesRemoved = true;
    }
    
    public void setContext(String aString) {
        if (this.context != null) {
            this.context.contextUseages -= 1;
        }
        this.context = this.findOrCreateVariable(aString, false);
        this.context.contextUseages += 1;
    }
    
    public void setRequirements(String aString) {
        this.requirementsString = aString;
        this.requirements.clear();
        this.compile(aString, this.requirements);
        if (this.requirements.size() > 0) {
            for (int i = 0; i < this.requirements.size(); i++) {
            	this.requirements.get(i).variable.requirementsUseages += 1;
            }
        }
    }
    
    public void setCommand(String aString) {
        if (this.command != null) {
            this.command.commandUseages -= 1;
        }
        this.command = this.findOrCreateVariable(aString, false);
        this.command.commandUseages += 1;
    }
    
    public void setReply(String aString) {
        String safeString = "";
        for (int i = 0; i < aString.length(); i++) {
        	char c = aString.charAt(i);
            if (c == 13) {
            	safeString += " ";
            } else if (c == 10) {
            	safeString += " ";
            } else {
            	safeString += c;
            }
        }
        this.reply = safeString;
    }
    
    public void setMove(String aString) {
        if (this.move != null) {
            this.move.moveUseages -= 1;
        }
        this.move = this.findOrCreateVariable(aString, false);
        this.move.moveUseages += 1;
    }
    
    public void setChanges(String aString) {
        this.changesString = aString;
        this.changes.clear();
        this.compile(aString, this.changes);
        if (this.changes.size() > 0) {
            for (int i = 0; i < this.changes.size(); i++) {
            	this.changes.get(i).variable.changesUseages += 1;
            }
        }
    }
    
    public void compile(String aString, Vector<TSDesiredStateVariableWrapper> list) {
        String phrase = "";
        TSVariable variable = null;
        TSVariableState desiredState = null;
        TSDesiredStateVariableWrapper wrapper = null;
        
        String remaining = aString.trim();
        int position = 0;
        while (remaining.trim().length() > 0) {
            position = remaining.indexOf("&");
            if (position > 0) {
                phrase = remaining.substring(0, position);
            } else {
                phrase = remaining;
            }
            phrase = phrase.trim();
            if (phrase.indexOf("~") == 0) {
                desiredState = TSVariableState.kAbsent;
            } else {
                desiredState = TSVariableState.kPresent;
            }
            if (desiredState == TSVariableState.kAbsent) {
                phrase = phrase.substring(1).trim();
            }
            variable = this.findOrCreateVariable(phrase, false);
            // need to distinguish for context list
            // PDF PORT -- THERE IS NOTHING DIFFERENT HERE: ISSUE?
            if (list == this.requirements) {
                wrapper = new TSDesiredStateVariableWrapper();
            } else {
                wrapper = new TSDesiredStateVariableWrapper();
            }
            wrapper.variable = variable;
            wrapper.desiredState = desiredState;
            list.add(wrapper);
            if (position > 0) {
            	// skip over &
                remaining = remaining.substring(position + 1);
            } else {
                remaining = "";
            }
        }
    }
    
    public String decompile(Vector<TSDesiredStateVariableWrapper> list) {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            // OK to cast requirements as this
        	TSDesiredStateVariableWrapper wrapper = list.get(i);
            String item = wrapper.leader() + wrapper.variable.phrase;
            if (!result.equals("")) {
                result = result + " & " + item;
            } else {
                result = item;
            }
        }
        return result;
    }
    
    public String decompileRequirements() {
        return this.decompile(this.requirements);
    }
    
    public String decompileChanges() {
        return this.decompile(this.changes);
    }
    
    public void updateAvailable() {
        // assuming all field set up correctly and not nil
        this.available = false;
        if (this.context == this.getEmptyEntry()) {
            //for now - really should do anyway - assuming unfinished
            return;
        }
        if (this.context.state != TSVariableState.kPresent) {
            return;
        }
        int i = 0;
        while (i < this.requirements.size()) {
        	TSDesiredStateVariableWrapper wrapper = this.requirements.get(i);
            if (wrapper.variable.getState() != wrapper.desiredState) {
                return;
            }
            i = i + 1;
        }
        this.available = true;
    }
    
    // Caller code in TSDoCommandPhrase:
    // this.newLastSaidTextWithMacros = rule.recordReplyMoveChanges(this.changedVariables, this.newLastSaidTextWithMacros, this.newFocus);
    public String recordReplyMoveChanges(Vector<TSChangedVariableWrapper> changedVariablesList, String totalReply, TSVariableHolder contextToFocusToHolder) {
        if ((!totalReply.equals("")) && (!this.reply.equals(""))) {
            totalReply = totalReply + " ";
        }
        totalReply = totalReply + this.reply;
        if (this.move != this.getEmptyEntry()) {
        	contextToFocusToHolder.variable = this.move;
        }
        int i = 0;
        while (i < this.changes.size()) {
        	TSDesiredStateVariableWrapper desiredStateWrapper = this.changes.get(i);
        	TSChangedVariableWrapper changedVariableWrapper = new TSChangedVariableWrapper(desiredStateWrapper.variable, desiredStateWrapper.desiredState);
            changedVariablesList.add(changedVariableWrapper);
            i = i + 1;
        }
        return totalReply;
    }
    
    public void setTextForField(int col, String text) {
        switch (col) {
            case TSVariableDisplayOptions.kRuleContext:
                this.setContext(text);
                break;
            case TSVariableDisplayOptions.kRuleCommand:
                this.setCommand(text);
                break;
            case TSVariableDisplayOptions.kRuleReply:
                this.setReply(text);
                break;
            case TSVariableDisplayOptions.kRuleMove:
                this.setMove(text);
                break;
            case TSVariableDisplayOptions.kRuleRequirements:
                this.setRequirements(text);
                break;
            case TSVariableDisplayOptions.kRuleChanges:
                this.setChanges(text);
                break;
        }
    }
    
    public String getTextForField(int col) {
        String result = "";
        switch (col) {
            case TSVariableDisplayOptions.kRuleContext:
                result = this.context.phrase;
                break;
            case TSVariableDisplayOptions.kRuleCommand:
                result = this.command.phrase;
                break;
            case TSVariableDisplayOptions.kRuleReply:
                result = this.reply;
                break;
            case TSVariableDisplayOptions.kRuleMove:
                result = this.move.phrase;
                break;
            case TSVariableDisplayOptions.kRuleRequirements:
                result = this.requirementsString;
                break;
            case TSVariableDisplayOptions.kRuleChanges:
                result = this.changesString;
                break;
        }
        return result;
    }
    
    public static String headerForField(int col) {
        String result = "";
        switch (col) {
            case TSVariableDisplayOptions.kRuleContext:
                result = "Context";
                break;
            case TSVariableDisplayOptions.kRuleCommand:
                result = "Command";
                break;
            case TSVariableDisplayOptions.kRuleReply:
                result = "Reply";
                break;
            case TSVariableDisplayOptions.kRuleMove:
                result = "Move";
                break;
            case TSVariableDisplayOptions.kRuleRequirements:
                result = "Requirements";
                break;
            case TSVariableDisplayOptions.kRuleChanges:
                result = "Changes";
                break;
        }
        return result;
    }
    
    public boolean usesVariableInList(TSVariable variable, Vector<TSDesiredStateVariableWrapper> list) {
        for (int i = 0; i < list.size(); i++) {
        	TSDesiredStateVariableWrapper wrapper = list.get(i);
            if (wrapper.variable == variable) {
                return true;
            }
        }
        return false;
    }
    
    public boolean usesVariableFor(TSVariable variable, int col) {
        boolean result = false;
        switch (col) {
            case TSVariableDisplayOptions.kRuleContext:
                result = this.context == variable;
                break;
            case TSVariableDisplayOptions.kRuleCommand:
                result = this.command == variable;
                break;
            case TSVariableDisplayOptions.kRuleReply:
                // error
                result = false;
                break;
            case TSVariableDisplayOptions.kRuleMove:
                result = this.move == variable;
                break;
            case TSVariableDisplayOptions.kRuleRequirements:
                result = this.usesVariableInList(variable, this.requirements);
                break;
            case TSVariableDisplayOptions.kRuleChanges:
                result = this.usesVariableInList(variable, this.changes);
                break;
        }
        return result;
    }
    
    public TSVariable variableInList(int n, Vector<TSDesiredStateVariableWrapper> list) {
    	TSVariable result = null;
        if (n < 0) {
            n = 0;
        }
        if (list.size() > n) {
        	TSDesiredStateVariableWrapper wrapper = list.get(n);
            result = wrapper.variable;
        } else {
            result = this.getEmptyEntry();
        }
        return result;
    }
    
    public TSVariable variableForFieldWithSelections(int col, int requirementsIndex, int changesIndex) {
    	TSVariable result = null;
        result = this.getEmptyEntry();
        switch (col) {
            case TSVariableDisplayOptions.kRuleContext:
                result = this.context;
                break;
            case TSVariableDisplayOptions.kRuleCommand:
                result = this.command;
                break;
            case TSVariableDisplayOptions.kRuleReply:
                // error
                result = this.getEmptyEntry();
                break;
            case TSVariableDisplayOptions.kRuleMove:
                result = this.move;
                break;
            case TSVariableDisplayOptions.kRuleRequirements:
                result = this.variableInList(requirementsIndex, this.requirements);
                break;
            case TSVariableDisplayOptions.kRuleChanges:
                result = this.variableInList(changesIndex, this.changes);
                break;
        }
        return result;
    }
    
    public TSVariable variableForField(int col) {
        return this.variableForFieldWithSelections(col, 0, 0);
    }
    
    public void setPosition(String value) {
        String[] sections = value.split("\\|");
        if (sections.length != 3) {
        	System.out.println("setPosition: Problem with input value not in three parts: " + value);
        	System.out.println("section count: " + sections.length);
        }
        String firstPart = sections[0];
        String secondPart = sections[1];
        String thirdPart = sections[2];
        
        super.setPosition(firstPart);
        this.context.setPosition(secondPart);
        this.move.setPosition(thirdPart);
    }
    
}