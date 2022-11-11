package rs.edu.raf.dsw.rudok.app.gui.swing.tree.view;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.listeners.MapTreeCellEditor;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.listeners.MapTreeSelectionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class MapTreeView extends JTree {

    public MapTreeView(DefaultTreeModel newModel) {
        setModel(newModel);
        MapTreeCellRenderer renderer = new MapTreeCellRenderer();
        addTreeSelectionListener(new MapTreeSelectionListener());
        setCellRenderer(renderer);
        setCellEditor(new MapTreeCellEditor(this, renderer));
        setEditable(true);
    }
}
