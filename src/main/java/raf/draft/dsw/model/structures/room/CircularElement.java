package raf.draft.dsw.model.structures.room;

import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class CircularElement extends RoomElement implements CircularVisualElement {
    public CircularElement(Point2D location, Integer id){
        super(location, id);
    }

    public CircularElement(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public void scale(Point2D p, double sx, double sy) {
        double lambda = Math.max(sx, sy);
        super.scale(p, lambda, lambda);
    }

    @Override
    public void setR(double r) {
        double r0 = getR();
        pScale(getLocation(), r/r0, r/r0);
        if (parent != null) parent.notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
    }
}
