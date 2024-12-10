package raf.draft.dsw.model.structures.room.interfaces;

public interface RectangularVisualElement extends VisualElement {
    int getW();
    int getH();
    void scaleW(float lambda);
    void scaleH(float lambda);
}
