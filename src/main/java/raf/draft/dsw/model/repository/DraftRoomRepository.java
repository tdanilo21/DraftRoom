package raf.draft.dsw.model.repository;

import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.dtos.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.Renamable;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;

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

public class DraftRoomRepository {
    private HashMap<Integer, DraftNode> nodes;
    private Integer K;
    private final ProjectExplorerFactory projectExplorerFactory;
    private final ProjectFactory projectFactory;
    private final BuildingFactory buildingFactory;
    private final RoomFactory roomFactory;


    public DraftRoomRepository(){
        nodes = new HashMap<>();
        K = 0;

        projectExplorerFactory = new ProjectExplorerFactory();
        projectFactory = new ProjectFactory();
        buildingFactory = new BuildingFactory();
        roomFactory = new RoomFactory();

        createNode(DraftNodeTypes.PROJECT_EXPLORER);
    }

    private DraftNodeDTO createDraftNodeDTO(DraftNode node){
        if (node instanceof ProjectExplorer)
            return new DraftNodeDTO(node.getId(), DraftNodeTypes.PROJECT_EXPLORER, "ProjectExplorer", null);
        if (node instanceof Project)
            return new DraftNodeDTO(node.getId(), DraftNodeTypes.PROJECT, ((Project) node).getName(), null);
        if (node instanceof Building)
            return new DraftNodeDTO(node.getId(), DraftNodeTypes.BUILDING, ((Building) node).getName(), ((Building) node).getColor());
        if (node instanceof Room)
            return new DraftNodeDTO(node.getId(), DraftNodeTypes.ROOM, ((Room) node).getName(), null);
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
        DraftNode parent = nodes.get(parentId);
        if (parent instanceof DraftNodeComposite)
            ((DraftNodeComposite) parent).addChild(nodes.get(id));
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
        removeNode(node);
        deleteNode(node);
    }

    public void renameNode(Integer id, String newName){
        DraftNode node = nodes.get(id);
        if (node instanceof Renamable)
            ((Renamable)node).setName(newName);
    }

    public void changeAuthor(Integer id, String newAuthor){
        DraftNode node = nodes.get(id);
        if (node instanceof Project)
            ((Project) node).setAuthor(newAuthor);
    }

    public void changePath(Integer id, String newPath){
        DraftNode node = nodes.get(id);
        if (node instanceof Project)
            ((Project) node).setPath(newPath);
    }
}
