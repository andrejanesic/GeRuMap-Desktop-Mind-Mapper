package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public abstract class IStateAction extends AbstractAction {

    public IStateAction(int shortcut, String iconPath, String name) {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                shortcut, ActionEvent.CTRL_MASK));
        // putValue(SMALL_ICON, loadIcon("images/" + iconPath));
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, name);
    }
}
