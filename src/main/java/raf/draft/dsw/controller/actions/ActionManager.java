package raf.draft.dsw.controller.actions;

import lombok.Getter;
import raf.draft.dsw.controller.actions.state.*;
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

    private final SelectStateAction selectStateAction;
    private final AddStateAction addStateAction;
    private final DeleteStateAction deleteStateAction;
    private final MoveStateAction moveStateAction;
    private final EditStateAction editStateAction;
    private final ResizeStateAction resizeStateAction;
    private final ZoomStateAction zoomStateAction;


    public ActionManager(){
        exitAction = new ExitAction();
        aboutUsAction = new AboutUsAction();

        createNodeAction = new CreateNodeAction();
        deleteNodeAction = new DeleteNodeAction();
        renameNodeAction = new RenameNodeAction();
        changeAuthorAction = new ChangeAuthorAction();
        changePathAction = new ChangePathAction();

        selectStateAction = new SelectStateAction();
        addStateAction = new AddStateAction();
        deleteStateAction = new DeleteStateAction();
        moveStateAction = new MoveStateAction();
        editStateAction = new EditStateAction();
        resizeStateAction = new ResizeStateAction();
        zoomStateAction = new ZoomStateAction();
    }
}
