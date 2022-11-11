package rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.controller;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteProjectAction extends IProjectAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        Project project = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().getProject();
        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this project?",
                "Confirm delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (result == 0) {
            // AppCore.getInstance().getProjectExplorer().removeChild(project);
        }
    }
}
