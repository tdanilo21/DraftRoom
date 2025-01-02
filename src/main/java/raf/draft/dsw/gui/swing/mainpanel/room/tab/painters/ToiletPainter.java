package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.controller.RealToPixelSpaceConverter;
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
    public void paint(Graphics g, AffineTransform f, RealToPixelSpaceConverter converter) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));

        AffineTransform t = converter.transformToPixelSpace(toilet.getTransform());
        t.preConcatenate(f);

        Point2D p = new Point2D.Double(0, 0);
        Point2D p1 = new Point2D.Double(0, 0.5/2);
        Point2D p2 = new Point2D.Double(1, 0.5/2);
        Point2D p3 = new Point2D.Double(0, 0.5);
        Point2D p4 = new Point2D.Double(1, 0.5);
        Point2D p5 = new Point2D.Double(0.5, 0.5);
        Point2D p6 = new Point2D.Double(0.5/2, 0.5);
        Point2D p7 = new Point2D.Double(3*0.5/2, 0.5);

        drawRectangle(new Segment(p, p2), g2, t);
        drawLine(new Segment(p1, p3), g2, t);
        drawLine(new Segment(p2, p4), g2, t);
        drawLine(new Segment(p6, p7), g2, t);
        drawCircularArc(new CircularArc(p5, 0.5, Math.PI, Math.PI), g2, t);
        drawCircularArc(new CircularArc(p5, 0.5/2, Math.PI, Math.PI), g2, t);

        /*Point2D p = toilet.getLocationInPixelSpace();
        double r = toilet.getRInPixelSpace();
        Point2D center = toilet.getCenterInPixelSpace();
        double angle = toilet.getAngleInPixelSpace();
        f.concatenate(AffineTransform.getRotateInstance(-angle, center.getX(), center.getY()));
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
        drawCircularArc(new CircularArc(p5, r, Math.PI + 2*angle, Math.PI), g2, f);
        drawCircularArc(new CircularArc(p5, r/2, Math.PI + 2*angle, Math.PI), g2, f);
         */
    }
}
