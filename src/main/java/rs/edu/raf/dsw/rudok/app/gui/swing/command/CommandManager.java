package rs.edu.raf.dsw.rudok.app.gui.swing.command;

import java.util.Stack;

public class CommandManager implements ICommandManager {

    /**
     * Stack of un-doable actions.
     */
    private final Stack<AbstractCommand> undoStack = new Stack<>();
    /**
     * Stack of re-doable actions.
     */
    private final Stack<AbstractCommand> redoStack = new Stack<>();

    /**
     * Protected constructor.
     */
    protected CommandManager() {

    }

    @Override
    public void addCommand(AbstractCommand command) {
        // clear entire redo stack
        redoStack.clear();
        command.doCommand();
        undoStack.push(command);
    }

    @Override
    public void redoCommand() {
        if (redoStack.empty()) return;
        AbstractCommand command = redoStack.pop();
        command.redoCommand();
        undoStack.push(command);
    }

    @Override
    public boolean canUndo() {
        return !undoStack.empty();
    }

    @Override
    public boolean canRedo() {
        return !redoStack.empty();
    }

    @Override
    public void undoCommand() {
        if (undoStack.empty()) return;
        AbstractCommand command = undoStack.pop();
        command.undoCommand();
        redoStack.push(command);
    }

}
