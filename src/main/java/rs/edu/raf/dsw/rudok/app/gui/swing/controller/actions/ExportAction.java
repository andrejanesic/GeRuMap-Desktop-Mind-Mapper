package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectexplorerpanel.IProjectExplorerPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.IProjectPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.RenderedImage;

public class ExportAction extends IAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        IProjectExplorerPanel projectExplorerPanel = MainFrame.getInstance().getProjectExplorerPanel();
        if (projectExplorerPanel == null) {
            AppCore.getInstance().getMessageGenerator().error("Project explorer panel not open");
            return;
        }

        IProjectPanel projectPanel = projectExplorerPanel.getProjectPanel();
        if (projectPanel == null) {
            AppCore.getInstance().getMessageGenerator().error("No project open");
            return;
        }

        IMindMapPanel mindMapPanel = projectPanel.getActiveMindMapPanel();
        if (mindMapPanel == null) {
            AppCore.getInstance().getMessageGenerator().error("No mind map open");
            return;
        }

        MindMap mindMap = mindMapPanel.getMindMap();
        RenderedImage image = mindMapPanel.getDiagramController().exportImage();
        if (AppCore.getInstance().getFileSystem().exportMindMap(mindMap, image)) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Mind map saved successfully to project path");
        }
    }
}
