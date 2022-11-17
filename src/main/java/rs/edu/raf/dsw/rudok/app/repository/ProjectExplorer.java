package rs.edu.raf.dsw.rudok.app.repository;


import java.util.Set;

/**
 * <h1>Project Explorer</h1>
 * The root of all {@link Project} nodes.
 */
public class ProjectExplorer extends IMapNodeComposite {

    public ProjectExplorer(String nodeName) {
        super(nodeName);
    }

    @Override
    public void setChildren(Set<IMapNode> children) {
        for (IMapNode child : children) {
            if (!(child instanceof Project)) return;
        }
        super.setChildren(children);
    }

    @Override
    public void addChild(IMapNode child) {
        if (child instanceof Project) super.addChild(child);
    }

}
