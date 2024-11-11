package raf.draft.dsw.model.repository;

import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.dtos.DraftNodeTypes;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.Named;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

class ProjectExplorerFactory implements DraftNodeFactory{
    @Override
    public DraftNode createNode(Integer id, String... parameters){
        return new ProjectExplorer(id);
    }
}

class ProjectFactory implements DraftNodeFactory{
    @Override
    public DraftNode createNode(Integer id, String... parameters){
        return new Project(id, parameters[0], parameters[1], parameters[2]);
    }
}

class BuildingFactory implements DraftNodeFactory{
    @Override
    public DraftNode createNode(Integer id, String... parameters){
        return new Building(id, parameters[0]);
    }
}

class RoomFactory implements DraftNodeFactory{
    @Override
    public DraftNode createNode(Integer id, String... parameters){
        return new Room(id, parameters[0]);
    }
}

public class DraftRoomRepository implements IPublisher {
    private HashMap<Integer, DraftNode> nodes;
    private Integer K;
    private final ProjectExplorerFactory projectExplorerFactory;
    private final ProjectFactory projectFactory;
    private final BuildingFactory buildingFactory;
    private final RoomFactory roomFactory;
    private HashMap<EventTypes, Vector<ISubscriber> > subscribers;


    public DraftRoomRepository(){
        nodes = new HashMap<>();
        K = 0;

        projectExplorerFactory = new ProjectExplorerFactory();
        projectFactory = new ProjectFactory();
        buildingFactory = new BuildingFactory();
        roomFactory = new RoomFactory();

        subscribers = new HashMap<>();

        createNode(DraftNodeTypes.PROJECT_EXPLORER);
    }

    @Override
    public void addSubscriber(ISubscriber subscriber, EventTypes... types) {
        for (EventTypes type : types) {
            subscribers.computeIfAbsent(type, k -> new Vector<>());
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

    public boolean hasChildWithName(Integer parentId, String name){
        DraftNode node = nodes.get(parentId);
        if (node instanceof DraftNodeComposite parent)
            for (DraftNode child : parent.getChildren())
                if (child instanceof Named named && named.getName().equals(name))
                    return true;
        return false;
    }

    public boolean hasSiblingWithName(Integer id, String name){
        DraftNodeComposite parent = nodes.get(id).getParent();
        return parent != null && hasChildWithName(parent.getId(), name);
    }

    public Vector<Integer> getChildrenIds(Integer id){
        DraftNode node = nodes.get(id);
        Vector<Integer> ids = new Vector<>();
        if (node instanceof DraftNodeComposite nodeComposite){
            for (DraftNode child : nodeComposite.getChildren())
                ids.add(child.getId());
        }
        return ids;
    }

    private Integer getParentId(DraftNode node){
        if (node.getParent() != null)
            return node.getParent().getId();
        return null;
    }

    private Color getColor(DraftNode node){
        if (node != null && node.getParent() instanceof Building)
            return ((Building) node.getParent()).getColor();
        return null;
    }

    private DraftNodeDTO createDraftNodeDTO(DraftNode node){
        if (node instanceof ProjectExplorer)
            return new DraftNodeDTO(node.getId(), DraftNodeTypes.PROJECT_EXPLORER, "ProjectExplorer", null, null, null);
        if (node instanceof Project)
            return new DraftNodeDTO(node.getId(), DraftNodeTypes.PROJECT, ((Project) node).getName(), ((Project) node).getAuthor(), null, getParentId(node));
        if (node instanceof Building)
            return new DraftNodeDTO(node.getId(), DraftNodeTypes.BUILDING, ((Building) node).getName(), null, ((Building) node).getColor(), getParentId(node));
        if (node instanceof Room)
            return new DraftNodeDTO(node.getId(), DraftNodeTypes.ROOM, ((Room) node).getName(), null, getColor(node), getParentId(node));
        return null;
    }

    public DraftNodeDTO createNode(DraftNodeTypes type, String... parameters){
        DraftNode node = switch (type) {
            case DraftNodeTypes.PROJECT_EXPLORER -> projectExplorerFactory.createNode(K, parameters);
            case DraftNodeTypes.PROJECT -> projectFactory.createNode(K, parameters);
            case DraftNodeTypes.BUILDING -> buildingFactory.createNode(K, parameters);
            case DraftNodeTypes.ROOM -> roomFactory.createNode(K, parameters);
        };
        if (node == null) return null;
        if (node instanceof Named namedNode && node.getParent() != null && hasChildWithName(node.getParent().getId(), namedNode.getName()))
            return null;
        K++;
        nodes.put(node.getId(), node);
        return createDraftNodeDTO(node);
    }

    public Vector<DraftNodeTypes> getAllowedChildrenTypes(Integer id){
        Class<? extends DraftNode>[] classes = nodes.get(id).getAllowedChildrenTypes();
        Vector<DraftNodeTypes> types = new Vector<>();
        if (Arrays.stream(classes).toList().contains(ProjectExplorer.class)) types.add(DraftNodeTypes.PROJECT_EXPLORER);
        if (Arrays.stream(classes).toList().contains(Project.class)) types.add(DraftNodeTypes.PROJECT);
        if (Arrays.stream(classes).toList().contains(Building.class)) types.add(DraftNodeTypes.BUILDING);
        if (Arrays.stream(classes).toList().contains(Room.class)) types.add(DraftNodeTypes.ROOM);
        return types;
    }

    public void addChild(Integer parentId, Integer id){
        DraftNode parent = nodes.get(parentId), child = nodes.get(id);
        if (parent instanceof DraftNodeComposite) {
            boolean newNode = child.getParent() == null;
            ((DraftNodeComposite) parent).addChild(child);
            if (newNode) notifySubscribers(EventTypes.NODE_CREATED, createDraftNodeDTO(child));
            else notifySubscribers(EventTypes.NODE_EDITED, createDraftNodeDTO(child));
        }
    }

    public DraftNodeDTO getNode(Integer id){
        return createDraftNodeDTO(nodes.get(id));
    }

    public DraftNodeDTO getRoot(){
        return createDraftNodeDTO(nodes.get(0));
    }

    public boolean isReadOnly(Integer id){
        return nodes.get(id).isReadOnly();
    }

    public boolean cannotChangeAuthor(Integer id){
        return !(nodes.get(id) instanceof Project);
    }

    public boolean cannotChangePath(Integer id){
        return !(nodes.get(id) instanceof Project);
    }

    private void removeNode(DraftNode node){
        if (node == null){
            System.err.println("Node is null");
            return;
        }
        if (node.isReadOnly()){
            System.err.println("Node is read-only");
            return;
        }
        DraftNodeComposite parent = node.getParent();
        if (parent != null) parent.removeChild(node);
        node.setParent(null);
    }

    public void removeNode(Integer id){
        removeNode(nodes.get(id));
    }

    private void deleteNode(DraftNode node){
        if (node instanceof DraftNodeComposite)
            for (DraftNode child : ((DraftNodeComposite) node).getChildren())
                deleteNode(child);
        nodes.remove(node.getId());
    }

    public void deleteNode(Integer id){
        DraftNode node = nodes.get(id);
        if (node == null){
            System.err.println("Node is null");
            return;
        }
        if (node.isReadOnly()){
            System.err.println("Node is read-only");
            return;
        }
        notifySubscribers(EventTypes.NODE_DELETED, createDraftNodeDTO(node));
        removeNode(node);
        deleteNode(node);
    }

    public void renameNode(Integer id, String newName){
        DraftNode node = nodes.get(id);
        if (node instanceof Named namedNode && (node.getParent() == null || !hasChildWithName(node.getParent().getId(), newName))) {
            namedNode.setName(newName);
            notifySubscribers(EventTypes.NODE_EDITED, createDraftNodeDTO(node));
        }
    }

    public void changeAuthor(Integer id, String newAuthor){
        DraftNode node = nodes.get(id);
        if (node instanceof Project) {
            ((Project) node).setAuthor(newAuthor);
            notifySubscribers(EventTypes.NODE_EDITED, createDraftNodeDTO(node));
        }
    }

    public void changePath(Integer id, String newPath){
        DraftNode node = nodes.get(id);
        if (node instanceof Project) {
            ((Project) node).setPath(newPath);
            notifySubscribers(EventTypes.NODE_EDITED, createDraftNodeDTO(node));
        }
    }

    private void getSubtree(DraftNode node, Vector<DraftNodeDTO> subtree){
        subtree.add(createDraftNodeDTO(node));
        if (node instanceof DraftNodeComposite parent)
            for (DraftNode child : parent.getChildren())
                getSubtree(child, subtree);
    }

    public Vector<DraftNodeDTO> getSubtree(Integer id){
        Vector<DraftNodeDTO> subtree = new Vector<>();
        DraftNode node = nodes.get(id);
        if (node != null) getSubtree(node, subtree);
        return subtree;
    }

    public boolean isAncestor(Integer parentId, Integer id){
        DraftNode parent = nodes.get(parentId), child = nodes.get(id);
        return child != null && child.isAncestor(parent);
    }

    private DraftNodeDTO getProject(DraftNode node){
        if (node == null) return null;
        if (node instanceof Project) return createDraftNodeDTO(node);
        return getProject(node.getParent());
    }

    public DraftNodeDTO getProject(Integer id){
        return getProject(nodes.get(id));
    }

    private DraftNodeDTO getBuilding(DraftNode node){
        if (node == null) return null;
        if (node instanceof Building) return createDraftNodeDTO(node);
        return getBuilding(node.getParent());
    }

    public DraftNodeDTO getBuilding(Integer id){
        return getBuilding(nodes.get(id));
    }
}
