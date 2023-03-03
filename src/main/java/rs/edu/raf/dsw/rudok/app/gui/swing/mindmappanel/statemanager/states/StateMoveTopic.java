package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.command.CommandManagerFactory;
import rs.edu.raf.dsw.rudok.app.gui.swing.command.standard.MoveTopicCommand;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import java.awt.*;

/**
 * State for moving topics (if any selected) or translating the view (if no topic selected.)
 */
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

        // No topics selected, so translate view
        if (i == 0) {
            MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                    .getActiveMindMapPanel().getDiagramController().getView().translateView(
                            dX, dY);
            MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                    .getActiveMindMapPanel().getDiagramController().getView().clearHelpers();
            MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                    .getActiveMindMapPanel().getDiagramController().getView().repaint();
            MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                    .getActiveMindMapPanel().getDiagramController().getView().revalidate();
        }

        if (complete) {
            dX = x2 - origin.x;
            dY = y2 - origin.y;
            // Commit change if any
            if (i > 0) {
                CommandManagerFactory.getCommandManager(parent)
                        .addCommand(new MoveTopicCommand(dX, dY, changed));
            }
            origin = null;
        } else {
            tempMovement = new Point(x2, y2);
        }
    }
}
