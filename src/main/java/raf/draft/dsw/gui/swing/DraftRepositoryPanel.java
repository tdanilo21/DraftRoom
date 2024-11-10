package raf.draft.dsw.gui.swing;

import raf.draft.dsw.gui.swing.tree.DraftRepository;

import javax.swing.*;
import java.awt.*;

public class DraftRepositoryPanel extends JPanel {

    public DraftRepositoryPanel(DraftRepository repositoryTreeView){
        initialize(repositoryTreeView);
    }

    private void initialize(DraftRepository repositoryTreeView){
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(repositoryTreeView);
        add(scrollPane, BorderLayout.CENTER);
    }
}
