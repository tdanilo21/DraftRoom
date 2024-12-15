package raf.draft.dsw.controller.states;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public class SelectState extends State{
    @Override
    public void mouseClick(int x, int y, VisualElement element, Integer roomId) {
        // Select element
    }

    @Override
    void mousePressed(int x, int y, VisualElement element, Integer roomId) {
        // Create new selection rectangle
    }

    @Override
    public void mouseDragged(int dx, int dy, VisualElement element, Integer roomId) {
        // Scale selection rectangle
    }
}
