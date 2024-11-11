package raf.draft.dsw.controller.messagegenerator;

import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageTypes;

public class ConsoleLogger extends Logger{
    @Override
    public void notify(Object state) {
        if (state instanceof Message message) {
            if (message.getType() == MessageTypes.ERROR)
                System.err.println(formatMessage(message));
            else
                System.out.println(formatMessage(message));
        }
    }
}
