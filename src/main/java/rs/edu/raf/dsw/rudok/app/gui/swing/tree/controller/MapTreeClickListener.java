package rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapTreeClickListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        MapTreeItem selected = MainFrame.getInstance().getMapTree().getSelectedNode();
        if (selected != null) {

            IMapNode node = selected.getMapNode();
            if (node instanceof Project) {
                MainFrame.getInstance().getProjectExplorerPanel().openProject((Project) node);
            }

            if (node instanceof MindMap) {
                // TODO this only works with single-parent nodes
                Project p = (Project) node.getParents().iterator().next();
                MainFrame.getInstance().getProjectExplorerPanel().openProject(p);
                MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().openMindMap((MindMap) node);
            }
        }
    }
}
