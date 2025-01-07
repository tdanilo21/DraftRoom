package raf.draft.dsw.controller.actions;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.gui.swing.organizemyroom.OrganizeMyRoomFrame;
import raf.draft.dsw.model.messages.MessageTypes;

import java.awt.event.ActionEvent;

public class OrganizeMyRoomAction extends AbstractRoomAction{
    public OrganizeMyRoomAction(){
        putValue(SMALL_ICON, loadIcon("/images/organize.png"));
        putValue(NAME, "Organize my room");
        putValue(SHORT_DESCRIPTION, "Organize my room");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (MainFrame.getInstance().getRoomViewController().getSelectedTab() == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Open a room", MessageTypes.WARNING);
            return;
        }
        RoomTab roomTab = MainFrame.getInstance().getRoomViewController().getSelectedTab();
        if (!ApplicationFramework.getInstance().getRepository().isRoomInitialized(roomTab.getRoom().id())){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Room is not initialized", MessageTypes.WARNING);
            return;
        }
        OrganizeMyRoomFrame frame = new OrganizeMyRoomFrame();
        frame.setVisible(true);
    }
}
