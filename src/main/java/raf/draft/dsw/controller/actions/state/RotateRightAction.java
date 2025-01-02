package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.PixelSpaceConverter;
import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class RotateRightAction extends AbstractRoomAction {

    public RotateRightAction(){
        putValue(SMALL_ICON, loadIcon("/images/rotateRight.png"));
        putValue(NAME, "Rotate right");
        putValue(SHORT_DESCRIPTION, "Rotate right");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Vector<VisualElement> selection = MainFrame.getInstance().getRoomViewController().getSelectedTab().getSelection();
        PixelSpaceConverter converter = MainFrame.getInstance().getRoomViewController().getSelectedTab().getConverter();
        for(VisualElement element : selection)
            element.rotate(converter.angleFromPixelSpace(3*Math.PI/2));
    }
}
