package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigManager is responsible for reading configuration
 * values (like URLs, timeouts, browser type, etc.) from the
 * config.properties file located in the resources folder.
 */
public class ConfigManager {

    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";
    private static Properties properties = new Properties();
    private static ConfigManager instance;

    // Private constructor to enforce Singleton pattern
    private ConfigManager() {
        loadProperties();
    }

    /**
     * Returns the singleton instance of ConfigManager.
     */
    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    /**
     * Loads configuration properties from the file.
     */
    private void loadProperties() {
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("❌ Failed to load configuration file: " + CONFIG_FILE_PATH);
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a property value by key.
     *
     * @param key Property key
     * @return Property value or null if not found
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Retrieves a property value with a default fallback.
     *
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or defaultValue
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // Common getters for frequently used configuration keys
    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }

    public String getBrowser() {
        return getProperty("browser", "chrome");
    }
}