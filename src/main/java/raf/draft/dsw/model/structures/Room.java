package raf.draft.dsw.model.structures;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.dtos.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.Named;
import raf.draft.dsw.model.structures.room.RoomElement;

import java.awt.*;

@Getter @Setter
public class Room extends DraftNodeComposite implements Named {
    private String name;

    public Room(Integer id, String name){
        super(id);
        this.name = name;
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
}
