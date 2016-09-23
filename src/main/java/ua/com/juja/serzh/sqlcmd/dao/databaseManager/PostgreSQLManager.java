package ua.com.juja.serzh.sqlcmd.dao.databaseManager;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@Component("PostgreSQLManager")
@Scope(value = "prototype")
public class PostgreSQLManager implements DatabaseManager {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new DriverException("Not installed PostgreSQL JDBC driver.", e);
        }
        loadProperties();
    }

    private static final String ERROR = "It is impossible because: ";
    private static final String PROPERTIES_FILE = "src/main/resources/config.properties";
    private static String host;
    private static String port;

    private Connection connection;
    private JdbcTemplate template;
    private String database;
    private String userName;
    private String password;

    private static void loadProperties() {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            property.load(fis);
            host = property.getProperty("host");
            port = property.getProperty("port");
        } catch (IOException e) {
            throw new RuntimeException("Properties do not loaded. " + e.getCause());
        }
    }

    @Override
    public void connect(String database, String userName, String password) {
        if (userName != null && password != null) {
            this.userName = userName;
            this.password = password;
        }
        this.database = database;
        closeOpenedConnection();
        getConnection();
    }

    private void getConnection() {
        try {
            String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
            connection = DriverManager.getConnection(url, userName, password);
            template = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
        } catch (SQLException e) {
            connection = null;
            template = null;
            throw new DatabaseManagerException(
                    String.format("Cant get connection for dao:%s user:%s", database, userName), e);
        }
    }

    private void closeOpenedConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                template = null; // ? TODO
            } catch (SQLException e) {
                throw new DatabaseManagerException(ERROR, e);
            }
        }
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName) {
        return template.query("SELECT * FROM public." + tableName,
                (rs, rowNum) -> {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    Map<String, Object> result = new LinkedHashMap<>();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        result.put(rsmd.getColumnName(i + 1), rs.getObject(i + 1));
                    }
                    return result;
                }
        );
    }

    @Override
    public int getSize(String tableName) {
        return template.queryForObject("SELECT COUNT(*) FROM public." + tableName, Integer.class);
    }

    @Override
    public Set<String> getTableNames() {
        return new LinkedHashSet<>(template.query("SELECT table_name FROM information_schema.tables " +
                        "WHERE table_schema='public' AND table_type='BASE TABLE'",
                (rs, rowNum) -> rs.getString("table_name")
        ));
    }

    @Override
    public Set<String> getDatabases() {
        return new LinkedHashSet<>(template.query("SELECT datname FROM pg_database WHERE datistemplate = false;",
                (rs, rowNum) -> rs.getString("datname")
        ));
    }

    @Override
    public void clear(String tableName) {
        template.execute("DELETE FROM public." + tableName);
    }
   /* @Override
    public void clear(String tableName) {
        template.update(String.format("DELETE FROM public.%s", tableName));
    }*/

    @Override
    public Set<String> getTableColumns(String tableName) {
        return new LinkedHashSet<>(template.query("SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '" + tableName + "'",
                (rs, rowNum) -> rs.getString("column_name")
        ));
    }


    @Override
    public void createDatabase(String database) {
        template.execute(String.format("CREATE DATABASE %s", database));
    }

    @Override
    public void createTable(String query) {
        template.execute(String.format("CREATE TABLE IF NOT EXISTS %s", query));
    }

    @Override
    public void dropDatabase(String database) {
        template.execute(String.format("DROP DATABASE IF EXISTS %s", database));
    }

    @Override
    public void dropTable(String table) {
        template.execute(String.format("DROP TABLE IF EXISTS %s", table));
    }


    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public String getDatabaseName() {
        return database;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void insert(String tableName, Map<String, Object> columnData) {
        StringJoiner tableNames = new StringJoiner(", ");
        StringJoiner values = new StringJoiner("', '", "'", "'");
        for (Map.Entry<String, Object> pair : columnData.entrySet()) {
            tableNames.add(pair.getKey());
            values.add(pair.getValue().toString());
        }
        template.update(String.format("INSERT INTO public.%s(%s) values (%s)",
                tableName, tableNames.toString(), values.toString()));
    }

    @Override
    public void update(String tableName, String keyName, String keyValue,
                       Map<String, Object> columnData) {
        for (Map.Entry<String, Object> pair : columnData.entrySet()) {
            template.update(String.format("UPDATE public.%s SET %s = '%s' " +
                            "WHERE %s = '%s'",
                    tableName, pair.getKey(),
                    pair.getValue(), keyName, keyValue));
        }
    }
}
