// unit uabout
// initial comment
// before interface and uses comment

from conversion_common import *;
import ufilesupport;
import uregister;
import usdomain;
import delphi_compatability;

// var
TUnregisteredAboutForm UnregisteredAboutForm = new TUnregisteredAboutForm();



class TUnregisteredAboutForm extends TForm {
    public TButton close;
    public TButton registerIt;
    public TLabel noDistributeLabel;
    public TButton readLicense;
    public TPanel TimePanel;
    public TLabel hoursLabel;
    public TButton cancel;
    public TButton whyRegister;
    public TLabel timeWarningLabel;
    public TLabel Label1;
    public TImage Image1;
    public TLabel Label2;
    public TLabel Label3;
    public TLabel Label4;
    public TLabel Label5;
    public TLabel versionLabel;
    public TLabel Label7;
    public TLabel Label8;
    public TLabel Label9;
    public TLabel registeredToLabel;
    
    //$R *.DFM
    public void initializeWithWhetherClosingProgram(boolean closingProgram) {
        if (usdomain.domain.playerOnly) {
            this.ClientHeight = this.TimePanel.Top - 1;
            this.Caption = "Thank you for using the StoryHarp player!";
            this.registeredToLabel.Caption = "Player-only mode";
            this.whyRegister.Visible = false;
            this.registerIt.Visible = false;
            this.readLicense.Top = this.registerIt.Top;
        }
        if (closingProgram) {
            this.close.Caption = "Quit";
        } else {
            this.close.Caption = "Close";
        }
        this.cancel.Visible = closingProgram;
    }
    
    public void FormActivate(TObject Sender) {
        TDateTime timeBetween = new TDateTime();
        byte smallHours = 0;
        byte minutes = 0;
        byte seconds = 0;
        byte milliseconds = 0;
        int randomNumber = 0;
        int hours = 0;
        int i = 0;
        
        if (usdomain.domain.playerOnly) {
            return;
        }
        this.ActiveControl = this.registerIt;
        UNRESOLVED.randomize;
        for (i = 0; i <= 100; i++) {
            UNRESOLVED.random;
        }
        randomNumber = UNRESOLVED.random(2);
        switch (randomNumber) {
            case 0:
                this.close.Top = 4;
                this.registerIt.Top = this.close.Top + this.close.Height + 3;
                break;
            case 1:
                this.registerIt.Top = 4;
                this.close.Top = this.registerIt.Top + this.registerIt.Height + 3;
                break;
        this.hoursLabel.Caption = "You have been using StoryHarp for ";
        timeBetween = usdomain.max((UNRESOLVED.Now - usdomain.domain.startTimeThisSession), 0) + usdomain.domain.accumulatedUnregisteredTime;
        UNRESOLVED.DecodeTime(timeBetween, smallHours, minutes, seconds, milliseconds);
        hours = smallHours;
        if (timeBetween >= 1.0) {
            hours = hours + trunc(timeBetween) * 24;
        }
        if ((minutes < 1) && (hours < 1)) {
            // hoursLabel.caption := hoursLabel.caption + 'more than 24 hours.'
            this.hoursLabel.Caption = this.hoursLabel.Caption + "less than one minute.";
        } else if ((minutes == 1) && (hours < 1)) {
            this.hoursLabel.Caption = this.hoursLabel.Caption + "one minute.";
        } else if (hours < 1) {
            this.hoursLabel.Caption = this.hoursLabel.Caption + IntToStr(minutes) + " minutes.";
        } else if (hours == 1) {
            this.hoursLabel.Caption = this.hoursLabel.Caption + IntToStr(hours) + " hour and " + IntToStr(minutes) + " minutes.";
        } else {
            this.hoursLabel.Caption = this.hoursLabel.Caption + IntToStr(hours) + " hours and " + IntToStr(minutes) + " minutes.";
        }
        if (hours >= 24.0) {
            this.timeWarningLabel.Font.Color = delphi_compatability.clGreen;
            this.timeWarningLabel.Font.Style = {UNRESOLVED.fsBold, };
        }
    }
    
    public void closeClick(TObject Sender) {
        this.ModalResult = mrOK;
    }
    
    public void registerItClick(TObject Sender) {
        if (uregister.RegistrationForm.ShowModal() == mrOK) {
            this.ModalResult = mrCancel;
        }
    }
    
    public void hoursLabelClick(TObject Sender) {
        pass
        //var
        //  hours, minutes: smallint;
        //  hourString: string;
        // only for cfk testing -- remove later
        //
        //  if inputQuery('Testing', 'Enter number of hours', hourString) then
        //    hours := strToInt(hourString);
        //  if inputQuery('Testing', 'Enter number of minutes', hourString) then
        //    minutes := strToInt(hourString);
        //
        //  hoursLabel.caption := 'You have been evaluating StoryHarp for ';
        //  if hours >= 24 then
        //    hoursLabel.caption := hoursLabel.caption + 'more than 24 hours.'
        //  else if (minutes < 1) and (hours < 1) then
        //    hoursLabel.caption := hoursLabel.caption + 'less than one minute.'
        //  else if (minutes = 1) and (hours < 1) then
        //    hoursLabel.caption := hoursLabel.caption + 'one minute.'
        //  else if hours < 1 then
        //    hoursLabel.caption := hoursLabel.caption + intToStr(minutes) + ' minutes.'
        //  else if hours = 1 then
        //    hoursLabel.caption := hoursLabel.caption + intToStr(hours) + ' hour and ' + intToStr(minutes) + ' minutes.'
        //  else
        //    hoursLabel.caption := hoursLabel.caption + intToStr(hours) + ' hours and ' + intToStr(minutes) + ' minutes.';
        //  
    }
    
    public void readLicenseClick(TObject Sender) {
        delphi_compatability.Application.HelpJump("License");
    }
    
    public void whyRegisterClick(TObject Sender) {
        delphi_compatability.Application.HelpJump("Why_register?");
    }
    
    public void FormCreate(TObject Sender) {
        this.versionLabel.Caption = ufilesupport.gVersionName;
    }
    
}
// almost final comment
// very final comment
