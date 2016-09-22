package ua.com.juja.serzh.sqlcmd.service;

import ua.com.juja.serzh.sqlcmd.model.entity.UserActionLog;
import ua.com.juja.serzh.sqlcmd.model.databaseManager.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.entity.DatabaseConnection;
import ua.com.juja.serzh.sqlcmd.model.entity.Description;
import ua.com.juja.serzh.sqlcmd.model.entity.UserAction;

import java.util.List;
import java.util.Set;

public interface Service {

    List<Description> commandsDescription();

    DatabaseManager connect(String databaseName, String userName, String password);

    List<List<String>> getTableData(DatabaseManager manager, String tableName);

    Set<String> tables(DatabaseManager manager);

    DatabaseConnection getDataFor(String userName, String dbName);

    List<UserAction> getAllFor(String userName);

    Set<String> databases(DatabaseManager manager);

    List<UserActionLog> getAll();

}
