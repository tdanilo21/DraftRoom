package raf.draft.dsw.model.structures;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.dtos.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.Named;

@Getter @Setter
public class Project extends DraftNodeComposite implements Named {
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

    @Override
    public DraftNodeDTO getDTO() {
        Integer parentId = (parent == null ? null : parent.getId());
        return new DraftNodeDTO(id, DraftNodeTypes.PROJECT, name, author, null, parentId);
    }

    @Override
    public Project getProject() {
        return this;
    }
}
