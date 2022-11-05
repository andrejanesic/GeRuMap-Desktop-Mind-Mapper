package rs.edu.raf.dsw.rudok.app.gui.swing.tree;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.view.MapTreeView;
import rs.edu.raf.dsw.rudok.app.observer.IObserver;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class MapTree extends IObserver implements IMapTree {

    private MapTreeItem root;
    private DefaultTreeModel defaultTreeModel;
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
        root = new MapTreeItem(projectExplorer);
        defaultTreeModel = new DefaultTreeModel(root);
        treeView = new MapTreeView(defaultTreeModel);
        return treeView;
    }

    @Override
    public void refreshTree(boolean expand) {
        if (expand)
            treeView.expandPath(treeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public MapTreeItem getSelectedNode() {
        return (MapTreeItem) treeView.getLastSelectedPathComponent();
    }
}
