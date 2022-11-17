package rs.edu.raf.dsw.rudok.app.repository.nodefactory;

import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

public class ElementFactory extends IMapNodeFactory{
    @Override
    public void createNode(IMapNode node) {
        MindMap mindMap = (MindMap) node;
        Element element = new Element(node.getNodeName());
        mindMap.addChild(element);
        element.addParent(mindMap);
    }
}
