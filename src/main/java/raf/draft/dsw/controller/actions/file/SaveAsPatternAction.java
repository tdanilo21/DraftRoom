package raf.draft.dsw.controller.actions.file;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.dtos.DraftNodeDTO;

import java.awt.event.ActionEvent;

public class SaveAsPatternAction extends AbstractRoomAction {
    public SaveAsPatternAction(){
        putValue(SMALL_ICON, loadIcon("/images/info.png"));
        putValue(NAME, "Save as pattern");
        putValue(SHORT_DESCRIPTION, "Save as pattern");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (MainFrame.getInstance().getRoomViewController().getSelectedTab() == null) return;
        DraftNodeDTO room = MainFrame.getInstance().getRoomViewController().getSelectedTab().getRoom();
        ApplicationFramework.getInstance().getRepository().saveAsPattern(room.id());
    }
}
