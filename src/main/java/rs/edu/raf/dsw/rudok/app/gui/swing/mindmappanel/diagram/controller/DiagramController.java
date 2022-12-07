package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.DiagramView;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.IDiagramView;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

/**
 * Controller for {@link IDiagramView}.
 */
public class DiagramController implements IDiagramController {

    private final IMindMapPanel mindMapPanel;
    private final MindMap mindMap;
    private final IDiagramView view;

    public DiagramController(IMindMapPanel mindMapPanel) {
        this.mindMapPanel = mindMapPanel;
        mindMap = mindMapPanel.getMindMap();
        view = new DiagramView(mindMap);
        view.addMouseListener(new InteractionListener(mindMapPanel));
    }

    @Override
    public IDiagramView getView() {
        return view;
    }
}
