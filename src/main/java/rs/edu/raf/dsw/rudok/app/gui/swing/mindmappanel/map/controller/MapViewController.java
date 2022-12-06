package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.map.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.map.view.MapView;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

/**
 * Controller for {@link MapView}.
 */
public class MapViewController {

    private final IMindMapPanel mindMapPanel;
    private final MindMap mindMap;
    private final MapView view;

    public MapViewController(IMindMapPanel mindMapPanel) {
        this.mindMapPanel = mindMapPanel;
        mindMap = mindMapPanel.getMindMap();
        view = new MapView(mindMap);
    }
}
