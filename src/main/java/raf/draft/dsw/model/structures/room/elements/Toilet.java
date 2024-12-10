package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;

import java.awt.*;
import java.awt.geom.Point2D;

public class Toilet extends CircularElement {
    public Toilet(int r, Point location, float angle, Integer id){
        super(r, location, angle, id);
    }

    public Toilet(int r, Point location, Integer id){
        super(r, location, 0, id);
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Float(location.x + r, location.y + r);
    }

    @Override
    public Prototype clone(Integer id) {
        return new Toilet(r, location, angle, id);
    }
}
