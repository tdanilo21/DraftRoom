package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Bed extends RectangularElement {
    public Bed(int w, int h, Point location, float angle, Integer id){
        super(w, h, location, angle, id);
    }

    public Bed(int w, int h, Point location, Integer id){
        super(w, h, location, 0, id);
        if (this.w > this.h) rotate90();
    }

    @Override
    public Prototype clone(Integer id) {
        return new Bed(w, h, (Point)location.clone(), angle, id);
    }
}
