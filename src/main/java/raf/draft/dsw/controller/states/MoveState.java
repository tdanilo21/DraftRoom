package raf.draft.dsw.controller.states;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public class MoveState implements AbstractState {
    @Override
    public void mouseClick(int x, int y, VisualElement element, Integer roomId) {}

    @Override
    public void mouseDragged(int dx, int dy, VisualElement element, Integer roomId) {
        if (element != null) element.translate(dx, dy);
        else{
            // Move room view
        }
    }
}
