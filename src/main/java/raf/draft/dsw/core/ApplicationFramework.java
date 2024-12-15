package raf.draft.dsw.core;

import lombok.Getter;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.controller.messagegenerator.Logger;
import raf.draft.dsw.controller.messagegenerator.LoggerFactory;
import raf.draft.dsw.controller.messagegenerator.MessageGenerator;
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
    private MessageGenerator messageGenerator;

    public void initialize() {
        repository = DraftRoomRepository.getInstance();
        messageGenerator = new MessageGenerator();

        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setVisible(true);

        repository.addSubscriber(mainFrame.getRoomViewController(), EventTypes.NODE_CREATED, EventTypes.NODE_DELETED, EventTypes.NODE_EDITED,
                EventTypes.VISUAL_ELEMENT_CREATED, EventTypes.VISUAL_ELEMENT_DELETED, EventTypes.VISUAL_ELEMENT_EDITED);
        repository.addSubscriber(mainFrame.getProjectViewController(), EventTypes.NODE_EDITED);

        Logger consoleLogger = LoggerFactory.createLogger(LoggerFactory.CONSOLE);
        messageGenerator.addSubscriber(consoleLogger, EventTypes.MESSAGE_GENERATED);

        Logger fileLogger = LoggerFactory.createLogger(LoggerFactory.FILE);
        messageGenerator.addSubscriber(fileLogger, EventTypes.MESSAGE_GENERATED);

        messageGenerator.addSubscriber(mainFrame, EventTypes.MESSAGE_GENERATED);
    }
}
