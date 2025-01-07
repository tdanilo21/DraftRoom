package raf.draft.dsw.controller.commands;

import raf.draft.dsw.model.structures.room.CircularElement;
import raf.draft.dsw.model.structures.room.RectangularElement;
import raf.draft.dsw.model.structures.room.interfaces.RectangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.TriangularVisualElement;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.util.Vector;

public abstract class EditCommand extends AbstractCommand{
    protected final double[] oldDims;
    protected final double[] newDims;

    public EditCommand(VisualElement element, double[] oldDims, double[] newDims){
        super(new Vector<>(){{add(element);}});
        this.oldDims = oldDims;
        this.newDims = newDims;
    }

    public static EditCommand getCommand(VisualElement element, double ...dims){
        if (element instanceof RectangularVisualElement) return new EditRectangularCommand((RectangularVisualElement)element, dims);
        if (element instanceof CircularElement) return new EditCircularCommand((CircularElement)element, dims);
        if (element instanceof TriangularVisualElement) return new EditTriangularCommand((TriangularVisualElement)element, dims);
        return null;
    }

    private static class EditRectangularCommand extends EditCommand{
        public EditRectangularCommand(RectangularVisualElement element, double ...dims){
            super(element, new double[]{element.getW(), element.getH()}, dims);
        }

        @Override
        public void doCommand(int i) {
            ((RectangularElement)elements.get(i)).setW(newDims[0]);
            ((RectangularElement)elements.get(i)).setH(newDims[1]);
        }

        @Override
        public void undoCommand(int i) {
            ((RectangularElement)elements.get(i)).setW(oldDims[0]);
            ((RectangularElement)elements.get(i)).setH(oldDims[1]);
        }
    }

    private static class EditCircularCommand extends EditCommand{
        public EditCircularCommand(CircularElement element, double ...dims){
            super(element, new double[]{element.getR()}, dims);
        }

        @Override
        public void doCommand(int i) {
            ((CircularElement)elements.get(i)).setR(newDims[0]);
        }

        @Override
        public void undoCommand(int i) {
            ((CircularElement)elements.get(i)).setR(oldDims[0]);
        }
    }

    private static class EditTriangularCommand extends EditCommand{
        public EditTriangularCommand(TriangularVisualElement element, double ...dims){
            super(element, new double[]{element.getA()}, dims);
        }

        @Override
        public void doCommand(int i) {
            ((TriangularVisualElement)elements.get(i)).setA(newDims[0]);
        }

        @Override
        public void undoCommand(int i) {
            ((TriangularVisualElement)elements.get(i)).setA(oldDims[0]);
        }
    }
}
