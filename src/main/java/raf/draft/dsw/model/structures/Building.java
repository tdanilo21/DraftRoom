package raf.draft.dsw.model.structures;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.DraftNodeSubType;
import raf.draft.dsw.model.nodes.Named;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

@Getter @DraftNodeSubType("Building")
public class Building extends DraftNodeComposite implements Named {
    @JsonProperty("name")
    private String name;
    @JsonProperty("color")
    private Color color;

    @JsonCreator
    public Building(@JsonProperty("name") String name, @JsonProperty("color") Color color, @JsonProperty("children") Vector<DraftNode> children){
        super(children);
        this.name = name;
        this.color = color;
    }

    public Building(Integer id, String name){
        super(id);
        this.name = name;
        this.color = new Color(new Random().nextInt(0xFFFFFF));
    }

    @Override
    public void setName(String newName) {
        name = newName;
        changed();
        notifySubscribers(EventTypes.NODE_EDITED, getDTO());
    }

    public void setColor(Color newColor) {
        color = newColor;
        changed();
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
        return new DraftNodeDTO(id, DraftNodeTypes.BUILDING, name, null, color, saved, parentId);
    }

    @Override
    public Building getBuilding() {
        return this;
    }
}
