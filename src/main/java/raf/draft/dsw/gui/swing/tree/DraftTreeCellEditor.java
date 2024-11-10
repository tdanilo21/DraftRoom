package raf.draft.dsw.gui.swing.tree;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.repository.DraftRoomRepository;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class DraftTreeCellEditor extends DefaultTreeCellEditor implements ActionListener {
    private Object node;

    public DraftTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer){
        super(tree, renderer);
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
        node = value;
        JTextField textField = new JTextField(value.toString(), 10);
        textField.addActionListener(this);
        return textField;
    }

    @Override
    public boolean isCellEditable(EventObject event) {
        if (lastPath != null && lastPath.getLastPathComponent() instanceof DraftTreeNode draftTreeNode){
            return !ApplicationFramework.getInstance().getRepository().isReadOnly(draftTreeNode.getData().id()) && super.isCellEditable(event);
        }
        return false;
    }

    @Override
    protected boolean canEditImmediately(EventObject event) {
        if (event instanceof MouseEvent mouseEvent && mouseEvent.getButton() == MouseEvent.BUTTON1 && mouseEvent.getClickCount() == 1){
            TreePath currentPath = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
            return lastPath != null && currentPath != null && lastPath.getLastPathComponent().equals(currentPath.getLastPathComponent());
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (node instanceof DraftTreeNode draftTreeNode){
            DraftRoomRepository repository = ApplicationFramework.getInstance().getRepository();
            String newName = e.getActionCommand();
            if (repository.hasSiblingWithName(draftTreeNode.getData().id(), newName)) {
                System.err.println("Node with the same name already exists at the same path.");
                return;
            }
            stopCellEditing();
            repository.renameNode(draftTreeNode.getData().id(), newName);
            draftTreeNode.setName(newName);
            MainFrame.getInstance().getRepoTreeModel().reload(draftTreeNode);
        }
    }
}
