package raf.draft.dsw.gui.swing;

import lombok.Getter;
import raf.draft.dsw.controller.actions.ActionManager;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.mainpanel.*;
import raf.draft.dsw.gui.swing.mainpanel.project.ProjectViewController;
import raf.draft.dsw.gui.swing.mainpanel.room.RoomViewController;
import raf.draft.dsw.gui.swing.tree.DraftRepository;
import raf.draft.dsw.gui.swing.tree.DraftTreeCellEditor;
import raf.draft.dsw.gui.swing.tree.DraftTreeNode;
import raf.draft.dsw.gui.swing.tree.TreeMouseListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

@Getter
public class MainFrame extends JFrame {

    private static MainFrame instance = null;
    private MainFrame(){}

    public static MainFrame getInstance(){
        if(instance == null){
            instance = new MainFrame();
            instance.initialize();
        }
        return instance;
    }

    private ActionManager actionManager;
    private DraftRepository repoTreeView;
    private DefaultTreeModel repoTreeModel;
    private RoomViewController roomViewController;
    private ProjectViewController projectViewController;


    private void initialize(){
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DraftRoom");

        actionManager = new ActionManager();

        MyMenuBar menu = new MyMenuBar(actionManager);
        setJMenuBar(menu);

        MyToolBar toolBar = new MyToolBar(actionManager);
        add(toolBar, BorderLayout.NORTH);

        DraftTreeNode root = new DraftTreeNode(ApplicationFramework.getInstance().getRepository().getRoot());
        repoTreeModel = new DefaultTreeModel(root);
        repoTreeView = new DraftRepository(repoTreeModel);
        repoTreeView.setEditable(true);
        repoTreeView.setCellEditor(new DraftTreeCellEditor(repoTreeView, (DefaultTreeCellRenderer) repoTreeView.getCellRenderer()));
        TreeMouseListener treeMouseListener = new TreeMouseListener(repoTreeView);
        repoTreeView.addMouseListener(treeMouseListener);

        DraftRepositoryPanel draftRepositoryPanel = new DraftRepositoryPanel(repoTreeView);
        add(draftRepositoryPanel, BorderLayout.WEST);

        MainPanel mainPanel = new MainPanel();
        roomViewController = mainPanel.getRoomViewController();
        treeMouseListener.addSubscriber(roomViewController, EventTypes.NODE_DOUBLE_CLICK);
        projectViewController = mainPanel.getProjectViewController();
        add(mainPanel, BorderLayout.CENTER);
    }
}
