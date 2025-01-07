package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.controller.states.ResizeState;
import raf.draft.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class ResizeStateAction extends AbstractRoomAction {
    public ResizeStateAction(){
        putValue(SMALL_ICON, loadIcon("/images/resize.png"));
        putValue(NAME, "Resize");
        putValue(SHORT_DESCRIPTION, "Resize");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (MainFrame.getInstance().getRoomViewController().getSelectedTab() == null) return;
        MainFrame.getInstance().getRoomViewController().getSelectedTab().getStateManager().changeState(new ResizeState());
    }
}
