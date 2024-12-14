package raf.draft.dsw.gui.swing.mainpanel.room.tab;

import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class RoomTabMouseListener extends MouseAdapter {
    private final RoomTab tab;

    public RoomTabMouseListener(RoomTab tab){
        this.tab = tab;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        VisualElement element = tab.getElementAt(e.getX(), e.getY());
        MainFrame.getInstance().getStateManager().mouseClick(e.getX(), e.getY(), element, tab.getRoom().id());
    }
}
