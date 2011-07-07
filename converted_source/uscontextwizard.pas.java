// unit uscontextwizard

from conversion_common import *;
import uschangelog;
import uscommands;
import usconsoleform;
import usruleeditorform;
import usworld;
import usdomain;
import delphi_compatability;

// var
TContextWizardForm ContextWizardForm = new TContextWizardForm();


// const
kStartPage = 0;
kContextsPage = 1;
kDescriptionPage = 2;
kFinishPage = 3;



class TContextWizardForm extends TForm {
    public TButton goBack;
    public TButton goNext;
    public TButton cancel;
    public TButton helpButton;
    public TNotebook notebook;
    public TLabel Label1;
    public TLabel Label2;
    public TLabel Label3;
    public TLabel Label5;
    public TLabel Label6;
    public TLabel Label21;
    public TLabel Label22;
    public TImage Image3;
    public TMemo NewContextsMemo;
    public TLabel DescribeLabel;
    public TLabel DescribeLabelExtra;
    public TLabel Label4;
    public TImage DescribeImage;
    public TEdit DescribeEdit;
    public TLabel Label13;
    public TLabel Label14;
    public TLabel Label18;
    public TLabel Label7;
    public TLabel Label9;
    public TImage Image1;
    public TLabel Label10;
    public TLabel Label26;
    public TLabel Label19;
    public TImage Image2;
    public TLabel Label8;
    public TImage commandStartPageImage;
    public TImage contextStartPageImage;
    public TImage replyStartPageImage;
    public TImage commandImage;
    public TLabel Label11;
    public TLabel Label12;
    public TLabel Label15;
    public TLabel Label16;
    public boolean generated;
    
    //$R *.DFM
    public void FormActivate(TObject Sender) {
        this.contextStartPageImage.Picture.Bitmap = usconsoleform.ConsoleForm.ContextButton.Glyph;
        this.commandStartPageImage.Picture.Bitmap = usconsoleform.ConsoleForm.CommandButton.Glyph;
        this.replyStartPageImage.Picture.Bitmap = usruleeditorform.RuleEditorForm.replyPicture.Picture.Bitmap;
        this.commandImage.Picture.Bitmap = usconsoleform.ConsoleForm.CommandButton.Glyph;
    }
    
    public boolean initialize() {
        result = false;
        this.generated = false;
        this.notebook.PageIndex = kStartPage;
        this.NewContextsMemo.Text = "";
        result = true;
        return result;
    }
    
    public void goBackClick(TObject Sender) {
        if (this.notebook.PageIndex > kStartPage) {
            this.notebook.PageIndex = this.notebook.PageIndex - 1;
        }
    }
    
    public void goNextClick(TObject Sender) {
        if (this.notebook.PageIndex == kFinishPage) {
            this.generateRules();
        } else if (this.notebook.PageIndex == kContextsPage) {
            if ((trim(this.NewContextsMemo.Text) == "")) {
                ShowMessage("You must enter one or more contexts to proceed.");
                return;
            }
        } else if (this.notebook.PageIndex == kDescriptionPage) {
            if ((trim(this.DescribeEdit.Text) == "")) {
                ShowMessage("You must enter a command to be used to describe these contexts.");
                return;
            }
        }
        if (this.notebook.PageIndex < kFinishPage) {
            this.notebook.PageIndex = this.notebook.PageIndex + 1;
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
        String contextName = "";
        String contextDescription = "";
        String character = "";
        boolean pipeRead = false;
        TSRule newRule = new TSRule();
        TPoint position = new TPoint();
        TSNewRulesCommand newRulesCommand = new TSNewRulesCommand();
        
        uschangelog.ChangeLogForm.addToLog(this.NewContextsMemo.Text);
        position = usruleeditorform.RuleEditorForm.goodPosition();
        stream = delphi_compatability.TStringStream().Create(this.NewContextsMemo.Text);
        previousContext = "";
        newRulesCommand = uscommands.TSNewRulesCommand().create();
        newRulesCommand.creator = "new context wizard";
        try {
            character = stream.ReadString(1);
            while (character != "") {
                line = "";
                contextName = "";
                contextDescription = "";
                pipeRead = false;
                while (!((character == Character(13)) || (character == Character(10)) || (character == ""))) {
                    line = line + character;
                    if (pipeRead) {
                        contextDescription = contextDescription + character;
                    } else if (character == "|") {
                        pipeRead = true;
                    } else {
                        contextName = contextName + character;
                    }
                    character = stream.ReadString(1);
                }
                if (trim(contextName) != "") {
                    if ((trim(this.DescribeEdit.Text) != "")) {
                        //ShowMessage('name: ' + contextName);
                        //ShowMessage('description: ' + contextDescription);
                        newRule = usdomain.domain.world.newRule();
                        newRulesCommand.addRule(newRule);
                        usruleeditorform.RuleEditorForm.lastChoice = newRule;
                        newRule.position = position;
                        newRule.setContext(trim(contextName));
                        newRule.setCommand(trim(this.DescribeEdit.Text));
                        if (trim(contextDescription) != "") {
                            newRule.setReply(trim(contextDescription));
                        } else {
                            newRule.setReply("There is nothing of interest here.");
                        }
                    }
                    previousContext = trim(contextName);
                    position.Y = position.Y + 60;
                }
                while ((character == Character(13)) || (character == Character(10))) {
                    character = stream.ReadString(1);
                }
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
        this.ModalResult = mrOK;
        this.generated = true;
    }
    
    //
    //cave|You are in a big cave.
    //forest|You are in a lively forest.
    //spring|You are standing near a burbling spring.
    //
    //
    //well house|You are in a well house for a small spring.
    //grate|You are standing above a grate.
    //forest|You are wandering around in dense forest.
    //glade|You are in a forest glade.
    //stream|You are walking along a dry stream bed.
    //
    public void FormCloseQuery(TObject Sender, boolean CanClose) {
        byte result = 0;
        
        if (this.generated) {
            return CanClose;
        }
        if (trim(this.NewContextsMemo.Text) != "") {
            result = MessageDialog("You are about to close this wizard" + chr(13) + "and lose any work done with it." + chr(13) + chr(13) + "Is this OK?", mtConfirmation, {mbYes, mbNo, }, 0);
            if (result != mrYes) {
                CanClose = false;
                return CanClose;
            }
            uschangelog.ChangeLogForm.addToLog(this.NewContextsMemo.Text);
        }
        return CanClose;
    }
    
    public void helpButtonClick(TObject Sender) {
        delphi_compatability.Application.HelpJump("Making_new_rules_using_the_new_contexts_wizard");
    }
    
}
//with forwardEdit do
//  if enabled then color := clWindow else color := clBtnFace;
