package raf.draft.dsw.model.structures.room.interfaces;

import java.awt.*;

public interface VisualElement {
    Integer getId();
    Point getLocation();
    float getAngle();
    void translate(int dx, int dy);
    void rotate(float alpha);
}
