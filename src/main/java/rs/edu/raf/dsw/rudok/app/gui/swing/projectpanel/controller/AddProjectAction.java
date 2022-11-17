package rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.EditMindMapDialog;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;

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
                MindMap c = new MindMap(d.getIsTemplate(), d.getNodeName());
                p.addChild(c);
                break;
            }
            case CANCELED:
            default: {
                break;
            }
        }
    }
}
