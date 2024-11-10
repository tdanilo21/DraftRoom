package raf.draft.dsw.controller.actions;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.tree.DraftTreeNode;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteNodeAction extends AbstractRoomAction {
    public DeleteNodeAction(){
        putValue(SMALL_ICON, loadIcon("/images/exit.png"));
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeNode selectedNode = (DraftTreeNode)MainFrame.getInstance().getRepoTreeView().getLastSelectedPathComponent();
        if (selectedNode == null){
            System.err.println("Node has not been selected");
            return;
        }
        DraftRoomRepository repository = ApplicationFramework.getInstance().getRepository();
        if (repository.isReadOnly(selectedNode.getData().id())){
            System.err.println(STR."\{selectedNode.getUserObject()} cannot be deleted");
            return;
        }
        int choice = JOptionPane.showConfirmDialog(
                null,
                STR."If you delete \{selectedNode.getData().name()} all objects inside it will be deleted. Are you sure you want to proceed?",
                "Delete", JOptionPane.YES_NO_OPTION
            );
        if (choice == JOptionPane.YES_OPTION){
            repository.deleteNode(selectedNode.getData().id());
            MainFrame.getInstance().getRepoTreeModel().removeNodeFromParent(selectedNode);
        }
    }
}
