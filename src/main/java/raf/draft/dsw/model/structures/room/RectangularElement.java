package raf.draft.dsw.model.structures.room;

import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.curves.Vec;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

public abstract class RectangularElement extends RoomElement implements RectangularVisualElement {
    public RectangularElement(double w, double h, Point2D location, Integer id){
        super(location, id);
        pScale(location, w, h);
    }

    public RectangularElement(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public double getW(){
        Segment s = new Segment(new Point2D.Double(0, 0), new Point2D.Double(1, 0));
        s.transform(transform);
        return (new Vec(s.getA(), s.getB())).abs();
    }

    @Override
    public double getH() {
        Segment s = new Segment(new Point2D.Double(0, 0), new Point2D.Double(0, 1));
        s.transform(transform);
        return (new Vec(s.getA(), s.getB())).abs();
    }

    @Override
    public void setW(double w) {
        double w0 = getW();
        double alpha = getAngle();
        Point2D p = getLocation();
        pRotate(-alpha, p);
        pScale(p,w/w0,1);
        pRotate(alpha, p);
        if (parent != null) parent.notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
    }

    @Override
    public void setH(double h) {
        double h0 = getH();
        double alpha = getAngle();
        Point2D p = getLocation();
        pRotate(-alpha, p);
        pScale(p, 1, h/h0);
        pRotate(alpha, p);
        if (parent != null) parent.notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
    }

    @Override
    public Vector<Point2D> getVertexes() {
        Vector<Point2D> vertexes = new Vector<>();
        vertexes.add(new Point2D.Double(0, 0));
        vertexes.add(new Point2D.Double(1, 0));
        vertexes.add(new Point2D.Double(1, 1));
        vertexes.add(new Point2D.Double(0, 1));
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
}
