package ua.com.juja.serzh.sqlcmd.dao.databaseManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DatabaseManager {

    List<Map<String, Object>> getTableData(String tableName);

    Set<String> getTableNames();

    Set<String> getDatabases();

    void connect(String database, String userName, String password);

    void clear(String tableName);

    Set<String> getTableColumns(String tableName);

    void createDatabase(String database);

    void createTable(String query);

    void dropDatabase(String database);

    void dropTable(String table);

    boolean isConnected();

    String getDatabaseName();

    String getUserName();

    void insert(String tableName, Map<String, Object> input);

    void update(String tableName, String keyName, String keyValue,
                Map<String, Object> columnData);

    int getTableSize(String tableName);
}
