package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.painters.AbstractPainter;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.elements.BathTub;
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
    public void paint(Graphics g, AffineTransform f) {
        Graphics2D g2 = (Graphics2D)g;
        Point2D p = bathTub.getLocationInPixelSpace();
        f.concatenate(AffineTransform.getTranslateInstance(p.getX(), p.getY()));
        f.concatenate(AffineTransform.getRotateInstance(bathTub.getAngleInPixelSpace(), p.getX(), p.getY()));
        double w = bathTub.getWInPixelSpace(), h = bathTub.getHInPixelSpace();
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
        drawCircularArc(new CircularArc(p1, w/4, 0, Math.PI), g2, f);
        drawCircularArc(new CircularArc(p2, w/4, Math.PI, Math.PI), g2, f);
        drawLine(new Segment(p3, p6), g2, f);
        drawLine(new Segment(p4, p5), g2, f);
    }
}
