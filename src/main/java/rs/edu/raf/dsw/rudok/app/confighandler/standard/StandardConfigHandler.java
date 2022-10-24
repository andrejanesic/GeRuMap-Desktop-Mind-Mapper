package rs.edu.raf.dsw.rudok.app.confighandler.standard;

import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.core.IConfigHandler;
import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Standard (default) implementation of the IConfigHandler component.
 */
public class StandardConfigHandler extends IPublisher implements IConfigHandler {

    /**
     * App core reference.
     */
    private ApplicationFramework applicationFramework;

    public StandardConfigHandler(ApplicationFramework applicationFramework) {
        this.applicationFramework = applicationFramework;
    }

    /**
     * Holds all current config properties.
     */
    private HashMap<String, Object> config = new HashMap<>();

    /**
     * Default (fallback) config properties.
     */
    private static final HashMap<String, Object> DEFAULT_CONFIG = new HashMap<String, Object>() {{
        put("language", "English");
    }};

    @Override
    public boolean loadConfig(String relPath) {
        Serializable configRaw = this.applicationFramework.getSerializer().load(relPath);
        if (configRaw == null) return false;

        try {
            config = (HashMap<String, Object>) configRaw;

            this.publish(new Message(Message.Type.CONFIG_LOADED, this));
        } catch (Exception e) {
            // TODO call error handler component
            return false;
        }
        return true;
    }

    @Override
    public void resetConfig() {
        config = DEFAULT_CONFIG;

        this.publish(new Message(Message.Type.CONFIG_LOADED, this));
    }

    @Override
    public void saveConfig() {
        this.applicationFramework.getSerializer().save(config);

        this.publish(new Message(Message.Type.CONFIG_SAVED, this));
    }

    @Override
    public void set(String key, String val) {
        config.put(key, val);

        this.publish(new Message(Message.Type.CONFIG_UPDATED, this));
    }

    @Override
    public Object get(String key) {
        return config.getOrDefault(key, null);
    }

    @Override
    public Object get(String key, Object defaultValue) {
        return config.getOrDefault(key, defaultValue);
    }

    public static class Message extends IMessage<StandardConfigHandler.Message.Type, StandardConfigHandler> {

        public Message(Type status, StandardConfigHandler data) {
            super(status, data);
        }

        public enum Type {
            // When a (new) config is loaded
            CONFIG_LOADED,
            // When a key is updated
            CONFIG_UPDATED,
            // When the config is saved
            CONFIG_SAVED,
        }
    }
}
