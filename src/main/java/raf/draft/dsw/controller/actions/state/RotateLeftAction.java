package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class RotateLeftAction extends AbstractRoomAction{
    public RotateLeftAction(){
        putValue(SMALL_ICON, loadIcon("/images/rotateLeft.png"));
        putValue(NAME, "Rotate left");
        putValue(SHORT_DESCRIPTION, "Rotate left");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Vector<VisualElement> selection = MainFrame.getInstance().getRoomViewController().getSelectedTab().getSelection();
        for(VisualElement element : selection) {
            element.rotate(-Math.PI/2);
        }
    }
}
