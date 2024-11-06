package raf.draft.dsw.model.nodes;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Vector;

@Getter
public abstract class DraftNodeComposite extends DraftNode{
    private Vector<DraftNode> childs;

    public DraftNodeComposite(DraftNode parent) {
        super(parent);
    }

    public void addChild(DraftNode node){
        if (childs == null) childs = new Vector<DraftNode>();
        childs.add(node);
    }
}
