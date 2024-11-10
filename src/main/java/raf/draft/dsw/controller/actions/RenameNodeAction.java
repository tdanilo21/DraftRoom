package raf.draft.dsw.controller.actions;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.tree.DraftTreeNode;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RenameNodeAction extends AbstractRoomAction {
    public RenameNodeAction(){
        putValue(SMALL_ICON, loadIcon("/images/exit.png"));
        putValue(NAME, "Rename");
        putValue(SHORT_DESCRIPTION, "Rename");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeNode selectedNode = (DraftTreeNode) MainFrame.getInstance().getRepoTreeView().getLastSelectedPathComponent();
        if (selectedNode == null){
            System.err.println("Node has not been selected");
            return;
        }
        DraftRoomRepository repository = ApplicationFramework.getInstance().getRepository();
        if (repository.isReadOnly(selectedNode.getData().id())){
            System.err.println(STR."\{selectedNode.getUserObject()} cannot be renamed");
            return;
        }
        String newName = JOptionPane.showInputDialog(null, "New name", "Rename object", JOptionPane.QUESTION_MESSAGE);
        if (newName != null){
            repository.renameNode(selectedNode.getData().id(), newName);
            selectedNode.setName(newName);
            MainFrame.getInstance().getRepoTreeModel().reload(selectedNode);
        }
    }
}
