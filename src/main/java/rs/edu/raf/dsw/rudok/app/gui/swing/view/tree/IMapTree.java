package rs.edu.raf.dsw.rudok.app.gui.swing.view.tree;

import rs.edu.raf.dsw.rudok.app.gui.swing.view.tree.view.MapTreeView;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import javax.swing.*;

public interface IMapTree {
    MapTreeView generateTree(ProjectExplorer projectExplorer);
}
