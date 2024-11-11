package raf.draft.dsw.gui.swing.mainpanel.room;

import raf.draft.dsw.controller.dtos.DraftNodeDTO;

import javax.swing.*;

public class RoomView extends JTabbedPane {
    public RoomView(){
        super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    private int getTabIndex(DraftNodeDTO node) {
        for (int i = 0; i < getTabCount(); i++)
            if (((RoomTab)getComponentAt(i)).getNode().equals(node))
                return i;
        return -1;
    }

    public void addTab(DraftNodeDTO node){
        RoomTab newTab = new RoomTab(node);
        RoomTabComponent newTabComponent = new RoomTabComponent(this, node.name());
        add(newTab);
        int i = indexOfComponent(newTab);
        setTabComponentAt(i, newTabComponent);
        setBackgroundAt(i, node.color());
    }

    public void removeTab(DraftNodeDTO node){
        int i = getTabIndex(node);
        if (i != -1) removeTabAt(i);
    }

    public void updateTab(DraftNodeDTO node){
        int i = getTabIndex(node);
        RoomTabComponent tabComponent = (RoomTabComponent) getTabComponentAt(i);
        tabComponent.setTitle(node.name());
        setBackgroundAt(i, node.color());
    }
}
