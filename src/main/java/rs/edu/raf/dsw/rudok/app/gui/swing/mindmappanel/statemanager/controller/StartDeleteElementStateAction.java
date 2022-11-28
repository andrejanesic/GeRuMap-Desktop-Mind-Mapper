package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import java.awt.event.ActionEvent;

public class StartDeleteElementStateAction extends IStateAction {

    public StartDeleteElementStateAction(int shortcut, String iconPath, String name) {
        super(shortcut, iconPath, name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO call set state on MindMapPanel
        // TODO should it also calculate?
    }
}
