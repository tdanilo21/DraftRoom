package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.interfaces.TriangularVisualElement;

import java.awt.*;
import java.awt.geom.Point2D;

public class Sink extends RoomElement implements TriangularVisualElement {
    private int a;

    public Sink(int a, Point location, float angle, Integer id){
        super(location, angle, id);
        this.a = a;
    }

    public Sink(int a, Point location, Integer id){
        super(location, 0, id);
        this.a = a;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Sink(a, location, angle, id);
    }

    @Override
    public int getA(){
        // TODO: Transform to pixel space
        return a;
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Float(location.x + (float)a / 2.0f, location.y + (float)a / 2.0f);
    }

    @Override
    public void scaleA(float lambda) {
        a = multiply(a, lambda);
    }
}
