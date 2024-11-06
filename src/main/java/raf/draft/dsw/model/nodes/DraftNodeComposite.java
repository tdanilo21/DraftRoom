package raf.draft.dsw.model.nodes;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor
public abstract class DraftNodeComposite extends DraftNode{
    private List<DraftNode> childs;

    public void addChild(DraftNode node){
        childs.add(node);
    }
}
