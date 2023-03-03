package rs.edu.raf.dsw.rudok.app.confighandler;

import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.observer.IMessageData;

/**
 * Config component specification.
 */
public interface IConfigHandler {

    /**
     * Loads the configuration file from the given URI. Returns true if successful, false otherwise.
     *
     * @param name Config name.
     * @return True if loaded, false otherwise.
     */
    boolean loadConfig(String name);

    /**
     * Loads the configuration file from the given URI. Returns true if successful, false otherwise.
     *
     * @return True if loaded, false otherwise.
     */
    default boolean loadConfig() {
        return loadConfig("default");
    }

    /**
     * Saves the current configuration.
     */
    void saveConfig();

    /**
     * Loads the default config.
     */
    void resetConfig();

    /**
     * Sets up a new configuration in program and in memory.
     *
     * @param name New configuration name.
     */
    void createConfig(String name);

    /**
     * Sets the configuration value for the given key.
     *
     * @param key Config key.
     * @param val Value to set.
     */
    void set(String key, String val);

    /**
     * Returns the configuration value for the given key.
     *
     * @param key Config key.
     * @return Value if set, otherwise null.
     */
    Object get(String key);

    /**
     * Returns the configuration value for the given key, or default value if key not set.
     *
     * @param key          Config key.
     * @param defaultValue Default value to return if config key not set.
     * @return Value if set, otherwise defaultValue.
     */
    Object get(String key, Object defaultValue);

    /**
     * Returns the configuration value for the given key, or default value if key not set.
     *
     * @param key          Config key.
     * @param defaultValue Default value to return if config key not set.
     * @return Value if set, otherwise defaultValue.
     */
    default Object getOrDefault(String key, Object defaultValue) {
        return this.get(key, defaultValue);
    }

    /**
     * Used for sending out updates by implementations of IConfigHandler.
     */
    class Message extends IMessage<Message.Type, IMessageData> {

        public Message(Type status, IMessageData data) {
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

        public static class ConfigMessageData extends IMessageData<IConfigHandler> {

            private final IConfigHandler configHandler;

            public ConfigMessageData(IConfigHandler configHandler) {
                super(configHandler);
                this.configHandler = configHandler;
            }

            public IConfigHandler getConfigHandler() {
                return configHandler;
            }
        }

        public static class ConfigChangeMessageData extends IMessageData<IConfigHandler> {

            private final String key;
            private final String value;

            public ConfigChangeMessageData(IConfigHandler configHandler, String key, String value) {
                super(configHandler);
                this.key = key;
                this.value = value;
            }

            public String getKey() {
                return key;
            }

            public String getValue() {
                return value;
            }
        }
    }
}
