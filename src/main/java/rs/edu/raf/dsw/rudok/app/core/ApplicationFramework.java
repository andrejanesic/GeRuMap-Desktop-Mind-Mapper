package rs.edu.raf.dsw.rudok.app.core;

import rs.edu.raf.dsw.rudok.app.confighandler.IConfigHandler;
import rs.edu.raf.dsw.rudok.app.constants.IConstants;
import rs.edu.raf.dsw.rudok.app.filesystem.IFileSystem;
import rs.edu.raf.dsw.rudok.app.gui.IGui;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

/**
 * The "glue" together between all components.
 */
public abstract class ApplicationFramework {

    protected IConstants iConstants;
    protected IConfigHandler iConfigHandler;
    protected IErrorHandler iErrorHandler;
    protected IGui iGui;
    protected ProjectExplorer projectExplorer;
    protected IFileSystem iFileSystem;

    /**
     * Initializes the app.
     *
     * @param iConstants      Environment/app constants.
     * @param iConfigHandler  Config handler implementation instance.
     * @param iErrorHandler   Error handler implementation instance.
     * @param iGui            Gui handler implementation instance.
     * @param projectExplorer Repository implementation instance.
     * @param iFileSystem     FileSystem implementation instance.
     */
    public void initialize(IConstants iConstants, IConfigHandler iConfigHandler, IErrorHandler iErrorHandler, IGui iGui, ProjectExplorer projectExplorer, IFileSystem iFileSystem) {
        this.iConstants = iConstants;
        this.iConfigHandler = iConfigHandler;
        this.iErrorHandler = iErrorHandler;
        this.iGui = iGui;
        this.projectExplorer = projectExplorer;
        this.iFileSystem = iFileSystem;
    }

    /**
     * Runs the application.
     */
    public void run() {
        this.iGui.run();
    }

    public IConstants getConstants() {
        return iConstants;
    }

    public IConfigHandler getConfigHandler() {
        return iConfigHandler;
    }

    public IErrorHandler getErrorHandler() {
        return iErrorHandler;
    }

    public IGui getGui() {
        return iGui;
    }

    public ProjectExplorer getProjectExplorer() {
        return projectExplorer;
    }

    public IFileSystem getFileSystem() {
        return iFileSystem;
    }
}
