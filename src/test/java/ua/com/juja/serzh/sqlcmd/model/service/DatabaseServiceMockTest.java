package ua.com.juja.serzh.sqlcmd.model.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.juja.serzh.sqlcmd.model.databaseManager.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.service.Service;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ("classpath:test-application-context.xml"))
public class DatabaseServiceMockTest {

    @Autowired
    Service service;

    @Autowired
    DatabaseManager manager;

    /*@Autowired
    private UserActionRepository userActions;
*/
    @Test
    public void shouldAutowireDependencies() {
        assertNotNull(manager);
        assertNotNull(service);
    }

    @Test
    public void connectTest() {
        //given
        String database = "databaseName";
        String username = "username";
        String password = "password";

        //when
        service.connect(database, username, password);

        //then
        verify(manager).connect(database, username, password);
        //userActions.createAction( TODO ..
    }

    @Test
    public void databasesTest() throws Exception {
        service.databases(manager);
        verify(manager).getDatabasesName();

    }

    @Test
    public void tablesTest() throws Exception {
        service.tables(manager);
        verify(manager).getTableNames();

    }

    @Test
    public void testGetEmptyTableData() {
        String table = "test";

        List<Map<String, Object>> tableData = new LinkedList<>();
        Set<String> tableColumns = new LinkedHashSet<>(Arrays.asList("id", "name", "password"));

        List<List<Object>> expected = new LinkedList<>();
        expected.add(new LinkedList<>(tableColumns));

        when(manager.getTableColumns(table)).thenReturn(tableColumns);
        when(manager.getTableData(table)).thenReturn(tableData);
        //when
        List<List<String>> actual = service.getTableData(manager, table);
        //then
        assertEquals(expected.toString(), actual.toString());
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTableData() {
        String table = "test";

        Map<String, Object> user1 = new LinkedHashMap<>();
        user1.put("id", 1);
        user1.put("name", "Vas");
        user1.put("password", "****");

        Map<String, Object> user2 = new LinkedHashMap<>();
        user2.put("id", 2);
        user2.put("name", "Nik");
        user2.put("password", "******");

        List<Map<String, Object>> tableData = new LinkedList<>();
        tableData.add(user1);
        tableData.add(user2);

        Set<String> tableColumns = new LinkedHashSet<>(Arrays.asList("id", "name", "password"));
        List<Object> row1 = new LinkedList<>(Arrays.asList(1, "Vas", "****"));
        List<Object> row2 = new LinkedList<>(Arrays.asList(2, "Nik", "******"));

        List<List<Object>> expected = new LinkedList<>();
        expected.add(new LinkedList<>(tableColumns));
        expected.add(row1);
        expected.add(row2);

        when(manager.getTableColumns(table)).thenReturn(tableColumns);
        when(manager.getTableData(table)).thenReturn(tableData);
        //when
        List<List<String>> actual = service.getTableData(manager, table);
        //then
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void getTableData() throws Exception {

    }

    @Test
    public void getAll() throws Exception {

    }


/*    @Test
    public void testCreateEntry() {
        //given
        String table = "test";
        String[] values = {"John", "Smith"};
        when(manager.getTableColumns(table))
                .thenReturn(new LinkedHashSet<String>(Arrays.asList("username", "password")));

        Map<String, Object> expected = new LinkedHashMap<>();
        expected.put("username", "John");
        expected.put("password", "Smith");

      *//*  //when
        service.createEntry(manager, table, values);

        //then
        verify(manager).insert(table, expected);*//*
    }*/
}
