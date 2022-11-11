package rs.edu.raf.dsw.rudok.app.messagegenerator;

import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.observer.IMessageData;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * <h1>Message generator</h1>
 * Used for generating messages propagated throughout the system, such as logs, warnings, errors, etc.
 */
public abstract class IMessageGenerator extends IPublisher {

    /**
     * Sends the message with the given content, type and timestamp.
     *
     * @param content   Message content.
     * @param type      Message type.
     * @param timestamp Message timestamp.
     */
    public abstract void send(String content, Type type, String timestamp);

    /**
     * Sends the message with the given content and type.
     *
     * @param content Message content.
     * @param type    Message type.
     */
    public void send(String content, Type type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp t = new Timestamp(System.currentTimeMillis());
        this.send(content, type, sdf.format(t));
    }

    /**
     * Produces a log message with the given content and the current timestamp.
     *
     * @param content Message content.
     */
    public void log(String content) {
        this.send(content, Type.LOG);
    }

    /**
     * Produces a warning message with the given content and the current timestamp.
     *
     * @param content Message content.
     */
    public void warning(String content) {
        this.send(content, Type.WARNING);
    }

    /**
     * Produces an error message with the given content and the current timestamp.
     *
     * @param content Message content.
     */
    public void error(String content) {
        this.send(content, Type.ERROR);
    }

    /**
     * Message types.
     */
    public enum Type {
        LOG,
        WARNING,
        ERROR,
    }

    /**
     * Message wrapper for {@link IMessage} observer data.
     */
    public static class Message extends IMessage<Message.Type, Message.MessageData> {

        public Message(Message.Type status, Message.MessageData data) {
            super(status, data);
        }

        public enum Type {
            Message,
        }

        public static class MessageData extends IMessageData<IMessageGenerator> {

            private final IMessageGenerator.Type type;
            private final String content;
            private final String timestamp;

            public MessageData(IMessageGenerator sender, IMessageGenerator.Type type, String content, String timestamp) {
                super(sender);
                this.type = type;
                this.content = content;
                this.timestamp = timestamp;
            }

            public IMessageGenerator.Type getType() {
                return type;
            }

            public String getContent() {
                return content;
            }

            public String getTimestamp() {
                return timestamp;
            }
        }
    }
}
