package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.controller.states.MoveState;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class MoveStateAction extends AbstractRoomAction {
    public MoveStateAction(){
        putValue(SMALL_ICON, loadIcon("/images/move.png"));
        putValue(NAME, "Move");
        putValue(SHORT_DESCRIPTION, "Move");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (MainFrame.getInstance().getRoomViewController().getSelectedTab() == null) return;
        MainFrame.getInstance().getRoomViewController().getSelectedTab().getStateManager().changeState(new MoveState());
    }
}
