package raf.draft.dsw.gui.swing.mainpanel.room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class CloseButton extends JButton {

    public CloseButton() {
        setPreferredSize(new Dimension(17, 17));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setRolloverEnabled(true);
        setFocusable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        if (getModel().isRollover()) g2.setColor(Color.RED);
        int offset = 5;
        g2.drawLine(offset, offset, getWidth() - offset - 1, getHeight() - offset - 1);
        g2.drawLine(getWidth() - offset - 1, offset, offset, getHeight() - offset - 1);
        g2.dispose();
    }
}

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
