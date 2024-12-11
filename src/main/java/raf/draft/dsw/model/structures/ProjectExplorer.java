package raf.draft.dsw.model.structures;

import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.dtos.DraftNodeTypes;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;

public class ProjectExplorer extends DraftNodeComposite {

    public ProjectExplorer(Integer id){
        super(id);
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public Class<? extends DraftNode>[] getAllowedChildrenTypes() {
        return new Class[]{Project.class};
    }

    @Override
    public DraftNodeDTO getDTO() {
        return new DraftNodeDTO(id, DraftNodeTypes.PROJECT_EXPLORER, "Project explorer", null, null, null);
    }
}
