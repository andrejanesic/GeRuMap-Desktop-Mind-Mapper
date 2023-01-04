package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.IProjectPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * <h1>Save project as action</h1>
 * Saves the currently open {@link Project} under a different name.
 */
public class SaveProjectAsAction extends IAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        final JFileChooser fc = new JFileChooser();
        fc.showSaveDialog(MainFrame.getInstance());
        if (fc.getSelectedFile() == null) {
            AppCore.getInstance().getMessageGenerator().error("No file selected");
            return;
        }
        String path = fc.getSelectedFile().getAbsolutePath();

        IProjectPanel panel = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel();
        if (panel == null) return;
        Project p = panel.getProject();
        if (p == null) return;

        p.setFilepath(path);
        if (AppCore.getInstance().getFileSystem().saveProject(p)) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Saved project");
        }
    }
}
