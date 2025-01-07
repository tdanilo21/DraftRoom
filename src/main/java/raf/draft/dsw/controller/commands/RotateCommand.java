package raf.draft.dsw.controller.commands;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.geom.Point2D;
import java.util.Vector;

public class RotateCommand extends AbstractCommand{
    private final Point2D p;
    private final double alpha;

    public RotateCommand(Vector<VisualElement> elements, Point2D p, double alpha){
        super(elements);
        this.p = p;
        this.alpha = alpha;
    }

    @Override
    public void doCommand(int i) {
        elements.get(i).rotate(alpha, p);
    }

    @Override
    public void undoCommand(int i) {
        elements.get(i).rotate(-alpha, p);
    }
}
