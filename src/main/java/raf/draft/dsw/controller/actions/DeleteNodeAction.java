package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.gui.swing.DraftTreeNode;
import raf.draft.dsw.model.structures.ProjectExplorer;

import java.awt.event.ActionEvent;

public class DeleteNodeAction extends AbstractRoomAction implements ISubscriber<DraftTreeNode> {

    private DraftTreeNode selectedNode;

    public DeleteNodeAction(){
        selectedNode = null;
        putValue(SMALL_ICON, loadIcon("/images/exit.png"));
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete");
    }

    @Override
    public void notify(DraftTreeNode newState) {
        selectedNode = newState;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (selectedNode == null){
            System.err.println("Node has not been selected");
            return;
        }
        if (selectedNode.getNode() instanceof ProjectExplorer){
            System.err.println("Cannot delete ProjectExplorer");
            return;
        }
        System.out.println(STR."Delete node \{selectedNode}");
    }
}
