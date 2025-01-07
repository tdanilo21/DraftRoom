package raf.draft.dsw.controller.actions.file;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class ImportPatternAction extends AbstractRoomAction {
    public ImportPatternAction(){
        putValue(SMALL_ICON, loadIcon("/images/info.png"));
        putValue(NAME, "Import pattern");
        putValue(SHORT_DESCRIPTION, "Import pattern");
    }

    private void importPattern(File file, boolean force){
        ApplicationFramework app = ApplicationFramework.getInstance();
        int result = app.getRepository().getFileIO().importPattern(file, force);
        switch (result){
            case DraftRoomRepository.FileIO.FILE_EXISTS -> {
                int choice = JOptionPane.showConfirmDialog(null, "File with given path already exists. Do you want to replace it?", "Warning", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) importPattern(file, true);
            }
            case DraftRoomRepository.FileIO.INVALID_FILE_TYPE -> app.getMessageGenerator().generateMessage(STR."\{file.getName()} is not a file", MessageTypes.WARNING);
            case DraftRoomRepository.FileIO.INVALID_FILE_FORMAT -> app.getMessageGenerator().generateMessage("Invalid file format", MessageTypes.WARNING);
            default -> {
                if (result != DraftRoomRepository.FileIO.OK) app.getMessageGenerator().generateMessage("Something went wrong, file couldn't be saved", MessageTypes.WARNING);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) importPattern(fileChooser.getSelectedFile(), false);
    }
}
