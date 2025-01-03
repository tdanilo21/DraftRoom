package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.Geometry;
import raf.draft.dsw.model.structures.room.SimpleRectangle;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.Point2D;

public class SelectState extends AbstractState{
    @Override
    public void mouseClick(double x, double y, VisualElement element, RoomTab roomTab) {
        if (element != null) roomTab.setSelectionRectangle(Geometry.getRectangleHull(element));
    }

    @Override
    void mousePressed(double x, double y, VisualElement element, RoomTab roomTab) {
        Point2D p = roomTab.getConverter().pointFromPixelSpace(new Point2D.Double(x, y));
        double a = roomTab.getConverter().lengthFromPixelSpace(1);
        roomTab.setSelectionRectangle(new SimpleRectangle(roomTab.getRoom().id(), a, a, p));
    }

    @Override
    public void mouseDragged(double dx, double dy, VisualElement element, RoomTab roomTab) {
        roomTab.scaleSelectionRectangle(dx, dy);
    }

    @Override
    void mouseReleased(double x, double y, VisualElement element, RoomTab roomTab) {
        roomTab.setSelectionRectangle(Geometry.getRectangleHull(roomTab.getSelection()));
    }
}
