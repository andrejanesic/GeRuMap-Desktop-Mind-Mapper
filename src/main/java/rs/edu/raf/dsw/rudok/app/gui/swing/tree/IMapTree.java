package rs.edu.raf.dsw.rudok.app.gui.swing.tree;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.actions.ITreeActionManager;
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
     * Refreshes the tree view.
     */
    void refreshTree();

    /**
     * Expands the tree to the given node.
     *
     * @param toNode Node to expand to.
     */
    void expandTree(MapTreeItem toNode);

    /**
     * Returns the currently selected {@link MapTreeItem} node.
     *
     * @return Selected {@link MapTreeItem} node.
     */
    MapTreeItem getSelectedNode();

    /**
     * Returns the root {@link MapTreeItem} node.
     *
     * @return Root {@link MapTreeItem} node.
     */
    MapTreeItem getRoot();

    /**
     * {@link ITreeActionManager} of the tree.
     *
     * @return {@link ITreeActionManager}.
     */
    ITreeActionManager getTreeActionManager();
}
