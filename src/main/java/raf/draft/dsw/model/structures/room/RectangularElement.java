package raf.draft.dsw.model.structures.room;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.curves.Vec;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

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
    public void setH(double h) {
        Segment s = new Segment(new Point2D.Double(0, 0), new Point2D.Double(0, 1));
        s.transform(transform);
        double h0 = (new Vec(s.getA(), s.getB())).abs();

        double alpha = getCurrentAngle();
        Point2D p = getCurrentLocation();
        pRotate(-alpha, p);
        pScale(p, 1, h/h0);
        pRotate(alpha, p);

        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void setW(double w) {
        Segment s = new Segment(new Point2D.Double(0, 0), new Point2D.Double(1, 0));
        s.transform(transform);
        double w0 = (new Vec(s.getA(), s.getB())).abs();

        double alpha = getCurrentAngle();
        Point2D p = getCurrentLocation();
        pRotate(-alpha, p);
        pScale(p,w/w0,1);
        pRotate(alpha, p);

        DraftRoomRepository.getInstance().visualElementEdited(this);
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
