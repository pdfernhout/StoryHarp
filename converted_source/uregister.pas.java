// unit uregister

from conversion_common import *;
import uregistersupport;
import ufilesupport;
import usdomain;
import delphi_compatability;

// var
TRegistrationForm RegistrationForm = new TRegistrationForm();



class TRegistrationForm extends TForm {
    public TLabel Label11;
    public TPanel Panel1;
    public TLabel Label3;
    public TLabel Label4;
    public TEdit registrationNameEdit;
    public TEdit registrationCodeEdit;
    public TLabel Label1;
    public TEdit orderPageEdit;
    public TLabel Label2;
    public TLabel Label5;
    public TButton openBrowser;
    public TButton close;
    public TButton readLicense;
    public TButton moreInfo;
    public TButton registerMe;
    public TLabel Label6;
    public TButton PrintButton;
    public TRichEdit thankYou;
    public TPrintDialog PrintDialog;
    public boolean printedDetails;
    public boolean opened;
    
    //$R *.DFM
    public void FormActivate(TObject Sender) {
        this.opened = true;
    }
    
    public void openBrowserClick(TObject Sender) {
        UNRESOLVED.ShellExecute(this.Handle, "open", "http://www.kurtz-fernhout.com/order.htm", null, null, delphi_compatability.SW_SHOWNORMAL);
    }
    
    public void registerMeClick(TObject Sender) {
        String cr = "";
        
        if (uregistersupport.RegistrationMatch(this.registrationNameEdit.Text, this.registrationCodeEdit.Text)) {
            this.registerMe.Hide();
            this.thankYou.clear;
            cr = chr(13) + chr(10);
            this.thankYou.text = "StoryHarp version 1.x Registration Details" + cr + "-------------------------------------------" + cr + "Registration name:  " + this.registrationNameEdit.Text + cr + "Registration code:  " + this.registrationCodeEdit.Text + cr + cr + "Welcome to the StoryHarp community!" + cr + "You now own a fully registered copy of StoryHarp version 1.x." + cr + cr + "Your registration also entitles you to free upgrades of StoryHarp up to but not including version 2.0. See the help system for details." + cr + cr + "We will save encoded registration information in your INI settings file in your Windows directory. You should keep a copy of your name and code in a safe place in case you need to reinstall StoryHarp." + cr + cr + "(You should click Print now to print this message.)" + cr + cr + "Be sure to visit our web site to read the latest news about StoryHarp. Thank you for your business!" + cr + cr + "--- Paul Fernhout and Cynthia Kurtz" + cr + "Kurtz-Fernhout Software" + cr + "http://www.kurtz-fernhout.com" + cr + "-------------------------------------------";
            this.thankYou.left = 4;
            this.thankYou.top = 4;
            this.thankYou.show;
            this.thankYou.bringToFront;
            usdomain.domain.registrationName = this.registrationNameEdit.Text;
            usdomain.domain.registrationCode = this.registrationCodeEdit.Text;
            usdomain.domain.registered = true;
            usdomain.domain.justRegistered = true;
            this.PrintButton.Visible = true;
            //309 352 0302
        } else {
            MessageDialog("The registration name and code you entered are incorrect." + chr(13) + "Please retype them and try again." + chr(13) + chr(13) + "If you are sure they are correct" + chr(13) + "and you still get this message, contact us.", delphi_compatability.TMsgDlgType.mtError, {mbOK, }, 0);
        }
    }
    
    public void FormCloseQuery(TObject Sender, boolean CanClose) {
        if (!this.opened) {
            return CanClose;
        }
        CanClose = false;
        if ((!usdomain.domain.registered) && ((trim(this.registrationNameEdit.Text) != "") || (trim(this.registrationCodeEdit.Text) != ""))) {
            ShowMessage("You entered registration information but didn't finish registering." + chr(13) + "You should click \"Register Me!\" or clear out the registration information.");
            return CanClose;
        }
        if ((usdomain.domain.registered) && (!this.printedDetails)) {
            if (MessageDialog("You registered but didn't print the information." + chr(13) + "Do you want to leave WITHOUT printing your registration information?", mtConfirmation, mbYesNoCancel, 0) != delphi_compatability.IDYES) {
                return CanClose;
            }
        }
        CanClose = true;
        return CanClose;
    }
    
    public void closeClick(TObject Sender) {
        if (usdomain.domain.registered) {
            this.ModalResult = mrOK;
        } else {
            this.ModalResult = mrCancel;
        }
    }
    
    public void moreInfoClick(TObject Sender) {
        delphi_compatability.Application.HelpJump("Why_register?");
    }
    
    public void readLicenseClick(TObject Sender) {
        delphi_compatability.Application.HelpJump("License");
    }
    
    public void PrintButtonClick(TObject Sender) {
        if (this.PrintDialog.execute) {
            this.thankYou.print("StoryHarp version 1.x Registration Details");
            this.printedDetails = true;
        }
    }
    
    public void FormCreate(TObject Sender) {
        this.thankYou.visible = true;
        this.thankYou.visible = false;
    }
    
}
