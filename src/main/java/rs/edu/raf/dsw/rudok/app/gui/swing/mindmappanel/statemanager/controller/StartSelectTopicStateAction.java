package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IStateManager;

import java.awt.event.ActionEvent;

public class StartSelectTopicStateAction extends IStateAction {

    private final IStateManager stateManager;

    public StartSelectTopicStateAction(IStateManager stateManager) {
        super("/images/select.png", "Select");
        this.stateManager = stateManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO should it also calculate?
        stateManager.setSelectTopic();
    }
}
