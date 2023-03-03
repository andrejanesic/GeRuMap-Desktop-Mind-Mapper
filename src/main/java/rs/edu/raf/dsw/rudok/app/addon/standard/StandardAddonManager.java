package rs.edu.raf.dsw.rudok.app.addon.standard;

import rs.edu.raf.dsw.rudok.app.addon.IAddon;
import rs.edu.raf.dsw.rudok.app.addon.IAddonManager;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;

/**
 * Default implementation of the Addon Manager. Loads {@link IAddon} into the app.
 */
public class StandardAddonManager extends IAddonManager {

    public StandardAddonManager(ApplicationFramework applicationFramework) {
        super(applicationFramework);
    }

    @Override
    public void loadAddons() {
        String addons = (String) getApplicationFramework().getConfigHandler().get("addons");
        if (addons == null) return;

        String[] addonClassnames = addons.split(",");
        for (int i = 0; i < addonClassnames.length; i++) {
            String classname = addonClassnames[i];

            IAddon addon = getApplicationFramework().getFileSystem().loadAddon(classname);
            addAddon(addon);

            publish(new IAddonManager.Message(Message.Type.ADDON_INITIALIZED,
                    new Message.AddonInitializedMessageData(this, addon)));
        }
    }
}
