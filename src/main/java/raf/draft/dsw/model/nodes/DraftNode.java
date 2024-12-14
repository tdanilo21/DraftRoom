package raf.draft.dsw.model.nodes;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;

import java.awt.*;
import java.util.Vector;

@Getter
public abstract class DraftNode {
    protected Integer id;
    @Setter
    protected DraftNodeComposite parent;

    public DraftNode(Integer id){
        this.id = id;
    }

    public abstract DraftNodeTypes getNodeType();

    public Vector<DraftNodeTypes> getAllowedChildrenTypes(){
        return new Vector<>();
    }

    public boolean isAncestor(DraftNode ancestor) {
        if (ancestor == null || parent == null) return false;
        if (parent.equals(ancestor)) return true;
        return parent.isAncestor(ancestor);
    }

    public boolean isReadOnly(){
        return false;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id.equals(((DraftNode)o).id);
    }

    public Color getColor(){
        return null;
    }

    public abstract DraftNodeDTO getDTO();

    public void getSubtree(Vector<DraftNodeDTO> subtree){
        subtree.add(getDTO());
    }

    public Project getProject(){
        if (parent == null) return null;
        return parent.getProject();
    }

    public Building getBuilding(){
        if (parent == null) return null;
        return parent.getBuilding();
    }
}
