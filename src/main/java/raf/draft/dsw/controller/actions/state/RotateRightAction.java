package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.PixelSpaceConverter;
import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.controller.commands.AbstractCommand;
import raf.draft.dsw.controller.commands.RotateCommand;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.Geometry;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.Vector;

public class RotateRightAction extends AbstractRoomAction {

    public RotateRightAction(){
        putValue(SMALL_ICON, loadIcon("/images/rotateRight.png"));
        putValue(NAME, "Rotate right");
        putValue(SHORT_DESCRIPTION, "Rotate right");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RoomTab roomTab = MainFrame.getInstance().getRoomViewController().getSelectedTab();
        if (roomTab.getSelectionRectangle() == null) return;
        Vector<VisualElement> selection = roomTab.getSelection();
        double alpha = roomTab.getConverter().angleFromPixelSpace(3*Math.PI/2);
        AbstractCommand command = new RotateCommand(selection, roomTab.getSelectionRectangle().getCenter(), alpha);
        command.doCommand();
        roomTab.getCommandManager().addCommand(command);
        roomTab.setSelectionRectangle(Geometry.getRectangleHull(roomTab.getSelection()));
    }
}
