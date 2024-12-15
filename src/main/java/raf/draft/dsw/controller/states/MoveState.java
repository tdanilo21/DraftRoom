package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.AffineTransform;

public class MoveState extends AbstractState{
    VisualElement element;

    @Override
    void mousePressed(double x, double y, VisualElement element, RoomTab roomTab) {
        this.element = element;
    }

    @Override
    public void mouseDragged(double dx, double dy, VisualElement element, RoomTab roomTab) {
        if (this.element != null) this.element.translate(dx, dy);
        else{
            dx *= roomTab.getZoomFactor(); dy *= roomTab.getZoomFactor();
            roomTab.getF().preConcatenate(AffineTransform.getTranslateInstance(dx, dy));
            roomTab.repaint();
        }
    }
}
