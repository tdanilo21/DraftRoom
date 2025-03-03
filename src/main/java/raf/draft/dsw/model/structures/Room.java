package raf.draft.dsw.model.structures;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.DraftNodeSubType;
import raf.draft.dsw.model.nodes.Named;
import raf.draft.dsw.model.structures.room.Geometry;
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.SimpleRectangle;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.interfaces.Prototype;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Vector;

@Getter @DraftNodeSubType("Room")
public class Room extends DraftNodeComposite implements Named, Wall {
    @JsonProperty("name")
    private String name;

    @JsonCreator
    public Room(
            @JsonProperty("name") String name,
            @JsonProperty("nameCounters") HashMap<VisualElementTypes, Integer> nameCounters,
            @JsonProperty("rect1") SimpleRectangle rect1,
            @JsonProperty("rect2") SimpleRectangle rect2,
            @JsonProperty("initialized") boolean initialized,
            @JsonProperty("children") Vector<DraftNode> children
    ) {
        super(children);
        this.name = name;
        this.nameCounters = nameCounters;
        this.rect1 = rect1;
        this.rect2 = rect2;
        this.initialized = initialized;
    }

    public Room(Integer id, String name){
        super(id);
        this.name = name;
        this.initialized = false;
    }

    @Override
    public void load(Integer id) {
        super.load(id);
        rect1.setRoomId(id);
        rect2.setRoomId(id);
    }

    @Override
    public void setName(String newName) {
        name = newName;
        changed();
        notifySubscribers(EventTypes.NODE_EDITED, getDTO());
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
        return new DraftNodeDTO(id, DraftNodeTypes.ROOM, name, null, getColor(), saved, parentId);
    }

    private static final double wallWidth = 10;
    @JsonProperty("nameCounters")
    private HashMap<VisualElementTypes, Integer> nameCounters;
    @JsonProperty("rect1")
    private SimpleRectangle rect1;
    @JsonProperty("rect2")
    private SimpleRectangle rect2;
    @JsonProperty("initialized")
    private boolean initialized;

    @Override
    public void addChild(DraftNode child){
        if (child instanceof RoomElement roomElement) {
            VisualElementTypes type = roomElement.getVisualElementType();
            nameCounters.putIfAbsent(type, 1);
            Integer index = nameCounters.get(type);
            nameCounters.replace(type, index+1);
            roomElement.setName(STR."\{type.toString()} \{index}");
        }
        super.addChild(child);
    }

    public void initialize(double w, double h){
        w += 2*wallWidth; h += 2*wallWidth;
        rect1 = new SimpleRectangle(id, w, h, new Point2D.Double(0, 0));
        rect2 = new SimpleRectangle(id, w - 2*wallWidth, h - 2*wallWidth, new Point2D.Double(wallWidth, wallWidth));
        nameCounters = new HashMap<>();
        initialized = true;
        changed();
        notifySubscribers(EventTypes.ROOM_DIMENSIONS_CHANGED, null);
        notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
    }

    public Vector<VisualElement> getVisualElements(){
        Vector<VisualElement> visualElements = new Vector<>();
        if (initialized) visualElements.add(this);
        for (DraftNode child : children)
            if (child instanceof VisualElement e && child.getId() != null)
                visualElements.add(e);
        return visualElements;
    }

    public boolean overlaps(){
        Vector<VisualElement> elements = getVisualElements();
        for (int i = 0; i < elements.size(); i++)
            for (int j = i+1; j < elements.size(); j++)
                if (Geometry.overlaps(elements.get(i), elements.get(j)))
                    return true;
        return false;
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
    public double getAngle() {
        return 0;
    }

    @Override
    public Point2D getLocation() {
        return new Point2D.Double(0, 0);
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
        rect1.setH(h + 2*wallWidth);
        rect2.setH(h);
        changed();
        notifySubscribers(EventTypes.ROOM_DIMENSIONS_CHANGED, null);
        notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
    }

    @Override
    public void setW(double w) {
        rect1.setW(w + 2*wallWidth);
        rect2.setW(w);
        changed();
        notifySubscribers(EventTypes.ROOM_DIMENSIONS_CHANGED, null);
        notifySubscribers(EventTypes.VISUAL_ELEMENT_EDITED, null);
    }

    @Override
    public Prototype clone() {
        return null;
    }
}
