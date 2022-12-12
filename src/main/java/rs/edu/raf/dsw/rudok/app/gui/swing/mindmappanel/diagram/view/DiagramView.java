package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view;

import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import java.awt.*;

public class DiagramView extends IDiagramView {

    /**
     * The painted {@link MindMap}.
     */
    private final MindMap mindMap;

    /**
     * Where the {@link MindMap} is painted.
     */
    private final DiagramFramework framework;

    public DiagramView(MindMap mindMap) {
        this.mindMap = mindMap;
        this.framework = new DiagramFramework(mindMap);
        framework.setCursor(new Cursor(Cursor.HAND_CURSOR));
        framework.setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(framework, BorderLayout.CENTER);
    }

    @Override
    public int getCenterX() {
        return framework.getWidth() / 2;
    }

    @Override
    public int getCenterY() {
        return framework.getHeight() / 2;
    }

    @Override
    public void paintLine(int x1, int y1, int x2, int y2) {
        framework.paintLine(x1, y1, x2, y2);
    }

    @Override
    public void clearLine() {
        framework.clearLine();
    }
}
