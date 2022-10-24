package rs.edu.raf.dsw.rudok.app.core;

import rs.edu.raf.dsw.rudok.app.gui.IGui;

/**
 * The "glue" together between all components.
 */
public abstract class ApplicationFramework {

    protected IConfigHandler iConfigHandler;
    protected IErrorHandler iErrorHandler;
    protected IGui iGui;
    protected IRepository iRepository;
    protected IFileSystem iFileSystem;

    /**
     * Initializes the app.
     *
     * @param iConfigHandler Config handler implementation instance.
     * @param iErrorHandler  Error handler implementation instance.
     * @param iGui           Gui handler implementation instance.
     * @param iRepository    Repository implementation instance.
     * @param iFileSystem    Serializer implementation instance.
     */
    public void initialize(IConfigHandler iConfigHandler, IErrorHandler iErrorHandler, IGui iGui, IRepository iRepository, IFileSystem iFileSystem) {
        this.iConfigHandler = iConfigHandler;
        this.iErrorHandler = iErrorHandler;
        this.iGui = iGui;
        this.iRepository = iRepository;
        this.iFileSystem = iFileSystem;
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

    public IFileSystem getSerializer() {
        return iFileSystem;
    }
}
