package raf.draft.dsw.controller.actions.file;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Paths;

public class LoadPatternAction extends AbstractRoomAction {
    public LoadPatternAction(){
        putValue(SMALL_ICON, loadIcon("/images/info.png"));
        putValue(NAME, "Load pattern");
        putValue(SHORT_DESCRIPTION, "Load pattern");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (MainFrame.getInstance().getRoomViewController().getSelectedTab() == null) return;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        String path = STR."\{Paths.get("").toAbsolutePath()}\\src\\main\\resources\\patterns\\";
        fileChooser.setCurrentDirectory(new File(path));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            DraftNodeDTO room = MainFrame.getInstance().getRoomViewController().getSelectedTab().getRoom();
            int result = ApplicationFramework.getInstance().getRepository().getFileIO().loadPattern(file, room.id());
            if (result != DraftRoomRepository.FileIO.OK) ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Something went wrong, file couldn't be opened", MessageTypes.WARNING);
        }
    }
}
