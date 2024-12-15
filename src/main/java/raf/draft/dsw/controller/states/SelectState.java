package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public class SelectState extends AbstractState{
    @Override
    public void mouseClick(double x, double y, VisualElement element, RoomTab roomTab) {
        // Select element
    }

    @Override
    void mousePressed(double x, double y, VisualElement element, RoomTab roomTab) {
        // Create new selection rectangle
    }

    @Override
    public void mouseDragged(double dx, double dy, VisualElement element, RoomTab roomTab) {
        // Scale selection rectangle
    }
}
