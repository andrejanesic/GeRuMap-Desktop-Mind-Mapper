package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import java.awt.event.ActionEvent;

/**
 * Saves and closes the currently open {@link Project}.
 */
public class CloseProjectAction extends IAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        Project selected = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().getProject();
        if (selected == null) return;
        ProjectExplorer root = AppCore.getInstance().getProjectExplorer();
        root.removeChild(selected);
    }
}
