package raf.draft.dsw.gui.swing.mainpanel.room.tab;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class RoomTabMouseListener extends MouseAdapter {
    private final RoomTab roomTab;
    private double lastX, lastY;

    public RoomTabMouseListener(RoomTab roomTab){
        this.roomTab = roomTab;
    }

    private Point2D toDefaultPixelSpace(int x, int y){
        try{
            return roomTab.getF().inverseTransform(new Point2D.Double(x, y), null);
        } catch (NoninvertibleTransformException e){
            System.err.println(e.getMessage());
            return new Point2D.Double();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point2D p = toDefaultPixelSpace(e.getX(), e.getY());
        VisualElement element = roomTab.getElementAt(p);
        roomTab.getStateManager().mouseClick(p.getX(), p.getY(), element);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point2D p = toDefaultPixelSpace(e.getX(), e.getY());
        lastX = p.getX();
        lastY = p.getY();
        VisualElement element = roomTab.getElementAt(p);
        roomTab.getStateManager().mousePressed(p.getX(), p.getY(), element);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point2D p = toDefaultPixelSpace(e.getX(), e.getY());
        lastX = p.getX();
        lastY = p.getY();
        VisualElement element = roomTab.getElementAt(p);
        roomTab.getStateManager().mouseReleased(p.getX(), p.getY(), element);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point2D p = toDefaultPixelSpace(e.getX(), e.getY());
        double dx = p.getX() - lastX, dy = p.getY() - lastY;
        VisualElement element = roomTab.getElementAt(p);
        roomTab.getStateManager().mouseDragged(dx, dy, element);
        lastX += dx; lastY += dy;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Point2D p = toDefaultPixelSpace(e.getX(), e.getY());
        roomTab.getStateManager().mouseWheelScrolled(p.getX(), p.getY(), e.getPreciseWheelRotation());
    }
}
