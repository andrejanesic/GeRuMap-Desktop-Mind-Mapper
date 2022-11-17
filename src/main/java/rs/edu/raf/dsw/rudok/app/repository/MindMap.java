package rs.edu.raf.dsw.rudok.app.repository;

import java.util.Iterator;
import java.util.Set;

public class MindMap extends IMapNodeComposite {

    private boolean template;

    public MindMap(boolean template, String nodeName) {
        super(nodeName);
        this.template = template;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    @Override
    public void setParents(Set<IMapNodeComposite> parents) {
        for (IMapNode parent : parents) {
            if (!(parent instanceof Project)) return;
        }
        super.setParents(parents);
    }

    @Override
    public void addParent(IMapNodeComposite parent) {
        if (parent instanceof Project) super.addParent(parent);
    }

    @Override
    public void setChildren(Set<IMapNode> children) {
        for (IMapNode child : children) {
            if (!(child instanceof Element)) return;
        }
        super.setChildren(children);
    }

    @Override
    public void addChild(IMapNode child) {
        if (child instanceof Element) super.addChild(child);
    }

    /**
     * Copies the children and settings from the given template.
     *
     * @param template {@link MindMap} template.
     */
    public void copyTemplate(MindMap template) {
        Iterator<IMapNode> iterator = template.getChildren().iterator();
        while (iterator.hasNext()) {
            Element e = (Element) iterator.next();
            Element eCopy = new Element(e.getNodeName());
            this.addChild(eCopy);
        }
    }
}
