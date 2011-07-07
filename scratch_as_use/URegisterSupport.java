package com.kurtz_fernhout.storyharp;

public class URegisterSupport {
	// record
	class RegisterStruct {
	    public int s1;
	    public int s2;
	    public int s3;
	    public int s4;
	}

	// const
	public static final int codeArraySize = 9;


	public static void WriteCodeFile(String userName, String code, String fileNameMaybeLeadingPipe) {
	    int success = 0;
	    TextFile CodeFile = new TextFile();
	    String tempFileName = "";
	    String tempFilePath = "";
	    String fileName = "";
	    boolean warningIfExists = false;
	    
	    warningIfExists = true;
	    if (fileNameMaybeLeadingPipe.charAt(0) == '|') {
	        fileName = UNRESOLVED.copy(fileNameMaybeLeadingPipe, 2, fileNameMaybeLeadingPipe.length());
	        warningIfExists = false;
	    } else {
	        fileName = fileNameMaybeLeadingPipe;
	    }
	    tempFilePath = ExtractFilePath(fileName);
	    if (tempFilePath.equals("")) {
	        UNRESOLVED.SetLength(tempFilePath, UNRESOLVED.MAX_PATH + 2);
	        UNRESOLVED.GetTempPath(UNRESOLVED.MAX_PATH, tempFilePath);
	        // maybe plus one??
	        UNRESOLVED.SetLength(tempFilePath, tempFilePath.length());
	    }
	    UNRESOLVED.SetLength(tempFileName, UNRESOLVED.MAX_PATH + 2);
	    // address of directory name for temporary file
	    // address of filename prefix
	    // number used to create temporary filename
	    // address of buffer that receives the new filename
	    success = UNRESOLVED.GetTempFileName(tempFilePath, "shr", 0, tempFileName);
	    if (success == 0) {
	    	UFileSupport.ShowMessage((UFileSupport.getExeName() + ": Unable to generate temporary file name for StoryHarp registration");
	        return;
	    }
	    // maybe plus one??
	    UNRESOLVED.SetLength(tempFileName, tempFileName.length());
	    AssignFile(CodeFile, tempFileName);
	    Rewrite(CodeFile);
	    try {
	        writeln(CodeFile, "StoryHarp 1.x registration information");
	        writeln(CodeFile, "Registration Name: ", userName);
	        writeln(CodeFile, "Registration Code: ", code);
	        Flush(CodeFile);
	    } finally {
	        CloseFile(CodeFile);
	    }
	    if (FileExists(fileName)) {
	        if (warningIfExists) {
	            if (!UFileSupport.Confirm(UFileSupport.getExeName() + ": " + "\n" + "A problem has occured in the coordination of the PsL order-entry system and" + "\n" + "the program which generates the StoryHarp registration code output file. " + "\n" + "\n" + "The requested output file \"" + fileName + "\" already exists, and it should not exist." + "\n" + "The existing file will be deleted and then created again if you press OK." + "\n" + "(If you press Cancel the file will be left as is.)" + "\n" + "\n" + "However, because the PsL order-entry system is supposed to be waiting right now" + "\n" + "for a file with this name to appear, something improper is happening" + "\n" + "If you see this error, it is possible a registration code for someone else " + "\n" + "has already been given to the customer \"" + userName + "\"." + "\n" + "\n" + "This is a serious error in the way PsL uses this program and should be reported and corrected." + "\n" + "\n" + "If you wish to never see this error again (not recommended), " + "\n" + "you can bypass it by putting a \"|\" at the start of the file path in the second argument." + "\n" + "Contact Paul or Cynthia at http://www.kurtz-fernhout.com for details.") {
	                // questionable?
	                return;
	            }
	        }
	        DeleteFile(fileName);
	    }
	    AssignFile(CodeFile, tempFileName);
	    UNRESOLVED.Rename(CodeFile, fileName);
	}

	public static int nextRandom(RegisterStruct generator) {
	    result = 0;
	    int newValue = 0;
	    
	    // one added to defeat case of all zeroing out seeds
	    newValue = generator.s1 + generator.s2 + 1;
	    generator.s1 = generator.s2;
	    generator.s2 = generator.s3;
	    generator.s3 = generator.s4;
	    generator.s4 = newValue % 65536;
	    result = generator.s1 % 256;
	    return result;
	}

	// only used to calculate initial seeds
	public static void WarmUp(RegisterStruct generator) {
	    int i = 0;
	    
	    for (i = 0; i <= 897; i++) {
	        // SH 897
	        nextRandom(generator);
	    }
	}

    // SH   37903 61164  59082  16777
	// Maybe PS ??? 234    34432    4356   7

	public static void ResetGenerator(RegisterStruct generator) {
	    generator.s1 = 37903;
	    generator.s2 = 61164;
	    generator.s3 = 59082;
	    generator.s4 = 16777;
	}

	public static String generate(String name) {
	    result = "";
	    int i = 0;
	    int j = 0;
	    int n = 0;
	    int nmod = 0;
	    char letter = ' ';
	    String reorganizedName = "";
	    String remainingLetters = "";
	    String nameWithOnlyChars = "";
	    String lowerCaseName = "";
	     codeArray = [0] * (range(0, (codeArraySize - 1) + 1) + 1);
	    RegisterStruct generator = new RegisterStruct();
	    
	    ResetGenerator(generator);
	    result = "";
	    nameWithOnlyChars = "";
	    lowerCaseName = name.toLowerCase().trim();
	    for (i = 1; i <= lowerCaseName.length(); i++) {
	        if ((ord(lowerCaseName[i]) >= ord("a")) && (ord(lowerCaseName[i]) <= ord("z")) || (ord(lowerCaseName[i]) >= ord("0")) && (ord(lowerCaseName[i]) <= ord("9"))) {
	            nameWithOnlyChars = nameWithOnlyChars + lowerCaseName[i];
	        }
	    }
	    remainingLetters = nameWithOnlyChars;
	    reorganizedName = "";
	    for (i = 1; i <= nameWithOnlyChars.length(); i++) {
	        letter = nameWithOnlyChars[nameWithOnlyChars.length() + 1 - i];
	        for (j = 0; j <= (ord(letter)); j++) {
	            //  + i * 7
	            nextRandom(generator);
	        }
	        n = nextRandom(generator);
	        nmod = n % remainingLetters.length();
	        reorganizedName = reorganizedName + remainingLetters[nmod + 1];
	        UNRESOLVED.Delete(remainingLetters, nmod + 1, 1);
	    }
	    for (i = 0; i < codeArraySize; i++) {
	        codeArray[i] = nextRandom(generator);
	    }
	    for (i = 1; i <= reorganizedName.length(); i++) {
	        letter = reorganizedName[reorganizedName.length() + 1 - i];
	        if (letter == ' ') {
	            continue;
	        }
	        for (j = 0; j <= (ord(letter)); j++) {
	            //  + i * 7
	            nextRandom(generator);
	        }
	        n = nextRandom(generator);
	        nmod = n % 10;
	        codeArray[i % codeArraySize] = codeArray[i % codeArraySize] + nmod;
	    }
	    result = "3";
	    for (i = 0; i < codeArraySize; i++) {
	        result = result + chr(ord("0") + (codeArray[i] % 10));
	    }
	    return result;
	}

	public static void GenerateFromCommandLine() {
	    String userName = "";
	    String fileName = "";
	    String code = "";
	    
	    if (UNRESOLVED.ParamCount != 2) {
	    	UFileSupport.ShowMessage(("Useage: " + UFileSupport.getExeName() + " \"user name\"  \"file name\" \n\n The key code generator " + UFileSupport.getExeName() + 
	        		" expects two arguments.\nThe first is the user name,\n" +
	        		"and the second is the name of a text file to store the code in.\n" +
	        		"Arguments that can have embedded spaces like the user name\nmust be surrounded by double quotes.\n" +
	        		"Example: " + UFileSupport.getExeName() + 
	        		" \"Joe User\"  \"C:\\temp\\dir with spaces\\regfile.txt\"\n" +
	        		"Contact us at http://www.kurtz-fernhout.com for details\n\n" +
	        		"This program operates under the expectation the output file does not exist.\n" +
	        		"If for some reason the output file exists when the program is started\n" +
	        		"a warning dialog will pop-up requiring OK or Cancel to be pressed.\n" +
	        		"(unless you have disabled that warning as explained on that dialog).\n" +
	        		"This program is Copyright 1998 Paul D. Fernhout and Cynthia F. Kurtz\n" +
	        		"This key generator is confidential intellectual property of the authors\n" +
	        		"and is intended only for use only by the Nelson Ford and his staff\n" +
	        		"at the Public Software Library (PsL) for generation of keys\n" +
	        		"for StoryHarp users who have paid a registration fee.");
	        return;
	    }
	    userName = UNRESOLVED.ParamStr(1);
	    fileName = UNRESOLVED.ParamStr(2);
	    code = generate(userName);
	    WriteCodeFile(userName, code, fileName);
	}

	public static boolean RegistrationMatch(String name, String code) {
	    String collapsedCode = "";
	    for (int i = 0; i < code.length(); i++) {
	    	char c = code.charAt(i);
	        if ((c >= '0') && (c <= '9')) {
	            collapsedCode = collapsedCode + Character.toString(c);
	        }
	    }
	    // PDF PORT: MAYBE FIX: return collapsedCode.equals(generate(name));
	    return true;
	}
}
