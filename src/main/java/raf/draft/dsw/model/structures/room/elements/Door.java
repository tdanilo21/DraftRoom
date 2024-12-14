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

public class Door extends CircularElement {
    public Door(Room room, double r, Point2D location, double angle, Integer id){
        super(room, r, location, angle, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.DOOR;
    }

    @Override
    public Point2D getCenterInPixelSpace() {
        Point2D location = getLocationInPixelSpace();
        double r = getRInPixelSpace();
        return new Point2D.Double(location.getX() + r / 2, location.getY() + r / 2);
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Double(location.getX() + r / 2, location.getY() + r / 2);
    }

    @Override
    public Vector<Point2D> getVertexes() {
        Vector<Point2D> vertexes = new Vector<>();
        vertexes.add(new Point2D.Double(location.getX() + r, location.getY()));
        vertexes.add(new Point2D.Double(location.getX(), location.getY() + r));
        vertexes.add(new Point2D.Double(location.getX() + r, location.getY() + r));
        AffineTransform f = getRotation();
        for (Point2D p : vertexes) f.transform(p, p);
        return vertexes;
    }

    @Override
    protected Vector<Curve> getEdgeCurves() {
        Vector<Curve> curves = new Vector<>();
        Point2D a = new Point2D.Double(location.getX() + r, location.getY());
        Point2D b = new Point2D.Double(location.getX(), location.getY() + r);
        Point2D c = new Point2D.Double(location.getX() + r, location.getY() + r);
        curves.add(new CircularArc((Point2D)c.clone(), r, Math.PI, Math.PI/2));
        curves.add(new Segment((Point2D)b.clone(), (Point2D)c.clone()));
        curves.add(new Segment((Point2D)c.clone(), (Point2D)a.clone()));
        AffineTransform f = getRotation();
        for (Curve curve : curves) curve.transform(f);
        return curves;
    }


    @Override
    public Prototype clone(Integer id) {
        return new Door(getRoom(), r, (Point2D)location.clone(), angle, id);
    }
}
