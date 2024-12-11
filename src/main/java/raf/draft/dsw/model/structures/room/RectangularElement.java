package raf.draft.dsw.model.structures.room;

import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class RectangularElement extends RoomElement implements RectangularVisualElement {
    protected float w, h;

    public RectangularElement(float w, float h, Point2D location, float angle, Integer id){
        super(location, angle, id);
        this.w = w;
        this.h = h;
    }

    protected void rotate90(){
        angle = (float)Math.PI / 2;
        Point2D center = new Point2D.Double(location.getX() + w / 2, location.getY() + h / 2);
        AffineTransform f = AffineTransform.getRotateInstance(angle, center.getX(), center.getY());
        translate(w, 0);
        location = f.transform(location, null);
        float temp = w; w = h; h = temp;
        angle *= -1;
    }

    @Override
    public float getWInPixelSpace(){
        return toPixelSpace(w);
    }

    @Override
    public float getHInPixelSpace(){
        return toPixelSpace(h);
    }

    @Override
    public Point2D getCenterInPixelSpace() {
        Point2D location = getLocationInPixelSpace();
        float w = getWInPixelSpace(), h = getHInPixelSpace();
        return new Point2D.Double(location.getX() + w / 2, location.getY() + h / 2);
    }

    @Override
    public void scaleW(float lambda) {
        w *= lambda;
    }

    @Override
    public void scaleH(float lambda) {
        h *= lambda;
    }
}
