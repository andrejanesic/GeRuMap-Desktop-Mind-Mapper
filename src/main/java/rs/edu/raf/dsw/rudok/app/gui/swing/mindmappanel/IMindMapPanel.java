package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.controller.IActionManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.controller.IDiagramController;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IStateManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller.IStateActionManager;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

/**
 * The GUI interface of mind maps.
 */
public interface IMindMapPanel {

    /**
     * Returns the {@link MindMap} associated with this panel.
     *
     * @return {@link MindMap} associated with this panel.
     */
    MindMap getMindMap();

    /**
     * Returns the {@link IStateManager} for this {@link IMindMapPanel} instance.
     *
     * @return {@link IStateManager} for this {@link IMindMapPanel} instance.
     */
    IStateManager getStateManager();

    IActionManager getActionManager();

    /**
     * Returns the {@link IStateActionManager} for this {@link IMindMapPanel} instance.
     *
     * @return {@link IStateActionManager} for this {@link IMindMapPanel} instance.
     */
    IStateActionManager getStateActionManager();

    IDiagramController getDiagramController();

    /**
     * Starts add topic state.
     */
    void startAddTopicState();

    /**
     * Starts delete element state.
     */
    void startDeleteElementState();

    /**
     * Starts draw connection state.
     */
    void startDrawConnectionState();

    /**
     * Starts select topic state.
     */
    void startSelectTopicState();

    /**
     * Performs the state migration for a mouse draw.
     *  @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param complete
     */
    void mouseDrawStateMigrate(int x1, int y1, int x2, int y2, boolean complete);

    /**
     * Performs the state migration for a mouse draw.
     *
     * @param t1
     * @param t2
     */
    void mouseDrawStateMigrate(Topic t1, Topic t2);

    /**
     * Performs the state migration for a mouse click.
     *
     * @param x1
     * @param y1
     */
    void mouseClickStateMigrate(int x1, int y1);

    /**
     * Performs the state migration for a mouse click.
     *
     * @param topic
     */
    void mouseClickStateMigrate(Topic topic);

    /**
     * Performs the state migration for a mouse draw.
     *
     * @param topics
     */
    void mouseDrawStateMigrate(Topic... topics);

    /**
     * Performs the state migration for a mouse draw.
     *  @param t
     * @param x1
     * @param y1
     * @param complete
     */
    void mouseDrawStateMigrate(Topic t, int x1, int y1, boolean complete);
}
