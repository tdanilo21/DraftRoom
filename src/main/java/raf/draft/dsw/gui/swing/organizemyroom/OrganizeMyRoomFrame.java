package raf.draft.dsw.gui.swing.organizemyroom;

import raf.draft.dsw.controller.commands.AbstractCommand;
import raf.draft.dsw.controller.commands.AddCommand;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.mainpanel.room.tab.RoomTab;
import raf.draft.dsw.model.enums.VisualElementTypes;
import raf.draft.dsw.model.messages.MessageTypes;
import raf.draft.dsw.model.structures.room.interfaces.VisualElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.lang.foreign.PaddingLayout;
import java.util.Vector;

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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(createLeft());
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        panel.add(createCenter());
        panel.add(Box.createRigidArea(new Dimension(20,0)));
        panel.add(createRight());
        add(panel);
    }

    private JPanel createLeft() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel(), panel2 = new JPanel();

        panel1.add(new JLabel("Width:"));
        widthField = new JTextField(6);
        panel1.add(widthField);

        panel2.add(new JLabel("Height:"));
        heightField = new JTextField(6);
        panel2.add(heightField);

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(panel1);
        panel.add(panel2);
        return panel;
    }

    private JPanel createCenter() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1, 5, 10));
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
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setAlignmentY(0);
            }
        }
        return panel;
    }

    private JPanel createRight() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        elementsList = new ElementsList();
        elementsList.setLayout(new BoxLayout(elementsList, BoxLayout.Y_AXIS));
        elementsList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(elementsList);
        JButton button = new JButton("Enter");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(_ -> {
            Vector<Element> elements = elementsList.getElements();
            int n = elements.size();
            double[] w = new double[n], h = new double[n];
            VisualElementTypes[] types = new VisualElementTypes[n];
            for (int i = 0; i < n; i++){
                w[i] = elements.get(i).w();
                h[i] = elements.get(i).h();
                types[i] = elements.get(i).type();
            }
            RoomTab roomTab = MainFrame.getInstance().getRoomViewController().getSelectedTab();
            ApplicationFramework app = ApplicationFramework.getInstance();
            Vector<VisualElement> createdElements = app.getRepository().createBatchSpiral(roomTab.getRoom().id(), n, w, h, types);
            if (createdElements == null) {
                app.getMessageGenerator().generateMessage("Not enough space inside the room", MessageTypes.WARNING);
                return;
            }
            AbstractCommand command = new AddCommand(createdElements, roomTab.getRoom().id());
            roomTab.getCommandManager().addCommand(command);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
        panel.add(scrollPane);
        panel.add(button);
        return panel;
    }
}
