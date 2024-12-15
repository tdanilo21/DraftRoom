package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public class SelectState extends AbstractState{
    @Override
    public void mouseClick(int x, int y, VisualElement element, RoomTab roomTab) {
        // Select element
    }

    @Override
    void mousePressed(int x, int y, VisualElement element, RoomTab roomTab) {
        // Create new selection rectangle
    }

    @Override
    public void mouseDragged(int dx, int dy, VisualElement element, RoomTab roomTab) {
        // Scale selection rectangle
    }
}
