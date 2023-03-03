package rs.edu.raf.dsw.rudok.app.repository.nodefactory;

import rs.edu.raf.dsw.rudok.app.repository.*;

/**
 * <h1>Element factory</h1>
 * Handles the creation of {@link Element} nodes with or without parent {@link MindMap}s.
 */
public class ElementFactory extends IMapNodeFactory {

    public static int TOPIC_DEFAULT_WIDTH = 10;
    public static int TOPIC_DEFAULT_HEIGHT = 10;
    /**
     * For counting all children created thus far.
     */
    private static int CHILD_ID = 0;

    public ElementFactory(MindMap parent) {
        super(parent);
    }

    @Override
    public IMapNode createNode(Object... params) {
        try {
            if (!(params[0] instanceof Type)) {
                throw new RuntimeException("Programmatic error: invalid parameters to ElementFactory.createNode");
            }

            Element child;
            if (params[0].equals(Type.Connection)) {
                if ((!(params[1] instanceof Topic)) || (!(params[2] instanceof Topic))) {
                    throw new RuntimeException("Programmatic error: invalid parameters to ElementFactory.createNode");
                }

                Topic from = (Topic) params[1];
                Topic to = (Topic) params[2];
                if (from == to) return null;

                Connection c = from.getConnections().getOrDefault(to, null);
                if (c != null) return c;

                child = new Connection(
                        "New connection " + ++CHILD_ID,
                        2,
                        "#FFFFFF",
                        (Topic) params[1],
                        (Topic) params[2]
                );
            } else if (params[0].equals(Type.Topic)) {
                int x = (int) params[1];
                int y = (int) params[2];
                int w = TOPIC_DEFAULT_WIDTH;
                int h = TOPIC_DEFAULT_HEIGHT;
                if (params.length >= 5) {
                    w = (int) params[3];
                    h = (int) params[4];
                }

                child = new Topic(
                        "New topic " + ++CHILD_ID,
                        2,
                        "#FFFFFF",
                        x,
                        y,
                        w,
                        h
                );
            } else {
                throw new RuntimeException("Programmatic error: invalid parameters to ElementFactory.createNode");
            }

            if (getParent() == null) {
                return child;
            }

            getParent().addChild(child);
            return child;
        } catch (ClassCastException e) {
            // TODO programmatic error, should never happen
            return null;
        }
    }

    public enum Type {
        Connection,
        Topic
    }
}
