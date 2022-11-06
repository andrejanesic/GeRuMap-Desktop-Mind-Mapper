package rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel;

import rs.edu.raf.dsw.rudok.app.repository.MindMap;

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
}
