package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view;

import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainter;
import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainterFactory;
import rs.edu.raf.dsw.rudok.app.observer.IObserver;
import rs.edu.raf.dsw.rudok.app.repository.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

/**
 * <h1>DiagramFramework</h1>
 * The "canvas" on which the {@link MindMap} is painted.
 */
public class DiagramFramework extends JPanel {

    /**
     * The {@link MindMap} to be painted.
     */
    private final MindMap parent;
    private IObserver observer;
    /**
     * Helper graphic start and endpoint.
     */
    private int helperX1 = 0, helperY1 = 0, helperX2 = 0, helperY2 = 0;
    private HelperType helperType = null;

    public DiagramFramework(MindMap parent) {
        this.parent = parent;
        observer = new DiagramFrameworkObserver(this);
        parent.addObserver(observer);
        parent.getChildren().forEach(c -> c.addObserver(observer));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw connections
        Iterator<IMapNode> it = parent.getChildren().iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            if (e instanceof Topic) continue;
            ElementPainter ep = ElementPainterFactory.getPainter(e);
            ep.draw(g2d);
            ep.addObserver(observer);
        }

        // Then draw topics on top
        it = parent.getChildren().iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            if (e instanceof Connection) continue;
            ElementPainter ep = ElementPainterFactory.getPainter(e);
            ep.draw(g2d);
            ep.addObserver(observer);
        }

        if (helperType != null) {
            if (helperType == HelperType.LINE) {
                // draw stroke
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.BLACK);
                g2d.drawLine(helperX1, helperY1, helperX2, helperY2);
            } else {
                Shape r = new Rectangle2D.Float(
                        Math.min(helperX1, helperX2),
                        Math.min(helperY1, helperY2),
                        Math.abs(helperX2 - helperX1),
                        Math.abs(helperY2 - helperY1)
                );
                // draw fill
                g2d.setColor(new Color(0, 0, 0, 0.125f));
                g2d.fill(r);

                // draw stroke
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.BLACK);
                g2d.draw(r);
            }
        }
    }

    /**
     * Paints a (temporary) rectangle for displaying lasso-selection.
     *
     * @param x1 Origin x.
     * @param y1 Origin y.
     * @param x2 Endpoint x.
     * @param y2 Endpoint y.
     */
    protected void paintRectangle(int x1, int y1, int x2, int y2) {
        helperX1 = x1;
        helperY1 = y1;
        helperX2 = x2;
        helperY2 = y2;
        helperType = HelperType.RECTANGLE;
    }

    /**
     * Paints a (temporary) line for displaying connections being drawn.
     *
     * @param x1 Origin x.
     * @param y1 Origin y.
     * @param x2 Endpoint x.
     * @param y2 Endpoint y.
     */
    protected void paintLine(int x1, int y1, int x2, int y2) {
        helperX1 = x1;
        helperY1 = y1;
        helperX2 = x2;
        helperY2 = y2;
        helperType = HelperType.LINE;
    }

    /**
     * Clears all temporary lines/rectangles/other helpers being drawn.
     */
    protected void clearHelpers() {
        helperX1 = 0;
        helperY1 = 0;
        helperX2 = 0;
        helperY2 = 0;
        helperType = null;
    }

    /**
     * Types of helpers to draw.
     */
    private enum HelperType {
        LINE,
        RECTANGLE
    }

    /**
     * Observer sub-class for the {@link DiagramFramework}.
     */
    private class DiagramFrameworkObserver extends IObserver {

        private final DiagramFramework host;

        public DiagramFrameworkObserver(DiagramFramework host) {
            this.host = host;
        }

        @Override
        public void receive(Object message) {
            super.receive(message);

            if (message instanceof IMapNodeComposite.Message) {
                IMapNodeComposite.Message m = (IMapNodeComposite.Message) message;
                if (m.getStatus().equals(IMapNodeComposite.Message.Type.CHILD_ADDED)) {
                    ((IMapNodeComposite.Message.ChildChangeMessageData) m.getData()).getChild().addObserver(this);
                } else if (m.getStatus().equals(IMapNodeComposite.Message.Type.CHILD_REMOVED)) {
                    ((IMapNodeComposite.Message.ChildChangeMessageData) m.getData()).getChild().removeObserver(this);
                } else {
                    // TODO future
                }
            }

            if (message instanceof IMapNode.Message ||
                    message instanceof IMapNodeComposite.Message ||
                    (message instanceof ElementPainter.Message &&
                            (((ElementPainter.Message) message).getStatus().equals(
                                    ElementPainter.Message.Type.REPAINT_REQUEST)))
            ) {
                host.repaint();
                host.revalidate();
            }
        }
    }
}
