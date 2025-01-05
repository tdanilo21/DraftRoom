package raf.draft.dsw.gui.swing.mainpanel.room;

import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import javax.swing.event.ChangeEvent;
import java.util.Vector;

public class RoomViewController implements ISubscriber {
    private final RoomView roomView;

    public RoomViewController(RoomView roomView){
        this.roomView = roomView;
        roomView.getModel().addChangeListener((ChangeEvent e) -> {
            RoomTab roomTab = getSelectedTab();
            if (roomTab != null) roomTab.updateElements();
        });
    }

    private Vector<DraftNodeDTO> getRoomsInSubtree(DraftNodeDTO node){
        Vector<DraftNodeDTO> subtree = ApplicationFramework.getInstance().getRepository().getSubtree(node.id());
        Vector<DraftNodeDTO> rooms = new Vector<>();
        for (DraftNodeDTO child : subtree)
            if (child.type() == DraftNodeTypes.ROOM)
                rooms.add(child);
        return rooms;
    }

    private void addTabs(DraftNodeDTO node){
        Vector<DraftNodeDTO> rooms = getRoomsInSubtree(node);
        for (int i = 0; i < rooms.size(); i++)
            roomView.addTab(rooms.get(i));
    }

    private void removeTabs(DraftNodeDTO node){
        Vector<DraftNodeDTO> rooms = getRoomsInSubtree(node);
        for (DraftNodeDTO room : rooms)
            roomView.removeTab(room);
    }

    private void updateTabs(DraftNodeDTO node){
        Vector<DraftNodeDTO> rooms = getRoomsInSubtree(node);
        for (DraftNodeDTO room : rooms)
            roomView.updateTab(room);
    }

    @Override
    public void notify(EventTypes type, Object state) {
        if (state instanceof DraftNodeDTO node) {
            switch (type){
                case EventTypes.NODE_DOUBLE_CLICK, EventTypes.NODE_CREATED -> addTabs(node);
                case EventTypes.NODE_DELETED -> removeTabs(node);
                case EventTypes.NODE_EDITED -> updateTabs(node);
            }
        }
    }

    public RoomTab getSelectedTab(){
        return (RoomTab)roomView.getSelectedComponent();
    }
}
