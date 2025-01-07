package raf.draft.dsw.controller.commands;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.Point2D;
import java.util.Vector;

public class ScaleCommand extends AbstractCommand{
    private final Point2D p;
    private final double sx;
    private final double sy;

    public ScaleCommand(Vector<VisualElement> elements, Point2D p, double sx, double sy){
        super(elements);
        this.p = p;
        this.sx = sx;
        this.sy = sy;
    }

    @Override
    public void doCommand(int i) {
        elements.get(i).scale(p, sx, sy);
    }

    @Override
    public void undoCommand(int i) {
        elements.get(i).scale(p, 1/sx, 1/sy);
    }
}
