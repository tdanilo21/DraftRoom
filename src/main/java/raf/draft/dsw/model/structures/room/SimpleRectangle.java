package raf.draft.dsw.model.structures.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.curves.Segment;
import raf.draft.dsw.model.structures.room.curves.Vec;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

@Getter
public class SimpleRectangle implements RectangularVisualElement {

    public static final int DOWN = 1;
    public static final int RIGHT = 2;
    public static final int UP = 4;
    public static final int LEFT = 8;

    @Setter
    private Integer roomId;
    @JsonProperty("location")
    private Point2D location;
    @JsonProperty("w")
    private double w;
    @JsonProperty("h")
    private double h;

    @JsonCreator
    public SimpleRectangle(@JsonProperty("location") Point2D location, @JsonProperty("w") double w, @JsonProperty("h") double h){
        this.location = location;
        this.w = w;
        this.h = h;
    }

    public SimpleRectangle(Integer roomId, double w, double h, Point2D location) {
        this.roomId = roomId;
        this.location = (Point2D) location.clone();
        this.w = w;
        this.h = h;
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return null;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public AffineTransform getTransform() {
        return null;
    }

    private boolean containsVertical(double x, double y0, double y1, Point2D p, double margin){
        SimpleRectangle rect = new SimpleRectangle(roomId, 2*margin, y1-y0 + 2*margin, new Point2D.Double(x-margin, y0-margin));
        return Geometry.contains(rect, p);
    }

    private boolean containsHorizontal(double x0, double x1, double y, Point2D p, double margin){
        SimpleRectangle rect = new SimpleRectangle(roomId, x1-x0 + 2*margin, 2*margin, new Point2D.Double(x0-margin, y-margin));
        return Geometry.contains(rect, p);
    }

    public int getEdges(Point2D p, double margin){
        double x0 = location.getX(), y0 = location.getY();
        double x1 = x0 + w, y1 = y0 + h;
        int edges = 0;
        if (containsHorizontal(x0, x1, y0, p, margin)) edges |= DOWN;
        if (containsVertical(x1, y0, y1, p, margin)) edges |= RIGHT;
        if (containsHorizontal(x0, x1, y1, p, margin)) edges |= UP;
        if (containsVertical(x0, y0, y1, p, margin)) edges |= LEFT;
        return edges;
    }

    public Point2D getCenter(){
        return new Point2D.Double(location.getX() + w/2, location.getY() + h/2);
    }

    @Override
    public double getAngle() {
        return 0;
    }

    @Override
    public void translate(double dx, double dy) {
        location = new Point2D.Double(location.getX() + dx, location.getY() + dy);
        DraftRoomRepository.getInstance().getNodeObject(roomId).notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
    }

    private void rotate90(Point2D p){
        location = new Point2D.Double(location.getX() - p.getX(), location.getY() - p.getY());
        location = new Point2D.Double(-location.getY()-h, location.getX());
        double t = w; w = h; h = t;
        location = new Point2D.Double(location.getX() + p.getX(), location.getY() + p.getY());
    }

    @Override
    public void rotate(double alpha) {
        rotate(alpha, getCenter());
    }

    @Override
    public void rotate(double alpha, Point2D p) {
        double k = 2*alpha / Math.PI;
        if (k == (int)k){
            int l = (((int)k % 4) + 4) % 4;
            while (l > 0){
                rotate90(p);
                l--;
            }
            DraftRoomRepository.getInstance().getNodeObject(roomId).notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
        }
    }

    @Override
    public void scale(Point2D p, double sx, double sy) {
        if (p.equals(location)) {
            w *= sx;
            h *= sy;
            DraftRoomRepository.getInstance().getNodeObject(roomId).notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
        }
    }

    @Override
    public Point2D getInternalPoint() {
        return getCenter();
    }

    @Override
    public Vector<Point2D> getVertexes() {
        Vector<Point2D> vertexes = new Vector<>();
        vertexes.add(new Point2D.Double(location.getX(), location.getY()));
        vertexes.add(new Point2D.Double(location.getX()+w, location.getY()));
        vertexes.add(new Point2D.Double(location.getX()+w, location.getY()+h));
        vertexes.add(new Point2D.Double(location.getX(), location.getY()+h));
        return vertexes;
    }

    @Override
    public Vector<Curve> getEdgeCurves() {
        Vector<Point2D> vertexes = getVertexes();
        Vector<Curve> curves = new Vector<>();
        for (int i = 0; i < vertexes.size(); i++)
            curves.add(new Segment((Point2D)vertexes.get(i).clone(), (Point2D)vertexes.get((i+1) % vertexes.size()).clone()));
        return curves;
    }

    @Override
    public void setH(double h) {
        this.h = h;
        DraftRoomRepository.getInstance().getNodeObject(roomId).notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
    }

    @Override
    public void setW(double w) {
        this.w = w;
        DraftRoomRepository.getInstance().getNodeObject(roomId).notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
    }

    @Override
    public Prototype clone() {
        return new SimpleRectangle(roomId, w, h, location);
    }
}
