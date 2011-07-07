package com.kurtz_fernhout.storyharp;
public class UCursor {
	// const
	public static final int crMagMinus = 1;
	public static final int crMagPlus = 2;
	public static final int crScroll = 3;
	public static final int crRotate = 4;
	public static final int crDragPlant = 5;
	public static final int crAddTriangle = 6;
	public static final int crDeleteTriangle = 7;
	public static final int crFlipTriangle = 8;
	public static final int crDragPoint = 9;
	
	
	// var
	public static int waitState = 0;
	
	
	//$R CURSOR32
	public static void cursor_initializeWait() {
	    waitState = 0;
	}
	
	public static void cursor_startWait() {
	    waitState += 1;
	    // PDF PORT: delphi_compatability.Screen.Cursor = delphi_compatability.crHourGlass;
	}
	
	public static void cursor_startWaitIfNotWaiting() {
	    if (waitState == 0) {
	        cursor_startWait();
	    }
	}
	
	public static void cursor_stopWait() {
	    if (waitState > 0) {
	        waitState -= 1;
	        if (waitState == 0) {
	            // PDF PORT: delphi_compatability.Screen.Cursor = delphi_compatability.crDefault;
	        }
	    }
	}
	
	//Note:	You don't need to call the WinAPI function DestroyCursor when you are finished using the custom
	//cursor; Delphi does this automatically. 
	public static void cursor_loadCustomCursors() {
		// PDF PORT: FIX
//	    delphi_compatability.Screen.Cursors[crMagMinus] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "MAGMINUS");
//	    delphi_compatability.Screen.Cursors[crMagPlus] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "MAGPLUS");
//	    delphi_compatability.Screen.Cursors[crScroll] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "SCROLL");
//	    delphi_compatability.Screen.Cursors[crRotate] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "ROTATE");
//	    delphi_compatability.Screen.Cursors[crDragPlant] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "DRAGPLANT");
//	    delphi_compatability.Screen.Cursors[crAddTriangle] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "ADDTRIANGLEPOINT");
//	    delphi_compatability.Screen.Cursors[crDeleteTriangle] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "DELETETRIANGLE");
//	    delphi_compatability.Screen.Cursors[crFlipTriangle] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "FLIPTRIANGLE");
//	    delphi_compatability.Screen.Cursors[crDragPoint] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "DRAGPOINT");
	}

}
