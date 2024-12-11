package raf.draft.dsw.model.structures.room;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.dtos.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.Named;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class RoomElement extends DraftNode implements Named, Prototype, VisualElement {
    @Getter @Setter
    protected String name;
    protected Point2D location;
    @Getter
    protected float angle;

    public RoomElement(Point2D location, float angle, Integer id){
        super(id);
        this.location = fromPixelSpace(location);
        this.angle = angle;
        this.name = "Element";
    }

    @Override
    public DraftNodeDTO getDTO() {
        Integer parentId = (parent == null ? null : parent.getId());
        return new DraftNodeDTO(id, DraftNodeTypes.ROOM_ELEMENT, name, null, null, parentId);
    }

    protected Room getRoom(){
        return (Room)parent;
    }

    protected float toPixelSpace(float a){
        return a * getRoom().getScaleFactor();
    }

    protected float fromPixelSpace(float a){
        return a / getRoom().getScaleFactor();
    }

    protected Point2D toPixelSpace(Point2D a){
        float scaleFactor = getRoom().getScaleFactor();
        Point2D d = getRoom().getLocation();
        AffineTransform f = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
        f.concatenate(AffineTransform.getTranslateInstance(d.getX(), d.getY()));
        return f.transform(a, null);
    }

    protected Point2D fromPixelSpace(Point2D a){
        float scaleFactor = getRoom().getScaleFactor();
        Point2D d = getRoom().getLocation();
        AffineTransform f = AffineTransform.getScaleInstance(1.0f / scaleFactor, 1.0f / scaleFactor);
        f.concatenate(AffineTransform.getTranslateInstance(-d.getX(), -d.getY()));
        return f.transform(a, null);
    }

    @Override
    public Point2D getLocationInPixelSpace(){
        return toPixelSpace(location);
    }

    @Override
    public void translate(float dx, float dy){
        location.setLocation(location.getX() + fromPixelSpace(dx), location.getY() + fromPixelSpace(dy));
    }

    @Override
    public void rotate(float alpha){
        angle += alpha;
    }
}
