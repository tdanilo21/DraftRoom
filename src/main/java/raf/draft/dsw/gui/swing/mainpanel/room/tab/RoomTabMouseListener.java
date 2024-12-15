package raf.draft.dsw.gui.swing.mainpanel.room.tab;

import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;

public class RoomTabMouseListener extends MouseAdapter {
    private final RoomTab tab;
    private int lastX, lastY;

    public RoomTabMouseListener(RoomTab tab){
        this.tab = tab;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        VisualElement element = tab.getElementAt(e.getX(), e.getY());
        MainFrame.getInstance().getStateManager().mouseClick(e.getX(), e.getY(), element, tab);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - lastX, dy = e.getY() - lastY;
        VisualElement element = tab.getElementAt(e.getX(), e.getY());
        MainFrame.getInstance().getStateManager().mouseDragged(dx, dy, element, tab);
        lastX += dx; lastY += dy;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}
