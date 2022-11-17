package rs.edu.raf.dsw.rudok.app.repository.nodefactory;

import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;

/**
 * <h1>Node factory</h1>
 * Creates a new node on the given parent. Parent should be set in the constructor.
 */
public abstract class IMapNodeFactory {

    private final IMapNodeComposite parent;

    public IMapNodeFactory(IMapNodeComposite parent) {
        this.parent = parent;
    }

    /**
     * Creates a new node on the given parent. If no parent, only creates the node and assigns to no parent.
     *
     * @return {@link IMapNode} new node on the given parent.
     */
    public abstract IMapNode createNode();

    /**
     * Parent that any new nodes will be created on/existing nodes edited/manipulated on.
     *
     * @return {@link IMapNodeComposite} parent.
     */
    public IMapNodeComposite getParent() {
        return parent;
    }
}
