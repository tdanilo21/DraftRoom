package raf.draft.dsw.controller.actions.tree;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.tree.DraftTreeNode;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteNodeAction extends AbstractRoomAction {
    public DeleteNodeAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/delete.png"));
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeNode selectedNode = (DraftTreeNode)MainFrame.getInstance().getRepoTreeView().getLastSelectedPathComponent();
        if (selectedNode == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Object has not been selected.", MessageTypes.ERROR);
            return;
        }
        DraftRoomRepository repository = ApplicationFramework.getInstance().getRepository();
        if (repository.isReadOnly(selectedNode.getData().id())){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(STR."\{selectedNode.getUserObject()} cannot be deleted.", MessageTypes.WARNING);
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
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Object has been deleted.", MessageTypes.NOTIFICATION);
        }
    }
}
