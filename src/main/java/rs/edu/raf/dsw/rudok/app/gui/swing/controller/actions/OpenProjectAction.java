package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Iterator;

/**
 * Opens a new {@link Project} from the given path string.
 */
public class OpenProjectAction extends IAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        int ret = fc.showOpenDialog(MainFrame.getInstance());
        ProjectExplorer parent = AppCore.getInstance().getProjectExplorer();

        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Project child = AppCore.getInstance().getFileSystem().loadProject(file.getAbsolutePath());
            if (child != null) {
                parent.addChild(child);
                MainFrame.getInstance().getMapTree().refreshTree();
            } else {
                AppCore.getInstance().getMessageGenerator().error("Could not load project");
            }
        }
    }

}
