package rs.edu.raf.dsw.rudok.app.gui.swing.command;

import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Assigns one {@link CommandManager} to each {@link MindMap}. This is done so as to avoid adding {@link CommandManager}
 * features to the {@link rs.edu.raf.dsw.rudok.app.repository} package, thus avoiding unnecessary coupling.
 */
public class CommandManagerFactory {

    /**
     * Mapping of {@link MindMap}s to their respective {@link CommandManager}.
     */
    private static final Map<MindMap, CommandManager> map = new HashMap<>();

    /**
     * Returns the {@link CommandManager} assigned to the passed {@link MindMap}, or creates a new one if not existing.
     *
     * @param mindMap {@link MindMap} to fetch the {@link CommandManager} for.
     * @return {@link CommandManager} instance.
     */
    public static CommandManager getCommandManager(MindMap mindMap) {
        CommandManager cm = map.getOrDefault(mindMap, null);
        if (cm == null) {
            cm = new CommandManager();
            map.put(mindMap, cm);
        }
        return cm;
    }
}
