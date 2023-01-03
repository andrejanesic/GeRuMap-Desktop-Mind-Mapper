package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.command.CommandManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.command.CommandManagerFactory;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectexplorerpanel.IProjectExplorerPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.IProjectPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import java.awt.event.ActionEvent;

public class UndoAction extends IAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        IProjectExplorerPanel pep = MainFrame.getInstance().getProjectExplorerPanel();
        if (pep == null) {
            AppCore.getInstance().getMessageGenerator().error("Project explorer panel not available");
            return;
        }

        IProjectPanel pp = pep.getProjectPanel();
        if (pp == null) {
            AppCore.getInstance().getMessageGenerator().error("No project open");
            return;
        }

        IMindMapPanel mp = pp.getActiveMindMapPanel();
        if (mp == null) {
            AppCore.getInstance().getMessageGenerator().error("No mind map open");
            return;
        }

        MindMap mindMap = mp.getMindMap();
        if (mindMap == null) {
            AppCore.getInstance().getMessageGenerator().error("Mind map not available");
            return;
        }

        CommandManager cm = CommandManagerFactory.getCommandManager(mindMap);
        if (!cm.canUndo()) {
            AppCore.getInstance().getMessageGenerator().error("No action to undo on this mind map!");
            return;
        }

        cm.undoCommand();
        AppCore.getInstance().getMessageGenerator().log("Action undone");
    }
}
