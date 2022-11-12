package rs.edu.raf.dsw.rudok.app.addon;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.observer.IMessageData;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Manages the loading and execution of add-ons.
 */
public abstract class IAddonManager extends IPublisher {

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

    public void addAddon(IAddon addon) {
        this.addons.add(addon);
    }

    public void removeAddon(IAddon addon) {
        this.addons.remove(addon);
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
                AppCore.getInstance().getMessageGenerator().error("Failed to initialize component");
            }
        }
    }

    public static class Message extends IMessage<Message.Type, IMessageData> {

        public Message(Type status, IMessageData data) {
            super(status, data);
        }

        public enum Type {
            // When an add-on is initialized
            ADDON_INITIALIZED,
        }

        public static class AddonInitializedMessageData extends IMessageData<IAddonManager> {

            private final IAddon addon;

            public AddonInitializedMessageData(IAddonManager manager, IAddon addon) {
                super(manager);
                this.addon = addon;
            }

            public IAddon getAddon() {
                return addon;
            }
        }
    }
}
