package raf.draft.dsw.gui.swing.mainpanel.room;

import lombok.Getter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;

import javax.swing.*;
import java.awt.*;

@Getter
public class RoomTab extends JPanel {

    private final DraftNodeDTO room;
    public RoomTab(DraftNodeDTO room){
        this.room = room;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return ((RoomTab)o).getRoom().equals(room);
    }
}
