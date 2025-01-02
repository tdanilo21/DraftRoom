package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class WashingMachine extends RectangularElement {
    public WashingMachine(double w, double h, Point2D location, Integer id){
        super(w, h, location, id);
    }

    public WashingMachine(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.WASHING_MACHINE;
    }

    @Override
    public Prototype clone(Integer id) {
        return new WashingMachine(transform, id);
    }
}
