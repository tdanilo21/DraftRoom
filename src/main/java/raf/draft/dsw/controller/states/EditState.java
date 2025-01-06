package raf.draft.dsw.controller.states;

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
            if (element instanceof CircularVisualElement cElement){
                int[] results = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width"}, new int[]{(int)Math.round(cElement.getR())});
                if (results == null) return;
                cElement.setR(results[0]);
            } else if (element instanceof TriangularVisualElement tElement){
                int[] results = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width"}, new int[]{(int)Math.round(tElement.getA())});
                if (results == null) return;
                tElement.setA(results[0]);
            } else if (element instanceof RectangularVisualElement rElement){
                double w = rElement.getW(), h = rElement.getH();
                if (rElement instanceof Wall wall){
                    w -= 2*wall.getWallWidth();
                    h -= 2*wall.getWallWidth();
                }
                int[] results = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width", "Height"}, new int[]{(int)Math.round(w), (int)Math.round(h)});
                if (results == null) return;
                rElement.setW(results[0]);
                rElement.setH(results[1]);
            }
        } catch (NumberFormatException e){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Dimensions must be numbers", MessageTypes.ERROR);
        }
    }
}
