package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.curves.Vec;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.interfaces.TriangularVisualElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

public class Sink extends RoomElement implements TriangularVisualElement {

    public Sink(double a, Point2D location, Integer id){
        super(location, id);
        pScale(location, a, a);
    }

    public Sink(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.SINK;
    }

    @Override
    public void scale(Point2D p, double sx, double sy) {
        double lambda = Math.max(sx, sy);
        super.scale(p, lambda, lambda);
    }

    @Override
    public double getA() {
        Segment s = new Segment(new Point2D.Double(0, 0), new Point2D.Double(1, 0));
        s.transform(transform);
        return (new Vec(s.getA(), s.getB())).abs();
    }

    @Override
    public void setA(double a) {
        double a0 = getA();
        pScale(getLocation(), a/a0, a/a0);
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public Vector<Point2D> getVertexes() {
        Vector<Point2D> vertexes = new Vector<>();
        vertexes.add(new Point2D.Double(0, 0));
        vertexes.add(new Point2D.Double(1, 0));
        vertexes.add(new Point2D.Double(0.5, Math.sqrt(3)/2));
        for (Point2D p : vertexes) transform.transform(p, p);
        return vertexes;
    }

    @Override
    public Vector<Curve> getEdgeCurves() {
        Vector<Point2D> vertexes = getVertexes();
        Vector<Curve> curves = new Vector<>();
        for (int i = 0; i < vertexes.size(); i++)
            curves.add(new Segment((Point2D)vertexes.get(i).clone(), (Point2D)vertexes.get((i+1) % vertexes.size()).clone()));
        return curves;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Sink(transform, id);
    }
}
