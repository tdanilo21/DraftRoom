package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.ActionManager;

import javax.swing.*;

public class MyToolBar extends JToolBar {
    public MyToolBar(ActionManager actionManager){
        super(HORIZONTAL);
        setFloatable(false);
        add(actionManager.getExitAction());
        add(actionManager.getCreateNodeAction());
        add(actionManager.getDeleteNodeAction());
    }
}
