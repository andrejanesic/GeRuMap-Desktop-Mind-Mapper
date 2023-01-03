package rs.edu.raf.dsw.rudok.app.gui.swing.command;

/**
 * Invoker of commands in the command pattern.
 */
public interface ICommandManager {

    /**
     * Adds the given {@link AbstractCommand} to the stack and executes it.
     *
     * @param command Command to execute.
     */
    void addCommand(AbstractCommand command);

    /**
     * Undo's the last committed command.
     */
    void undoCommand();

    /**
     * Redo's the last committed command.
     */
    void redoCommand();

    /**
     * Returns true if {@link ICommandManager#undoCommand()} can be called.
     *
     * @return Returns true if {@link ICommandManager#undoCommand()} can be called.
     */
    boolean canUndo();

    /**
     * Returns true if {@link ICommandManager#redoCommand()} ()} can be called.
     *
     * @return Returns true if {@link ICommandManager#redoCommand()} ()} can be called.
     */
    boolean canRedo();
}
