package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.*;

public class Bed extends RectangularElement {
    public Bed(int w, int h, Point location, float angle, Integer id){
        super(w, h, location, angle, id);
    }

    @Override
    public Prototype clone(Integer id) {
        return new Bed(w, h, location, angle, id);
    }
}
