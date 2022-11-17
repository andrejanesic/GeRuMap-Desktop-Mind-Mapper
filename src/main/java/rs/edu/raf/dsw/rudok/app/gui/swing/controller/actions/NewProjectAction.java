package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.EditProjectDialog;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

import java.awt.event.ActionEvent;

/**
 * Creates a new {@link Project} on the root {@link ProjectExplorer}.
 */
public class NewProjectAction extends IAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        EditProjectDialog editProjectDialog = new EditProjectDialog(null, MainFrame.getInstance(), "Create a project", true);
        editProjectDialog.setVisible(true);
        if (!editProjectDialog.isConfirmed()) return;
        String nodeName = editProjectDialog.getNodeName();
        String authorName = editProjectDialog.getAuthorName();
        String filepath = editProjectDialog.getFilepath();

        // Project child = new Project(nodeName, authorName, filepath);
        // parent.addChild(child);

        // Project spec required the use of factory pattern - however the method above may be more streamlined.
        ProjectExplorer parent = AppCore.getInstance().getProjectExplorer();
        Project child = (Project) MapNodeFactoryUtils.getFactory(parent).createNode();
        child.setNodeName(nodeName);
        child.setAuthorName(authorName);
        child.setFilepath(filepath);
    }

}
