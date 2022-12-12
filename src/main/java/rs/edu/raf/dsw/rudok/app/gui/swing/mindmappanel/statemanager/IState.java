package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager;

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
    public void migrate(Object... params) {
        history.push(params);
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
    public abstract void rollback(Object... params);
}
