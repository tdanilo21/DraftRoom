package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.ActionManager;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar(ActionManager actionManager){
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(actionManager.getExitAction());
        fileMenu.add(actionManager.getCreateNodeAction());
        fileMenu.add(actionManager.getRenameNodeAction());
        fileMenu.add(actionManager.getChangeAuthorAction());
        fileMenu.add(actionManager.getChangePathAction());
        fileMenu.add(actionManager.getDeleteNodeAction());
        add(fileMenu);
    }
}
