package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.gui.swing.DraftTreeNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;

import java.awt.event.ActionEvent;

public class CreateNodeAction extends AbstractRoomAction implements ISubscriber<DraftTreeNode> {

    private DraftTreeNode selectedNode;

    public CreateNodeAction(){
        selectedNode = null;
        putValue(SMALL_ICON, loadIcon("/images/exit.png"));
        putValue(NAME, "Add");
        putValue(SHORT_DESCRIPTION, "Add");
    }

    @Override
    public void notify(DraftTreeNode node){
        selectedNode = node;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (selectedNode == null){
            System.err.println("Node has not been selected");
            return;
        }
        if (!(selectedNode.getNode() instanceof DraftNodeComposite)){
            System.err.println("Cannot add a child to leaf node");
            return;
        }
        if (selectedNode.getNode() instanceof ProjectExplorer){
            System.out.println("Create new project");
        }
        if (selectedNode.getNode() instanceof Project){
            System.out.println("Create new building/room");
        }
        if (selectedNode.getNode() instanceof Building){
            System.out.println("Create new room");
        }
    }
}
