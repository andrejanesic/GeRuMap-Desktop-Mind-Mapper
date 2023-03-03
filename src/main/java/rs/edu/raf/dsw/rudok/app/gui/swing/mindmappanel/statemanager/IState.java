package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager;

import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import java.util.Stack;

/**
 * <h1>State</h1>
 * Application interaction state.
 */
public abstract class IState {

    /**
     * Array of parameters pushed onto the stack prior to conducting the migration. When migrating, new parameters are
     * pushed to the stack. During rollback, parameters are popped.
     */
    private final Stack<Object[]> history;

    public IState() {
        history = new Stack<>();
    }

    /**
     * Performs the {@link IState} migration.
     *
     * @param params Parameters.
     */
    protected void commit(Object... params) {
        history.push(params);
    }

    /**
     * Performs the migration of {@link IState} with the given parameters. May produce no result if not implemented.
     *
     * @param parent
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param complete
     */
    public void migrate(MindMap parent, int x1, int y1, int x2, int y2, boolean complete) {

    }

    /**
     * Performs the migration of {@link IState} with the given parameters. May produce no result if not implemented.
     *
     * @param parent
     * @param t1
     * @param t2
     */
    public void migrate(MindMap parent, Topic t1, Topic t2) {

    }

    /**
     * Performs the migration of {@link IState} with the given parameters. May produce no result if not implemented.
     *
     * @param parent
     * @param x1
     * @param y1
     */
    public void migrate(MindMap parent, int x1, int y1) {

    }

    /**
     * Performs the migration of {@link IState} with the given parameters. May produce no result if not implemented.
     *
     * @param parent
     * @param topics
     */
    public void migrate(MindMap parent, Topic... topics) {

    }

    /**
     * Performs the migration of {@link IState} with the given parameters. May produce no result if not implemented.
     *
     * @param parent
     * @param t
     * @param x1
     * @param y1
     * @param complete
     */
    public void migrate(MindMap parent, Topic t, int x1, int y1, boolean complete) {

    }

    /**
     * Rolls back the previous state.
     */
    public void rollback() {
        if (!history.empty())
            rollback(history.pop());
    }

    /**
     * Rolls back the previous state.
     *
     * @param params Parameters.
     */
    public void rollback(Object... params) {

    }

    /**
     * Clears the history stack.
     */
    public void clear() {
        history.clear();
    }
}
