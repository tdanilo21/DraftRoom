package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class ZoomState extends AbstractState{
    @Override
    void mouseWheelScrolled(double x, double y, double wheelRotation, RoomTab roomTab) {
        double newZoomFactor = roomTab.getZoomFactor() * (1 - wheelRotation/10);
        if (newZoomFactor < 0.1 || newZoomFactor > 2) return;
        Point2D p = roomTab.getF().transform(new Point2D.Double(x, y), null);
        AffineTransform f = AffineTransform.getTranslateInstance(p.getX(), p.getY());
        f.concatenate(AffineTransform.getScaleInstance(1 - wheelRotation/10, 1 - wheelRotation/10));
        f.concatenate(AffineTransform.getTranslateInstance(-p.getX(), -p.getY()));
        roomTab.getF().preConcatenate(f);
        roomTab.setZoomFactor(newZoomFactor);
        roomTab.repaint();
    }
}
