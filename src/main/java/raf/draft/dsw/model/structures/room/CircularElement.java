package raf.draft.dsw.model.structures.room;

import lombok.Setter;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;

import java.awt.*;
import java.awt.geom.Point2D;

@Setter
public abstract class CircularElement extends RoomElement implements CircularVisualElement {
    protected double r;

    public CircularElement(Room room, double r, Point2D location, double angle, Integer id){
        super(room, location, angle, id);
        this.r = r;
    }

    @Override
    public double getRInPixelSpace(){
        return toPixelSpace(r);
    }

    @Override
    public void scaleR(double lambda) {
        r *= lambda;
    }
}
