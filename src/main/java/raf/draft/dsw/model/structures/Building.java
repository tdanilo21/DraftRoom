package raf.draft.dsw.model.structures;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.dtos.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.Named;

import java.awt.*;
import java.util.Random;

@Getter @Setter
public class Building extends DraftNodeComposite implements Named {
    private String name;
    private Color color;

    public Building(Integer id, String name){
        super(id);
        this.name = name;
        this.color = new Color(new Random().nextInt(0xFFFFFF));
    }

    @Override
    public Class<? extends DraftNode>[] getAllowedChildrenTypes() {
        return new Class[]{Room.class};
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
