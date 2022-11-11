package rs.edu.raf.dsw.rudok.app.gui.swing.tree;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.actions.ITreeActionManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.actions.TreeActionManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.listeners.MapTreeClickListener;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.view.MapTreeView;
import rs.edu.raf.dsw.rudok.app.observer.IObserver;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.LinkedList;
import java.util.List;

public class MapTree extends IObserver implements IMapTree {

    private static final ITreeActionManager treeActionManager = new TreeActionManager();
    private MapTreeView treeView;

    @Override
    public MapTreeView generateTree(ProjectExplorer projectExplorer) {
//        // Subscribe to all nodes for updates
//        traverse(projectExplorer, new ITraverseAction() {
//
//            @Override
//            public void execute(IMapNode current) {
//                current.addObserver(traverseRef);
//            }
//
//        });

        // Generate tree
        MapTreeItem root = new MapTreeItem(projectExplorer);
        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(root);
        treeView = new MapTreeView(defaultTreeModel);
        treeView.addMouseListener(new MapTreeClickListener());
        treeView.setSelectionPath(new TreePath(root));
        return treeView;
    }

    @Override
    public void refreshTree() {
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void expandTree(MapTreeItem toNode) {
        TreePath path = pathToRoot(toNode);
        treeView.expandPath(path);
        treeView.setSelectionPath(path);
        treeView.scrollPathToVisible(path);
    }

    /**
     * Finds the path to the root node from any {@link MapTreeItem} node. <b>WARNING: Works ONLY with single-parent
     * nodes!</b>
     *
     * @param node Node to find the path for.
     * @return {@link TreePath} to the given node.
     */
    private TreePath pathToRoot(MapTreeItem node) {
        List<MapTreeItem> objs = traverse(node, new LinkedList<>());
        return new TreePath(objs.toArray());
    }

    /**
     * Finds an array of nodes from the given node to the root. <b>WARNING: Works ONLY with single-parent nodes!</b>
     *
     * @param current Node being currently processed.
     * @param last    Last calculated {@link TreePath}.
     * @return {@link MapTreeItem}[] array from current node to root.
     */
    private List<MapTreeItem> traverse(MapTreeItem current, List<MapTreeItem> last) {
        if (current == null) return last;
        last.add(0, current);
        return traverse((MapTreeItem) current.getParent(), last);
    }

    @Override
    public MapTreeItem getSelectedNode() {
        return (MapTreeItem) treeView.getLastSelectedPathComponent();
    }

    @Override
    public ITreeActionManager getTreeActionManager() {
        return treeActionManager;
    }
}
