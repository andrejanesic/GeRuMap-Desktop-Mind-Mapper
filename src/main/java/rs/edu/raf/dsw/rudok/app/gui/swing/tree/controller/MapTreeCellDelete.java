package rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.view.MapTreeView;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MapTreeCellDelete extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        MapTreeView mapTreeView = MainFrame.getInstance().getMapTreeView();
        MapTreeItem item = (MapTreeItem) mapTreeView.getLastSelectedPathComponent();
        if(!(item.getiMapNode() instanceof ProjectExplorer)) item.removeFromParent();
    }
}
