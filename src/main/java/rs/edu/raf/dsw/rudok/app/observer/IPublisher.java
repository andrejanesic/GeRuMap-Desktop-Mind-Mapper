package rs.edu.raf.dsw.rudok.app.observer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Publisher for observer design pattern.
 */
public abstract class IPublisher<T> {

    public Set<IObserver<T>> getObservers() {
        return observers;
    }

    public void setObservers(Set<IObserver<T>> observers) {
        this.observers = observers;
    }

    private Set<IObserver<T>> observers = new HashSet<>();

    /**
     * Updates all subscribed observers.
     * @param message Message.
     */
    public void update(T message) {
        Iterator<IObserver<T>> iterator = observers.iterator();
        while (iterator.hasNext()) {
            IObserver<T> observer = iterator.next();
            observer.receive(message);
        }
    }

    /**
     * Adds a new observer.
     * @param observer
     */
    public void addObserver(IObserver<T> observer) {
        if (observer == null) return;
        if (this.observers.contains(observer)) return;
        this.observers.add(observer);
        observer.addPublisher(this);
    }

    /**
     * Removes an observer.
     * @param observer
     */
    public void removeObserver(IObserver<T> observer) {
        if (observer == null) return;
        if (!this.observers.contains(observer)) return;
        this.observers.remove(observer);
        observer.removePublisher(this);
    }
}
