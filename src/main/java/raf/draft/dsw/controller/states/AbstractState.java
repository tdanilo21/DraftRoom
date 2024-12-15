package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public abstract class AbstractState {
    void mouseClick(double x, double y, VisualElement element, RoomTab roomTab){}
    void mousePressed(double x, double y, VisualElement element, RoomTab roomTab){}
    void mouseReleased(double x, double y, VisualElement element, RoomTab roomTab){}
    void mouseDragged(double dx, double dy, VisualElement element, RoomTab roomTab){}
    void mouseWheelScrolled(double x, double y, double wheelRotation, RoomTab roomTab){}
}
