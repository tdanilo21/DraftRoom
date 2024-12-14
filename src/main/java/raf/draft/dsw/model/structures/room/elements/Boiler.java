package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;

import java.awt.geom.Point2D;
import java.util.Vector;

public class Boiler extends CircularElement {
    public Boiler(Room room, double r, Point2D location, double angle, Integer id){
        super(room, r, location, angle, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.BOILER;
    }

    @Override
    public Point2D getCenterInPixelSpace() {
        Point2D location = getLocationInPixelSpace();
        double r = getRInPixelSpace();
        return new Point2D.Double(location.getX() + r, location.getY() + r);
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Double(location.getX() + r, location.getY() + r);
    }

    @Override
    public Vector<Point2D> getVertexes() {
        return new Vector<>();
    }


    @Override
    protected Vector<Curve> getEdgeCurves() {
        Vector<Curve> curves = new Vector<>();
        curves.add(new CircularArc(getCenter(), r, 0, 2*Math.PI));
        curves.getFirst().transform(getRotation());
        return curves;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Boiler(getRoom(), r, (Point2D)location.clone(), angle, id);
    }
}
