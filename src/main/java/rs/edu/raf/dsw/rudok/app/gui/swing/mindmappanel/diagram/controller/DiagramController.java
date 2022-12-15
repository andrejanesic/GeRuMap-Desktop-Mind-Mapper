package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.DiagramView;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.IDiagramView;
import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainter;
import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainterFactory;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

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
    private Point originPoint;
    private Topic originTopic;

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
                originPoint = e.getPoint();
                ElementPainter ep = getElementAt(e.getPoint());
                if (ep == null) {
                    originTopic = null;
                } else {
                    if (ep.getElement() instanceof Topic) {
                        originTopic = (Topic) ep.getElement();
                    } else {
                        originTopic = null;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // If click
                if (e.getPoint().equals(originPoint)) {
                    ElementPainter ep = getElementAt(e.getPoint());
                    if (ep != null && ep.getElement() instanceof Topic) {
                        mindMapPanel.mouseClickStateMigrate((Topic) ep.getElement());
                    } else {
                        mindMapPanel.mouseClickStateMigrate(e.getX(), e.getY());
                    }
                    return;
                }

                // If drag
                ElementPainter ep2 = getElementAt(e.getPoint());
                Element e2 = ep2 != null ? ep2.getElement() : null;
                if (originTopic != null) {
                    if (e2 instanceof Topic && originTopic != e2) {
                        mindMapPanel.mouseDrawStateMigrate(originTopic, (Topic) e2);
                    } else {
                        mindMapPanel.mouseDrawStateMigrate(originTopic, e.getX(), e.getY(), true);
                    }
                } else {
                    if (originPoint != null) {
                        mindMapPanel.mouseDrawStateMigrate(originPoint.x, originPoint.y, e.getX(), e.getY(), true);
                    } else {
                        ; // TODO anything here?
                    }
                }
                view.clearHelpers();
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
                ElementPainter ep2 = getElementAt(e.getPoint());
                Element e2 = ep2 != null ? ep2.getElement() : null;
                if (originTopic != null) {
                    if (e2 instanceof Topic && originTopic != e2) {
                        mindMapPanel.mouseDrawStateMigrate(originTopic, (Topic) e2);
                    } else {
                        mindMapPanel.mouseDrawStateMigrate(originTopic, e.getX(), e.getY(), false);
                    }
                } else {
                    if (originPoint != null) {
                        mindMapPanel.mouseDrawStateMigrate(originPoint.x, originPoint.y, e.getX(), e.getY(), false);
                    } else {
                        ; // TODO anything here?
                    }
                }
                view.repaint();
                view.revalidate();
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
