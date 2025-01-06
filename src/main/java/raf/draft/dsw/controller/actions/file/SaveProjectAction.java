package raf.draft.dsw.controller.actions.file;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.model.messages.MessageTypes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SaveProjectAction extends AbstractSaveAction {
    public SaveProjectAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        putValue(NAME, "Save Project");
        putValue(SHORT_DESCRIPTION, "Save Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canSave()){
            DraftNodeDTO room = MainFrame.getInstance().getRoomViewController().getSelectedTab().getRoom();
            ApplicationFramework app = ApplicationFramework.getInstance();
            DraftNodeDTO project = app.getRepository().getProject(room.id());
            if (!app.getRepository().hasPath(project.id())) saveAs();
            else if (!app.getRepository().saveProject(project.id()))
                app.getMessageGenerator().generateMessage("Something went wrong. File couldn't be saved", MessageTypes.WARNING);
        }
    }
}
