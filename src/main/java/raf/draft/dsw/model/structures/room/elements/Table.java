package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.*;

public class Table extends RectangularElement {
    public Table(int w, int h, Point location, float angle, Integer id){
        super(w, h, location, angle, id);
    }

    public Table(int w, int h, Point location, Integer id){
        super(w, h, location, 0, id);
    }

    @Override
    public Prototype clone(Integer id) {
        return new Table(w, h, location, angle, id);
    }
}
