package rs.edu.raf.dsw.rudok.app.repository.nodefactory;

import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

/**
 * <h1>Project explorer factory</h1>
 * Handles the creation of the global {@link ProjectExplorer} instance.
 */
public class ProjectExplorerFactory extends IMapNodeFactory {

    private static final ProjectExplorer PROJECT_EXPLORER_GLOBAL = new ProjectExplorer("Workspace");

    public ProjectExplorerFactory() {
        super(null);
    }

    @Override
    public IMapNode createNode(Object... params) {
        return PROJECT_EXPLORER_GLOBAL;
    }
}
