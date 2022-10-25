package rs.edu.raf.dsw.rudok.app.core;

import rs.edu.raf.dsw.rudok.app.addon.IAddon;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * FileSystem component specification.
 */
public interface IFileSystem {

    /**
     * Saves the config map.
     *  @param path         Relative path where to save.
     * @param config Serializable object.
     */
    void saveConfig(String path, Map<String, String> config);

    /**
     * Loads a serializable object from the given path. In case of error returns null.
     *
     * @param path Relative path to load the object from.
     * @return The serializable object or null if error.
     */
    Map<String, String> loadConfig(String path);

    /**
     * Loads an add-on based on class name.
     *
     * @param classname Add-on class name.
     * @return Addon.
     */
    default IAddon loadAddon(String classname) {
        File f = new File("./add-ons");
        try {
            URL url = f.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader cl = new URLClassLoader(urls);
            Class<?> cls = cl.loadClass(classname);
            return (IAddon) cls.getConstructor().newInstance();
        } catch (Exception e) {
            // TODO call error handler here
        }
        return null;
    }
}
