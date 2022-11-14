package rs.edu.raf.dsw.rudok.app.gui.swing.tree.view;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

public class MapTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        URL imageURL = null;

        if (((MapTreeItem)value).getMapNode() instanceof ProjectExplorer) {
            imageURL = getClass().getResource("/images/iconmonstr-folder-29-16.png");
        }
        else if (((MapTreeItem)value).getMapNode() instanceof Project) {
            imageURL = getClass().getResource("/images/iconmonstr-folder-open-thin-16.png");
        }else if (((MapTreeItem)value).getMapNode() instanceof MindMap) {
            imageURL = getClass().getResource("/images/iconmonstr-map-thin-16.png");
        }else if (((MapTreeItem)value).getMapNode() instanceof Element) {
            imageURL = getClass().getResource("/images/iconmonstr-idea-12-16.png");
        }

        Icon icon = null;
        if (imageURL != null)
            icon = new ImageIcon(imageURL);
        setIcon(icon);

        return this;

    }
}
