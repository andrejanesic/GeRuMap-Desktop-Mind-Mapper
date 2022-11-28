package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager;

/**
 * <h1>State</h1>
 * Application interaction state.
 */
public interface IState {

    /**
     * Performs the {@link IState} migration.
     *
     * @param params Parameters.
     */
    void migrate(Object ...params);

    /**
     * Rolls back the state.
     */
    void rollback();
}
