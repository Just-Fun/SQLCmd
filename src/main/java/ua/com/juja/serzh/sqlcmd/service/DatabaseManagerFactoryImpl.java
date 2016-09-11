package ua.com.juja.serzh.sqlcmd.service;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;

/**
 * Created by oleksandr.baglai on 13.11.2015.
 */
public class DatabaseManagerFactoryImpl implements DatabaseManagerFactory {

    private String className;

    @Override
    public DatabaseManager createDatabaseManager() {
        try {
            return (DatabaseManager)Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
