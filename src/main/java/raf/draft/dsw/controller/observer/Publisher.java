package raf.draft.dsw.controller.observer;

import java.util.Vector;

public class Publisher<E> {
    private Vector<ISubscriber<E>> subscribers;

    public Publisher(){
        subscribers = new Vector<>();
    }

    public void update(E newState){
        for (ISubscriber<E> subscriber : subscribers)
            subscriber.notify(newState);
    }

    public void addSubscriber(ISubscriber<E> subscriber){
        subscribers.add(subscriber);
    }
}
