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
            img = img.getScaledInstance(100, 200, Image.SCALE_DEFAULT);
            imageIcon = new ImageIcon(img);
        }
        else
        {
            System.err.println(STR."File \{path} not found");
        }
        JPanel panel = new JPanel();
        JLabel textLabel = new JLabel(text);
        panel.add(textLabel);
        JLabel imgLabel = new JLabel(imageIcon);
        panel.add(imgLabel);
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
        setLayout(new FlowLayout());
        add(createProfile("Iva Videnov RN 15/23","/images/Avatar.png"));
        add(createProfile("Danilo Trninić RN 19/23","/images/Avatar.png"));
    }
}
