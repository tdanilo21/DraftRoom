package raf.draft.dsw.controller.actions;

import lombok.Getter;
import raf.draft.dsw.controller.actions.state.AddStateAction;
import raf.draft.dsw.controller.actions.state.MoveStateAction;
import raf.draft.dsw.controller.actions.tree.*;

@Getter
public class ActionManager {
    private final ExitAction exitAction;
    private final AboutUsAction aboutUsAction;

    private final CreateNodeAction createNodeAction;
    private final DeleteNodeAction deleteNodeAction;
    private final RenameNodeAction renameNodeAction;
    private final ChangeAuthorAction changeAuthorAction;
    private final ChangePathAction changePathAction;

    private final AddStateAction addStateAction;
    private final MoveStateAction moveStateAction;

    public ActionManager(){
        exitAction = new ExitAction();
        aboutUsAction = new AboutUsAction();

        createNodeAction = new CreateNodeAction();
        deleteNodeAction = new DeleteNodeAction();
        renameNodeAction = new RenameNodeAction();
        changeAuthorAction = new ChangeAuthorAction();
        changePathAction = new ChangePathAction();

        addStateAction = new AddStateAction();
        moveStateAction = new MoveStateAction();
    }
}
