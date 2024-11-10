package raf.draft.dsw.model.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.Renamable;

@Getter @Setter
public class Project extends DraftNodeComposite implements Renamable {
    private String name;
    private String author;
    private String path;

    public Project(Integer id, String name, String author, String path){
        super(id);
        this.name = name;
        this.author = author;
        this.path = path;
    }

    @Override
    public Class<? extends DraftNode>[] getAllowedChildrenTypes() {
        return new Class[]{Building.class, Room.class};
    }
}
