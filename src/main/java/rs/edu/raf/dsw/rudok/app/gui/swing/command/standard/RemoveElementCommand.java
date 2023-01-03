package rs.edu.raf.dsw.rudok.app.gui.swing.command.standard;

import rs.edu.raf.dsw.rudok.app.gui.swing.command.AbstractCommand;
import rs.edu.raf.dsw.rudok.app.repository.Connection;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import java.util.Collection;
import java.util.HashSet;

/**
 * This command removes the passed {@link Element}s from their parent {@link MindMap}.
 */
public class RemoveElementCommand extends AbstractCommand {

    /**
     * Parent {@link MindMap} for the removed {@link Element}s.
     */
    private final MindMap parent;

    /**
     * {@link Element}s to be removed on execution.
     */
    private final Element[] toRemove;

    /**
     * {@link Connection}s deleted while removing {@link Topic}s.
     */
    private final Collection<Connection> removedConnections = new HashSet<>();

    public RemoveElementCommand(MindMap parent, Element... toRemove) {
        this.parent = parent;
        this.toRemove = toRemove;
    }

    @Override
    public void doCommand() {
        if (isCommitted()) return;
        for (Element e : toRemove) {
            if (e instanceof Topic) {
                removedConnections.addAll(((Topic) e).getConnections().values());
            }
            parent.removeChild(e);
        }
        setCommitted(true);
    }

    @Override
    public void redoCommand() {
        doCommand();
    }

    @Override
    public void undoCommand() {
        if (!isCommitted()) return;
        for (Element e : toRemove) {
            parent.addChild(e);
        }
        for (Connection c : removedConnections) {
            parent.addChild(c);
        }
        setCommitted(false);
    }
}
