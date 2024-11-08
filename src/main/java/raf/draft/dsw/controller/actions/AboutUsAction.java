package raf.draft.dsw.controller.actions;


import raf.draft.dsw.gui.swing.AboutUsFrame;

import java.awt.event.ActionEvent;


public class AboutUsAction extends AbstractRoomAction{
    public AboutUsAction(){
        putValue(SMALL_ICON, loadIcon("/images/info.png"));
        putValue(NAME, "About us");
        putValue(SHORT_DESCRIPTION, "About us");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AboutUsFrame frame = new AboutUsFrame();
        frame.setVisible(true);
    }
}
