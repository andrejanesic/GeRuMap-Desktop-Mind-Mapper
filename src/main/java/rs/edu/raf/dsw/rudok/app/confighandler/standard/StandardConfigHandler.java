package rs.edu.raf.dsw.rudok.app.confighandler.standard;

import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.core.IConfigHandler;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;

import java.util.HashMap;
import java.util.Map;

/**
 * Standard (default) implementation of the IConfigHandler component.
 */
public class StandardConfigHandler extends IPublisher implements IConfigHandler {

    /**
     * Default (fallback) config properties.
     */
    private static final HashMap<String, String> DEFAULT_CONFIG = new HashMap<String, String>() {{
        put("config", "default");
        put("language", "English");
    }};
    /**
     * App core reference.
     */
    private ApplicationFramework applicationFramework;
    /**
     * Holds all current config properties.
     */
    private HashMap<String, String> config = new HashMap<>();

    public StandardConfigHandler(ApplicationFramework applicationFramework) {
        this.applicationFramework = applicationFramework;
    }

    @Override
    public boolean loadConfig(String name) {
        Map<String, String> configRaw = this.applicationFramework.getFileSystem().loadConfig(name);
        if (configRaw == null) return false;

        try {
            config = (HashMap<String, String>) configRaw;

            this.publish(new IConfigHandler.Message(Message.Type.CONFIG_LOADED, new Message.ConfigMessageData(this)));
        } catch (Exception e) {
            // TODO call error handler component
            return false;
        }
        return true;
    }

    @Override
    public void resetConfig() {
        config = DEFAULT_CONFIG;

        this.publish(new IConfigHandler.Message(Message.Type.CONFIG_LOADED, new Message.ConfigMessageData(this)));
    }

    @Override
    public void saveConfig() {
        this.applicationFramework.getFileSystem().saveConfig(config);

        this.publish(new IConfigHandler.Message(Message.Type.CONFIG_SAVED, new Message.ConfigMessageData(this)));
    }

    @Override
    public void set(String key, String val) {
        config.put(key, val);

        this.publish(new IConfigHandler.Message(Message.Type.CONFIG_UPDATED,
                new Message.ConfigChangeMessageData(this, key, val)));
    }

    @Override
    public Object get(String key) {
        return config.getOrDefault(key, null);
    }

    @Override
    public Object get(String key, Object defaultValue) {
        String val = config.get(key);
        if (val == null) return defaultValue;
        return val;
    }
}
