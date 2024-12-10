package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;

import java.awt.*;
import java.awt.geom.Point2D;

public class Door extends CircularElement {
    public Door(int r, Point location, float angle, Integer id){
        super(r, location, angle, id);
    }

    public Door(int r, Point location, Integer id){
        super(r, location, 0, id);
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Float(location.x + (float)r / 2.0f, location.y + (float)r / 2.0f);
    }
    @Override
    public Prototype clone(Integer id) {
        return new Door(r, location, angle, id);
    }
}
