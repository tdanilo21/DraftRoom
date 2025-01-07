package raf.draft.dsw.controller.actions;

import lombok.Getter;
import raf.draft.dsw.controller.actions.file.*;
import raf.draft.dsw.controller.actions.state.*;
import raf.draft.dsw.controller.actions.tree.*;

@Getter
public class ActionManager {
    private final ExitAction exitAction;
    private final AboutUsAction aboutUsAction;
    private final OrganizeMyRoomAction organizeMyRoomAction;

    private final OpenProjectAction openProjectAction;
    private final CloseProjectAction closeProjectAction;
    private final SaveProjectAction saveProjectAction;
    private final SaveProjectAsAction saveProjectAsAction;
    private final LoadPatternAction loadPatternAction;
    private final SaveAsPatternAction saveAsPatternAction;
    private final ImportPatternAction importPatternAction;

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
    private final CopyPasteAction copyPasteAction;
    private final RotateLeftAction rotateLeftAction;
    private final RotateRightAction rotateRightAction;


    public ActionManager(){
        exitAction = new ExitAction();
        aboutUsAction = new AboutUsAction();
        organizeMyRoomAction = new OrganizeMyRoomAction();

        openProjectAction = new OpenProjectAction();
        closeProjectAction = new CloseProjectAction();
        saveProjectAction = new SaveProjectAction();
        saveProjectAsAction = new SaveProjectAsAction();
        loadPatternAction = new LoadPatternAction();
        saveAsPatternAction = new SaveAsPatternAction();
        importPatternAction = new ImportPatternAction();

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
        copyPasteAction = new CopyPasteAction();
        rotateLeftAction = new RotateLeftAction();
        rotateRightAction = new RotateRightAction();
    }
}
