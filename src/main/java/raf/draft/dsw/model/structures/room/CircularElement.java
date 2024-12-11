package raf.draft.dsw.model.structures.room;

import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class CircularElement extends RoomElement implements CircularVisualElement {
    protected float r;

    public CircularElement(float r, Point2D location, float angle, Integer id){
        super(location, angle, id);
        this.r = r;
    }

    @Override
    public float getRInPixelSpace(){
        return toPixelSpace(r);
    }

    @Override
    public void scaleR(float lambda) {
        r *= lambda;
    }
}
