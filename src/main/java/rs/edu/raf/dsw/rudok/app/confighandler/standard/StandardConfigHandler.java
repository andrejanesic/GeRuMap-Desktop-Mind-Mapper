package rs.edu.raf.dsw.rudok.app.confighandler.standard;

import rs.edu.raf.dsw.rudok.app.core.IConfigHandler;
import rs.edu.raf.dsw.rudok.app.core.ISerializer;
import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Standard (default) implementation of the IConfigHandler component.
 */
public class StandardConfigHandler extends IPublisher implements IConfigHandler {

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
    public void loadConfig(ISerializer iSerializer, String relPath) {
        Serializable configRaw = iSerializer.load(relPath);
        if (configRaw == null) {
            config = DEFAULT_CONFIG;
            return;
        }

        try {
            config = (HashMap<String, Object>) configRaw;
        } catch (Exception e) {
            // TODO call error handler component
        }
    }

    @Override
    public void saveConfig(ISerializer iSerializer) {
        iSerializer.save(config);
    }

    @Override
    public void set(String key, Object val) {
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
