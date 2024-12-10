package raf.draft.dsw.model.structures;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.Named;
import raf.draft.dsw.model.structures.room.RoomElement;

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
}
