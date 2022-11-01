package rs.edu.raf.dsw.rudok.app.repository;

import java.util.Set;

public class Element extends IMapNode {

    @Override
    public void setParents(Set<IMapNodeComposite> parents) {
        for (IMapNode parent : parents) {
            if (!(parent instanceof MindMap)) return;
        }
        super.setParents(parents);
    }

    @Override
    public void addParent(IMapNodeComposite parent) {
        if (parent instanceof MindMap) super.addParent(parent);
    }
}
