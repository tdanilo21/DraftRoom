package raf.draft.dsw.model.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import raf.draft.dsw.model.nodes.DraftNodeComposite;

@Getter @AllArgsConstructor
public class Building extends DraftNodeComposite {
    private String name;
}
