package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import raf.draft.dsw.controller.PixelSpaceConverter;
import raf.draft.dsw.model.structures.room.SimpleRectangle;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class SelectionPainter extends AbstractPainter{
    SimpleRectangle selection;

    public SelectionPainter(SimpleRectangle selection){
        this.selection = selection;
    }

    @Override
    public VisualElement getElement() {
        return selection;
    }

    @Override
    public void paint(Graphics g, AffineTransform f, PixelSpaceConverter converter) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.BLUE);

        double w = selection.getW(), h = selection.getH();
        Point2D a = selection.getLocation();

        w = converter.lengthToPixelSpace(w);
        h = converter.lengthToPixelSpace(h);
        a = converter.pointToPixelSpace(a);

        Point2D b = new Point2D.Double(a.getX() + w, a.getY() + h);
        drawRectangle(new Segment(a, b), g2, f);
    }
}
