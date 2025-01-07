package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.controller.states.EditState;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class EditStateAction extends AbstractRoomAction {
    public EditStateAction(){
        putValue(SMALL_ICON, loadIcon("/images/edit.png"));
        putValue(NAME, "Edit");
        putValue(SHORT_DESCRIPTION, "Edit");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (MainFrame.getInstance().getRoomViewController().getSelectedTab() == null) return;
        MainFrame.getInstance().getRoomViewController().getSelectedTab().getStateManager().changeState(new EditState());
    }
}
