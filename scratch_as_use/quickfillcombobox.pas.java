// unit QuickFillComboBox

from conversion_common import *;
import delphi_compatability;

//Must be published before Items
// pdf added
// pdf added
// pdf added
// PDF PORT changed from "register" as there was a name conflict with grammar; won't be called anyway
public void RegisterIt() {
    UNRESOLVED.RegisterComponents("Speech", {TQuickFillComboBox, });
}


class TQuickFillComboBox extends TCustomComboBox {
    public String lastMatch;
    public boolean FMustBeInList;
    public boolean FEntryRequired;
    
    // pdf hack test remove
    // pdf hack - test remove
    public void CBNEditChange(Tmessage MSG) {
        int i = 0;
        
        super.CBNEditChange();
        i = 0;
    }
    
    public int findMatch(String match) {
        result = 0;
        int i = 0;
        int j = 0;
        String test = "";
        boolean matches = false;
        
        result = -1;
        if (match.equals("")) {
            return result;
        }
        if (thisModel.size() == 0) {
            return result;
        }
        for (i = 0; i < thisModel.size(); i++) {
            matches = true;
            test = thisModel[i];
            for (j = 1; j <= match.length(); j++) {
                if (test[j] != match[j]) {
                    matches = false;
                    break;
                }
            }
            if (matches == true) {
                result = i;
                return result;
            }
        }
        return result;
    }
    
    public void quickFillComboBoxKeyPress(byte Key) {
        int index = 0;
        String startText = "";
        boolean atEnd = false;
        
        if (this.getText().equals("")) {
            //reset if empty
            this.lastMatch = "";
        }
        //compensate for selection about to replace
        atEnd = (this.SelStart + this.SelLength) == this.getText().length();
        if (!atEnd) {
            if ((this.getText() != this.lastMatch) || (this.SelLength != 0)) {
                if (this.FMustBeInList) {
                    if ((Key == 8) && (this.SelStart > 0)) {
                        this.SelStart = this.SelStart - 1;
                    }
                    //eat key
                    Key = 0;
                } else {
                    this.lastMatch = "";
                }
                if ((this.getText().equals("")) && this.FEntryRequired && (thisModel.size() > 0)) {
                    //eat key
                    Key = 0;
                    this.lastMatch = thisModel[0];
                    this.setText(this.lastMatch);
                }
                return Key;
            }
        }
        if (Key == 8) {
            if (this.SelLength == 0) {
                startText = UNRESOLVED.Copy(this.getText(), 1, this.SelStart - 1);
                if (this.SelStart > 0) {
                    this.SelStart = this.SelStart - 1;
                }
            } else {
                startText = UNRESOLVED.Copy(this.getText(), 1, this.SelStart);
            }
        } else if (Key < 32) {
            //don't process control keys - low asciii values
            this.lastMatch = "";
            if ((this.getText().equals("")) && this.FEntryRequired && (thisModel.size() > 0)) {
                this.lastMatch = thisModel[0];
                this.setText(this.lastMatch);
            }
            return Key;
        } else {
            startText = UNRESOLVED.Copy(this.getText(), 1, this.SelStart) + Character(Key);
        }
        Key = 0;
        if (startText.equals("")) {
            if (this.FEntryRequired && (thisModel.size() > 0)) {
                this.lastMatch = thisModel[0];
                this.setText(this.lastMatch);
            } else {
                this.setText("");
                this.lastMatch = "";
            }
            return Key;
        }
        index = this.findMatch(startText);
        if (index >= 0) {
            //   if (index < 0) and EntryRequired and (self.items.size() > 0) then
            //     begin
            //     index := 0;
            //     startText := Copy(;
            //     end;
            this.lastMatch = thisModel[index];
            this.setText(this.lastMatch);
            this.SelStart = startText.length();
        } else {
            if (!this.FMustBeInList) {
                this.lastMatch = "";
                this.setText(startText);
                this.SelStart = startText.length();
            }
        }
        return Key;
    }
    
    // PDF PORT changed Message to TheMessage as used in with and gramamr did not like that
    public void ComboWndProc(TMessage TheMessage, HWnd ComboWnd, Pointer ComboProc) {
        try {
            //FIX unresolved WITH expression: TheMessage
            switch (UNRESOLVED.Msg) {
                case UNRESOLVED.WM_CHAR:
                    if (this.Style != delphi_compatability.TComboBoxStyle.csDropDownList) {
                        UNRESOLVED.TWMKey(TheMessage).charCode = this.quickFillComboBoxKeyPress(UNRESOLVED.TWMKey(TheMessage).charCode);
                    }
                    break;
                case UNRESOLVED.CBN_EDITUPDATE:
                    UNRESOLVED.Dispatch(TheMessage);
                    break;
                case UNRESOLVED.WM_LBUTTONUP:
                    UNRESOLVED.Dispatch(TheMessage);
                    break;
            super.ComboWndProc(TheMessage, ComboWnd, ComboProc);
        } catch (Exception e) {
            Application.HandleException(this);
        }
    }
    
    public void Change() {
        if (this.FMustBeInList && (!this.getText().equals(""))) {
            if ((thisModel.size() <= 0)) {
                this.setText("");
                return;
            }
            if (this.findMatch(this.getText()) < 0) {
                this.lastMatch = thisModel[0];
                this.setText(this.lastMatch);
                this.SelStart = 0;
                this.SelLength = 0;
            }
        }
        if ((this.getText().equals("")) && this.FEntryRequired && (thisModel.size() > 0)) {
            this.lastMatch = thisModel[0];
            this.setText(this.lastMatch);
        }
        super.Change();
    }
    
}
