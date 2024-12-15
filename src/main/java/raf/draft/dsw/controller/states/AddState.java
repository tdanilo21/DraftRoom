package raf.draft.dsw.controller.states;


import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.dialogs.RequestDimensionsPane;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import javax.swing.*;
import java.awt.*;

public class AddState implements AbstractState {

    @Override
    public void mouseClick(int x, int y, VisualElement element, Integer roomId) {
        if (element != null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Can not create new element inside another one.", MessageTypes.ERROR);
            return;
        }
        String[] type = new String[9];
        type[0] = "Bathtub";
        type[1] = "Bed";
        type[2] = "Boiler";
        type[3] = "Closet";
        type[4] = "Door";
        type[5] = "Sink";
        type[6] = "Table";
        type[7] = "Toilet";
        type[8] = "Washing machine";
        VisualElementTypes selectedType = (VisualElementTypes)JOptionPane.showInputDialog(null, "Choose element type",
                        "Type selection", JOptionPane.QUESTION_MESSAGE, null, VisualElementTypes.values(), null);
        double[] dims;
        try{
            if (selectedType == VisualElementTypes.BOILER || selectedType == VisualElementTypes.TOILET
                    || selectedType == VisualElementTypes.DOOR || selectedType == VisualElementTypes.SINK){
                int[] result = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width"}, null);
                if (result == null) return;
                dims = new double[]{result[0]};
                if (selectedType == VisualElementTypes.BOILER || selectedType == VisualElementTypes.TOILET)
                    dims[0] /= 2;
            } else {
                int[] result = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width", "Height"}, null);
                if (result == null) return;
                dims = new double[]{result[0], result[1]};
            }
        } catch(NumberFormatException e){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Dimensions must be numbers", MessageTypes.ERROR);
            return;
        }
        ApplicationFramework.getInstance().getRepository().createRoomElement(selectedType, roomId, new Point(x, y), dims);
    }

    @Override
    public void mouseDragged(int dx, int dy, VisualElement element, Integer roomId){}
}
