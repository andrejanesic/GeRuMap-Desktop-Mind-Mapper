package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.mapview.view;

import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import javax.swing.*;

/**
 * Graphical representation of the selected {@link MindMap}.
 */
public class MapView extends JPanel {

    private final MindMap mindMap;

    public MapView(MindMap mindMap) {
        this.mindMap = mindMap;
    }
}
