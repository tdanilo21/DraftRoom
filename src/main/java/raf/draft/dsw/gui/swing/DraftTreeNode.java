package raf.draft.dsw.gui.swing;

import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.structures.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

public class DraftTreeNode extends DefaultMutableTreeNode {
    private DraftNode node;

    public DraftTreeNode(DraftNode node){
        super(null, node instanceof DraftNodeComposite);
        setUserObject(getText(node));
        this.node = node;
    }

    private String getText(DraftNode node){
        if (node instanceof ProjectExplorer) return "ProjectExplorer";
        if (node instanceof Project) return STR."\{((Project)node).getName()} - \{((Project)node).getAuthor()}";
        if (node instanceof Building) return ((Building)node).getName();
        if (node instanceof Room) return ((Room)node).getName();
        return null;
    }

    public void insert(MutableTreeNode newChild, int childIndex){
        if (!(newChild instanceof DraftTreeNode)){
            System.err.println("Child node must be of type DraftTreeNode");
            return;
        }
        try{
            super.insert(newChild, childIndex);
            ((DraftNodeComposite)node).addChild(((DraftTreeNode)newChild).node);
        } catch (Exception e){
            System.err.println(e);
        }
    }

    public void insert(MutableTreeNode newChild){
        insert(newChild, getChildCount());
    }
}
