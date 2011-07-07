// unit usfocuscommands

from conversion_common import *;
import usdomain;
import quickfillcombobox;
import usmodelchanges;
import usworld;
import ucommand;
import delphi_compatability;

// record
class TSelectionInformation {
    public String text;
    public int selStart;
    public int selLength;
}

public void RecordSelectionInformation(TSelectionInformation selectionInformation, TControl control) {
    if (control instanceof delphi_compatability.TCustomEdit) {
        selectionInformation.text = ((delphi_compatability.TCustomEdit)control).Text;
        selectionInformation.selStart = ((delphi_compatability.TCustomEdit)control).selStart;
        selectionInformation.selLength = ((delphi_compatability.TCustomEdit)control).selLength;
    } else if (control instanceof quickfillcombobox.TQuickFillComboBox) {
        selectionInformation.text = ((quickfillcombobox.TQuickFillComboBox)control).Text;
        selectionInformation.selStart = ((quickfillcombobox.TQuickFillComboBox)control).selStart;
        selectionInformation.selLength = ((quickfillcombobox.TQuickFillComboBox)control).selLength;
    } else {
        // error
        throw new GeneralException.create("RecordSelectionInformation: unsupported control class");
    }
}

public void RestoreSelectionInformation(TSelectionInformation selectionInformation, TControl control) {
    if (control instanceof delphi_compatability.TCustomEdit) {
        ((delphi_compatability.TCustomEdit)control).Text = selectionInformation.text;
        ((delphi_compatability.TCustomEdit)control).selStart = selectionInformation.selStart;
        ((delphi_compatability.TCustomEdit)control).selLength = selectionInformation.selLength;
    } else if (control instanceof quickfillcombobox.TQuickFillComboBox) {
        ((quickfillcombobox.TQuickFillComboBox)control).Text = selectionInformation.text;
        ((quickfillcombobox.TQuickFillComboBox)control).selStart = selectionInformation.selStart;
        ((quickfillcombobox.TQuickFillComboBox)control).selLength = selectionInformation.selLength;
    } else {
        // error
        throw new GeneralException.create("RestoreSelectionInformation: unsupported control class");
    }
}


class TFocusShiftAction extends TObject {
    public TWinControl control;
    public TForm form;
    public TModelChange modelChange;
    
    ///////////////////////////// TFocusShiftAction //////////////////////////
    public void createWithFormControlModelChange(TForm form, TWinControl control, TModelChange modelChange) {
        this.form = form;
        this.control = control;
        this.modelChange = modelChange;
    }
    
    public void destroy() {
        this.modelChange.free;
        super.destroy();
    }
    
    // param ignored
    public void recordState(TSelectionInformation selectionInformation) {
        pass
        // subclasses should override
    }
    
    // param ignored
    public void restoreStateWithFocus() {
        // form.Active := true;
        this.control.SetFocus();
        // subclasses may override
    }
    
    // assume focus shifted is handled by system the first time
    // assume do must be called before other component is focused to if it is off the form
    public void doShift() {
        if (this.modelChange != null) {
            // subclasses may override
            this.modelChange.doChange();
        }
    }
    
    public void undoShift() {
        if (this.modelChange != null) {
            // subclasses may override
            this.modelChange.undoChange();
        }
    }
    
    public void redoShift() {
        if (this.modelChange != null) {
            this.modelChange.redoChange();
        }
    }
    
}
class TFocusExitAction extends TFocusShiftAction {
    
    //////////////////////////// TFocusExitAction ////////////////////////////////
    public void doShift() {
        super.doShift();
    }
    
    // exit moves focus on undo
    public void undoShift() {
        this.restoreStateWithFocus();
        // need to restore selectionInformation in form
        super.undoShift();
    }
    
    public void redoShift() {
        super.redoShift();
    }
    
}
class TFocusEnterAction extends TFocusShiftAction {
    
    //////////////////////////// TFocusEnterAction ////////////////////////////////
    public void doShift() {
        super.doShift();
    }
    
    public void undoShift() {
        super.undoShift();
    }
    
    // enter moves focus on redo
    public void redoShift() {
        this.restoreStateWithFocus();
        super.redoShift();
    }
    
}
class TTextFocusExitAction extends TFocusExitAction {
    public TSelectionInformation selectionInformation;
    
    //////////////////////////// TTextFocusExitAction ////////////////////////////////
    public void recordState(TSelectionInformation selectionInformation) {
        super.recordState(selectionInformation);
        this.selectionInformation = selectionInformation;
        //RecordSelectionInformation(selectionInformation, control);
    }
    
    public void restoreStateWithFocus() {
        super.restoreStateWithFocus();
        RestoreSelectionInformation(this.selectionInformation, this.control);
    }
    
}
class TTextFocusEnterAction extends TFocusEnterAction {
    
}
class TFocusShiftCommand extends KfCommand {
    public TFocusExitAction focusExitAction;
    public TFocusEnterAction focusEnterAction;
    
    //////////////////////////// TTextFocusEnterAction ////////////////////////////////
    //////////////////////////// TFocusShiftCommand ////////////////////////////////
    public void createWithFocusExitAction(TFocusExitAction focusExitAction) {
        this.create();
        this.focusExitAction = focusExitAction;
        //self.focusExitAction.recordState;
        // responsibility of component being focused to call setFocusEnterAction
    }
    
    public void setFocusEnterAction(TFocusEnterAction focusEnterAction) {
        TSelectionInformation selectionInformation = new TSelectionInformation();
        
        this.focusEnterAction = focusEnterAction;
        // param ignored
        this.focusEnterAction.recordState(selectionInformation);
    }
    
    public void destroy() {
        this.focusExitAction.free;
        this.focusEnterAction.free;
        super.destroy();
    }
    
    public void doCommand() {
        super.doCommand();
        usdomain.domain.beginUpdate();
        this.focusExitAction.doShift();
        if (this.focusEnterAction != null) {
            this.focusEnterAction.doShift();
        }
        usdomain.domain.endUpdate();
    }
    
    public void undoCommand() {
        super.undoCommand();
        usdomain.domain.beginUpdate();
        if (this.focusEnterAction != null) {
            this.focusEnterAction.undoShift();
        }
        this.focusExitAction.undoShift();
        usdomain.domain.endUpdate();
    }
    
    public void redoCommand() {
        super.doCommand();
        usdomain.domain.beginUpdate();
        this.focusExitAction.redoShift();
        if (this.focusEnterAction != null) {
            this.focusEnterAction.redoShift();
        }
        usdomain.domain.endUpdate();
    }
    
    public String description() {
        result = "";
        result = "editor focus shift";
        return result;
    }
    
}
