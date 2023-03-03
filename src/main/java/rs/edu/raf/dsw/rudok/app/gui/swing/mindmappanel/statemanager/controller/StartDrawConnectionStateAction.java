package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IStateManager;

import java.awt.event.ActionEvent;

public class StartDrawConnectionStateAction extends IStateAction {

    private final IStateManager stateManager;

    public StartDrawConnectionStateAction(IStateManager stateManager) {
        super("/images/link.png", "Connect");
        this.stateManager = stateManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO should it also calculate?
        stateManager.setDrawConnection();
    }
}
