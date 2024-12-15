package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.controller.states.ZoomState;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class ZoomStateAction extends AbstractRoomAction {
    public ZoomStateAction(){
        putValue(SMALL_ICON, loadIcon("/images/add.png"));
        putValue(NAME, "Zoom");
        putValue(SHORT_DESCRIPTION, "Zoom");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getStateManager().changeState(new ZoomState());
    }
}
