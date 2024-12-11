package raf.draft.dsw.gui.swing.mainpanel.room;

import raf.draft.dsw.controller.dtos.DraftNodeDTO;

import javax.swing.*;

public class RoomView extends JTabbedPane {
    public RoomView(){
        super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    private int getTabIndex(DraftNodeDTO room) {
        for (int i = 0; i < getTabCount(); i++)
            if (((RoomTab)getComponentAt(i)).getRoom().equals(room))
                return i;
        return -1;
    }

    public void addTab(DraftNodeDTO room){
        RoomTab newTab = new RoomTab(room);
        RoomTabComponent newTabComponent = new RoomTabComponent(this, room.name());
        add(newTab);
        int i = indexOfComponent(newTab);
        setTabComponentAt(i, newTabComponent);
        setBackgroundAt(i, room.color());
    }

    public void removeTab(DraftNodeDTO room){
        int i = getTabIndex(room);
        if (i != -1) removeTabAt(i);
    }

    public void updateTab(DraftNodeDTO room){
        int i = getTabIndex(room);
        RoomTabComponent tabComponent = (RoomTabComponent) getTabComponentAt(i);
        tabComponent.setTitle(room.name());
        setBackgroundAt(i, room.color());
    }
}
