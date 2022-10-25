package rs.edu.raf.dsw.rudok.app.core;

import rs.edu.raf.dsw.rudok.app.addon.IAddon;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * FileSystem component specification.
 */
public interface IFileSystem {

    /**
     * Saves the serializable object.
     *
     * @param relPath      Relative path where to save.
     * @param serializable Serializable object.
     */
    void save(String relPath, Serializable serializable);

    /**
     * Loads a serializable object from the given path. In case of error returns null.
     *
     * @param relPath Relative path to load the object from.
     * @return The serializable object or null if error.
     */
    Serializable load(String relPath);

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
