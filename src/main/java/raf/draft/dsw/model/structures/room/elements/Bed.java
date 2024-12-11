package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.controller.dtos.VisualElementTypes;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Bed extends RectangularElement {
    public Bed(float w, float h, Point2D location, float angle, Integer id){
        super(w, h, location, angle, id);
    }

    public Bed(float w, float h, Point2D location, Integer id){
        super(w, h, location, 0, id);
        if (this.w > this.h) rotate90();
    }

    @Override
    public VisualElementTypes getType() {
        return VisualElementTypes.BED;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Bed(w, h, (Point2D)location.clone(), angle, id);
    }
}
