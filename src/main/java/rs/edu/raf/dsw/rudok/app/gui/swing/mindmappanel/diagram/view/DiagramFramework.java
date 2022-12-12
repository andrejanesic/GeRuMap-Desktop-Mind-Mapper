package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view;

import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainter;
import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainterFactory;
import rs.edu.raf.dsw.rudok.app.observer.IObserver;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

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
        Iterator<IMapNode> it = parent.getChildren().iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            ElementPainter ep = ElementPainterFactory.getPainter(e);
            ep.draw(g2d);
            ep.addObserver(observer);
        }
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
