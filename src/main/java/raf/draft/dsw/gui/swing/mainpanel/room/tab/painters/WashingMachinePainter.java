package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.controller.PixelSpaceConverter;
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
    public void paint(Graphics g, AffineTransform f, PixelSpaceConverter converter) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));

        AffineTransform t = converter.getUnitPixelSpaceTransform();
        t.preConcatenate(converter.transformToPixelSpace(washingMachine.getTransform()));
        t.preConcatenate(f);

        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(1, 1);
        drawRectangle(new Segment(a, b), g2, t);

        Point2D c = new Point2D.Double(1.0/6, 1.0/5);
        Point2D d = new Point2D.Double(5.0/6, 4.0/5);
        drawEllipse(new Segment(c, d), g2, t);
    }
}
