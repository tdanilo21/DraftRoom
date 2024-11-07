package raf.draft.dsw.controller.observer;

public interface ISubscriber<E> {

    void notify(E newState);
}
