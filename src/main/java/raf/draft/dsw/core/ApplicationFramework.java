package raf.draft.dsw.core;

import lombok.Getter;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.model.repository.DraftRoomRepository;

@Getter
public class ApplicationFramework {

    private static ApplicationFramework instance = null;

    private ApplicationFramework(){}

    public static ApplicationFramework getInstance(){
        if(instance == null) instance = new ApplicationFramework();
        return instance;
    }

    private DraftRoomRepository repository;

    public void initialize() {
        repository = new DraftRoomRepository();

        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setVisible(true);

        repository.addSubscriber(MainFrame.getInstance().getRoomViewController(), EventTypes.NODE_CREATED, EventTypes.NODE_DELETED, EventTypes.NODE_EDITED);
        repository.addSubscriber(MainFrame.getInstance().getProjectViewController(), EventTypes.NODE_EDITED);
    }
}
