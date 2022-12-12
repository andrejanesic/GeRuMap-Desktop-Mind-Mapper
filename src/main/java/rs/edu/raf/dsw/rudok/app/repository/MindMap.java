package rs.edu.raf.dsw.rudok.app.repository;

import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
        Map<Topic, Topic> copyMap = new HashMap<>();

        // First copy topics
        Iterator<IMapNode> iterator = template.getChildren().iterator();
        while (iterator.hasNext()) {
            Element e = (Element) iterator.next();
            ElementFactory eFactory = (ElementFactory) MapNodeFactoryUtils.getFactory(this);
            if (!(e instanceof Topic)) continue;
            Element eCopy = (Element) eFactory.createNode(ElementFactory.Type.Topic,
                    ((Topic) e).getX(),
                    ((Topic) e).getY(),
                    ((Topic) e).getWidth(),
                    ((Topic) e).getHeight()
            );
            copyMap.put((Topic) e, (Topic) eCopy);
        }

        // Then copy connections
        iterator = template.getChildren().iterator();
        while (iterator.hasNext()) {
            Element e = (Element) iterator.next();
            ElementFactory eFactory = (ElementFactory) MapNodeFactoryUtils.getFactory(this);
            if (!(e instanceof Connection)) continue;
            Element eCopy = (Element) eFactory.createNode(
                    copyMap.get(((Connection) e).getFrom()),
                    copyMap.get(((Connection) e).getTo())
            );
            eCopy.setNodeName(e.getNodeName());
            eCopy.setColor(e.getColor());
            eCopy.setStroke(e.getStroke());
        }
    }
}
