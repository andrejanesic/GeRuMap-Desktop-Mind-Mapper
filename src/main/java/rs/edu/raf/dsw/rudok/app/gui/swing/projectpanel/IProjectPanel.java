package rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.controller.IProjectActionManager;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;

/**
 * Hosts a tabbed pane of all opened {@link MindMap}s. Listens for opening and closing.
 */
public interface IProjectPanel {

    /**
     * Opens a new {@link MindMap} in the panel.
     *
     * @param m MindMap to open.
     */
    void openMindMap(MindMap m);

    /**
     * Closes a {@link MindMap} in the panel.
     *
     * @param m MindMap to open.
     */
    void closeMindMap(MindMap m);

    /**
     * {@link Project} of the panel.
     *
     * @return {@link Project}.
     */
    Project getProject();

    /**
     * {@link IProjectActionManager} of the panel.
     *
     * @return {@link IProjectActionManager}.
     */
    IProjectActionManager getProjectActionManager();

    /**
     * Returns the currently open {@link IMindMapPanel}, if any is open. Null otherwise.
     *
     * @return The currently open {@link IMindMapPanel}, if any is open. Null otherwise.
     */
    IMindMapPanel getActiveMindMapPanel();
}
