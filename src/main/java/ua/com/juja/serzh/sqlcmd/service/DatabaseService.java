package ua.com.juja.serzh.sqlcmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.com.juja.serzh.sqlcmd.model.entity.UserActionLog;
import ua.com.juja.serzh.sqlcmd.model.databaseManager.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.entity.Description;
import ua.com.juja.serzh.sqlcmd.model.entity.UserAction;
import ua.com.juja.serzh.sqlcmd.model.repository.DatabaseConnectionRepository;
import ua.com.juja.serzh.sqlcmd.model.repository.UserActionRepository;

import java.util.*;

@Component
@Transactional //?
public abstract class DatabaseService implements Service {

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
                new Description("databases",
                        "For a list of current databases."),
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
    public List<List<String>> getTableData(DatabaseManager manager, String tableName) {
        List<Map<String, Object>> tableData = manager.getTableData(tableName);
        Set<String> tableColumns = manager.getTableColumns(tableName);
        List<String> columns = new LinkedList<>(tableColumns);

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

    @Override
    public Set<String> databases(DatabaseManager manager) {
        userActions.createAction(manager.getUserName(), manager.getDatabaseName(), "DATABASES");
        return manager.getDatabasesName();
    }

    @Override
    public List<UserActionLog> getAll() {
        List<UserActionLog> result = new LinkedList<>();
        Pageable topTen = new PageRequest(0, 10);
        Page<UserAction> actions = userActions.findAll(topTen);
        for (UserAction action: actions) {
            result.add(new UserActionLog(action));
        }
        return result;
    }
}
