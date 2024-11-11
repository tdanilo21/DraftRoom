package raf.draft.dsw.gui.swing.mainpanel.room;

import lombok.Getter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;

import javax.swing.*;

public class RoomTab extends JPanel {
    @Getter
    private final DraftNodeDTO node;
    public RoomTab(DraftNodeDTO node){
        this.node = node;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return ((RoomTab)o).getNode().equals(node);
    }
}
