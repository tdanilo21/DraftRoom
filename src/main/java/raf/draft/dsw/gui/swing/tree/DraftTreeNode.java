package raf.draft.dsw.gui.swing.tree;

import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.dtos.DraftNodeDTO;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
@Getter
public class DraftTreeNode extends DefaultMutableTreeNode {
    private DraftNodeDTO data;

    public DraftTreeNode(DraftNodeDTO data){
        super(data.name());
        this.data = data;
    }

    public void setName(String newName){
        data = new DraftNodeDTO(data.id(), data.type(), newName, data.color());
        setUserObject(data.name());
    }
}
