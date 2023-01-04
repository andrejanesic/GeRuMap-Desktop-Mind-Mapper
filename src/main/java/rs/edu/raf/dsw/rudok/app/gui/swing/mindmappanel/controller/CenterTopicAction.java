package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Center-aligns all selected {@link Topic}s.
 */
public class CenterTopicAction extends IAction {

    private final MindMap mindMap;
    private final IMindMapPanel mindMapPanel;

    /**
     * Set of traversed {@link Topic}s.
     */
    private final Set<Topic> traversed = new HashSet<>();

    public CenterTopicAction(IMindMapPanel mindMapPanel) {
        super("/images/hierarchy.png", "Center-align topic");
        mindMap = mindMapPanel.getMindMap();
        this.mindMapPanel = mindMapPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // reset traversed
        traversed.clear();
        boolean found = false;
        int screenX = mindMapPanel.getDiagramController().getView().getCenterX();
        int screenY = mindMapPanel.getDiagramController().getView().getCenterY();

        Iterator<IMapNode> iterator = mindMap.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            if (child instanceof Connection) continue;
            Topic t = (Topic) child;
            if (!t.isSelected()) continue;
            found = true;
            align(t, new Position(screenX, screenY));
        }

        if (!found) {
            JOptionPane.showMessageDialog(
                    MainFrame.getInstance(),
                    "No topic selected",
                    "No topic selected",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Aligns the {@link Topic} t to x, y position and does so recursively for its children.
     *
     * @param t Topic to align.
     * @param p X and Y position.
     */
    private Position align(Topic t, Position p) {
        t.setX(p.x);
        t.setY(p.y);
        traversed.add(t);

        Iterator<Topic> iterator = t.getConnections().keySet().iterator();
        int maxY = 0;
        while (iterator.hasNext()) {
            Topic child = iterator.next();
            if (traversed.contains(child)) continue;
            traversed.add(child);
            Position temp = align(child, new Position(p.x + 200, p.y + maxY * 100));
            if (temp.y > p.y) {
                maxY = (temp.y - p.y) / 100;
            } else {
                maxY += 1;
            }
        }
        return new Position(p.x + 200, maxY);
    }

    /**
     * Helper class for returning an int tuple.
     */
    private static class Position {
        public final int x;
        public final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
