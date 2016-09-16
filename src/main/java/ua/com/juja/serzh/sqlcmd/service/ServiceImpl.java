package ua.com.juja.serzh.sqlcmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.juja.serzh.sqlcmd.controller.UserActionLog;
import ua.com.juja.serzh.sqlcmd.model.*;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.entity.DatabaseConnection;
import ua.com.juja.serzh.sqlcmd.model.entity.Description;
import ua.com.juja.serzh.sqlcmd.model.entity.UserAction;

import java.util.*;

@Component
//@Transactional //?
public abstract class ServiceImpl implements Service {

    protected abstract DatabaseManager getManager();

    @Autowired
    private UserActionRepository userActions;

    @Autowired
    private DatabaseConnectionRepository databaseConnections;

    @Override
    public List<Description> commandsDescription() {
        return Arrays.asList(
                new Description("connect",
                        "To connect to a database, which will work."),
                new Description("tables",
                        "For a list of current database tables."),
                new Description("actions",
                        "To view a user's activity."));
    }

    @Override
    public DatabaseManager connect(String databaseName, String userName, String password) {
        DatabaseManager manager = getManager();
        manager.connect(databaseName, userName, password);
        userActions.createAction(userName, databaseName, "CONNECT");
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
        userActions.createAction(manager.getUserName(), manager.getDatabaseName(), "FIND(" + tableName +  ")");
        return result;
    }

    @Override
    public Set<String> tables(DatabaseManager manager) {
        userActions.createAction(manager.getUserName(), manager.getDatabaseName(), "TABLES");
        return manager.getTableNames();
    }

   /* @Override
    public List<UserAction> getAllFor(String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("User name cant be null!");
        }
        return userActions.findByUserName(userName);
    }*/

    /*@Override
    public List<UserAction> getAll() {
        List<UserAction> result = new LinkedList<>();
        for (UserAction action: userActions.findAll()) {
//            result.add(new UserAction(action.getAction(), action.getConnection()));
//            result.add(new UserAction(action.getAction(), new DatabaseConnection("Name", "db")));
            result.add(new UserAction(action.getAction(),
                    new DatabaseConnection(action.getConnection().getUserName(), action.getConnection().getDbName())));
        }
        return result;
//        return userActions.findAll();
    }*/

    @Override
    public List<UserActionLog> getAll() {
        List<UserActionLog> result = new LinkedList<>();
        for (UserAction action: userActions.findAll()) {
            result.add(new UserActionLog(action));
        }
        return result;
    }
}
