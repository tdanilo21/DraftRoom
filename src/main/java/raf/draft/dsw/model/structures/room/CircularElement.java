package raf.draft.dsw.model.structures.room;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.curves.Vec;
import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class CircularElement extends RoomElement implements CircularVisualElement {
    public CircularElement(double r, Point2D location, Integer id){
        super(location, id);
        pScale(location, r, r);
    }

    public CircularElement(AffineTransform transform, Integer id){
        super(transform, id);
    }

    @Override
    public void scale(Point2D p, double sx, double sy) {
        double lambda = Math.max(sx, sy);
        super.scale(p, lambda, lambda);
    }

    protected abstract double getR();

    @Override
    public void setR(double r) {
        double r0 = getR();
        pScale(getCurrentLocation(), r/r0, r/r0);
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }
}
