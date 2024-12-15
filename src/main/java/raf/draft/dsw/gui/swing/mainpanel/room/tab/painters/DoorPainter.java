package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

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
    public void paint(Graphics g, AffineTransform f) {
        Graphics2D g2 = (Graphics2D)g;
        Point2D p = door.getLocationInPixelSpace();
        double r = door.getRInPixelSpace();
        g2.setStroke(new BasicStroke(2));

        Point2D c = new Point2D.Double(p.getX() + r, p.getY() + r);
        drawCircularArc(new CircularArc(c, r, Math.PI/2, Math.PI/2), g2, f);

        Point2D a = new Point2D.Double(p.getX() + r, p.getY());
        Point2D b = new Point2D.Double(p.getX() + r, p.getY() + r);
        drawLine(new Segment(a, b), g2, f);
    }
}
