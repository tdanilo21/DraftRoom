package raf.draft.dsw.model.structures;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.Renamable;

import java.lang.reflect.Array;

@Getter @Setter
public class Room extends DraftNode implements Renamable {
    private String name;

    public Room(Integer id, String name){
        super(id);
        this.name = name;
    }

    @Override
    public Class<? extends DraftNode>[] getAllowedChildrenTypes() {
        return new Class[]{};
    }
}
