package raf.draft.dsw.model.repository;

import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.dtos.DraftNodeTypes;
import raf.draft.dsw.controller.dtos.VisualElementTypes;
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
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.elements.*;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

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

class RoomElementFactory{
    public static RoomElement createRoomElement(VisualElementTypes type, Integer id, Point location, int... dimensions){
        return switch (type){
            case VisualElementTypes.BED -> new Bed(dimensions[0], dimensions[1], location, id);
            case VisualElementTypes.BATH_TUB -> new BathTub(dimensions[0], dimensions[1], location, id);
            case VisualElementTypes.CLOSET -> new Closet(dimensions[0], dimensions[1], location, id);
            case VisualElementTypes.TABLE -> new Table(dimensions[0], dimensions[1], location, id);
            case VisualElementTypes.WASHING_MACHINE -> new WashingMachine(dimensions[0], dimensions[1], location, id);
            case VisualElementTypes.BOILER -> new Boiler(dimensions[0], location, id);
            case VisualElementTypes.DOOR -> new Door(dimensions[0], location, id);
            case VisualElementTypes.TOILET -> new Toilet(dimensions[0], location, id);
            case VisualElementTypes.SINK -> new Sink(dimensions[0], location, id);
            default -> null;
        };
    }
}

public class DraftRoomRepository implements IPublisher {
    private final HashMap<Integer, DraftNode> nodes;
    private Integer K;
    private final ProjectExplorerFactory projectExplorerFactory;
    private final ProjectFactory projectFactory;
    private final BuildingFactory buildingFactory;
    private final RoomFactory roomFactory;
    private final HashMap<EventTypes, Vector<ISubscriber> > subscribers;


    public DraftRoomRepository(){
        nodes = new HashMap<>();
        K = 0;

        projectExplorerFactory = new ProjectExplorerFactory();
        projectFactory = new ProjectFactory();
        buildingFactory = new BuildingFactory();
        roomFactory = new RoomFactory();

        subscribers = new HashMap<>();

        createNode(DraftNodeTypes.PROJECT_EXPLORER, null);
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
        return node instanceof DraftNodeComposite parent && parent.hasChildWithName(name);
    }

    public boolean hasSiblingWithName(Integer id, String name){
        DraftNodeComposite parent = nodes.get(id).getParent();
        return parent != null && parent.hasChildWithName(name);
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

    public DraftNodeDTO createNode(DraftNodeTypes type, Integer parentId, String... parameters){
        DraftNode node = switch (type) {
            case DraftNodeTypes.PROJECT_EXPLORER -> projectExplorerFactory.createNode(K, parameters);
            case DraftNodeTypes.PROJECT -> projectFactory.createNode(K, parameters);
            case DraftNodeTypes.BUILDING -> buildingFactory.createNode(K, parameters);
            case DraftNodeTypes.ROOM -> roomFactory.createNode(K, parameters);
            default -> null;
        };
        if (node == null) return null;
        if (node instanceof Named namedNode && node.getParent() != null && hasChildWithName(node.getParent().getId(), namedNode.getName()))
            return null;
        DraftNode parent = nodes.get(parentId);
        if (parent instanceof DraftNodeComposite composite) composite.addChild(node);
        K++;
        nodes.put(node.getId(), node);
        notifySubscribers(EventTypes.NODE_CREATED, node.getDTO());
        return node.getDTO();
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
        if (parent instanceof DraftNodeComposite composite) {
            composite.addChild(child);
            notifySubscribers(EventTypes.NODE_EDITED, child.getDTO());
        }
    }

    public DraftNodeDTO getNode(Integer id){
        return nodes.get(id).getDTO();
    }

    public DraftNodeDTO getRoot(){
        return nodes.get(0).getDTO();
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
    }

    public void removeNode(Integer id){
        removeNode(nodes.get(id));
    }

    private void deleteNode(DraftNode node){
        if (node instanceof DraftNodeComposite composite)
            for (DraftNode child : composite.getChildren())
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
        removeNode(node);
        deleteNode(node);
        notifySubscribers(EventTypes.NODE_DELETED, node.getDTO());
    }

    public void renameNode(Integer id, String newName){
        DraftNode node = nodes.get(id);
        if (node instanceof Named namedNode && hasSiblingWithName(id, newName)){
            namedNode.setName(newName);
            notifySubscribers(EventTypes.NODE_EDITED, node.getDTO());
        }
    }

    public void changeAuthor(Integer id, String newAuthor){
        DraftNode node = nodes.get(id);
        if (node instanceof Project) {
            ((Project) node).setAuthor(newAuthor);
            notifySubscribers(EventTypes.NODE_EDITED, node.getDTO());
        }
    }

    public void changePath(Integer id, String newPath){
        DraftNode node = nodes.get(id);
        if (node instanceof Project) {
            ((Project) node).setPath(newPath);
            notifySubscribers(EventTypes.NODE_EDITED, node.getDTO());
        }
    }

    public Vector<DraftNodeDTO> getSubtree(Integer id){
        Vector<DraftNodeDTO> subtree = new Vector<>();
        DraftNode node = nodes.get(id);
        if (node != null) node.getSubtree(subtree);
        return subtree;
    }

    public boolean isAncestor(Integer parentId, Integer id){
        DraftNode parent = nodes.get(parentId), child = nodes.get(id);
        return child != null && child.isAncestor(parent);
    }

    public DraftNodeDTO getProject(Integer id){
        DraftNode node = nodes.get(id);
        if (node != null){
            Project project = node.getProject();
            return project == null ? null : project.getDTO();
        }
        return null;
    }

    public DraftNodeDTO getBuilding(Integer id){
        DraftNode node = nodes.get(id);
        if (node != null){
            Building building = node.getBuilding();
            return building == null ? null : building.getDTO();
        }
        return null;
    }

    public VisualElement createRoomElement(VisualElementTypes type, Integer parentId, Point location, int... dimensions){
        RoomElement roomElement = RoomElementFactory.createRoomElement(type, K, location, dimensions);
        DraftNode node = nodes.get(parentId);
        if (roomElement != null && node instanceof Room room){
            // TODO: Check for overlaps
            room.addChild(roomElement);
            K++;
            nodes.put(roomElement.getId(), roomElement);
            notifySubscribers(EventTypes.NODE_CREATED, roomElement.getDTO());
            return roomElement;
        }
        return null;
    }

    public VisualElement cloneRoomElement(Integer id){
        DraftNode node = nodes.get(id);
        if (node instanceof RoomElement roomElement){
            RoomElement clone = (RoomElement)roomElement.clone(K);
            if (clone != null) {
                if (roomElement.getParent() != null) roomElement.getParent().addChild(clone);
                K++;
                nodes.put(clone.getId(), clone);
                notifySubscribers(EventTypes.NODE_CREATED, clone.getDTO());
                return clone;
            }
            return null;
        }
        return null;
    }

    public void initializeRoom(Integer roomId, int w, int h, int screenW, int screenH){
        DraftNode node = nodes.get(roomId);
        if (node instanceof Room room) room.initialize(w, h, screenW, screenH);
    }

    public void updateRoomScaleFactor(Integer roomId, int screenW, int screenH){
        DraftNode node = nodes.get(roomId);
        if (node instanceof Room room) room.updateScaleFactor(screenW, screenH);
    }

    public Vector<VisualElement> getVisualElements(Integer roomId){
        DraftNode node = nodes.get(roomId);
        if (node instanceof Room room) return room.getVisualElements();
        return null;
    }
}
