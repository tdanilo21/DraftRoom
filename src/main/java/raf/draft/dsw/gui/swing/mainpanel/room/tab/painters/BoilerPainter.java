package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.painters.AbstractPainter;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class BoilerPainter extends AbstractPainter {
    private final CircularVisualElement boiler;

    public BoilerPainter(CircularVisualElement boiler){this.boiler = boiler;}

    @Override
    public VisualElement getElement() {
        return boiler;
    }

    @Override
    public void paint(Graphics g, AffineTransform f) {
        Graphics2D g2 = (Graphics2D)g;
        Point2D p = boiler.getCenterInPixelSpace();
        double r = boiler.getRInPixelSpace();
        g2.setStroke(new BasicStroke(2));
        drawCircularArc(new CircularArc(p, r, 0, 2*Math.PI), g2, f);

        Point2D p1 = new Point2D.Double(p.getX() - r/2, p.getY() - r/2);
        Point2D p2 = new Point2D.Double(p.getX() + r/2, p.getY() + r/2);
        drawLine(new Segment(p1, p2), g2, f);

        Point2D p3 = new Point2D.Double(p.getX() + r/2, p.getY() - r/2);
        Point2D p4 = new Point2D.Double(p.getX() - r/2, p.getY() + r/2);
        drawLine(new Segment(p3, p4), g2, f);
    }
}
