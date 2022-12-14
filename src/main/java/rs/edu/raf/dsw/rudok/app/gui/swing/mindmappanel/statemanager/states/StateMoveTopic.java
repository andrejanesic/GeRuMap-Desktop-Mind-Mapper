package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import java.awt.*;

public class StateMoveTopic extends IState {

    /**
     * For temporary movement (dragging support.)
     */
    private Point tempMovement = null;
    /**
     * Origin point of any movement.
     */
    private Point origin = null;

    @Override
    public void migrate(MindMap parent, Topic t, int x1, int y1, boolean complete) {
        this.migrate(parent, t.getX(), t.getY(), x1, y1, complete);
    }

    @Override
    public void migrate(MindMap parent, int x1, int y1, int x2, int y2, boolean complete) {
        if (!complete) {
            if (tempMovement == null) {
                tempMovement = new Point(x1, y1);
                origin = tempMovement;
            } else {
                x1 = tempMovement.x;
                y1 = tempMovement.y;
            }
        } else {
            if (tempMovement != null) {
                x1 = tempMovement.x;
                y1 = tempMovement.y;
                tempMovement = null;
            }
        }
        int dX = x2 - x1;
        int dY = y2 - y1;
        int deltaCount = 0;
        for (IMapNode child : parent.getChildren()) {
            if (!(child instanceof Topic)) continue;
            Topic t = (Topic) child;
            if (!t.isSelected()) continue;
            deltaCount++;
        }
        Topic[] changed = new Topic[deltaCount];
        int i = 0;
        for (IMapNode child : parent.getChildren()) {
            if (!(child instanceof Topic)) continue;
            Topic t = (Topic) child;
            if (!t.isSelected()) continue;
            t.setX(t.getX() + dX);
            t.setY(t.getY() + dY);
            changed[i++] = t;
        }

        if (complete) {
            dX = x2 - origin.x;
            dY = y2 - origin.y;
            super.commit(parent, changed, dX, dY);
            origin = null;
        } else {
            tempMovement = new Point(x2, y2);
        }
    }

    @Override
    public void rollback(Object... params) {
        int dX = (int) params[2];
        int dY = (int) params[3];
        for (Topic t : (Topic[]) params[1]) {
            t.setX(t.getX() - dX);
            t.setY(t.getX() - dY);
        }
    }
}
