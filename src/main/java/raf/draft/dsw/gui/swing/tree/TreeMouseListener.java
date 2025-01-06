package raf.draft.dsw.gui.swing.tree;

import raf.draft.dsw.model.dtos.DraftNodeDTO;
import raf.draft.dsw.model.enums.DraftNodeTypes;
import raf.draft.dsw.controller.observer.EventTypes;
import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;

import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class TreeMouseListener extends MouseAdapter implements IPublisher {
    private final Vector<ISubscriber> subscribers;
    private final DraftTree tree;

    public TreeMouseListener(DraftTree tree){
        this.tree = tree;
        subscribers = new Vector<>();
    }

    @Override
    public void addSubscriber(ISubscriber subscriber, EventTypes... types) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber, EventTypes... types) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(EventTypes type, Object state) {
        for (ISubscriber subscriber : subscribers)
            subscriber.notify(type, state);
    }

    @Override
    public void mousePressed(MouseEvent e){
        TreePath path = tree.getPathForLocation(e.getX(), e.getY());
        if (path != null && e.getClickCount() == 2 && e.isControlDown()){
            DraftNodeDTO node = ((DraftTreeNode)path.getLastPathComponent()).getData();
            if (node.type() != DraftNodeTypes.PROJECT_EXPLORER) notifySubscribers(EventTypes.NODE_DOUBLE_CLICK, node);
        }
    }
}
