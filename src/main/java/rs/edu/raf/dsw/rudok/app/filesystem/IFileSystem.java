package rs.edu.raf.dsw.rudok.app.filesystem;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.addon.IAddon;
import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.observer.IMessageData;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * FileSystem component specification.
 */
public abstract class IFileSystem extends IPublisher {

    /**
     * Saves the config map.
     *
     * @param config Serializable object.
     */
    public abstract void saveConfig(Map<String, String> config);

    /**
     * Loads a serializable object from the given path. In case of error returns null.
     *
     * @param name Relative path to load the object from.
     * @return The serializable object or null if error.
     */
    public abstract Map<String, String> loadConfig(String name);

    /**
     * Saves the {@link Project} and its subtree.
     *
     * @param project {@link Project} to save.
     * @return True if successful, false otherwise.
     */
    public abstract boolean saveProject(Project project);

    /**
     * Loads the project from the given filepath.
     *
     * @param filepath Project filepath.
     * @return Returns project as an IMapNodeComposite if successful, null otherwise.
     */
    public abstract Project loadProject(String filepath);

    /**
     * Loads the most recent project.
     *
     * @return Returns project as an IMapNodeComposite if successful, null otherwise.
     */
    public Project loadProject() {
        return loadProject("default");
    }

    /**
     * Loads the {@link MindMap} from the given path.
     *
     * @param path Path to the {@link MindMap}.
     * @return {@link MindMap} if successful, null otherwise.
     */
    public abstract MindMap loadMindMapTemplate(String path);

    /**
     * Saves the {@link MindMap} and its subtree.
     *
     * @param mindMap {@link MindMap} to save.
     * @return True if successful, false otherwise.
     */
    public abstract boolean saveMindMapTemplate(MindMap mindMap);

    /**
     * Deletes the passed {@link Project} from file system.
     *
     * @return True if successfully deleted, false otherwise.
     */
    public abstract boolean deleteProject(Project p);

    /**
     * Exports a rendered mind map.
     *
     * @param mindMap       Mind map source.
     * @param renderedImage Mind map render.
     * @return True if successfully exported, false otherwise.
     */
    public abstract boolean exportMindMap(MindMap mindMap, RenderedImage renderedImage);

    /**
     * Loads an add-on based on class name.
     *
     * @param classname Add-on class name.
     * @return Addon.
     */
    public IAddon loadAddon(String classname) {
        File f = new File("./add-ons");
        try {
            URL url = f.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader cl = new URLClassLoader(urls);
            Class<?> cls = cl.loadClass(classname);
            return (IAddon) cls.getConstructor().newInstance();
        } catch (Exception e) {
            AppCore.getInstance().getMessageGenerator().error("Failed to load addon from " + classname);
        }
        return null;
    }

    /**
     * Logs a line to the logfile on the file system of implementation.
     *
     * @param line Message content.
     */
    public abstract void log(String line);

    /**
     * Messages broadcasted by IFileSystem sub-classes.
     */
    static final class Message extends IMessage<Message.Type, IMessageData> {

        public Message(Type status, IMessageData data) {
            super(status, data);
        }

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
    }
}
