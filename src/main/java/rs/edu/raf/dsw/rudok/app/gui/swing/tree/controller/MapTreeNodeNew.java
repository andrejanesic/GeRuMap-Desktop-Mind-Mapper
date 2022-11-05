package rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.view.MapTreeView;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.*;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.nio.file.Paths;

public class MapTreeNodeNew extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        MapTreeView mapTreeView = MainFrame.getInstance().getMapTreeView();
        MapTreeItem item = (MapTreeItem)mapTreeView.getLastSelectedPathComponent();
        IMapNode node = item.getiMapNode();
        IMapNode child;
        if(node instanceof IMapNodeComposite){
           IMapNodeComposite parent = (IMapNodeComposite)node;
            if(parent instanceof ProjectExplorer){
                child = new Project("a","a","a");
            } else if(parent instanceof Project){
                child = new MindMap(false,"a");
            } else {
                child = new Element("a");
            }
            parent.addChild(child);
            System.out.println(parent.getChildren());
        }
        mapTreeView.expandPath(mapTreeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(mapTreeView);


    }
}
