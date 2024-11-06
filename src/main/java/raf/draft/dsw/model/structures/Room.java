package raf.draft.dsw.model.structures;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;

@Getter @Setter
public class Room extends DraftNode {
    private String name;

    public Room(String name, DraftNode parent){
        super(parent);
        this.name = name;
    }
}
