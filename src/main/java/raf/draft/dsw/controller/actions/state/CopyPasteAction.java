package raf.draft.dsw.controller.actions.state;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.controller.commands.AbstractCommand;
import raf.draft.dsw.controller.commands.AddCommand;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.event.ActionEvent;
import java.util.Vector;

public class CopyPasteAction extends AbstractRoomAction {

    public CopyPasteAction(){
        putValue(SMALL_ICON, loadIcon("/images/copyPaste.png"));
        putValue(NAME, "Copy & Paste");
        putValue(SHORT_DESCRIPTION, "Copy & Paste");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        RoomTab roomTab = MainFrame.getInstance().getRoomViewController().getSelectedTab();
        if (roomTab == null) return;
        Vector<VisualElement> v = new Vector<>();
        for(VisualElement element : roomTab.getSelection()){
            VisualElement clone = (VisualElement)element.clone();
            clone.translate(roomTab.getConverter().lengthFromPixelSpace(20), 0);
            v.add(clone);
        }
        AbstractCommand command = new AddCommand(v, roomTab.getRoom().id());
        roomTab.getCommandManager().addCommand(command);
    }
}
