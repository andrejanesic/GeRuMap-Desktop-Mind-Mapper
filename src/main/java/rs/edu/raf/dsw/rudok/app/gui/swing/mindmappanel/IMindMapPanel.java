package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.controller.IDiagramController;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.IDiagramView;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IStateManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller.IStateActionManager;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

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
     * Performs the state migration for a mouse click event.
     *
     * @param target Target of mouse click.
     * @param x      Mouse click X coordinate.
     * @param y      Mouse click Y coordinate.
     */
    void mouseClickStateMigrate(Element target, int x, int y);


    /**
     * Performs the state migration for a mouse draw.
     *
     * @param target1 First target of mouse draw.
     * @param target2 Second target of mouse draw.
     * @param x1      Mouse draw start X coordinate.
     * @param y1      Mouse draw start Y coordinate.
     * @param x2      Mouse draw end X coordinate.
     * @param y2      Mouse draw end Y coordinate.
     */
    void mouseDrawStateMigrate(Element target1, Element target2, int x1, int y1, int x2, int y2);
}
