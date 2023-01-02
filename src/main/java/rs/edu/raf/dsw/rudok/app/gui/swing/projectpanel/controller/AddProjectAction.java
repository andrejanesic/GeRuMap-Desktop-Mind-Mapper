package rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.controller;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.filesystem.local.LocalFileSystem;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.EditMindMapDialog;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

import java.awt.event.ActionEvent;

/**
 * Adds a child {@link MindMap} to the currently open {@link Project}.
 */
public class AddProjectAction extends IProjectAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        Project p = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().getProject();
        EditMindMapDialog d = new EditMindMapDialog(p, null);
        d.setVisible(true);
        if (d.getResult() == null) return;
        switch (d.getResult()) {
            case CONFIRMED: {
                // MindMap c = new MindMap(d.getIsTemplate(), d.getNodeName());
                // p.addChild(c);

                // Project spec required the use of factory pattern - however the method above may be more streamlined.
                MindMap child = (MindMap) MapNodeFactoryUtils.getFactory(p).createNode();
                child.setNodeName(d.getNodeName());
                child.setTemplate(d.getIsTemplate());

                //AppCore.getInstance().getFileSystem().loadMindMapTemplate();

                if (d.getTemplate() != null) {
                    MindMap template = d.getTemplate();
                    child.copyTemplate(template);
                }
                break;
            }
            case CANCELED:
            default: {
                break;
            }
        }
    }
}
