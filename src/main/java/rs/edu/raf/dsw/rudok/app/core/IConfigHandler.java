package rs.edu.raf.dsw.rudok.app.core;

/**
 * Config component specification.
 */
public interface IConfigHandler {

    /**
     * Loads the configuration file from the given URI.
     *
     * @param iSerializer Serializer component.
     *                    TODO pitati da li se pass-uje u funkc ili konstruktor ili mora Core da se poziva
     * @param relPath Config file relative path.
     */
    void loadConfig(ISerializer iSerializer, String relPath);

    /**
     * Saves the current configuration.
     *
     * @param iSerializer Serializer component.
     */
    void saveConfig(ISerializer iSerializer);

    /**
     * Sets the configuration value for the given key.
     *
     * @param key Config key.
     * @param val Value to set.
     */
    void set(String key, Object val);

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
