package raf.draft.dsw.model.structures;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.Named;

import java.util.Vector;

@Getter
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
    public void setName(String newName) {
        name = newName;
        notifySubscribers(EventTypes.NODE_EDITED, getDTO());
    }

    public void setAuthor(String newAuthor) {
        author = newAuthor;
        notifySubscribers(EventTypes.NODE_EDITED, getDTO());
    }

    public void setPath(String newPath) {
        path = newPath;
        notifySubscribers(EventTypes.NODE_EDITED, getDTO());
    }

    @Override
    public DraftNodeTypes getNodeType() {
        return DraftNodeTypes.PROJECT;
    }

    @Override
    public Vector<DraftNodeTypes> getAllowedChildrenTypes() {
        Vector<DraftNodeTypes> types = new Vector<>();
        types.add(DraftNodeTypes.BUILDING);
        types.add(DraftNodeTypes.ROOM);
        return types;
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
