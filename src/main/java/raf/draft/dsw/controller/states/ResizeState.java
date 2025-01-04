package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.Geometry;
import raf.draft.dsw.model.structures.room.SimpleRectangle;
import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.TriangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.Point2D;

public class ResizeState extends AbstractState{
    private Point2D p;
    private int cx, cy;
    private boolean uniformly;

    @Override
    void mousePressed(double x, double y, VisualElement element, RoomTab roomTab) {
        SimpleRectangle selection = roomTab.getSelectionRectangle();
        if (selection == null) return;
        Point2D q = roomTab.getConverter().pointFromPixelSpace(new Point2D.Double(x, y));
        double margin = roomTab.getConverter().lengthFromPixelSpace(10);
        int edges = selection.getEdges(q, margin);
        if (edges == 0) return;
        p = selection.getLocation();
        cx = ((edges & SimpleRectangle.RIGHT) > 0 ? 1 : ((edges & SimpleRectangle.LEFT) > 0 ? -1 : 0));
        cy = ((edges & SimpleRectangle.UP) > 0 ? 1 : ((edges & SimpleRectangle.DOWN) > 0 ? -1 : 0));
        if ((edges & SimpleRectangle.LEFT) > 0) p = new Point2D.Double(p.getX() + selection.getW(), p.getY());
        if ((edges & SimpleRectangle.DOWN) > 0) p = new Point2D.Double(p.getX(), p.getY() + selection.getH());
        uniformly = false;
        for (VisualElement e : roomTab.getSelection()) {
            if (e instanceof CircularElement || e instanceof TriangularVisualElement) {
                uniformly = true;
                break;
            }
        }
        if (uniformly && cx*cy == 0) cx = cy = 0;
    }

    @Override
    void mouseDragged(double dx, double dy, VisualElement element, RoomTab roomTab) {
        if (cx == 0 && cy == 0) return;
        double w = roomTab.getSelectionRectangle().getW(), h = roomTab.getSelectionRectangle().getH();
        dx = roomTab.getConverter().lengthFromPixelSpace(dx) * cx;
        dy = roomTab.getConverter().lengthFromPixelSpace(dy) * cy;
        if ((w <= 1 && dx < 0) || (h <= 1 && dy < 0)) return;
        double sx = 1 + dx / w, sy = 1 + dy / h;
        if (uniformly) sx = sy = Math.max(sx, sy);
        for (VisualElement e : roomTab.getSelection())
            e.scale(p, sx, sy);
        roomTab.setSelectionRectangle(Geometry.getRectangleHull(roomTab.getSelection()));
    }
}
