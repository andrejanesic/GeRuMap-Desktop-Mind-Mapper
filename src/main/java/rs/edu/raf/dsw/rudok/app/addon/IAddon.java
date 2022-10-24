package rs.edu.raf.dsw.rudok.app.addon;

import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.gui.IGuiAddon;

/**
 * Interface for app add-ons.
 */
public interface IAddon {

    /**
     * Initializes the add-on. Should be called when starting up the app.
     *
     * @param applicationFramework Application framework with initialized components (all but add-ons.)
     */
    void initialize(ApplicationFramework applicationFramework);

    /**
     * Add-on GUI.
     *
     * @return
     */
    IGuiAddon getGui();
}
