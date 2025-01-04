package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.controller.PixelSpaceConverter;
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
    public void paint(Graphics g, AffineTransform f, PixelSpaceConverter converter) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));

        AffineTransform t = converter.getUnitPixelSpaceTransform();
        t.preConcatenate(converter.transformToPixelSpace(door.getTransform()));
        t.preConcatenate(f);

        Point2D a = new Point2D.Double(1, 0);
        Point2D b = new Point2D.Double(1, 1);
        drawLine(new Segment(a, b), g2, t);
        drawCircularArc(new CircularArc(b, 1, -Math.PI/2, Math.PI/2), g2, t);
    }
}
