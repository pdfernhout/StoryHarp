package com.kurtz_fernhout.storyharp;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import com.kurtz_fernhout.storyharp.TSVariable.TSVariableState;

public class TWorld {
	public final static boolean kSaveAllRules = false;
	public final static boolean kSaveOnlySelectedRules = true;
	
    public TSVariable emptyEntry = new TSVariable();
    public Vector<TSVariable> variables = new Vector<TSVariable>();
    public Vector<TSRule> rules = new Vector<TSRule>();
    public TSVariable focus;
    public TSVariable previousFocus;
    public int firstCommandDoneForLastCommandPhrase;
    public String lastVariableCreated = "";
    
	// For Windows compatability for now for testing identical output
	public String newLine = "\r\n";
	// String newLine = "\n";
    
    public void resetVariableValues() {
        for (TSVariable variable: variables) {
        	variable.state = TSVariableState.kAbsent;
        }
    }
    
    public void resetVariablesAndRules() {
        this.rules.clear();
        this.variables.clear();
    }
    
    public Point goodPosition() {
    	if (Application.RuleEditorForm != null) {
    		return Application.RuleEditorForm.goodPosition();
    	} else {
    		return new Point();
    	}
    }
    
    public TSRule newRule() {
        TSRule result = new TSRule();
        result.world = this;
        result.context = this.emptyEntry;
        result.command = this.emptyEntry;
        result.move = this.emptyEntry;
        result.position = this.goodPosition();
        this.rules.add(result);
        return result;
    }
    
    public TSVariable findVariable(String aString) {
        if (aString.equals("")) {
            return this.emptyEntry;
        }
        if (this.variables.size() > 0) {
            for (int i = 0; i < this.variables.size(); i++) {
                if (this.variables.get(i).phrase.equalsIgnoreCase(aString)) {
                	TSVariable result = this.variables.get(i);
                    if (!this.variables.get(i).phrase.equals(aString)) {
                        // take on the case of the last choice if not the same
                        this.variables.get(i).setPhrase(aString);
                    }
                    return result;
                }
            }
        }
        return null;
    }
    
    public TSVariable findOrCreateVariable(String aString, boolean madeByMacro) {
    	TSVariable result = null;
        result = this.findVariable(aString.trim());
        if (result != null) {
            return result;
        }
        result = new TSVariable();
        result.world = this;
        result.setPhrase(aString.trim());
        // directly set for now - otherwise circular error on startup...
        result.state = TSVariableState.kAbsent;
        result.position = this.goodPosition();
        this.variables.add(result);
        this.lastVariableCreated = aString;
        return result;
    }
    
    public void setInitialFocus() {
        if (this.rules.size() > 0) {
            this.focus = this.rules.get(0).context;
            this.previousFocus = this.rules.get(0).context;
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
    	BufferedReader inputStream;
		try {
			inputStream = new BufferedReader(new FileReader(name));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
        try {
            // reset is done by caller to allow merges
            //self.resetVariablesAndRules;
            int count = 0;
            // unfinished - need better error checking
            String header = inputStream.readLine();
            if (header == null || ((!header.equals("; world file version 1.0")) && (!header.equals("; StoryHarp world file version 1.3")))) {
            	UFileSupport.ShowMessage("File header for world file is not correct");
                return false;
            }
            while (true) {
            	String value = inputStream.readLine();
            	if (value == null) break;
                if ((!value.equals("")) && (value.charAt(0) == ';')) {
                    continue;
                }
                if (!value.equals("====================")) {
                    return false;
                }
                if (count == 0) {
                    // context
                	value = inputStream.readLine();
                    // command
                	value = inputStream.readLine();
                    // reply
                	value = inputStream.readLine();
                    // move to
                	value = inputStream.readLine();
                    // extra changes
                	value = inputStream.readLine();
                    // extra requirements
                	value = inputStream.readLine();
                    // map positions
                	value = inputStream.readLine();
                } else {
                	TSRule rule = this.newRule();
                	value = inputStream.readLine();
                    rule.setContext(value.trim());
                    value = inputStream.readLine();
                    rule.setCommand(value.trim());
                    value = inputStream.readLine();
                    rule.setReply(value.trim());
                    value = inputStream.readLine();
                    rule.setMove(value.trim());
                    value = inputStream.readLine();
                    rule.setChanges(value.trim());
                    value = inputStream.readLine();
                    rule.setRequirements(value.trim());
                    value = inputStream.readLine();
                    rule.setPosition(value.trim());
                }
                count = count + 1;
            }
        } catch (IOException e) {
			e.printStackTrace();
			return false;        	
        } finally {
            try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return true;
    }
    
    public boolean saveWorldToFile(String name, boolean saveOnlySelectedRules) {
    	FileWriter outputStream;
		try {
			outputStream = new FileWriter(name);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
        try {
            // 1.0 had all lower case
            // 1.3 supports mixed case
        	writeln(outputStream, "; StoryHarp world file version 1.3");
        	writeln(outputStream, "====================");
            for (int i = 0; i <= 5; i++) {
            	writeln(outputStream, TSRule.headerForField(i));
            }
            writeln(outputStream, "map positions");
            for (int i = 0; i < this.rules.size(); i++) {
            	TSRule rule = this.rules.get(i);
                if (saveOnlySelectedRules && !rule.selected) {
                    continue;
                }
                writeln(outputStream, "====================");
                writeln(outputStream, rule.context.phrase);
                writeln(outputStream, rule.command.phrase);
                writeln(outputStream, rule.reply);
                writeln(outputStream, rule.move.phrase);
                writeln(outputStream, rule.changesString);
                writeln(outputStream, rule.requirementsString);
                String positionInformation = rule.position.x + "," + rule.position.y + "|" + rule.context.position.x + "," + rule.context.position.y + "|" + rule.move.position.x + "," + rule.move.position.y;
                writeln(outputStream, positionInformation);
            }
            outputStream.flush();
        } catch (IOException e) {
			e.printStackTrace();
			return false;        	
        } finally {
            try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return true;
    }
    
    public void newSession() {
        this.resetVariableValues();
        this.setInitialFocus();
    }
    
    public String findCompleteWorldFileName(String worldFileNameRead) {
        String result = "";
        if (new File(worldFileNameRead).exists()) {
        	throw new Error("UNFINISHED");
            //result = UFileSupport.getFileOpenInfo(UFileSupport.kFileTypeWorld, worldFileNameRead, "Search for world file: " + worldFileNameRead, UFileSupport.kOtherExtNotOK);
        } else {
            result = worldFileNameRead;
        }
        return result;
    }
    
    public boolean loadSessionFromFile(String sessionFileName, String worldFileName) {
    	BufferedReader inputStream;
		try {
			inputStream = new BufferedReader(new FileReader(sessionFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
        TSVariable variable = new TSVariable();
        String focusNameRead = "";
        String previousFocusNameRead = "";
        try {
            this.resetVariableValues();
            // unfinished - need better error checking
            String header = inputStream.readLine();
            if (!header.equals("; session file version 1.0")) {
            	UFileSupport.ShowMessage("File header for session file is not correct");
                return false;
            }
            header = inputStream.readLine();
            if (!header.equals("============ Variables for world =================")) {
                return false;
            }
            String worldFileNameRead = inputStream.readLine();
            if (!worldFileNameRead.equals(worldFileName)) {
                String completeWorldFileName = findCompleteWorldFileName(worldFileNameRead);
                if (!completeWorldFileName.equals("")) {
                	// PDF PORT -- MAYBE WAIT CURSOR ISSUES?
                	TSDomain.domain.loadWorld(worldFileName);
                    // to counteract resetting session when load world
                    TSDomain.domain.sessionFileName = sessionFileName;  
                } else {
                    return false;
                }
            }
            header = inputStream.readLine();
            if (!header.equals("============ Focus ===============================")) {
                return false;
            }
            focusNameRead = inputStream.readLine();
            previousFocusNameRead = inputStream.readLine();
            header = inputStream.readLine();
            if (!header.equals("============ Variables ===========================")) {
                return false;
            }
            while (true) {
                String variableNameRead = inputStream.readLine();
                if (variableNameRead == null) break;
                variableNameRead = variableNameRead.trim();
                variable = this.findOrCreateVariable(variableNameRead, false);
                variable.state = TSVariableState.kPresent;
            }
        } catch (IOException e) {
			e.printStackTrace();
			return false;
        } finally {
        	try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        this.focus = this.findOrCreateVariable(focusNameRead, false);
        this.previousFocus = this.findOrCreateVariable(previousFocusNameRead, false);
        this.updateAvailable();
        return true;
    }
    
    public void writeln(FileWriter writer, String line) throws IOException {
    	writer.write(line);
    	writer.write(newLine);
    }
    
    public boolean saveSessionToFile(String sessionFileName, String worldFileName) {
    	FileWriter outputStream;
		try {
			outputStream = new FileWriter(sessionFileName);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
        try {
            writeln(outputStream, "; session file version 1.0");
            writeln(outputStream, "============ Variables for world =================");
            writeln(outputStream, worldFileName);
            writeln(outputStream, "============ Focus ===============================");
            writeln(outputStream, this.focus.phrase);
            writeln(outputStream, this.previousFocus.phrase);
            writeln(outputStream, "============ Variables ===========================");
            for (int i = 0; i < this.variables.size(); i++) {
            	TSVariable variable = this.variables.get(i);
                if (variable.state == TSVariableState.kPresent) {
                    writeln(outputStream, variable.phrase);
                }
            }
            outputStream.flush();
        } catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
            try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return true;
    }
    
    public void updateAvailable() {
        TSRule rule = new TSRule();
        int i = 0;
        
        if (this.rules.size() > 0) {
            for (i = 0; i < this.rules.size(); i++) {
                rule = this.rules.get(i);
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
    
    // returns whether should redraw grid
    public boolean deselectAllExcept(TSDraggableObject exceptObject) {
        boolean result = false;
        for (int i = 0; i < this.rules.size(); i++) {
        	TSRule rule = this.rules.get(i);
            if ((rule.selected) && (rule != exceptObject)) {
                rule.selected = false;
                result = true;
            }
        }
        for (int i = 0; i < this.variables.size(); i++) {
        	TSVariable variable = this.variables.get(i);
            if ((variable.selected) && (variable != exceptObject)) {
                variable.selected = false;
                result = true;
            }
        }
        return result;
    }
    
    public void addDragRecordsToList(Vector<TSDragRecord> dragRecords) {
        for (int i = 0; i < this.rules.size(); i++) {
        	TSRule rule = this.rules.get(i);
            if ((rule.selected)) {
                dragRecords.add(new TSDragRecord(rule));
            }
        }
        for (int i = 0; i < this.variables.size(); i++) {
        	TSVariable variable = this.variables.get(i);
            if ((variable.selected)) {
                dragRecords.add(new TSDragRecord(variable));
            }
        }
    }
    
    public void selectAvailable() {
        for (int i = 0; i < this.rules.size(); i++) {
        	TSRule rule = this.rules.get(i);
            rule.selected = rule.available;
        }
    }
    
    public TSRule firstAvailable() {   
        for (int i = 0; i < this.rules.size(); i++) {
            TSRule rule = this.rules.get(i);
            if (rule.available) {
                return rule;
            }
        }
        return null;
    }
    
    public void selectInRectangle(Rectangle rect) {
        if (rect.width < 0) {
        	// adding the negative width moves x to the left
        	rect.x = rect.x + rect.width;
        	rect.width = Math.abs(rect.width);
        }
        if (rect.height < 0) {
        	rect.y = rect.y + rect.height;
        	rect.height = Math.abs(rect.height);
        }
        for (int i = 0; i < this.rules.size(); i++) {
        	TSRule rule = this.rules.get(i);
            if (rule.bounds().intersects(rect)) {
                rule.selected = true;
            }
        }
        for (int i = 0; i < this.variables.size(); i++) {
        	TSVariable variable = this.variables.get(i);
            if (variable.bounds().intersects(rect)) {
                variable.selected = true;
            }
        }
    }
    
    public TSVariable firstSelectedVariable() {
        for (int i = 0; i < this.variables.size(); i++) {
        	TSVariable variable = this.variables.get(i);
            if (variable.selected) {
                return variable;
            }
        }
        return null;
    }
    
    public TSDraggableObject firstSelectedObject() {
        for (int i = 0; i < this.variables.size(); i++) {
        	TSVariable variable = this.variables.get(i);
            if (variable.selected) {
                return variable;
            }
        }
        for (int i = 0; i < this.rules.size(); i++) {
        	TSRule rule = this.rules.get(i);
            if (rule.selected) {
                return rule;
            }
        }
        return null;
    }
    
    public Rectangle boundsRect() {
        TSDraggableObject node = null;
        int i = 0;
        
        int top = 0;
        int bottom = 0;
        int left = 0;
        int right = 0;
        for (i = 0; i < this.variables.size(); i++) {
            node = this.variables.get(i);
            if (left > node.position.x) {
                left = node.position.x;
            }
            if (right < node.position.x + node.extent.x) {
                right = node.position.x + node.extent.x;
            }
            if (top > node.position.y) {
                top = node.position.y;
            }
            if (bottom < node.position.y + node.extent.y) {
                bottom = node.position.y + node.extent.y;
            }
        }
        for (i = 0; i < this.rules.size(); i++) {
            node = this.rules.get(i);
            if (left > node.position.x) {
                left = node.position.x;
            }
            if (right < node.position.x + node.extent.x) {
                right = node.position.x + node.extent.x;
            }
            if (top > node.position.y) {
                top = node.position.y;
            }
            if (bottom < node.position.y + node.extent.y) {
                bottom = node.position.y + node.extent.y;
            }
        }
        return new Rectangle(left, top, right - left, bottom - top);
    }
    
    public void updateVariablesForIndexInVariables() {
        int i = 0;
        TSVariable variable = new TSVariable();
        
        for (i = 0; i < this.variables.size(); i++) {
            variable = this.variables.get(i);
            variable.indexInVariables = i;
        }
    }
 
}
