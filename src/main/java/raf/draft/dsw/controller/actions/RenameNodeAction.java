package raf.draft.dsw.controller.actions;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.tree.DraftTreeNode;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RenameNodeAction extends AbstractRoomAction {
    public RenameNodeAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/edit.png"));
        putValue(NAME, "Rename");
        putValue(SHORT_DESCRIPTION, "Rename");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeNode selectedNode = (DraftTreeNode) MainFrame.getInstance().getRepoTreeView().getLastSelectedPathComponent();
        if (selectedNode == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Node has not been selected.", MessageTypes.ERROR);
            return;
        }
        DraftRoomRepository repository = ApplicationFramework.getInstance().getRepository();
        if (repository.isReadOnly(selectedNode.getData().id())){
            System.err.println(STR."\{selectedNode.getUserObject()} cannot be renamed");
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(STR."\{selectedNode.getUserObject()} cannot be renamed", MessageTypes.WARNING);
            return;
        }
        String newName = JOptionPane.showInputDialog(null, "New name", "Rename object", JOptionPane.QUESTION_MESSAGE);
        if (newName != null){
            if (repository.hasSiblingWithName(selectedNode.getData().id(), newName)){
                System.err.println("Node with the same name already exists at the same path.");
                return;
            }
            repository.renameNode(selectedNode.getData().id(), newName);
            selectedNode.setName(newName);
            MainFrame.getInstance().getRepoTreeModel().reload(selectedNode);
        }
    }
}
