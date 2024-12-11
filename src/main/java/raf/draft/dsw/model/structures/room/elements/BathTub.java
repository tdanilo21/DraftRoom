package raf.draft.dsw.model.structures.room.elements;

import raf.draft.dsw.controller.dtos.VisualElementTypes;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.RectangularElement;

import java.awt.*;
import java.awt.geom.Point2D;

public class BathTub extends RectangularElement {
    public BathTub(float w, float h, Point2D location, float angle, Integer id){
        super(w, h, location, angle, id);
    }

    public BathTub(float w, float h, Point2D location, Integer id){
        super(w, h, location, 0, id);
    }

    @Override
    public VisualElementTypes getType() {
        return VisualElementTypes.BATH_TUB;
    }

    @Override
    public Prototype clone(Integer id) {
        return new BathTub(w, h, (Point2D)location.clone(), angle, id);
    }
}
