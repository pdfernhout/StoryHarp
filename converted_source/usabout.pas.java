// unit usabout

from conversion_common import *;
import ufilesupport;
import usdomain;
import delphi_compatability;

// var
TAboutForm AboutForm = new TAboutForm();


// const
kAsSplash = true;
kAsAbout = false;



class TAboutForm extends TForm {
    public TButton OKButton;
    public TImage Image1;
    public TLabel Label1;
    public TLabel Label2;
    public TLabel Label3;
    public TLabel Label4;
    public TLabel versionLabel;
    public TLabel Label6;
    public TLabel Label7;
    public TLabel Label8;
    public TLabel registeredToLabel;
    
    //$R *.DFM
    public void FormCreate(TObject Sender) {
        this.versionLabel.Caption = ufilesupport.gVersionName;
    }
    
    public void OKButtonClick(TObject Sender) {
        this.ModalResult = mrOK;
    }
    
    public void setUpAsSplashOrAbout(boolean splash) {
        if (splash) {
            this.OKButton.Visible = false;
            this.Caption = "";
            this.BorderIcons = {};
            this.showNameString("");
        } else {
            this.Caption = "About StoryHarp";
            this.BorderIcons = {delphi_compatability.TBorderIcon.biSystemMenu, delphi_compatability.TBorderIcon.biMinimize, delphi_compatability.TBorderIcon.biMaximize, };
            this.OKButton.Visible = true;
            this.showNameString(usdomain.domain.registrationName);
        }
    }
    
    public void showNameString(String aName) {
        if (aName == "") {
            this.registeredToLabel.Caption = "";
        } else if (usdomain.domain.playerOnly) {
            this.registeredToLabel.Caption = "Player-only mode";
        } else if (!usdomain.domain.registered) {
            this.registeredToLabel.Caption = "Unregistered";
        } else {
            if ((len(aName) > 1) && (aName[len(aName)] == ",")) {
                aName = UNRESOLVED.copy(aName, 1, len(aName) - 1);
            }
            this.registeredToLabel.Caption = "Registered to: " + aName;
        }
    }
    
}
