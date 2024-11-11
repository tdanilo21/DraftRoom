package raf.draft.dsw.controller.observer;

public interface IPublisher {
    void addSubscriber(ISubscriber subscriber, EventTypes... types);
    void removeSubscriber(ISubscriber subscriber, EventTypes... types);
    void notifySubscribers(EventTypes type, Object state);
}
