package raf.draft.dsw.controller.commands;

import java.util.Deque;
import java.util.LinkedList;

public class CommandManager {
    private static final int BUFFER_SIZE = 50;
    private final Deque<AbstractCommand> undo, redo;

    public CommandManager(){
        undo = new LinkedList<>();
        redo = new LinkedList<>();
    }

    public void addCommand(AbstractCommand command){
        redo.clear();
        undo.addFirst(command);
        if (undo.size() > BUFFER_SIZE) undo.removeLast();
    }

    public void undo(){
        if (!undo.isEmpty()) {
            undo.getFirst().undoCommand();
            redo.addFirst(undo.getFirst());
            undo.removeFirst();
        }
    }

    public void redo(){
        if (!redo.isEmpty()) {
            redo.getFirst().doCommand();
            undo.addFirst(redo.getFirst());
            redo.removeFirst();
        }
    }
}
