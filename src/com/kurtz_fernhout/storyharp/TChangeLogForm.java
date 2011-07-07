package com.kurtz_fernhout.storyharp;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class TChangeLogForm extends JFrame {
    private static final long serialVersionUID = 1L;
    JPanel bottomPanel;
    JButton CopySelectedTextButton;
    JButton UpdateButton;
    JButton changeLogFile;
    JButton clearLogFile;
    JButton helpButton;
    JButton okButton;
    JTextArea LogContentsRichEdit;
    // UNHANDLED_TYPE OpenDialog;
    public JPanel mainContentPane;
    public JPanel delphiPanel;
    
    public boolean issuedWarning;
    
    public TChangeLogForm() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // HIDE_ON_CLOSE DO_NOTHING_ON_CLOSE
        initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(618, 325));
        this.setContentPane(getMainContentPane());
        this.setTitle("Log file");
    }
    
    private JPanel getMainContentPane() {
        if (mainContentPane == null) {
            mainContentPane = new JPanel();
            mainContentPane.setLayout(new BorderLayout());
            mainContentPane.add(getDelphiPanel(), BorderLayout.CENTER);
        }
        return mainContentPane;
    }
    public void OnClose(ActionEvent event) {
        System.out.println("Closed");
    }
    
    public JPanel getDelphiPanel() {
        if (delphiPanel != null) return delphiPanel;
        
        delphiPanel = new JPanel(new BorderLayout());
        //  --------------- UNHANDLED ATTRIBUTE: this.TextHeight = 14;
        //  --------------- UNHANDLED ATTRIBUTE: this.ActiveControl = CopySelectedTextButton;
        //  --------------- UNHANDLED ATTRIBUTE: this.KeyPreview = True;
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent event) {
                FormKeyUp(event);
            }
        });
        //  --------------- UNHANDLED ATTRIBUTE: this.Position = poScreenCenter;
        //  --------------- UNHANDLED ATTRIBUTE: this.OnCreate = FormCreate;
        //  --------------- UNHANDLED ATTRIBUTE: this.PixelsPerInch = 100;
        
        
        this.CopySelectedTextButton = new JButton("Copy selection");
        CopySelectedTextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                CopySelectedTextButtonClick(event);
            }
        });
        this.CopySelectedTextButton.setMnemonic(KeyEvent.VK_C);
        
        this.UpdateButton = new JButton("Reload");
        UpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                UpdateButtonClick(event);
            }
        });
        this.UpdateButton.setMnemonic(KeyEvent.VK_R);
        
        this.changeLogFile = new JButton("Change log file...");
        changeLogFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                changeLogFileClick(event);
            }
        });
        this.changeLogFile.setMnemonic(KeyEvent.VK_A);
        
        this.clearLogFile = new JButton("Clear");
        clearLogFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                clearLogFileClick(event);
            }
        });
        this.clearLogFile.setMnemonic(KeyEvent.VK_L);
        
        this.helpButton = new JButton("Help");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                helpButtonClick(event);
            }
        });
        this.helpButton.setMnemonic(KeyEvent.VK_H);
        
        this.okButton = new JButton("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                okButtonClick(event);
            }
        });
        this.helpButton.setMnemonic(KeyEvent.VK_O);
        
        this.bottomPanel = new JPanel(new FlowLayout());
        // -- this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, BoxLayout.Y_AXIS));
        this.bottomPanel.add(CopySelectedTextButton);
        this.bottomPanel.add(UpdateButton);
        this.bottomPanel.add(changeLogFile);
        this.bottomPanel.add(clearLogFile);
        this.bottomPanel.add(helpButton);
        this.bottomPanel.add(okButton);
        //  --------------- UNHANDLED ATTRIBUTE: this.bottomPanel.OnResize = bottomPanelResize;
        
        this.LogContentsRichEdit = new JTextArea(10, 10);
        //  --------------- UNHANDLED ATTRIBUTE: this.LogContentsRichEdit.Name = 'LogContentsRichEdit';
        //  --------------- UNHANDLED ATTRIBUTE: this.LogContentsRichEdit.Cursor = crIBeam;
        
        //  ------- UNHANDLED TYPE TOpenDialog: OpenDialog 
        //  --------------- UNHANDLED ATTRIBUTE: this.OpenDialog.Title = 'Open existing file';
        //  --------------- UNHANDLED ATTRIBUTE: this.OpenDialog.FilterIndex = 0;
        //  --------------- UNHANDLED ATTRIBUTE: this.OpenDialog.y = 188;
        //  --------------- UNHANDLED ATTRIBUTE: this.OpenDialog.Options = [ofOverwritePrompt, ofHideReadOnly];
        //  --------------- UNHANDLED ATTRIBUTE: this.OpenDialog.x = 372;
        
        delphiPanel.add(bottomPanel, BorderLayout.SOUTH);
        JScrollPane scroller1 = new JScrollPane();
        scroller1.setViewportView(LogContentsRichEdit);
        delphiPanel.add(scroller1, BorderLayout.CENTER);
        return delphiPanel;
    }
    
        
    public void CopySelectedTextButtonClick(ActionEvent event) {
        this.LogContentsRichEdit.copy();
    }
        
    public void FormKeyUp(KeyEvent event) {
        System.out.println("FormKeyUp");
    }
        
    public void UpdateButtonClick(ActionEvent event) {
        this.loadChangeLog();
        this.scrollLogEndIntoView();
    }
        
    public void changeLogFileClick(ActionEvent event) {
        String backupLogFileName = "";
        
        this.OpenDialog.Title = "Choose or type in a new log file name";
        this.OpenDialog.FileName = TSDomain.domain.options.logFileName;
        this.OpenDialog.Filter = "Log files (*.log)|*.log|All files (*.*)|*.*";
        this.OpenDialog.DefaultExt = "log";
        if (this.OpenDialog.Execute()) {
            backupLogFileName = UFileSupport.ChangeFileExt(TSDomain.domain.options.logFileName, ".~lo");
            makeBackupCopy(TSDomain.domain.options.logFileName, backupLogFileName);
            TSDomain.domain.options.logFileName = this.OpenDialog.FileName;
            if (!UFileSupport.FileExists(TSDomain.domain.options.logFileName)) {
                this.clearLog();
            }
            this.loadChangeLog();
            this.scrollLogEndIntoView();
        }
    }
        
    public void clearLogFileClick(ActionEvent event) {
        String backupLogFileName = "";
        
        if (!UFileSupport.Confirm("Are you sure you want to clear the log file?\nThis is not undoable, but a backup file will be made.")) {
            return;
        }
        backupLogFileName = UFileSupport.ChangeFileExt(TSDomain.domain.options.logFileName, ".~log");
        UFileSupport.DeleteFile(backupLogFileName);
        UFileSupport.RenameFile(TSDomain.domain.options.logFileName, backupLogFileName);
        // PDF PORT: NO longer needed if missing log is OK? this.clearLog();
    }
        
    public void helpButtonClick(ActionEvent event) {
        Application.HelpJump("Using_the_Change_Log_file_to_recover_text");
    }
    
    public void okButtonClick(ActionEvent event) {
        this.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	TChangeLogForm thisClass = new TChangeLogForm();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    public void fileErrorWarning() {
        if (this.issuedWarning) {
            return;
        }
        UFileSupport.ShowMessage("Could not write to the log file: " + TSDomain.domain.options.logFileName + "\nThe file may be write protected or in use by another program, or your disk may be full.\nYour changes will not be logged.");
        this.issuedWarning = true;
    }
    
    public void ensureLogFileName() {
        if (TSDomain.domain.options.logFileName.equals("")) {
            TSDomain.domain.options.logFileName = UFileSupport.getExePathAndSeparator() + TSDomain.kDefaultLogFileName;
        }
    }
    
    public void addToLog(String change, boolean appendNewLine) {
        if (change.trim().equals("")) {
            return;
        }
        this.ensureLogFileName();
        
    	FileWriter outputStream = null;
		try {
			outputStream = new FileWriter(TSDomain.domain.options.logFileName);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		try {
        outputStream.write(change);
        if (appendNewLine) {
        	outputStream.write("\n");
        }
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                if (!this.issuedWarning) {
                    UFileSupport.ShowMessage("Problem closing log file " + TSDomain.domain.options.logFileName);
                }
            }
        }
    }
    
    public void addToLog(String change) {
    	this.addToLog(change, true);
    }
    
    public void clearLog() {
        this.ensureLogFileName();
        UFileSupport.DeleteFile(TSDomain.domain.options.logFileName);
        this.loadChangeLog();
    }
    
    public void loadChangeLog() {
        this.ensureLogFileName();
        this.LogContentsRichEdit.setText("");
        if (!UFileSupport.FileExists(TSDomain.domain.options.logFileName)) {
            return;
        }
         
    	BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new FileReader(TSDomain.domain.options.logFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			String newText = inputStream.readLine();
			while (newText != null) {
				this.LogContentsRichEdit.append(newText);
				this.LogContentsRichEdit.append("\n");
				newText = inputStream.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

        this.setTitle("Log file " + UFileSupport.ExtractFileName(TSDomain.domain.options.logFileName));
    }
    
    public void scrollLogEndIntoView() {
        this.LogContentsRichEdit.setCaretPosition(this.LogContentsRichEdit.getText().length());
    }
    
//    public void FormCreate(TObject Sender) {
//        Application.setFormSize(this, TSDomain.domain.options.logFileWindowRect);
//    }
//
//    public void WMGetMinMaxInfo(Tmessage MSG) {
//        super.WMGetMinMaxInfo();
//        //FIX unresolved WITH expression: UNRESOLVED.PMinMaxInfo(MSG.lparam).PDF_FIX_POINTER_ACCESS
//        UNRESOLVED.ptMinTrackSize.x = 316;
//        UNRESOLVED.ptMinTrackSize.y = 100;
//    }
//
//    
//    public void FormKeyUp(TObject Sender, byte Key, TShiftState Shift) {
//        if (event.getKeyCode() == KeyEvent.VK_F2) {
//            this.showTechSupportInfoInChangeLog();
//        }
//        return Key;
//    }
//    
//    public void showTechSupportInfoInChangeLog() {
//        long numRules = 0;
//        long numVariables = 0;
//        TMemoryStatus memoryStatus = new TMemoryStatus();
//        long diskSpace = 0;
//        int screenColorBits = 0;
//        long screenColors = 0;
//        HDC screenDC = new HDC();
//        
//        this.LogContentsRichEdit.lines.add("");
//        this.LogContentsRichEdit.lines.add("StoryHarp Technical Support Information");
//        this.LogContentsRichEdit.lines.add("");
//        numRules = TSDomain.domain.world.rules.size();
//        this.LogContentsRichEdit.lines.add("Rules: " + String.valueOf(numRules));
//        numVariables = TSDomain.domain.world.variables.size();
//        this.LogContentsRichEdit.lines.add("Variables: " + String.valueOf(numVariables));
//        // more here...
//        this.LogContentsRichEdit.lines.add("");
//        screenDC = UNRESOLVED.GetDC(0);
//        try {
//            screenColorBits = (UNRESOLVED.GetDeviceCaps(screenDC, delphi_compatability.BITSPIXEL) * UNRESOLVED.GetDeviceCaps(screenDC, delphi_compatability.PLANES));
//        } finally {
//            UNRESOLVED.ReleaseDC(0, screenDC);
//        }
//        if (screenColorBits != 32) {
//            screenColors = 1 << screenColorBits;
//        } else {
//            screenColors = Math.round(UNRESOLVED.power(2.0, screenColorBits));
//        }
//        this.LogContentsRichEdit.lines.add("Colors: " + String.valueOf(screenColors) + " (" + String.valueOf(screenColorBits) + " bits)");
//        this.LogContentsRichEdit.lines.add("Size: " + String.valueOf(delphi_compatability.Screen.Width) + " x " + String.valueOf(delphi_compatability.Screen.Height));
//        this.LogContentsRichEdit.lines.add("Resolution: " + String.valueOf(delphi_compatability.Screen.PixelsPerInch) + " pixels/inch");
//        this.LogContentsRichEdit.lines.add("");
//        this.GetOSInfo();
//        memoryStatus.dwLength = FIX_sizeof(memoryStatus);
//        UNRESOLVED.GlobalMemoryStatus(memoryStatus);
//        this.LogContentsRichEdit.lines.add("Percent memory in use: " + String.valueOf(memoryStatus.dwMemoryLoad));
//        this.LogContentsRichEdit.lines.add("Total physical memory: " + String.valueOf(memoryStatus.dwTotalPhys / 1024) + " K");
//        this.LogContentsRichEdit.lines.add("Available physical memory: " + String.valueOf(memoryStatus.dwAvailPhys / 1024) + " K");
//        this.LogContentsRichEdit.lines.add("Total paging file: " + String.valueOf(memoryStatus.dwTotalPageFile / 1024) + " K");
//        this.LogContentsRichEdit.lines.add("Available paging file: " + String.valueOf(memoryStatus.dwAvailPageFile / 1024) + " K");
//        this.LogContentsRichEdit.lines.add("Total user memory: " + String.valueOf(memoryStatus.dwTotalVirtual / 1024) + " K");
//        this.LogContentsRichEdit.lines.add("Available user memory: " + String.valueOf(memoryStatus.dwAvailVirtual / 1024) + " K");
//        diskSpace = UNRESOLVED.DiskFree(0);
//        this.LogContentsRichEdit.lines.add("Disk space on current drive: " + String.valueOf(diskSpace / (1024 * 1024)) + " MB");
//    }
//    
//    public void GetOSInfo() {
//        String Platform = "";
//        int BuildNumber = 0;
//        
//        switch (UNRESOLVED.Win32Platform) {
//            case UNRESOLVED.VER_PLATFORM_WIN32_WINDOWS:
//                Platform = "Windows 95";
//                BuildNumber = UNRESOLVED.Win32BuildNumber && 0x0000FFFF;
//                break;
//            case UNRESOLVED.VER_PLATFORM_WIN32_NT:
//                Platform = "Windows NT";
//                BuildNumber = UNRESOLVED.Win32BuildNumber;
//                break;
//            default:
//                Platform = "Windows";
//                BuildNumber = 0;
//                break;
//        if ((UNRESOLVED.Win32Platform == UNRESOLVED.VER_PLATFORM_WIN32_WINDOWS) || (UNRESOLVED.Win32Platform == UNRESOLVED.VER_PLATFORM_WIN32_NT)) {
//            if (UNRESOLVED.Win32CSDVersion.equals("")) {
//                this.LogContentsRichEdit.lines.add(UNRESOLVED.Format("%s %d.%d (Build %d)", {Platform, UNRESOLVED.Win32MajorVersion, UNRESOLVED.Win32MinorVersion, BuildNumber, }));
//            } else {
//                this.LogContentsRichEdit.lines.add(UNRESOLVED.Format("%s %d.%d (Build %d: %s)", {Platform, UNRESOLVED.Win32MajorVersion, UNRESOLVED.Win32MinorVersion, BuildNumber, UNRESOLVED.Win32CSDVersion, }));
//            }
//        } else {
//            this.LogContentsRichEdit.lines.add(UNRESOLVED.Format("%s %d.%d", {Platform, UNRESOLVED.Win32MajorVersion, UNRESOLVED.Win32MinorVersion, }));
//        }
//    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
