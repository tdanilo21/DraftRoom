package raf.draft.dsw.controller.commands;

import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.TriangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.util.Vector;

public abstract class AbstractCommand {
    protected Vector<VisualElement> elements;

    public AbstractCommand(Vector<VisualElement> elements){
        this.elements = new Vector<>(elements);
    }

    protected abstract void doCommand(int i);

    protected abstract void undoCommand(int i);

    protected double[] getDims(VisualElement element){
        if (element instanceof RectangularVisualElement rElement) return new double[]{rElement.getW(), rElement.getH()};
        if (element instanceof CircularElement cElement) return new double[]{cElement.getR()};
        if (element instanceof TriangularVisualElement tElement) return new double[]{tElement.getA()};
        return null;
    }

    public void doCommand(){
        for (int i = 0; i < elements.size(); i++) doCommand(i);
    }

    public void undoCommand(){
        for (int i = 0; i < elements.size(); i++) undoCommand(i);
    }
}
