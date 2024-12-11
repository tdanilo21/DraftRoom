package raf.draft.dsw.model.structures.room.interfaces;

public interface RectangularVisualElement extends VisualElement {
    float getWInPixelSpace();
    float getHInPixelSpace();
    void scaleW(float lambda);
    void scaleH(float lambda);
}
