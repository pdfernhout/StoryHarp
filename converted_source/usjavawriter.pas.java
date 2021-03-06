// unit usjavawriter

from conversion_common import *;
import usworld;
import usdomain;
import delphi_compatability;


class TSJavaWriter extends TObject {
    public TextFile javaFile;
    
    public void java(String line) {
        writeln(this.javaFile, line);
    }
    
    public String logicalStatementForRule(TSRule rule) {
        result = "";
        int i = 0;
        TSDesiredStateVariableWrapper wrapper = new TSDesiredStateVariableWrapper();
        
        result = "variableValue[" + IntToStr(rule.context.indexInVariables) + "]";
        for (i = 0; i <= rule.requirements.Count - 1; i++) {
            result = result + " && ";
            wrapper = usworld.TSDesiredStateVariableWrapper(rule.requirements.Items[i]);
            if (wrapper.desiredState == usworld.TSVariableState.kAbsent) {
                result = result + "!";
            }
            result = result + "variableValue[" + IntToStr(wrapper.variable.indexInVariables) + "]";
        }
        return result;
    }
    
    public String writeChangesForRule(TSRule rule) {
        result = "";
        int i = 0;
        TSDesiredStateVariableWrapper wrapper = new TSDesiredStateVariableWrapper();
        String varies = "";
        
        for (i = 0; i <= rule.changes.Count - 1; i++) {
            wrapper = usworld.TSDesiredStateVariableWrapper(rule.changes.Items[i]);
            varies = IntToStr(wrapper.variable.indexInVariables);
            if (wrapper.desiredState == usworld.TSVariableState.kAbsent) {
                this.java("      variableValue[" + varies + "] = false;");
            } else {
                this.java("      variableValue[" + varies + "] = true;");
            }
        }
        if (rule.move != usdomain.domain.world.emptyEntry) {
            varies = IntToStr(rule.move.indexInVariables);
            this.java("      move(" + varies + ");");
        }
        return result;
    }
    
    public String specialHandlingForReply(String reply) {
        result = "";
        int i = 0;
        
        result = "";
        for (i = 1; i <= len(reply); i++) {
            if (reply[i] == "\"") {
                result = result + "\\";
            }
            result = result + reply[i];
        }
        return result;
    }
    
    public void writeStoryFunctions() {
        TSRule firstRule = new TSRule();
        TSRule rule = new TSRule();
        String varies = "";
        int i = 0;
        
        firstRule = usworld.TSRule(usdomain.domain.world.rules[0]);
        varies = IntToStr(usdomain.domain.world.variables.Count);
        this.java("  int SHNumberVariables()");
        this.java("    {");
        this.java("    return " + varies + ";");
        this.java("    }");
        this.java("");
        varies = IntToStr(usdomain.domain.world.rules.Count);
        this.java("  int SHNumberRules()");
        this.java("    {");
        this.java("    return " + varies + ";");
        this.java("    }");
        this.java("");
        varies = IntToStr(firstRule.context.indexInVariables);
        this.java("  int SHFirstLocation()");
        this.java("    {");
        this.java("    return " + varies + ";");
        this.java("    }");
        this.java("");
        varies = IntToStr(firstRule.command.indexInVariables);
        this.java("  int SHFirstCommand()");
        this.java("    {");
        this.java("    return " + varies + ";");
        this.java("    }");
        this.java("");
        this.java("  void SHDefineVariables()");
        this.java("    {");
        for (i = 0; i <= usdomain.domain.world.variables.Count - 1; i++) {
            varies = this.specialHandlingForReply(usworld.TSVariable(usdomain.domain.world.variables[i]).phrase);
            this.java("    variableName[" + IntToStr(i) + "] = \"" + varies + "\";");
        }
        this.java("    }");
        this.java("");
        this.java("  void SHComputeSatisfiedRules()");
        this.java("    {");
        for (i = 0; i <= usdomain.domain.world.rules.Count - 1; i++) {
            rule = usworld.TSRule(usdomain.domain.world.rules[i]);
            this.java("    ruleSatisfied[" + IntToStr(i) + "] = " + this.logicalStatementForRule(rule) + ";");
        }
        this.java("    }");
        this.java("");
        this.java("  void SHAddAvailableCommands()");
        this.java("    {");
        for (i = 0; i <= usdomain.domain.world.rules.Count - 1; i++) {
            rule = usworld.TSRule(usdomain.domain.world.rules[i]);
            varies = IntToStr(rule.command.indexInVariables);
            this.java("    if (ruleSatisfied[" + IntToStr(i) + "]) addCommand(" + varies + ");");
        }
        this.java("    }");
        this.java("");
        this.java("  void SHDoCommand(int command)");
        this.java("    {");
        for (i = 0; i <= usdomain.domain.world.rules.Count - 1; i++) {
            rule = usworld.TSRule(usdomain.domain.world.rules[i]);
            varies = IntToStr(rule.command.indexInVariables);
            this.java("    if (command == " + varies + " && ruleSatisfied[" + IntToStr(i) + "])");
            this.java("      {");
            this.java("      reply(\"" + this.specialHandlingForReply(rule.reply) + "\");");
            this.writeChangesForRule(rule);
            this.java("      }");
        }
        this.java("    }");
        this.java("");
        this.java("  }");
    }
    
    public void writeJavaProgram(String filename) {
        TextFile javaTemplate = new TextFile();
        String line = "";
        
        if ((usdomain.domain.world.rules.Count < 1) || (usdomain.domain.world.variables.Count < 1)) {
            ShowMessage("Some rules and contexts must be defined first");
            return;
        }
        AssignFile(javaTemplate, ExtractFilePath(delphi_compatability.Application.exeName) + "Template.java");
        try {
            Reset(javaTemplate);
            AssignFile(this.javaFile, filename);
            try {
                Rewrite(this.javaFile);
                usdomain.domain.world.updateVariablesForIndexInVariables();
                while (!UNRESOLVED.eof(javaTemplate)) {
                    UNRESOLVED.readln(javaTemplate, line);
                    this.java(line);
                }
                this.writeStoryFunctions();
                Flush(this.javaFile);
            } finally {
                try {
                    CloseFile(this.javaFile);
                } catch (Exception e) {
                    ShowMessage("Problem closing java file " + filename);
                }
            }
        } finally {
            CloseFile(javaTemplate);
        }
    }
    
}
