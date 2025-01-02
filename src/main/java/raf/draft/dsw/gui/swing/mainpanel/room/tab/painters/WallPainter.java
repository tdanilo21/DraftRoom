package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import lombok.Getter;
import raf.draft.dsw.controller.RealToPixelSpaceConverter;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

@Getter
public class WallPainter extends AbstractPainter{
    private final Wall wall;

    public WallPainter(Wall wall){
        this.wall = wall;
    }

    @Override
    public VisualElement getElement() {
        return wall;
    }

    @Override
    public void paint(Graphics g, AffineTransform f, RealToPixelSpaceConverter converter) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));

        double w = wall.getW(), h = wall.getH();
        // TODO: Add getLocation
        Point2D p = new Point2D.Double(0, 0);

        w = converter.lengthToPixelSpace(w);
        h = converter.lengthToPixelSpace(h);
        p = converter.pointToPixelSpace(p);

        double s = wall.getWallWidth();
        Point2D a = new Point2D.Double(p.getX() + s/2, p.getY() + s/2);
        Point2D b = new Point2D.Double(p.getX() + w - s/2, p.getY() + h - s/2);
        g2.setStroke(new BasicStroke((float)s));
        drawRectangle(new Segment(a, b), g2, f);
    }
}
