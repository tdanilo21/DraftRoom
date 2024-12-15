package raf.draft.dsw.model.structures.room;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

@Getter
public abstract class RectangularElement extends RoomElement implements RectangularVisualElement {
    protected double w, h;

    public RectangularElement(Room room, double w, double h, Point2D location, double angle, Integer id){
        super(room, location, angle, id);
        this.w = w;
        this.h = h;
    }

    protected void rotate90(){
        Point2D center = getCenter();
        AffineTransform f = AffineTransform.getRotateInstance(Math.PI / 2, center.getX(), center.getY());
        translate(w, 0);
        f.transform(location, location);
        double temp = w; w = h; h = temp;
        this.angle -= Math.PI / 2;
    }

    @Override
    public double getWInPixelSpace(){
        return getRoom().toPixelSpace(w);
    }

    @Override
    public double getHInPixelSpace(){
        return getRoom().toPixelSpace(h);
    }

    @Override
    public Point2D getCenterInPixelSpace() {
        return getRoom().toPixelSpace(getCenter());
    }

    @Override
    public void scaleW(double lambda) {
        w *= lambda;
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void scaleH(double lambda) {
        h *= lambda;
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void setH(double h) {
        this.h = h;
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void setW(double w) {
        this.w = w;
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Double(location.getX() + w / 2, location.getY() + h / 2);
    }

    @Override
    public Vector<Point2D> getVertexes() {
        Vector<Point2D> vertexes = new Vector<>();
        vertexes.add((Point2D)location.clone());
        vertexes.add(new Point2D.Double(location.getX(), location.getY()+h));
        vertexes.add(new Point2D.Double(location.getX()+w, location.getY()+h));
        vertexes.add(new Point2D.Double(location.getX()+w, location.getY()));
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
}
