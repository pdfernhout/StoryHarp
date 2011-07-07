package com.kurtz_fernhout.storyharp;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class TSSpeechSystem {
    
	// const
    public static final String commandListCommand = "options";
    public static final String repeatLastSayCommand = "say that again";
    public static final String undoCommand = "undo";
    public static final String redoCommand = "redo";
    public static final String firstRiddleAnswer = "say an answer for a riddle";
    public static final String agentName = "StoryHarp";
    
    public static final String riddlePrefix = "$";
    
    public static final String kPlaySoundMacroStart = "sound ";
    public static final String kPlayMusicMacroStart = "music ";
    public static final String kPlayMusicOnceMacroStart = "musiconce ";
    public static final String kShowPictureMacroStart = "picture ";
    
    String midiFilePlaying = "";
    Sequencer sm_sequencer = null;

    public Vector<String> commandsListenedFor = new Vector<String>();
    public String lastSaidTextWithMacros;
    public int riddleIndex;
    public boolean usingSpeech;

    public boolean mediaPlayerOpened;
    public boolean characterLoaded;
    public boolean sayOptionsMacroInForce;
    public boolean systemShuttingDown;

    public String joinSentences(String firstSentence, String secondSentence) {
        String result = firstSentence.trim();
        if ((secondSentence.trim()) != "") {
            if (!result.equals("")) {
                result = result + " ";
            }
            result = result + secondSentence.trim();
        }
        return result;
    }
    
    // PDF PORT FIX: not called -- was a constructor, should it still be?
    public void create() {
        this.commandsListenedFor = new Vector<String>();
        this.usingSpeech = false;
        if (this.usingSpeech) {
        	this.connectToSpeechEngine();
        }
        // if speech engine does not connect, will set usingSpeech to false
        if (!this.usingSpeech) {
            Application.ConsoleForm.MenuOptionsSpeak.setEnabled(false);
            Application.ConsoleForm.MenuOptionsSounds.setEnabled(false);
            Application.ConsoleForm.MenuSettingsCharacter.setEnabled(false);
            Application.ConsoleForm.MenuSettingsSayOptionsAfterLook.setEnabled(false);
            Application.ConsoleForm.MenuOptionsVoiceUndo.setEnabled(false);
            Application.ConsoleForm.MenuOptionsVoiceRedo.setEnabled(false);
        }
    }
    
    public void connectToSpeechEngine() {
        if (!this.usingSpeech) {
            return;
        }
        // PDF SPEECH FIX: Do something here
        UFileSupport.ShowMessage("Speech engines are not supported yet.");
        this.usingSpeech = false;
     }
    
    public void disconnectFromSpeechEngine() {
        if (!this.usingSpeech) {
            return;
        }
        if (!this.systemShuttingDown) {
        }
        // PDF SPEECH FIX: Do something here
        this.usingSpeech = false;
    }
    
    // maybe is not needed?
    public void finalize() throws Throwable{
        if (sm_sequencer != null) {
        	sm_sequencer.stop();
        }
        this.disconnectFromSpeechEngine();
        super.finalize();
    }
    
    // not currently used, would need to be linked to a speech engine for callback on speech recognition
    public void speechEngineCallback(String commandString) {
    	// PDF SPEECH FIX: Do something here if needed to really get a command string from the engine
        if (!commandString.equals("")) {
            // I don't know why blank entries get sent
            this.doCommand(commandString);
            if (TSDomain.domain.options.updateEditorAfterCommandDone) {
                Application.RuleEditorForm.trackLastCommand();
            }
        }
    }
    
    public void doCommand(String utterance) {
    	System.out.println("doCommand utterance: " + utterance);
        String commandPhrase = "";
        String commandPhraseModified = "";
        
        //command: TSDoCommandPhrase;
        this.haltSpeechAndSound();
        commandPhrase = utterance;
        if (commandPhrase.equals(undoCommand)) {
            if (TSDomain.domain.sessionCommandList.isUndoEnabled()) {
                TSDomain.domain.sessionCommandList.undoLast();
            } else {
                this.speakText("There is nothing to undo.");
            }
            return;
        }
        if (commandPhrase.equals(redoCommand)) {
            if (TSDomain.domain.sessionCommandList.isRedoEnabled()) {
                TSDomain.domain.sessionCommandList.redoLast();
            } else {
                this.speakText("There is nothing to redo.");
            }
            return;
        }
        if (commandPhrase.equals(repeatLastSayCommand)) {
            this.sayTextWithMacros(this.lastSaidTextWithMacros);
            return;
        }
        if ((commandPhrase.equals(commandListCommand))) {
            this.sayOptions();
            return;
        }
        if (commandPhrase.equals(firstRiddleAnswer)) {
            // for riddles - need to be reassembled into command string first
            commandPhrase = "answer ";
            // old title: "Riddle answer"
            String inputResult = JOptionPane.showInputDialog(null, "Please enter the answer to a riddle.", commandPhrase);
            if (inputResult == null || inputResult.equals("")) {
                return;
            }
            commandPhrase = inputResult;
        }
        if ((this.commandsListenedFor.indexOf(commandPhrase) == -1)) {
            commandPhraseModified = riddlePrefix + commandPhrase;
        } else {
            commandPhraseModified = commandPhrase;
        }
        if ((commandsListenedFor.indexOf(commandPhraseModified) == -1)) {
            if ((commandPhrase.length() > 1) && (commandPhrase.startsWith(riddlePrefix))) {
                // elimitate leading $
                commandPhrase = commandPhrase.substring(1);
            }
            Application.ConsoleForm.addLineToTranscript("> " + commandPhrase, Color.red);
            // bug - or bad riddle answer.
            Application.ConsoleForm.addLineToTranscript("That accomplishes nothing.", Color.blue);
            Application.ConsoleForm.scrollTranscriptEndIntoView();
            return;
        }
        System.out.println("command OK");
        //command := 
        TSDomain.domain.sessionCommandList.doCommandPhrase(commandPhraseModified);
        if ((commandPhrase.equals("look")) && (TSDomain.domain.options.sayOptionsAfterLook)) {
            //if command.shiftsFocus then  - might lead to recursion if do look commands...
            this.sayOptions();
        }
    }
    
    public void sayOptions() {
        String thingsToSay = "";
        
        this.riddleIndex = 1;
        thingsToSay = "";
        if (this.commandsListenedFor.size() > 0) {
            for (int i = 0; i < this.commandsListenedFor.size(); i++) {
                String thing = this.commandsListenedFor.get(i);
                thing = this.hideRiddleAnswerForCommand(thing);
                if ((this.commandsListenedFor.get(i).startsWith(riddlePrefix)) && (this.riddleIndex > 2)) {
                    // only tell of first riddle answer
                    thing = commandListCommand;
                }
                if ((thing != commandListCommand) && (thing != undoCommand) && (thing != redoCommand) && (thing != repeatLastSayCommand)) {
                    if (thingsToSay.length() != 0) {
                        // and (thing <> 'look') then
                        thingsToSay = thingsToSay + ", ";
                        // won't work if options are last
                        // works, but sounds wrong
                        //if i = commandsListenedFor.size() - 1 then
                        //	thingsToSay := thingsToSay + 'and ';
                    }
                    thingsToSay = thingsToSay + thing;
                }
            }
        }
        if (thingsToSay.trim().equals("")) {
            thingsToSay = "nothing except " + commandListCommand + ".";
        }
        this.speakText("You can say: " + thingsToSay + ".");
    }
    
    public String stripMacros(String aString) {
        String result = "";
        // String macro = "";
        String toSay = "";
        
        String remaining = aString;
        
        while ((remaining.length() > 0)) {
            int startPosition = remaining.indexOf("{");
            if (startPosition >= 0) {
            	if (startPosition > 0) {
            		toSay = remaining.substring(0, startPosition - 1);
            	} else {
            		toSay = "";
            	}
                result = joinSentences(result, toSay);
                remaining = remaining.substring(startPosition + 1);
            } else {
                result = joinSentences(result, remaining);
                return result;
            }
            int endPosition = remaining.indexOf("}");
            if (endPosition == -1) {
                // error - unmatched braces
                result = joinSentences(result, remaining);
                return result;
            }
            // macro = remaining.substring(0, endPosition - 1);
            remaining = remaining.substring(endPosition + 1);
        }
        return result;
    }
 
    public void sayTextWithMacros(String aString) {
        String macro = "";
        String toSay = "";
        
        String remaining = aString;
        
        while ((remaining.length() > 0)) {
            int startPosition = remaining.indexOf("{");
            if (startPosition >= 0) {
            	if (startPosition > 0) {
            		toSay = remaining.substring(0, startPosition - 1);
            	} else {
            		toSay = "";
            	}
                if (!toSay.trim().equals("")) {
                    this.speakText(toSay);
                }
                remaining = remaining.substring(startPosition + 1);
            } else {
                if (!remaining.trim().equals("")) {
                    this.speakText(remaining);
                }
                return;
            }
            int endPosition = remaining.indexOf("}");
            if (endPosition == -1) {
                // error - unmatched braces
            	if (!remaining.trim().equals("")) {
            		this.speakText(remaining);
            	}
                return;
            }
            macro = remaining.substring(0, endPosition - 1);
            remaining = remaining.substring(endPosition + 1);
            if (macro.indexOf("options") == 0) {
                // cfk added
                this.sayOptionsMacroInForce = true;
            } else if (macro.indexOf(kShowPictureMacroStart) == 0) {
                this.showPicture(macro, this.stripMacros(aString));
            } else {
                this.speakSound(macro);
            }
        }
    }
    
    // cfk added
    public void checkForSayOptionsMacro() {
        if (!this.sayOptionsMacroInForce) {
            return;
        }
        try {
            this.sayOptions();
        } finally {
            this.sayOptionsMacroInForce = false;
        }
    }
    
    public void speakText(String somethingToSay) {
        if (!this.usingSpeech) {
            return;
        }
        if (!TSDomain.domain.options.playerSpeak) {
            return;
        }
        // PDF SPEECH FIX: Do something here
    }
    
    public void haltSpeechAndSound() {
    	haltMusic();
    	haltSound();
    	haltSpeech();
    }
    
    public void haltSpeech() {
        if (!this.usingSpeech) {
            return;
        }
        //if not domain.options.playerSpeak then exit;
        // PDF SPEECH FIX: Do something here
    }
    
    public void haltSound() {
        //if (!this.usingSpeech) {
        //    return;
        //}
        //if not domain.options.playerPlaySounds then exit;
        // PDF SPEECH FIX: Do something here
    }
    
    public void haltMusic() {
        if (sm_sequencer != null) {
        	sm_sequencer.stop();
        	midiFilePlaying = "";
        }
    }
    
    // PDF IMPROVE -- Does not run in the background
    // derived from: http://www.jsresources.org/examples/SimpleAudioPlayer.java.html
    public void playSampledSound(String fileName) {
    	System.out.println("Playing sound: " + fileName);
		try {
			int	EXTERNAL_BUFFER_SIZE = 128000;
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName));
			AudioFormat	audioFormat = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
			line.start();
			int	nBytesRead = 0;
			byte[]	abData = new byte[EXTERNAL_BUFFER_SIZE];
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0) {
					@SuppressWarnings("unused")
					int	nBytesWritten = line.write(abData, 0, nBytesRead);
				}
			}
			line.drain();
			line.close();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("done playing sound");
	}
    
    // derived from: http://www.jsresources.org/examples/SimpleMidiPlayer.java.html
    public void playMidiFile(String fileName) {
		try {
			File midiFile = new File(fileName);
			Sequence sequence = MidiSystem.getSequence(midiFile);
			if (sm_sequencer == null) {
				sm_sequencer = MidiSystem.getSequencer();
			}
			if (sm_sequencer == null) return;
			sm_sequencer.open();
			sm_sequencer.setSequence(sequence);
			if (! (sm_sequencer instanceof Synthesizer)) {
				Synthesizer sm_synthesizer = MidiSystem.getSynthesizer();
				sm_synthesizer.open();
				Receiver synthReceiver = sm_synthesizer.getReceiver();
				Transmitter	seqTransmitter = sm_sequencer.getTransmitter();
				seqTransmitter.setReceiver(synthReceiver);
				}
			sm_sequencer.start();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void speakSound(String soundDesignation) {
        String soundFileWithPath = "";
        
        String soundFile = soundDesignation.trim();
        boolean music = soundFile.startsWith(kPlayMusicMacroStart);
        boolean musiconce = soundFile.startsWith(kPlayMusicOnceMacroStart);
        music = music || (soundFile.equals(kPlayMusicMacroStart.trim()));
        musiconce = musiconce || (soundFile.equals(kPlayMusicOnceMacroStart.trim()));
        if (music || musiconce) {
            if (!TSDomain.domain.options.playerPlayMusic) {
                // or musicwait then
                return;
            }
            int lengthToCut = 0;
            if (music) {
            	lengthToCut = kPlayMusicMacroStart.length();
            } else {
            	lengthToCut = kPlayMusicOnceMacroStart.length();
            }
            if (lengthToCut < soundFile.length()) {
            	soundFile = soundFile.substring(lengthToCut);
            }
            soundFile = soundFile.trim();
            haltMusic();
            if (soundFile.equals("")) {
                //MediaPlayer.close();
                //MediaPlayer.fileName = "";
                return;
            }
            if (!soundFile.contains(".")) {
                soundFile = soundFile + ".mid";
            }
            soundFileWithPath = soundFile;
            if (!soundFileWithPath.contains(":")  && (!soundFileWithPath.startsWith("/"))) {
                soundFileWithPath = UFileSupport.findFileRecursivelyInMediaDirectories(soundFile, TSDomain.domain.options.extraMediaDirectory);
            }
            if ((!soundFileWithPath.equals("")) && (!soundFileWithPath.contains(":")) && (!soundFileWithPath.startsWith("/"))) {
                soundFileWithPath = UFileSupport.GetCurrentDir() + File.separator + soundFileWithPath;
            }
            if (UFileSupport.FileExists(soundFileWithPath)) {
                if (midiFilePlaying.equals(soundFileWithPath)) {
                    if (sm_sequencer.isRunning()) {
                        // already playing
                        return;
                    }
                }
                midiFilePlaying = soundFileWithPath;
                // PDF PORT FIX: NEED TO LOOP MUSIC BY PUTTING BACK EVENT HANDLER
                // setting notify to true would setup a callback event under Delphi, which would then loop
                // MediaPlayer.Notify = !musiconce;
                TSDomain.domain.options.loopMusic = !musiconce;
                playMidiFile(soundFile);
            }
            return;
        }
        if (!this.usingSpeech) {
            return;
        }
        if (!TSDomain.domain.options.playerPlaySounds) {
            return;
        }
        if (soundFile.startsWith(kPlaySoundMacroStart)) {
            // if pos('run ', soundFile) = 1 then
            //    begin
            //    soundFile := Copy(soundFile, length('run ') + 1, length(soundFile));
            //    WinExec(pchar(soundFile), SW_SHOWNORMAL);
            //    exit;
            //    end;
            soundFile = soundFile.substring(kPlaySoundMacroStart.length());
        } else if (soundFile.equals(kPlaySoundMacroStart.trim())) {
            soundFile = "";
        }
        soundFile = soundFile.trim();
        if ((soundFile.length() > 0) && (!soundFile.contains("."))) {
            soundFile = soundFile + ".wav";
        }
        soundFileWithPath = soundFile;
        if (!soundFileWithPath.contains(":") && !soundFileWithPath.startsWith("/")) {
            soundFileWithPath = UFileSupport.findFileRecursivelyInMediaDirectories(soundFile, TSDomain.domain.options.extraMediaDirectory);
        }
        if ((!soundFileWithPath.equals("")) && (!soundFileWithPath.contains(":")) && (!soundFileWithPath.startsWith("/"))) {
            soundFileWithPath = UFileSupport.GetCurrentDir() + File.separator + soundFileWithPath;
        }
        if (UFileSupport.FileExists(soundFileWithPath)) {
        	playSampledSound(soundFileWithPath);
        }
    }
    
    public void showPicture(String pictureName, String reply) {
        String pictureFile = pictureName.substring(kShowPictureMacroStart.length());
        pictureFile = pictureFile.trim();
        if (pictureFile.equals("")) {
            return;
        }
        if (pictureFile.indexOf(".") == -1) {
            pictureFile = pictureFile + ".bmp";
        }
        String pictureFileWithPath = pictureFile;
        // PDF PORT: checking for drive letter and absolute path?
        if (!pictureFileWithPath.contains(":") && (!pictureFileWithPath.startsWith("/"))) {
            pictureFileWithPath = UFileSupport.findFileRecursivelyInMediaDirectories(pictureFile, TSDomain.domain.options.extraMediaDirectory);
        }
        if ((!pictureFileWithPath.equals("")) && (!pictureFileWithPath.contains(":")) && (!pictureFileWithPath.startsWith("/"))) {
            pictureFileWithPath = UFileSupport.GetCurrentDir() + File.separator + pictureFileWithPath;
        }
        if (UFileSupport.FileExists(pictureFileWithPath)) {
            Application.PictureForm.addPictureFromFile(pictureFileWithPath, reply);
        }
    }
    
    public void clearVoiceCommands() {
        if (!this.usingSpeech) {
            return;
        }
        // PDF SPEECH FIX: Do something here
    }
    
    public String hideRiddleAnswerForCommand(String aString) {
        String result = "";
        if (aString.startsWith(riddlePrefix)) {
            if (this.riddleIndex == 1) {
                result = firstRiddleAnswer;
            } else if (this.riddleIndex == 2) {
                result = "say another answer for a riddle";
            } else {
                result = "say yet another answer for a riddle - number " + String.valueOf(this.riddleIndex);
            }
            this.riddleIndex = this.riddleIndex + 1;
        } else {
            result = aString;
        }
        return result;
    }
    
    public void listenForPhraseCaptionCommand(String listenFor, String commandCaption, String command) {
        if ((listenFor.equals("")) || (command.equals("")) || (commandCaption.equals(""))) {
            return;
        }
        if (this.usingSpeech) {
        	// PDF SPEECH FIX: Do something here
        }
        this.commandsListenedFor.add(command);
    }
    
    public void listenForCommand(String aString) {
        String listenFor = "";
        String commandCaption = "";
        
        if ((this.commandsListenedFor.indexOf(aString) == -1)) {
            commandCaption = this.hideRiddleAnswerForCommand(aString);
            if (!commandCaption.equals(aString)) {
                listenFor = aString.substring(1).trim();
            } else {
                listenFor = aString;
            }
            this.listenForPhraseCaptionCommand(listenFor, commandCaption, aString);
        }
    }
    
    // setsa up speech system and also updates GUI list of commands
    public void listenForAvailableCommands() {
        TSRule rule = new TSRule();
        int i = 0;
        String command = "";
        
        this.clearVoiceCommands();
        this.commandsListenedFor.clear();
        Application.ConsoleForm.visibleCommandsListModel.clear();
        this.riddleIndex = 1;
        if (TSDomain.domain.world.rules.size() <= 0) {
            return;
        }
        this.listenForCommand(commandListCommand);
        this.listenForCommand(repeatLastSayCommand);
        if (TSDomain.domain.options.useVoiceToUndo) {
            this.listenForCommand(undoCommand);
        }
        if (TSDomain.domain.options.useVoiceToRedo) {
            this.listenForCommand(redoCommand);
        }
        for (i = 0; i < TSDomain.domain.world.rules.size(); i++) {
            rule = TSDomain.domain.world.rules.get(i);
            if (rule.available) {
                if (rule.command.phrase.equals("")) {
                    continue;
                }
                this.listenForCommand(rule.command.phrase);
                command = rule.command.phrase;
                if (rule.command.phrase.startsWith(riddlePrefix)) {
                    // only list first riddle answer
                    command = firstRiddleAnswer;
                }
                if (Application.ConsoleForm.visibleCommandsListModel.indexOf(command) == -1) {
                	Application.ConsoleForm.visibleCommandsListModel.addElement(command);
                    // PDF PORT ISSUE: Is the rule needed? Application.ConsoleForm.VisibleCommandsListModel.AddObject(command, rule);
                }
            }
        }
    }
    
}
