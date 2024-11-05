package raf.draft.dsw.controller.actions;

import lombok.Getter;

@Getter
public class ActionManager {
    private ExitAction exitAction;
    private AboutUsAction aboutUsAction;

    public ActionManager(){
        exitAction = new ExitAction();
        aboutUsAction = new AboutUsAction();
    }
}
