package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.controller.commands.AbstractCommand;
import raf.draft.dsw.controller.commands.DeleteCommand;
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
        if (roomTab == null) return;
        Vector<VisualElement> selection = roomTab.getSelection();
        if (!selection.isEmpty()){
            AbstractCommand command = new DeleteCommand(selection, roomTab.getRoom().id());
            command.doCommand();
            roomTab.getCommandManager().addCommand(command);
            roomTab.setSelectionRectangle(null);
        }
        roomTab.getStateManager().changeState(new DeleteState());
    }
}
