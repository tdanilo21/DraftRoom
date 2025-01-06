package raf.draft.dsw.model.repository;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.DraftNodeSubType;
import raf.draft.dsw.model.nodes.Named;
import raf.draft.dsw.model.repository.jacksonmodules.AffineTransformModule;
import raf.draft.dsw.model.repository.jacksonmodules.Point2DModule;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.room.Geometry;
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.elements.*;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.reflections.Reflections;

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
    public static RoomElement createRoomElement(VisualElementTypes type, Integer id, Point2D location, double... dimensions){
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
    private static DraftRoomRepository instance;
    public static DraftRoomRepository getInstance(){
        if (instance == null) instance = new DraftRoomRepository();
        return instance;
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        Reflections reflections = new Reflections("raf.draft.dsw.model.structures");
        Set<Class<?> > subTypes = reflections.getTypesAnnotatedWith(DraftNodeSubType.class);
        for (Class<?> subType : subTypes){
            DraftNodeSubType annotation = subType.getAnnotation(DraftNodeSubType.class);
            if (annotation != null) mapper.registerSubtypes(new NamedType(subType, annotation.value()));
        }
        mapper.disable(MapperFeature.AUTO_DETECT_FIELDS, MapperFeature.AUTO_DETECT_GETTERS, MapperFeature.AUTO_DETECT_IS_GETTERS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.registerModule(new Point2DModule());
        mapper.registerModule(new AffineTransformModule());
    }

    private final HashMap<Integer, DraftNode> nodes;
    private Integer K;
    private final ProjectExplorerFactory projectExplorerFactory;
    private final ProjectFactory projectFactory;
    private final BuildingFactory buildingFactory;
    private final RoomFactory roomFactory;
    private final HashMap<EventTypes, Vector<ISubscriber> > subscribers;

    private DraftRoomRepository(){
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

    public void addSubscriber(Integer id, ISubscriber subscriber, EventTypes ...types){
        DraftNode node = nodes.get(id);
        if (node != null) node.addSubscriber(subscriber, types);
    }

    public void removeSubscriber(Integer id, ISubscriber subscriber, EventTypes ...types){
        DraftNode node = nodes.get(id);
        if (node != null) node.removeSubscriber(subscriber, types);
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
        K++;
        nodes.put(node.getId(), node);
        DraftNode parent = nodes.get(parentId);
        if (parent instanceof DraftNodeComposite composite) composite.addChild(node);
        notifySubscribers(EventTypes.NODE_CREATED, node.getDTO());
        return node.getDTO();
    }

    public Vector<DraftNodeTypes> getAllowedChildrenTypes(Integer id){
        return nodes.get(id).getAllowedChildrenTypes();
    }

    public void addChild(Integer parentId, Integer id){
        DraftNode parent = nodes.get(parentId), child = nodes.get(id);
        if (parent instanceof DraftNodeComposite composite)
            composite.addChild(child);
    }

    public DraftNodeDTO getNode(Integer id){
        return nodes.get(id).getDTO();
    }

    public DraftNode getNodeObject(Integer id){
        return nodes.get(id);
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

    public boolean hasPath(Integer id){
        DraftNode node = nodes.get(id);
        return node instanceof Project project && project.getPath() != null;
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
        notifySubscribers(EventTypes.NODE_DELETED, node.getDTO());
        deleteNode(node);
    }

    public void renameNode(Integer id, String newName){
        DraftNode node = nodes.get(id);
        if (node instanceof Named namedNode && !hasSiblingWithName(id, newName)) {
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

    public VisualElement createRoomElement(VisualElementTypes type, Integer parentId, Point2D location, double... dimensions){
        DraftNode node = nodes.get(parentId);
        if (node instanceof Room room && room.isInitialized()){
            RoomElement roomElement = RoomElementFactory.createRoomElement(type, K, location, dimensions);
            if (roomElement != null) {
                K++;
                nodes.put(roomElement.getId(), roomElement);
                room.addChild(roomElement);
                notifySubscribers(EventTypes.NODE_CREATED, roomElement.getDTO());
                return roomElement;
            }
        }
        return null;
    }

    public boolean isRoomInitialized(Integer id){
        DraftNode node = nodes.get(id);
        return node instanceof Room && ((Room)node).isInitialized();
    }

    public VisualElement cloneRoomElement(Integer id){
        DraftNode node = nodes.get(id);
        if (node instanceof RoomElement roomElement){
            RoomElement clone = (RoomElement)roomElement.clone(K);
            if (clone != null) {
                K++;
                nodes.put(clone.getId(), clone);
                ((Room)nodes.get(roomElement.getRoomId())).addChild(clone);
                notifySubscribers(EventTypes.NODE_CREATED, clone.getDTO());
                return clone;
            }
            return null;
        }
        return null;
    }

    public void initializeRoom(Integer roomId, double w, double h){
        DraftNode node = nodes.get(roomId);
        if (node instanceof Room room) room.initialize(w, h);
    }

    public Vector<VisualElement> getVisualElements(Integer roomId){
        DraftNode node = nodes.get(roomId);
        if (node instanceof Room room) return room.getVisualElements();
        return null;
    }

    public Wall getRoom(Integer roomId){
        DraftNode node = nodes.get(roomId);
        if (node instanceof Room) return (Wall)node;
        return null;
    }

    private void overlaps(DraftNode node, Vector<DraftNodeDTO> rooms){
        if (node instanceof Room room){
            if (room.overlaps()) rooms.add(room.getDTO());
            return;
        }
        if (node instanceof DraftNodeComposite composite)
            for (DraftNode child : composite.getChildren())
                overlaps(child, rooms);
    }

    public Vector<DraftNodeDTO> overlaps(Integer id){
        Vector<DraftNodeDTO> rooms = new Vector<>();
        overlaps(nodes.get(id), rooms);
        return rooms;
    }

    public boolean saveProjectAs(File file, Integer id){
        DraftNode node = nodes.get(id);
        if (!(node instanceof Project)) return false;
        try{
            if (!file.createNewFile())
                return false;
        } catch (IOException exception){
            System.err.println(exception.getMessage());
            return false;
        }
        ((Project)node).setPath(file.getAbsolutePath());
        if (saveProject(id)) return true;
        file.delete();
        return false;
    }

    public boolean saveProject(Integer id){
        DraftNode node = nodes.get(id);
        if (!(node instanceof Project)) return false;
        File file = new File(((Project)node).getPath());
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, node);
        } catch (IOException exception){
            System.err.println(exception.getMessage());
            return false;
        }
        node.save();
        return true;
    }

    private void loadTree(DraftNode node, DraftNodeComposite parent){
        node.load(K);
        K++;
        nodes.put(node.getId(), node);
        parent.reconnect(node);
        notifySubscribers(EventTypes.NODE_CREATED, node.getDTO());
        if (node instanceof DraftNodeComposite composite) {
            Vector<DraftNode> children = new Vector<>(composite.getChildren());
            for (DraftNode child : children)
                loadTree(child, composite);
        }
    }

    public boolean openProject(File file){
        if (!file.isFile()) return false;
        DraftNode node;
        try {
            node = mapper.readValue(file, DraftNode.class);
        } catch (IOException exception){
            System.err.println(exception.getMessage());
            return false;
        }
        if (!(node instanceof Project)) return false;
        if (hasChildWithName(0, ((Project)node).getName())) return false;
        ((Project)node).setPath(file.getAbsolutePath());
        node.save();
        loadTree(node, (DraftNodeComposite)nodes.get(0));
        return true;
    }

    public void createBatchSpiral(Integer roomId, int n, double[] w, double[] h, VisualElementTypes[] types){

    }
}
