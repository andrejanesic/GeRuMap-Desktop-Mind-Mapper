package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;

public class ZoomOutAction extends IAction{


    public ZoomOutAction() {
        super("/images/palette.png", "Zoom out");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double scaling = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().getActiveMindMapPanel().getDiagramController().getView().getFramework().getScaling();
        scaling /= 1.2;
        if(scaling<0.4)
            scaling = 0.4;
        MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().getActiveMindMapPanel().getDiagramController().getView().getFramework().setScaling(scaling);
        MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().getActiveMindMapPanel().getDiagramController().getView().getFramework().repaint();

        AffineTransform affineTransform = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().getActiveMindMapPanel().getDiagramController().getView().getFramework().getAffineTransform();
        double translateX = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().getActiveMindMapPanel().getDiagramController().getView().getFramework().getTranslateX();
        double translateY = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().getActiveMindMapPanel().getDiagramController().getView().getFramework().getTranslateY();

        affineTransform.setToIdentity();
        affineTransform.translate(translateX, translateY);
        affineTransform.scale(scaling, scaling);
        MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel().getActiveMindMapPanel().getDiagramController().getView().getFramework().repaint();

    }
}
