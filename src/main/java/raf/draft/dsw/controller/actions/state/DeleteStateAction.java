package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.controller.states.DeleteState;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.event.ActionEvent;
import java.util.Vector;

public class DeleteStateAction extends AbstractRoomAction {
    public DeleteStateAction(){
        putValue(SMALL_ICON, loadIcon("/images/delete.png"));
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RoomTab roomTab = MainFrame.getInstance().getRoomViewController().getSelectedTab();
        Vector<VisualElement> selection = roomTab.getSelection();
        for (VisualElement element : selection)
            ApplicationFramework.getInstance().getRepository().deleteNode(element.getId());
        selection.clear();
        roomTab.setSelectionRectangle(null);
        roomTab.getStateManager().changeState(new DeleteState());
    }
}
