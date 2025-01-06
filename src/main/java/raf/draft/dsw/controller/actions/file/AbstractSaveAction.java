package raf.draft.dsw.controller.actions.file;

import raf.draft.dsw.controller.actions.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.model.messages.MessageTypes;

import javax.swing.*;
import java.io.File;
import java.util.Vector;

public abstract class AbstractSaveAction extends AbstractRoomAction {
    public AbstractSaveAction(){
        putValue(SMALL_ICON, loadIcon("/images/info.png"));
    }

    protected void saveAs(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ApplicationFramework app = ApplicationFramework.getInstance();
            if (!file.isDirectory()){
                app.getMessageGenerator().generateMessage(STR."\{file.getAbsolutePath()} is not a directory", MessageTypes.WARNING);
                return;
            }
            DraftNodeDTO room = MainFrame.getInstance().getRoomViewController().getSelectedTab().getRoom();
            DraftNodeDTO project = app.getRepository().getProject(room.id());
            File newFile = new File(STR."\{file.getAbsolutePath()}/\{project.name()}.json");
            if (newFile.exists()){
                int choice = JOptionPane.showConfirmDialog(null, "File with given path already exists. Do you want to replace it?", "Warning", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) return;
                newFile.delete();
            }
            if (!app.getRepository().saveProjectAs(newFile, project.id()))
                app.getMessageGenerator().generateMessage("Something went wrong. File couldn't be saved", MessageTypes.WARNING);
        }
    }

    protected boolean canSave(){
        if (MainFrame.getInstance().getRoomViewController().getSelectedTab() == null) return false;
        DraftNodeDTO room = MainFrame.getInstance().getRoomViewController().getSelectedTab().getRoom();
        ApplicationFramework app = ApplicationFramework.getInstance();
        DraftNodeDTO project = app.getRepository().getProject(room.id());
        Vector<DraftNodeDTO> rooms = app.getRepository().overlaps(project.id());
        if (rooms.isEmpty()) return true;
        StringBuilder builder = new StringBuilder("There are overlapping elements in rooms: ");
        for (int i = 0; i < rooms.size(); i++){
            builder.append(rooms.get(i).name());
            if (i < rooms.size()-1) builder.append(", ");
        }
        builder.append("\nMove overlapping elements before saving");
        app.getMessageGenerator().generateMessage(builder.toString(), MessageTypes.WARNING);
        return false;
    }
}
