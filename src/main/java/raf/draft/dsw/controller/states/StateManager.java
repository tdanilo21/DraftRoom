package raf.draft.dsw.controller.states;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public class StateManager {
    private State currentState;

    public StateManager(){
        currentState = new EditRoomState();
    }

    public void changeState(State newState){
        currentState = newState;
    }

    public void mouseClick(int x, int y, VisualElement element, Integer roomId){
        currentState.mouseClick(x, y, element, roomId);
    }

    public void mouseDragged(int dx, int dy, VisualElement element, Integer roomId){
        currentState.mouseDragged(dx, dy, element, roomId);
    }

    public void mousePressed(int x, int y, VisualElement element, Integer roomId){
        currentState.mousePressed(x, y, element, roomId);
    }

    public void mouseReleased(int x, int y, VisualElement element, Integer roomId){
        currentState.mouseReleased(x, y, element, roomId);
    }

    public void mouseWheelScrolled(int x, int y, double wheelRotation, Integer roomId){
        currentState.mouseWheelScrolled(x, y, wheelRotation, roomId);
    }
}
