package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.repository.Element;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Custom listener for interactions with the
 * {@link rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.IDiagramView}.
 */
public class InteractionListener implements MouseListener {

    private final IMindMapPanel mindMapPanel;

    public InteractionListener(IMindMapPanel mindMapPanel) {
        this.mindMapPanel = mindMapPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO check if element pressed; if not, get x, y coordinates
        int x = 0, y = 0;
        Element selected = null;
        mindMapPanel.mouseClickStateMigrate(selected, x, y);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO check if element pressed; if not, get x, y coordinates; this could be the start of a mouse drag!
        int x = 0, y = 0;
        Element selected = null;
        // mindMapPanel.getStateManager().getState().migrate(selected, x, y);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO check for collision with mouseClicked!
        // TODO check for second element "contained" by drag; if not, get x, y coordinates; this could be the end of a
        // mouse drag!
        int x = 0, y = 0;
        Element selected = null;
        // mindMapPanel.getStateManager().getState().migrate(selected, x, y);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
