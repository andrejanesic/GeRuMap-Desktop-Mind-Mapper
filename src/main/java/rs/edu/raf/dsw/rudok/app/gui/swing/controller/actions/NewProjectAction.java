package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.EditProjectDialog;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import java.awt.event.ActionEvent;

/**
 * Creates a new {@link Project} on the root {@link ProjectExplorer}.
 */
public class NewProjectAction extends IAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        EditProjectDialog editProjectDialog = new EditProjectDialog(null, MainFrame.getInstance(), "Create a project", true);
        editProjectDialog.setVisible(true);
        String nodeName = editProjectDialog.getNodeName();
        String authorName = editProjectDialog.getAuthorName();
        String filepath = editProjectDialog.getFilepath();
        if (nodeName.length() == 0 || authorName.length() == 0 || filepath.length() == 0) {
            // TODO throw err
            return;
        }
        Project child = new Project(nodeName, authorName, filepath);
        ProjectExplorer parent = AppCore.getInstance().getProjectExplorer();
        parent.addChild(child);
    }

}
