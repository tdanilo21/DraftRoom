package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;

import java.awt.*;

public class Door extends CircularElement {
    public Door(int r, Point location, float angle, Integer id){
        super(r, location, angle, id);
    }

    @Override
    public Prototype clone(Integer id) {
        return new Door(r, location, angle, id);
    }
}
