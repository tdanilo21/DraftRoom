package raf.draft.dsw.model.structures;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.Named;
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.curves.Curve;
import raf.draft.dsw.model.structures.room.elements.Table;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

import java.awt.*;
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

    private static final double wallWidth = 20;
    private HashMap<VisualElementTypes, Integer> nameCounters;
    @Setter
    private double w, h;
    @Getter
    private double scaleFactor;
    private Point2D location;
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

    public void updateScaleFactor(double screenW, double screenH){
        scaleFactor = Math.min(screenW / w, screenH / h);
        location = new Point2D.Double((screenW - w * scaleFactor) / 2, (screenH - h * scaleFactor) / 2);
    }

    public void initialize(double w, double h, int screenW, int screenH){
        this.w = w; this.h = h;
        updateScaleFactor(screenW, screenH);
        nameCounters = new HashMap<>();
        initialized = true;
    }

    public Vector<VisualElement> getVisualElements(){
        Vector<VisualElement> visualElements = new Vector<>();
        visualElements.add(this);
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
    public Point2D getLocationInPixelSpace() {
        return location;
    }

    @Override
    public double getAngleInPixelSpace() {
        return 0;
    }

    @Override
    public double getWInPixelSpace() {
        return w * scaleFactor;
    }

    @Override
    public double getHInPixelSpace() {
        return h * scaleFactor;
    }

    @Override
    public Point2D getCenterInPixelSpace() {
        Point2D location = getLocationInPixelSpace();
        double w = getWInPixelSpace(), h = getHInPixelSpace();
        return new Point2D.Double(location.getX() + w / 2, location.getY() + h / 2);
    }

    @Override
    public double getWallWidth() {
        return Room.wallWidth;
    }

    @Override
    public void translate(double dx, double dy) {

    }

    @Override
    public void rotate(double alpha) {

    }

    @Override
    public void scaleW(double lambda) {
        w *= lambda;
    }

    @Override
    public void scaleH(double lambda) {
        h *= lambda;
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Double(w/2, h/2);
    }

    @Override
    public boolean inside(Point2D p) {
        Table rectangle = new Table(this, w, h, location, 0, -1);
        if (!rectangle.inside(p)){
            removeChild(rectangle);
            nameCounters.put(VisualElementTypes.TABLE, nameCounters.get(VisualElementTypes.TABLE)-1);
            return false;
        }
        rectangle.translate(wallWidth * scaleFactor, wallWidth * scaleFactor);
        rectangle.scaleW(1 - 2*wallWidth/w);
        rectangle.scaleH(1 - 2*wallWidth/h);
        boolean ret = !rectangle.inside(p);
        removeChild(rectangle);
        nameCounters.put(VisualElementTypes.TABLE, nameCounters.get(VisualElementTypes.TABLE)-1);
        return ret;
    }

    @Override
    public boolean intersect(Curve curve) {
        Table rectangle = new Table(this, w, h, location, 0, -1);
        if (rectangle.intersect(curve)){
            removeChild(rectangle);
            nameCounters.put(VisualElementTypes.TABLE, nameCounters.get(VisualElementTypes.TABLE)-1);
            return true;
        }
        rectangle.translate(wallWidth * scaleFactor, wallWidth * scaleFactor);
        rectangle.scaleW(1 - 2*wallWidth/w);
        rectangle.scaleH(1 - 2*wallWidth/h);
        boolean ret = rectangle.intersect(curve);
        removeChild(rectangle);
        nameCounters.put(VisualElementTypes.TABLE, nameCounters.get(VisualElementTypes.TABLE)-1);
        return ret;
    }

    @Override
    public boolean overlap(VisualElement element) {
        if (element instanceof Wall){
            System.err.println("Detecting overlap between two walls");
            return false;
        }
        return element.overlap(this);
    }
}
