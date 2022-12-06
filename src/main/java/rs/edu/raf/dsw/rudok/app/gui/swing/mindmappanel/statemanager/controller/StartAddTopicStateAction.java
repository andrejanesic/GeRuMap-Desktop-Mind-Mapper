package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import java.awt.event.ActionEvent;

public class StartAddTopicStateAction extends IStateAction {

    public StartAddTopicStateAction() {
        // TODO define icon path
        super("/images/plus.png", "Add");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO call set state on MindMapPanel
        // TODO should it also calculate?
    }
}
