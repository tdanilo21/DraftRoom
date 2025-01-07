package raf.draft.dsw.controller.commands;

import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.util.Vector;

public class MoveCommand extends AbstractCommand{
    private final double dx, dy;

    public MoveCommand(Vector<VisualElement> elements, double dx, double dy){
        super(elements);
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void doCommand(int i) {
        elements.get(i).translate(dx, dy);
    }

    @Override
    public void undoCommand(int i) {
        elements.get(i).translate(-dx, -dy);
    }
}
