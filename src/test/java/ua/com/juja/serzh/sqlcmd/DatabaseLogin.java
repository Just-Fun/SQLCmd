package ua.com.juja.serzh.sqlcmd;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseLogin {
    private final static String PROPERTIES_FILE = "src/test/resources/test-config.properties";

    private static String database;
    private static String user;
    private static String password;

    public DatabaseLogin() {
        loadProperties();
    }

    private static void loadProperties() {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            property.load(fis);
            database = property.getProperty("database");
            user = property.getProperty("user");
            password = property.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException("Properties do not loaded. " + e.getCause());
        }
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
