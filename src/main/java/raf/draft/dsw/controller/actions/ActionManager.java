package raf.draft.dsw.controller.actions;

import lombok.Getter;

@Getter
public class ActionManager {
    private ExitAction exitAction;

    public ActionManager(){
        exitAction = new ExitAction();
    }
}
