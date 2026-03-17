package com.bridgelabz.quantitymeasurement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads database and repository configuration from application.properties.
 */
public final class DatabaseConfig {

    private static final String DEFAULT_URL = "jdbc:h2:./data/quantity_measurements_db;AUTO_SERVER=TRUE";
    private static final String DEFAULT_USERNAME = "sa";
    private static final String DEFAULT_PASSWORD = "";
    private static final String DEFAULT_DRIVER = "org.h2.Driver";
    private static final String DEFAULT_REPOSITORY_TYPE = "db";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                PROPERTIES.load(inputStream);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load application.properties", e);
        }
    }

    private DatabaseConfig() {
    }

    public static String getJdbcUrl() {
        return getProperty("jdbc.url", DEFAULT_URL);
    }

    public static String getJdbcUsername() {
        return getProperty("jdbc.username", DEFAULT_USERNAME);
    }

    public static String getJdbcPassword() {
        return getProperty("jdbc.password", DEFAULT_PASSWORD);
    }

    public static String getJdbcDriver() {
        return getProperty("jdbc.driver", DEFAULT_DRIVER);
    }

    public static String getRepositoryType() {
        return getProperty("repository.type", DEFAULT_REPOSITORY_TYPE);
    }

    private static String getProperty(String key, String defaultValue) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.trim().isEmpty()) {
            return systemValue.trim();
        }
        String propertyValue = PROPERTIES.getProperty(key);
        return propertyValue != null && !propertyValue.trim().isEmpty()
                ? propertyValue.trim()
                : defaultValue;
    }
}
