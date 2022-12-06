package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IStateManager;

import java.awt.event.ActionEvent;

public class StartAddTopicStateAction extends IStateAction {

    private final IStateManager stateManager;

    public StartAddTopicStateAction(IStateManager stateManager) {
        // TODO define icon path
        super("/images/plus.png", "Add");
        this.stateManager = stateManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO call set state on MindMapPanel
        // TODO should it also calculate?
        stateManager.setAddTopic();
    }
}
