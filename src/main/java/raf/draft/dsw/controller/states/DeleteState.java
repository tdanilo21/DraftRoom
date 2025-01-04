package raf.draft.dsw.controller.states;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.util.Vector;

public class DeleteState extends AbstractState{
    @Override
    void mouseClick(double x, double y, VisualElement element, RoomTab roomTab) {
        if (element != null) ApplicationFramework.getInstance().getRepository().deleteNode(element.getId());
    }
}
