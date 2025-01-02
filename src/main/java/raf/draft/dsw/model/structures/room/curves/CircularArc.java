package raf.draft.dsw.model.structures.room.curves;

import lombok.Getter;
import lombok.Setter;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

@Getter @Setter
public class CircularArc implements Curve {

    private Point2D c;
    private double r, startAngle, sweepAngle;

    public CircularArc(Point2D c, double r, double startAngle, double sweepAngle){
        this.c = c;
        this.r = r;
        this.startAngle = startAngle;
        this.sweepAngle = sweepAngle;
    }

    public Point2D getStartPoint(){
        Point2D startPoint = new Point2D.Double(c.getX() + r, c.getY());
        return AffineTransform.getRotateInstance(startAngle, c.getX(), c.getY()).transform(startPoint, null);
    }

    public Point2D getEndPoint(){
        Point2D startPoint = new Point2D.Double(c.getX() + r, c.getY());
        return AffineTransform.getRotateInstance(startAngle + sweepAngle, c.getX(), c.getY()).transform(startPoint, null);
    }

    public Point2D getMiddlePoint(){
        Point2D startPoint = new Point2D.Double(c.getX() + r, c.getY());
        return AffineTransform.getRotateInstance(startAngle + sweepAngle / 2, c.getX(), c.getY()).transform(startPoint, null);
    }

    @Override
    public void transform(AffineTransform f) {
        Segment s = new Segment(new Point2D.Double(0, 0), new Point2D.Double(1, 0));
        s.transform(f);
        double scaleFactor = s.getA().distance(s.getB());
        Point2D middlePoint = getMiddlePoint();
        f.transform(c, c);
        f.transform(middlePoint, middlePoint);
        r *= scaleFactor;
        startAngle = Vec.angle(new Vec(1, 0), new Vec(c, middlePoint)) - sweepAngle / 2;
        if (startAngle < 0) startAngle += 2*Math.PI;
    }

    @Override
    public Curve getTransformedInstance(AffineTransform f) {
        CircularArc a = new CircularArc((Point2D)c.clone(), r, startAngle, sweepAngle);
        a.transform(f);
        return a;
    }

    @Override
    public boolean isEdgePoint(Point2D p){
        if (Vec.equals(sweepAngle, 2*Math.PI)) return false;
        return Vec.equals(getStartPoint(), p) || Vec.equals(getEndPoint(), p);
    }

    public CircularArc getComplement(){
        return new CircularArc((Point2D)c.clone(), r, startAngle + sweepAngle, 2*Math.PI - sweepAngle);
    }

    public boolean contains(Point2D p){
        if (!Vec.equals(c.distance(p), r)) return false;
        if (Vec.equals(sweepAngle, 2*Math.PI)) return true;
        if (sweepAngle > Math.PI) return !getComplement().contains(p);
        Vec u = new Vec(c, getStartPoint()), v = new Vec(c, p), w = new Vec(c, getEndPoint());
        return Vec.orientation(u, v) != Vec.CLOCKWISE && Vec.orientation(v, w) != Vec.CLOCKWISE;
    }

    private int pCountIntersections(CircularArc arc){
        double d = c.distance(arc.c);
        if (d >= r + arc.r) return 0;
        Vec u = new Vec(c);
        double lambda = 1 / r;
        if (!Vec.equals(u.abs(), 0) || !Vec.equals(lambda, 1)) {
            AffineTransform f = AffineTransform.getScaleInstance(lambda, lambda);
            f.concatenate(AffineTransform.getTranslateInstance(-u.getX(), -u.getY()));
            return getTransformedInstance(f).countIntersections(arc.getTransformedInstance(f));
        }
        u = new Vec(arc.c);
        if (Vec.equals(u.abs(), 0)) return 0;
        double t = (1 - u.abs() * u.abs() - arc.r * arc.r) / (2 * u.abs() * arc.r);
        if (Vec.greaterThanOrEquals(Math.abs(t), 1)) return 0;
        double alpha = Math.acos(t), beta = Vec.angle(new Vec(1, 0), u);
        CircularArc cut = new CircularArc((Point2D)arc.c.clone(), arc.r, alpha + beta, 2 * (Math.PI - alpha));
        Point2D a = cut.getStartPoint(), b = cut.getEndPoint();
        int cnt = 0;
        if (!isEdgePoint(a) && !arc.isEdgePoint(a) && contains(a) && arc.contains(a)) cnt++;
        if (!isEdgePoint(b) && !arc.isEdgePoint(b) && contains(b) && arc.contains(b)) cnt++;
        return cnt;
    }

    @Override
    public int countIntersections(Curve curve) {
        if (curve instanceof Segment) return curve.countIntersections(this);
        if (curve instanceof CircularArc) return pCountIntersections((CircularArc)curve);
        System.err.println("Curve c is not a segment or an arc");
        return 0;
    }

    @Override
    public double getMinX() {
        if ((startAngle < Math.PI && startAngle + sweepAngle > Math.PI) || startAngle + sweepAngle > 3*Math.PI)
            return c.getX() - r;
        return Math.min(getStartPoint().getX(), getEndPoint().getX());
    }

    @Override
    public double getMaxX() {
        if (startAngle + sweepAngle > 2*Math.PI)
            return c.getX() + r;
        return Math.max(getStartPoint().getX(), getEndPoint().getX());
    }

    @Override
    public double getMinY() {
        if ((startAngle < Math.PI/2 && startAngle + sweepAngle > Math.PI/2) || startAngle + sweepAngle > 5*Math.PI/2)
            return c.getY() - r;
        return Math.min(getStartPoint().getY(), getEndPoint().getY());
    }

    @Override
    public double getMaxY() {
        if ((startAngle < 3*Math.PI/2 && startAngle + sweepAngle > 2*Math.PI/2) || startAngle + sweepAngle > 7*Math.PI/2)
            return c.getY() + r;
        return Math.max(getStartPoint().getY(), getEndPoint().getY());
    }
}
