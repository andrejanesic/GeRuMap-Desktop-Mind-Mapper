package rs.edu.raf.dsw.rudok.app.repository.nodefactory;

import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;

/**
 * <h1>Mind map factory</h1>
 * Handles the creation of {@link MindMap} nodes with or without parent {@link Project}s.
 */
public class MindMapFactory extends IMapNodeFactory {

    /**
     * For counting all children created thus far.
     */
    private static int CHILD_ID = 0;

    public MindMapFactory(Project parent) {
        super(parent);
    }

    @Override
    public IMapNode createNode() {
        if (getParent() == null) {
            return new MindMap(false, "New mind map " + ++CHILD_ID);
        }

        try {
            MindMap mindMap = new MindMap(false, "New mind map " + ++CHILD_ID);
            getParent().addChild(mindMap);
            return mindMap;
        } catch (ClassCastException e) {
            // TODO programmatic error, should never happen
            return null;
        }
    }
}
