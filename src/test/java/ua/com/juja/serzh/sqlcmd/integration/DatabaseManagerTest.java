package ua.com.juja.serzh.sqlcmd.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.BadSqlGrammarException;
import ua.com.juja.serzh.sqlcmd.Setup;
import ua.com.juja.serzh.sqlcmd.dao.databaseManager.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.dao.databaseManager.DatabaseManagerException;
import ua.com.juja.serzh.sqlcmd.dao.databaseManager.PostgreSQLManager;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by serzh on 5/11/16.
 */
@Ignore
public class DatabaseManagerTest {

    private static DatabaseManager manager;

    private static Setup setup = new Setup();
    private final static String DATABASE = "sqlcmd5hope5never5exist";
    private final static String USER = setup.getUser();
    private final static String PASSWORD = setup.getPassword();

    private final String SQL_QUERY_CREATE_TABLE_1 = "test1(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))";
    private final String TABLE_1 = "users";

    @BeforeClass
    public static void setup() {
        manager = new PostgreSQLManager();
        setup.setupData(manager, DATABASE);
    }

    @AfterClass
    public static void dropDatabase() {
        setup.dropData(manager);
    }

    @Test
    public void testGetAllTableNames() {
        Set<String> tables = manager.getTableNames();
        assertEquals("[users, users2, test1]", tables.toString());
    }

    @Test
    public void testGetTableData() {
        // given
        manager.clear(TABLE_1);
        // when
        Map<String, Object> input = new LinkedHashMap<>();
        input.put("name", "Vasia");
        input.put("password", "****");
        input.put("id", 22);
        manager.insert(TABLE_1, input);
        // then
        List<Map<String, Object>> users = manager.getTableData(TABLE_1);
        assertEquals(1, users.size());

        Map<String, Object> user = users.get(0);
        assertEquals("[Vasia, ****, 22]", user.values().toString());
        assertEquals("[name, password, id]", user.keySet().toString());
    }

    @Test
    public void testGetTableData2() {
        // given
        manager.clear(TABLE_1);
        // when
        Map<String, Object> input = new LinkedHashMap<>();
        input.put("name", "Vasia");
        input.put("password", "****");
        input.put("id", 22);
        manager.insert(TABLE_1, input);

        Map<String, Object> input2 = new LinkedHashMap<>();
        input2.put("name", "Stiven2");
        input2.put("password", "*****");
        input2.put("id", 12);
        manager.insert(TABLE_1, input2);
        // then
        List<Map<String, Object>> users = manager.getTableData(TABLE_1);
        assertEquals(2, users.size());

        Map<String, Object> user = users.get(0);
        assertEquals("[Vasia, ****, 22]", user.values().toString());
        assertEquals("[name, password, id]", user.keySet().toString());

        Map<String, Object> user2 = users.get(1);
        assertEquals("[Stiven2, *****, 12]", user2.values().toString());
        assertEquals("[name, password, id]", user2.keySet().toString());
    }

    @Test
    public void testUpdateTableData() {
        // given
        manager.clear(TABLE_1);

        Map<String, Object> input = new LinkedHashMap<>();
        input.put("name", "Kesha");
        input.put("password", "pass");
        input.put("id", 15);
        manager.insert(TABLE_1, input);
        // when
        Map<String, Object> newValue = new LinkedHashMap<>();
        newValue.put("password", "pass2");
        newValue.put("name", "Pup");
        manager.update(TABLE_1, "id", "15", newValue);
        // then
        List<Map<String, Object>> users = manager.getTableData(TABLE_1);
        assertEquals(1, users.size());

        Map<String, Object> user = users.get(0);
        assertEquals("[name, password, id]", user.keySet().toString());
        assertEquals("[Pup, pass2, 15]", user.values().toString());
    }

    @Test
    public void testGetColumnNames() {
        // when
        Set<String> columnNames = manager.getTableColumns(TABLE_1);
        // then
        assertEquals("[name, password, id]", columnNames.toString());
    }

    @Test
    public void getTableSize() {
        int size = manager.getTableSize(TABLE_1);
        assertEquals(1, size);
    }

    @Test
    public void clearTable() {
        // given
        // when
        manager.clear(TABLE_1);
        // then
        List<Map<String, Object>> users = manager.getTableData(TABLE_1);
        List<Map<String, Object>> expected = new ArrayList<>();
        assertEquals(0, users.size());
        assertEquals(expected, users);
    }

    @Test(expected = BadSqlGrammarException.class)
    public void testClearNotExistTable() {
        //when
        manager.clear("notExistTable");
    }

    @Test
    public void testIsConnected() {
        assertTrue(manager.isConnected());
    }

    @Test(expected = DatabaseManagerException.class)
    public void testConnectToNotExistDatabase() {
        //when
        try {
            manager.connect("notExistBase", null, null);
            fail();
        } catch (Exception e) {
            //then
            manager.connect(DATABASE, null, null);
            throw e;
        }
    }

    @Test(expected = DatabaseManagerException.class)
    public void testConnectToDatabaseWhenIncorrectUserAndPassword() {
        //when
        try {
            manager.connect(DATABASE, "notExistUser", "qwertyuiop");
            fail();
        } catch (Exception e) {
            //then
            manager.connect(DATABASE, USER, PASSWORD);
            throw e;
        }
    }

    @Test(expected = DatabaseManagerException.class)
    public void testConnectToServerWhenIncorrectUserAndPassword() {
        //when
        try {
            manager.connect(null, "notExistUser", "qwertyuiop");
            fail();
        } catch (Exception e) {
            //then
            manager.connect(DATABASE, USER, PASSWORD);
            throw e;
        }
    }

    @Test
    public void testGetDatabases() {
        //when
        Set<String> actual = manager.getDatabases();
        //then
        assertNotNull(actual);
    }

    @Test
    public void testCreateAndDropDatabase() {
        //given
        String newDatabase = "createdatabasetest";
        //when
        manager.createDatabase(newDatabase);
        //then
        Set<String> databases = manager.getDatabases();
        if (!databases.contains(newDatabase)) {
            fail();
        }
        //when
        manager.dropDatabase(newDatabase);
        //then
        Set<String> databases2 = manager.getDatabases();
        if (databases2.contains(newDatabase)) {
            fail();
        }
    }

    @Test
    public void testDropAndCreateTable() {
        manager.dropTable("test1");
        Set<String> tables = manager.getTableNames();
        assertEquals("[users, users2]", tables.toString());

        manager.createTable(SQL_QUERY_CREATE_TABLE_1);
        Set<String> tables2 = manager.getTableNames();
        assertEquals("[users, users2, test1]", tables2.toString());
    }

    @Test(expected = BadSqlGrammarException.class)
    public void testCreateTableWrongQuery() {
        //given
        String query = "testTable(qwerty)";
        //when
        manager.createTable(query);
    }

    @Test
    public void testGetTableColumns() {
        //given
        Set<String> expected = new LinkedHashSet<>(Arrays.asList("name", "password", "id"));
        //when
        Set<String> actual = manager.getTableColumns(TABLE_1);
        //then
        assertEquals(expected, actual);
    }

    @Test(expected = BadSqlGrammarException.class)
    public void testInsertNotExistTable() {
        //given
        Map<String, Object> newData = new LinkedHashMap<>();
        newData.put("name", "Bob");
        newData.put("password", "*****");
        newData.put("id", 1);
        //when
        //then
        manager.insert("nonExistTable", newData);
    }

    @Test
    public void testInsertWithId() {
        //given
        Map<String, Object> newData = new LinkedHashMap<>();
        newData.put("name", "Vasia");
        newData.put("password", "****");
        newData.put("id", "22");
        //when
        //then
        Map<String, Object> user = manager.getTableData(TABLE_1).get(0);
        assertEquals(newData.toString(), user.toString());
    }

    @Test
    public void testUpdate() {
        //given
        Map<String, Object> newData = new LinkedHashMap<>();
        newData.put("name", "testUser");
        newData.put("password", "azerty");
        newData.put("id", 5);

        manager.insert(TABLE_1, newData);

        //when
        Map<String, Object> updateData = new LinkedHashMap<>();
        updateData.put("name", "Bill");
        updateData.put("password", "qwerty");
        updateData.put("id", "5");

        manager.update(TABLE_1, "id", "5", updateData);

        //then
        Map<String, Object> user = manager.getTableData(TABLE_1).get(2);
        assertEquals(updateData.toString(), user.toString());
    }

    @Test(expected = BadSqlGrammarException.class)
    public void testUpdateNotExistTable() {
        //when
        Map<String, Object> updateData = new LinkedHashMap<>();
        updateData.put("username", "Bill");
        updateData.put("password", "qwerty");
        updateData.put("id", "1");
        //then
        manager.update("nonExistTable", "", "",  updateData);
    }
}