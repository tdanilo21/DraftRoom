package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.SimpleRectangle;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.Point2D;

public class SelectState extends AbstractState{
    @Override
    public void mouseClick(double x, double y, VisualElement element, RoomTab roomTab) {
        if (element != null) roomTab.select(element);
    }

    @Override
    void mousePressed(double x, double y, VisualElement element, RoomTab roomTab) {
        roomTab.setSelectionRectangle(new SimpleRectangle(roomTab.getRoom().id(), 1, 1, new Point2D.Double(x, y)));
    }

    @Override
    public void mouseDragged(double dx, double dy, VisualElement element, RoomTab roomTab) {
        roomTab.scaleSelectionRectangle(dx, dy);
    }
}
