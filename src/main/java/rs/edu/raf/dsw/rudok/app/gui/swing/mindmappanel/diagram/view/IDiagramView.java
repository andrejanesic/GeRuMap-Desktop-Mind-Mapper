package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view;

import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import javax.swing.*;

/**
 * Paints the contents of {@link MindMap} into its pane.
 */
public abstract class IDiagramView extends JPanel {

    /**
     * X-coordinate of the center of the painting area.
     *
     * @return X-coordinate of the center of the painting area.
     */
    public abstract int getCenterX();

    /**
     * Y-coordinate of the center of the painting area.
     *
     * @return Y-coordinate of the center of the painting area.
     */
    public abstract int getCenterY();
}
