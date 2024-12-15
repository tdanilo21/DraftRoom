package raf.draft.dsw.controller.states;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public abstract class AbstractState {
    void mouseClick(int x, int y, VisualElement element, Integer roomId){}
    void mousePressed(int x, int y, VisualElement element, Integer roomId){}
    void mouseReleased(int x, int y, VisualElement element, Integer roomId){}
    void mouseDragged(int dx, int dy, VisualElement element, Integer roomId){}
    void mouseWheelScrolled(int x, int y, double wheelRotation, Integer roomId){}
}
