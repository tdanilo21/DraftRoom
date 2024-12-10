package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;

import java.awt.*;

public class Boiler extends CircularElement {
    public Boiler(int r, Point location, float angle, Integer id){
        super(r, location, angle, id);
    }

    @Override
    public Prototype clone(Integer id) {
        return new Boiler(r, location, angle, id);
    }
}
