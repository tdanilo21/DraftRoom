package raf.draft.dsw.model.structures.room;

import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.curves.Vec;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.Vector;

public class Geometry {

    public static boolean contains(VisualElement a, Point2D p){
        Vector<Curve> curves = a.getEdgeCurves();
        double alpha = 0;
        Vector<Point2D> vertexes = a.getVertexes();
        if (!vertexes.isEmpty()) {
            Vector<Double> v = new Vector<>();
            for (Point2D t : vertexes)
                v.add(Vec.angle(new Vec(1, 0), new Vec(p, t)));
            Collections.sort(v);
            int index = v.size() - 1;
            double angle = v.getFirst() - v.getLast() + 2 * Math.PI;
            for (int i = 0; i < v.size() - 1; i++) {
                if (v.get(i + 1) - v.get(i) > angle) {
                    index = i;
                    angle = v.get(i + 1) - v.get(i);
                }
            }
            alpha = v.get(index) + angle / 2;
        }
        Point2D inf = new Point2D.Double(1e6, p.getY());
        AffineTransform.getRotateInstance(alpha, p.getX(), p.getY()).transform(inf, inf);
        Segment s = new Segment(p, inf);
        int cnt = 0;
        for (Curve c : curves)
            cnt += c.countIntersections(s);
        return (cnt&1) == 1;
    }

    private static boolean intersects(VisualElement a, Curve curve){
        Vector<Curve> curves = a.getEdgeCurves();
        for (Curve c : curves)
            if (curve.countIntersections(c) > 0)
                return true;
        return false;
    }

    private static boolean intersects(VisualElement a, VisualElement b){
        Vector<Curve> curves = a.getEdgeCurves();
        for (Curve c : curves)
            if (intersects(b, c))
                return true;
        return false;
    }

    public static boolean contains(VisualElement a, VisualElement b){
        return !intersects(a, b) && contains(a, b.getInternalPoint()) && getRectangleHull(a).getW() >= getRectangleHull(b).getW();
    }

    public static boolean overlaps(VisualElement a, VisualElement b){
        return intersects(a, b) || contains(a, b.getInternalPoint()) || contains(b, a.getInternalPoint());
    }

    private static SimpleRectangle merge(SimpleRectangle a, SimpleRectangle b){
        Vector<Point2D> va = a.getVertexes(), vb = b.getVertexes();
        double x0 = Math.min(va.getFirst().getX(), vb.getFirst().getX());
        double y0 = Math.min(va.getFirst().getY(), vb.getFirst().getY());
        double x1 = Math.max(va.get(2).getX(), vb.get(2).getX());
        double y1 = Math.max(va.get(2).getY(), vb.get(2).getY());
        return new SimpleRectangle(a.getRoomId(), x1-x0, y1-y0, new Point2D.Double(x0, y0));
    }

    public static SimpleRectangle getRectangleHull(VisualElement a){
        Vector<Curve> curves = a.getEdgeCurves();
        double x0 = curves.getFirst().getMinX();
        double y0 = curves.getFirst().getMinY();
        double x1 = curves.getFirst().getMaxX();
        double y1 = curves.getFirst().getMaxY();
        for (int i = 1; i < curves.size(); i++){
            x0 = Math.min(x0, curves.get(i).getMinX());
            y0 = Math.min(y0, curves.get(i).getMinY());
            x1 = Math.max(x1, curves.get(i).getMaxX());
            y1 = Math.max(y1, curves.get(i).getMaxY());
        }
        return new SimpleRectangle(a.getRoomId(), x1-x0, y1-y0, new Point2D.Double(x0, y0));
    }

    public static SimpleRectangle getRectangleHull(Vector<VisualElement> v){
        if (v.isEmpty()) return null;
        SimpleRectangle hull = getRectangleHull(v.getFirst());
        for (int i = 1; i < v.size(); i++)
            hull = merge(hull, getRectangleHull(v.get(i)));
        return hull;
    }
}
