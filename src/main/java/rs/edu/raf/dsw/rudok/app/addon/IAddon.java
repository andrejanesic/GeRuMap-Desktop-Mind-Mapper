package rs.edu.raf.dsw.rudok.app.addon;

import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.gui.IGuiAddon;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;

/**
 * Interface for app add-ons.
 */
public abstract class IAddon extends IPublisher {

    /**
     * Initializes the add-on. Should be called when starting up the app.
     *
     * @param applicationFramework Application framework with initialized components (all but add-ons.)
     */
    public abstract void initialize(ApplicationFramework applicationFramework);

    /**
     * Add-on GUI.
     *
     * @return
     */
    public abstract IGuiAddon getGui();

    /**
     * Meta-info about the add-on.
     *
     * @return
     */
    public abstract IAddonMeta getMeta();
}
