package rs.edu.raf.dsw.rudok.app.observer;

import java.util.HashSet;
import java.util.Set;

/**
 * Observer from observer design pattern.
 */
public abstract class IObserver<T> {

    public Set<IPublisher<T>> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<IPublisher<T>> publishers) {
        this.publishers = publishers;
    }

    /**
     * All publishers that this observer is listening to.
     */
    private Set<IPublisher<T>> publishers = new HashSet<>();

    /**
     * Receive data update from publisher.
     * @param message Data sent by publisher.
     */
    public abstract void receive(T message);

    /**
     * Adds a new publisher.
     * @param publisher
     */
    public void addPublisher(IPublisher<T> publisher) {
        if (publisher == null) return;
        if (this.publishers.contains(publisher)) return;
        this.publishers.add(publisher);
        publisher.addObserver(this);
    }

    /**
     * Removes a publisher.
     * @param publisher
     */
    public void removePublisher(IPublisher<T> publisher) {
        if (publisher == null) return;
        if (!this.publishers.contains(publisher)) return;
        this.publishers.remove(publisher);
        publisher.removeObserver(this);
    }
}
