package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.AffineTransform;

public class MoveState extends AbstractState{
    @Override
    public void mouseDragged(double dx, double dy, VisualElement element, RoomTab roomTab) {
        if (element != null) element.translate(dx, dy);
        else{
            roomTab.preConcatenateTransform(AffineTransform.getTranslateInstance(dx, dy));
            roomTab.repaint();
        }
    }
}
