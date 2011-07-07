// unit uscommandwizard

from conversion_common import *;
import uschangelog;
import uscommands;
import usconsoleform;
import usruleeditorform;
import usworld;
import usdomain;
import delphi_compatability;

// var
TCommandWizardForm CommandWizardForm = new TCommandWizardForm();


// const
kStartPage = 0;
kContextPage = 1;
kCommandsPage = 2;
kSequencePage = 3;
kFinishPage = 4;



class TCommandWizardForm extends TForm {
    public TNotebook notebook;
    public TLabel Label1;
    public TLabel Label2;
    public TMemo NewCommandsMemo;
    public TCheckBox GenerateSequence;
    public TEdit PrefixEdit;
    public TLabel PrefixLabel;
    public TPanel sequenceEndPanel;
    public TLabel endSequenceLabel;
    public TRadioButton endSequenceLoopToFirst;
    public TRadioButton endSequenceRemoveTheLastCommand;
    public TRadioButton endSequenceLeaveLastCommand;
    public TButton helpButton;
    public TButton goBack;
    public TButton goNext;
    public TButton cancel;
    public TImage Image1;
    public TLabel Label7;
    public TLabel Label17;
    public TLabel Label26;
    public TImage Image3;
    public TLabel Label8;
    public TLabel Label11;
    public TImage Image2;
    public TLabel Label9;
    public TLabel Label12;
    public TImage Image4;
    public TLabel Label13;
    public TImage prefixArrow;
    public TImage sequenceEndArrow;
    public TLabel prefixNote;
    public TLabel Label18;
    public TLabel Label16;
    public TLabel Label20;
    public TLabel Label3;
    public TImage Image8;
    public TListBox ContextBox;
    public TImage commandStartPageImage;
    public TLabel Label5;
    public TImage contextStartPageImage;
    public TLabel Label6;
    public TImage replyStartPageImage;
    public TLabel Label10;
    public TImage contextListImage;
    public TLabel Label4;
    public TLabel Label14;
    public TLabel Label21;
    public TLabel Label22;
    public TLabel Label23;
    public TLabel Label19;
    public TLabel Label15;
    public TImage requirementsStartPageImage;
    public TImage changesStartPageImage;
    public TLabel Label24;
    public TLabel Label25;
    public TLabel Label27;
    public TLabel newCommandsForContextLabel;
    public String lastFloodedContextPrefix;
    public boolean generated;
    
    //$R *.DFM
    public void FormActivate(TObject Sender) {
        this.contextStartPageImage.Picture.Bitmap = usconsoleform.ConsoleForm.ContextButton.Glyph;
        this.commandStartPageImage.Picture.Bitmap = usconsoleform.ConsoleForm.CommandButton.Glyph;
        this.replyStartPageImage.Picture.Bitmap = usruleeditorform.RuleEditorForm.replyPicture.Picture.Bitmap;
        this.requirementsStartPageImage.Picture.Bitmap = usconsoleform.ConsoleForm.RequirementsButton.Glyph;
        this.changesStartPageImage.Picture.Bitmap = usconsoleform.ConsoleForm.ChangesButton.Glyph;
        this.contextListImage.Picture.Bitmap = usconsoleform.ConsoleForm.ContextButton.Glyph;
    }
    
    public boolean initialize() {
        result = false;
        TSVariable variable = new TSVariable();
        int i = 0;
        int j = 0;
        
        this.generated = false;
        this.notebook.PageIndex = kStartPage;
        usdomain.domain.world.addContextsToListBox(this.ContextBox);
        if (this.ContextBox.Items.Count < 1) {
            result = false;
            ShowMessage("You must create at least one context before using the command wizard.");
            return result;
        }
        //self.GenerateSequenceClick(self);
        this.NewCommandsMemo.Text = "";
        this.GenerateSequence.Checked = false;
        this.PrefixEdit.Text = "";
        this.endSequenceLoopToFirst.Checked = true;
        this.lastFloodedContextPrefix = "";
        for (i = 0; i <= usdomain.domain.world.variables.Count - 1; i++) {
            variable = usworld.TSVariable(usdomain.domain.world.variables[i]);
            if (variable.selected) {
                for (j = 0; j <= this.ContextBox.Items.Count - 1; j++) {
                    if (this.ContextBox.Items[j] == variable.phrase) {
                        this.ContextBox.ItemIndex = j;
                        break;
                    }
                }
            }
        }
        result = true;
        return result;
    }
    
    public void goNextClick(TObject Sender) {
        if (this.notebook.PageIndex == kFinishPage) {
            this.generateRules();
        } else if (this.notebook.PageIndex == kContextPage) {
            if ((this.ContextBox.ItemIndex < 0)) {
                ShowMessage("You must select a context to proceed.");
                return;
            }
            if ((trim(this.PrefixEdit.Text) == "") || (trim(this.PrefixEdit.Text) == this.lastFloodedContextPrefix)) {
                this.PrefixEdit.Text = this.ContextBox.Items[this.ContextBox.ItemIndex];
                this.lastFloodedContextPrefix = this.ContextBox.Items[this.ContextBox.ItemIndex];
            }
            this.newCommandsForContextLabel.Caption = "New commands for: " + this.ContextBox.Items[this.ContextBox.ItemIndex];
        } else if (this.notebook.PageIndex == kCommandsPage) {
            if ((trim(this.NewCommandsMemo.Text) == "")) {
                ShowMessage("You must enter one or more commands to proceed.");
                return;
            }
        } else if (this.notebook.PageIndex == kSequencePage) {
            if (this.GenerateSequence.Checked) {
                if ((trim(this.PrefixEdit.Text) == "")) {
                    ShowMessage("You must enter a prefix to proceed.");
                    return;
                }
            }
        }
        if (this.notebook.PageIndex < kFinishPage) {
            this.notebook.PageIndex = this.notebook.PageIndex + 1;
        }
    }
    
    public void goBackClick(TObject Sender) {
        if (this.notebook.PageIndex > kStartPage) {
            this.notebook.PageIndex = this.notebook.PageIndex - 1;
        }
    }
    
    public void notebookPageChanged(TObject Sender) {
        this.goBack.Enabled = this.notebook.PageIndex > kStartPage;
        if (this.notebook.PageIndex != kFinishPage) {
            this.goNext.Caption = "&Next >>";
        } else {
            this.goNext.Caption = "&Finish";
        }
    }
    
    public void cancelClick(TObject Sender) {
        this.ModalResult = mrCancel;
    }
    
    public void generateRules() {
        TStringStream stream = new TStringStream();
        String line = "";
        String previousContext = "";
        String commandPhrase = "";
        String commandResponse = "";
        String character = "";
        boolean pipeRead = false;
        TSRule newRule = new TSRule();
        TPoint position = new TPoint();
        int index = 0;
        TSNewRulesCommand newRulesCommand = new TSNewRulesCommand();
        String context = "";
        String requirements = "";
        String changes = "";
        String prefix = "";
        
        uschangelog.ChangeLogForm.addToLog(this.NewCommandsMemo.Text);
        position = usruleeditorform.RuleEditorForm.goodPosition();
        stream = delphi_compatability.TStringStream().Create(this.NewCommandsMemo.Text);
        previousContext = "";
        index = 1;
        newRulesCommand = uscommands.TSNewRulesCommand().create();
        newRulesCommand.creator = "command sequence wizard";
        newRule = null;
        try {
            character = stream.ReadString(1);
            while (character != "") {
                line = "";
                commandPhrase = "";
                commandResponse = "";
                pipeRead = false;
                prefix = trim(this.PrefixEdit.Text);
                while (!((character == Character(13)) || (character == Character(10)) || (character == ""))) {
                    line = line + character;
                    if (pipeRead) {
                        commandResponse = commandResponse + character;
                    } else if (character == "|") {
                        pipeRead = true;
                    } else {
                        commandPhrase = commandPhrase + character;
                    }
                    character = stream.ReadString(1);
                }
                //  	ShowMessage('name: ' + contextName);
                //  ShowMessage('description: ' + contextDescription);
                context = "";
                if (this.ContextBox.ItemIndex >= 0) {
                    context = this.ContextBox.Items[this.ContextBox.ItemIndex];
                }
                if (trim(commandResponse) == "") {
                    commandResponse = "Nothing happens.";
                }
                if ((trim(commandPhrase) != "") && (trim(context) != "")) {
                    newRule = usdomain.domain.world.newRule();
                    newRulesCommand.addRule(newRule);
                    usruleeditorform.RuleEditorForm.lastChoice = newRule;
                    newRule.position = position;
                    newRule.setContext(trim(context));
                    newRule.setCommand(trim(commandPhrase));
                    newRule.setReply(trim(commandResponse));
                    if (this.GenerateSequence.Checked && (prefix != "")) {
                        if (index == 1) {
                            requirements = "~" + prefix + " started";
                            changes = prefix + " started & " + prefix + " " + IntToStr(index) + "0";
                        } else {
                            requirements = prefix + " " + IntToStr(index - 1) + "0";
                            changes = "~" + prefix + " " + IntToStr(index - 1) + "0 & " + prefix + " " + IntToStr(index) + "0";
                        }
                        newRule.setRequirements(requirements);
                        newRule.setChanges(changes);
                    }
                    position.Y = position.Y + 60;
                    index += 1;
                }
                while ((character == Character(13)) || (character == Character(10))) {
                    character = stream.ReadString(1);
                }
            }
            if (this.GenerateSequence.Checked && (prefix != "") && (newRule != null) && (index > 2)) {
                // cleanup for last rule
                changes = newRule.changesString;
                if (this.endSequenceLoopToFirst.Checked) {
                    changes = "~" + prefix + " " + IntToStr(index - 2) + "0 & " + "~" + prefix + " started";
                } else if (this.endSequenceLeaveLastCommand.Checked) {
                    changes = "";
                } else if (this.endSequenceRemoveTheLastCommand.Checked) {
                    changes = "~" + prefix + " " + IntToStr(index - 2) + "0";
                }
                newRule.setChanges(changes);
            }
        } finally {
            stream.free;
        }
        if (newRulesCommand.rules.Count > 0) {
            usdomain.domain.worldCommandList.doCommand(newRulesCommand);
        } else {
            newRulesCommand.free;
        }
        usruleeditorform.RuleEditorForm.updateForRuleChange();
        usruleeditorform.RuleEditorForm.adjustScrollBars();
        this.ModalResult = 1;
        this.generated = true;
    }
    
    public void GenerateSequenceClick(TObject Sender) {
        boolean doSequence = false;
        
        doSequence = this.GenerateSequence.Checked;
        this.prefixArrow.Visible = doSequence;
        this.PrefixLabel.Enabled = doSequence;
        this.PrefixEdit.Visible = doSequence;
        this.prefixNote.Enabled = doSequence;
        this.sequenceEndArrow.Visible = doSequence;
        this.endSequenceLabel.Enabled = doSequence;
        this.endSequenceLoopToFirst.Enabled = doSequence;
        this.endSequenceLeaveLastCommand.Enabled = doSequence;
        this.endSequenceRemoveTheLastCommand.Enabled = doSequence;
    }
    
    //
    //talk to the grue|The grue won't listen.
    //talk to the grue|The grue seems to be getting very agitated.
    //talk to the grue|The grue seems about to fly into a rage.
    //talk to the grue|The grue devours you (except your bones of course).
    //grue pit
    //
    public void FormCloseQuery(TObject Sender, boolean CanClose) {
        byte result = 0;
        
        if (this.generated) {
            return CanClose;
        }
        if (trim(this.NewCommandsMemo.Text) != "") {
            result = MessageDialog("You are about to close this wizard" + chr(13) + "and lose any work done with it." + chr(13) + chr(13) + "Is this OK?", mtConfirmation, {mbYes, mbNo, }, 0);
            if (result != mrYes) {
                return CanClose;
            }
            uschangelog.ChangeLogForm.addToLog(this.NewCommandsMemo.Text);
        }
        this.ModalResult = mrCancel;
        return CanClose;
    }
    
    public void helpButtonClick(TObject Sender) {
        delphi_compatability.Application.HelpJump("Making_new_rules_using_the_new_commands_wizard");
    }
    
}
