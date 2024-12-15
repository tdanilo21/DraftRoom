package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Segment;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class AbstractPainter{

    public abstract VisualElement getElement();

    protected void drawLine(Segment s, Graphics2D g2, AffineTransform f){
        Segment st = (Segment)s.getTransformedInstance(f);
        int x1 = (int)Math.round(st.getA().getX());
        int y1 = (int)Math.round(st.getA().getY());
        int x2 = (int)Math.round(st.getB().getX());
        int y2 = (int)Math.round(st.getB().getY());
        g2.drawLine(x1, y1, x2, y2);
    }

    protected void drawRectangle(Segment s, Graphics2D g2, AffineTransform f){
        Segment st = (Segment)s.getTransformedInstance(f);
        int x1 = (int)Math.round(st.getA().getX());
        int y1 = (int)Math.round(st.getA().getY());
        int x2 = (int)Math.round(st.getB().getX());
        int y2 = (int)Math.round(st.getB().getY());
        g2.drawRect(x1, y1, x2-x1, y2-y1);
    }

    protected void drawCircularArc(CircularArc arc, Graphics2D g2, AffineTransform f){
        CircularArc arct = (CircularArc)arc.getTransformedInstance(f);
        int x = (int)Math.round(arct.getC().getX() - arct.getR());
        int y = (int)Math.round(arct.getC().getY() - arct.getR());
        int a = (int)Math.round(2*arct.getR());
        int startAngle = (int)Math.round(Math.toDegrees(arct.getStartAngle()));
        int arcAngle = (int)Math.round(Math.toDegrees(arct.getSweepAngle()));
        g2.drawArc(x, y, a, a, startAngle, arcAngle);
    }
    protected void drawElipse(Segment s, Graphics2D g2, AffineTransform f){
        Segment st = (Segment)s.getTransformedInstance(f);
        int x1 = (int)Math.round(st.getA().getX());
        int y1 = (int)Math.round(st.getA().getY());
        int x2 = (int)Math.round(st.getB().getX());
        int y2 = (int)Math.round(st.getB().getY());
        g2.drawArc(x1, y1, x2-x1, y2-y1, 0, 360);
    }

    public abstract void paint(Graphics g, AffineTransform f);
}
