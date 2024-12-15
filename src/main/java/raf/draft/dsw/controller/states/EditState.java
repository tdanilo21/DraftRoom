package raf.draft.dsw.controller.states;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.dialogs.RequestDimensionsPane;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.TriangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public class EditState extends AbstractState{
    @Override
    void mouseClick(int x, int y, VisualElement element, Integer roomId) {
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
                int[] results = RequestDimensionsPane.showDialog("Insert dimensions", new String[]{"Width", "Height"},
                                new int[]{(int)Math.round(rElement.getW()), (int)Math.round(rElement.getH())});
                if (results == null) return;
                rElement.setW(results[0]);
                rElement.setH(results[1]);
            }
        } catch (NumberFormatException e){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Dimensions must be numbers", MessageTypes.ERROR);
        }
    }
}
