package raf.draft.dsw.controller.messagegenerator;

import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageTypes;

import java.time.LocalDateTime;
import java.util.Vector;

public class MessageGenerator implements IPublisher {
    private Vector<ISubscriber> subscribers;
    public MessageGenerator(){
        subscribers = new Vector<>();
    }
    @Override
    public void addSubscriber(ISubscriber subscriber, EventTypes... types){
        subscribers.add(subscriber);
    }
    @Override
    public void removeSubscriber(ISubscriber subscriber, EventTypes... types){
        subscribers.remove(subscriber);
    }
    @Override
    public void notifySubscribers(EventTypes type, Object state){
        for(ISubscriber subscriber : subscribers){
            subscriber.notify(type, state);
        }
    }
    public void generateMessage(String text, MessageTypes type){
        Message message = new Message(text, type, LocalDateTime.now());
        notifySubscribers(EventTypes.MESSAGE_GENERATED, message);
    }
}
