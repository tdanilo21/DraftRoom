package raf.draft.dsw.gui.swing.mainpanel.room.tab;

import lombok.Getter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.painters.AbstractPainter;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.painters.WallPainter;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;
import raf.draft.dsw.model.structures.room.interfaces.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.Vector;

@Getter
public class RoomTab extends JPanel {

    private final DraftNodeDTO room;
    private Vector<AbstractPainter> elements;

    public RoomTab(DraftNodeDTO room){
        elements = new Vector<>();
        this.room = room;
        updateElements();
        setBackground(Color.WHITE);
    }

    private void updateElements(){}

    public VisualElement getElementAt(int x, int y){
        return null;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for (AbstractPainter x : elements)
            x.paint(g, new AffineTransform());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return ((RoomTab)o).getRoom().equals(room);
    }
}
