package rs.edu.raf.dsw.rudok.app.messagegenerator;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * <h1>Message generator</h1>
 * Used for generating messages propagated throughout the system, such as logs, warnings, errors, etc.
 */
public interface IMessageGenerator {

    /**
     * Sends the message with the given content, type and timestamp.
     *
     * @param content   Message content.
     * @param type      Message type.
     * @param timestamp Message timestamp.
     */
    void send(String content, Type type, String timestamp);

    /**
     * Sends the message with the given content and type.
     *
     * @param content Message content.
     * @param type    Message type.
     */
    default void send(String content, Type type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp t = new Timestamp(System.currentTimeMillis());
        this.send(content, type, sdf.format(t));
    }

    /**
     * Produces a log message with the given content and the current timestamp.
     *
     * @param content Message content.
     */
    default void log(String content) {
        this.send(content, Type.LOG);
    }

    /**
     * Produces a warning message with the given content and the current timestamp.
     *
     * @param content Message content.
     */
    default void warning(String content) {
        this.send(content, Type.WARNING);
    }

    /**
     * Produces an error message with the given content and the current timestamp.
     *
     * @param content Message content.
     */
    default void error(String content) {
        this.send(content, Type.ERROR);
    }

    enum Type {
        LOG,
        WARNING,
        ERROR,
    }
}
