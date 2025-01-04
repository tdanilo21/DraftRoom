package raf.draft.dsw.model.nodes;

import lombok.Getter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.observer.EventTypes;

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
        if (!getAllowedChildrenTypes().contains(child.getNodeType())){
            System.err.println("Child type is not compatible with allowed types of parent");
            return;
        }
        if (isAncestor(child)){
            System.err.println("Child cannot be ancestor of parent");
            return;
        }
        DraftNodeComposite oldParent = child.parent;
        if (oldParent != null) oldParent.removeChild(child);
        child.parent = this;
        children.add(child);
        notifySubscribers(EventTypes.CHILD_ADDED, child.getDTO());
    }

    public void removeChild(DraftNode child){
        children.remove(child);
        child.setParent(null);
        notifySubscribers(EventTypes.CHILD_REMOVED, child.getDTO());
    }

    public boolean hasChildWithName(String name){
        for (DraftNode child : children)
            if (child instanceof Named named && named.getName().equals(name))
                return true;
        return false;
    }

    @Override
    public void getSubtree(Vector<DraftNodeDTO> subtree) {
        subtree.add(getDTO());
        for (DraftNode child : children)
            child.getSubtree(subtree);
    }
}
