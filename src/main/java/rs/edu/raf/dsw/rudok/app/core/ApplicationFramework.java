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
    protected ISerializer iSerializer;

    /**
     * Initializes the app.
     *
     * @param iConfigHandler Config handler implementation instance.
     * @param iErrorHandler  Error handler implementation instance.
     * @param iGui           Gui handler implementation instance.
     * @param iRepository    Repository implementation instance.
     * @param iSerializer    Serializer implementation instance.
     */
    public void initialize(IConfigHandler iConfigHandler, IErrorHandler iErrorHandler, IGui iGui, IRepository iRepository, ISerializer iSerializer) {
        this.iConfigHandler = iConfigHandler;
        this.iErrorHandler = iErrorHandler;
        this.iGui = iGui;
        this.iRepository = iRepository;
        this.iSerializer = iSerializer;
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

    public ISerializer getSerializer() {
        return iSerializer;
    }
}
