package raf.draft.dsw.model.structures.room;

import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;

import java.awt.*;

public abstract class RectangularElement extends RoomElement implements RectangularVisualElement {
    protected int w, h;

    public RectangularElement(int w, int h, Point location, float angle, Integer id){
        super(location, angle, id);
        this.w = w;
        this.h = h;
    }

    @Override
    public int getW(){
        // TODO: Transform to pixel space;
        return w;
    }

    @Override
    public int getH(){
        // TODO: Transform to pixel space;
        return h;
    }

    @Override
    public void scaleW(float lambda) {
        w = multiply(w, lambda);
    }

    @Override
    public void scaleH(float lambda) {
        h = multiply(h, lambda);
    }
}
