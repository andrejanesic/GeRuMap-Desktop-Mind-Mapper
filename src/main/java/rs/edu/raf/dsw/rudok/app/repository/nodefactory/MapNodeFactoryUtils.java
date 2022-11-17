package rs.edu.raf.dsw.rudok.app.repository.nodefactory;

import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

public class MapNodeFactoryUtils extends IMapNodeFactoryUtils {

    public static IMapNodeFactory getFactory(IMapNodeComposite parent) {

        if (parent == null) return new ProjectExplorerFactory();

        if (parent instanceof ProjectExplorer) return new ProjectFactory((ProjectExplorer) parent);

        if (parent instanceof Project) return new MindMapFactory((Project) parent);

        if (parent instanceof MindMap) return new ElementFactory((MindMap) parent);

        return null;
    }
}
