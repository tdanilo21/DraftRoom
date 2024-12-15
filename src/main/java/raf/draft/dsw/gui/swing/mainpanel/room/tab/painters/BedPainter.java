package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class BedPainter extends AbstractPainter{
    private final RectangularVisualElement bed;

    public BedPainter(RectangularVisualElement bed){this.bed = bed;}

    @Override
    public VisualElement getElement() {
        return bed;
    }

    @Override
    public void paint(Graphics g, AffineTransform f) {
        Graphics2D g2 = (Graphics2D)g;
        Point2D p = bed.getLocationInPixelSpace();
        f.concatenate(AffineTransform.getTranslateInstance(p.getX(), p.getY()));
        f.concatenate(AffineTransform.getRotateInstance(bed.getAngleInPixelSpace(), p.getX(), p.getY()));
        double w = bed.getWInPixelSpace(), h = bed.getHInPixelSpace();
        g2.setStroke(new BasicStroke(2));
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(w, h);
        drawRectangle(new Segment(a, b), g2, f);

        Point2D c = new Point2D.Double(w/8, h/12);
        Point2D d = new Point2D.Double(7*w/8, 4*h/12);
        drawRectangle(new Segment(c, d), g2, f);
    }
}
