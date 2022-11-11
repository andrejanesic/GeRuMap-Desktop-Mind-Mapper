package rs.edu.raf.dsw.rudok.app.core;

import rs.edu.raf.dsw.rudok.app.confighandler.IConfigHandler;
import rs.edu.raf.dsw.rudok.app.constants.IConstants;
import rs.edu.raf.dsw.rudok.app.filesystem.IFileSystem;
import rs.edu.raf.dsw.rudok.app.gui.IGui;
import rs.edu.raf.dsw.rudok.app.messagegenerator.IMessageGenerator;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

/**
 * The "glue" together between all components.
 */
public abstract class ApplicationFramework {

    protected IMessageGenerator iMessageGenerator;
    protected IConstants iConstants;
    protected IConfigHandler iConfigHandler;
    protected IGui iGui;
    protected ProjectExplorer projectExplorer;
    protected IFileSystem iFileSystem;

    /**
     * Initializes the app.
     *
     * @param iConstants        Environment/app constants.
     * @param iMessageGenerator Message generator implementation instance.
     * @param iConfigHandler    Config handler implementation instance.
     * @param iGui              Gui handler implementation instance.
     * @param projectExplorer   Repository implementation instance.
     * @param iFileSystem       FileSystem implementation instance.
     */
    public void initialize(IConstants iConstants, IMessageGenerator iMessageGenerator, IConfigHandler iConfigHandler, IGui iGui, ProjectExplorer projectExplorer, IFileSystem iFileSystem) {
        this.iMessageGenerator = iMessageGenerator;
        this.iConstants = iConstants;
        this.iConfigHandler = iConfigHandler;
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

    public IMessageGenerator getMessageGenerator() {
        return iMessageGenerator;
    }

    public IConfigHandler getConfigHandler() {
        return iConfigHandler;
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
