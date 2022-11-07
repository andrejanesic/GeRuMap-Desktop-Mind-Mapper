package rs.edu.raf.dsw.rudok.app.observer;

/**
 * Represents a wrapper for the data transmitted through the message.
 */
public abstract class IMessageData<T> {

    /**
     * Sender of the message.
     */
    private final T sender;

    /**
     * Default constructor. Initializes the sender.
     *
     * @param sender Sender of the message.
     */
    public IMessageData(T sender) {
        this.sender = sender;
    }

    /**
     * Returns the sender of the message.
     *
     * @return Sender of the message.
     */
    public T getSender() {
        return sender;
    }
}
