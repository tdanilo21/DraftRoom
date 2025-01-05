package raf.draft.dsw.controller.actions.state;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.PixelSpaceConverter;
import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.Geometry;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.Vector;

public class RotateLeftAction extends AbstractRoomAction{
    public RotateLeftAction(){
        putValue(SMALL_ICON, loadIcon("/images/rotateLeft.png"));
        putValue(NAME, "Rotate left");
        putValue(SHORT_DESCRIPTION, "Rotate left");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RoomTab roomTab = MainFrame.getInstance().getRoomViewController().getSelectedTab();
        if (roomTab.getSelectionRectangle() == null) return;
        Vector<VisualElement> selection = roomTab.getSelection();
        double alpha = roomTab.getConverter().angleFromPixelSpace(Math.PI/2);
        for(VisualElement element : selection)
            element.rotate(alpha, roomTab.getSelectionRectangle().getCenter());
        roomTab.setSelectionRectangle(Geometry.getRectangleHull(roomTab.getSelection()));
    }
}
