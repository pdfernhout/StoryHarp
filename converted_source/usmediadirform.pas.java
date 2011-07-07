// unit usmediadirform

from conversion_common import *;
import usspeech;
import usdomain;
import delphi_compatability;

// var
TExtraMediaDirectoryForm ExtraMediaDirectoryForm = new TExtraMediaDirectoryForm();



class TExtraMediaDirectoryForm extends TForm {
    public TButton Close;
    public TButton cancel;
    public TSpeedButton helpButton;
    public TLabel Label1;
    public TLabel Label2;
    public TLabel Label3;
    public TEdit extraMediaDirectoryEdit;
    public TLabel Label4;
    public TLabel Label5;
    public TLabel Label6;
    public TEdit openWorldFileDirectory;
    public TEdit exeDirectory;
    public TEdit windowsMediaDirectory;
    
    //$R *.DFM
    public void FormActivate(TObject Sender) {
        this.openWorldFileDirectory.Text = usdomain.domain.worldFileName;
        this.openWorldFileDirectory.Text = ExtractFilePath(usdomain.domain.worldFileName);
        if (this.openWorldFileDirectory.Text == "") {
            this.openWorldFileDirectory.Text = UNRESOLVED.GetCurrentDir + "\\";
        }
        this.extraMediaDirectoryEdit.Text = usdomain.domain.options.extraMediaDirectory;
        this.exeDirectory.Text = ExtractFilePath(delphi_compatability.Application.exeName);
        this.windowsMediaDirectory.Text = usspeech.getWindowsMediaDirectory();
    }
    
    public void CloseClick(TObject Sender) {
        if (!UNRESOLVED.directoryExists(this.extraMediaDirectoryEdit.Text)) {
            ShowMessage("The directory you specified does not exist.");
            return;
        }
        usdomain.domain.options.extraMediaDirectory = this.extraMediaDirectoryEdit.Text;
        this.ModalResult = mrOK;
    }
    
    public void helpButtonClick(TObject Sender) {
        delphi_compatability.Application.HelpJump("Setting_the_sounds_and_music_directory");
    }
    
}
