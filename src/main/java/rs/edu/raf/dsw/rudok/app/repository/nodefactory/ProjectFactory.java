package rs.edu.raf.dsw.rudok.app.repository.nodefactory;

import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

/**
 * <h1>Project factory</h1>
 * Handles the creation of {@link Project} nodes with or without parent {@link ProjectExplorer}s.
 */
public class ProjectFactory extends IMapNodeFactory {

    /**
     * For counting all children created thus far.
     */
    private static int CHILD_ID = 0;

    public ProjectFactory(ProjectExplorer parent) {
        super(parent);
    }

    @Override
    public IMapNode createNode(Object... params) {
        if (getParent() == null) {
            return new Project("New project " + ++CHILD_ID, "Unknown", null);
        }

        try {
            Project project = new Project("New project " + ++CHILD_ID, "Unknown", null);
            getParent().addChild(project);
            return project;
        } catch (ClassCastException e) {
            // TODO programmatic error, should never happen
            return null;
        }
    }
}
