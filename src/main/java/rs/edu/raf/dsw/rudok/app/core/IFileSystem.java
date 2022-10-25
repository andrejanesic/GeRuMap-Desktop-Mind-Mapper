package rs.edu.raf.dsw.rudok.app.core;

import rs.edu.raf.dsw.rudok.app.addon.IAddon;

import java.io.Serializable;

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
     * @param path Path to load the object from.
     * @return The serializable object or null if error.
     */
    Serializable load(String path);

    /**
     * Loads an add-on based on class name.
     *
     * @param classname Add-on class name.
     * @return Addon.
     */
    IAddon loadAddon(String classname);
}
