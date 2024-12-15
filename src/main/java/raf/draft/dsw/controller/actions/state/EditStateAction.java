package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.controller.states.EditState;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class EditStateAction extends AbstractRoomAction {
    public EditStateAction(){
        putValue(SMALL_ICON, loadIcon("/images/add.png"));
        putValue(NAME, "Edit");
        putValue(SHORT_DESCRIPTION, "Edit");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getStateManager().changeState(new EditState());
    }
}
