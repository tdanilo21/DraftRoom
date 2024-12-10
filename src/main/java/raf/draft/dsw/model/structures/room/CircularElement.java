package raf.draft.dsw.model.structures.room;

import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class CircularElement extends RoomElement implements CircularVisualElement {
    protected int r;

    public CircularElement(int r, Point location, float angle, Integer id){
        super(location, angle, id);
    }

    @Override
    public int getR(){
        // TODO: Transform to pixel space
        return r;
    }

    @Override
    public void scaleR(float lambda) {
        r = multiply(r, lambda);
    }
}
