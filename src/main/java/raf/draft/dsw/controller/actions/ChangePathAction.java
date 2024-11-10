package raf.draft.dsw.controller.actions;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.tree.DraftTreeNode;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChangePathAction extends AbstractRoomAction {
    public ChangePathAction(){
        putValue(SMALL_ICON, loadIcon("/images/edit.png"));
        putValue(NAME, "Change path");
        putValue(SHORT_DESCRIPTION, "Change path");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeNode selectedNode = (DraftTreeNode) MainFrame.getInstance().getRepoTreeView().getLastSelectedPathComponent();
        if (selectedNode == null){
            System.err.println("Node has not been selected");
            return;
        }
        DraftRoomRepository repository = ApplicationFramework.getInstance().getRepository();
        if (repository.cannotChangePath(selectedNode.getData().id())){
            System.err.println(STR."Cannot change path of \{selectedNode.getUserObject()}");
            return;
        }
        String newPath = JOptionPane.showInputDialog(null, "New path", "Change path", JOptionPane.QUESTION_MESSAGE);
        if (newPath != null){
            repository.changePath(selectedNode.getData().id(), newPath);
        }
    }
}
