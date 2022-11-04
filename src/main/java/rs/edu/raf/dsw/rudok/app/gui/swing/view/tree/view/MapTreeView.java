package rs.edu.raf.dsw.rudok.app.gui.swing.view.tree.view;

import rs.edu.raf.dsw.rudok.app.gui.swing.view.tree.controller.MapTreeCellEditor;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.tree.controller.MapTreeSelectionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class MapTreeView extends JTree {

public MapTreeView(DefaultTreeModel newModel) {
        this.setModel(newModel);
        MapTreeCellRenderer renderer = new MapTreeCellRenderer();
        this.addTreeSelectionListener(new MapTreeSelectionListener());
        this.setCellRenderer(renderer);
        this.setCellEditor(new MapTreeCellEditor(this, renderer));
        this.setEditable(true);
    }
}
