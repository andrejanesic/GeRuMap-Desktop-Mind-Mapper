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

    /**
     * Paints a (temporary) rectangle for displaying lasso-selection.
     *
     * @param x1 Origin x.
     * @param y1 Origin y.
     * @param x2 Endpoint x.
     * @param y2 Endpoint y.
     */
    public abstract void paintRectangle(int x1, int y1, int x2, int y2);

    /**
     * Paints a (temporary) line for displaying connections being drawn.
     *
     * @param x1 Origin x.
     * @param y1 Origin y.
     * @param x2 Endpoint x.
     * @param y2 Endpoint y.
     */
    public abstract void paintLine(int x1, int y1, int x2, int y2);

    /**
     * Clears all temporary lines/rectangles/other helpers being drawn.
     */
    public abstract void clearHelpers();

    public abstract DiagramFramework getFramework();
}
