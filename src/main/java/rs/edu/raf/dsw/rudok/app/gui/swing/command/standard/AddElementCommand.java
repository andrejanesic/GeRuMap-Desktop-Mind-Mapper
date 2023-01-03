package rs.edu.raf.dsw.rudok.app.gui.swing.command.standard;

import rs.edu.raf.dsw.rudok.app.gui.swing.command.AbstractCommand;
import rs.edu.raf.dsw.rudok.app.repository.Connection;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.IMapNodeFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

/**
 * This command adds a new {@link Element} of the given type to its parent {@link MindMap}.
 */
public class AddElementCommand extends AbstractCommand {

    /**
     * Host mind map.
     */
    private final MindMap parent;

    /**
     * Type of created element.
     */
    private final ElementFactory.Type kind;

    /**
     * Params for {@link Topic} creation.
     */
    private int x, y, width, height;

    /**
     * Params for {@link Connection} creation.
     */
    private Topic from, to;

    /**
     * The created element.
     */
    private Element target;

    /**
     * Adds a new {@link Topic}.
     *
     * @param parent Parent {@link MindMap}.
     * @param x      {@link Topic} x position.
     * @param y      {@link Topic} y position.
     * @param width  {@link Topic} width.
     * @param height {@link Topic} height.
     */
    public AddElementCommand(MindMap parent, int x, int y, int width, int height) {
        kind = ElementFactory.Type.Topic;
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Adds a new {@link Connection} between the given {@link Topic}s.
     *
     * @param parent Parent {@link MindMap}.
     * @param from   Starting {@link Topic}.
     * @param to     Ending {@link Topic}.
     */
    public AddElementCommand(MindMap parent, Topic from, Topic to) {
        kind = ElementFactory.Type.Connection;
        this.parent = parent;
        this.from = from;
        this.to = to;
    }

    @Override
    public void doCommand() {
        if (isCommitted()) {
            return;
        }

        if (target != null) {
            redoCommand();
            return;
        }

        if (kind.equals(ElementFactory.Type.Topic)) {
            IMapNodeFactory factory = MapNodeFactoryUtils.getFactory(parent);
            target = (Element) factory.createNode(ElementFactory.Type.Topic, x, y, width, height);
            setCommitted(true);
            return;
        }

        if (kind.equals(ElementFactory.Type.Connection)) {
            IMapNodeFactory factory = MapNodeFactoryUtils.getFactory(parent);
            target = (Element) factory.createNode(ElementFactory.Type.Connection, from, to);
            setCommitted(true);
            return;
        }

        throw new RuntimeException("ElementFactory.Type." + kind.name() + " not supported!");
    }

    @Override
    public void redoCommand() {
        if (isCommitted()) {
            return;
        }
        parent.addChild(target);
        setCommitted(true);
    }

    @Override
    public void undoCommand() {
        if (!isCommitted()) {
            return;
        }
        parent.removeChild(target);
        setCommitted(false);
    }
}
