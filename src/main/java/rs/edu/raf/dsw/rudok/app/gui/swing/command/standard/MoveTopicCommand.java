package rs.edu.raf.dsw.rudok.app.gui.swing.command.standard;

import rs.edu.raf.dsw.rudok.app.gui.swing.command.AbstractCommand;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

/**
 * This command moves the passed {@link Topic}s along the X/Y axis by the given amount. <b>NOTE:</b> this command
 * considers the movement to have been ALREADY completed. {@link MoveTopicCommand#doCommand()} method does nothing. This
 * command is simply a wrapper to record the history of {@link Topic}s movement and enable redoing/undoing.
 */
public class MoveTopicCommand extends AbstractCommand {

    /**
     * The change in movement.
     */
    private final int dX, dY;

    /**
     * Array of moved topics.
     */
    private final Topic[] targets;

    /**
     * Default constructor.
     *
     * @param dX      Change in position along the X-axis.
     * @param dY      Change in position along the Y-axis.
     * @param targets Moved topics.
     */
    public MoveTopicCommand(int dX, int dY, Topic... targets) {
        this.targets = targets;
        this.dX = dX;
        this.dY = dY;
        setCommitted(true);
    }

    @Override
    public void doCommand() {

    }

    @Override
    public void redoCommand() {
        if (isCommitted()) return;

        for (Topic t : targets) {
            t.setX(t.getX() + dX);
            t.setY(t.getY() + dY);
        }

        setCommitted(true);
    }

    @Override
    public void undoCommand() {
        if (!isCommitted()) {
            return;
        }

        for (Topic t : targets) {
            t.setX(t.getX() - dX);
            t.setY(t.getY() - dY);
        }

        setCommitted(false);
    }
}
