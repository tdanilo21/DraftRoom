package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.controller.states.AddState;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class AddStateAction extends AbstractRoomAction {
    public AddStateAction(){
        putValue(SMALL_ICON, loadIcon("/images/add.png"));
        putValue(NAME, "Add");
        putValue(SHORT_DESCRIPTION, "Add");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getRoomViewController().getSelectedTab().getStateManager().changeState(new AddState());
    }
}
