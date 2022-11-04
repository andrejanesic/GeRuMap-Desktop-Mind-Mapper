package rs.edu.raf.dsw.rudok.app.repository;

import java.util.Set;

/**
 * <h1>Project.</h1>
 * <p>Represents a group of mind maps.</p>
 * <p><b>NOTE:</b> the filepath only points to the name of the file database, not the fully-qualified pathname!</p>
 */
public class Project extends IMapNodeComposite {

    private String projectName;
    private String authorName;
    private String filepath;

    /**
     * Default constructor.
     *
     * @param nodeName Project name.
     * @param authorName  Project author.
     * @param filepath    Filepath to the project.
     */
    public Project(String nodeName, String authorName, String filepath) {
        super(nodeName);
        this.authorName = authorName;
        this.filepath = filepath;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void setParents(Set<IMapNodeComposite> parents) {
        for (IMapNode parent : parents) {
            if (!(parent instanceof ProjectExplorer)) return;
        }
        super.setParents(parents);
    }

    @Override
    public void addParent(IMapNodeComposite parent) {
        if (parent instanceof ProjectExplorer) super.addParent(parent);
    }

    @Override
    public void setChildren(Set<IMapNode> children) {
        for (IMapNode child : children) {
            if (!(child instanceof MindMap)) return;
        }
        super.setChildren(children);
    }

    @Override
    public void addChild(IMapNode child) {
        if (child instanceof MindMap) super.addChild(child);
    }
}
