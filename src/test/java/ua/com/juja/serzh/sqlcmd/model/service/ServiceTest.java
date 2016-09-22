package ua.com.juja.serzh.sqlcmd.model.service;

import org.hibernate.service.spi.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.juja.serzh.sqlcmd.service.Service;
import ua.com.juja.serzh.sqlcmd.model.databaseManager.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.databaseManager.DatabaseManagerException;
import ua.com.juja.serzh.sqlcmd.model.databaseManager.PostgreSQLManager;
import ua.com.juja.serzh.sqlcmd.DatabaseLogin;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = ("classpath:test-application-context.xml"))
@ContextConfiguration(locations = ("file:src/main/webapp/WEB-INF/application-context.xml"))
public class ServiceTest {

    @Autowired
    private Service service;

    DatabaseManager manager = new PostgreSQLManager();
    DatabaseLogin login = new DatabaseLogin();

    @Test(expected = DatabaseManagerException.class)
//    @Test(expected = NoSuchBeanDefinitionException.class)
    public void testConnect_WithIncorrectData() throws ServiceException {
        service.connect("qwe", "qwe", "qwe");
    }

    @Test
    public void testConnect() throws ServiceException {
        String database = login.getDatabase();
        service.connect(database, login.getUser(), login.getPassword());
//        service.connect("postgres", "postgres", "postgres");
        assertNotNull(manager);
    }

    /*

    DatabaseManager mockManager = mock(DatabaseManager.class);

    @Test
    public void testCommandList() {
        assertEquals("[connect, " +
                      "create-table, " +
                      "tables, " +
                      "create-database, " +
                      "delete-database]", service.commandList().toString());
    }

    @Test
    public void testAllFor() throws Exception {
        manager.connect("sqlcmd_log", "postgres", "postgres");
        manager.clearTable("user_actions");
//        DatabaseManager mockManager = mock(DatabaseManager.class);
        when(mockManager.getUser()).thenReturn("postgres");

        service.createBase(mockManager, "mockDatabase");
        service.dropBase(mockManager, "mockDatabase");
        when(mockManager.getUser()).thenReturn("other");
        service.dropTable(mockManager, "mockTableName");
        List<UserAction> userActions = service.getAllFor("postgres");

        List<String> actions = new LinkedList<>();
        for (int index = 0; index < userActions.size(); index++) {
            actions.add(index, userActions.get(index).getUserAction());
        }
        assertEquals("[CREATE DATABASE ( mockDatabase ), " +
                      "DELETE DATABASE ( mockDatabase )]", actions.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAllFor_WithNullName() throws ServiceException {
        service.getAllFor(null);
    }

    @Test
    public void testLogger() throws Exception {
        manager.connect("sqlcmd_log", "postgres", "postgres");
        manager.clearTable("user_actions");
        manager.clearTable("database_connection");
        DatabaseManager mockManager = mock(DatabaseManager.class);
        when(mockManager.getDatabase()).thenReturn("sqlcmd");
        when(mockManager.getUser()).thenReturn("postgres");

        service.connect(login.getDatabase(), login.getUser(), login.getPassword());
        service.clearTable(mockManager, "mockTable");
        service.getTableNames(mockManager);
        service.getTableData(mockManager, "mockTable");
        service.createBase(mockManager, "mockDatabase");
        service.dropBase(mockManager, "mockDatabase");
        service.deleteRecord(mockManager, "mockTable", "mockKeyName", "mockKeyValue");
        service.dropTable(mockManager, "mockTable");
        service.createRecord(mockManager, "mockTable", new HashMap<>());
        service.createTable(mockManager, "mockTable", "mockKeyName", new HashMap<>());
        service.updateRecord(mockManager, "mockTable", "mockKeyName", "mockKeyValue",
                                                      new HashMap<>());

        service.connect("sqlcmd_log", "postgres", "postgres");
        List<List<String>> actions = manager.getTableData("user_actions");
        for (List<String> row : actions) {
            row.remove(0);
        }
        List<List<String>> databaseConnection = manager.getTableData("database_connection");
        String id1 = databaseConnection.get(0).get(0);
        String id2 = databaseConnection.get(1).get(0);
        assertEquals("[[" + id1 + ", sqlcmd, postgres], " +
                      "[" + id2 + ", sqlcmd_log, postgres]]", databaseConnection.toString());
        assertEquals("[[CONNECT, " + id1 + "], " +
                      "[CLEAR TABLE ( mockTable ), " + id1 + "], " +
                      "[GET TABLES LIST, " + id1 + "], " +
                      "[GET TABLE ( mockTable ), " + id1 + "], "  +
                      "[CREATE DATABASE ( mockDatabase ), " + id1 + "], " +
                      "[DELETE DATABASE ( mockDatabase ), " + id1 + "], " +
                      "[DELETE RECORD IN TABLE ( mockTable ) KEY = mockKeyValue, " + id1 + "], " +
                      "[DELETE TABLE ( mockTable ), " + id1 + "], " +
                      "[CREATE RECORD IN TABLE ( mockTable ), " + id1 + "], " +
                      "[CREATE TABLE ( mockTable ), " + id1 + "], " +
                      "[UPDATE RECORD IN TABLE ( mockTable ) KEY = mockKeyValue, " + id1 + "], " +
                      "[CONNECT, " + id2 + "]]", actions.toString());
    }*/
}
