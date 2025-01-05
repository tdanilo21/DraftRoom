package raf.draft.dsw.gui.swing.tree;

import com.sun.tools.javac.Main;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.messagegenerator.MessageGenerator;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;

import javax.swing.tree.*;

@Getter
public class DraftTreeNode extends DefaultMutableTreeNode implements ISubscriber {
    private DraftNodeDTO data;

    public DraftTreeNode(DraftNodeDTO data){
        super(data.name());
        this.data = data;
        ApplicationFramework.getInstance().getRepository().addSubscriber(data.id(), this, EventTypes.NODE_EDITED, EventTypes.CHILD_REMOVED, EventTypes.CHILD_ADDED);
    }

    @Override
    public void notify(EventTypes type, Object state) {
        DefaultTreeModel treeModel = MainFrame.getInstance().getRepoTreeModel();
        DraftTree treeView = MainFrame.getInstance().getRepoTreeView();
        DraftTreeNode child = null;
        switch (type){
            case EventTypes.CHILD_ADDED:
                child = new DraftTreeNode((DraftNodeDTO)state);
                treeModel.insertNodeInto(child, this, getChildCount());
                treeView.expandPath(new TreePath(getPath()));
                break;
            case EventTypes.CHILD_REMOVED:
                for (TreeNode node : children)
                    if (((DraftTreeNode)node).getData().id().equals(((DraftNodeDTO)state).id()))
                        child = (DraftTreeNode)node;
                treeModel.removeNodeFromParent(child);
                break;
            case EventTypes.NODE_EDITED:
                data = (DraftNodeDTO)state;
                setUserObject(data.name());
                treeModel.reload(this);
                break;
        }
    }
}
