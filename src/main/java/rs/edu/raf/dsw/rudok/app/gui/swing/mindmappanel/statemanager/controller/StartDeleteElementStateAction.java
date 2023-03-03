package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IStateManager;

import java.awt.event.ActionEvent;

public class StartDeleteElementStateAction extends IStateAction {

    private final IStateManager stateManager;

    public StartDeleteElementStateAction(IStateManager stateManager) {
        super("/images/eraser.png", "Delete");
        this.stateManager = stateManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO should it also calculate?
        stateManager.setDeleteElement();
    }
}
