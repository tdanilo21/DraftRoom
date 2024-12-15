package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class AbstractPainter{

    public abstract VisualElement getElement();

    protected void drawLine(Point2D a, Point2D b, Graphics2D g2, AffineTransform f){
        Point2D at = f.transform(a, null);
        Point2D bt = f.transform(b, null);
        int x1 = (int)Math.round(at.getX());
        int y1 = (int)Math.round(at.getY());
        int x2 = (int)Math.round(bt.getX());
        int y2 = (int)Math.round(bt.getY());
        g2.drawLine(x1, y1, x2, y2);
    }
    protected void drawRectangle(Point2D a, Point2D b, Graphics2D g2, AffineTransform f){
        Point2D at = f.transform(a, null);
        Point2D bt = f.transform(b, null);
        int x1 = (int)Math.round(at.getX());
        int y1 = (int)Math.round(at.getY());
        int x2 = (int)Math.round(bt.getX());
        int y2 = (int)Math.round(bt.getY());
        g2.drawRect(x1, y1, x2-x1, y2-y1);
    }
    protected void drawArc(){

    }

    public abstract void paint(Graphics g, AffineTransform f);
}
