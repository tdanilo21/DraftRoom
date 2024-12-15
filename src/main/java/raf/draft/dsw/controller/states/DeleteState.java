package raf.draft.dsw.controller.states;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

public class DeleteState extends State{
    @Override
    void mouseClick(int x, int y, VisualElement element, Integer roomId) {
        ApplicationFramework.getInstance().getRepository().deleteNode(element.getId());
    }
}
