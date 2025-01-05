package raf.draft.dsw.gui.swing.organizemyroom;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.messages.MessageTypes;

import javax.swing.*;
import java.awt.*;

public class OrganizeMyRoomFrame extends JFrame {
    private JTextField widthField, heightField;
    private ElementsList elementsList;

    public OrganizeMyRoomFrame() {
        initialize();
    }

    private void initialize() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationRelativeTo(null);
        setTitle("OrganizeMyRoom");

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 5));

        panel.add(createLeft());
        panel.add(createCenter());
        panel.add(createRight());

        add(panel);
    }

    private JPanel createLeft() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 5));
        JPanel panel1 = new JPanel(), panel2 = new JPanel();

        panel1.add(new JLabel("Width:"));
        widthField = new JTextField(10);
        panel1.add(widthField);

        panel2.add(new JLabel("Height:"));
        heightField = new JTextField(10);
        panel2.add(heightField);

        panel.add(panel1);
        panel.add(panel2);
        return panel;
    }

    private JPanel createCenter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 5));
        for (VisualElementTypes type : VisualElementTypes.values()) {
            if (type != VisualElementTypes.WALL){
                JButton button = new JButton(type.toString());
                button.addActionListener(_ -> {
                    double w, h;
                    try{
                        w = Integer.parseInt(widthField.getText());
                        h = Integer.parseInt(heightField.getText());
                    } catch (NumberFormatException exception){
                        ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Dimensions must be numbers", MessageTypes.ERROR);
                        return;
                    }
                    if (type == VisualElementTypes.BOILER || type == VisualElementTypes.DOOR
                            || type == VisualElementTypes.SINK || type == VisualElementTypes.TOILET)
                        h = w;
                    elementsList.addElement(new Element(w, h, type));
                });
                panel.add(button);
            }
        }
        return panel;
    }

    private JPanel createRight() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 5));
        elementsList = new ElementsList();
        JButton button = new JButton("Enter");
        button.addActionListener(_ -> {
            // TODO
        });
        panel.add(elementsList);
        panel.add(button);
        return panel;
    }
}
