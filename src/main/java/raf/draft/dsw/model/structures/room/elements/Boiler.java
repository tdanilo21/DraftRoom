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

@DraftNodeSubType("Boiler")
public class Boiler extends CircularElement {
    @JsonCreator
    public Boiler(@JsonProperty("transform") AffineTransform transform, @JsonProperty("name") String name){
        super(transform, name);
    }

    public Boiler(double r, Point2D location, Integer id){
        super(location, id);
        pScale(location, 2*r, 2*r);
    }

    public Boiler(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.BOILER;
    }

    @Override
    public double getR() {
        Segment s = new Segment(new Point2D.Double(0, 0), new Point2D.Double(0.5, 0));
        s.transform(transform);
        return (new Vec(s.getA(), s.getB())).abs();
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
