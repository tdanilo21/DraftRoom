package raf.draft.dsw.model.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import raf.draft.dsw.model.nodes.DraftNodeLeaf;

@Getter @AllArgsConstructor
public class Room extends DraftNodeLeaf {
    private String name;
}
