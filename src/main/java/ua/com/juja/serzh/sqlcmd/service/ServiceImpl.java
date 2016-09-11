package ua.com.juja.serzh.sqlcmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.com.juja.serzh.sqlcmd.model.*;
import ua.com.juja.serzh.sqlcmd.model.entity.DatabaseConnection;
import ua.com.juja.serzh.sqlcmd.model.entity.Description;
import ua.com.juja.serzh.sqlcmd.model.entity.UserAction;

import java.util.*;

/**
 * Created by oleksandr.baglai on 30.10.2015.
 */
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
        return Arrays.asList("help", "list");
    }

    @Override
    public List<Description> commandsDescription() {
        return Arrays.asList(
                new Description("connect",
                        "Для подключения к базе данных, с которой будем " +
                                "работать. При старте приложения ты " +
                                "подключаешься, и в любой момент можешь " +
                                "переподключиться к другой базе."),
                new Description("list",
                        "Для получения списка таблиц текущей базы."),
                new Description("find",
                        "Для получения содержимого выбранной таблицы"),
                new Description("actions",
                        "Для просмотра активностей пользователя"),
                new Description("help",
                        "Эта страничка с описанием возможностей приложения"));
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
        List<List<String>> result = new LinkedList<>();

        List<String> columns = new LinkedList<>(manager.getTableColumns(tableName));
        List<DataSet> tableData = manager.getTableData(tableName);

        result.add(columns);
        for (DataSet dataSet : tableData) {
            List<String> row = new ArrayList<>(columns.size());
            result.add(row);
            for (String column : columns) {
                Object value = dataSet.get(column);
                if (value == null) {
                    throw new IllegalStateException(String.format(
                            "Ожидалось колонка с именем %s но ее нет, а есть %s",
                            column, dataSet));
                }
                row.add(value.toString());
            }
        }

        userActions.createAction(manager.getDatabaseName(), manager.getUserName(), "FIND(" + tableName +  ")");

        return result;
    }

    @Override
    public Set<String> tables(DatabaseManager manager) {
        userActions.createAction(manager.getDatabaseName(), manager.getUserName(), "TABLES");
        return manager.getTableNames();
    }

    @Override // TODO удалить это оно в демо целях
    public DatabaseConnection getDataFor(String userName, String dbName) {
        DatabaseConnection databaseConnection = databaseConnections.findByUserNameAndDbName(userName, dbName);
        databaseConnection.getUserActions().size();
//        databaseConnection.getUserActions().get(1).setAction("CHANGED");
//        databaseConnection.getUserActions().clear();
        return databaseConnection;
    }

    @Override
    public List<UserAction> getAllFor(String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("User name cant be null!");
        }

        return userActions.findByUserName(userName);
    }
}
