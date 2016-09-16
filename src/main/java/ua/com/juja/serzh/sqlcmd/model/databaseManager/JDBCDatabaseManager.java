package ua.com.juja.serzh.sqlcmd.model.databaseManager;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;
import ua.com.juja.serzh.sqlcmd.model.repository.DriverException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@Component/*("JDBCDatabaseManager")*/ //() ?
//@Scope(value = "prototype") // ?
public class JDBCDatabaseManager implements DatabaseManager {

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

    private Connection connection;
    private JdbcTemplate template;
    private String database;
    private String userName;
    private String password;


    @Override
    public void connect(String database, String userName, String password) {
        if (userName != null && password != null) {
            this.userName = userName;
            this.password = password;
        }
        this.database = database;

        closeOpenedConnection();
        getConnection();
       /* try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, userName,
                    password);
            this.database = database;
            this.userName = userName;
            template = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
        } catch (SQLException e) {
            connection = null;
            template = null;
            throw new RuntimeException(
                    String.format("Cant get connection for model:%s user:%s", database, userName), e);
        }*/
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
                new RowMapper<Map<String, Object>>() {
                    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ResultSetMetaData rsmd = rs.getMetaData();
                        Map<String, Object> result = new LinkedHashMap<>();
                        for (int i = 0; i < rsmd.getColumnCount(); i++) {
                            result.put(rsmd.getColumnName(i + 1), rs.getObject(i + 1));
                        }
                        return result;
                    }
                }
        );
    }

    @Override
    public int getSize(String tableName) {
        return template.queryForObject("SELECT COUNT(*) FROM public." + tableName, Integer.class);
    }

    @Override
    public Set<String> getTableNames() {
        return new LinkedHashSet<>(template.query("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'",
                new RowMapper<String>() {
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("table_name");
                    }
                }
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
                new RowMapper<String>() {
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("column_name");
                    }
                }
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