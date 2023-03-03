package rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.actions;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.command.CommandManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.command.CommandManagerFactory;
import rs.edu.raf.dsw.rudok.app.gui.swing.command.standard.RemoveElementCommand;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TreeDeleteAction extends ITreeAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (MainFrame.getInstance().getMapTree().getSelectedNode() != null) {
            MapTreeItem selected = MainFrame.getInstance().getMapTree().getSelectedNode();
            IMapNode selectedMapNode = selected.getMapNode();

            if (selectedMapNode instanceof ProjectExplorer) {
                AppCore.getInstance().getMessageGenerator().error("You can not delete project explorer");
            }

            if (selectedMapNode instanceof Project) {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this project?",
                        "Confirm delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (result == 0) {
                    ProjectExplorer parent = (ProjectExplorer) (selectedMapNode).getParents().iterator().next();
                    Project child = (Project) selectedMapNode;
                    parent.removeChild(child);
                    AppCore.getInstance().getFileSystem().deleteProject(child);
                }
            }


            if (selectedMapNode instanceof MindMap) {
                // TODO only works for single parent
                IMapNode node = ((MindMap) selectedMapNode).getParents().iterator().next();
                Project parent = (Project) node;
                parent.removeChild(selectedMapNode);
            }

            if (selectedMapNode instanceof Element) {
                // TODO only works for single parent
                IMapNode node = ((Element) selectedMapNode).getParents().iterator().next();
                MindMap parent = (MindMap) node;
                CommandManager cm = CommandManagerFactory.getCommandManager(parent);
                cm.addCommand(new RemoveElementCommand(parent, (Element) selectedMapNode));
            }
        }
    }

}
