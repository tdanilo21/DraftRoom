package raf.draft.dsw.controller.states;

import lombok.Getter;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public class StateManager {
    private AbstractState currentState;

    public StateManager(){
        currentState = new EditRoomState();
    }

    public void changeState(AbstractState newState){
        Integer selectedRoomId = MainFrame.getInstance().getRoomViewController().getSelectedTab().getRoom().id();
        ApplicationFramework app = ApplicationFramework.getInstance();
        if (!app.getRepository().isRoomInitialized(selectedRoomId)){
            app.getMessageGenerator().generateMessage("Room must be initialized first", MessageTypes.WARNING);
            return;
        }
        if (currentState instanceof SelectState) MainFrame.getInstance().getRoomViewController().getSelectedTab().setSelectionRectangle(null);
        currentState = newState;
        if (currentState instanceof DeleteState) ((DeleteState)currentState).deleteSelection();
    }

    public void mouseClick(double x, double y, VisualElement element, RoomTab roomTab){
        currentState.mouseClick(x, y, element, roomTab);
    }

    public void mouseDragged(double dx, double dy, VisualElement element, RoomTab roomTab){
        currentState.mouseDragged(dx, dy, element, roomTab);
    }

    public void mousePressed(double x, double y, VisualElement element, RoomTab roomTab){
        currentState.mousePressed(x, y, element, roomTab);
    }

    public void mouseReleased(double x, double y, VisualElement element, RoomTab roomTab){
        currentState.mouseReleased(x, y, element, roomTab);
    }

    public void mouseWheelScrolled(double x, double y, double wheelRotation, RoomTab roomTab){
        currentState.mouseWheelScrolled(x, y, wheelRotation, roomTab);
    }
}
