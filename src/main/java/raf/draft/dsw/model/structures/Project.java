package raf.draft.dsw.model.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import raf.draft.dsw.model.nodes.DraftNodeComposite;

@Getter @AllArgsConstructor
public class Project extends DraftNodeComposite {
    private String name;
    private String author;
    private String path;
}
