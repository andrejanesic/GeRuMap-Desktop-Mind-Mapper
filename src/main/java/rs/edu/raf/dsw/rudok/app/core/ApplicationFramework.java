package rs.edu.raf.dsw.rudok.app.core;

/**
 * The "glue" together between all components.
 */
public abstract class ApplicationFramework {

    protected ConfigHandler configHandler;
    protected ErrorHandler errorHandler;
    protected Gui gui;
    protected Repository repository;
    protected Serializer serializer;

    /**
     * Initializes the app.
     *
     * @param configHandler Config handler implementation instance.
     * @param errorHandler  Error handler implementation instance.
     * @param gui           Gui handler implementation instance.
     * @param repository    Repository implementation instance.
     * @param serializer    Serializer implementation instance.
     */
    public void initialize(ConfigHandler configHandler, ErrorHandler errorHandler, Gui gui, Repository repository, Serializer serializer) {
        this.configHandler = configHandler;
        this.errorHandler = errorHandler;
        this.gui = gui;
        this.repository = repository;
        this.serializer = serializer;
    }
}
