// unit uspictureform

from conversion_common import *;
import ucursor;
import usdomain;
import delphi_compatability;

// var
TPictureForm PictureForm = new TPictureForm();


//$R *.DFM
public int localIntMin(int a, int b) {
    result = 0;
    result = a;
    if (b < a) {
        result = b;
    }
    return result;
}

public int localIntMax(int a, int b) {
    result = 0;
    result = a;
    if (b > a) {
        result = b;
    }
    return result;
}


class TPictureForm extends TForm {
    public TPanel controlsPanel;
    public TSpeedButton FirstPictureButton;
    public TSpeedButton PreviousPictureButton;
    public TSpeedButton NextPictureButton;
    public TSpeedButton LastPictureButton;
    public TLabel numbersLabel;
    public TScrollBox PictureScrollBox;
    public TImage PictureImage;
    public TRichEdit CaptionRichEdit;
    public TStringList pictureNames;
    public TStringList commands;
    public TStringList replies;
    public int selectedPictureIndex;
    
    public void FormCreate(TObject Sender) {
        this.pictureNames = delphi_compatability.TStringList.create;
        this.commands = delphi_compatability.TStringList.create;
        this.replies = delphi_compatability.TStringList.create;
        usdomain.domain.setFormSize(this, usdomain.domain.options.pictureWindowRect);
        this.numbersLabel.Caption = "";
        this.controlsPanel.BevelOuter = UNRESOLVED.bvNone;
    }
    
    public void FormDestroy(TObject Sender) {
        this.pictureNames.free;
        this.pictureNames = null;
        this.commands.free;
        this.commands = null;
        this.replies.free;
        this.replies = null;
    }
    
    public void updateViews() {
        this.Caption = "StoryHarp Pictures - " + ExtractFileName(usdomain.domain.worldFileName);
    }
    
    public void addPictureFromFile(String aFileName, String reply) {
        if (!usdomain.domain.options.showPictures) {
            return;
        }
        if (this.pictureNames.IndexOf(aFileName) < 0) {
            // assumes file existence has been verified by caller (speech system)
            this.pictureNames.Add(aFileName);
            //commands.add(command);
            this.replies.Add(reply);
            this.selectedPictureIndex = this.pictureNames.Count - 1;
            this.loadSelectedPicture();
        } else {
            this.selectedPictureIndex = this.pictureNames.IndexOf(aFileName);
            this.loadSelectedPicture();
        }
    }
    
    public void loadSelectedPicture() {
        String fileName = "";
        
        fileName = this.pictureNames.Strings[this.selectedPictureIndex];
        try {
            ucursor.cursor_startWait();
            try {
                this.PictureImage.Picture.Bitmap.LoadFromFile(fileName);
            } catch (Exception e) {
                ShowMessage("Picture file " + fileName + " not found or could not load.");
                ucursor.cursor_stopWait();
                return;
            }
        } finally {
            ucursor.cursor_stopWait();
        }
        this.PictureImage.SetBounds(localIntMax(0, this.PictureScrollBox.Width / 2 - this.PictureImage.Width / 2), localIntMax(0, this.PictureScrollBox.Height / 2 - this.PictureImage.Height / 2), this.PictureImage.Width, this.PictureImage.Height);
        this.numbersLabel.Caption = IntToStr(this.selectedPictureIndex + 1) + " of " + IntToStr(this.pictureNames.Count);
        this.CaptionRichEdit.text = this.replies.Strings[this.selectedPictureIndex];
        this.FirstPictureButton.Enabled = this.selectedPictureIndex > 0;
        this.PreviousPictureButton.Enabled = this.FirstPictureButton.Enabled;
        this.LastPictureButton.Enabled = this.selectedPictureIndex < this.pictureNames.Count - 1;
        this.NextPictureButton.Enabled = this.LastPictureButton.Enabled;
        if (!this.Visible) {
            this.Show();
        }
        this.BringToFront();
    }
    
    public void FormResize(TObject Sender) {
        //with CaptionRichEdit do setBounds(0, self.clientHeight - height, self.clientWidth, height);
        //controlsPanel.height
        //- CaptionRichEdit.height
        this.PictureScrollBox.SetBounds(0, 0, this.ClientWidth, this.ClientHeight - this.controlsPanel.Height);
        this.controlsPanel.SetBounds(0, this.ClientHeight - this.controlsPanel.Height, this.ClientWidth, this.controlsPanel.Height);
        this.PictureImage.SetBounds(localIntMax(0, this.PictureScrollBox.Width / 2 - this.PictureImage.Width / 2), localIntMax(0, this.PictureScrollBox.Height / 2 - this.PictureImage.Height / 2), this.PictureImage.Width, this.PictureImage.Height);
    }
    
    public void clearPictures() {
        this.pictureNames.Clear();
        this.FirstPictureButton.Enabled = false;
        this.PreviousPictureButton.Enabled = false;
        this.LastPictureButton.Enabled = false;
        this.NextPictureButton.Enabled = false;
        this.Hide();
    }
    
    public void FirstPictureButtonClick(TObject Sender) {
        this.selectedPictureIndex = 0;
        this.loadSelectedPicture();
    }
    
    public void PreviousPictureButtonClick(TObject Sender) {
        this.selectedPictureIndex = localIntMax(0, this.selectedPictureIndex - 1);
        this.loadSelectedPicture();
    }
    
    public void NextPictureButtonClick(TObject Sender) {
        this.selectedPictureIndex = localIntMin(this.pictureNames.Count - 1, this.selectedPictureIndex + 1);
        this.loadSelectedPicture();
    }
    
    public void LastPictureButtonClick(TObject Sender) {
        this.selectedPictureIndex = this.pictureNames.Count - 1;
        this.loadSelectedPicture();
    }
    
    // palette stuff 
    // ----------------------------------------------------------------------------- *palette stuff 
    public HPALETTE GetPalette() {
        result = new HPALETTE();
        result = this.PictureImage.Picture.Bitmap.Palette;
        return result;
    }
    
    //overriden because paint box will not update correctly
    //makes window take first priority for palettes
    public boolean PaletteChanged(boolean Foreground) {
        result = false;
        HPALETTE oldPalette = new HPALETTE();
        HPALETTE palette = new HPALETTE();
        HWnd windowHandle = new HWnd();
        HDC DC = new HDC();
        
        result = false;
        if (delphi_compatability.Application.terminated) {
            return result;
        }
        palette = this.GetPalette();
        if (palette != 0) {
            DC = this.GetDeviceContext(windowHandle);
            oldPalette = UNRESOLVED.selectPalette(DC, palette, !Foreground);
            if ((UNRESOLVED.realizePalette(DC) != 0) && (this.PictureImage != null)) {
                // if palette changed, repaint drawing 
                this.PictureImage.Invalidate();
            }
            UNRESOLVED.selectPalette(DC, oldPalette, true);
            UNRESOLVED.realizePalette(DC);
            UNRESOLVED.releaseDC(windowHandle, DC);
        }
        result = super.PaletteChanged(Foreground);
        return result;
    }
    
}
