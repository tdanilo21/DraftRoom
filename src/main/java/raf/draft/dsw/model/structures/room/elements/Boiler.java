package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

public class Boiler extends CircularElement {
    public Boiler(double r, Point2D location, Integer id){
        super(r, location, id);
    }

    public Boiler(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.BOILER;
    }

    @Override
    public Vector<Point2D> getVertexes() {
        return new Vector<>();
    }


    @Override
    public Vector<Curve> getEdgeCurves() {
        Vector<Curve> curves = new Vector<>();
        curves.add(new CircularArc(new Point2D.Double(0.5, 0.5), 0.5, 0, 2*Math.PI));
        curves.getFirst().transform(transform);
        return curves;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Boiler(transform, id);
    }
}
