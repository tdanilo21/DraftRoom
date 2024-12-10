package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.interfaces.TriangularVisualElement;

import java.awt.*;

public class Sink extends RoomElement implements TriangularVisualElement {
    private int a;

    public Sink(int a, Point location, float rotation, Integer id){
        super(location, rotation, id);
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
    public void scaleA(float lambda) {
        a = multiply(a, lambda);
    }
}
