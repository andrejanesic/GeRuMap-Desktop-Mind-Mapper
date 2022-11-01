package rs.edu.raf.dsw.rudok.app.gui.swing.view.tree.view;

import rs.edu.raf.dsw.rudok.app.gui.swing.view.tree.controller.MapTreeCellEditor;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class MapTreeView extends JTree {

public MapTreeView(DefaultTreeModel newModel) {
        this.setModel(newModel);
        MapTreeCellRenderer renderer = new MapTreeCellRenderer();
        this.setCellEditor(new MapTreeCellEditor(this, renderer));
        this.setCellRenderer(renderer);
    }
}
