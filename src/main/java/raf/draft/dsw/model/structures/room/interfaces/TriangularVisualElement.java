package raf.draft.dsw.model.structures.room.interfaces;

public interface TriangularVisualElement extends VisualElement {
    double getAInPixelSpace();
    void setA(double a);
    void scaleA(double lambda);
}
