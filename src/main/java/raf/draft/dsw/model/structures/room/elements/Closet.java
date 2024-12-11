package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.controller.dtos.VisualElementTypes;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Closet extends RectangularElement {
    public Closet(float w, float h, Point2D location, float angle, Integer id){
        super(w, h, location, angle, id);
    }

    public Closet(float w, float h, Point2D location, Integer id){
        super(w, h, location, 0, id);
        if (this.w > this.h) rotate90();
    }

    @Override
    public VisualElementTypes getType() {
        return VisualElementTypes.CLOSET;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Closet(w, h, (Point2D)location.clone(), angle, id);
    }
}
