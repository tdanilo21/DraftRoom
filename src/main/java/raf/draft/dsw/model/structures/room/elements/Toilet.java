package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

public class Toilet extends CircularElement {
    public Toilet(Room room, double r, Point2D location, double angle, Integer id){
        super(room, r, location, angle, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.TOILET;
    }

    @Override
    public Point2D getCenterInPixelSpace() {
        return getRoom().toPixelSpace(getCenter());
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Double(location.getX() + r, location.getY() + r);
    }

    @Override
    public Vector<Point2D> getVertexes() {
        Vector<Point2D> vertexes = new Vector<>();
        vertexes.add((Point2D)location.clone());
        vertexes.add(new Point2D.Double(location.getX(), location.getY() + r));
        vertexes.add(new Point2D.Double(location.getX() + 2*r, location.getY() + r));
        vertexes.add(new Point2D.Double(location.getX() + 2*r, location.getY()));
        AffineTransform f = getRotation();
        for (Point2D p : vertexes) f.transform(p, p);
        return vertexes;
    }

    @Override
    protected Vector<Curve> getEdgeCurves() {
        Vector<Curve> curves = new Vector<>();
        Point2D a = (Point2D)location.clone();
        Point2D b = new Point2D.Double(location.getX(), location.getY() + r);
        Point2D c = new Point2D.Double(location.getX() + 2*r, location.getY() + r);
        Point2D d = new Point2D.Double(location.getX() + 2*r, location.getY());
        curves.add(new Segment((Point2D)a.clone(), (Point2D)b.clone()));
        curves.add(new CircularArc(getCenter(), r, 0, Math.PI));
        curves.add(new Segment((Point2D)c.clone(), (Point2D)d.clone()));
        curves.add(new Segment((Point2D)d.clone(), (Point2D)a.clone()));
        AffineTransform f = getRotation();
        for (Curve curve : curves) curve.transform(f);
        return curves;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Toilet(getRoom(), r, getRoom().toPixelSpace(location), angle, id);
    }
}
