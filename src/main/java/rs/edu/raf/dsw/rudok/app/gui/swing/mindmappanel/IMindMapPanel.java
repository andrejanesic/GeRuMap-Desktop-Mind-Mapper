package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IStateManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller.IStateActionManager;

/**
 * The GUI interface of mind maps.
 */
public interface IMindMapPanel {

    /**
     * Returns the {@link IStateActionManager} for this {@link IMindMapPanel} instance.
     *
     * @return {@link IStateActionManager} for this {@link IMindMapPanel} instance.
     */
    IStateActionManager getStateActionManager();

    /**
     * Returns the {@link IStateManager} for this {@link IMindMapPanel} instance.
     *
     * @return {@link IStateManager} for this {@link IMindMapPanel} instance.
     */
    IStateManager getStateManager();

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
     * Performs the state migration for a mouse click event.
     *
     * @param x Mouse click X coordinate.
     * @param y Mouse click Y coordinate.
     */
    void mouseClickStateMigrate(int x, int y);


    /**
     * Performs the state migration for a mouse draw.
     *
     * @param x1 Mouse draw start X coordinate.
     * @param y1 Mouse draw start Y coordinate.
     * @param x2 Mouse draw end X coordinate.
     * @param y2 Mouse draw end Y coordinate.
     */
    void mouseDrawStateMigrate(int x1, int y1, int x2, int y2);
}
