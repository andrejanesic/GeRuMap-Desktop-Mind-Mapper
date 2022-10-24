package rs.edu.raf.dsw.rudok.app.addon.standard;

import rs.edu.raf.dsw.rudok.app.addon.IAddon;
import rs.edu.raf.dsw.rudok.app.addon.IAddonManager;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Default implementation of the Addon Manager. Loads add-ons into
 */
public class StandardAddonManager extends IAddonManager {

    public StandardAddonManager(ApplicationFramework applicationFramework) {
        super(applicationFramework);
    }

    @Override
    public void loadAddons() {
        String addons = (String) getApplicationFramework().getConfigHandler().get("addons");
        if (addons == null) return;

        File f = new File("./add-ons");
        String[] addonClassnames = addons.split(",");
        for (int i = 0; i < addonClassnames.length; i++) {
            String classname = addonClassnames[i];

            try {
                URL url = f.toURI().toURL();
                URL[] urls = new URL[]{url};

                ClassLoader cl = new URLClassLoader(urls);
                Class<?> cls = cl.loadClass(classname);
                IAddon addon = (IAddon) cls.getConstructor().newInstance();
                addAddon(addon);

                publish(new IAddonManager.Message(Message.Type.ADDON_INITIALIZED, addon));
            } catch (Exception e) {
                // TODO call error handler here
            }
        }
    }
}
