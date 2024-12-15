package raf.draft.dsw.model.structures.room;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.Named;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.curves.Vec;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.Vector;

public abstract class RoomElement extends DraftNode implements Named, VisualElement {
    @Getter @Setter
    protected String name;
    protected Point2D location;
    protected double angle;

    public RoomElement(Room room, Point2D location, double angle, Integer id){
        super(id);
        room.addChild(this);
        this.location = getRoom().fromPixelSpace(location);
        this.angle = angle;
    }

    @Override
    public DraftNodeTypes getNodeType() {
        return DraftNodeTypes.ROOM_ELEMENT;
    }

    @Override
    public DraftNodeDTO getDTO() {
        Integer parentId = (parent == null ? null : parent.getId());
        return new DraftNodeDTO(id, DraftNodeTypes.ROOM_ELEMENT, name, null, null, parentId);
    }

    protected Room getRoom(){
        return (Room)parent;
    }

    @Override
    public Integer getRoomId() {
        return getRoom().getId();
    }

    @Override
    public Point2D getLocationInPixelSpace(){
        return getRoom().toPixelSpace(location);
    }

    @Override
    public double getAngleInPixelSpace() {
        return 2*Math.PI - angle;
    }

    @Override
    public void translate(double dx, double dy){
        location.setLocation(location.getX() + getRoom().fromPixelSpace(dx), location.getY() + getRoom().fromPixelSpace(dy));
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void rotate(double alpha){
        angle += 2*Math.PI - alpha;
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    protected AffineTransform getRotation(){
        Point2D center = getCenter();
        return AffineTransform.getRotateInstance(angle, center.getX(), center.getY());
    }

    protected abstract Vector<Curve> getEdgeCurves();

    protected abstract Vector<Point2D> getVertexes();

    @Override
    public boolean overlap(VisualElement element) {
        Vector<Curve> curves = getEdgeCurves();
        for (Curve c : curves)
            if (element.intersect(c))
                return true;
        Point2D p = element.getCenter();
        if (element instanceof Wall wall) p = (Point2D)getRoom().fromPixelSpace(wall.getLocationInPixelSpace()).clone();
        return contains(p) || element.contains(getCenter());
    }

    @Override
    public boolean intersect(Curve curve) {
        Vector<Curve> curves = getEdgeCurves();
        for (Curve c : curves)
            if (curve.countIntersections(c) > 0)
                return true;
        return false;
    }

    @Override
    public boolean contains(Point2D p) {
        Vector<Curve> curves = getEdgeCurves();
        double alpha = 0;
        Vector<Point2D> vertexes = getVertexes();
        if (!vertexes.isEmpty()) {
            Vector<Double> v = new Vector<>();
            for (Point2D a : vertexes)
                v.add(Vec.angle(new Vec(1, 0), new Vec(p, a)));
            Collections.sort(v);
            int index = v.size() - 1;
            double angle = v.getFirst() - v.getLast() + 2 * Math.PI;
            for (int i = 0; i < v.size() - 1; i++) {
                if (v.get(i + 1) - v.get(i) > angle) {
                    index = i;
                    angle = v.get(i + 1) - v.get(i);
                }
            }
            alpha = v.get(index) + angle / 2;
        }
        Point2D inf = new Point2D.Double(1e6, p.getY());
        AffineTransform.getRotateInstance(alpha, p.getX(), p.getY()).transform(inf, inf);
        Segment s = new Segment(p, inf);
        int cnt = 0;
        for (Curve c : curves)
            cnt += c.countIntersections(s);
        return cnt % 2 == 1;
    }

    @Override
    public boolean containsInPixelSpace(Point2D p) {
        return contains(getRoom().fromPixelSpace(p));
    }

    public abstract Prototype clone(Integer id);

    @Override
    public Prototype clone() {
        return (Prototype)DraftRoomRepository.getInstance().cloneRoomElement(id);
    }
}
