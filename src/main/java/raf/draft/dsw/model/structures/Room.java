package raf.draft.dsw.model.structures;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.dtos.DraftNodeTypes;
import raf.draft.dsw.controller.dtos.VisualElementTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.Named;
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Vector;

public class Room extends DraftNodeComposite implements Named, RectangularVisualElement {
    @Getter @Setter
    private String name;

    public Room(Integer id, String name){
        super(id);
        this.name = name;
        this.initialized = false;
    }

    @Override
    public Class<? extends DraftNode>[] getAllowedChildrenTypes() {
        return new Class[]{RoomElement.class};
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

    private HashMap<VisualElementTypes, Integer> nameCounters;
    private float w, h;
    @Getter
    private float scaleFactor;
    @Getter
    private Point2D location;
    @Getter
    private boolean initialized;

    @Override
    public void addChild(DraftNode child){
        super.addChild(child);
        if (child instanceof RoomElement roomElement) {
            VisualElementTypes type = roomElement.getType();
            nameCounters.putIfAbsent(type, 1);
            Integer index = nameCounters.get(type);
            nameCounters.replace(type, index+1);
            roomElement.setName(STR."\{type.toString()} \{index}");
        }
    }

    public void updateScaleFactor(float screenW, float screenH){
        scaleFactor = Math.min(screenW / w, screenH / h);
        location = new Point2D.Float((screenW - w * scaleFactor) / 2, (screenH - h * scaleFactor) / 2);
    }

    public void initialize(int w, int h, int screenW, int screenH){
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
    public VisualElementTypes getType() {
        return VisualElementTypes.WALL;
    }

    @Override
    public Point2D getLocationInPixelSpace() {
        return location;
    }

    @Override
    public float getAngle() {
        return 0;
    }

    @Override
    public float getWInPixelSpace() {
        return w * scaleFactor;
    }

    @Override
    public float getHInPixelSpace() { return h * scaleFactor; }

    @Override
    public Point2D getCenterInPixelSpace() {
        Point2D location = getLocationInPixelSpace();
        float w = getWInPixelSpace(), h = getHInPixelSpace();
        return new Point2D.Double(location.getX() + w / 2, location.getY() + h / 2);
    }

    @Override
    public void translate(float dx, float dy) {

    }

    @Override
    public void rotate(float alpha) {

    }

    @Override
    public void scaleW(float lambda) {
        w *= lambda;
    }

    @Override
    public void scaleH(float lambda) {
        h *= lambda;
    }
}
