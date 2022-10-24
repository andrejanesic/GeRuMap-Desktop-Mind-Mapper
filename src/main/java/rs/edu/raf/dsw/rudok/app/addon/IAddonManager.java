package rs.edu.raf.dsw.rudok.app.addon;

import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Manages the loading and execution of add-ons.
 */
public abstract class IAddonManager {

    private ApplicationFramework applicationFramework;

    public IAddonManager(ApplicationFramework applicationFramework) {
        this.applicationFramework = applicationFramework;
    }

    protected ApplicationFramework getApplicationFramework() {
        return applicationFramework;
    }

    public Set<IAddon> getAddons() {
        return addons;
    }

    public void setAddons(Set<IAddon> addons) {
        this.addons = addons;
    }

    private Set<IAddon> addons = new HashSet<>();

    /**
     * Attempts to load add-ons into instance.
     */
    public abstract void loadAddons();

    /**
     * Initializes all add-ons.
     */
    public void initializeAddons() {
        Iterator<IAddon> iterator = addons.iterator();
        while (iterator.hasNext()) {
            try {
                iterator.next().initialize(applicationFramework);
            } catch (Exception e) {
                // TODO call error handler here
            }
        }
    }
}
