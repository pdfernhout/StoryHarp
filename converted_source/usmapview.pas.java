// unit usmapview

from conversion_common import *;
import usruleeditorform;
import usdomain;
import ucommand;
import usworld;
import delphi_compatability;

// const
kDrawCommand = true;
kDrawContext = false;


// const
arrowlength = 10;
arrowwidth = 4;


//needs to have rectangle with center of 0,0 and origin adjusted to that coordinate system
public TPoint AdjustedIntersectionPointForLineAndRectangle(TPoint origin, TRect rect) {
    result = new TPoint();
    double slope = 0.0;
    
    if (delphi_compatability.PtInRect(Rect, origin)) {
        result = Point(0, 0);
        return result;
    }
    if ((Rect.left == 0) || (Rect.top == 0) || (Rect.right == 0) || (Rect.bottom == 0)) {
        result = Point(0, 0);
        return result;
    }
    if (origin.X == 0) {
        if (origin.Y < 0) {
            //do zero cases to avoid divide by zero later
            result = Point(0, Rect.top);
        } else if (origin.Y == 0) {
            // pathalogical case
            // origin.y > 0
            result = Point(0, 0);
        } else {
            result = Point(0, Rect.bottom);
        }
    } else if (origin.Y == 0) {
        if (origin.X < 0) {
            // origin.x > 0
            result = Point(Rect.left, 0);
        } else {
            result = Point(Rect.right, 0);
        }
    } else {
        slope = (origin.Y * 1.0) / origin.X;
        if ((origin.X > 0) && (origin.Y < 0)) {
            if (slope < Rect.top * 1.0 / Rect.right) {
                result = Point(intround(Rect.top / slope), Rect.top);
            } else {
                result = Point(Rect.right, intround(Rect.right * slope));
            }
        } else if ((origin.X > 0) && (origin.Y > 0)) {
            if (slope > Rect.bottom * 1.0 / Rect.right) {
                result = Point(intround(Rect.bottom / slope), Rect.bottom);
            } else {
                result = Point(Rect.right, intround(Rect.right * slope));
            }
        } else if ((origin.X < 0) && (origin.Y < 0)) {
            if (slope > Rect.top * 1.0 / Rect.left) {
                result = Point(intround(Rect.top / slope), Rect.top);
            } else {
                result = Point(Rect.left, intround(Rect.left * slope));
            }
        } else if ((origin.X < 0) && (origin.Y > 0)) {
            if (slope < Rect.bottom * 1.0 / Rect.left) {
                result = Point(intround(Rect.bottom / slope), Rect.bottom);
            } else {
                result = Point(Rect.left, intround(Rect.left * slope));
            }
        }
    }
    return result;
}

public TPoint IntersectionPointForLineAndRectangle(TPoint origin, TRect destRect) {
    result = new TPoint();
    TPoint center = new TPoint();
    TRect adjustedRect = new TRect();
    TPoint adjustedOrigin = new TPoint();
    
    center = Point((destRect.Left + destRect.Right) / 2, (destRect.Top + destRect.Bottom) / 2);
    //make center of rectangle = 0,0
    adjustedRect = Rect(destRect.Left - center.X, destRect.Top - center.Y, destRect.Right - center.X, destRect.Bottom - center.Y);
    adjustedOrigin = Point(origin.X - center.X, origin.Y - center.Y);
    result = AdjustedIntersectionPointForLineAndRectangle(adjustedOrigin, adjustedRect);
    result.X = result.X + center.X;
    result.Y = result.Y + center.Y;
    return result;
}


class TSMapView extends TObject {
    public TPoint scroll;
    
    // TSMapView ---------------------------------------- 
    public void drawBogusArrow(TCanvas canvas, TPoint a, TPoint b) {
        TPoint midPoint2 = new TPoint();
        TColor oldColor = new TColor();
        
        //midPoint1: TPoint;
        //midPoint1 := Point(a.x + (b.x - a.x) div 3, a.y + (b.y - a.y) div 3);
        midPoint2 = Point(a.X + (b.X - a.X) / 3, a.Y + (b.Y - a.Y) / 3);
        //midPoint2 := Point(b.x - (b.x - a.x) mod 2, b.y - (b.y - a.y) div 5);
        canvas.Pen.Style = delphi_compatability.TFPPenStyle.psSolid;
        canvas.MoveTo(a.X + this.scroll.X, a.Y + this.scroll.Y);
        canvas.LineTo(b.X + this.scroll.X, b.Y + this.scroll.Y);
        oldColor = canvas.Brush.Color;
        //canvas.brush.color := clGreen;
        //canvas.Ellipse(midPoint1.x-4, midPoint1.y-4,midPoint1.x+4, midPoint1.y+4);
        canvas.Brush.Color = delphi_compatability.clBlue;
        canvas.Ellipse(midPoint2.X - 4 + this.scroll.X, midPoint2.Y - 4 + this.scroll.Y, midPoint2.X + 4 + this.scroll.X, midPoint2.Y + 4 + this.scroll.Y);
        canvas.Brush.Color = oldColor;
    }
    
    public void drawArrowToRectEdge(TCanvas canvas, TPoint origin, TRect destRect) {
        TPoint startPoint = new TPoint();
        TPoint endPoint = new TPoint();
        TPoint intersectPoint = new TPoint();
        
        // add some to prevent cutting off arrow heads in certain cases for long words
        UNRESOLVED.InflateRect(destRect, arrowwidth, arrowwidth);
        intersectPoint = IntersectionPointForLineAndRectangle(origin, destRect);
        endPoint = Point(intersectPoint.X + this.scroll.X, intersectPoint.Y + this.scroll.Y);
        startPoint = Point(origin.X + this.scroll.X, origin.Y + this.scroll.Y);
        canvas.Pen.Style = delphi_compatability.TFPPenStyle.psSolid;
        canvas.MoveTo(startPoint.X, startPoint.Y);
        canvas.LineTo(endPoint.X, endPoint.Y);
        //arrow head
        this.drawArrowhead(canvas, startPoint, endPoint);
    }
    
    public void drawArrowFromRectEdgeToRectEdge(TCanvas canvas, TRect originRect, TRect destRect) {
        TPoint startPoint = new TPoint();
        TPoint endPoint = new TPoint();
        TPoint intersectPoint = new TPoint();
        TPoint origin = new TPoint();
        TPoint dest = new TPoint();
        TRect scrolledOriginRect = new TRect();
        
        //theRect: TRect;
        //IntersectRect(theRect, originRect, destRect);
        //if not IsEmptyRect(theRect) then exit;
        // add some to prevent cutting off arrow heads in certain cases for long words
        UNRESOLVED.InflateRect(destRect, arrowwidth, arrowwidth);
        UNRESOLVED.InflateRect(originRect, arrowwidth, arrowwidth);
        origin = Point((originRect.Left + originRect.Right) / 2, (originRect.Top + originRect.Bottom) / 2);
        dest = Point((destRect.Left + destRect.Right) / 2, (destRect.Top + destRect.Bottom) / 2);
        intersectPoint = IntersectionPointForLineAndRectangle(origin, destRect);
        endPoint = Point(intersectPoint.X + this.scroll.X, intersectPoint.Y + this.scroll.Y);
        startPoint = IntersectionPointForLineAndRectangle(dest, originRect);
        startPoint.X = startPoint.X + this.scroll.X;
        startPoint.Y = startPoint.Y + this.scroll.Y;
        //clipp arrow if it would end up beind drawn incorrectly
        scrolledOriginRect = originRect;
        delphi_compatability.OffsetRect(scrolledOriginRect, this.scroll.X, this.scroll.Y);
        if (delphi_compatability.PtInRect(scrolledOriginRect, endPoint)) {
            return;
        }
        canvas.Pen.Style = delphi_compatability.TFPPenStyle.psSolid;
        canvas.MoveTo(startPoint.X, startPoint.Y);
        canvas.LineTo(endPoint.X, endPoint.Y);
        //arrow head
        this.drawArrowhead(canvas, startPoint, endPoint);
    }
    
    public void drawLineFromRectEdgeToRectEdge(TCanvas canvas, TRect originRect, TRect destRect) {
        TPoint startPoint = new TPoint();
        TPoint endPoint = new TPoint();
        TPoint intersectPoint = new TPoint();
        TPoint origin = new TPoint();
        TPoint dest = new TPoint();
        
        // add some to prevent cutting off arrow heads in certain cases for long words
        UNRESOLVED.InflateRect(destRect, arrowwidth, arrowwidth);
        UNRESOLVED.InflateRect(originRect, arrowwidth, arrowwidth);
        origin = Point((originRect.Left + originRect.Right) / 2, (originRect.Top + originRect.Bottom) / 2);
        dest = Point((destRect.Left + destRect.Right) / 2, (destRect.Top + destRect.Bottom) / 2);
        intersectPoint = IntersectionPointForLineAndRectangle(origin, destRect);
        endPoint = Point(intersectPoint.X + this.scroll.X, intersectPoint.Y + this.scroll.Y);
        startPoint = IntersectionPointForLineAndRectangle(dest, originRect);
        startPoint.X = startPoint.X + this.scroll.X;
        startPoint.Y = startPoint.Y + this.scroll.Y;
        canvas.Pen.Style = delphi_compatability.TFPPenStyle.psSolid;
        canvas.MoveTo(startPoint.X, startPoint.Y);
        canvas.LineTo(endPoint.X, endPoint.Y);
    }
    
    public void drawArrowhead(TCanvas canvas, TPoint p1, TPoint p2) {
        int dx = 0;
        int dy = 0;
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        double linelen = 0.0;
        double xstep = 0.0;
        double ystep = 0.0;
        TPoint outerOne = new TPoint();
        TPoint outerTwo = new TPoint();
         Points = [0] * (range(0, 2 + 1) + 1);
        
        //Code translated from C++ posted:
        //	Subject:      Re: calculation for drawing arrow heads
        //	From:         "Jesper Hansen" <jesperh@edit.se>
        //	Date:         1998/01/27
        //	Message-ID:   <01bd2b58$1d33eaa0$65656565@foo.telia.com>
        //	Newsgroups:   comp.os.ms-windows.programmer.graphics 
        // given line from P1 to P2
        // draws arrowhead at P2
        dx = p1.X - p2.X;
        dy = p1.Y - p2.Y;
        linelen = sqrt(dx * dx + dy * dy);
        if (linelen == 0) {
            linelen = 1;
        }
        xstep = dx / linelen;
        ystep = dy / linelen;
        try {
            // modify according to preference
            // relationship comes from tan(angle)
            // relation 10:5 is 45 degrees (2*22.5)
            //
            x2 = p2.X + intround(xstep * arrowlength);
            y2 = p2.Y + intround(ystep * arrowlength);
            y1 = -intround(xstep * arrowwidth);
            x1 = intround(ystep * arrowwidth);
        } catch (Exception e) {
            return;
        }
        outerOne.X = x2 + x1;
        outerOne.Y = y2 + y1;
        outerTwo.X = x2 - x1;
        outerTwo.Y = y2 - y1;
        Points[0] = p2;
        Points[1] = outerOne;
        Points[2] = outerTwo;
        canvas.Pen.Style = delphi_compatability.TFPPenStyle.psSolid;
        canvas.Polygon(Points);
        //canvas.moveTo(p2.x, p2.y);
        //  canvas.LineTo(outerOne.x, outerOne.y);
        //  canvas.moveTo(p2.x, p2.y);
        //  canvas.LineTo(outerTwo.x, outerTwo.y); 
    }
    
    // procedure drawArrow(canvas: TCanvas; a, b: TPoint);
    public void drawArrowToRect(TCanvas canvas, TPoint origin, TRect destRect) {
        TPoint center = new TPoint();
        TPoint endPoint = new TPoint();
        TPoint startPoint = new TPoint();
        
        // add some to prevent cutting off arrow heads in certain cases for long words
        UNRESOLVED.InflateRect(destRect, arrowwidth, arrowwidth);
        center = Point((destRect.Left + destRect.Right) / 2, (destRect.Top + destRect.Bottom) / 2);
        //middlePoint := Point((origin.x + center.x) div 2 + scroll.x, (origin.y + center.y) div 2 + scroll.y); 
        //middlePoint := Point((origin.x + center.x) div 2 + scroll.x, (origin.y + center.y) div 2 + scroll.y);  
        endPoint = Point(center.X + (origin.X - center.X) / 5 + this.scroll.X, center.Y + (origin.Y - center.Y) / 5 + this.scroll.Y);
        //startPoint := Point(origin.x + (center.x - origin.x) div 5 + scroll.x, origin.y + (center.y - origin.y) div 5 + scroll.y);
        //  
        startPoint = Point(origin.X + this.scroll.X, origin.Y + this.scroll.Y);
        canvas.Pen.Style = delphi_compatability.TFPPenStyle.psSolid;
        canvas.MoveTo(startPoint.X, startPoint.Y);
        canvas.LineTo(endPoint.X, endPoint.Y);
        //arrow head
        this.drawArrowhead(canvas, startPoint, endPoint);
        //self.DrawArrowhead(canvas, Point(origin.x + scroll.x, origin.y + scroll.y), Point(center.x + scroll.x, center.y + scroll.y));
        //	canvas.moveTo(middlePoint.x + scroll.x, middlePoint.y + scroll.y);
        // 	canvas.lineTo(middlePoint.x + scroll.x + , middlePoint.y + scroll.y + );
        // 	canvas.moveTo(middlePoint.x + scroll.x, middlePoint.y + scroll.y);
        // 	canvas.lineTo(middlePoint.x + scroll.x + , middlePoint.y + scroll.y + ); 
    }
    
    public TSDraggableObject nearestNode(TPoint location, TSVariableDisplayOptions displayOptions) {
        result = new TSDraggableObject();
        TSVariable variable = new TSVariable();
        TSRule rule = new TSRule();
        int i = 0;
        boolean showNode = false;
        
        //nearestNode: TSDraggableObject;
        //distance, nearestDistance: integer;
        result = null;
        for (i = usdomain.domain.world.variables.Count - 1; i >= 0; i--) {
            //nearestDistance := -1;
            //nearestNode := nil;
            variable = usworld.TSVariable(usdomain.domain.world.variables[i]);
            showNode = variable.meetsDisplayOptions(displayOptions);
            if (!showNode) {
                continue;
            }
            if (delphi_compatability.PtInRect(variable.bounds(), location)) {
                //distance := (variable.position.x - location.x) * (variable.position.x - location.x) +
                //		(variable.position.y - location.y) * (variable.position.y - location.y);
                //if (nearestDistance = -1) or (distance < nearestDistance) then
                //  begin
                //  nearestDistance := distance;
                //  nearestNode := variable;
                //  end;
                result = variable;
                return result;
            }
        }
        if (!displayOptions[usworld.kRuleCommand]) {
            //result := nearestNode;
            return result;
        }
        for (i = usdomain.domain.world.rules.Count - 1; i >= 0; i--) {
            rule = usworld.TSRule(usdomain.domain.world.rules[i]);
            if (delphi_compatability.PtInRect(rule.bounds(), location)) {
                //distance := (rule.position.x - location.x) * (rule.position.x - location.x) +
                //	(rule.position.y - location.y) * (rule.position.y - location.y);
                //if (nearestDistance = -1) or (distance < nearestDistance) then
                //  begin
                //  nearestDistance := distance;
                //  nearestNode := rule;
                //  end;
                result = rule;
                return result;
            }
        }
        //result := nearestNode;
        return result;
    }
    
    public void displayOn(TCanvas canvas, TSVariableDisplayOptions displayOptions, TSDraggableObject lastChoice, TSDraggableObject previousChoice) {
        int i = 0;
        TSVariable variable = new TSVariable();
        TSRule rule = new TSRule();
        TSize textSize = new TSize();
        TColor oldColor = new TColor();
        
        // calculate bounds for text boxes
        UNRESOLVED.SetTextAlign(canvas.Handle, delphi_compatability.TA_LEFT || delphi_compatability.TA_TOP);
        canvas.Pen.Color = delphi_compatability.clBlack;
        for (i = 0; i <= usdomain.domain.world.rules.Count - 1; i++) {
            //need to compute these first - because they are referenced
            //could be optimized out - only do if change text...
            rule = usworld.TSRule(usdomain.domain.world.rules[i]);
            if (rule == usruleeditorform.RuleEditorForm.rule) {
                // update bounds -- optimize for case where rule is selected
                canvas.Font.Style = {UNRESOLVED.fsBold, };
                textSize = canvas.TextExtent(rule.displayName());
                canvas.Font.Style = {};
            } else {
                textSize = canvas.TextExtent(rule.displayName());
            }
            rule.extent.X = textSize.cx;
            rule.extent.Y = textSize.cy;
        }
        for (i = 0; i <= usdomain.domain.world.variables.Count - 1; i++) {
            variable = usworld.TSVariable(usdomain.domain.world.variables[i]);
            if (variable.meetsDisplayOptions(displayOptions)) {
                textSize = canvas.TextExtent(variable.displayName());
                variable.extent.X = textSize.cx;
                variable.extent.Y = textSize.cy;
            }
        }
        oldColor = canvas.Brush.Color;
        // draw lines and arrows
        canvas.Brush.Color = delphi_compatability.clBlack;
        for (i = 0; i <= usdomain.domain.world.rules.Count - 1; i++) {
            rule = usworld.TSRule(usdomain.domain.world.rules[i]);
            if (displayOptions[usworld.kRuleCommand]) {
                if (rule.context != usdomain.domain.world.emptyEntry) {
                    this.drawLineFromRectEdgeToRectEdge(canvas, rule.context.bounds(), rule.bounds());
                }
                if (rule.move != usdomain.domain.world.emptyEntry) {
                    this.drawArrowFromRectEdgeToRectEdge(canvas, rule.bounds(), rule.move.bounds());
                }
            } else {
                if ((rule.context != usdomain.domain.world.emptyEntry) && (rule.move != usdomain.domain.world.emptyEntry)) {
                    this.drawArrowFromRectEdgeToRectEdge(canvas, rule.context.bounds(), rule.move.bounds());
                }
            }
        }
        canvas.Brush.Color = oldColor;
        if (displayOptions[usworld.kRuleCommand]) {
            for (i = 0; i <= usdomain.domain.world.rules.Count - 1; i++) {
                // draw rectangles and text
                rule = usworld.TSRule(usdomain.domain.world.rules[i]);
                this.drawCommandOrContext(canvas, rule.displayName(), rule.bounds(), rule.position, rule.selected, rule == usruleeditorform.RuleEditorForm.rule, kDrawCommand);
            }
        }
        for (i = 0; i <= usdomain.domain.world.variables.Count - 1; i++) {
            variable = usworld.TSVariable(usdomain.domain.world.variables[i]);
            if (variable.meetsDisplayOptions(displayOptions)) {
                this.drawCommandOrContext(canvas, variable.displayName(), variable.bounds(), variable.position, variable.selected, false, kDrawContext);
            }
        }
    }
    
    public void drawCommandOrContext(TCanvas canvas, String text, TRect bounds, TPoint position, boolean selected, boolean focused, boolean isCommand) {
        TRect drawRect = new TRect();
        TPoint textPoint = new TPoint();
        
        drawRect.Left = bounds.Left - 2 + this.scroll.X;
        drawRect.Top = bounds.Top - 1 + this.scroll.Y;
        drawRect.Right = bounds.Right + 2 + this.scroll.X;
        drawRect.Bottom = bounds.Bottom + 1 + this.scroll.Y;
        textPoint.X = bounds.Left + this.scroll.X;
        textPoint.Y = bounds.Top + this.scroll.Y;
        usruleeditorform.setCanvasColorsForSelection(canvas, selected, focused, isCommand);
        if (selected) {
            canvas.Pen.Style = delphi_compatability.TFPPenStyle.psSolid;
        } else {
            canvas.Pen.Style = delphi_compatability.TFPPenStyle.psClear;
        }
        canvas.Rectangle(drawRect.Left, drawRect.Top, drawRect.Right, drawRect.Bottom);
        canvas.TextOut(textPoint.X, textPoint.Y, text);
    }
    
}
