package raf.draft.dsw.controller.states;

import lombok.Setter;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

@Setter
public class StateManager {
    private State currentState;

    public StateManager(){
        currentState = new EditRoomState();
    }

    public void mouseClick(int x, int y, VisualElement element, Integer roomId){
        currentState.mouseClick(x, y, element, roomId);
    }

    public void mouseDragged(int dx, int dy, VisualElement element, Integer roomId){
        currentState.mouseClick(dx, dy, element, roomId);
    }
}
