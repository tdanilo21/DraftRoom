package raf.draft.dsw.controller.states;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public interface State {
    void mouseClick(int x, int y, VisualElement element, Integer roomId);
    void mouseDragged(int dx, int dy, VisualElement element, Integer roomId);
}
