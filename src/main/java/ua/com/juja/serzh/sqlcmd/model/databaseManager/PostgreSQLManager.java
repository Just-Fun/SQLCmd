package ua.com.juja.serzh.sqlcmd.model.databaseManager;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
                    String.format("Cant get connection for model:%s user:%s", database, userName), e);
        }
    }

    private void closeOpenedConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
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
    public Set<String> getDatabasesName() {
        return new LinkedHashSet<>(template.query("SELECT datname FROM pg_database WHERE datistemplate = false;",
                (rs, rowNum) -> rs.getString("datname")
        ));
    }

    @Override
    public void clear(String tableName) {
        template.execute("DELETE FROM public." + tableName);
    }
/*    @Override
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
}