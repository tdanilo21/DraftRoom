package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.controller.dtos.VisualElementTypes;
import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;

import java.awt.*;
import java.awt.geom.Point2D;

public class Door extends CircularElement {
    public Door(float r, Point2D location, float angle, Integer id){
        super(r, location, angle, id);
    }

    public Door(float r, Point2D location, Integer id){
        super(r, location, 0, id);
    }

    @Override
    public VisualElementTypes getType() {
        return VisualElementTypes.DOOR;
    }

    @Override
    public Point2D getCenterInPixelSpace() {
        Point2D location = getLocationInPixelSpace();
        float r = getRInPixelSpace();
        return new Point2D.Double(location.getX() + r / 2, location.getY() + r / 2);
    }
    @Override
    public Prototype clone(Integer id) {
        return new Door(r, (Point2D)location.clone(), angle, id);
    }
}
