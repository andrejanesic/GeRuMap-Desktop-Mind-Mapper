package rs.edu.raf.dsw.rudok.app.core;

import rs.edu.raf.dsw.rudok.app.addon.IAddon;
import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;

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
     *
     * @param config Serializable object.
     */
    void saveConfig(Map<String, String> config);

    /**
     * Loads a serializable object from the given path. In case of error returns null.
     *
     * @param name Relative path to load the object from.
     * @return The serializable object or null if error.
     */
    Map<String, String> loadConfig(String name);

    /**
     * Saves the project and its subtree.
     *
     * @param project Project to save.
     */
    void saveProject(IMapNodeComposite project);

    /**
     * Loads the project under "name".
     *
     * @param name Project name.
     * @return Returns project as an IMapNodeComposite if successful, null otherwise.
     */
    IMapNodeComposite loadProject(String name);

    /**
     * Loads the most recent project.
     *
     * @return Returns project as an IMapNodeComposite if successful, null otherwise.
     */
    default IMapNodeComposite loadProject() {
        return loadProject("default");
    }

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

    /**
     * Messages broadcasted by IFileSystem sub-classes.
     */
    final class Message extends IMessage<Message.Type, Object> {

        public enum Type {
            // When a config is saved
            CONFIG_SAVED,
            // When a config is loaded
            CONFIG_LOADED,
            // When a project is saved
            PROJECT_SAVED,
            // When a project is loaded
            PROJECT_LOADED,
            // When an addon is loaded
            ADDON_LOADED,
        }

        public Message(Type status, Object data) {
            super(status, data);
        }
    }
}
