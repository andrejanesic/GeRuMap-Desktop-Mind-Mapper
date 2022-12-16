package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.controller;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.IDiagramView;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.IProjectPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class ZoomOutAction extends IAction {


    public ZoomOutAction() {
        super("/images/zoomout.png", "Zoom out");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IProjectPanel p = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel();
        if (p == null) {
            AppCore.getInstance().getMessageGenerator().error("No project open");
            return;
        }

        IMindMapPanel m = p.getActiveMindMapPanel();
        if (m == null) {
            AppCore.getInstance().getMessageGenerator().error("No mind map open");
            return;
        }

        IDiagramView v = m.getDiagramController().getView();
        if (v == null) {
            AppCore.getInstance().getMessageGenerator().error("No mind map diagram open");
            return;
        }

        v.zoomOut(1.2);
    }
}
