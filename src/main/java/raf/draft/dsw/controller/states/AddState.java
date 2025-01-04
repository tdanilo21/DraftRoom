package raf.draft.dsw.controller.states;


import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.dialogs.RequestDimensionsPane;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

public class AddState extends AbstractState{

    @Override
    public void mouseClick(double x, double y, VisualElement element, RoomTab roomTab) {
        super.mouseClick(x, y, element, roomTab);
        if (element != null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Can not create new element inside another one.", MessageTypes.ERROR);
            return;
        }
        Vector<VisualElementTypes> values = new Vector<>();
        for (VisualElementTypes type : VisualElementTypes.values())
            if (type != VisualElementTypes.WALL) values.add(type);
        VisualElementTypes selectedType = (VisualElementTypes)JOptionPane.showInputDialog(null, "Choose element type",
                        "Type selection", JOptionPane.QUESTION_MESSAGE, null, values.toArray(), null);
        if (selectedType == null) return;
        double[] dims;
        try{
            if (selectedType == VisualElementTypes.BOILER || selectedType == VisualElementTypes.TOILET
                    || selectedType == VisualElementTypes.DOOR || selectedType == VisualElementTypes.SINK){
                int[] result = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width"}, null);
                if (result == null) return;
                dims = new double[]{result[0]};
            } else {
                int[] result = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width", "Height"}, null);
                if (result == null) return;
                dims = new double[]{result[0], result[1]};
            }
        } catch(NumberFormatException e){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Dimensions must be numbers", MessageTypes.ERROR);
            return;
        }
        Point2D location = roomTab.getConverter().pointFromPixelSpace(new Point2D.Double(x, y));
        if (selectedType == VisualElementTypes.BOILER || selectedType == VisualElementTypes.TOILET)
            dims[0] /= 2;
        boolean swapped = false;
        if (selectedType == VisualElementTypes.BATH_TUB && dims[0] > dims[1]){
            double t = dims[0]; dims[0] = dims[1]; dims[1] = t;
            swapped = true;
        }
        VisualElement newElement = ApplicationFramework.getInstance().getRepository().createRoomElement(selectedType, roomTab.getRoom().id(), location, dims);
        if (swapped) newElement.rotate(roomTab.getConverter().angleFromPixelSpace(Math.PI/2), new Point2D.Double(location.getX() + dims[0] / 2, location.getY() + dims[0] / 2));
    }
}
