package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.controller.PixelSpaceConverter;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class BathTubPainter extends AbstractPainter {
    private final RectangularVisualElement bathTub;
    public BathTubPainter(RectangularVisualElement bathTub){this.bathTub = bathTub;}

    @Override
    public VisualElement getElement() {
        return bathTub;
    }

    @Override
    public void paint(Graphics g, AffineTransform f, PixelSpaceConverter converter) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));

        Point2D o = converter.pointToPixelSpace(new Point2D.Double(0, 0));
        AffineTransform t = AffineTransform.getTranslateInstance(o.getX(), o.getY());
        t.preConcatenate(converter.transformToPixelSpace(bathTub.getTransform()));
        t.preConcatenate(f);

        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(1, 1);
        drawRectangle(new Segment(a, b), g2, t);

        Point2D p1 = new Point2D.Double( 1.0/2, 1.0/4);
        Point2D p2 = new Point2D.Double(1.0/2, 3.0/4);
        drawCircularArc(new CircularArc(p1, 1.0/4, 0, Math.PI), g2, t);
        drawCircularArc(new CircularArc(p2, 1.0/4, Math.PI, Math.PI), g2, t);

        Point2D p3 = new Point2D.Double(1.0/4, 1.0/4);
        Point2D p4 = new Point2D.Double(3.0/4, 1.0/4);
        Point2D p5 = new Point2D.Double(3.0/4, 3.0/4);
        Point2D p6 = new Point2D.Double(1.0/4, 3.0/4);
        drawLine(new Segment(p3, p6), g2, t);
        drawLine(new Segment(p4, p5), g2, t);

        /*Point2D p = bathTub.getLocationInPixelSpace();
        double w = bathTub.getWInPixelSpace(), h = bathTub.getHInPixelSpace();
        double angle = bathTub.getAngleInPixelSpace();
        f.concatenate(AffineTransform.getTranslateInstance(p.getX(), p.getY()));
        f.concatenate(AffineTransform.getRotateInstance(-angle, w/2, h/2));
        g2.setStroke(new BasicStroke(2));
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(w, h);
        drawRectangle(new Segment(a, b), g2, f);

        Point2D p1 = new Point2D.Double( w/2, h/4);
        Point2D p2 = new Point2D.Double(w/2, 3*h/4);

        Point2D p3 = new Point2D.Double(w/4, h/4);
        Point2D p4 = new Point2D.Double(3*w/4, h/4);
        Point2D p5 = new Point2D.Double(3*w/4, 3*h/4);
        Point2D p6 = new Point2D.Double(w/4, 3*h/4);
        drawCircularArc(new CircularArc(p1, w/4, 0 + 2*angle, Math.PI), g2, f);
        drawCircularArc(new CircularArc(p2, w/4, Math.PI + 2*angle, Math.PI), g2, f);
        drawLine(new Segment(p3, p6), g2, f);
        drawLine(new Segment(p4, p5), g2, f);
         */
    }
}
