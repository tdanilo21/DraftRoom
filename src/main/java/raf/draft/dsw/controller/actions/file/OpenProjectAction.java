package raf.draft.dsw.controller.actions.file;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.model.messages.MessageTypes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class OpenProjectAction extends AbstractRoomAction {
    public OpenProjectAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/info.png"));
        putValue(NAME, "Open Project");
        putValue(SHORT_DESCRIPTION, "Open Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        // TODO: Only json allowed
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            ApplicationFramework app = ApplicationFramework.getInstance();
            if (!app.getRepository().openProject(fileChooser.getSelectedFile()))
                app.getMessageGenerator().generateMessage("Something went wrong. File couldn't be opened", MessageTypes.WARNING);
        }
    }
}
