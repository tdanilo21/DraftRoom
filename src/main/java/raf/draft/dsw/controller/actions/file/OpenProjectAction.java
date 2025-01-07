package raf.draft.dsw.controller.actions.file;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class OpenProjectAction extends AbstractRoomAction {
    public OpenProjectAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/open.png"));
        putValue(NAME, "Open Project");
        putValue(SHORT_DESCRIPTION, "Open Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            ApplicationFramework app = ApplicationFramework.getInstance();
            File file = fileChooser.getSelectedFile();
            int result = app.getRepository().getFileIO().openProject(file);
            switch (result) {
                case DraftRoomRepository.FileIO.INVALID_FILE_FORMAT -> app.getMessageGenerator().generateMessage("Invalid file format", MessageTypes.WARNING);
                case DraftRoomRepository.FileIO.INVALID_FILE_TYPE -> app.getMessageGenerator().generateMessage(STR."\{file.getName()} is not a file", MessageTypes.WARNING);
                case DraftRoomRepository.FileIO.NODE_EXISTS -> app.getMessageGenerator().generateMessage("Project with the same name is already open", MessageTypes.WARNING);
                default -> {
                    if (result != DraftRoomRepository.FileIO.OK) app.getMessageGenerator().generateMessage("Something went wrong, file couldn't be opened", MessageTypes.WARNING);
                }
            }
        }
    }
}
