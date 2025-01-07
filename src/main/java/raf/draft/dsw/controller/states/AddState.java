package raf.draft.dsw.controller.states;


import raf.draft.dsw.controller.commands.AbstractCommand;
import raf.draft.dsw.controller.commands.AddCommand;
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
                dims = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width"}, null);
                if (dims == null) return;
            } else {
                dims = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width", "Height"}, null);
                if (dims == null) return;
            }
        } catch(NumberFormatException e){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Dimensions must be numbers", MessageTypes.ERROR);
            return;
        }
        Point2D location = roomTab.getConverter().pointFromPixelSpace(new Point2D.Double(x, y));
        if (selectedType == VisualElementTypes.BOILER || selectedType == VisualElementTypes.TOILET) dims[0] /= 2;
        VisualElement newElement = ApplicationFramework.getInstance().getRepository().createRoomElement(selectedType, roomTab.getRoom().id(), location, dims);
        AbstractCommand command = new AddCommand(new Vector<>(){{add(newElement);}}, roomTab.getRoom().id());
        roomTab.getCommandManager().addCommand(command);
    }
}
