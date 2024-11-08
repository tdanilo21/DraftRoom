package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.ActionManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static MainFrame instance = null;
    private MainFrame(){
        initialize();
    }

    public static MainFrame getInstance(){
        if(instance == null) instance = new MainFrame();
        return instance;
    }

    private void initialize(){
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DraftRoom");

        ActionManager actionManager = new ActionManager();

        MyMenuBar menu = new MyMenuBar(actionManager);
        setJMenuBar(menu);

        MyToolBar toolBar = new MyToolBar(actionManager);
        add(toolBar, BorderLayout.NORTH);
    }
}
