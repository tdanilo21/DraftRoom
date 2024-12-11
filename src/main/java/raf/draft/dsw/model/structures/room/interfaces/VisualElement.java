package raf.draft.dsw.model.structures.room.interfaces;

import raf.draft.dsw.controller.dtos.VisualElementTypes;

import java.awt.*;
import java.awt.geom.Point2D;

public interface VisualElement {
    VisualElementTypes getType();
    Integer getId();
    Point2D getLocationInPixelSpace();
    float getAngle();
    Point2D getCenterInPixelSpace();
    void translate(float dx, float dy);
    void rotate(float alpha);
}
