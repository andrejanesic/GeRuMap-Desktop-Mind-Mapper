package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import java.awt.event.ActionEvent;

public class StartDrawConnectionStateAction extends IStateAction {

    public StartDrawConnectionStateAction() {
        // TODO define icon path
        super("/images/link.png", "Connect");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO call set state on MindMapPanel
        // TODO should it also calculate?
    }
}
