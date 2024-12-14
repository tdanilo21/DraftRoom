package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.geom.Point2D;

public class BathTub extends RectangularElement {
    public BathTub(Room room, double w, double h, Point2D location, double angle, Integer id){
        super(room, w, h, location, angle, id);
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.BATH_TUB;
    }

    @Override
    public Prototype clone(Integer id) {
        return new BathTub(getRoom(), w, h, (Point2D)location.clone(), angle, id);
    }
}
