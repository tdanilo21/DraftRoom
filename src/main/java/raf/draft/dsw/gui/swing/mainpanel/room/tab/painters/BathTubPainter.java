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

        AffineTransform t = converter.getUnitPixelSpaceTransform();
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
    }
}
