package raf.draft.dsw.model.structures.room.elements;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.interfaces.TriangularVisualElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

@Getter
public class Sink extends RoomElement implements TriangularVisualElement {
    private double a;

    public Sink(Room room, double a, Point2D location, double angle, Integer id){
        super(room, location, angle, id);
        this.a = a;
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.SINK;
    }

    @Override
    public double getAInPixelSpace(){
        return getRoom().toPixelSpace(a);
    }

    @Override
    public Point2D getCenterInPixelSpace() {
        return getRoom().toPixelSpace(getCenter());
    }

    @Override
    public void scaleA(double lambda) {
        a *= lambda;
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void setA(double a) {
        this.a = a;
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Double(location.getX() + a / 2, location.getY() + a * (Math.sqrt(3) / 2));
    }

    @Override
    public Vector<Point2D> getVertexes() {
        Vector<Point2D> vertexes = new Vector<>();
        vertexes.add((Point2D)location.clone());
        vertexes.add(new Point2D.Double(location.getX() + a/2, location.getY() + a * (Math.sqrt(3)/2)));
        vertexes.add(new Point2D.Double(location.getX()+a, location.getY()));
        AffineTransform f = getRotation();
        for (Point2D p : vertexes) f.transform(p, p);
        return vertexes;
    }

    @Override
    protected Vector<Curve> getEdgeCurves() {
        Vector<Point2D> vertexes = getVertexes();
        Vector<Curve> curves = new Vector<>();
        for (int i = 0; i < vertexes.size(); i++)
            curves.add(new Segment((Point2D)vertexes.get(i).clone(), (Point2D)vertexes.get((i+1) % vertexes.size()).clone()));
        return curves;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Sink(getRoom(), a, (Point2D)location.clone(), angle, id);
    }
}
