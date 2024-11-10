package raf.draft.dsw.controller.actions;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.tree.DraftTreeNode;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChangeAuthorAction extends AbstractRoomAction {
    public ChangeAuthorAction(){
        putValue(SMALL_ICON, loadIcon("/images/edit.png"));
        putValue(NAME, "Change author");
        putValue(SHORT_DESCRIPTION, "Change author");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeNode selectedNode = (DraftTreeNode) MainFrame.getInstance().getRepoTreeView().getLastSelectedPathComponent();
        if (selectedNode == null){
            System.err.println("Node has not been selected");
            return;
        }
        DraftRoomRepository repository = ApplicationFramework.getInstance().getRepository();
        if (repository.cannotChangeAuthor(selectedNode.getData().id())){
            System.err.println(STR."Cannot change author of \{selectedNode.getUserObject()}");
            return;
        }
        String newAuthor = JOptionPane.showInputDialog(null, "New author", "Change author", JOptionPane.QUESTION_MESSAGE);
        if (newAuthor != null){
            repository.changeAuthor(selectedNode.getData().id(), newAuthor);
        }
    }
}