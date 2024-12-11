package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.controller.dtos.VisualElementTypes;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.interfaces.TriangularVisualElement;

import java.awt.*;
import java.awt.geom.Point2D;

public class Sink extends RoomElement implements TriangularVisualElement {
    private float a;

    public Sink(float a, Point2D location, float angle, Integer id){
        super(location, angle, id);
        this.a = a;
    }

    public Sink(float a, Point2D location, Integer id){
        super(location, 0, id);
        this.a = a;
    }

    @Override
    public VisualElementTypes getType() {
        return VisualElementTypes.SINK;
    }

    @Override
    public float getAInPixelSpace(){
        return toPixelSpace(a);
    }

    @Override
    public Point2D getCenterInPixelSpace() {
        Point2D location = getLocationInPixelSpace();
        float a = getAInPixelSpace();
        return new Point2D.Double(location.getX() + a / 2, location.getY() + a / 2);
    }

    @Override
    public void scaleA(float lambda) {
        a *= lambda;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Sink(a, (Point2D)location.clone(), angle, id);
    }
}
