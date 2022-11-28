package rs.edu.raf.dsw.rudok.app.repository.nodefactory;

import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

/**
 * <h1>Element factory</h1>
 * Handles the creation of {@link Element} nodes with or without parent {@link MindMap}s.
 */
public class ElementFactory extends IMapNodeFactory {

    /**
     * For counting all children created thus far.
     */
    private static int CHILD_ID = 0;

    public ElementFactory(MindMap parent) {
        super(parent);
    }

    @Override
    public IMapNode createNode() {
        if (getParent() == null) {
            return new Element("New element " + ++CHILD_ID,0);
        }

        try {
            Element element = new Element("New element " + ++CHILD_ID,0);
            getParent().addChild(element);
            return element;
        } catch (ClassCastException e) {
            // TODO programmatic error, should never happen
            return null;
        }
    }
}
