package raf.draft.dsw.model.repository;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import lombok.Getter;
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
import raf.draft.dsw.model.structures.room.Pattern;
import raf.draft.dsw.model.structures.room.RoomElement;
import raf.draft.dsw.model.structures.room.elements.*;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private final HashMap<Integer, DraftNode> nodes;
    private Integer K;
    private final ProjectExplorerFactory projectExplorerFactory;
    private final ProjectFactory projectFactory;
    private final BuildingFactory buildingFactory;
    private final RoomFactory roomFactory;
    private final HashMap<EventTypes, Vector<ISubscriber> > subscribers;
    @Getter
    private final FileIO fileIO;

    private DraftRoomRepository(){
        nodes = new HashMap<>();
        K = 0;

        projectExplorerFactory = new ProjectExplorerFactory();
        projectFactory = new ProjectFactory();
        buildingFactory = new BuildingFactory();
        roomFactory = new RoomFactory();

        subscribers = new HashMap<>();

        fileIO = new FileIO();

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

    public Wall getWall(Integer roomId){
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

    public Vector<VisualElement> createBatchSpiral(Integer roomId, int k, double[] w, double[] h, VisualElementTypes[] types){
        double maxW = 0;
        double maxH = 0;
        for(int i = 0; i < k; i++) {
            maxW = Math.max(w[i], maxW);
            maxH = Math.max(h[i], maxH);
        }

        DraftNode node = nodes.get(roomId);
        if(!(node instanceof Room room)) return null;

        int m = (int)Math.floor((room.getW() - 2*room.getWallWidth()) / maxW);
        int n = (int)Math.floor((room.getH() - 2*room.getWallWidth()) / maxH);
        if(n*m < k) return null;

        int[] rnd = new int[k];
        for(int i = 0; i < k; i++) rnd[i] = i;
        Random random = new Random();
        for (int i = k-1; i > 0; i--){
            int j = random.nextInt(i+1);
            int t = rnd[i];
            rnd[i] = rnd[j];
            rnd[j] = t;
        }

        Vector<VisualElement> elements = new Vector<>();
        int idx = 0;
        for (int t = 0; t < Math.min(n, m) / 2 && idx < k; t++){
            int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            int i = t, j = t;
            for (int p = 0; p < 4 && idx < k; p++){
                int di = dir[p][0], dj = dir[p][1];
                int l = ((p&1) == 0 ? m : n) - 1 - 2*t;
                for(int u = 0; u < l && idx < k; u++, idx++, i += di, j += dj){
                    int ridx = rnd[idx];
                    double x = j * maxW + room.getWallWidth();
                    double y = i * maxH + room.getWallWidth();
                    if(j == m - 1) x = room.getW() - w[ridx] - room.getWallWidth();
                    if(i == n - 1) y = room.getH() - h[ridx] - room.getWallWidth();
                    if (types[ridx] == VisualElementTypes.BOILER || types[ridx] == VisualElementTypes.TOILET) w[ridx] /= 2;
                    elements.add(createRoomElement(types[ridx], roomId, new Point2D.Double(x, y), w[ridx], h[ridx]));
                }
            }
        }
        if ((Math.min(n, m)&1) == 1){
            int t = Math.min(n, m) / 2;
            int di = (n > m ? 1 : 0), dj = (n < m ? 1 : 0);
            for (int u = 0, i = t, j = t; u < Math.max(n, m) - 2*t && idx < k; u++, idx++, i += di, j += dj){
                int ridx = rnd[idx];
                double x = j * maxW + room.getWallWidth();
                double y = i * maxH + room.getWallWidth();
                if(j == m - 1) x = room.getW() - w[ridx] - room.getWallWidth();
                if(i == n - 1) y = room.getH() - h[ridx] - room.getWallWidth();
                if (types[ridx] == VisualElementTypes.BOILER || types[ridx] == VisualElementTypes.TOILET) w[ridx] /= 2;
                elements.add(createRoomElement(types[ridx], roomId, new Point2D.Double(x, y), w[ridx], h[ridx]));
            }
        }
        return elements;
    }

    public class FileIO {
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
        private static final Path patternsPath = Paths.get(STR."\{Paths.get("").toAbsolutePath()}\\src\\main\\resources\\patterns\\");

        public static final int OK = 0;
        public static final int FAIL = 1;
        public static final int FILE_EXISTS = 2;
        public static final int NODE_EXISTS = 3;
        public static final int INVALID_FILE_TYPE = 4;
        public static final int INVALID_NODE_TYPE = 5;
        public static final int INVALID_FILE_FORMAT = 6;

        public int saveProjectAs(File file, Integer id, boolean force) {
            if (!file.isDirectory()) return INVALID_FILE_TYPE;
            DraftNode node = nodes.get(id);
            if (!(node instanceof Project project)) return INVALID_NODE_TYPE;
            File projectFile = new File(STR."\{file.getAbsolutePath()}\\\{project.getName()}.json");
            if (!projectFile.exists()) {
                try {
                    if (!projectFile.createNewFile()) return FAIL;
                } catch (IOException exception) {
                    System.err.println(exception.getMessage());
                    return FAIL;
                }
            } else if (!force) return FILE_EXISTS;
            project.setPath(projectFile.getAbsolutePath());
            int result = saveProject(id);
            if (result != OK) projectFile.delete();
            return result;
        }

        public int saveProject(Integer id) {
            DraftNode node = nodes.get(id);
            if (!(node instanceof Project)) return INVALID_NODE_TYPE;
            File file = new File(((Project) node).getPath());
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, node);
            } catch (IOException exception) {
                System.err.println(exception.getMessage());
                return FAIL;
            }
            node.save();
            return OK;
        }

        private void loadTree(DraftNode node, DraftNodeComposite parent) {
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

        public int openProject(File file) {
            if (!file.isFile()) return INVALID_FILE_TYPE;
            DraftNode node;
            try {
                node = mapper.readValue(file, DraftNode.class);
            } catch (IOException exception) {
                System.err.println(exception.getMessage());
                return FAIL;
            }
            if (!(node instanceof Project project)) return INVALID_FILE_FORMAT;
            if (hasChildWithName(0, project.getName())) return NODE_EXISTS;
            project.setPath(file.getAbsolutePath());
            node.save();
            loadTree(node, (DraftNodeComposite) nodes.get(0));
            return OK;
        }

        public void createPatternFolder(){
            File directory = new File(patternsPath.toString());
            if (directory.exists()) return;
            directory.mkdir();
        }

        private int savePattern(Pattern pattern, String name, boolean force) {
            createPatternFolder();
            File file = new File(STR."\{patternsPath}\\\{name}");
            try {
                if (!file.exists()) {
                    if (!file.createNewFile()) return FAIL;
                } else if (!force) return FILE_EXISTS;
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, pattern);
            } catch (IOException exception) {
                System.err.println(exception.getMessage());
                return FAIL;
            }
            return OK;
        }

        public int saveAsPattern(Integer id, boolean force) {
            DraftNode node = nodes.get(id);
            if (node instanceof Room room) return savePattern(new Pattern(room), STR."\{room.getName()}.json", force);
            return INVALID_NODE_TYPE;
        }

        private Pattern readPattern(File file) {
            Pattern pattern;
            try {
                pattern = mapper.readValue(file, Pattern.class);
            } catch (IOException exception) {
                System.err.println(exception.getMessage());
                return null;
            }
            return pattern;
        }

        public int loadPattern(File file, Integer id) {
            if (!file.isFile()) return INVALID_FILE_TYPE;
            DraftNode node = nodes.get(id);
            if (!(node instanceof Room room)) return INVALID_NODE_TYPE;
            Pattern pattern = readPattern(file);
            if (pattern == null) return INVALID_FILE_FORMAT;
            room.initialize(pattern.getW(), pattern.getH());
            room.removeChildren();
            for (DraftNode child : pattern.getChildren()) {
                child.load(K);
                K++;
                nodes.put(child.getId(), child);
                room.addChild(child);
            }
            return OK;
        }

        public int importPattern(File file, boolean force) {
            if (!file.isFile()) return INVALID_FILE_TYPE;
            Pattern pattern = readPattern(file);
            if (pattern == null) return INVALID_FILE_FORMAT;
            return savePattern(pattern, file.getName(), force);
        }
    }
}