package raf.draft.dsw.model.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Getter @JsonTypeInfo(use = NAME, include = PROPERTY)
public abstract class DraftNode implements IPublisher {
    protected Integer id;
    @Setter
    protected DraftNodeComposite parent;
    protected HashMap<EventTypes, Vector<ISubscriber> > subscribers;
    protected boolean saved;

    public DraftNode(){
        subscribers = new HashMap<>();
        saved = true;
    }

    public DraftNode(Integer id){
        this.id = id;
        subscribers = new HashMap<>();
        saved = false;
    }

    public void load(Integer id){
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
        if (id != null) subtree.add(getDTO());
    }

    public Project getProject(){
        if (parent == null) return null;
        return parent.getProject();
    }

    public Building getBuilding(){
        if (parent == null) return null;
        return parent.getBuilding();
    }

    public void changed(){
        saved = false;
        notifySubscribers(EventTypes.NODE_SAVED_CHANGED, getDTO());
        if (!(this instanceof Project) && parent != null)
            parent.changed();
    }

    public void save(){
        saved = true;
        notifySubscribers(EventTypes.NODE_SAVED_CHANGED, getDTO());
    }

    @Override
    public void addSubscriber(ISubscriber subscriber, EventTypes... types) {
        for (EventTypes type : types) {
            subscribers.putIfAbsent(type, new Vector<>());
            subscribers.get(type).add(subscriber);
        }
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber, EventTypes... types) {
        for (EventTypes type : types)
            if (subscribers.get(type) != null)
                subscribers.get(type).remove(subscriber);
    }

    @Override
    public void notifySubscribers(EventTypes type, Object state) {
        if (subscribers.get(type) != null)
            for (ISubscriber subscriber : subscribers.get(type))
                subscriber.notify(type, state);
    }
}
