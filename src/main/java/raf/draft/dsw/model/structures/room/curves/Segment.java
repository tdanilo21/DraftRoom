package raf.draft.dsw.model.structures.room.curves;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

@Getter @Setter
public class Segment implements Curve {

    private Point2D a, b;
    public Segment(Point2D a, Point2D b){
        this.a = a;
        this.b = b;
    }

    @Override
    public void transform(AffineTransform f) {
        f.transform(a, a);
        f.transform(b, b);
    }

    @Override
    public Curve getTransformedInstance(AffineTransform f) {
        Segment s = new Segment((Point2D)a.clone(), (Point2D)b.clone());
        s.transform(f);
        return s;
    }

    @Override
    public boolean isEdgePoint(Point2D p){
        return Vec.equals(a, p) || Vec.equals(b, p);
    }

    private boolean contains(Point2D p){
        double x = p.getX(), y = p.getY();
        double x1 = a.getX(), y1 = a.getY();
        double x2 = b.getX(), y2 = b.getY();
        return Vec.orientation(new Vec(a, p), new Vec(p, b)) == Vec.COLLINEAR
            && Vec.greaterThanOrEquals(x, Math.min(x1, x2)) && Vec.greaterThanOrEquals(Math.max(x1, x2), x)
            && Vec.greaterThanOrEquals(y, Math.min(y1, y2)) && Vec.greaterThanOrEquals(Math.max(y1, y2), y);
    }

    private int pCountIntersections(Segment s){
        if (contains(s.a) || contains(s.b) || s.contains(a) || s.contains(b)) return 0;
        int o1 = Vec.orientation(new Vec(a, b), new Vec(a, s.a));
        int o2 = Vec.orientation(new Vec(a, b), new Vec(a, s.b));
        int o3 = Vec.orientation(new Vec(s.a, s.b), new Vec(s.a, a));
        int o4 = Vec.orientation(new Vec(s.a, s.b), new Vec(s.b, b));
        return o1 != o2 && o3 != o4 ? 1 : 0;
    }

    private int pCountIntersections(CircularArc arc){
        Vec u = new Vec(a, b);
        if (Vec.equals(u.abs(), 0)) return 0;
        double alpha = Vec.angle(u, new Vec(0, 1));
        u = new Vec(a);
        if (!(Vec.equals(alpha, 0) || Vec.equals(alpha, 2*Math.PI)) || !Vec.equals(u.abs(), 0)) {
            AffineTransform f = AffineTransform.getRotateInstance(alpha);
            f.concatenate(AffineTransform.getTranslateInstance(-u.getX(), -u.getY()));
            return getTransformedInstance(f).countIntersections(arc.getTransformedInstance(f));
        }
        double t = -arc.getC().getX() / arc.getR();
        if (Vec.greaterThanOrEquals(Math.abs(t), 1)) return 0;
        alpha = Math.acos(t);
        CircularArc cut = new CircularArc((Point2D)arc.getC().clone(), arc.getR(), alpha, 2*(Math.PI - alpha));
        Point2D a = cut.getStartPoint(), b = cut.getEndPoint();
        int cnt = 0;
        if (!isEdgePoint(a) && !arc.isEdgePoint(a) && contains(a) && arc.contains(a)) cnt++;
        if (!isEdgePoint(b) && !arc.isEdgePoint(b) && contains(b) && arc.contains(b)) cnt++;
        return cnt;
    }

    @Override
    public final int countIntersections(Curve curve) {
        if (curve instanceof Segment) return pCountIntersections((Segment)curve);
        if (curve instanceof CircularArc) return pCountIntersections((CircularArc)curve);
        System.err.println("Curve c is not a segment or an arc");
        return 0;
    }
}
