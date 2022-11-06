package rs.edu.raf.dsw.rudok.app.gui.swing.projectexplorerpanel;

import rs.edu.raf.dsw.rudok.app.repository.Project;

/**
 * Hosts a tabbed pane of all opened {@link Project}s. Listens for opening and closing.
 */
public interface IProjectExplorerPanel {

    /**
     * Opens a new {@link Project} in the panel.
     *
     * @param p Project to open.
     */
    void openProject(Project p);

    /**
     * Closes a {@link Project} in the panel.
     *
     * @param p Project to open.
     */
    void closeProject(Project p);
}
