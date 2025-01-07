package raf.draft.dsw.controller.actions.file;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import java.io.File;
import java.util.Vector;

public abstract class AbstractSaveAction extends AbstractRoomAction {

    protected boolean canSave(){
        if (MainFrame.getInstance().getRoomViewController().getSelectedTab() == null) return false;
        DraftNodeDTO room = MainFrame.getInstance().getRoomViewController().getSelectedTab().getRoom();
        ApplicationFramework app = ApplicationFramework.getInstance();
        DraftNodeDTO project = app.getRepository().getProject(room.id());
        Vector<DraftNodeDTO> rooms = app.getRepository().overlaps(project.id());
        if (rooms.isEmpty()) return true;
        StringBuilder builder = new StringBuilder("There are overlapping elements in rooms: ");
        for (int i = 0; i < rooms.size(); i++){
            builder.append(rooms.get(i).name());
            if (i < rooms.size()-1) builder.append(", ");
        }
        builder.append(". Move overlapping elements before saving");
        app.getMessageGenerator().generateMessage(builder.toString(), MessageTypes.WARNING);
        return false;
    }
}
