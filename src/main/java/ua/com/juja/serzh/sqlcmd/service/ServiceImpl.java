package ua.com.juja.serzh.sqlcmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.com.juja.serzh.sqlcmd.model.*;
import ua.com.juja.serzh.sqlcmd.model.entity.DatabaseConnection;
import ua.com.juja.serzh.sqlcmd.model.entity.Description;
import ua.com.juja.serzh.sqlcmd.model.entity.UserAction;

import java.util.*;

@Component
@Transactional
public abstract class ServiceImpl implements Service {

    protected abstract DatabaseManager getManager();

    @Autowired
    private UserActionRepository userActions;

    @Autowired
    private DatabaseConnectionRepository databaseConnections;

    @Override
    public List<String> commandsList() {
        return Arrays.asList("help", "list", "actions");
    }

    @Override
    public List<Description> commandsDescription() {
        return Arrays.asList(
                new Description("connect",
                        "To connect to a database, which will work."),
                new Description("list",
                        "For a list of current database tables."),
                new Description("find",
                        "For the contents of the selected table."),
                new Description("actions",
                        "To view a user's activity."),
                new Description("help",
                        "This page is a description of the application possibilities."));
    }

    @Override
    public DatabaseManager connect(String databaseName, String userName, String password) {
        DatabaseManager manager = getManager();
        manager.connect(databaseName, userName, password);
        userActions.createAction(databaseName, userName, "CONNECT");
        return manager;
    }

    @Override
    public List<List<String>> find(DatabaseManager manager, String tableName) {
        List<Map<String, Object>> tableData = manager.getTableData(tableName);
        List<String> columns = new LinkedList<>(manager.getTableColumns(tableName));

        List<List<String>> result = new LinkedList<>();
        result.add(columns);
        for (Map<String, Object> entry : tableData) {
            List<String> row = new ArrayList<>();
            for (String column : columns) {
                row.add(entry.get(column).toString());
            }
            result.add(row);
        }
        userActions.createAction(manager.getDatabaseName(), manager.getUserName(), "FIND(" + tableName +  ")");
        return result;
    }

    @Override
    public Set<String> tables(DatabaseManager manager) {
        userActions.createAction(manager.getDatabaseName(), manager.getUserName(), "TABLES");
        return manager.getTableNames();
    }

   /* @Override
    public List<UserAction> getAllFor(String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("User name cant be null!");
        }
        return userActions.findByUserName(userName);
    }*/

    @Override
    public List<UserAction> getAll() {
        List<UserAction> result = new LinkedList<>();
        for (UserAction action: userActions.findAll()) {
            result.add(new UserAction(action.getAction(), new DatabaseConnection("Name", "db")));
        }
        return result;
//        return userActions.findAll();
    }
}
