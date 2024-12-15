package raf.draft.dsw.controller.actions.state;

import com.sun.java.accessibility.util.Translator;
import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import java.awt.event.ActionEvent;
import java.util.Vector;

public class CopyPasteAction extends AbstractRoomAction {

    public CopyPasteAction(){
        putValue(SMALL_ICON, loadIcon("/images/copyPaste.png"));
        putValue(NAME, "Copy & Paste");
        putValue(SHORT_DESCRIPTION, "Copy & Paste");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Vector<VisualElement> selection = MainFrame.getInstance().getRoomViewController().getSelectedTab().getSelection();
        for(VisualElement element : selection){
            VisualElement clone = (VisualElement)element.clone();
            clone.translate(20, 0);
        }
    }
}
