package raf.draft.dsw.controller.commands;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.util.Vector;

public class DeleteCommand extends AbstractCommand {
    private final Integer roomId;
    public DeleteCommand(Vector<VisualElement> elements, Integer roomId){
        super(elements);
        this.roomId = roomId;
    }

    @Override
    public void doCommand(int i) {
        ApplicationFramework.getInstance().getRepository().deleteNode(elements.get(i).getId());
    }

    @Override
    public void undoCommand(int i) {
        VisualElement newElement = ApplicationFramework.getInstance().getRepository().createRoomElement(
                elements.get(i).getVisualElementType(), roomId, elements.get(i).getLocation(), getDims(elements.get(i)));
        elements.set(i, newElement);
    }
}
