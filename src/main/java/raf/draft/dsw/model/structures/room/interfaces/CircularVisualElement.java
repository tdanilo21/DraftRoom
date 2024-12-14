package raf.draft.dsw.model.structures.room.interfaces;

public interface CircularVisualElement extends VisualElement {
    double getRInPixelSpace();
    void setR(double r);
    void scaleR(double lambda);
}
