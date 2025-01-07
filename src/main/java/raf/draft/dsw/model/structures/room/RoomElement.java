package raf.draft.dsw.model.structures.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.Named;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.curves.Vec;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

@Getter
public abstract class RoomElement extends DraftNode implements Named, VisualElement {
    @JsonProperty("name")
    protected String name;
    @JsonProperty("transform")
    protected AffineTransform transform;

    public RoomElement(AffineTransform transform, String name){
        this.transform = transform;
        this.name = name;
    }

    public RoomElement(Point2D location, Integer id){
        super(id);
        transform = AffineTransform.getTranslateInstance(location.getX(), location.getY());
    }

    public RoomElement(AffineTransform transform, Integer id){
        super(id);
        this.transform = (AffineTransform)transform.clone();
    }

    @Override
    public void setName(String newName) {
        name = newName;
        changed();
        notifySubscribers(EventTypes.NODE_EDITED, getDTO());
    }

    @Override
    public DraftNodeTypes getNodeType() {
        return DraftNodeTypes.ROOM_ELEMENT;
    }

    @Override
    public Integer getRoomId() {
        return (parent == null ? null : parent.getId());
    }

    @Override
    public DraftNodeDTO getDTO() {
        Integer parentId = (parent == null ? null : parent.getId());
        return new DraftNodeDTO(id, DraftNodeTypes.ROOM_ELEMENT, name, null, null, saved, parentId);
    }

    protected Point2D getCenter(){
        return Geometry.getRectangleHull(this).getCenter();
    }

    @Override
    public double getAngle(){
        Segment a = new Segment(new Point2D.Double(0, 0), new Point2D.Double(1, 0));
        Segment b = (Segment)a.getTransformedInstance(transform);
        return Vec.angle(new Vec(a.getA(), a.getB()), new Vec(b.getA(), b.getB()));
    }

    @Override
    public Point2D getLocation(){
        return Geometry.getRectangleHull(this).getLocation();
    }

    @Override
    public Point2D getInternalPoint() {
        return getCenter();
    }

    protected void pTranslate(double dx, double dy){
        transform.preConcatenate(AffineTransform.getTranslateInstance(dx, dy));
    }

    protected void pRotate(double alpha, Point2D p) {
        transform.preConcatenate(AffineTransform.getRotateInstance(alpha, p.getX(), p.getY()));
    }

    protected void pScale(Point2D p, double sx, double sy) {
        pTranslate(-p.getX(), -p.getY());
        transform.preConcatenate(AffineTransform.getScaleInstance(sx, sy));
        pTranslate(p.getX(), p.getY());
    }

    @Override
    public void translate(double dx, double dy){
        pTranslate(dx, dy);
        changed();
        if (parent != null) parent.notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
    }

    @Override
    public void rotate(double alpha){
        rotate(alpha, getCenter());
    }

    @Override
    public void rotate(double alpha, Point2D p) {
        pRotate(alpha, p);
        changed();
        if (parent != null) parent.notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
    }

    @Override
    public void scale(Point2D p, double sx, double sy) {
        pScale(p, sx, sy);
        changed();
        if (parent != null) parent.notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
    }

    public abstract Prototype clone(Integer id);

    @Override
    public Prototype clone() {
        return DraftRoomRepository.getInstance().cloneRoomElement(id);
    }
}
