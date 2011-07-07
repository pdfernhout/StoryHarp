// unit usagentwarning

from conversion_common import *;
import usdomain;
import delphi_compatability;


class TAgentWarningForm extends TForm {
    public TLabel Label1;
    public TCheckBox dontShowThisDialogAgain;
    public TButton okButton;
    public TLabel Label2;
    public TLabel Label3;
    public TLabel Label4;
    
    //$R *.DFM
    public void okButtonClick(TObject Sender) {
        usdomain.domain.options.suppressAgentNotPresentWarning = this.dontShowThisDialogAgain.Checked;
        this.ModalResult = mrOK;
    }
    
}
