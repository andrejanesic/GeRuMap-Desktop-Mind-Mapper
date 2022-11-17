package rs.edu.raf.dsw.rudok.app.repository.nodefactory;

import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;

/**
 * <h1>Node Factory Utils</h1>
 * Returns the factory for creating a child {@link rs.edu.raf.dsw.rudok.app.repository.IMapNode} on the given parent.
 */
public abstract class IMapNodeFactoryUtils {

    /**
     * Returns an {@link IMapNodeFactory} for creating children on the given parent.
     *
     * @param parent Parent node.
     * @return {@link IMapNodeFactory}.
     */
    public static IMapNodeFactory getFactory(IMapNodeComposite parent) {
        throw new RuntimeException("Method not implemented!");
    }
}
