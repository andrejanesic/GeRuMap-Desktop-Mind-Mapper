package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view;

import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import javax.swing.*;
import java.awt.*;

public class DiagramView extends IDiagramView {

    /**
     * The painted {@link MindMap}.
     */
    private final MindMap mindMap;

    /**
     * Where the {@link MindMap} is painted.
     */
    private final JPanel framework;

    public DiagramView(MindMap mindMap) {
        this.mindMap = mindMap;
        this.framework = new DiagramFramework(mindMap);
        framework.setCursor(new Cursor(Cursor.HAND_CURSOR));
        framework.setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(framework, BorderLayout.CENTER);
    }
}
