package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IStateManager;

import java.awt.event.ActionEvent;

public class StartZoomStateAction extends IStateAction {

    private final IStateManager stateManager;

    public StartZoomStateAction(IStateManager stateManager) {
        super("/images/zoom.png", "Zoom");
        this.stateManager = stateManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO should it also calculate?
        stateManager.setZoom();
    }
}
