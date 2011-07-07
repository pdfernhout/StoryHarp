// unit USPreferences

from conversion_common import *;
import usdomain;
import delphi_compatability;

// var
TPreferencesForm PreferencesForm = new TPreferencesForm();



class TPreferencesForm extends TForm {
    public TButton Close;
    public TButton cancel;
    public TSpeedButton helpButton;
    public TPanel Panel1;
    public TLabel Label1;
    public TEdit tableFontNameEdit;
    public TButton changeTableFont;
    public TPanel backgroundColorPanel;
    public TPanel textColorPanel;
    public TCheckBox showMapCommands;
    public TLabel Label3;
    public TLabel Label2;
    public TButton changeBackgroundColor;
    public TButton changeTextColor;
    public TColorDialog ColorDialog;
    public TFontDialog FontDialog;
    public TLabel Label4;
    public TEdit mapFontNameEdit;
    public TButton changeMapFont;
    public TLabel Label5;
    public TEdit browserFontNameEdit;
    public TButton changeBrowserFont;
    public TLabel Label6;
    public TPanel mapCommandsColorPanel;
    public TButton ChangeMapCommandsColor;
    public TCheckBox symbolButtons;
    public DomainOptionsStructure options;
    
    //$R *.DFM
    public void FormActivate(TObject Sender) {
        this.tableFontNameEdit.Text = this.options.tableFontName + ", " + IntToStr(this.options.tableFontSize);
        this.mapFontNameEdit.Text = this.options.mapFontName + ", " + IntToStr(this.options.mapFontSize);
        this.browserFontNameEdit.Text = this.options.browserFontName + ", " + IntToStr(this.options.browserFontSize);
        this.backgroundColorPanel.Color = this.options.selectedItemColor;
        this.textColorPanel.Color = this.options.selectedTextColor;
        this.showMapCommands.Checked = this.options.showCommandPrefixInMap;
        this.symbolButtons.Checked = this.options.buttonSymbols;
    }
    
    public void changeTableFontClick(TObject Sender) {
        this.FontDialog.Font.Name = this.options.tableFontName;
        this.FontDialog.Font.Size = this.options.tableFontSize;
        if (this.FontDialog.Execute()) {
            this.options.tableFontName = this.FontDialog.Font.Name;
            this.options.tableFontSize = this.FontDialog.Font.Size;
            this.tableFontNameEdit.Text = this.options.tableFontName + ", " + IntToStr(this.options.tableFontSize);
        }
    }
    
    public void changeMapFontClick(TObject Sender) {
        this.FontDialog.Font.Name = this.options.mapFontName;
        this.FontDialog.Font.Size = this.options.mapFontSize;
        if (this.FontDialog.Execute()) {
            this.options.mapFontName = this.FontDialog.Font.Name;
            this.options.mapFontSize = this.FontDialog.Font.Size;
            this.mapFontNameEdit.Text = this.options.mapFontName + ", " + IntToStr(this.options.mapFontSize);
        }
    }
    
    public void changeBrowserFontClick(TObject Sender) {
        this.FontDialog.Font.Name = this.options.browserFontName;
        this.FontDialog.Font.Size = this.options.browserFontSize;
        if (this.FontDialog.Execute()) {
            this.options.browserFontName = this.FontDialog.Font.Name;
            this.options.browserFontSize = this.FontDialog.Font.Size;
            this.browserFontNameEdit.Text = this.options.browserFontName + ", " + IntToStr(this.options.browserFontSize);
        }
    }
    
    public void changeBackgroundColorClick(TObject Sender) {
        this.ColorDialog.Color = this.backgroundColorPanel.Color;
        if (this.ColorDialog.Execute()) {
            this.backgroundColorPanel.Color = this.ColorDialog.Color;
            this.options.selectedItemColor = this.backgroundColorPanel.Color;
        }
    }
    
    public void changeTextColorClick(TObject Sender) {
        this.ColorDialog.Color = this.textColorPanel.Color;
        if (this.ColorDialog.Execute()) {
            this.textColorPanel.Color = this.ColorDialog.Color;
            this.options.selectedTextColor = this.textColorPanel.Color;
        }
    }
    
    public void ChangeMapCommandsColorClick(TObject Sender) {
        this.ColorDialog.Color = this.mapCommandsColorPanel.Color;
        if (this.ColorDialog.Execute()) {
            this.mapCommandsColorPanel.Color = this.ColorDialog.Color;
            this.options.commandTextColorInMap = this.mapCommandsColorPanel.Color;
        }
    }
    
    public void showMapCommandsClick(TObject Sender) {
        this.options.showCommandPrefixInMap = this.showMapCommands.Checked;
    }
    
    public void symbolButtonsClick(TObject Sender) {
        this.options.buttonSymbols = this.symbolButtons.Checked;
    }
    
    public void helpButtonClick(TObject Sender) {
        delphi_compatability.Application.HelpJump("Changing_editor_preferences");
    }
    
}
