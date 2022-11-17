package rs.edu.raf.dsw.rudok.app.repository.nodefactory;

import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;

public class NodeFactoryUtils {
    public static void createNode(IMapNode node){
        IMapNodeFactory iMapNodeFactory;
        if(node instanceof Project){
            iMapNodeFactory = new ProjectFactory();
            iMapNodeFactory.createNode(node);
        }else if(node instanceof MindMap){
            iMapNodeFactory = new MindMapFactory();
            iMapNodeFactory.createNode(node);
        }else if(node instanceof Element){
            iMapNodeFactory = new ElementFactory();
            iMapNodeFactory.createNode(node);
        }
    }

}
