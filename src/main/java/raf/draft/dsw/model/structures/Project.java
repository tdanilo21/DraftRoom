package raf.draft.dsw.model.structures;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.DraftNodeSubType;
import raf.draft.dsw.model.nodes.Named;

import java.util.Vector;

@Getter @DraftNodeSubType("Project")
public class Project extends DraftNodeComposite implements Named {
    @JsonProperty("name")
    private String name;
    @JsonProperty("author")
    private String author;
    private String path;

    @JsonCreator
    public Project(@JsonProperty("name") String name, @JsonProperty("author") String author, @JsonProperty("children") Vector<DraftNode> children){
        super(children);
        this.name = name;
        this.author = author;
        this.saved = true;
    }

    public Project(Integer id, String name, String author, String path){
        super(id);
        this.name = name;
        this.author = author;
        this.path = path;
    }

    @Override
    public void setName(String newName) {
        name = newName;
        changed();
        notifySubscribers(EventTypes.NODE_EDITED, getDTO());
    }

    public void setAuthor(String newAuthor) {
        author = newAuthor;
        changed();
        notifySubscribers(EventTypes.NODE_EDITED, getDTO());
    }

    public void setPath(String newPath) {
        path = newPath;
        changed();
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
        return new DraftNodeDTO(id, DraftNodeTypes.PROJECT, name, author, null, saved, parentId);
    }

    @Override
    public Project getProject() {
        return this;
    }
}
