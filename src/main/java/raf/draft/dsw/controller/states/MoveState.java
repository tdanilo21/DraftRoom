package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class MoveState extends AbstractState{
    private double xx, yy;
    private VisualElement element;

    @Override
    void mousePressed(double x, double y, VisualElement element, RoomTab roomTab) {
        xx = 0; yy = 0;
        this.element = element;
    }

    @Override
    public void mouseDragged(double dx, double dy, VisualElement element, RoomTab roomTab) {
        if (this.element != null){
            this.element.translate(dx, dy);
            xx += dx; yy += dy;
        }
        else{
            dx *= roomTab.getZoomFactor(); dy *= roomTab.getZoomFactor();
            roomTab.getF().preConcatenate(AffineTransform.getTranslateInstance(dx, dy));
            roomTab.repaint();
        }
    }

    @Override
    void mouseReleased(double x, double y, VisualElement element, RoomTab roomTab) {
        if (this.element != null && roomTab.overlaps(this.element)) this.element.translate(-xx, -yy);
    }
}
