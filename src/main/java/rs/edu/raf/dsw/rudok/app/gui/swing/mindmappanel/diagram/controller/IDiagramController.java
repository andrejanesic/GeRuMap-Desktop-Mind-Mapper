package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.IDiagramView;

import java.awt.image.RenderedImage;

/**
 * Controller for {@link IDiagramView}.
 */
public interface IDiagramController {
    IDiagramView getView();

    /**
     * Creates a PNG binary render of the image.
     */
    RenderedImage exportImage();
}
