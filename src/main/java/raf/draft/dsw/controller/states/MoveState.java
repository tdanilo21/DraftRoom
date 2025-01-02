package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.Geometry;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

public class MoveState extends AbstractState{
    private double xx, yy;
    private Vector<VisualElement> elements;

    @Override
    void mousePressed(double x, double y, VisualElement element, RoomTab roomTab) {
        xx = 0; yy = 0;
        elements = new Vector<>();
        Point2D p = roomTab.getConverter().pointFromPixelSpace(new Point2D.Double(x, y));
        if (Geometry.contains(roomTab.getSelectionRectangle(), p)){
            elements.addAll(roomTab.getSelection());
            elements.add(roomTab.getSelectionRectangle());
        }
        else {
            roomTab.setSelectionRectangle(null);
            if (element != null) elements.add(element);
        }
    }

    @Override
    public void mouseDragged(double dx, double dy, VisualElement element, RoomTab roomTab) {
        if (!elements.isEmpty()){
            dx = roomTab.getConverter().lengthFromPixelSpace(dx);
            dy = roomTab.getConverter().lengthFromPixelSpace(dy);
            for (VisualElement e : elements)
                e.translate(dx, dy);
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
        boolean succ = true;
        for (VisualElement e : elements)
            if (roomTab.overlaps(e))
                succ = false;
        if (!succ)
            for (VisualElement e : elements)
                e.translate(-xx, -yy);
    }
}
