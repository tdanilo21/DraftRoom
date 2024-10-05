package raf.draft.dsw.core;

import raf.draft.dsw.gui.swing.MainFrame;

public class ApplicationFramework {
    //buduca polja za model celog projekta

    public ApplicationFramework(){
        initialize();
    }

    public void initialize(){
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
