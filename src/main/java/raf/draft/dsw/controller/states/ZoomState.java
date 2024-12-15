package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;

import java.awt.geom.AffineTransform;

public class ZoomState extends AbstractState{
    @Override
    void mouseWheelScrolled(double x, double y, double wheelRotation, RoomTab roomTab) {
        AffineTransform f = AffineTransform.getTranslateInstance(-x, -y);
        f.concatenate(AffineTransform.getScaleInstance(1 - wheelRotation/10, 1 - wheelRotation/10));
        f.concatenate(AffineTransform.getTranslateInstance(x, y));
        roomTab.preConcatenateTransform(f);
        roomTab.repaint();
    }
}
