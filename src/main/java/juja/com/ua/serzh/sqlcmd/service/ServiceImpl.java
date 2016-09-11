package juja.com.ua.serzh.sqlcmd.service;

import juja.com.ua.serzh.sqlcmd.model.DatabaseManager;
import juja.com.ua.serzh.sqlcmd.model.JDBCDatabaseManager;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ServiceImpl implements Service {

    private DatabaseManager manager;

    public ServiceImpl() {
        manager = new JDBCDatabaseManager();
    }

    @Override
    public List<String> commandsList() {
        return Arrays.asList("help", "menu", "connect", "tables");
    }

    @Override
    public void connect(String databaseName, String userName, String password) {
        manager.connect(databaseName, userName, password);
    }

    @Override
    public Set<String> getTables() {
        return manager.getTableNames();
    }

}
