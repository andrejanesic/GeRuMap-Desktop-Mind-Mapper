package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view;

import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainter;
import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainterFactory;
import rs.edu.raf.dsw.rudok.app.observer.IObserver;
import rs.edu.raf.dsw.rudok.app.repository.*;

import javax.swing.*;
import java.awt.*;
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
    private int lineX1 = 0, lineY1 = 0, lineX2 = 0, lineY2 = 0;

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

        if (lineX1 - lineX2 != 0 && lineY1 - lineY2 != 0) {
            g.drawLine(lineX1, lineY1, lineX2, lineY2);
        }
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
        lineX1 = x1;
        lineY1 = y1;
        lineX2 = x2;
        lineY2 = y2;
    }

    /**
     * Clears the (temporary) line for displaying connections being drawn.
     */
    protected void clearLine() {
        lineX1 = 0;
        lineY1 = 0;
        lineX2 = 0;
        lineY2 = 0;
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
