package raf.draft.dsw.controller.states;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public class MoveState extends State{
    @Override
    public void mouseDragged(int dx, int dy, VisualElement element, Integer roomId) {
        if (element != null) element.translate(dx, dy);
        else{
            // Move room view
        }
    }
}
