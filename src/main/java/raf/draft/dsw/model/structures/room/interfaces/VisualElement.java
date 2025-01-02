package raf.draft.dsw.model.structures.room.interfaces;

import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.room.curves.Curve;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

public interface VisualElement extends Prototype {
    VisualElementTypes getVisualElementType();
    Integer getId();
    Integer getRoomId();

    AffineTransform getTransform();
    void translate(double dx, double dy);
    void rotate(double alpha);
    void rotate(double alpha, Point2D p);
    void scale(Point2D p, double sx, double sy);

    Point2D getInternalPoint();
    Vector<Curve> getEdgeCurves();
    Vector<Point2D> getVertexes();
}
