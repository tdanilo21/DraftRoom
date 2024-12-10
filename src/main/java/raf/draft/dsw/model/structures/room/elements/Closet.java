package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.*;

public class Closet extends RectangularElement {
    public Closet(int w, int h, Point location, float angle, Integer id){
        super(w, h, location, angle, id);
    }

    @Override
    public Prototype clone(Integer id) {
        return new Closet(w, h, location, angle, id);
    }
}
