package rs.edu.raf.dsw.rudok.app.gui.swing.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Handles the creation of a new project, mind map or element.
 */
public class NewAction extends AbstractAction {

    public NewAction() {
        // TODO add interactive detail
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MapTreeItem selected = MainFrame.getInstance().getMapTree().getSelectedNode();
        IMapNode parent = selected.getMapNode();

        // new project added
        if (parent instanceof ProjectExplorer) {
            // TODO connect this with project autosave
            // TODO this should use data from current user
            // TODO how do we set the project filepath for this?
            Project child = new Project("New project", "Foo", null);
            ((ProjectExplorer) parent).addChild(child);
            return;
        }

        // new mindmap added
        if (parent instanceof Project) {
            MindMap child = new MindMap(false, "New mind map");
            ((Project) parent).addChild(child);
            return;
        }

        // new element added
        if (parent instanceof MindMap) {
            Element child = new Element("New element");
            ((MindMap) parent).addChild(child);
            return;
        }

        if (parent instanceof Element) {
            // TODO log error, cannot be done
            return;
        }
        // TODO extra case here, unidentified impl of IMapNode--handled by Addon?
    }
}
