package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.elements.Sink;
import raf.draft.dsw.model.structures.room.interfaces.TriangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class SinkPainter extends AbstractPainter{
    private final TriangularVisualElement sink;

    public SinkPainter(TriangularVisualElement sink){this.sink = sink;}

    @Override
    public VisualElement getElement() {
        return sink;
    }

    @Override
    public void paint(Graphics g, AffineTransform f) {
        Graphics2D g2 = (Graphics2D)g;
        Point2D b = sink.getLocationInPixelSpace();
        double a = sink.getAInPixelSpace();
        g2.setStroke(new BasicStroke(2));

        Point2D c = new Point2D.Double(b.getX() + a, b.getY());
        Point2D d = new Point2D.Double(b.getX() + a/2, b.getY() + a*Math.sqrt(3)/2);
        drawLine(new Segment(b, c), g2, f);
        drawLine(new Segment(c, d), g2, f);
        drawLine(new Segment(d, b), g2, f);

        Point2D p = new Point2D.Double(d.getX(), b.getY() + a*Math.sqrt(3)/8);
        drawCircularArc(new CircularArc(p, a/24, 0, 2*Math.PI), g2, f);
    }
}
