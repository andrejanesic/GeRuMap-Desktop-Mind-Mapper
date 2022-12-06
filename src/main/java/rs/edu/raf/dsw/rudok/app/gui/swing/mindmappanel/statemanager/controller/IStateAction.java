package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import javax.swing.*;
import java.net.URL;

public abstract class IStateAction extends AbstractAction {

    public IStateAction(String iconPath, String name) {
        // TODO add back in
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
        //         shortcut, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon(iconPath));
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, name);
    }

    private Icon loadIcon(String fileName) {
        URL imageURL = getClass().getResource(fileName);
        Icon icon = null;
        if (imageURL != null) {
            icon = new ImageIcon(imageURL);
        }
        else {
            System.err.println("Resource not found: " + fileName);
        }
        return icon;
    }
}
