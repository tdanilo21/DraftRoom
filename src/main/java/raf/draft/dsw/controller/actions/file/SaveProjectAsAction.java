package raf.draft.dsw.controller.actions.file;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SaveProjectAsAction extends AbstractSaveProjectAction{
    public SaveProjectAsAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
        putValue(NAME, "Save Project As");
        putValue(SHORT_DESCRIPTION, "Save Project As");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canSave()) saveAs();
    }
}
