package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.DiagramView;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.IDiagramView;
import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainter;
import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainterFactory;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;

/**
 * Controller for {@link IDiagramView}.
 */
public class DiagramController implements IDiagramController {

    private final IMindMapPanel mindMapPanel;
    private final MindMap mindMap;
    private final IDiagramView view;
    private Point dragOrigin;

    public DiagramController(IMindMapPanel mindMapPanel) {
        this.mindMapPanel = mindMapPanel;
        mindMap = mindMapPanel.getMindMap();
        view = new DiagramView(mindMap);
        view.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                dragOrigin = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // If click
                if (e.getPoint().equals(dragOrigin)) {
                    ElementPainter ep = getElementAt(e.getPoint());
                    if (ep != null) {
                        mindMapPanel.mouseClickStateMigrate(ep.getElement(), e.getX(), e.getY());
                    } else {
                        mindMapPanel.mouseClickStateMigrate(null, e.getX(), e.getY());
                    }
                    return;
                }

                // If drag
                ElementPainter ep1 = getElementAt(dragOrigin);
                ElementPainter ep2 = getElementAt(e.getPoint());
                Element e1 = ep1 != null ? ep1.getElement() : null;
                Element e2 = ep2 != null ? ep2.getElement() : null;
                mindMapPanel.mouseDrawStateMigrate(e1, e2, dragOrigin.x, dragOrigin.y, e.getX(), e.getY());

                view.repaint();
                view.revalidate();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        view.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                ElementPainter ep1 = getElementAt(dragOrigin);
                ElementPainter ep2 = getElementAt(e.getPoint());
                Element e1 = ep1 != null ? ep1.getElement() : null;
                Element e2 = ep2 != null ? ep2.getElement() : null;
                mindMapPanel.mouseDrawStateMigrate(e1, e2, dragOrigin.x, dragOrigin.y, e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    @Override
    public IDiagramView getView() {
        return view;
    }

    /**
     * Returns the {@link ElementPainter} of the {@link Element} found at the given coordinates, null otherwise.
     *
     * @param p Coordinate of the mouse interaction.
     * @return {@link ElementPainter} of the {@link Element} found at the given coordinates, null otherwise.
     */
    private ElementPainter getElementAt(Point p) {
        Iterator<IMapNode> it = mindMapPanel.getMindMap().getChildren().iterator();
        while (it.hasNext()) {
            Element el = (Element) it.next();
            ElementPainter ep = ElementPainterFactory.getPainter(el);
            if (ep.elementAt(p)) {
                return ep;
            }
        }
        return null;
    }
}
