package raf.draft.dsw.model.structures.room;

import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class RectangularElement extends RoomElement implements RectangularVisualElement {
    protected int w, h;

    public RectangularElement(int w, int h, Point location, float angle, Integer id){
        super(location, angle, id);
        this.w = w;
        this.h = h;
    }

    protected void rotate90(){
        angle = (float)Math.PI / 2.0f;
        Point2D center = getCenter();
        AffineTransform t = AffineTransform.getRotateInstance(angle, center.getX(), center.getY());
        location.x += w;
        location = (Point)t.transform(location, null);
        w ^= h; h ^= w; w ^= h;
        angle *= -1;
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
    public Point2D getCenter() {
        return new Point2D.Float(location.x + (float)w / 2.0f, location.y + (float)h / 2.0f);
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
