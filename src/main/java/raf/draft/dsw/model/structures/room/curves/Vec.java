package raf.draft.dsw.model.structures.room.curves;

import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;

@Getter @Setter
public class Vec {

    public static final int COUNTER_CLOCKWISE = 0;
    public static final int CLOCKWISE = 1;
    public static final int COLLINEAR = 2;
    private static final double E = 1e-9;

    static boolean equals(double a, double b){
        return Math.abs(a - b) < E;
    }

    static boolean greaterThanOrEquals(double a, double b){
        return a > b || equals(a, b);
    }

    static boolean equals(Point2D a, Point2D b){
        return equals(a.getX(), b.getX()) && equals(a.getY(), b.getY());
    }

    private double x, y;

    public Vec(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vec(Point2D a, Point2D b){
        x = b.getX() - a.getX();
        y = b.getY() - a.getY();
    }

    public Vec(Point2D a){
        x = a.getX();
        y = a.getY();
    }

    public double abs(){
        return Math.sqrt(x*x + y*y);
    }

    public static double dot(Vec v, Vec u){
        return v.x * u.x + v.y * u.y;
    }

    public static int orientation(Vec v, Vec u){
        double w = v.x * u.y - v.y * u.x;
        if (equals(w, 0)) return COLLINEAR;
        if (w > 0) return COUNTER_CLOCKWISE;
        if (w < 0) return CLOCKWISE;
        /* NOTREACHED */
        return COLLINEAR;
    }

    public static double angle(Vec v, Vec u){
        if (equals(u.abs() * v.abs(), 0)){
            System.err.println("Zero vector given");
            return 0;
        }
        double dp = dot(v, u) / (u.abs() * v.abs());
        double angle = Math.acos(dp);
        if (orientation(v, u) == CLOCKWISE) angle = 2*Math.PI - angle;
        return angle;
    }
}
