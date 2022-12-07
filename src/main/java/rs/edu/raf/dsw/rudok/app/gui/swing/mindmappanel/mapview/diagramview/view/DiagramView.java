package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.mapview.diagramview.view;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.mapview.view.IMapView;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import javax.swing.*;

public class DiagramView extends IDiagramView {

    private final MindMap mindMap;
    private final IMapView parent;
    private final JPanel canvas;

    public DiagramView(MindMap mindMap, IMapView parent) {
        this.mindMap = mindMap;
        this.parent = parent;
    }
}
