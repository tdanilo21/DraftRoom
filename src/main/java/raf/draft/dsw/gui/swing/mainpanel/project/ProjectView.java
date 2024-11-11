package raf.draft.dsw.gui.swing.mainpanel.project;

import javax.swing.*;
import java.awt.*;

public class ProjectView extends JPanel {
    private final JLabel projectNameLabel;
    private final JLabel authorNameLabel;
    private final JLabel buildingNameLabel;

    public ProjectView(){
        projectNameLabel = new JLabel("/");
        authorNameLabel = new JLabel("/");
        buildingNameLabel = new JLabel("/");
        initialize();
    }

    private void initialize(){
        setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        Dimension d = getPreferredSize();
        setPreferredSize(new Dimension(300, d.height));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        final int fontSize = 15;

        JPanel projectPanel = new JPanel();
        JLabel projectLabel = new JLabel("Project");
        projectLabel.setFont(new Font("SanSerif", Font.BOLD, fontSize));
        projectPanel.add(projectLabel);
        projectNameLabel.setFont(new Font("SanSerif", Font.PLAIN, fontSize));
        projectPanel.add(projectNameLabel);

        JPanel authorPanel = new JPanel();
        JLabel authorLabel = new JLabel("Author");
        authorLabel.setFont(new Font("SanSerif", Font.BOLD, fontSize));
        authorPanel.add(authorLabel);
        authorNameLabel.setFont(new Font("SanSerif", Font.PLAIN, fontSize));
        authorPanel.add(authorNameLabel);

        JPanel buildingPanel = new JPanel();
        JLabel buildingLabel = new JLabel("Building");
        buildingLabel.setFont(new Font("SanSerif", Font.BOLD, fontSize));
        buildingPanel.add(buildingLabel);
        buildingNameLabel.setFont(new Font("SanSerif", Font.PLAIN, fontSize));
        buildingPanel.add(buildingNameLabel);

        panel.add(projectPanel);
        panel.add(authorPanel);
        panel.add(buildingPanel);

        add(panel);
    }

    public void updateLabels(String project, String author, String building){
        projectNameLabel.setText(project);
        authorNameLabel.setText(author);
        buildingNameLabel.setText(building);
    }
}
