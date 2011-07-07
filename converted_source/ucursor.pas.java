// unit ucursor

from conversion_common import *;
import delphi_compatability;

// const
crMagMinus = 1;
crMagPlus = 2;
crScroll = 3;
crRotate = 4;
crDragPlant = 5;
crAddTriangle = 6;
crDeleteTriangle = 7;
crFlipTriangle = 8;
crDragPoint = 9;


// var
int waitState = 0;


//$R CURSOR32
public void cursor_initializeWait() {
    waitState = 0;
}

public void cursor_startWait() {
    waitState += 1;
    delphi_compatability.Screen.Cursor = delphi_compatability.crHourGlass;
}

public void cursor_startWaitIfNotWaiting() {
    if (waitState == 0) {
        cursor_startWait();
    }
}

public void cursor_stopWait() {
    if (waitState > 0) {
        waitState -= 1;
        if (waitState == 0) {
            delphi_compatability.Screen.Cursor = delphi_compatability.crDefault;
        }
    }
}

//Note:	You don't need to call the WinAPI function DestroyCursor when you are finished using the custom
//cursor; Delphi does this automatically. 
public void cursor_loadCustomCursors() {
    delphi_compatability.Screen.Cursors[crMagMinus] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "MAGMINUS");
    delphi_compatability.Screen.Cursors[crMagPlus] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "MAGPLUS");
    delphi_compatability.Screen.Cursors[crScroll] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "SCROLL");
    delphi_compatability.Screen.Cursors[crRotate] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "ROTATE");
    delphi_compatability.Screen.Cursors[crDragPlant] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "DRAGPLANT");
    delphi_compatability.Screen.Cursors[crAddTriangle] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "ADDTRIANGLEPOINT");
    delphi_compatability.Screen.Cursors[crDeleteTriangle] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "DELETETRIANGLE");
    delphi_compatability.Screen.Cursors[crFlipTriangle] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "FLIPTRIANGLE");
    delphi_compatability.Screen.Cursors[crDragPoint] = UNRESOLVED.LoadCursor(UNRESOLVED.HInstance, "DRAGPOINT");
}


