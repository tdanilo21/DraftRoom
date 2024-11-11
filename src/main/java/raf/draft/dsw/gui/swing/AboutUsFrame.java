package raf.draft.dsw.gui.swing;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AboutUsFrame extends JFrame {
    public AboutUsFrame(){
        initialize();
    }

    private JPanel createProfile(String text, String path){
        URL ImageURL = getClass().getResource(path);
        ImageIcon imageIcon = null;
        if(ImageURL != null)
        {
            Image img = new ImageIcon(ImageURL).getImage();
            img = img.getScaledInstance(225, 300, Image.SCALE_DEFAULT);
            imageIcon = new ImageIcon(img);
        }
        else
        {
            System.err.println(STR."File \{path} not found");
        }
        JPanel panel = new JPanel(new BorderLayout(5,20));
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("SanSerif", Font.PLAIN, 18));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(textLabel, BorderLayout.SOUTH);
        JLabel imgLabel = new JLabel(imageIcon);
        panel.add(imgLabel, BorderLayout.CENTER);

        return panel;
    }

    private void initialize(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationRelativeTo(null);
        setTitle("AboutUs");

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 5));
        panel.add(createProfile("Iva Videnov RN 15/23","/images/iva.png"));
        panel.add(createProfile("Danilo Trninić RN 19/23","/images/danilo.png"));

        Box box = new Box(BoxLayout.Y_AXIS);
        box.add(Box.createVerticalGlue());
        box.add(panel);
        box.add(Box.createVerticalGlue());

        add(box);
    }
}
