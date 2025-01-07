package raf.draft.dsw.controller.actions.file;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.io.File;

public abstract class AbstractSaveProjectAction extends AbstractSaveAction{
    public AbstractSaveProjectAction(){
        putValue(SMALL_ICON, loadIcon("/images/info.png"));
    }

    protected DraftNodeDTO getProject(){
        ApplicationFramework app = ApplicationFramework.getInstance();
        if (MainFrame.getInstance().getRoomViewController().getSelectedTab() != null) {
            DraftNodeDTO room = MainFrame.getInstance().getRoomViewController().getSelectedTab().getRoom();
            if (room != null) return app.getRepository().getProject(room.id());
        }
        return null;
    }

    private void saveAs(File file, boolean force){
        ApplicationFramework app = ApplicationFramework.getInstance();
        DraftNodeDTO project = getProject();
        int result = app.getRepository().getFileIO().saveProjectAs(file, project.id(), force);
        switch (result){
            case DraftRoomRepository.FileIO.FILE_EXISTS -> {
                int choice = JOptionPane.showConfirmDialog(null, "File with given path already exists. Do you want to replace it?", "Warning", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) saveAs(file, true);
            }
            case DraftRoomRepository.FileIO.INVALID_FILE_TYPE -> app.getMessageGenerator().generateMessage(STR."\{file.getName()} is not a directory", MessageTypes.WARNING);
            default -> {
                if (result != DraftRoomRepository.FileIO.OK) app.getMessageGenerator().generateMessage("Something went wrong, file couldn't be saved", MessageTypes.WARNING);
            }
        }
    }

    protected void saveAs(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) saveAs(fileChooser.getSelectedFile(), false);
    }
}
