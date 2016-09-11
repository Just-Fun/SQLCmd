package ua.com.juja.serzh.sqlcmd.service;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;

import java.util.List;

public interface Service {

    List<String> commandsList();

    DatabaseManager connect(String databaseName, String userName, String password);

    List<List<String>> find(DatabaseManager manager, String tableName);
}
