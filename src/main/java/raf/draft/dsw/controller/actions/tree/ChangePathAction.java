package raf.draft.dsw.controller.actions.tree;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.tree.DraftTreeNode;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChangePathAction extends AbstractRoomAction {
    public ChangePathAction(){
        putValue(SMALL_ICON, loadIcon("/images/changePath.png"));
        putValue(NAME, "Change path");
        putValue(SHORT_DESCRIPTION, "Change path");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeNode selectedNode = (DraftTreeNode) MainFrame.getInstance().getRepoTreeView().getLastSelectedPathComponent();
        if (selectedNode == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Object has not been selected.", MessageTypes.ERROR);
            return;
        }
        DraftRoomRepository repository = ApplicationFramework.getInstance().getRepository();
        if (repository.cannotChangePath(selectedNode.getData().id())){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(STR."Cannot change path of \{selectedNode.getUserObject()}.", MessageTypes.WARNING);
            return;
        }
        String newPath = JOptionPane.showInputDialog(null, "New path", "Change path", JOptionPane.QUESTION_MESSAGE);
        if (newPath != null){
            repository.changePath(selectedNode.getData().id(), newPath);
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Path has been changed.", MessageTypes.NOTIFICATION);
        }
    }
}
