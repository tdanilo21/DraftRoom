package raf.draft.dsw.controller.states;

import lombok.Getter;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public class StateManager {
    private AbstractState currentState;
    private final RoomTab roomTab;

    public StateManager(RoomTab roomTab){
        this.roomTab = roomTab;
        if (ApplicationFramework.getInstance().getRepository().isRoomInitialized(roomTab.getRoom().id()))
            currentState = new SelectState();
        else
            currentState = new EditRoomState();
    }

    public void changeState(AbstractState newState){
        ApplicationFramework app = ApplicationFramework.getInstance();
        if (!app.getRepository().isRoomInitialized(roomTab.getRoom().id())){
            app.getMessageGenerator().generateMessage("Room must be initialized first", MessageTypes.WARNING);
            return;
        }
        currentState = newState;
    }

    public void mouseClick(double x, double y, VisualElement element){
        currentState.mouseClick(x, y, element, roomTab);
    }

    public void mouseDragged(double dx, double dy, VisualElement element){
        currentState.mouseDragged(dx, dy, element, roomTab);
    }

    public void mousePressed(double x, double y, VisualElement element){
        currentState.mousePressed(x, y, element, roomTab);
    }

    public void mouseReleased(double x, double y, VisualElement element){
        currentState.mouseReleased(x, y, element, roomTab);
    }

    public void mouseWheelScrolled(double x, double y, double wheelRotation){
        currentState.mouseWheelScrolled(x, y, wheelRotation, roomTab);
    }
}
