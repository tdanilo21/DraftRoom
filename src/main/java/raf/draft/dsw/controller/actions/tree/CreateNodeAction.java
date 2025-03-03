package raf.draft.dsw.controller.actions.tree;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.dialogs.CreateNodePane;
import raf.draft.dsw.gui.swing.tree.DraftTreeNode;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class CreateNodeAction extends AbstractRoomAction {
    public CreateNodeAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/add.png"));
        putValue(NAME, "Add");
        putValue(SHORT_DESCRIPTION, "Add");
    }

    private void insertNode(DraftTreeNode parent, DraftNodeTypes type, String... parameters){
        DraftRoomRepository repository = ApplicationFramework.getInstance().getRepository();
        if (repository.hasChildWithName(parent.getData().id(), parameters[0])){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Object with the same name already exists at the same path.", MessageTypes.WARNING);
            return;
        }
        repository.createNode(type, parent.getData().id(), parameters);
        ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Object has been created.", MessageTypes.NOTIFICATION);
    }

    private void perform(DraftTreeNode selectedNode, DraftNodeTypes type){
        String[] result;
        switch(type){
            case DraftNodeTypes.PROJECT:
                result = CreateNodePane.showDialog("New project", new String[]{"name", "author"});
                if (result != null) insertNode(selectedNode, DraftNodeTypes.PROJECT, result[0], result[1], null);
                break;
            case DraftNodeTypes.BUILDING:
                result = CreateNodePane.showDialog("New Building", new String[]{"name"});
                if (result != null) insertNode(selectedNode, DraftNodeTypes.BUILDING, result[0]);
                break;
            case DraftNodeTypes.ROOM:
                result = CreateNodePane.showDialog("New Room", new String[]{"name"});
                if (result != null) insertNode(selectedNode, DraftNodeTypes.ROOM, result[0]);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeNode selectedNode = (DraftTreeNode)MainFrame.getInstance().getRepoTreeView().getLastSelectedPathComponent();
        if (selectedNode == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Object has not been selected.", MessageTypes.ERROR);
            return;
        }
        Vector<DraftNodeTypes> allowedTypes = ApplicationFramework.getInstance().getRepository().getAllowedChildrenTypes(selectedNode.getData().id());
        if (allowedTypes.isEmpty()){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(STR."Cannot add children to \{selectedNode.getData().name()}.", MessageTypes.WARNING);
            return;
        }
        if (allowedTypes.size() > 1){
            DraftNodeTypes[] allowedTypesArr = new DraftNodeTypes[allowedTypes.size()];
            for (int i = 0; i < allowedTypes.size(); i++)
                allowedTypesArr[i] = allowedTypes.get(i);
            int choice = JOptionPane.showOptionDialog(
                    null, "Choose object type", "New object",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    allowedTypesArr, allowedTypesArr[0]
                );
            if (choice == -1) return;
            allowedTypes.clear();
            allowedTypes.add(allowedTypesArr[choice]);
        }
        perform(selectedNode, allowedTypes.getFirst());
    }
}
