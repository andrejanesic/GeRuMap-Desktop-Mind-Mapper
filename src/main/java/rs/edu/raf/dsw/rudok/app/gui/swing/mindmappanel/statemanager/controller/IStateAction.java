package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import rs.edu.raf.dsw.rudok.app.AppCore;

import javax.swing.*;
import java.net.URL;

public abstract class IStateAction extends AbstractAction {

    public IStateAction(String iconPath, String name) {
        putValue(SMALL_ICON, loadIcon(iconPath));
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, name);
    }

    private Icon loadIcon(String fileName) {
        URL imageURL = getClass().getResource(fileName);
        Icon icon = null;
        if (imageURL != null) {
            icon = new ImageIcon(imageURL);
        } else {
            AppCore.getInstance().getMessageGenerator().error("Resource not found: " + fileName);
        }
        return icon;
    }
}
