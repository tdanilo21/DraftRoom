package raf.draft.dsw.controller.messagegenerator;

import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.model.messages.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Logger implements ISubscriber {
    protected String formatMessage(Message message){
        String dateTime = message.getTimestamp().format(DateTimeFormatter.ofPattern("[dd.MM.yyyy. HH:mm]"));
        return STR."[\{message.getType()}] [\{dateTime}] \{message.getText()}";
    }
}
