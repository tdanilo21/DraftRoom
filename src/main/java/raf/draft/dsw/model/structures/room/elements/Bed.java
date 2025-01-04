package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Bed extends RectangularElement {
    public Bed(double w, double h, Point2D location, Integer id){
        super(w, h, location, id);
    }

    public Bed(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.BED;
    }

    @Override
    public Prototype clone(Integer id) {
        return new Bed(transform, id);
    }
}
