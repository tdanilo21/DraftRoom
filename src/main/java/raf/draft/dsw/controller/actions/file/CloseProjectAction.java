package raf.draft.dsw.controller.actions.file;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.tree.DraftTreeNode;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.messages.MessageTypes;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CloseProjectAction extends AbstractRoomAction {
    public CloseProjectAction(){
        putValue(SMALL_ICON, loadIcon("/images/info.png"));
        putValue(NAME, "Close Project");
        putValue(SHORT_DESCRIPTION, "Close Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeNode selectedNode = ((DraftTreeNode)MainFrame.getInstance().getRepoTreeView().getLastSelectedPathComponent());
        if (selectedNode == null || selectedNode.getData().type() != DraftNodeTypes.PROJECT){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Project has not been selected.", MessageTypes.ERROR);
            return;
        }
        if (!selectedNode.getData().saved()){
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    STR."Project \{selectedNode.getData().name()} is not saved, progress might be lost. Are you sure you want to proceed?",
                    "Close project", JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.NO_OPTION) return;
        }
        ApplicationFramework.getInstance().getRepository().deleteNode(selectedNode.getData().id());
    }
}
