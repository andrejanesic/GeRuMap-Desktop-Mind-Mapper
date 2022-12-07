package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view;

import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainter;
import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainterFactory;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
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

    public DiagramFramework(MindMap parent) {
        this.parent = parent;
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
        }
    }
}
