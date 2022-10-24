package rs.edu.raf.dsw.rudok.app.core;

/**
 * Config component specification.
 */
public interface IConfigHandler {

    /**
     * Loads the configuration file from the given URI. Returns true if successful, false otherwise.
     *
     * @param relPath Config file relative path.
     */
    boolean loadConfig(String relPath);

    /**
     * Loads the default app configuration.
     */
    void resetConfig();

    /**
     * Saves the current configuration.
     */
    void saveConfig();

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
}
