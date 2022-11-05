package rs.edu.raf.dsw.rudok.app.gui.swing.tree;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.view.MapTreeView;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import javax.swing.tree.DefaultTreeModel;

public class MapTree implements IMapTree{

    private MapTreeView mapTreeView;
    private DefaultTreeModel defaultTreeModel;

    @Override
    public MapTreeView generateTree(ProjectExplorer projectExplorer) {
        MapTreeItem root = new MapTreeItem(projectExplorer);
        defaultTreeModel = new DefaultTreeModel(root);
        mapTreeView = new MapTreeView(defaultTreeModel);
        return mapTreeView;
    }

}
