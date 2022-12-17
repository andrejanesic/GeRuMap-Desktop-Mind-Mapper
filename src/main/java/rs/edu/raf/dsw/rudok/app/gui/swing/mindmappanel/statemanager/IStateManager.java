package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager;

/**
 * <h1>State Manager</h1>
 * Manages the states of the app interaction.
 */
public interface IStateManager {

    /**
     * Initializes all states.
     */
    void init();

    /**
     * Sets the state of adding a new topic.
     */
    void setAddTopic();

    /**
     * Sets the state of selecting a topic.
     */
    void setSelectTopic();

    /**
     * Sets the state of moving topics.
     */
    void setMoveTopic();



    /**
     * Sets the state of drawing a new connection.
     */
    void setDrawConnection();

    /**
     * Sets the state of deleting an element.
     */
    void setDeleteElement();

    /**
     * Current app {@link IState}.
     *
     * @return Current app {@link IState}.
     */
    IState getState();

    /**
     * Rolls back to the previous state.
     */
    void rollback();
}
