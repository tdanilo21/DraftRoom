package raf.draft.dsw.model.structures;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.Named;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

@Getter
public class Building extends DraftNodeComposite implements Named {
    private String name;
    private Color color;

    public Building(Integer id, String name){
        super(id);
        this.name = name;
        this.color = new Color(new Random().nextInt(0xFFFFFF));
    }

    @Override
    public void setName(String newName) {
        name = newName;
        notifySubscribers(EventTypes.NODE_EDITED, getDTO());
    }

    public void setColor(Color newColor) {
        color = newColor;
        notifySubscribers(EventTypes.NODE_EDITED, getDTO());
    }

    @Override
    public DraftNodeTypes getNodeType() {
        return DraftNodeTypes.BUILDING;
    }

    @Override
    public Vector<DraftNodeTypes> getAllowedChildrenTypes() {
        Vector<DraftNodeTypes> types = new Vector<>();
        types.add(DraftNodeTypes.ROOM);
        return types;
    }

    @Override
    public DraftNodeDTO getDTO() {
        Integer parentId = (parent == null ? null : parent.getId());
        return new DraftNodeDTO(id, DraftNodeTypes.BUILDING, name, null, color, parentId);
    }

    @Override
    public Building getBuilding() {
        return this;
    }
}
