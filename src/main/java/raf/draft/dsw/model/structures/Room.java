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
    private double screenW, screenH;
    @Getter
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
        this.screenH = screenH; this.screenW = screenW;
        updateScaleFactor();
    }

    private void updateScaleFactor(){
        scaleFactor = Math.min(screenW / w, screenH / h);
        location = new Point2D.Double((screenW - w * scaleFactor) / 2, (screenH - h * scaleFactor) / 2);
    }

    public void initialize(double w, double h, int screenW, int screenH){
        setW(w); setH(h);
        updateScaleFactor(screenW, screenH);
        nameCounters = new HashMap<>();
        initialized = true;
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    private AffineTransform getTransformFromPixelSpace(){
        AffineTransform f = AffineTransform.getScaleInstance(1 / scaleFactor, 1 / scaleFactor);
        f.concatenate(AffineTransform.getTranslateInstance(-location.getX(), -location.getY()));
        return f;
    }

    public double toPixelSpace(double a){
        return a * scaleFactor;
    }

    public double fromPixelSpace(double a){
        return a / scaleFactor;
    }

    public Point2D toPixelSpace(Point2D a){
        try{
            return getTransformFromPixelSpace().inverseTransform(a, null);
        } catch (NoninvertibleTransformException e){
            System.err.println(e.getMessage());
            return (Point2D)a.clone();
        }
    }

    public Point2D fromPixelSpace(Point2D a){
        return getTransformFromPixelSpace().transform(a, null);
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
    public Point2D getLocationInPixelSpace() {
        return (Point2D)location.clone();
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
        return toPixelSpace(getCenter());
    }

    @Override
    public double getWallWidth() {
        return Room.wallWidth;
    }

    @Override
    public void translate(double dx, double dy) {}

    @Override
    public void rotate(double alpha) {}

    @Override
    public void scaleW(double lambda) {
        w = (w - 2*wallWidth) * lambda + 2*wallWidth;
        updateScaleFactor();
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void scaleH(double lambda) {
        h = (h - 2*wallWidth) * lambda + 2*wallWidth;
        updateScaleFactor();
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void setH(double h) {
        this.h = h + 2*wallWidth;
        updateScaleFactor();
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public void setW(double w) {
        this.w = w + 2*wallWidth;
        updateScaleFactor();
        DraftRoomRepository.getInstance().visualElementEdited(this);
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Double(w/2, h/2);
    }

    @Override
    public boolean contains(Point2D p) {
        SimpleRectangle rectangle = new SimpleRectangle(id, w * scaleFactor, h * scaleFactor, location);
        if (!rectangle.contains(p)) return false;
        rectangle.translate(wallWidth * scaleFactor, wallWidth * scaleFactor);
        rectangle.scaleW(1 - 2*wallWidth/w);
        rectangle.scaleH(1 - 2*wallWidth/h);
        return !rectangle.contains(p);
    }

    @Override
    public boolean containsInPixelSpace(Point2D p) {
        return contains(fromPixelSpace(p));
    }

    @Override
    public boolean intersect(Curve curve) {
        SimpleRectangle rectangle = new SimpleRectangle(id, w * scaleFactor, h * scaleFactor, location);
        if (rectangle.intersect(curve)) return true;
        rectangle.translate(wallWidth * scaleFactor, wallWidth * scaleFactor);
        rectangle.scaleW(1 - 2*wallWidth/w);
        rectangle.scaleH(1 - 2*wallWidth/h);
        return rectangle.intersect(curve);
    }

    @Override
    public boolean overlap(VisualElement element) {
        if (element instanceof RoomElement) return element.overlap(this);
        return false;
    }

    @Override
    public Prototype clone() {
        return null;
    }
}
