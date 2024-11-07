package raf.draft.dsw.controller.actions;

import lombok.Getter;

@Getter
public class ActionManager {
    private final ExitAction exitAction;
    private final CreateNodeAction createNodeAction;
    private final DeleteNodeAction deleteNodeAction;

    public ActionManager(){
        exitAction = new ExitAction();
        createNodeAction = new CreateNodeAction();
        deleteNodeAction = new DeleteNodeAction();
    }
}
