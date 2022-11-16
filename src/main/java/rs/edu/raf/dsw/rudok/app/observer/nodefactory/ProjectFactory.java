package rs.edu.raf.dsw.rudok.app.observer.nodefactory;

import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

public class ProjectFactory extends IMapNodeFactory{
    @Override
    public void createNode(IMapNode node) {
        ProjectExplorer projectExplorer = (ProjectExplorer) node;
        Project project = new Project(node.getNodeName(),"","");
        (projectExplorer).addChild(project);
        project.addParent(projectExplorer);

    }
}
