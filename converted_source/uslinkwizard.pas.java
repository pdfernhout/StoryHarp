// unit uslinkwizard

from conversion_common import *;
import usconsoleform;
import uschangelog;
import uscommands;
import usruleeditorform;
import usdomain;
import usworld;
import delphi_compatability;

// var
TLinkWizardForm LinkWizardForm = new TLinkWizardForm();


// const
kStartPage = 0;
kContextsPage = 1;
kForwardPage = 2;
kBackwardPage = 3;
kFinishPage = 4;



class TLinkWizardForm extends TForm {
    public TNotebook notebook;
    public TLabel Label10;
    public TLabel ForwardLabel;
    public TLabel Label13;
    public TEdit ForwardEdit;
    public TLabel forwardReplyLabel;
    public TMemo ForwardMemo;
    public TLabel forwardReplyNote;
    public TButton helpButton;
    public TButton goBack;
    public TButton goNext;
    public TButton cancel;
    public TLabel Label11;
    public TLabel BackwardLabel;
    public TLabel Label12;
    public TEdit BackwardEdit;
    public TLabel backwardReplyLabel;
    public TMemo BackwardMemo;
    public TImage Image1;
    public TLabel Label4;
    public TLabel Label17;
    public TLabel Label26;
    public TImage Image3;
    public TImage Image2;
    public TImage Image4;
    public TImage forwardReplyArrow;
    public TImage Image6;
    public TImage backwardReplyArrow;
    public TLabel Label1;
    public TLabel backwardReplyNote;
    public TLabel Label18;
    public TLabel Label14;
    public TLabel Label19;
    public TLabel Label3;
    public TLabel Label8;
    public TLabel Label9;
    public TImage Image8;
    public TListBox FirstContextBox;
    public TListBox SecondContextBox;
    public TImage commandStartPageImage;
    public TLabel Label2;
    public TImage contextStartPageImage;
    public TLabel Label5;
    public TImage replyStartPageImage;
    public TLabel Label6;
    public TLabel Label7;
    public TImage firstContextImage;
    public TImage secondContextImage;
    public TImage forwardCommandImage;
    public TImage forwardReplyImage;
    public TImage backwardCommandImage;
    public TImage backwardReplyImage;
    public TLabel Label15;
    public TLabel Label16;
    public TLabel forwardSummary;
    public TLabel backwardSummary;
    public boolean generated;
    
    //$R *.DFM
    public void FormActivate(TObject Sender) {
        this.contextStartPageImage.Picture.Bitmap = usconsoleform.ConsoleForm.ContextButton.Glyph;
        this.commandStartPageImage.Picture.Bitmap = usconsoleform.ConsoleForm.CommandButton.Glyph;
        this.replyStartPageImage.Picture.Bitmap = usruleeditorform.RuleEditorForm.replyPicture.Picture.Bitmap;
        this.firstContextImage.Picture.Bitmap = usconsoleform.ConsoleForm.ContextButton.Glyph;
        this.secondContextImage.Picture.Bitmap = usconsoleform.ConsoleForm.ContextButton.Glyph;
        this.forwardCommandImage.Picture.Bitmap = usconsoleform.ConsoleForm.CommandButton.Glyph;
        this.forwardReplyImage.Picture.Bitmap = usruleeditorform.RuleEditorForm.replyPicture.Picture.Bitmap;
        this.backwardCommandImage.Picture.Bitmap = usconsoleform.ConsoleForm.CommandButton.Glyph;
        this.backwardReplyImage.Picture.Bitmap = usruleeditorform.RuleEditorForm.replyPicture.Picture.Bitmap;
    }
    
    public boolean initialize() {
        result = false;
        TSVariable variable = new TSVariable();
        int i = 0;
        int j = 0;
        int contextCount = 0;
        
        result = false;
        this.generated = false;
        this.notebook.PageIndex = kStartPage;
        usdomain.domain.world.addContextsToListBox(this.FirstContextBox);
        if (this.FirstContextBox.Items.Count < 2) {
            ShowMessage("You must create at least two contexts before using the link wizard.");
            return result;
        }
        usdomain.domain.world.addContextsToListBox(this.SecondContextBox);
        contextCount = 0;
        this.ForwardEditChange(this);
        this.BackwardEditChange(this);
        this.ForwardEdit.Text = "";
        this.ForwardMemo.Text = "";
        this.BackwardEdit.Text = "";
        this.BackwardMemo.Text = "";
        result = true;
        for (i = 0; i <= usdomain.domain.world.variables.Count - 1; i++) {
            // must be done last because of exit
            variable = usworld.TSVariable(usdomain.domain.world.variables[i]);
            if (variable.selected) {
                if (contextCount == 0) {
                    for (j = 0; j <= this.FirstContextBox.Items.Count - 1; j++) {
                        if (this.FirstContextBox.Items[j] == variable.phrase) {
                            this.FirstContextBox.ItemIndex = j;
                            contextCount += 1;
                            break;
                        }
                    }
                } else {
                    for (j = 0; j <= this.SecondContextBox.Items.Count - 1; j++) {
                        if (this.SecondContextBox.Items[j] == variable.phrase) {
                            this.SecondContextBox.ItemIndex = j;
                            return result;
                        }
                    }
                }
            }
        }
        return result;
    }
    
    public void goBackClick(TObject Sender) {
        if (this.notebook.PageIndex > kStartPage) {
            this.notebook.PageIndex = this.notebook.PageIndex - 1;
        }
    }
    
    public void goNextClick(TObject Sender) {
        String first = "";
        String second = "";
        
        first = trim(this.firstContext());
        second = trim(this.secondContext());
        if (this.notebook.PageIndex == kFinishPage) {
            this.generateRules();
        } else if (this.notebook.PageIndex == kContextsPage) {
            if ((first == "") || (second == "")) {
                ShowMessage("Both contexts must be entered to proceed.");
                return;
            }
            if (first == second) {
                ShowMessage("The two contexts must have different names.");
                return;
            }
            this.ForwardLabel.Caption = first + " --> " + second;
            this.BackwardLabel.Caption = second + " --> " + first;
        }
        if (this.notebook.PageIndex == kForwardPage) {
            if ((trim(this.ForwardEdit.Text) == "") && (trim(this.ForwardMemo.Text) != "")) {
                ShowMessage("You must enter a command phrase if you enter a reply.");
                return;
            }
        }
        if (this.notebook.PageIndex == kBackwardPage) {
            if ((trim(this.BackwardEdit.Text) == "") && (trim(this.BackwardMemo.Text) != "")) {
                ShowMessage("You must enter a command phrase if you enter a reply.");
                return;
            }
            if ((trim(this.BackwardEdit.Text) == "") && (trim(this.ForwardEdit.Text) == "")) {
                ShowMessage("You must enter a command on this page or the previous page to generate a link.");
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
            if (trim(this.ForwardEdit.Text) == "") {
                this.forwardSummary.Caption = "";
            } else {
                this.forwardSummary.Caption = this.firstContext() + "  ---  " + trim(this.ForwardEdit.Text) + "  -->  " + this.secondContext();
            }
            if (trim(this.BackwardEdit.Text) == "") {
                this.backwardSummary.Caption = "";
            } else {
                this.backwardSummary.Caption = this.secondContext() + "  ---  " + trim(this.BackwardEdit.Text) + "  -->  " + this.firstContext();
            }
        }
    }
    
    public void cancelClick(TObject Sender) {
        this.ModalResult = mrCancel;
    }
    
    public TSRule makeLink(String firstContext, String secondContext, String command, String reply) {
        result = new TSRule();
        int dx = 0;
        int dy = 0;
        
        result = null;
        if (firstContext == "") {
            return result;
        }
        result = usdomain.domain.world.newRule();
        usruleeditorform.RuleEditorForm.lastChoice = result;
        result.setContext(firstContext);
        result.setCommand(command);
        if (trim(reply) != "") {
            result.setReply(reply);
        } else {
            result.setReply("You " + command + ".");
        }
        result.setMove(secondContext);
        result.position.X = (result.context.position.X + result.move.position.X) / 2;
        result.position.Y = (result.context.position.Y + result.move.position.Y) / 2;
        dx = result.context.position.X - result.move.position.X;
        dy = result.context.position.Y - result.move.position.Y;
        if (abs(dy) >= abs(dx)) {
            if (dy >= 0) {
                //now determine offset
                result.position.X = result.position.X - 100;
            } else {
                result.position.X = result.position.X + 100;
            }
        } else {
            if (dx < 0) {
                result.position.Y = result.position.Y - 30;
            } else {
                result.position.Y = result.position.Y + 30;
            }
        }
        return result;
    }
    
    public String firstContext() {
        result = "";
        result = "";
        if (this.FirstContextBox.ItemIndex >= 0) {
            result = this.FirstContextBox.Items[this.FirstContextBox.ItemIndex];
        }
        return result;
    }
    
    public String secondContext() {
        result = "";
        result = "";
        if (this.SecondContextBox.ItemIndex >= 0) {
            result = this.SecondContextBox.Items[this.SecondContextBox.ItemIndex];
        }
        return result;
    }
    
    public void generateRules() {
        TSNewRulesCommand newRulesCommand = new TSNewRulesCommand();
        TSRule newRule = new TSRule();
        
        uschangelog.ChangeLogForm.addToLog(this.ForwardMemo.Text);
        uschangelog.ChangeLogForm.addToLog(this.BackwardMemo.Text);
        newRulesCommand = uscommands.TSNewRulesCommand().create();
        newRulesCommand.creator = "link wizard";
        newRule = this.makeLink(trim(this.firstContext()), trim(this.secondContext()), trim(this.ForwardEdit.Text), trim(this.ForwardMemo.Text));
        if (newRule != null) {
            newRulesCommand.addRule(newRule);
        }
        newRule = this.makeLink(trim(this.secondContext()), trim(this.firstContext()), trim(this.BackwardEdit.Text), trim(this.BackwardMemo.Text));
        if (newRule != null) {
            newRulesCommand.addRule(newRule);
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
    
    public void ForwardEditChange(TObject Sender) {
        boolean haveText = false;
        
        haveText = this.ForwardEdit.Text != "";
        this.forwardReplyArrow.Visible = haveText;
        this.forwardReplyLabel.Enabled = haveText;
        this.forwardReplyImage.Visible = haveText;
        this.ForwardMemo.Visible = haveText;
        this.forwardReplyNote.Enabled = haveText;
    }
    
    public void BackwardEditChange(TObject Sender) {
        boolean haveText = false;
        
        haveText = this.BackwardEdit.Text != "";
        this.backwardReplyArrow.Visible = haveText;
        this.backwardReplyLabel.Enabled = haveText;
        this.backwardReplyImage.Visible = haveText;
        this.BackwardMemo.Visible = haveText;
        this.backwardReplyNote.Enabled = haveText;
    }
    
    public void FormCloseQuery(TObject Sender, boolean CanClose) {
        byte result = 0;
        
        if (this.generated) {
            return CanClose;
        }
        if ((trim(this.ForwardEdit.Text) != "") || (trim(this.ForwardMemo.Text) != "") || (trim(this.BackwardEdit.Text) != "") || (trim(this.BackwardMemo.Text) != "")) {
            result = MessageDialog("You are about to close this wizard" + chr(13) + "and lose any work done with it." + chr(13) + chr(13) + "Is this OK?", mtConfirmation, {mbYes, mbNo, }, 0);
            if (result != mrYes) {
                CanClose = false;
                return CanClose;
            }
            uschangelog.ChangeLogForm.addToLog(this.ForwardMemo.Text);
            uschangelog.ChangeLogForm.addToLog(this.BackwardMemo.Text);
        }
        return CanClose;
    }
    
    public void helpButtonClick(TObject Sender) {
        delphi_compatability.Application.HelpJump("Making_new_rules_using_the_new_moves_wizard");
    }
    
}
