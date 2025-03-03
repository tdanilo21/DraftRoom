package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.ActionManager;

import javax.swing.*;

public class MyToolBar extends JToolBar {
    public MyToolBar(ActionManager actionManager){
        super(HORIZONTAL);
        setFloatable(false);
        add(actionManager.getExitAction());

        addSeparator();

        add(actionManager.getOpenProjectAction());
        add(actionManager.getCloseProjectAction());
        add(actionManager.getSaveProjectAction());
        add(actionManager.getSaveProjectAsAction());
        add(actionManager.getLoadPatternAction());
        add(actionManager.getSaveAsPatternAction());
        add(actionManager.getImportPatternAction());

        addSeparator();

        add(actionManager.getCreateNodeAction());
        add(actionManager.getDeleteNodeAction());
        add(actionManager.getRenameNodeAction());
        add(actionManager.getChangeAuthorAction());
        add(actionManager.getChangePathAction());

        addSeparator();

        add(actionManager.getSelectStateAction());
        add(actionManager.getAddStateAction());
        add(actionManager.getDeleteStateAction());
        add(actionManager.getMoveStateAction());
        add(actionManager.getEditStateAction());
        add(actionManager.getResizeStateAction());
        add(actionManager.getZoomStateAction());
        add(actionManager.getRotateLeftAction());
        add(actionManager.getRotateRightAction());
        add(actionManager.getCopyPasteAction());

        addSeparator();

        add(actionManager.getOrganizeMyRoomAction());

        addSeparator();

        add(actionManager.getAboutUsAction());

        addSeparator();
    }
}
