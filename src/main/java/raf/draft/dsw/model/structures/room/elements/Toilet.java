package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.controller.dtos.VisualElementTypes;
import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;

import java.awt.*;
import java.awt.geom.Point2D;

public class Toilet extends CircularElement {
    public Toilet(float r, Point2D location, float angle, Integer id){
        super(r, location, angle, id);
    }

    public Toilet(float r, Point2D location, Integer id){
        super(r, location, 0, id);
    }

    @Override
    public VisualElementTypes getType() {
        return VisualElementTypes.TOILET;
    }

    @Override
    public Point2D getCenterInPixelSpace() {
        Point2D location = getLocationInPixelSpace();
        float r = getRInPixelSpace();
        return new Point2D.Double(location.getX() + r, location.getY() + r);
    }

    @Override
    public Prototype clone(Integer id) {
        return new Toilet(r, (Point2D)location.clone(), angle, id);
    }
}
