package rs.edu.raf.dsw.rudok.app.logger;

import rs.edu.raf.dsw.rudok.app.messagegenerator.IMessageGenerator;
import rs.edu.raf.dsw.rudok.app.observer.IObserver;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;

/**
 * <h1>Logger</h1>
 * Logs incoming messages.
 */
public abstract class ILogger extends IPublisher {

    /**
     * Logs the message of the given {@link IMessageGenerator.Type}.
     *
     * @param content   Message content.
     * @param type      Message type: {@link IMessageGenerator.Type}.
     * @param timestamp Timestamp.
     */
    public abstract void log(String content, IMessageGenerator.Type type, String timestamp);

    /**
     * Watches for messages sent by {@link IMessageGenerator} and logs them.
     *
     * @param message Data sent by publisher.
     */
    @Override
    public void receive(Object message) {
        super.receive(message);

        if (message instanceof IMessageGenerator.Message) {
            IMessageGenerator.Message m = (IMessageGenerator.Message) message;
            assert (m.getStatus().equals(IMessageGenerator.Message.Type.Message));
            this.log(m.getData().getContent(), m.getData().getType(), m.getData().getTimestamp());
        }
    }
}
