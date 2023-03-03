package rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.actions;

import javax.swing.*;
import java.net.URL;

public abstract class ITreeAction extends AbstractAction {

    public Icon loadIcon(String fileName){

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
