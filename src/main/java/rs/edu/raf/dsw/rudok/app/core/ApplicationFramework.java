package rs.edu.raf.dsw.rudok.app.core;

/**
 * The "glue" together between all components.
 */
public abstract class ApplicationFramework {

    /**
     * GUI app component.
     */
    protected Gui gui;

    /**
     * Initializes the app.
     * @param gui
     */
    public void initialize(Gui gui) {
        this.gui = gui;
    }
}
