package raf.draft.dsw.controller.actions.file;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SaveProjectAction extends AbstractSaveProjectAction {
    public SaveProjectAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/save.png"));
        putValue(NAME, "Save Project");
        putValue(SHORT_DESCRIPTION, "Save Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canSave()){
            DraftNodeDTO project = getProject();
            ApplicationFramework app = ApplicationFramework.getInstance();
            if (!app.getRepository().hasPath(project.id())) saveAs();
            else{
                int result = app.getRepository().getFileIO().saveProject(project.id());
                if (result != DraftRoomRepository.FileIO.OK) app.getMessageGenerator().generateMessage("Something went wrong, file couldn't be saved", MessageTypes.WARNING);
            }
        }
    }
}
