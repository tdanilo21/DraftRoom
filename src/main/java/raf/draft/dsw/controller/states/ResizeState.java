package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.SimpleRectangle;
import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.TriangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.Point2D;

public class ResizeState extends AbstractState{
    public static final int SX = 1;
    public static final int SY = 2;

    private Point2D p;
    private int scale;

    @Override
    void mousePressed(double x, double y, VisualElement element, RoomTab roomTab) {
        scale = 0;
        SimpleRectangle selection = roomTab.getSelectionRectangle();
        if (selection == null) return;
        Point2D q = roomTab.getConverter().pointFromPixelSpace(new Point2D.Double(x, y));
        double margin = roomTab.getConverter().lengthFromPixelSpace(10);
        int edges = selection.getEdges(q, margin);
        if (edges == 0) return;
        p = roomTab.getConverter().pointToPixelSpace(selection.getLocation());
        double w = roomTab.getConverter().lengthToPixelSpace(selection.getW());
        double h = roomTab.getConverter().lengthToPixelSpace(selection.getH());
        if ((edges & SimpleRectangle.DOWN) > 0){
            scale |= SY;
            p = new Point2D.Double(p.getX(), p.getY() + h);
        }
        if ((edges & SimpleRectangle.RIGHT) > 0) scale |= SX;
        if ((edges & SimpleRectangle.UP) > 0) scale |= SY;
        if ((edges & SimpleRectangle.LEFT) > 0){
            scale |= SX;
            p = new Point2D.Double(p.getX() + w, p.getY());
        }
    }

    @Override
    void mouseDragged(double dx, double dy, VisualElement element, RoomTab roomTab) {
        if (scale == 0) return;
        double w = roomTab.getSelectionRectangle().getW(), h = roomTab.getSelectionRectangle().getH();
        if (w < 1 || h < 1) return;
        double sx = 1 + dx / w, sy = 1 + dy / h;
        if ((scale & SX) == 0) sx = 1;
        if ((scale & SY) == 0) sy = 1;
        for (VisualElement e : roomTab.getSelection())
            e.scale(p, sx, sy);
    }
}
