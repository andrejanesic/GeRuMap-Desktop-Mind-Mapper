package rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
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
        MindMap c = new MindMap(false, "New mind map");
        p.addChild(c);
    }
}
