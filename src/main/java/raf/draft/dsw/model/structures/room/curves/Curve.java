package raf.draft.dsw.model.structures.room.curves;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public interface Curve {
    int countIntersections(Curve curve);
    void transform(AffineTransform f);
    Curve getTransformedInstance(AffineTransform f);
    boolean isEdgePoint(Point2D p);

    double getMinX();
    double getMaxX();
    double getMinY();
    double getMaxY();
}
