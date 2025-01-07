package raf.draft.dsw.controller.states;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.dialogs.RequestDimensionsPane;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;

public class EditRoomState extends AbstractState{

    @Override
    public void mouseClick(double x, double y, VisualElement element, RoomTab roomTab) {
        try{
            double[] dims = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width", "Height"}, null);
            if (dims == null) return;
            ApplicationFramework.getInstance().getRepository().initializeRoom(roomTab.getRoom().id(), dims[0], dims[1]);
        } catch (NumberFormatException e){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Dimensions must be numbers", MessageTypes.ERROR);
        }
    }
}
