package ua.com.juja.serzh.sqlcmd.service;

import ua.com.juja.serzh.sqlcmd.model.databaseManager.DatabaseManager;

public interface DatabaseManagerFactory {
    DatabaseManager createDatabaseManager();
}
