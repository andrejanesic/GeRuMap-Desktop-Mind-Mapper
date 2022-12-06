package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IStateManager;

import java.awt.event.ActionEvent;

public class StartMoveTopicStateAction extends IStateAction {

    private final IStateManager stateManager;

    public StartMoveTopicStateAction(IStateManager stateManager) {
        super("/images/move.png", "Move");
        this.stateManager = stateManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO should it also calculate?
        stateManager.setMoveTopic();
    }
}
