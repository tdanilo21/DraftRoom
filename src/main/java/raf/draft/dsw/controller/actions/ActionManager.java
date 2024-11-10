package raf.draft.dsw.controller.actions;

import lombok.Getter;

@Getter
public class ActionManager {
    private final ExitAction exitAction;
    private final CreateNodeAction createNodeAction;
    private final DeleteNodeAction deleteNodeAction;
    private final RenameNodeAction renameNodeAction;
    private final ChangeAuthorAction changeAuthorAction;
    private final ChangePathAction changePathAction;
    private final AboutUsAction aboutUsAction;

    public ActionManager(){
        exitAction = new ExitAction();
        createNodeAction = new CreateNodeAction();
        deleteNodeAction = new DeleteNodeAction();
        renameNodeAction = new RenameNodeAction();
        changeAuthorAction = new ChangeAuthorAction();
        changePathAction = new ChangePathAction();
        aboutUsAction = new AboutUsAction();
    }
}
