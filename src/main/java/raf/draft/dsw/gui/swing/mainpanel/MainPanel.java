package raf.draft.dsw.gui.swing.mainpanel;

import lombok.Getter;
import raf.draft.dsw.gui.swing.mainpanel.project.ProjectView;
import raf.draft.dsw.gui.swing.mainpanel.project.ProjectViewController;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.gui.swing.mainpanel.room.RoomView;
import raf.draft.dsw.gui.swing.mainpanel.room.RoomViewController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;

@Getter
public class MainPanel extends JPanel {

    private final RoomViewController roomViewController;
    private final ProjectViewController projectViewController;

    public MainPanel(){
        setLayout(new BorderLayout());

        RoomView roomView = new RoomView();
        roomViewController = new RoomViewController(roomView);

        ProjectView projectView = new ProjectView();
        projectViewController = new ProjectViewController(projectView);

        roomView.getModel().addChangeListener((ChangeEvent e) -> {
            if (roomView.getSelectedComponent() instanceof RoomTab roomTab)
                projectViewController.selectedNodeChanged(roomTab.getRoom());
        });

        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.add(roomView);

        add(panel, BorderLayout.CENTER);
        add(projectView, BorderLayout.EAST);
    }
}
