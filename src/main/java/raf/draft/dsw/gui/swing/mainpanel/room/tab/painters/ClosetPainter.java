package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.controller.PixelSpaceConverter;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class ClosetPainter extends AbstractPainter{
    private final RectangularVisualElement closet;

    public ClosetPainter(RectangularVisualElement closet){this.closet = closet;}

    @Override
    public VisualElement getElement() {
        return closet;
    }

    @Override
    public void paint(Graphics g, AffineTransform f, PixelSpaceConverter converter) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));

        AffineTransform t = converter.getUnitPixelSpaceTransform();
        t.preConcatenate(converter.transformToPixelSpace(closet.getTransform()));
        t.preConcatenate(f);

        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(1, 1);
        drawRectangle(new Segment(a, b), g2, t);

        Point2D p1 = new Point2D.Double(1.0/2, 0);
        Point2D p2 = new Point2D.Double(1.0/2, 1.0);
        drawLine(new Segment(p1, p2), g2, t);

        Point2D p3 = new Point2D.Double(1.0/3, 1.0/2);
        drawCircularArc(new CircularArc(p3, 1.0/24, 0, 2*Math.PI), g2, t);

        Point2D p4 = new Point2D.Double(2.0/3, 1.0/2);
        drawCircularArc(new CircularArc(p4, 1.0/24, 0, 2*Math.PI), g2, t);

        /*Point2D p = closet.getLocationInPixelSpace();
        double w = closet.getWInPixelSpace(), h = closet.getHInPixelSpace();
        f.concatenate(AffineTransform.getTranslateInstance(p.getX(), p.getY()));
        f.concatenate(AffineTransform.getRotateInstance(closet.getAngleInPixelSpace(), w/2, h/2));
        g2.setStroke(new BasicStroke(2));
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(w, h);
        drawRectangle(new Segment(a, b), g2, f);

        Point2D p1 = new Point2D.Double(w/2, 0);
        Point2D p2 = new Point2D.Double(w/2, h);
        drawLine(new Segment(p1, p2), g2, f);

        Point2D p3 = new Point2D.Double(w/3, h/2);
        drawCircularArc(new CircularArc(p3, w/24, 0, 2*Math.PI), g2, f);

        Point2D p4 = new Point2D.Double(2*w/3, h/2);
        drawCircularArc(new CircularArc(p4, w/24, 0, 2*Math.PI), g2, f);
         */
    }
}
