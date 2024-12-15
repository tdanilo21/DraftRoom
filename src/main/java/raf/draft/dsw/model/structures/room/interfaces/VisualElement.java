package raf.draft.dsw.model.structures.room.interfaces;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.room.curves.Curve;

import java.awt.geom.Point2D;

public interface VisualElement {
    VisualElementTypes getVisualElementType();
    Integer getId();
    Integer getRoomId();
    Point2D getLocationInPixelSpace();
    double getAngleInPixelSpace();
    Point2D getCenterInPixelSpace();
    void translate(double dx, double dy);
    void rotate(double alpha);
    boolean overlap(VisualElement element);
    boolean intersect(Curve curve);
    boolean contains(Point2D p);
    boolean containsInPixelSpace(Point2D p);
    Point2D getCenter();
}
