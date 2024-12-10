package raf.draft.dsw.model.nodes;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.structures.Project;

@Getter
public abstract class DraftNode {
    protected Integer id;
    @Setter
    protected DraftNodeComposite parent;

    public DraftNode(Integer id){
        this.id = id;
    }

    public Class<? extends DraftNode>[] getAllowedChildrenTypes(){
        return new Class[]{};
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
}
