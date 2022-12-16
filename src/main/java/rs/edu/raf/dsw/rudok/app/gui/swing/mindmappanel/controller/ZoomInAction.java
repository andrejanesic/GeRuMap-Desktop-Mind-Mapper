package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.controller;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.controller.IDiagramController;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.IDiagramView;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.IProjectPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class ZoomInAction extends IAction {


    public ZoomInAction() {
        super("/images/zoomin.png", "Zoom in");
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

        v.zoomIn(1.2);
    }
}
