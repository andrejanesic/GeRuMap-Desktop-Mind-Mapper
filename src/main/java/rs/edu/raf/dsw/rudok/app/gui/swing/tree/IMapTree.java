package rs.edu.raf.dsw.rudok.app.gui.swing.tree;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.view.MapTreeView;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

public interface IMapTree {
    MapTreeView generateTree(ProjectExplorer projectExplorer);
}
