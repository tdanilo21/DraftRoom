package raf.draft.dsw.controller.states;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.dialogs.RequestDimensionsPane;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public class EditRoomState extends AbstractState{

    @Override
    public void mouseClick(int x, int y, VisualElement element, Integer roomId) {
        try{
            int[] dims = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width", "Height"}, null);
            //ApplicationFramework.getInstance().getRepository().initializeRoom(roomId, );
        } catch (NumberFormatException e){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Dimensions must be numbers", MessageTypes.ERROR);
        }
    }
}
