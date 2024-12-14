package raf.draft.dsw.model.structures.room.interfaces;

public interface RectangularVisualElement extends VisualElement {
    double getWInPixelSpace();
    double getHInPixelSpace();
    void setH(double h);
    void setW(double w);
    void scaleW(double lambda);
    void scaleH(double lambda);
}
