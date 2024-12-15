package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Segment;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class AbstractPainter{

    public abstract VisualElement getElement();

    protected void drawLine(Segment s, Graphics2D g2, AffineTransform f){
        s.transform(f);
        int x1 = (int)Math.round(s.getA().getX());
        int y1 = (int)Math.round(s.getA().getY());
        int x2 = (int)Math.round(s.getB().getX());
        int y2 = (int)Math.round(s.getB().getY());
        g2.drawLine(x1, y1, x2, y2);
    }

    protected void drawRectangle(Segment diagonal, Graphics2D g2, AffineTransform f){
        diagonal.transform(f);
        int x1 = (int)Math.round(diagonal.getA().getX());
        int y1 = (int)Math.round(diagonal.getA().getY());
        int x2 = (int)Math.round(diagonal.getB().getX());
        int y2 = (int)Math.round(diagonal.getB().getY());
        g2.drawRect(x1, y1, x2-x1, y2-y1);
    }

    protected void drawCircularArc(CircularArc arc, Graphics2D g2, AffineTransform f){
        arc.transform(f);
        int x = (int)Math.round(arc.getC().getX() - arc.getR());
        int y = (int)Math.round(arc.getC().getY() - arc.getR());
        int a = (int)Math.round(2*arc.getR());
        int startAngle = (int)Math.round(Math.toDegrees(arc.getStartAngle()));
        int arcAngle = (int)Math.round(Math.toDegrees(arc.getSweepAngle()));
        g2.drawArc(x, y, a, a, startAngle, arcAngle);
    }

    public abstract void paint(Graphics g, AffineTransform f);
}
