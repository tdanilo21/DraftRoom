package raf.draft.dsw.model.structures.room.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.nodes.DraftNodeSubType;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.curves.CircularArc;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.curves.Vec;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

@DraftNodeSubType("Door")
public class Door extends CircularElement {
    @JsonCreator
    public Door(@JsonProperty("transform") AffineTransform transform, @JsonProperty("name") String name){
        super(transform, name);
    }

    public Door(double r, Point2D location, Integer id){
        super(location, id);
        pScale(location, r, r);
    }

    public Door(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.DOOR;
    }

    @Override
    public double getR() {
        Segment s = new Segment(new Point2D.Double(0, 0), new Point2D.Double(1, 0));
        s.transform(transform);
        return (new Vec(s.getA(), s.getB())).abs();
    }

    @Override
    public Vector<Point2D> getVertexes() {
        Vector<Point2D> vertexes = new Vector<>();
        vertexes.add(new Point2D.Double(1, 0));
        vertexes.add(new Point2D.Double(1, 1));
        vertexes.add(new Point2D.Double(0, 1));
        for (Point2D p : vertexes) transform.transform(p, p);
        return vertexes;
    }

    @Override
    public Vector<Curve> getEdgeCurves() {
        Vector<Curve> curves = new Vector<>();
        Point2D a = new Point2D.Double(1, 0);
        Point2D b = new Point2D.Double(1, 1);
        Point2D c = new Point2D.Double(0, 1);
        curves.add(new CircularArc((Point2D)b.clone(), 1, Math.PI, Math.PI/2));
        curves.add(new Segment((Point2D)b.clone(), (Point2D)c.clone()));
        curves.add(new Segment((Point2D)b.clone(), (Point2D)a.clone()));
        for (Curve curve : curves) curve.transform(transform);
        return curves;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Door(transform, id);
    }
}
