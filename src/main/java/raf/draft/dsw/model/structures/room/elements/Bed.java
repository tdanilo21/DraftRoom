package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.geom.Point2D;

public class Bed extends RectangularElement {
    public Bed(Room room, double w, double h, Point2D location, double angle, Integer id){
        super(room, w, h, location, angle, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.BED;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Bed(getRoom(), w, h, getRoom().toPixelSpace(location), angle, id);
    }
}
