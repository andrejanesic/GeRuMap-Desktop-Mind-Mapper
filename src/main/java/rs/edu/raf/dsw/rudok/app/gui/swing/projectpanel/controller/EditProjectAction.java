package rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.EditProjectDialog;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import java.awt.event.ActionEvent;

public class EditProjectAction extends IProjectAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        Project project = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().getProject();
        EditProjectDialog editProjectDialog = new EditProjectDialog(project, MainFrame.getInstance(), "Project author modification", true);
        editProjectDialog.setVisible(true);
        String nodeName = editProjectDialog.getNodeName();
        String authorName = editProjectDialog.getAuthorName();
        String filepath = editProjectDialog.getFilepath();
        project.setNodeName(nodeName);
        project.setAuthorName(authorName);
        project.setFilepath(filepath);
    }
}
