package rs.edu.raf.dsw.rudok.app.core;

import rs.edu.raf.dsw.rudok.app.gui.IGui;

/**
 * The "glue" together between all components.
 */
public abstract class ApplicationFramework {

    protected IConstants iConstants;
    protected IConfigHandler iConfigHandler;
    protected IErrorHandler iErrorHandler;
    protected IGui iGui;
    protected IRepository iRepository;
    protected IFileSystem iFileSystem;

    /**
     * Initializes the app.
     *
     * @param iConstants     Environment/app constants.
     * @param iConfigHandler Config handler implementation instance.
     * @param iErrorHandler  Error handler implementation instance.
     * @param iGui           Gui handler implementation instance.
     * @param iRepository    Repository implementation instance.
     * @param iFileSystem    FileSystem implementation instance.
     */
    public void initialize(IConstants iConstants, IConfigHandler iConfigHandler, IErrorHandler iErrorHandler, IGui iGui, IRepository iRepository, IFileSystem iFileSystem) {
        this.iConstants = iConstants;
        this.iConfigHandler = iConfigHandler;
        this.iErrorHandler = iErrorHandler;
        this.iGui = iGui;
        this.iRepository = iRepository;
        this.iFileSystem = iFileSystem;
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

    public IRepository getRepository() {
        return iRepository;
    }

    public IFileSystem getFileSystem() {
        return iFileSystem;
    }
}
