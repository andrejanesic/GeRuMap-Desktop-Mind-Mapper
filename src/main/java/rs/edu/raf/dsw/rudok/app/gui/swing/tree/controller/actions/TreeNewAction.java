package rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.actions;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.EditMindMapDialog;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.EditProjectDialog;
import rs.edu.raf.dsw.rudok.app.repository.*;

import java.awt.event.ActionEvent;

/**
 * Handles the creation of a new project, mind map or element.
 */
public class TreeNewAction extends ITreeAction {

    public TreeNewAction() {
        putValue(SMALL_ICON, loadIcon("/images/plus.png"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (MainFrame.getInstance().getMapTree().getSelectedNode() != null) {
            MapTreeItem selected = MainFrame.getInstance().getMapTree().getSelectedNode();
            IMapNode parent = selected.getMapNode();

            // new project added
            if (parent instanceof ProjectExplorer) {
                // TODO connect this with project autosave
                // TODO this should use data from current user
                // TODO how do we set the project filepath for this?
                EditProjectDialog editProjectDialog = new EditProjectDialog(null, MainFrame.getInstance(), "Create a project", true);
                editProjectDialog.setVisible(true);
                if (!editProjectDialog.isConfirmed()) return;
                String nodeName = editProjectDialog.getNodeName();
                String authorName = editProjectDialog.getAuthorName();
                String filepath = editProjectDialog.getFilepath();
                Project child = new Project(nodeName, authorName, filepath);
                ((ProjectExplorer) parent).addChild(child);
                return;
            }

            // new mindmap added
            if (parent instanceof Project) {
                EditMindMapDialog d = new EditMindMapDialog((Project) parent, null);
                d.setVisible(true);
                if (d.getResult() == null) return;
                switch (d.getResult()) {
                    case CONFIRMED: {
                        MindMap child = new MindMap(d.getIsTemplate(), d.getNodeName());
                        ((Project) parent).addChild(child);
                        child.addParent((Project) parent);
                        break;
                    }
                    case CANCELED:
                    default: {
                        break;
                    }
                }
                return;
            }

            // new element added
            if (parent instanceof MindMap) {
                Element child = new Element("New element");
                ((MindMap) parent).addChild(child);
                child.addParent((MindMap) parent);
                return;
            }

            if (parent instanceof Element) {
                // TODO log error, cannot be done
                return;
            }
            // TODO extra case here, unidentified impl of IMapNode--handled by Addon?
        }
    }
}
