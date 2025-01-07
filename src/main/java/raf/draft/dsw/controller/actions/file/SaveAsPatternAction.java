package raf.draft.dsw.controller.actions.file;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SaveAsPatternAction extends AbstractSaveAction {
    public SaveAsPatternAction(){
        putValue(SMALL_ICON, loadIcon("/images/info.png"));
        putValue(NAME, "Save as pattern");
        putValue(SHORT_DESCRIPTION, "Save as pattern");
    }

    private void savePattern(Integer roomId, boolean force){
        ApplicationFramework app = ApplicationFramework.getInstance();
        int result = app.getRepository().getFileIO().saveAsPattern(roomId, force);
        if (result == DraftRoomRepository.FileIO.FILE_EXISTS) {
            int choice = JOptionPane.showConfirmDialog(null, "File with given path already exists. Do you want to replace it?", "Warning", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) savePattern(roomId, true);
        } else if (result != DraftRoomRepository.FileIO.OK)  app.getMessageGenerator().generateMessage("Something went wrong, file couldn't be saved", MessageTypes.WARNING);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canSave()) {
            if (MainFrame.getInstance().getRoomViewController().getSelectedTab() == null) return;
            DraftNodeDTO room = MainFrame.getInstance().getRoomViewController().getSelectedTab().getRoom();
            savePattern(room.id(), false);
        }
    }
}
