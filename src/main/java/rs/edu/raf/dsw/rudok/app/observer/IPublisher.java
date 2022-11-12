package rs.edu.raf.dsw.rudok.app.observer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Publisher for observer design pattern. Extends IObserver to allow for publisher-observer classes.
 */
public abstract class IPublisher extends IPublisherGlobal {

    private Set<IObserver> observers = new HashSet<>();

    public Set<IObserver> getObservers() {
        return observers;
    }

    public void setObservers(Set<IObserver> observers) {
        this.observers = observers;
    }

    /**
     * Updates all subscribed observers.
     *
     * @param message Message.
     */
    public void publish(IMessage message) {
        Set<IObserver> all = new HashSet<>(IPublisherGlobal.getObserversGlobal(message.getClass()));
        all.addAll(observers);
        Iterator<IObserver> iterator = all.iterator();
        while (iterator.hasNext()) {
            IObserver observer = iterator.next();
            observer.receive(message);
        }
    }

    /**
     * Adds a new observer.
     *
     * @param observer
     */
    public void addObserver(IObserver observer) {
        if (observer == null) return;
        if (this.observers.contains(observer)) return;
        this.observers.add(observer);
        observer.addPublisher(this);
    }

    /**
     * Removes an observer.
     *
     * @param observer
     */
    public void removeObserver(IObserver observer) {
        if (observer == null) return;
        if (!this.observers.contains(observer)) return;
        this.observers.remove(observer);
        observer.removePublisher(this);
    }
}
