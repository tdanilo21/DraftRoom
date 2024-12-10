package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.*;

public class BathTub extends RectangularElement {
    public BathTub(int w, int h, Point location, float angle, Integer id){
        super(w, h, location, angle, id);
    }

    public BathTub(int w, int h, Point location, Integer id){
        super(w, h, location, 0, id);
    }

    @Override
    public Prototype clone(Integer id) {
        return new BathTub(w, h, location, angle, id);
    }
}
