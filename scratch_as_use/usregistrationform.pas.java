// unit usregistrationform

from conversion_common import *;
import usconsoleform;
import delphi_compatability;

// var
TRegistrationForm RegistrationForm = new TRegistrationForm();


//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// Encode the data to be sent to the CGI                                     
public String Encode(String msg) {
    result = "";
    int I = 0;
    
    result = "";
    for (I = 1; I <= msg.length(); I++) {
        if (msg[I] == ' ') {
            result = result + "+";
        } else if (msg[I] in {range("a", "z" + 1), range("A", "Z" + 1), }) {
            result = result + "%" + UNRESOLVED.IntToHex(ord(msg[I]), 2);
        } else {
            result = result + msg[I];
        }
    }
    return result;
}


class TRegistrationForm extends TForm {
    public TEdit UserNameEdit;
    public TEdit RegistrationCodeEdit;
    public TLabel Label1;
    public TLabel Label2;
    public TButton OKButton;
    public TButton CancelButton;
    public TMemo DisplayMemo;
    public TLabel DateInstalledLabel;
    public TLabel DaysSinceInstallationLabel;
    public TButton EnableEditorButton;
    public TButton OnlineRegistrationButton;
    public THttpCli HttpClient;
    public TButton AbortButton;
    
    //$R *.DFM
    public void OKButtonClick(TObject Sender) {
        if (len(this.UserNameEdit.getText().trim()) < 3) {
            UFileSupport.ShowMessage("A name must be entered");
            return;
        }
        // PDF PORT: was != but seemed like a bug?
        if (this.RegistrationCodeEdit.getText().trim().toLowerCase().equals("htya9513".toLowerCase()) {
            UFileSupport.ShowMessage("Invalid registration code. Please see http://www.kurtz-fernhout.com for instructions on how to register.");
            return;
        }
        UFileSupport.ShowMessage("Thank you for supporting our work.");
        Application.ConsoleForm.registrationOK(this.UserNameEdit.getText().trim());
        this.ModalResult = 1;
    }
    
    public void CancelButtonClick(TObject Sender) {
        this.ModalResult = mrCancel;
    }
    
    public void UserNameEditKeyPress(TObject Sender, char Key) {
        pass
        //	OkButton.enabled := (UserNameEdit.getText() <> '') and (RegistrationCodeEdit.getText() <> '');
        return Key;
    }
    
    public void RegistrationCodeEditKeyPress(TObject Sender, char Key) {
        pass
        //	OkButton.enabled := (UserNameEdit.getText() <> '') and (RegistrationCodeEdit.getText() <> '');
        return Key;
    }
    
    public void FormActivate(TObject Sender) {
        this.DateInstalledLabel.setText("Date editor enabled: " + UNRESOLVED.DateToStr(UNRESOLVED.Date));
        this.DaysSinceInstallationLabel.setText("Days since editor enabled: " + "1");
        if (this.DateInstalledLabel.isVisible()) {
            this.EnableEditorButton.setText("Disable Editor");
        } else {
            this.EnableEditorButton.setText("Enable Editor");
        }
    }
    
    public void EnableEditorButtonClick(TObject Sender) {
        if (this.DateInstalledLabel.isVisible()) {
            this.DateInstalledLabel.setVisible(false);
            this.DaysSinceInstallationLabel.setVisible(false);
            Application.ConsoleForm.MenuEditStory.setEnabled(false);
            Application.ConsoleForm.MenuOptionsShowVariables.setEnabled(false);
            Application.ConsoleForm.MenuOptionsUpdateEditorSelections.setEnabled(false);
            this.ModalResult = mrCancel;
        } else {
            this.DateInstalledLabel.setVisible(true);
            this.DaysSinceInstallationLabel.setVisible(true);
            Application.ConsoleForm.MenuEditStory.setEnabled(true);
            Application.ConsoleForm.MenuOptionsShowVariables.setEnabled(true);
            Application.ConsoleForm.MenuOptionsUpdateEditorSelections.setEnabled(true);
            UFileSupport.ShowMessage("The editor is now enabled." + "\n" + "If you find value in it, please register." + "\n" + "You must register to legally distribute or publically present" + "\n" + "anything produced directly or indirectly through using the editor." + "\n" + "See the license for details.");
            this.ModalResult = mrCancel;
        }
    }
    
    public void OnlineRegistrationButtonClick(TObject Sender) {
        TMemoryStream DataIn = new TMemoryStream();
        TMemoryStream DataOut = new TMemoryStream();
        String Buf = "";
        
        if (this.UserNameEdit.getText().trim().equals("")) {
            UFileSupport.ShowMessage("You must enter your name first");
            return;
        }
        this.DisplayMemo.setText("Connecting to http://www.kurtz-fernhout.com");
        DataIn = delphi_compatability.TMemoryStream.create;
        DataOut = delphi_compatability.TMemoryStream.create;
        try {
            Buf = "N=" + Encode(this.UserNameEdit.getText());
            DataOut.write(Buf[1], Buf.length());
            DataOut.Seek(0, delphi_compatability.soFromBeginning);
            this.HttpClient.SendStream = DataOut;
            this.HttpClient.RcvdStream = DataIn;
            // none for now - not sure what this is
            this.HttpClient.Proxy = "";
            this.HttpClient.ProxyPort = "80";
            this.HttpClient.URL = "http://www.kurtz-fernhout.com/qny147/register.cgi";
            this.OnlineRegistrationButton.setEnabled(false);
            this.AbortButton.setEnabled(true);
            try {
                try {
                    this.HttpClient.post;
                    DataIn.Seek(0, 0);
                    this.DisplayMemo.Lines.LoadFromStream(DataIn);
                } catch (Exception e) {
                    this.DisplayMemo.Lines.Add("Failed : " + this.HttpClient.ReasonPhrase);
                }
            } finally {
                this.OnlineRegistrationButton.setEnabled(true);
                this.AbortButton.setEnabled(false);
            }
        } finally {
            DataOut.free;
            DataIn.free;
        }
    }
    
}
