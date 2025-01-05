package raf.draft.dsw.gui.swing.mainpanel.room.tab;

import raf.draft.dsw.gui.swing.CloseButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RoomTabComponent extends JPanel {
    private JLabel titleLabel;
    public RoomTabComponent(JTabbedPane tabs, String title){
        initialize(tabs, title);
    }

    private void initialize(JTabbedPane tabs, String title){
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        setOpaque(false);

        titleLabel = new JLabel(title);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
        add(titleLabel);

        CloseButton button = new CloseButton();
        button.addActionListener((ActionEvent e) -> {
            int i = tabs.indexOfTabComponent(RoomTabComponent.this);
            if (i != -1) tabs.remove(i);
        });
        add(button);
    }

    public void setTitle(String title){
        titleLabel.setText(title);
    }
}
