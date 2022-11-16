package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Saves and closes the currently open {@link Project}.
 */
public class CloseProjectAction extends IAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            Project selected = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().getProject();
            ProjectExplorer root = AppCore.getInstance().getProjectExplorer();
            root.removeChild(selected);
        }catch (Exception exception){
            AppCore.getInstance().getMessageGenerator().error("No project to close");
            //AppCore.getInstance().getGui().showDialog(,"No project to close");
        }
    }
}
