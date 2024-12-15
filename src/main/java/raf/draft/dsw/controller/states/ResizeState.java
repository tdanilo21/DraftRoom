package raf.draft.dsw.controller.states;

import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.structures.room.interfaces.CircularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.TriangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.Point2D;

public class ResizeState extends AbstractState{

    private VisualElement element;

    @Override
    void mousePressed(double x, double y, VisualElement element, RoomTab roomTab) {
        Point2D c = element.getCenterInPixelSpace();
        if (x > c.getX() && y > c.getY())  this.element = element;
        else this.element = null;
    }

    @Override
    void mouseDragged(double dx, double dy, VisualElement element, RoomTab roomTab) {
        if (this.element instanceof RectangularVisualElement rElement){
            double w = rElement.getWInPixelSpace(), h = rElement.getHInPixelSpace();
            if (w < 1 || h < 1) return;
            rElement.scaleW(1 + dx / w);
            rElement.scaleH(1 + dy / h);
        } else if (this.element instanceof CircularVisualElement cElement){
            double r = cElement.getRInPixelSpace();
            if (r < 1) return;
            cElement.scaleR(1 + Math.max(dx, dy) / r);
        } else if (this.element instanceof TriangularVisualElement tElement){
            double a = tElement.getAInPixelSpace();
            if (a < 1) return;
            tElement.scaleA(1 + Math.max(dx, dy) / a);
        }
    }
}
