package ua.com.juja.serzh.sqlcmd.service;


import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.InMemoryDatabaseManager;

import java.util.Arrays;
import java.util.List;

//@Component
public class DummyServiceImpl implements Service {

    @Override
    public List<String> commandsList() {
        return Arrays.asList("help", "menu", "connect");
    }

    @Override
    public DatabaseManager connect(String databaseName, String userName, String password) {
        return new InMemoryDatabaseManager();
    }

    @Override
    public List<List<String>> find(DatabaseManager manager, String tableName) {
        throw new UnsupportedOperationException();
    }
}
