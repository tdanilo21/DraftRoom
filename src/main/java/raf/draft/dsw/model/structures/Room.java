package raf.draft.dsw.model.structures;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.Named;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.SimpleRectangle;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.elements.Table;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Vector;

public class Room extends DraftNodeComposite implements Named, Wall {
    @Getter @Setter
    private String name;

    public Room(Integer id, String name){
        super(id);
        this.name = name;
        this.initialized = false;
    }

    @Override
    public DraftNodeTypes getNodeType() {
        return DraftNodeTypes.ROOM;
    }

    @Override
    public Vector<DraftNodeTypes> getAllowedChildrenTypes() {
        Vector<DraftNodeTypes> types = new Vector<>();
        types.add(DraftNodeTypes.ROOM_ELEMENT);
        return types;
    }

    @Override
    public Color getColor() {
        if (parent != null) return parent.getColor();
        return null;
    }

    @Override
    public DraftNodeDTO getDTO() {
        Integer parentId = (parent == null ? null : parent.getId());
        return new DraftNodeDTO(id, DraftNodeTypes.ROOM, name, null, getColor(), parentId);
    }

    private static final double wallWidth = 10;
    private HashMap<VisualElementTypes, Integer> nameCounters;
    private SimpleRectangle rect1, rect2;
    @Getter
    private boolean initialized;

    @Override
    public void addChild(DraftNode child){
        super.addChild(child);
        if (child instanceof RoomElement roomElement) {
            VisualElementTypes type = roomElement.getVisualElementType();
            nameCounters.putIfAbsent(type, 1);
            Integer index = nameCounters.get(type);
            nameCounters.replace(type, index+1);
            roomElement.setName(STR."\{type.toString()} \{index}");
        }
    }

    public void initialize(double w, double h){
        rect1 = new SimpleRectangle(id, w, h, new Point2D.Double(0, 0));
        rect2 = new SimpleRectangle(id, w - 2*wallWidth, h - 2*wallWidth, new Point2D.Double(wallWidth, wallWidth));
        nameCounters = new HashMap<>();
        initialized = true;
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    public Vector<VisualElement> getVisualElements(){
        Vector<VisualElement> visualElements = new Vector<>();
        if (initialized) visualElements.add(this);
        for (DraftNode child : children)
            if (child instanceof VisualElement e)
                visualElements.add(e);
        return visualElements;
    }

    @Override
    public VisualElementTypes getVisualElementType() {
        return VisualElementTypes.WALL;
    }

    @Override
    public Integer getRoomId() {
        return id;
    }

    @Override
    public AffineTransform getTransform() {
        return null;
    }

    @Override
    public void translate(double dx, double dy) {

    }

    @Override
    public void rotate(double alpha) {

    }

    @Override
    public void rotate(double alpha, Point2D p) {

    }

    @Override
    public void scale(Point2D p, double sx, double sy) {

    }

    @Override
    public Point2D getInternalPoint() {
        Point2D p = rect1.getLocation();
        return new Point2D.Double(p.getX() + wallWidth / 2, p.getY() + wallWidth / 2);
    }

    @Override
    public Vector<Point2D> getVertexes() {
        Vector<Point2D> vertexes = rect1.getVertexes();
        vertexes.addAll(rect2.getVertexes());
        return vertexes;
    }

    @Override
    public Vector<Curve> getEdgeCurves() {
        Vector<Curve> curves = rect1.getEdgeCurves();
        curves.addAll(rect2.getEdgeCurves());
        return curves;
    }

    @Override
    public double getWallWidth() {
        return wallWidth;
    }

    @Override
    public double getW() {
        return rect1.getW();
    }

    @Override
    public double getH() {
        return rect1.getH();
    }

    @Override
    public void setH(double h) {
        rect1.setH(h);
        rect2.setH(h - 2*wallWidth);
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void setW(double w) {
        rect1.setW(w);
        rect2.setW(w - 2*wallWidth);
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public Prototype clone() {
        return null;
    }
}
