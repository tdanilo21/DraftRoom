package raf.draft.dsw.gui.swing.mainpanel.project;

import raf.draft.dsw.controller.dtos.DraftNodeDTO;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.core.ApplicationFramework;

public class ProjectViewController implements ISubscriber {

    private DraftNodeDTO node;
    private final ProjectView projectView;

    public ProjectViewController(ProjectView projectView){
        this.projectView = projectView;
    }

    private String getProject(){
        if (node == null) return "/";
        DraftNodeDTO project = ApplicationFramework.getInstance().getRepository().getProject(node.id());
        return (project == null ? "/" : project.name());
    }

    private String getAuthor(){
        if (node == null) return "/";
        DraftNodeDTO project = ApplicationFramework.getInstance().getRepository().getProject(node.id());
        return (project == null ? "/" : project.author());
    }

    private String getBuilding(){
        if (node == null) return "/";
        DraftNodeDTO building = ApplicationFramework.getInstance().getRepository().getBuilding(node.id());
        return (building == null ? "/" : building.name());
    }

    public void selectedNodeChanged(DraftNodeDTO node){
        this.node = node;
        projectView.updateLabels(getProject(), getAuthor(), getBuilding());
    }

    @Override
    public void notify(EventTypes type, Object state) {
        if (state instanceof DraftNodeDTO editedNode && type == EventTypes.NODE_EDITED)
            if (node != null && ApplicationFramework.getInstance().getRepository().isAncestor(editedNode.id(), node.id()))
                projectView.updateLabels(getProject(), getAuthor(), getBuilding());
    }
}
