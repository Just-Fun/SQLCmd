package ua.com.juja.serzh.sqlcmd.model.databaseManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DatabaseManager {

    List<Map<String, Object>> getTableData(String tableName);

    int getSize(String tableName);

    Set<String> getTableNames();

    void connect(String database, String userName, String password);

    void clear(String tableName);

    Set<String> getTableColumns(String tableName);

    boolean isConnected();

    String getDatabaseName();

    String getUserName();
}
