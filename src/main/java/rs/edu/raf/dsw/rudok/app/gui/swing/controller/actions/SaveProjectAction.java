package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.IProjectPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Set;

/**
 * <h1>Save project action</h1>
 * Saves the currently open {@link Project}, or all open {@link Project}s.
 */
public class SaveProjectAction extends IAction {

    /**
     * Whether to save all open projects or not.
     */
    private final boolean all;

    /**
     * Saves the currently open {@link Project}, or all open {@link Project}s.
     *
     * @param all Whether to save all projects or not.
     */
    public SaveProjectAction(boolean all) {
        this.all = all;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (all) {
            saveAll();
        } else {
            save();
        }
    }

    private void save() {
        IProjectPanel panel = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel();
        if (panel == null) return;
        Project p = panel.getProject();
        if (p == null) return;
        if (AppCore.getInstance().getFileSystem().saveProject(p)) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Saved project");
        }
    }

    private void saveAll() {
        Set<IMapNode> children = AppCore.getInstance().getProjectExplorer().getChildren();
        Iterator<IMapNode> it = children.iterator();
        boolean errored = false;
        while (it.hasNext()) {
            Project p = (Project) it.next();
            if (!AppCore.getInstance().getFileSystem().saveProject(p)) {
                errored = true;
                break;
            }
        }
        if (!errored) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Saved all projects");
        }
    }
}
