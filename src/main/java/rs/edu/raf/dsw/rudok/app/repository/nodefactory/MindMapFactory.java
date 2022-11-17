package rs.edu.raf.dsw.rudok.app.repository.nodefactory;

import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;

public class MindMapFactory extends IMapNodeFactory{
    @Override
    public void createNode(IMapNode node) {
        Project project = (Project) node;
        MindMap mindMap = new MindMap(false,project.getNodeName());
        project.addChild(mindMap);
        mindMap.addParent(project);

    }
}
