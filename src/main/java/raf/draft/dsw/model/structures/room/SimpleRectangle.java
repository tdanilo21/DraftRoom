package raf.draft.dsw.model.structures.room;

import lombok.Getter;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

import java.awt.geom.Point2D;
import java.util.Vector;

public class SimpleRectangle implements RectangularVisualElement {

    private final Room room;
    private final Point2D location;
    @Getter
    private double w, h;

    public SimpleRectangle(Integer roomId, double w, double h, Point2D location){
        this.room = (Room)DraftRoomRepository.getInstance().getNodeObject(roomId);
        this.w = room.fromPixelSpace(w);
        this.h = room.fromPixelSpace(h);
        this.location = room.fromPixelSpace(location);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return null;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public Integer getRoomId() {
        return room.getId();
    }

    @Override
    public Point2D getLocationInPixelSpace() {
        return room.toPixelSpace(location);
    }

    @Override
    public double getAngleInPixelSpace() {
        return 0;
    }

    @Override
    public Point2D getCenterInPixelSpace() {
        return room.toPixelSpace(getCenter());
    }

    @Override
    public void translate(double dx, double dy) {
        location.setLocation(location.getX() + room.fromPixelSpace(dx), location.getY() + room.fromPixelSpace(dy));
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void rotate(double alpha) {}

    @Override
    public double getWInPixelSpace() {
        return room.toPixelSpace(w);
    }

    @Override
    public double getHInPixelSpace() {
        return room.toPixelSpace(h);
    }

    @Override
    public void setH(double h) {
        this.h = room.fromPixelSpace(h);
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void setW(double w) {
        this.w = room.fromPixelSpace(w);
        DraftRoomRepository.getInstance().visualElementEdited(this);
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

    private Vector<Curve> getCurves(){
        Vector<Curve> curves = new Vector<>();
        curves.add(new Segment(new Point2D.Double(location.getX(), location.getY()), new Point2D.Double(location.getX()+w, location.getY())));
        curves.add(new Segment(new Point2D.Double(location.getX()+w, location.getY()), new Point2D.Double(location.getX()+w, location.getY()+h)));
        curves.add(new Segment(new Point2D.Double(location.getX()+w, location.getY()+h), new Point2D.Double(location.getX(), location.getY()+h)));
        curves.add(new Segment(new Point2D.Double(location.getX(), location.getY()+h), new Point2D.Double(location.getX(), location.getY())));
        return curves;
    }

    @Override
    public boolean overlap(VisualElement element) {
        if (element instanceof RoomElement) return element.overlap(this);
        return false;
    }

    @Override
    public boolean intersect(Curve curve) {
        Vector<Curve> curves = getCurves();
        for (Curve c : curves)
            if (curve.countIntersections(c) > 0)
                return true;
        return false;
    }

    @Override
    public boolean contains(Point2D p) {
        double x1 = Math.min(location.getX(), location.getX()+w);
        double x2 = Math.max(location.getX(), location.getX()+w);
        double y1 = Math.min(location.getY(), location.getY()+h);
        double y2 = Math.max(location.getY(), location.getY()+h);
        return p.getX() > x1 && p.getX() < x2 && p.getY() > y1 && p.getY() < y2;
    }

    @Override
    public boolean containsInPixelSpace(Point2D p) {
        return contains(room.fromPixelSpace(p));
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Double(location.getX() + w / 2, location.getY() + h / 2);
    }

    public boolean contains(VisualElement element){
        Vector<Curve> curves = getCurves();
        for (Curve curve : curves)
            if (element.intersect(curve))
                return false;
        Point2D p = element.getCenter();
        if (element instanceof Wall wall) p = (Point2D)room.fromPixelSpace(wall.getLocationInPixelSpace()).clone();
        return contains(p);
    }

    @Override
    public Prototype clone() {
        return new SimpleRectangle(room.getId(), room.toPixelSpace(w), room.toPixelSpace(h), room.toPixelSpace(location));
    }
}
