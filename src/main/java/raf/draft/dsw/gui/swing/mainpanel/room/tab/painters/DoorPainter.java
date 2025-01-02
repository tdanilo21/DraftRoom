package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.controller.RealToPixelSpaceConverter;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class DoorPainter extends AbstractPainter{
    private final CircularVisualElement door;

    public DoorPainter(CircularVisualElement door){this.door = door;}
    @Override
    public VisualElement getElement() {
        return door;
    }

    @Override
    public void paint(Graphics g, AffineTransform f, RealToPixelSpaceConverter converter) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));

        AffineTransform t = converter.transformToPixelSpace(door.getTransform());
        t.preConcatenate(f);

        Point2D a = new Point2D.Double(1, 0);
        Point2D b = new Point2D.Double(1, 1);
        drawLine(new Segment(a, b), g2, t);
        drawCircularArc(new CircularArc(b, 1, Math.PI/2, Math.PI/2), g2, t);

        /*Point2D p = door.getLocationInPixelSpace();
        double r = door.getRInPixelSpace();
        Point2D center = door.getCenterInPixelSpace();
        double angle = door.getAngleInPixelSpace();
        f.concatenate(AffineTransform.getRotateInstance(-angle, center.getX(), center.getY()));
        g2.setStroke(new BasicStroke(2));

        Point2D c = new Point2D.Double(p.getX() + r, p.getY() + r);
        drawCircularArc(new CircularArc(c, r, Math.PI/2 + 2*angle, Math.PI/2), g2, f);

        Point2D a = new Point2D.Double(p.getX() + r, p.getY());
        Point2D b = new Point2D.Double(p.getX() + r, p.getY() + r);
        drawLine(new Segment(a, b), g2, f);
         */
    }
}
