package raf.draft.dsw.controller.observer;

public interface ISubscriber {
    void notify(EventTypes type, Object state);
}
