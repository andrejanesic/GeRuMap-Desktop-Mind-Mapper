package rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.actions;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.command.CommandManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.command.CommandManagerFactory;
import rs.edu.raf.dsw.rudok.app.gui.swing.command.standard.AddElementCommand;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.controller.IDiagramController;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.IProjectPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.EditMindMapDialog;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.EditProjectDialog;
import rs.edu.raf.dsw.rudok.app.repository.*;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

// TODO Fix this

/**
 * Handles the creation of a new project, mind map or element.
 */
public class TreeNewAction extends ITreeAction {

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

                // Project child = new Project(nodeName, authorName, filepath);
                // ((ProjectExplorer) parent).addChild(child);

                // Project spec required the use of factory pattern - however the method above may be more streamlined.
                Project child = (Project) MapNodeFactoryUtils.getFactory(((ProjectExplorer) parent)).createNode();
                child.setNodeName(nodeName);
                child.setAuthorName(authorName);
                child.setFilepath(filepath);

                return;
            }

            // new mindmap added
            if (parent instanceof Project) {
                EditMindMapDialog d = new EditMindMapDialog(null);
                d.setVisible(true);
                if (d.getResult() == null) return;
                switch (d.getResult()) {
                    case CONFIRMED: {
                        // MindMap child = new MindMap(d.getIsTemplate(), d.getNodeName());
                        // ((Project) parent).addChild(child);

                        // Project spec required the use of factory pattern - however the method above may be more streamlined.
                        MindMap child = (MindMap) MapNodeFactoryUtils.getFactory((Project) parent).createNode();
                        child.setNodeName(d.getNodeName());
                        child.setTemplate(d.isTemplate());

                        if (d.getTemplate() == null) break;
                        MindMap template = AppCore.getInstance().getFileSystem().loadMindMapTemplate(d.getTemplate());
                        if (template == null) break; // errored
                        child.copyTemplate(template);
                        JOptionPane.showMessageDialog(MainFrame.getInstance(), "Template applied");
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
                IProjectPanel p = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel();
                if (p == null) {
                    AppCore.getInstance().getMessageGenerator().error("No project open");
                    return;
                }

                IMindMapPanel mp = p.getActiveMindMapPanel();
                if (mp == null) {
                    AppCore.getInstance().getMessageGenerator().error("No mind map open");
                    return;
                }

                IDiagramController dc = mp.getDiagramController();
                int x = dc.getView().getCenterX();
                int y = dc.getView().getCenterY();
                MindMap mindMap = (MindMap) parent;
                CommandManager cm = CommandManagerFactory.getCommandManager(mindMap);
                cm.addCommand(new AddElementCommand(
                        mindMap,
                        x,
                        y,
                        ElementFactory.TOPIC_DEFAULT_WIDTH,
                        ElementFactory.TOPIC_DEFAULT_HEIGHT
                ));
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
