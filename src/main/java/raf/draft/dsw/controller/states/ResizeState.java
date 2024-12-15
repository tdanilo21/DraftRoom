package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public class ResizeState extends AbstractState{
    public static final int VERTICALLY = 1;
    public static final int HORIZONTALLY = 2;

    private int type = 0;

    @Override
    void mousePressed(int x, int y, VisualElement element, RoomTab roomTab) {
        // Set type
    }

    @Override
    void mouseDragged(int dx, int dy, VisualElement element, RoomTab roomTab) {
        // Resize
    }
}
