package raf.draft.dsw.model.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class DraftNode {
    protected Integer id;
    @Setter
    protected DraftNodeComposite parent;

    public DraftNode(Integer id){
        this.id = id;
    }

    public abstract Class<? extends DraftNode>[] getAllowedChildrenTypes();

    public boolean isAncestor(DraftNode ancestor) {
        if (parent == null) return false;
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
