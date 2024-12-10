package raf.draft.dsw.model.structures.room.interfaces;

import java.awt.*;
import java.awt.geom.Point2D;

public interface VisualElement {
    Integer getId();
    Point getLocation();
    float getAngle();
    Point2D getCenter();
    void translate(int dx, int dy);
    void rotate(float alpha);
}
