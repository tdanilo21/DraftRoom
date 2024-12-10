package raf.draft.dsw.model.structures.room;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.Named;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;

public abstract class RoomElement extends DraftNode implements Named, Prototype, VisualElement {
    @Getter @Setter
    protected String name;
    protected Point location;
    @Getter
    protected float angle;

    public RoomElement(Point location, float angle, Integer id){
        super(id);
        this.location = location;
        this.angle = angle;
        this.name = "Element";
    }

    protected int multiply(int a, float b){
        return Math.round((float)a * b);
    }

    @Override
    public Point getLocation(){
        // TODO: Transform to pixel space
        return location;
    }

    @Override
    public void translate(int dx, int dy){
        location.translate(dx, dy);
    }

    @Override
    public void rotate(float alpha){
        angle += alpha;
    }
}
