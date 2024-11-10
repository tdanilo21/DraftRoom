package raf.draft.dsw.model.nodes;

import lombok.Getter;

import java.util.Arrays;
import java.util.Vector;

@Getter
public abstract class DraftNodeComposite extends DraftNode{
    protected Vector<DraftNode> children;

    public DraftNodeComposite(Integer id) {
        super(id);
        children = new Vector<>();
    }

    public void addChild(DraftNode child){
        if (child == null){
            System.err.println("Child is null");
            return;
        }
        if (!Arrays.stream(getAllowedChildrenTypes()).toList().contains(child.getClass())){
            System.err.println("Child type is not compatable with allowed types of parent");
            return;
        }
        if (isAncestor(child)){
            System.err.println("Child cannot be ancestor of parent");
            return;
        }
        DraftNodeComposite oldParent = (DraftNodeComposite)child.parent;
        if (oldParent != null) oldParent.removeChild(child);
        child.parent = this;
        children.add(child);
    }

    public void removeChild(DraftNode child){
        children.remove(child);
    }
}
