package rs.edu.raf.dsw.rudok.app.observer;

import java.util.HashSet;
import java.util.Set;

/**
 * Observer from observer design pattern.
 */
public abstract class IObserver {

    public Set<IPublisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<IPublisher> publishers) {
        this.publishers = publishers;
    }

    /**
     * All publishers that this observer is listening to.
     */
    private Set<IPublisher> publishers = new HashSet<>();

    /**
     * Receive data update from publisher. Default implementation, should be overridden.
     * @param message Data sent by publisher.
     */
    public void receive(Object message) {
    }

    /**
     * Adds a new publisher.
     * @param publisher
     */
    public void addPublisher(IPublisher publisher) {
        if (publisher == null) return;
        if (this.publishers.contains(publisher)) return;
        this.publishers.add(publisher);
        publisher.addObserver(this);
    }

    /**
     * Removes a publisher.
     * @param publisher
     */
    public void removePublisher(IPublisher publisher) {
        if (publisher == null) return;
        if (!this.publishers.contains(publisher)) return;
        this.publishers.remove(publisher);
        publisher.removeObserver(this);
    }
}
