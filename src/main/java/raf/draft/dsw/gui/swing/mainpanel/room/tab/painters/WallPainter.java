package raf.draft.dsw.gui.swing.mainpanel.room.tab.painters;

import lombok.Getter;
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
    public void paint(Graphics g, AffineTransform f) {
        Graphics2D g2 = (Graphics2D)g;
        double w = wall.getWInPixelSpace(), h = wall.getHInPixelSpace();
        Point2D p = wall.getLocationInPixelSpace();
        double s = wall.getWallWidth();
        Point2D a = new Point2D.Double(p.getX() + s/2, p.getY() + s/2);
        Point2D b = new Point2D.Double(p.getX() + w - s/2, p.getY() + h - s/2);
        g2.setStroke(new BasicStroke((float)s));
        drawRectangle(new Segment(a, b), g2, f);
    }

}
