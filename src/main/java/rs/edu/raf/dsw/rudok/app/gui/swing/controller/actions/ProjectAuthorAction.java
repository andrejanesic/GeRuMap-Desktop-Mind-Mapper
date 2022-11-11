package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.view.MapTreeView;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.ProjectAuthorDialog;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import java.awt.event.ActionEvent;

public class ProjectAuthorAction extends IAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        MapTreeView mapTreeView = MainFrame.getInstance().getMapTreeView();
        MapTreeItem item = (MapTreeItem) mapTreeView.getLastSelectedPathComponent();
        IMapNode node = item.getMapNode();
        if (node instanceof Project) {
            ProjectAuthorDialog projectAuthorDialog = new ProjectAuthorDialog(MainFrame.getInstance(), "Project author modification", true);
            projectAuthorDialog.setVisible(true);
        }
    }

}
