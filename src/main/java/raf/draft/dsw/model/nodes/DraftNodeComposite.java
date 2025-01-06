package raf.draft.dsw.model.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.model.structures.Project;

import java.util.Vector;

@Getter
public abstract class DraftNodeComposite extends DraftNode{
    @JsonProperty("children")
    protected Vector<DraftNode> children;

    public DraftNodeComposite(Vector<DraftNode> children){
        this.children = children;
    }

    public DraftNodeComposite(Integer id) {
        super(id);
        children = new Vector<>();
    }

    public void reconnect(DraftNode child){
        children.remove(child);
        addChild(child, false);
    }

    protected void addChild(DraftNode child, boolean change){
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
        if (change) changed();
        notifySubscribers(EventTypes.CHILD_ADDED, child.getDTO());
    }

    public void addChild(DraftNode child){
        addChild(child, true);
    }

    public void removeChild(DraftNode child){
        children.remove(child);
        child.setParent(null);
        changed();
        notifySubscribers(EventTypes.CHILD_REMOVED, child.getDTO());
    }

    public void removeChildren(){
        Vector<DraftNode> children = new Vector<>(this.children);
        for (DraftNode child : children)
            removeChild(child);
    }

    public boolean hasChildWithName(String name){
        for (DraftNode child : children)
            if (child instanceof Named named && named.getName().equals(name))
                return true;
        return false;
    }

    @Override
    public void getSubtree(Vector<DraftNodeDTO> subtree) {
        super.getSubtree(subtree);
        for (DraftNode child : children)
            child.getSubtree(subtree);
    }

    @Override
    public void save() {
        super.save();
        for (DraftNode child : children)
            child.save();
    }
}
