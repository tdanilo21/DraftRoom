package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.ActionManager;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar(ActionManager actionManager){
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(actionManager.getExitAction());
        fileMenu.add(actionManager.getOpenProjectAction());
        fileMenu.add(actionManager.getCloseProjectAction());
        fileMenu.add(actionManager.getSaveProjectAction());
        fileMenu.add(actionManager.getSaveProjectAsAction());
        fileMenu.add(actionManager.getLoadPatternAction());
        fileMenu.add(actionManager.getSaveAsPatternAction());
        fileMenu.add(actionManager.getImportPatternAction());
        add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        editMenu.add(actionManager.getCreateNodeAction());
        editMenu.add(actionManager.getRenameNodeAction());
        editMenu.add(actionManager.getChangeAuthorAction());
        editMenu.add(actionManager.getChangePathAction());
        editMenu.add(actionManager.getDeleteNodeAction());
        add(editMenu);

        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.add(actionManager.getSelectStateAction());
        toolsMenu.add(actionManager.getAddStateAction());
        toolsMenu.add(actionManager.getDeleteStateAction());
        toolsMenu.add(actionManager.getMoveStateAction());
        toolsMenu.add(actionManager.getEditStateAction());
        toolsMenu.add(actionManager.getResizeStateAction());
        toolsMenu.add(actionManager.getZoomStateAction());
        toolsMenu.add(actionManager.getRotateLeftAction());
        toolsMenu.add(actionManager.getRotateRightAction());
        toolsMenu.add(actionManager.getCopyPasteAction());
        add(toolsMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        helpMenu.add(actionManager.getAboutUsAction());
        add(helpMenu);
    }
}
