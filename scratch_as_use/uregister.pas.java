// unit uregister

from conversion_common import *;
import uregistersupport;
import UFileSupport;
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
        
        if (uregistersupport.RegistrationMatch(this.registrationNameEdit.getText(), this.registrationCodeEdit.getText())) {
            this.registerMe.setVisible(false);
            this.thankYou.setVisible(true);
            this.thankYou.clear;
            cr = "\n";
            this.thankYou.setText("StoryHarp version 1.x Registration Details" + cr + "-------------------------------------------" + cr + "Registration name:  " + this.registrationNameEdit.getText() + cr + "Registration code:  " + this.registrationCodeEdit.getText() + cr + cr + "Welcome to the StoryHarp community!" + cr + "You now own a fully registered copy of StoryHarp version 1.x." + cr + cr + "Your registration also entitles you to free upgrades of StoryHarp up to but not including version 2.0. See the help system for details." + cr + cr + "We will save encoded registration information in your INI settings file in your Windows directory. You should keep a copy of your name and code in a safe place in case you need to reinstall StoryHarp." + cr + cr + "(You should click Print now to print this message.)" + cr + cr + "Be sure to visit our web site to read the latest news about StoryHarp. Thank you for your business!" + cr + cr + "--- Paul Fernhout and Cynthia Kurtz" + cr + "Kurtz-Fernhout Software" + cr + "http://www.kurtz-fernhout.com" + cr + "-------------------------------------------");
            this.thankYou.x = 4;
            this.thankYou.y = 4;
            this.thankYou.show;
            this.thankYou.bringToFront;
            TSDomain.domain.registrationName = this.registrationNameEdit.getText();
            TSDomain.domain.registrationCode = this.registrationCodeEdit.getText();
            TSDomain.domain.registered = true;
            TSDomain.domain.justRegistered = true;
            this.PrintButton.setVisible(true);
            //309 352 0302
        } else {
        	UFileSupport.ShowMessage("The registration name and code you entered are incorrect." + "\n" + "Please retype them and try again." + "\n" + "\n" + "If you are sure they are correct" + "\n" + "and you still get this message, contact us.");
        }
    }
    
    public void FormCloseQuery(TObject Sender, boolean CanClose) {
        if (!this.opened) {
            return CanClose;
        }
        CanClose = false;
        if ((!TSDomain.domain.registered) && ((!this.registrationNameEdit.getText().trim().equals("")) || (!this.registrationCodeEdit.getText().trim().equals("")))) {
            UFileSupport.ShowMessage("You entered registration information but didn't finish registering." + "\n" + "You should click \"Register Me!\" or clear out the registration information.");
            return CanClose;
        }
        if ((TSDomain.domain.registered) && (!this.printedDetails)) {
            if (!UFileSupport.Confirm("You registered but didn't print the information." + "\n" + "Do you want to leave WITHOUT printing your registration information?") {
                return CanClose;
            }
        }
        CanClose = true;
        return CanClose;
    }
    
    public void closeClick(TObject Sender) {
        if (TSDomain.domain.registered) {
            this.ModalResult = mrOK;
        } else {
            this.ModalResult = mrCancel;
        }
    }
    
    public void moreInfoClick(TObject Sender) {
        Application.HelpJump("Why_register?");
    }
    
    public void readLicenseClick(TObject Sender) {
        Application.HelpJump("License");
    }
    
    public void PrintButtonClick(TObject Sender) {
        if (this.PrintDialog.execute) {
            this.thankYou.print("StoryHarp version 1.x Registration Details");
            this.printedDetails = true;
        }
    }
    
    public void FormCreate(TObject Sender) {
        this.thankYou.setVisible(false);
    }
    
}
