package raf.draft.dsw.controller.states;

import raf.draft.dsw.controller.commands.AbstractCommand;
import raf.draft.dsw.controller.commands.EditCommand;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.dialogs.RequestDimensionsPane;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.structures.room.interfaces.*;

public class EditState extends AbstractState{
    @Override
    void mouseClick(double x, double y, VisualElement element, RoomTab roomTab) {
        super.mouseClick(x, y, element, roomTab);
        try{
            double[] results = new double[]{};
            if (element instanceof CircularVisualElement cElement)
                results = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width"}, new double[]{Math.round(cElement.getR())});
            else if (element instanceof TriangularVisualElement tElement)
                results = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width"}, new double[]{Math.round(tElement.getA())});
            else if (element instanceof RectangularVisualElement rElement){
                double w = rElement.getW(), h = rElement.getH();
                if (rElement instanceof Wall wall){
                    w -= 2*wall.getWallWidth();
                    h -= 2*wall.getWallWidth();
                }
                results = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width", "Height"}, new double[]{Math.round(w), Math.round(h)});
            }
            if (results == null) return;
            AbstractCommand command = EditCommand.getCommand(element, results);
            command.doCommand();
            roomTab.getCommandManager().addCommand(command);
        } catch (NumberFormatException e){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Dimensions must be numbers", MessageTypes.ERROR);
        }
    }
}
