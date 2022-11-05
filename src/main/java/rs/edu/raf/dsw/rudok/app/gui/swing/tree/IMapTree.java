package rs.edu.raf.dsw.rudok.app.gui.swing.tree;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.view.MapTreeView;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

/**
 * Functionality provided by the MapTree.
 */
public interface IMapTree {

    /**
     * Called to initialize the tree.
     *
     * @param projectExplorer Root {@link ProjectExplorer} workspace node.
     * @return {@link MapTreeView} ready for rendering.
     */
    MapTreeView generateTree(ProjectExplorer projectExplorer);

    /**
     * Refreshes the tree view without expanding to any node.
     */
    default void refreshTree() {
        refreshTree(false);
    }

    /**
     * Refreshes the tree view and expands to the current selected node.
     *
     * @param expand True to expand, false to simply refresh.
     */
    void refreshTree(boolean expand);

    /**
     * Returns the currently selected {@link MapTreeItem} node.
     *
     * @return Selected {@link MapTreeItem} node.
     */
    MapTreeItem getSelectedNode();

}
