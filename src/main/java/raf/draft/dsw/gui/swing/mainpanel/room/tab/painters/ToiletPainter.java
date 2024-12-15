package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class ToiletPainter extends AbstractPainter{
    private final CircularVisualElement toilet;

    public ToiletPainter(CircularVisualElement toilet){this.toilet = toilet;};

    @Override
    public VisualElement getElement() {
        return toilet;
    }

    @Override
    public void paint(Graphics g, AffineTransform f) {
        Graphics2D g2 = (Graphics2D)g;
        Point2D p = toilet.getLocationInPixelSpace();
        double r = toilet.getRInPixelSpace();
        g2.setStroke(new BasicStroke(2));

        Point2D p1 = new Point2D.Double(p.getX(), p.getY() + r/2);
        Point2D p2 = new Point2D.Double(p.getX() + 2*r, p.getY() + r/2);
        Point2D p3 = new Point2D.Double(p.getX(), p.getY() + r);
        Point2D p4 = new Point2D.Double(p.getX() + 2*r, p.getY() + r);
        Point2D p5 = new Point2D.Double(p.getX() + r, p.getY() + r);
        Point2D p6 = new Point2D.Double(p.getX() + r/2, p.getY() + r);
        Point2D p7 = new Point2D.Double(p.getX() + 3*r/2, p.getY() + r);

        drawRectangle(new Segment(p, p2), g2, f);
        drawLine(new Segment(p1, p3), g2, f);
        drawLine(new Segment(p2, p4), g2, f);
        drawLine(new Segment(p6, p7), g2, f);
        drawCircularArc(new CircularArc(p5, r, Math.PI, Math.PI), g2, f);
        drawCircularArc(new CircularArc(p5, r/2, Math.PI, Math.PI), g2, f);
    }
}
