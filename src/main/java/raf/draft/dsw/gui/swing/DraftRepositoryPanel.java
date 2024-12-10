package raf.draft.dsw.gui.swing;

import raf.draft.dsw.gui.swing.tree.DraftTree;

import javax.swing.*;
import java.awt.*;

public class DraftRepositoryPanel extends JPanel {

    public DraftRepositoryPanel(DraftTree repositoryTreeView){
        initialize(repositoryTreeView);
    }

    private void initialize(DraftTree repositoryTreeView){
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(repositoryTreeView);
        add(scrollPane, BorderLayout.CENTER);
    }
}
