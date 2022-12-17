package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.observer.IMessageData;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;
import rs.edu.raf.dsw.rudok.app.repository.Element;

import java.awt.*;

public abstract class ElementPainter extends IPublisher {

    /**
     * The {@link Element} to be painted.
     */
    private final Element element;

    public ElementPainter(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    /**
     * Draws the wrapped {@link Element} on the canvas {@link Graphics} g.
     *
     * @param g {@link Graphics} canvas to paint on.
     */
    public abstract void draw(Graphics2D g);

    /**
     * Returns true if the element's shape includes the passed {@link Point}.
     *
     * @param p {@link Point} where the interaction happened.
     * @return True if the element was clicked, false otherwise.
     */
    public abstract boolean elementAt(Point p);

    /**
     * The final painted {@link Shape}. May be null.
     *
     * @return The final painted {@link Shape}. May be null.
     */
    public abstract Shape getShape();

    /**
     * Message for publishing.
     */
    public static class Message extends IMessage<ElementPainter.Message.Type, Message.RepaintRequestMessageData> {

        public Message(Type status, RepaintRequestMessageData data) {
            super(status, data);
        }

        /**
         * Type of message.
         */
        public enum Type {
            REPAINT_REQUEST,
        }

        public static class RepaintRequestMessageData extends IMessageData<ElementPainter> {

            /**
             * Default constructor. Initializes the sender.
             *
             * @param sender Sender of the message.
             */
            public RepaintRequestMessageData(ElementPainter sender) {
                super(sender);
            }
        }
    }
}
