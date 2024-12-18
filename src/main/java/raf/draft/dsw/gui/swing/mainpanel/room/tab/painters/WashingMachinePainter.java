package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class WashingMachinePainter extends AbstractPainter{
    private final RectangularVisualElement washingMachine;

    public WashingMachinePainter(RectangularVisualElement washingMachine){this.washingMachine = washingMachine;}

    @Override
    public VisualElement getElement() {
        return washingMachine;
    }

    @Override
    public void paint(Graphics g, AffineTransform f) {
        Graphics2D g2 = (Graphics2D)g;
        Point2D p = washingMachine.getLocationInPixelSpace();
        double w = washingMachine.getWInPixelSpace(), h = washingMachine.getHInPixelSpace();
        f.concatenate(AffineTransform.getTranslateInstance(p.getX(), p.getY()));
        f.concatenate(AffineTransform.getRotateInstance(washingMachine.getAngleInPixelSpace(), w/2, h/2));
        g2.setStroke(new BasicStroke(2));
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(w, h);
        drawRectangle(new Segment(a, b), g2, f);

        Point2D c = new Point2D.Double(w/6, h/5);
        Point2D d = new Point2D.Double(5*w/6, 4*h/5);
        drawEllipse(new Segment(c, d), g2, f);
    }
}
