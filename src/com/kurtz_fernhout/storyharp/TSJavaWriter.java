package com.kurtz_fernhout.storyharp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.kurtz_fernhout.storyharp.TSVariable.TSVariableState;

public class TSJavaWriter {
    public FileWriter javaFile;
    public TWorld world;
    
    public void java(String line) throws IOException {
		this.javaFile.write(line);
		this.javaFile.write("\n");
    }
    
    public String logicalStatementForRule(TSRule rule) {
        String result = "variableValue[" + String.valueOf(rule.context.indexInVariables) + "]";
        for (int i = 0; i < rule.requirements.size(); i++) {
            result = result + " && ";
            TSDesiredStateVariableWrapper wrapper = rule.requirements.get(i);
            if (wrapper.desiredState == TSVariableState.kAbsent) {
                result = result + "!";
            }
            result = result + "variableValue[" + String.valueOf(wrapper.variable.indexInVariables) + "]";
        }
        return result;
    }
    
    public void writeChangesForRule(TSRule rule) throws IOException {
        String varies = "";
        
        for (int i = 0; i < rule.changes.size(); i++) {
        	TSDesiredStateVariableWrapper wrapper = rule.changes.get(i);
            varies = String.valueOf(wrapper.variable.indexInVariables);
            if (wrapper.desiredState == TSVariableState.kAbsent) {
                this.java("      variableValue[" + varies + "] = false;");
            } else {
                this.java("      variableValue[" + varies + "] = true;");
            }
        }
        if (rule.move != world.emptyEntry) {
            varies = String.valueOf(rule.move.indexInVariables);
            this.java("      move(" + varies + ");");
        }
    }
    
    public String specialHandlingForReply(String reply) {
    	// escape any quotation marks
        String result = "";
        for (char c: reply.toCharArray()) {
        	if (c == '"') {
        		result = result + "\\";
        	}
        	result = result + Character.toString(c);
        }
        return result;
    }
    
    public void writeStoryFunctions() throws IOException {
    	TSRule rule = null;
    	String varies = null;
    	
        TSRule firstRule = world.rules.get(0);
        varies = String.valueOf(world.variables.size());
        this.java("  int SHNumberVariables()");
        this.java("    {");
        this.java("    return " + varies + ";");
        this.java("    }");
        this.java("");
        varies = String.valueOf(world.rules.size());
        this.java("  int SHNumberRules()");
        this.java("    {");
        this.java("    return " + varies + ";");
        this.java("    }");
        this.java("");
        varies = String.valueOf(firstRule.context.indexInVariables);
        this.java("  int SHFirstLocation()");
        this.java("    {");
        this.java("    return " + varies + ";");
        this.java("    }");
        this.java("");
        varies = String.valueOf(firstRule.command.indexInVariables);
        this.java("  int SHFirstCommand()");
        this.java("    {");
        this.java("    return " + varies + ";");
        this.java("    }");
        this.java("");
        this.java("  void SHDefineVariables()");
        this.java("    {");
        for (int i = 0; i < world.variables.size(); i++) {
        	varies = this.specialHandlingForReply(world.variables.get(i).phrase);
            this.java("    variableName[" + String.valueOf(i) + "] = \"" + varies + "\";");
        }
        this.java("    }");
        this.java("");
        this.java("  void SHComputeSatisfiedRules()");
        this.java("    {");
        for (int i = 0; i < world.rules.size(); i++) {
        	rule = world.rules.get(i);
            this.java("    ruleSatisfied[" + String.valueOf(i) + "] = " + this.logicalStatementForRule(rule) + ";");
        }
        this.java("    }");
        this.java("");
        this.java("  void SHAddAvailableCommands()");
        this.java("    {");
        for (int i = 0; i < world.rules.size(); i++) {
        	rule = world.rules.get(i);
            varies = String.valueOf(rule.command.indexInVariables);
            this.java("    if (ruleSatisfied[" + String.valueOf(i) + "]) addCommand(" + varies + ");");
        }
        this.java("    }");
        this.java("");
        this.java("  void SHDoCommand(int command)");
        this.java("    {");
        for (int i = 0; i < world.rules.size(); i++) {
        	rule = world.rules.get(i);
            varies = String.valueOf(rule.command.indexInVariables);
            this.java("    if (command ==  " + varies + " && ruleSatisfied[" + String.valueOf(i) + "])");
            this.java("      {");
            this.java("      reply(\"" + this.specialHandlingForReply(rule.reply) + "\");");
            this.writeChangesForRule(rule);
            this.java("      }");
        }
        this.java("    }");
        this.java("");
        this.java("  }");
    }
    
    public boolean writeJavaProgram(TWorld world, String filename) {
    	this.world = world;
    	
        if ((world.rules.size() < 1) || (world.variables.size() < 1)) {
        	UFileSupport.ShowMessage("Some rules and contexts must be defined first");
            return false;
        }

        String templateFilename = UFileSupport.getResourcesPathAndSeparator() + "Template.java";
    	BufferedReader javaTemplateInputStream;
		try {
			javaTemplateInputStream = new BufferedReader(new FileReader(templateFilename));
		} catch (FileNotFoundException e) {
			UFileSupport.ShowMessage("Could not find template file: " + templateFilename);
			return false;
		}
        try {
    		try {
    			this.javaFile = new FileWriter(filename);
    		} catch (IOException e) {
    			e.printStackTrace();
    			return false;
    		}
            try {
                world.updateVariablesForIndexInVariables();
                String line = javaTemplateInputStream.readLine();
                while (line != null) {
                    this.java(line);
                    line = javaTemplateInputStream.readLine();
                }
                this.writeStoryFunctions();
                this.javaFile.flush();
            } catch (IOException e) {
				e.printStackTrace();
				return false;
			} finally {
                try {
                    this.javaFile.close();
                } catch (IOException e) {
                	e.printStackTrace();
                	UFileSupport.ShowMessage("Problem closing java file " + filename);
                }
                this.javaFile = null;
            }
        } finally {
        	try {
				javaTemplateInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return true;
    }
    
}
