package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public abstract class AbstractState {
    void mouseClick(int x, int y, VisualElement element, RoomTab roomTab){}
    void mousePressed(int x, int y, VisualElement element, RoomTab roomTab){}
    void mouseReleased(int x, int y, VisualElement element, RoomTab roomTab){}
    void mouseDragged(int dx, int dy, VisualElement element, RoomTab roomTab){}
    void mouseWheelScrolled(int x, int y, double wheelRotation, RoomTab roomTab){}
}
