package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.controller.RealToPixelSpaceConverter;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class TablePainter extends AbstractPainter{
    private final RectangularVisualElement table;

    public TablePainter(RectangularVisualElement table){this.table = table;}

    @Override
    public VisualElement getElement() {
        return table;
    }

    @Override
    public void paint(Graphics g, AffineTransform f, RealToPixelSpaceConverter converter) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));

        AffineTransform t = converter.transformToPixelSpace(table.getTransform());
        t.preConcatenate(f);

        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(1, 1);
        drawRectangle(new Segment(a, b), g2, t);

        Point2D p1 = new Point2D.Double(1.0/8, 1.0/12);
        drawCircularArc(new CircularArc(p1, 1.0/24, 0, 2*Math.PI), g2, t);

        Point2D p2 = new Point2D.Double(7.0/8, 1.0/12);
        drawCircularArc(new CircularArc(p2, 1.0/24, 0, 2*Math.PI), g2, t);

        Point2D p3 = new Point2D.Double(7.0/8, 11.0/12);
        drawCircularArc(new CircularArc(p3, 1.0/24, 0, 2*Math.PI), g2, t);

        Point2D p4 = new Point2D.Double(1.0/8, 11.0/12);
        drawCircularArc(new CircularArc(p4, 1.0/24, 0, 2*Math.PI), g2, t);

        /*Point2D p = table.getLocationInPixelSpace();
        double w = table.getWInPixelSpace(), h = table.getHInPixelSpace();
        f.concatenate(AffineTransform.getTranslateInstance(p.getX(), p.getY()));
        f.concatenate(AffineTransform.getRotateInstance(table.getAngleInPixelSpace(), w/2, h/2));
        g2.setStroke(new BasicStroke(2));
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(w, h);
        drawRectangle(new Segment(a, b), g2, f);

        Point2D p1 = new Point2D.Double(w/8, h/12);
        drawCircularArc(new CircularArc(p1, w/24, 0, 2*Math.PI), g2, f);

        Point2D p2 = new Point2D.Double(7*w/8, h/12);
        drawCircularArc(new CircularArc(p2, w/24, 0, 2*Math.PI), g2, f);

        Point2D p3 = new Point2D.Double(7*w/8, 11*h/12);
        drawCircularArc(new CircularArc(p3, w/24, 0, 2*Math.PI), g2, f);

        Point2D p4 = new Point2D.Double(w/8, 11*h/12);
        drawCircularArc(new CircularArc(p4, w/24, 0, 2*Math.PI), g2, f);
         */
    }
}
