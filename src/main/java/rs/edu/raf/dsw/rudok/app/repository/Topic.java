package rs.edu.raf.dsw.rudok.app.repository;

import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a topic/"bubble" in the {@link MindMap}.
 */
public class Topic extends Element {

    /**
     * Map of all {@link Connection}s to other {@link Topic}s.
     */
    private final Map<Topic, Connection> connections;

    /**
     * Width and height are considered as top and bottom padding. The original height the text height and is determined
     * by the GUI component.
     */
    private int width, height, x, y;

    /**
     * Whether this topic has been selected by the user or not. Non-persistent attribute.
     */
    private boolean selected = false;

    public Topic(String nodeName, int stroke, String color, int x, int y, int w, int h) {
        super(nodeName, stroke, color);
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        connections = new HashMap<>();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "width",
                        width
                )
        ));
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "height",
                        height
                )
        ));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "x",
                        x
                )
        ));
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "y",
                        y
                )
        ));
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "selected",
                        selected
                )
        ));
    }

    /**
     * Connects this {@link Topic} to the passed {@link Topic}.
     *
     * @param target {@link Topic} to connect to.
     * @return {@link Connection}.
     */
    public Connection connect(Topic target) {
        Connection c = connections.getOrDefault(target, null);
        if (c != null) return c;
        IMapNodeComposite p = getParents().iterator().next();
        c = (Connection) MapNodeFactoryUtils.getFactory(p)
                .createNode(ElementFactory.Type.Connection,
                        this,
                        target);
        if (c == null) return null;
        connections.put(target, c);
        return c;
    }

    public Map<Topic, Connection> getConnections() {
        return connections;
    }

    @Override
    public void receive(Object message) {
        super.receive(message);

        if (message instanceof IMapNodeComposite.Message) {

            // Listens for the creation of a new connection between this topic and another one and adds it to the
            // connections hashmap.
            if (((IMapNodeComposite.Message) message).getStatus()
                    .equals(IMapNodeComposite.Message.Type.CHILD_ADDED)) {
                IMapNodeComposite.Message.ChildChangeMessageData d = (IMapNodeComposite.Message.ChildChangeMessageData)
                        ((IMapNodeComposite.Message) message).getData();

                if (!(d.getChild() instanceof Connection)) return;
                Connection c = (Connection) d.getChild();
                if (c.getFrom() != this && c.getTo() != this) return;
                Topic target = c.getFrom() != this ? c.getFrom() : c.getTo();
                connections.put(target, c);
            }

            // Listen to connected topics being removed and remove the associated connections.
            if (((IMapNodeComposite.Message) message).getStatus()
                    .equals(IMapNodeComposite.Message.Type.CHILD_REMOVED)) {
                IMapNodeComposite.Message.ChildChangeMessageData d = (IMapNodeComposite.Message.ChildChangeMessageData)
                        ((IMapNodeComposite.Message) message).getData();

                if (!(d.getChild() instanceof Topic)) return;
                if (!connections.containsKey(d.getChild())) return;
                connections.remove(d.getChild());
            }
        }
    }
}
