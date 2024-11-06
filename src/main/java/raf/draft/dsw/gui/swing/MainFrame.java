package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.actions.ActionManager;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;

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
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DraftRoom");

        ActionManager actionManager = new ActionManager();

        MyMenuBar menu = new MyMenuBar(actionManager);
        setJMenuBar(menu);

        MyToolBar toolBar = new MyToolBar(actionManager);
        add(toolBar, BorderLayout.NORTH);

        DraftRepository repo = new DraftRepository(genTree());
        add(repo, BorderLayout.WEST);
    }

    private DraftTreeNode genTree(){
        DraftTreeNode root = new DraftTreeNode(new ProjectExplorer());

        DraftTreeNode p1 = new DraftTreeNode(new Project("Project1", "A", ""));
        DraftTreeNode b11 = new DraftTreeNode(new Building("Building1"));
        DraftTreeNode b12 = new DraftTreeNode(new Building("Building2"));
        DraftTreeNode r111 = new DraftTreeNode(new Room("Room1"));
        DraftTreeNode r121 = new DraftTreeNode(new Room("Room1"));
        DraftTreeNode r122 = new DraftTreeNode(new Room("Room2"));
        DraftTreeNode r = new DraftTreeNode(new Room("Room"));

        root.insert(p1);
        p1.insert(b11);
        p1.insert(b12);
        p1.insert(r);
        b11.insert(r111);
        b12.insert(r121);
        b12.insert(r122);

        return root;
    }
}
