package com.kurtz_fernhout.storyharp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class TSMapView {
    // const
    public final static boolean kDrawCommand = true;
    public final static boolean kDrawContext = false;
    public final static int arrowlength = 10;
    public final static int arrowwidth = 4;
    
    public Point scroll = new Point();
    
    //needs to have rectangle with center of 0,0 and origin adjusted to that coordinate system
    public Point AdjustedIntersectionPointForLineAndRectangle(Point origin, Rectangle Rect) {
        if (Rect.contains(origin)) {
            return new Point(0, 0);
        }
        if ((Rect.x == 0) || (Rect.y == 0) || (Rect.width == 0) || (Rect.height == 0)) {
        	return new Point(0, 0);
        }
        if (origin.x == 0) {
            if (origin.y < 0) {
                //do zero cases to avoid divide by zero later
                return new Point(0, Rect.y);
            } else if (origin.y == 0) {
                // pathological case
                // origin.y > 0
                return new Point(0, 0);
            } else {
                return new Point(0, Rect.y + Rect.height);
            }
        } else if (origin.y == 0) {
            if (origin.x < 0) {
                // origin.x > 0
                return new Point(Rect.x, 0);
            } else {
                return new Point(Rect.x + Rect.width, 0);
            }
        } else {
            double slope = (origin.y * 1.0) / origin.x;
            if ((origin.x > 0) && (origin.y < 0)) {
                if (slope < Rect.y * 1.0 / (Rect.x + Rect.width)) {
                    return new Point((int) Math.round(Rect.y / slope), Rect.y);
                } else {
                    return new Point(Rect.x + Rect.width, (int) Math.round((Rect.x + Rect.width) * slope));
                }
            } else if ((origin.x > 0) && (origin.y > 0)) {
                if (slope > (Rect.y + Rect.height) * 1.0 / (Rect.x + Rect.width)) {
                    return new Point((int) Math.round((Rect.y + Rect.height) / slope), Rect.y + Rect.height);
                } else {
                    return new Point(Rect.x + Rect.width, (int) Math.round((Rect.x + Rect.width) * slope));
                }
            } else if ((origin.x < 0) && (origin.y < 0)) {
                if (slope > Rect.y * 1.0 / Rect.x) {
                    return new Point((int) Math.round(Rect.y / slope), Rect.y);
                } else {
                    return new Point(Rect.x, (int) Math.round(Rect.x * slope));
                }
            } else if ((origin.x < 0) && (origin.y > 0)) {
                if (slope < (Rect.y + Rect.height) * 1.0 / Rect.x) {
                    return new Point((int) Math.round((Rect.y + Rect.height) / slope), Rect.y + Rect.height);
                } else {
                    return new Point(Rect.x, (int) Math.round(Rect.x * slope));
                }
            } else {
            	// PDF PORTING: Added this case (bug? or really that every other case is covered?)
            	return new Point(0, 0);
            }
        }
    }

    public Point IntersectionPointForLineAndRectangle(Point origin, Rectangle destRect) {
        Point center = new Point((2 * destRect.x + destRect.width) / 2, (2 * destRect.y + destRect.height) / 2);
        //make center of rectangle = 0,0
        Rectangle adjustedRect = new Rectangle(destRect.x - center.x, destRect.y - center.y, destRect.x + destRect.width - center.x, destRect.y + destRect.height - center.y);
        Point adjustedOrigin = new Point(origin.x - center.x, origin.y - center.y);
        Point result = AdjustedIntersectionPointForLineAndRectangle(adjustedOrigin, adjustedRect);
        result.x = result.x + center.x;
        result.y = result.y + center.y;
        return result;
    }
    
    // TSMapView ---------------------------------------- 
    public void drawBogusArrow(Graphics2D canvas, Point a, Point b) {
        //midPoint1: Point;
        //midPoint1 := new Point(a.x + (b.x - a.x) div 3, a.y + (b.y - a.y) div 3);
        Point midPoint2 = new Point(a.x + (b.x - a.x) / 3, a.y + (b.y - a.y) / 3);
        //midPoint2 := new Point(b.x - (b.x - a.x) mod 2, b.y - (b.y - a.y) div 5);
        canvas.drawLine(a.x + this.scroll.x, a.y + this.scroll.y, b.x + this.scroll.x, b.y + this.scroll.y);
        Color oldColor = canvas.getColor();
        //canvas.brush.color := clGreen;
        //canvas.Ellipse(midPoint1.x-4, midPoint1.y-4,midPoint1.x+4, midPoint1.y+4);
        canvas.setColor(Color.blue);
        canvas.drawOval(midPoint2.x - 4 + this.scroll.x, midPoint2.y - 4 + this.scroll.y, 4 + this.scroll.x, 4 + this.scroll.y);
        canvas.setColor(oldColor);
    }
    
    public void drawArrowToRectEdge(Graphics2D canvas, Point origin, Rectangle destRect) {
        // add some to prevent cutting off arrow heads in certain cases for long words
    	destRect = InflateRect(destRect, arrowwidth, arrowwidth);
        Point intersectPoint = IntersectionPointForLineAndRectangle(origin, destRect);
        Point endPoint = new Point(intersectPoint.x + this.scroll.x, intersectPoint.y + this.scroll.y);
        Point startPoint = new Point(origin.x + this.scroll.x, origin.y + this.scroll.y);
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        //arrow head
        this.drawArrowhead(canvas, startPoint, endPoint);
    }
    
    public Rectangle InflateRect(Rectangle rect, int extraWidth, int extraHeight) {
    	return new Rectangle(rect.x - extraWidth, rect.y - extraHeight, rect.width + extraWidth, rect.height + extraHeight);
    }
    
    public Rectangle OffsetRect(Rectangle rect, int x, int y) {
    	return new Rectangle(rect.x + x, rect.y + y, rect.width, rect.height);
    }
    
    public void drawArrowFromRectEdgeToRectEdge(Graphics2D canvas, Rectangle originRect, Rectangle destRect) {
        Point startPoint = new Point();
        Point endPoint = new Point();
        Rectangle scrolledOriginRect = new Rectangle();
        
        //theRect: Rectangle;
        //IntersectRect(theRect, originRect, destRect);
        //if not IsEmptyRect(theRect) then exit;
        // add some to prevent cutting off arrow heads in certain cases for long words
        destRect = InflateRect(destRect, arrowwidth, arrowwidth);
        originRect = InflateRect(originRect, arrowwidth, arrowwidth);
        Point origin = new Point((2 * originRect.x + originRect.width) / 2, (2 * originRect.y + originRect.height) / 2);
        Point dest = new Point((2 * destRect.x + destRect.width) / 2, (2 * destRect.y + destRect.height) / 2);
        Point intersectPoint = IntersectionPointForLineAndRectangle(origin, destRect);
        endPoint = new Point(intersectPoint.x + this.scroll.x, intersectPoint.y + this.scroll.y);
        startPoint = IntersectionPointForLineAndRectangle(dest, originRect);
        startPoint.x = startPoint.x + this.scroll.x;
        startPoint.y = startPoint.y + this.scroll.y;
        //clip arrow if it would end up being drawn incorrectly
        scrolledOriginRect = OffsetRect(originRect, this.scroll.x, this.scroll.y);
        if (scrolledOriginRect.contains(endPoint)) {
            return;
        }
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        //arrow head
        this.drawArrowhead(canvas, startPoint, endPoint);
    }
    
    public void drawLineFromRectEdgeToRectEdge(Graphics2D canvas, Rectangle originRect, Rectangle destRect) {
        Point startPoint = new Point();
        Point intersectPoint = new Point();
        
        // add some to prevent cutting off arrow heads in certain cases for long words
        destRect = InflateRect(destRect, arrowwidth, arrowwidth);
        originRect = InflateRect(originRect, arrowwidth, arrowwidth);
        Point origin = new Point((2 * originRect.x + originRect.width) / 2, (2 * originRect.y + originRect.height) / 2);
        Point dest = new Point((2 * destRect.x + destRect.width) / 2, (2 * destRect.y + destRect.height) / 2);
        intersectPoint = IntersectionPointForLineAndRectangle(origin, destRect);
        Point endPoint = new Point(intersectPoint.x + this.scroll.x, intersectPoint.y + this.scroll.y);
        startPoint = IntersectionPointForLineAndRectangle(dest, originRect);
        startPoint.x = startPoint.x + this.scroll.x;
        startPoint.y = startPoint.y + this.scroll.y;
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }
    
    public void drawArrowhead(Graphics2D canvas, Point p1, Point p2) {
        int dx = 0;
        int dy = 0;
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        double linelen = 0.0;
        double xstep = 0.0;
        double ystep = 0.0;
        Point outerOne = new Point();
        Point outerTwo = new Point();
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        
        //Code translated from C++ posted:
        //	Subject:      Re: calculation for drawing arrow heads
        //	From:         "Jesper Hansen" <jesperh@edit.se>
        //	Date:         1998/01/27
        //	Message-ID:   <01bd2b58$1d33eaa0$65656565@foo.telia.com>
        //	Newsgroups:   comp.os.ms-windows.programmer.graphics 
        // given line from P1 to P2
        // draws arrowhead at P2
        dx = p1.x - p2.x;
        dy = p1.y - p2.y;
        linelen = Math.sqrt(dx * dx + dy * dy);
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
            x2 = (int) (p2.x + Math.round(xstep * arrowlength));
            y2 = (int) (p2.y + Math.round(ystep * arrowlength));
            y1 = (int) -Math.round(xstep * arrowwidth);
            x1 = (int) Math.round(ystep * arrowwidth);
        } catch (Exception e) {
            return;
        }
        outerOne.x = x2 + x1;
        outerOne.y = y2 + y1;
        outerTwo.x = x2 - x1;
        outerTwo.y = y2 - y1;
        xPoints[0] = p2.x;
        yPoints[0] = p2.y;
        xPoints[1] = outerOne.x;
        yPoints[1] = outerOne.y;
        xPoints[2] = outerTwo.x;
        yPoints[2] = outerTwo.y;
        canvas.drawPolygon(xPoints, yPoints, 3);
        //canvas.moveTo(p2.x, p2.y);
        //  canvas.LineTo(outerOne.x, outerOne.y);
        //  canvas.moveTo(p2.x, p2.y);
        //  canvas.LineTo(outerTwo.x, outerTwo.y); 
    }
    
    // procedure drawArrow(canvas: Graphics2D; a, b: Point);
    public void drawArrowToRect(Graphics2D canvas, Point origin, Rectangle destRect) {
        // add some to prevent cutting off arrow heads in certain cases for long words
        destRect = InflateRect(destRect, arrowwidth, arrowwidth);
        Point center = new Point((2 * destRect.x + destRect.width) / 2, (2 * destRect.y + destRect.height) / 2);
        //middlePoint := new Point((origin.x + center.x) div 2 + scroll.x, (origin.y + center.y) div 2 + scroll.y); 
        //middlePoint := new Point((origin.x + center.x) div 2 + scroll.x, (origin.y + center.y) div 2 + scroll.y);  
        Point endPoint = new Point(center.x + (origin.x - center.x) / 5 + this.scroll.x, center.y + (origin.y - center.y) / 5 + this.scroll.y);
        //startPoint := new Point(origin.x + (center.x - origin.x) div 5 + scroll.x, origin.y + (center.y - origin.y) div 5 + scroll.y);
        //  
        Point startPoint = new Point(origin.x + this.scroll.x, origin.y + this.scroll.y);
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        //arrow head
        this.drawArrowhead(canvas, startPoint, endPoint);
        //self.DrawArrowhead(canvas, Point(origin.x + scroll.x, origin.y + scroll.y), Point(center.x + scroll.x, center.y + scroll.y));
        //	canvas.moveTo(middlePoint.x + scroll.x, middlePoint.y + scroll.y);
        // 	canvas.lineTo(middlePoint.x + scroll.x + , middlePoint.y + scroll.y + );
        // 	canvas.moveTo(middlePoint.x + scroll.x, middlePoint.y + scroll.y);
        // 	canvas.lineTo(middlePoint.x + scroll.x + , middlePoint.y + scroll.y + ); 
    }
    
    public TSDraggableObject nearestNode(Point location, TSVariableDisplayOptions displayOptions) {
        //nearestNode: TSDraggableObject;
        //distance, nearestDistance: integer;
        for (int i = TSDomain.domain.world.variables.size() - 1; i >= 0; i--) {
            //nearestDistance := -1;
            //nearestNode := nil;
        	TSVariable variable = TSDomain.domain.world.variables.get(i);
            boolean showNode = variable.meetsDisplayOptions(displayOptions);
            if (!showNode) {
                continue;
            }
            if (variable.bounds().contains(location)) {
                //distance := (variable.position.x - location.x) * (variable.position.x - location.x) +
                //		(variable.position.y - location.y) * (variable.position.y - location.y);
                //if (nearestDistance = -1) or (distance < nearestDistance) then
                //  begin
                //  nearestDistance := distance;
                //  nearestNode := variable;
                //  end;
                return variable;
            }
        }
        if (!displayOptions.displayOptions[TSVariableDisplayOptions.kRuleCommand]) {
            //result := nearestNode;
            return null;
        }
        for (int i = TSDomain.domain.world.rules.size() - 1; i >= 0; i--) {
        	TSRule rule = TSDomain.domain.world.rules.get(i);
            if (rule.bounds().contains(location)) {
                //distance := (rule.position.x - location.x) * (rule.position.x - location.x) +
                //	(rule.position.y - location.y) * (rule.position.y - location.y);
                //if (nearestDistance = -1) or (distance < nearestDistance) then
                //  begin
                //  nearestDistance := distance;
                //  nearestNode := rule;
                //  end;
                return rule;
            }
        }
        //result := nearestNode;
        return null;
    }
    
    public static void setCanvasFontColorAndStyleForSelection(Graphics2D canvas, boolean selected, boolean focused, boolean isCommandInMap) {
        //PDF PORT FIX: canvas.setColor(UNRESOLVED.clWindowText);
    	canvas.setColor(Color.black);
    	Font oldFont = canvas.getFont();
        Font newFont = oldFont.deriveFont(Font.PLAIN);
        canvas.setFont(newFont);
        if (focused && selected) {
        	canvas.setColor(TSDomain.domain.options.selectedTextColor);
            newFont = oldFont.deriveFont(Font.BOLD);
            canvas.setFont(newFont);
        } else if (focused) {
            newFont = oldFont.deriveFont(Font.BOLD);
            canvas.setFont(newFont);
        } else if (selected) {
        	canvas.setColor(TSDomain.domain.options.selectedTextColor);
        }
        if (isCommandInMap) {
        	canvas.setColor(TSDomain.domain.options.commandTextColorInMap);
        }
    }

    public static void setCanvasLineColorForSelection(Graphics2D canvas, boolean selected, boolean focused, boolean isCommandInMap) {
        //PDF PORT FIX: canvas.setColor(delphi_compatability.clWindow);
    	canvas.setColor(Color.lightGray);
        if (focused && selected) {
        	canvas.setColor(TSDomain.domain.options.selectedItemColor);
        } else if (focused) {
            //
        } else if (selected) {
        	canvas.setColor(TSDomain.domain.options.selectedItemColor);
        }
    }
    
    public void displayOn(Graphics2D canvas, TSVariableDisplayOptions displayOptions, TSDraggableObject lastChoice, TSDraggableObject previousChoice, TSRule currentRule) {
    	Rectangle2D textSize = null;
         
        // calculate bounds for text boxes
        canvas.setColor(Color.black);
        for (int i = 0; i < TSDomain.domain.world.rules.size(); i++) {
            //need to compute these first - because they are referenced
            //could be optimized out - only do if change text...
        	TSRule rule = TSDomain.domain.world.rules.get(i);
            if (rule == currentRule) {
                // update bounds -- optimize for case where rule is selected
            	Font oldFont = canvas.getFont();
                Font newFont = oldFont.deriveFont(Font.BOLD);
                canvas.setFont(newFont);
                textSize = canvas.getFontMetrics().getStringBounds(rule.displayName(), canvas);
                canvas.setFont(oldFont);
            } else {
            	textSize = canvas.getFontMetrics().getStringBounds(rule.displayName(), canvas);
            }
            rule.extent.x = (int) textSize.getWidth();
            rule.extent.y = (int) textSize.getHeight();
        }
        for (int i = 0; i < TSDomain.domain.world.variables.size(); i++) {
        	TSVariable variable = TSDomain.domain.world.variables.get(i);
            if (variable.meetsDisplayOptions(displayOptions)) {
            	textSize = canvas.getFontMetrics().getStringBounds(variable.displayName(), canvas);
                variable.extent.x = (int) textSize.getWidth();
                variable.extent.y = (int) textSize.getHeight();
            }
        }
        Color oldColor = canvas.getColor();
        // draw lines and arrows
        canvas.setColor(Color.black);
        for (int i = 0; i < TSDomain.domain.world.rules.size(); i++) {
        	TSRule rule = TSDomain.domain.world.rules.get(i);
            if (displayOptions.displayOptions[TSVariableDisplayOptions.kRuleCommand]) {
                if (rule.context != TSDomain.domain.world.emptyEntry) {
                    this.drawLineFromRectEdgeToRectEdge(canvas, rule.context.bounds(), rule.bounds());
                }
                if (rule.move != TSDomain.domain.world.emptyEntry) {
                    this.drawArrowFromRectEdgeToRectEdge(canvas, rule.bounds(), rule.move.bounds());
                }
            } else {
                if ((rule.context != TSDomain.domain.world.emptyEntry) && (rule.move != TSDomain.domain.world.emptyEntry)) {
                    this.drawArrowFromRectEdgeToRectEdge(canvas, rule.context.bounds(), rule.move.bounds());
                }
            }
        }
        canvas.setColor(oldColor);
        if (displayOptions.displayOptions[TSVariableDisplayOptions.kRuleCommand]) {
            for (int i = 0; i < TSDomain.domain.world.rules.size(); i++) {
                // draw rectangles and text
            	TSRule rule = TSDomain.domain.world.rules.get(i);
            	boolean isCurrentRule = rule == currentRule;
                this.drawCommandOrContext(canvas, rule.displayName(), rule.bounds(), rule.position, rule.selected, isCurrentRule, kDrawCommand);
            }
        }
        for (int i = 0; i < TSDomain.domain.world.variables.size(); i++) {
        	TSVariable variable = TSDomain.domain.world.variables.get(i);
            if (variable.meetsDisplayOptions(displayOptions)) {
                this.drawCommandOrContext(canvas, variable.displayName(), variable.bounds(), variable.position, variable.selected, false, kDrawContext);
            }
        }
    }
    
    public void drawCommandOrContext(Graphics2D canvas, String text, Rectangle bounds, Point position, boolean selected, boolean focused, boolean isCommand) {
        Rectangle drawRect = new Rectangle();
        drawRect.x = bounds.x - 2 + this.scroll.x;
        drawRect.y = bounds.y - 1 + this.scroll.y;
        drawRect.width = bounds.width + 2;
        drawRect.height = bounds.height + 1;
        
        Point textPoint = new Point(bounds.x + this.scroll.x, bounds.y + this.scroll.y);
        
        setCanvasLineColorForSelection(canvas, selected, focused, isCommand);
        if (selected) {
        	canvas.fillRect(drawRect.x, drawRect.y, drawRect.width, drawRect.height);
        } else {
        	canvas.fillRect(drawRect.x, drawRect.y, drawRect.width, drawRect.height);
        }
        int baseline = canvas.getFontMetrics().getAscent();
        setCanvasFontColorAndStyleForSelection(canvas, selected, focused, isCommand);
        canvas.drawString(text, textPoint.x, textPoint.y + baseline);
    }
    
}
