package rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class MapTreeCellEditor extends DefaultTreeCellEditor implements ActionListener {

    private Object clickedOn = null;
    private JTextField edit = null;

    public MapTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        clickedOn = value;
        edit = new JTextField(value.toString());
        edit.addActionListener(this);
        return edit;
    }

    @Override
    public boolean isCellEditable(EventObject event) {
        if (event instanceof MouseEvent) {

            if (((MouseEvent) event).getClickCount() > 0) {
                MapTreeItem selected = MainFrame.getInstance().getMapTree().getSelectedNode();
                if (selected != null) {

                    IMapNode node = selected.getMapNode();
                    if (node instanceof Project) {
                        MainFrame.getInstance().getProjectExplorerPanel().openProject((Project) node);
                    }
                }
            }

            if (((MouseEvent) event).getClickCount() == 3) {
                MapTreeItem selected = MainFrame.getInstance().getMapTree().getSelectedNode();
                if (selected == null)
                    return false;

                IMapNode node = selected.getMapNode();
                return !(node instanceof ProjectExplorer);
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(clickedOn instanceof MapTreeItem))
            return;

        if (!e.getActionCommand().matches("^[a-zA-Z0-9-_.& ]+$")) {
            // TODO log error, invalid name string
            return;
        }

        MapTreeItem clicked = (MapTreeItem) clickedOn;

        if (clicked.getMapNode() instanceof ProjectExplorer)
            return;

        clicked.setName(e.getActionCommand());
    }
}
