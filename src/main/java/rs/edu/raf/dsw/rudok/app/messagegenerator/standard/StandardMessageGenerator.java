package rs.edu.raf.dsw.rudok.app.messagegenerator.standard;

import rs.edu.raf.dsw.rudok.app.messagegenerator.IMessageGenerator;

/**
 * <h1>Standard message generator</h1>
 * Standard implementation of the {@link IMessageGenerator} service.
 */
public class StandardMessageGenerator extends IMessageGenerator {

    @Override
    public void send(String content, Type type, String timestamp) {
        this.publish(new Message(Message.Type.Message, new Message.MessageData(
                this, type, content, timestamp
        )));
    }

}
