package raf.draft.dsw.gui.swing;

import javax.swing.*;
import java.awt.*;

public class CloseButton extends JButton {

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