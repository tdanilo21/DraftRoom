package raf.draft.dsw.model.structures;

import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNodeComposite;

import java.util.Vector;

public class ProjectExplorer extends DraftNodeComposite {

    public ProjectExplorer(Integer id){
        super(id);
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public DraftNodeTypes getNodeType() {
        return DraftNodeTypes.PROJECT_EXPLORER;
    }

    @Override
    public Vector<DraftNodeTypes> getAllowedChildrenTypes() {
        Vector<DraftNodeTypes> types = new Vector<>();
        types.add(DraftNodeTypes.PROJECT);
        return types;
    }

    @Override
    public DraftNodeDTO getDTO() {
        return new DraftNodeDTO(id, DraftNodeTypes.PROJECT_EXPLORER, "Project explorer", null, null, null);
    }
}
