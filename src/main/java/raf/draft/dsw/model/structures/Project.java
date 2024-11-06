package raf.draft.dsw.model.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;

@Getter @Setter
public class Project extends DraftNodeComposite {
    private String name;
    private String author;
    private String path;

    public Project(String name, String author, String path, DraftNode parent){
        super(parent);
        this.name = name;
        this.author = author;
        this.path = path;
    }
}
