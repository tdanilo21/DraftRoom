package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.controller.states.MoveState;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class MoveStateAction extends AbstractRoomAction {
    public MoveStateAction(){
        putValue(SMALL_ICON, loadIcon("/images/add.png"));
        putValue(NAME, "Move");
        putValue(SHORT_DESCRIPTION, "Move");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getStateManager().changeState(new MoveState());
    }
}
