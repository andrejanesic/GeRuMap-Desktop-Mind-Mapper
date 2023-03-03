package rs.edu.raf.dsw.rudok.app.gui.swing.command;

/**
 * Command interface for the command pattern.
 */
public abstract class AbstractCommand {

    /**
     * Determines whether the action has been committed, and if yes, prevents double redoing or undoing.
     */
    private boolean committed = false;

    public boolean isCommitted() {
        return committed;
    }

    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    /**
     * Executes the encapsulated command.
     */
    public abstract void doCommand();

    /**
     * Executes the encapsulated command from previously calculated results.
     */
    public abstract void redoCommand();

    /**
     * Reverses the encapsulated command.
     */
    public abstract void undoCommand();
}
