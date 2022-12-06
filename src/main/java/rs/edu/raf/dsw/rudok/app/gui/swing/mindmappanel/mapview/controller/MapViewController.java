package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.mapview.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.mapview.view.MapView;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

/**
 * Controller for {@link MapView}.
 */
public class MapViewController implements IMapViewController {

    private final IMindMapPanel mindMapPanel;
    private final MindMap mindMap;
    private final MapView view;

    public MapViewController(IMindMapPanel mindMapPanel) {
        this.mindMapPanel = mindMapPanel;
        mindMap = mindMapPanel.getMindMap();
        view = new MapView(mindMap);
        view.addMouseListener(new InteractionListener(mindMapPanel));
    }

    @Override
    public MapView getView() {
        return view;
    }
}
